package com.example.coffeebrewapp.Data.UserReview;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;
import com.example.coffeebrewapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserReviewsCoffeProductAdaper extends RecyclerView.Adapter<UserReviewsCoffeProductAdaper.ViewHolder> {

    private List<CoffeeProduct> coffeeProductList;

    private UserReviewsCoffeProductAdaper.OnListItemClickListener listener;
    private Context context;

    @NonNull
    @Override
    public UserReviewsCoffeProductAdaper.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_review_item, parent, false);
        return new ViewHolder(view);
    }

    public UserReviewsCoffeProductAdaper(UserReviewsCoffeProductAdaper.OnListItemClickListener listener, Context context) {
        this.listener = listener;
        this.context = context;
        coffeeProductList = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull UserReviewsCoffeProductAdaper.ViewHolder holder, int position) {
        holder.userReviewsCoffeename.setText(coffeeProductList.get(position).getCoffeeName());
        Picasso.with(context).load(coffeeProductList.get(position).getImageSource()).into(holder.userReviewsImage);
        holder.userReviewsRating.setRating(coffeeProductList.get(position).getRating());
        holder.userReviewsBrewmethod.setText(coffeeProductList.get(position).getBrewmethod());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView userReviewsCoffeename;
        ImageView userReviewsImage;
        RatingBar userReviewsRating;
        TextView userReviewsBrewmethod;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userReviewsCoffeename = itemView.findViewById(R.id.user_review_coffee);
            userReviewsImage = itemView.findViewById(R.id.user_review_coffee_image);
            userReviewsRating = itemView.findViewById(R.id.user_review_coffee_rating);
            userReviewsBrewmethod = itemView.findViewById(R.id.user_review_coffee_brew);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            listener.onClick(getAbsoluteAdapterPosition());
        }
    }

    public void updatedList(List<CoffeeProduct> coffeeProducts) {
        coffeeProductList = coffeeProducts;
        notifyDataSetChanged();
    }

    public interface OnListItemClickListener {
        void onClick(int position);
    }

    public CoffeeProduct getProduct(int position) {
        return coffeeProductList.get(position);
    }

    @Override
    public int getItemCount() {
        return coffeeProductList.size();
    }


}
