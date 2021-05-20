package com.example.coffeebrewapp.UI.Frontpage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.coffeebrewapp.Data.Frontpage.FrontPageFeed;
import com.example.coffeebrewapp.Repository.RemoteDataSource.FronpageFeed.FrontpageFeedDAO;
import com.example.coffeebrewapp.Repository.RemoteDataSource.User.UserRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class FrontpageViewModel extends AndroidViewModel {


    private final UserRepository userRepository;
    private final FrontpageFeedDAO frontpageFeedDAO;

    public FrontpageViewModel(@NonNull Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
        frontpageFeedDAO = FrontpageFeedDAO.getInstance();

    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    public LiveData<List<FrontPageFeed>> getFrontpageFeed()
    {
        return frontpageFeedDAO.getFrontpageFeed();
    }

    public void init()
    {
        frontpageFeedDAO.init();
    }

}
