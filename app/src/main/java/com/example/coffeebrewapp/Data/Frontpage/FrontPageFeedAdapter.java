package com.example.coffeebrewapp.Data.Frontpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;
import com.example.coffeebrewapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FrontPageFeedAdapter extends RecyclerView.Adapter<FrontPageFeedAdapter.ViewHolder> {

    private List<FrontPageFeed> frontPageFeedList;
    private FrontPageFeedAdapter.OnListItemClickListener listener;
    private Context context;

    private int clickedVote = 0;
    private ImageView thumbsUp;
    private ImageView thumbsDown;


    public FrontPageFeedAdapter(Context context, FrontPageFeedAdapter.OnListItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FrontPageFeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.frontpage_feed_content_item, parent, false);


        //Thumbs down code
        thumbsDown = view.findViewById(R.id.frontpage_thumbs_down);
        thumbsDown.setImageResource(R.drawable.ic_thumb_down);

        thumbsUp = view.findViewById(R.id.frontpage_thumbs_up);
        thumbsUp.setImageResource(R.drawable.ic_thumb_up);


        //TODO: Fix buttons so they can only be pressed once (switch)
        thumbsDown.setOnClickListener(v -> {
            int temp = frontPageFeedList.get(clickedVote).getFeedRating();
            frontPageFeedList.get(clickedVote).setFeedRating(temp - 1);

            Toast.makeText(view.getContext(), "You have downvoted", Toast.LENGTH_SHORT).show();
            System.out.println("\t DownVote " + frontPageFeedList.get(clickedVote).getFeedRating());
            notifyDataSetChanged();
        });

        thumbsUp.setOnClickListener(v -> {
            int temp = frontPageFeedList.get(clickedVote).getFeedRating();
            frontPageFeedList.get(clickedVote).setFeedRating(temp + 1);

            Toast.makeText(view.getContext(), "You have upvoted", Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        });


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FrontPageFeedAdapter.ViewHolder holder, int position) {
        //Icons
        holder.followerIcon.setImageResource(R.drawable.ic_favorite);
        holder.shareIcon.setImageResource(R.drawable.ic_share);
        holder.mapsIcon.setImageResource(R.drawable.ic_maps);

        //Data
        Picasso.with(context).load(frontPageFeedList.get(position).getURL()).into(holder.userImage);
        holder.username.setText(frontPageFeedList.get(position).getCoffeeProduct().getUserId());

        holder.coffeeName.setText(frontPageFeedList.get(position).getCoffeeProduct().getCoffeeName());
        holder.coffeeRating.setRating(frontPageFeedList.get(position).getCoffeeProduct().getRating());
        holder.thumbsUp.setImageResource(R.drawable.ic_thumb_up);

        Picasso.with(context).load(frontPageFeedList.get(position).getCoffeeProduct().getImageSource()).into(holder.coffeeImage);


        // Sets icon depending on brew method
        switch (frontPageFeedList.get(position).getCoffeeProduct().getBrewmethod()) {
            case "Stempel":
                holder.brewmethodIcon.setImageResource(R.drawable.ic_stempel);
                break;

            case "Filter":
                holder.brewmethodIcon.setImageResource(R.drawable.ic_filter);
                break;

            default:
                holder.brewmethodIcon.setImageResource(R.drawable.ic_mocha);

        }

        // Remember that the user id is not set here
        // And remember that the description is not needed here
    }

    @Override
    public int getItemCount() {
        return frontPageFeedList.size();

    }

    public void updatedList(List<FrontPageFeed> frontPageFeeds) {

        frontPageFeedList = frontPageFeeds;

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView userImage;
        TextView username;
        ImageView followerIcon;
        ImageView shareIcon;
        ImageView mapsIcon;
        TextView coffeeName;
        RatingBar coffeeRating;

        // ImageView thumbsDown;

        ImageView thumbsUp;
        ImageView brewmethodIcon;
        ImageView coffeeImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: Fix that the view needs to be clicked before the position can be set
                    clickedVote = getLayoutPosition();
                    listener.onClick(getLayoutPosition());
                }
            });

            userImage = itemView.findViewById(R.id.frontpage_userImage);
            username = itemView.findViewById(R.id.frontpage_username);
            followerIcon = itemView.findViewById(R.id.frontpage_follow_ic);
            shareIcon = itemView.findViewById(R.id.frontpage_share_ic);
            mapsIcon = itemView.findViewById(R.id.frontpage_maps_ic);
            coffeeName = itemView.findViewById(R.id.frontpage_coffee_name);
            coffeeRating = itemView.findViewById(R.id.frontpage_rating);
            //thumbsDown = itemView.findViewById(R.id.frontpage_thumbs_down);
            thumbsUp = itemView.findViewById(R.id.frontpage_thumbs_up);
            brewmethodIcon = itemView.findViewById(R.id.frontpage_brewmethod);
            coffeeImage = itemView.findViewById(R.id.frontpage_coffee_image);


        }


    }


    public interface OnListItemClickListener {
        int onClick(int position);
    }


    //TODO: Implement sort func for when stuff is upvoted
    public void orderedList(ArrayList<FrontPageFeed> orderedList) {
        frontPageFeedList = orderedList;
        notifyDataSetChanged();
    }


}
