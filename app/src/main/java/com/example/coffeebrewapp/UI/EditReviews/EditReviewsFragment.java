package com.example.coffeebrewapp.UI.EditReviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;
import com.example.coffeebrewapp.R;
import com.squareup.picasso.Picasso;

public class EditReviewsFragment extends Fragment {

    private EditReviewsViewModel viewModel;
    private TextView coffeeName;
    private CoffeeProduct product;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_edit_reviews, container, false);
        viewModel = new ViewModelProvider(this).get(EditReviewsViewModel.class);



        coffeeName = layout.findViewById(R.id.edit_coffee_name);


        final Observer<CoffeeProduct> dataObserved = new Observer<CoffeeProduct>() {
            @Override
            public void onChanged(CoffeeProduct coffeeProduct) {
                product = coffeeProduct;

                coffeeName.setText(product.getCoffeeName());

            }
        };
        viewModel.getProductFromName().observe(getViewLifecycleOwner(), dataObserved);


        return layout;
    }


}
