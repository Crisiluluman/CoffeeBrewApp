package com.example.coffeebrewapp.UI.Frontpage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.coffeebrewapp.Data.Frontpage.FrontPageFeed;
import com.example.coffeebrewapp.Data.Frontpage.FrontPageFeedAdapter;
import com.example.coffeebrewapp.R;
import com.example.coffeebrewapp.Repository.RemoteDataSource.FronpageFeed.FrontpageFeedDAO;
import com.example.coffeebrewapp.UI.Login.LoginActivity;
import com.example.coffeebrewapp.UI.Main.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FrontpageFragment extends Fragment implements FrontPageFeedAdapter.OnListItemClickListener {

    private FrontpageViewModel viewModel;
    private RecyclerView recyclerView;

    private FrontPageFeedAdapter frontPageFeedAdapter;

    public static FloatingActionButton fab_search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_frontpage, container, false);
        viewModel = new ViewModelProvider(this).get(FrontpageViewModel.class);

        viewModel.init();

        String username = viewModel.getCurrentUser().getValue().getDisplayName();
        Toast.makeText(getContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();

        // Floating button to search page
        fab_search = layout.findViewById(R.id.floating_search_button);
        fab_search.setOnClickListener(v -> {
            MainActivity.navController.navigate(R.id.nav_searchCoffee);
        });

        // Recycler stuff
        recyclerView = layout.findViewById(R.id.frontpage_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.hasFixedSize();

        frontPageFeedAdapter = new FrontPageFeedAdapter(getContext(), this);
        viewModel.getFrontpageFeed().observe(getViewLifecycleOwner(), frontPageFeedAdapter::updatedList);
        frontPageFeedAdapter.notifyDataSetChanged();


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
