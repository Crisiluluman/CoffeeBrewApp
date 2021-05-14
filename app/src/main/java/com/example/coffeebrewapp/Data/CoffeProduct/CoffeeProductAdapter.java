package com.example.coffeebrewapp.Data.CoffeProduct;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeebrewapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CoffeeProductAdapter extends RecyclerView.Adapter<CoffeeProductAdapter.ViewHolder>  {

    private List<CoffeeProduct> coffeeProductList;

    OnListItemClickListener listener;

    public CoffeeProductAdapter(List<CoffeeProduct> coffeeProductList, OnListItemClickListener listener) {
        this.coffeeProductList = coffeeProductList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public CoffeeProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.coffee_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoffeeProductAdapter.ViewHolder holder, int position) {
        holder.coffeeProductName.setText(coffeeProductList.get(position).getCoffeeName());
        holder.coffeeProductName.setText(coffeeProductList.get(position).getCoffeeName());

        //TODO: Fix how to set image source
        //holder.coffeeProductImage.setImageResource(coffeeProductList.get(position).));
        //Picasso.with()
        holder.coffeeProductRating.setRating(coffeeProductList.get(position).getRating());
        holder.coffeeProductBrewmethod.setText(coffeeProductList.get(position).getBrewmethod());

        // Remember that the user id is not set here
        // And remember that the description is not needed here
    }

    @Override
    public int getItemCount() {
        return coffeeProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView coffeeProductName;
        ImageView coffeeProductImage;
        RatingBar coffeeProductRating;
        TextView coffeeProductBrewmethod;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition());
                }
            });
            coffeeProductName = itemView.findViewById(R.id.coffee_product_name);
            coffeeProductImage = itemView.findViewById(R.id.coffee_product_image);
            coffeeProductRating = itemView.findViewById(R.id.coffee_product_rating);
            coffeeProductBrewmethod = itemView.findViewById(R.id.coffee_product_brew);


        }
    }

    public interface OnListItemClickListener
    {
        void onClick(int position);
    }

    public void filterList(ArrayList<CoffeeProduct> filteredList)
    {
        coffeeProductList = filteredList;
        notifyDataSetChanged();
    }

}
