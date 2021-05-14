package com.example.coffeebrewapp.UI.FollowedPeople;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.coffeebrewapp.R;
import com.example.coffeebrewapp.UI.EditReviews.EditReviewsViewModel;

public class FollowedPeopleFragment extends Fragment {

    FollowedPeopleViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_followed_people,container,false);

        viewModel = new ViewModelProvider(this).get(FollowedPeopleViewModel.class);


        return layout;
    }
}
