package com.example.coffeebrewapp.UI.SearchSelectedCoffee;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;
import com.example.coffeebrewapp.R;
import com.example.coffeebrewapp.UI.SearchCoffee.SearchCoffeeFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class SearchSelectedCoffeeFragment extends Fragment {

    SearchSelectedCoffeeViewModel viewModel;

    TextView coffeeName;
    ImageView coffeeImage;
    RatingBar coffeeRating;
    ImageView coffeeBrewIcon;
    TextView coffeeDescription;
    RatingBar coffeeRate;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_searched_selected_coffee, container, false);
        viewModel = new ViewModelProvider(this).get(SearchSelectedCoffeeViewModel.class);


        //Initializing the page with data
        coffeeName = layout.findViewById(R.id.select_coffee_title);
        coffeeImage = layout.findViewById(R.id.select_coffee_image);
        coffeeRating = layout.findViewById(R.id.select_coffee_rating);
        coffeeBrewIcon = layout.findViewById(R.id.select_coffee_icon);
        coffeeDescription = layout.findViewById(R.id.select_coffee_description);


        CoffeeProduct product = viewModel.getProductFromName(viewModel.getCoffeeFromSearch());
        coffeeName.setText(product.getCoffeeName());
        coffeeRating.setRating(product.getRating());
        Picasso.with(getContext()).load(product.getImageSource()).into(coffeeImage);
        brewmethod(product.getBrewmethod());
        coffeeDescription.setText(product.getDescription());


        coffeeRate = layout.findViewById(R.id.select_coffee_rate);
        coffeeRate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(layout.getContext(), "Rating " + rating, Toast.LENGTH_SHORT).show();
            }
        });

        return layout;
    }

    private void brewmethod(String brewmethod){
        switch(brewmethod) {
            case "Stempel":
                coffeeBrewIcon.setImageResource(R.drawable.ic_stempel);
                break;
            case "Filter":
                coffeeBrewIcon.setImageResource(R.drawable.ic_filter);
                break;
            default:
                coffeeBrewIcon.setImageResource(R.drawable.ic_mocha);
        }
    }
}