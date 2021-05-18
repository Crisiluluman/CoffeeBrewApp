package com.example.coffeebrewapp.UI.ProfilePage;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.coffeebrewapp.Data.ProfileData.ProfileData;
import com.example.coffeebrewapp.Repository.RemoteDataSource.User.UserRepository;

public class ProfilePageViewModel extends AndroidViewModel {

    private final UserRepository userRepository;

    public ProfilePageViewModel(@NonNull Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
    }

    public void init(){
        userRepository.init();
    }

    public void uploadProfileImage(Uri imageUri, String uriExtension) {
        userRepository.uploadProfileImage(imageUri, uriExtension);
    }

    public ProfileData getCurrentProfileData(){
        try {
            return userRepository.getCurrentUserProfileData();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
