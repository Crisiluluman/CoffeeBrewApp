package com.example.coffeebrewapp.UI.ProfilePage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.coffeebrewapp.R;


public class ProfilePageFragment extends Fragment {

    ProfilePageViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.profile_page_fragment,container,false);
        viewModel = new ViewModelProvider(this).get(ProfilePageViewModel.class);

        return layout;
    }
}
