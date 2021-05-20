package com.example.coffeebrewapp.UI.UserReviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.coffeebrewapp.Data.UserReview.UserReviewsCoffeProductAdaper;
import com.example.coffeebrewapp.R;
import com.example.coffeebrewapp.UI.Main.MainActivity;

public class UserReviewsFragment extends Fragment implements UserReviewsCoffeProductAdaper.OnListItemClickListener{

    private UserReviewsViewModel viewModel;
    private RecyclerView recyclerView;
    private UserReviewsCoffeProductAdaper reviewsAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_user_reviews,container,false);
        viewModel = new ViewModelProvider(this).get(UserReviewsViewModel.class);

        viewModel.init();


        recyclerView = layout.findViewById(R.id.user_review_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.hasFixedSize();

        reviewsAdapter = new UserReviewsCoffeProductAdaper(this, getLayoutInflater().getContext());

        try {
            viewModel.getUserReviews().observe(getViewLifecycleOwner(),reviewsAdapter::updatedList);
            System.out.println("THE SIZE OF MY RUBIX " + viewModel.getUserReviews().getValue().size());
        } catch (NullPointerException e) {
            Toast.makeText(getContext(), "You have no reviews, go make some! :)", Toast.LENGTH_SHORT).show();
        }

        recyclerView.setAdapter(reviewsAdapter);

        reviewsAdapter.notifyDataSetChanged();

        return layout;
    }


    @Override
    public void onClick(int position) {
        String name = reviewsAdapter.getProduct(position).getCoffeeName();
        viewModel.setCoffeeFromSearch(name);
        MainActivity.navController.navigate(R.id.nav_edit_review);
    }
}
