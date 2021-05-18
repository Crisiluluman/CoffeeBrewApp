package com.example.coffeebrewapp.UI.ProfilePage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.coffeebrewapp.Repository.RemoteDataSource.User.UserRepository;

public class ProfilePageViewModel extends AndroidViewModel {

    private final UserRepository userRepository;

    public ProfilePageViewModel(@NonNull Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
    }


}
