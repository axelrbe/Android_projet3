package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NeighbourInfoActivity extends AppCompatActivity implements Serializable {
    public static final String TAG = "neighbourInfo";

    TextView neighbourName1;
    TextView neighbourName2;
    TextView neighbourDesc;
    TextView neighbourFacebookUrl;
    TextView neighbourPhoneNumber;
    TextView neighbourAddress;
    ImageView profileImage;
    FloatingActionButton neighbourFavoriteBtn;

    ImageView arrowBack;

    protected Neighbour neighbour;

    private NeighbourApiService mApiService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_info);
        ButterKnife.bind(this);

        neighbour = getIntent().getParcelableExtra(MyNeighbourRecyclerViewAdapter.NEIGHBOUR_INFO);
        mApiService = DI.getNeighbourApiService();

        neighbourName1 = findViewById(R.id.neighbour_name);
        neighbourName2 = findViewById(R.id.neighbour_name2);
        neighbourDesc = findViewById(R.id.neighbour_desc);
        neighbourAddress = findViewById(R.id.neighbour_location);
        neighbourPhoneNumber = findViewById(R.id.neighbour_phone_number);
        neighbourFacebookUrl = findViewById(R.id.neighbour_facebook_url);
        neighbourFavoriteBtn = findViewById(R.id.neighbour_favorite);
        arrowBack = findViewById(R.id.arrow_back);
        profileImage = findViewById(R.id.neighbour_profile_picture);

        arrowBack.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ListNeighbourActivity.class);
            v.getContext().startActivity(intent);
        });

        setAllNeighbourInfos();
        changeFavoriteStatus();
    }

    private void setAllNeighbourInfos() {
        if(neighbour != null) {
            neighbourName1.setText(neighbour.getName());
            neighbourName2.setText(neighbour.getName());
            neighbourDesc.setText(neighbour.getAboutMe());
            neighbourFacebookUrl.setText(String.format("www.facebook.com/%s", neighbour.getName()));
            neighbourPhoneNumber.setText(neighbour.getPhoneNumber());
            neighbourAddress.setText(neighbour.getAddress());
            Glide.with(this).load(neighbour.getAvatarUrl()).centerCrop().into(profileImage);
        }
    }

    private void changeFavoriteStatus() {
        Log.d(TAG, "changeFavoriteStatus: " + neighbour.checkIfFavorite());

        if(neighbour.checkIfFavorite()) {
            neighbourFavoriteBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_white_24dp));
        } else {
            neighbourFavoriteBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_border_white_24dp));
        }

        neighbourFavoriteBtn.setOnClickListener(v -> {
            mApiService.changeFavorite(neighbour);

            if(neighbour.checkIfFavorite()) {
                neighbour.setFavorite(false);
            } else {
                neighbour.setFavorite(true);
            }

            changeFavoriteStatus();
        });
    }
}