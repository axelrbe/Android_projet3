package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private final List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {return neighbours;}

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {neighbours.add(neighbour);}

    /* Implémentation de la fonction pour changer le status de favoris */
    @Override
    public void changeFavorite(Neighbour neighbour) {
        if (neighbours.contains(neighbour)) {
            neighbours.get(neighbours.indexOf(neighbour)).setFavorite(!neighbour.checkIfFavorite());
        }
    }

    @Override
    public List<Neighbour> getAllFavorites() {
        /* Récupérer tout les favoris dans une liste */
        List<Neighbour> allFavoriteNeighbour = new ArrayList();
        for(Neighbour neighbour : neighbours) {
            if(neighbour.checkIfFavorite()) {
                allFavoriteNeighbour.add(neighbour);
            }
        }
        return allFavoriteNeighbour;
    }
}
