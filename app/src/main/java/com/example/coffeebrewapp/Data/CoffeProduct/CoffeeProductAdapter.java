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


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeebrewapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CoffeeProductAdapter extends RecyclerView.Adapter<CoffeeProductAdapter.ViewHolder> implements Filterable {

    private List<CoffeeProduct> coffeeProductList;
    private List<CoffeeProduct> filteredList;

    private OnListItemClickListener listener;
    private Context context;


    public CoffeeProductAdapter(OnListItemClickListener listener, Context context) {
        this.listener = listener;
        this.context = context;
        coffeeProductList = new ArrayList<>();
        filteredList = new ArrayList<>();
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

        holder.coffeeProductName.setText(filteredList.get(position).getCoffeeName());
        Picasso.with(context).load(filteredList.get(position).getImageSource()).into(holder.coffeeProductImage);
        holder.coffeeProductRating.setRating(filteredList.get(position).getRating());
        holder.coffeeProductBrewmethod.setText(filteredList.get(position).getBrewmethod());

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView coffeeProductName;

        ImageView coffeeProductImage;
        RatingBar coffeeProductRating;
        TextView coffeeProductBrewmethod;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coffeeProductName = itemView.findViewById(R.id.user_review_coffee);
            coffeeProductImage = itemView.findViewById(R.id.user_review_coffee_image);
            coffeeProductRating = itemView.findViewById(R.id.user_review_coffee_rating);
            coffeeProductBrewmethod = itemView.findViewById(R.id.user_review_coffee_brew);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            listener.onClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnListItemClickListener {
        void onClick(int position);
    }


    public void updatedList(List<CoffeeProduct> coffeeProducts) {
        filteredList = coffeeProductList;
        coffeeProductList = coffeeProducts;

        notifyDataSetChanged();
    }


    public CoffeeProduct getProduct(int position) {
        return filteredList.get(position);
    }




    //Method for filtering
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                List<CoffeeProduct> filtered = new ArrayList<>();
                String filterPattern = constraint.toString().toLowerCase();

                if (constraint == null || constraint.length() == 0) {
                    filtered.addAll(coffeeProductList);
                } else {

                    for (CoffeeProduct product : coffeeProductList) {
                        if (product.getCoffeeName().toLowerCase().contains(filterPattern)) {
                            filtered.add(product);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filtered;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<CoffeeProduct>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

}