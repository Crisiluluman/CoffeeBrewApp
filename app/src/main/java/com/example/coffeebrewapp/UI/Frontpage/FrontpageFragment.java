package com.example.coffeebrewapp.UI.Frontpage;

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


import com.example.coffeebrewapp.Data.Frontpage.FrontPageFeed;
import com.example.coffeebrewapp.Data.Frontpage.FrontPageFeedAdapter;
import com.example.coffeebrewapp.R;
import com.example.coffeebrewapp.UI.Login.LoginActivity;
import com.example.coffeebrewapp.UI.Main.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FrontpageFragment extends Fragment implements FrontPageFeedAdapter.OnListItemClickListener{

    private FrontpageViewModel viewModel;
    private RecyclerView recyclerView;

    private List<FrontPageFeed> feedList;
    private FrontPageFeedAdapter frontPageFeedAdapter;

    public static FloatingActionButton fab_search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_frontpage, container, false);
        viewModel = new ViewModelProvider(this).get(FrontpageViewModel.class);


        // Recycler stuff
        recyclerView = layout.findViewById(R.id.frontpage_recycler);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.hasFixedSize();

        // Floating button to search page
        fab_search = layout.findViewById(R.id.floating_search_button);
        fab_search.setOnClickListener(v -> {
            MainActivity.navController.navigate(R.id.nav_searchCoffee);
        });

        // Hardcoded test data
        feedList = new ArrayList<>();

        feedList.add(new FrontPageFeed(1,1,R.drawable.test_user,"Crisilulumaner",4,"Nestle, just the best", 2,R.drawable.coffee_one,"Filter"));
        feedList.add(new FrontPageFeed(2,1,R.drawable.test_user,"Crisilulumanerer",2,"Nestle,  the best", 2,R.drawable.coffee_two,"Stempel"));
        feedList.add(new FrontPageFeed(3,1,R.drawable.test_user,"Crisilulumanererer",1,"Nestle, just  best", 2,R.drawable.coffee_one,"Mocha"));
        feedList.add(new FrontPageFeed(4,1,R.drawable.test_user,"Crisilulumanerere",5,"Nestle, just the best", 2,R.drawable.coffee_two,"Mocha"));
        feedList.add(new FrontPageFeed(5,1,R.drawable.test_user,"Crisilulumanererere",3,"Nestle, ", 2,R.drawable.coffee_one,"Filter"));

        frontPageFeedAdapter = new FrontPageFeedAdapter(feedList, this);
        recyclerView.setAdapter(frontPageFeedAdapter);


        return layout;

    }

    @Override
    public void onResume() {
        fab_search.show();
        super.onResume();
    }

    @Override
    public void onPause() {
        fab_search.hide();
        super.onPause();
    }

    @Override
    public int onClick(int position) {
        return position;
    }




}
