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

public class CoffeeProductAdapter extends RecyclerView.Adapter<CoffeeProductAdapter.ViewHolder> implements Filterable {

    private List<CoffeeProduct> coffeeProductList;
    private List<CoffeeProduct> filteredList;

    private OnListItemClickListener listener;
    private Context context;


    public CoffeeProductAdapter(OnListItemClickListener listener, Context context) {
        this.listener = listener;
        this.context = context;
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
        Picasso.with(context).load(coffeeProductList.get(position).getImageSource()).into(holder.coffeeProductImage);
        holder.coffeeProductRating.setRating(coffeeProductList.get(position).getRating());
        holder.coffeeProductBrewmethod.setText(coffeeProductList.get(position).getBrewmethod());

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
            coffeeProductName = itemView.findViewById(R.id.coffee_product_name);
            coffeeProductImage = itemView.findViewById(R.id.coffee_product_image);
            coffeeProductRating = itemView.findViewById(R.id.coffee_product_rating);
            coffeeProductBrewmethod = itemView.findViewById(R.id.coffee_product_brew);
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
    //TODO: Not working quite right
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            //For some reason if the CoffeeName has numbers in it, it makes this method react kinda weirdly... It only works on String/ CharSequences
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                String searchStr = constraint.toString();

                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = coffeeProductList.size();
                    filterResults.values = coffeeProductList;

                } else {
                    final List<CoffeeProduct> resultsModel = new ArrayList<>();

                    for (CoffeeProduct product : coffeeProductList) {
                        if (product.getCoffeeName().toLowerCase().contains(searchStr)) {
                            resultsModel.add(product);
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (List<CoffeeProduct>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

}


