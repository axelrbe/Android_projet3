package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    // Verifier que le status de favori d'un voisin change
    @Test
    public void changeFavoriteStatusToTrueWithSuccess() {
        Neighbour neighbour = service.getNeighbours().get(7);
        // Mettre le neighbour en favori
        assertFalse(neighbour.checkIfFavorite());
        service.changeFavorite(neighbour);
        // Récupérer le même voisin
        Neighbour neighbour2 = service.getNeighbours().get(7);
        // Verifier que les deux voisins sont bien les mêmes et si il est bien passer en favori
        assertEquals(neighbour.getId(), neighbour2.getId());
        assertTrue(neighbour2.checkIfFavorite());
        service.changeFavorite(neighbour);
    }

    @Test
    public void changeFavoriteStatusToFalseWithSuccess() {
        Neighbour neighbour = service.getNeighbours().get(4);
        // Mettre le neighbour en favori
        service.changeFavorite(neighbour);
        // Le retirer des favoris
        service.changeFavorite(neighbour);
        // Récupérer le même voisin
        Neighbour neighbour2 = service.getNeighbours().get(4);
        // Verifier que les deux voisins sont bien les mêmes et si il est bien retirer des favori
        assertEquals(neighbour.getId(), neighbour2.getId());
        assertFalse(neighbour2.checkIfFavorite());
    }

    // Test pour verifier qu'on obtient bien la liste des favoris
    @Test
    public void getAllFavoriteWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        // Mettre les 3 premiers en favoris
        service.changeFavorite(neighbours.get(0));
        service.changeFavorite(neighbours.get(1));
        service.changeFavorite(neighbours.get(2));
        // Vérifier que le nombre de voisin présent dans la liste est égale au nombre de voisin en favoris
        List<Neighbour> favoriteNeighbours = service.getAllFavorites();
        assertEquals(3, favoriteNeighbours.size());
    }
}
