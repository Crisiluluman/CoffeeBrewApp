package com.example.coffeebrewapp.Repository.RemoteDataSource.FronpageFeed;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;
import com.example.coffeebrewapp.Data.Frontpage.FrontPageFeed;
import com.example.coffeebrewapp.Data.ProfileData.ProfileData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FrontpageFeedDAO {

    private static FrontpageFeedDAO instance;

    private DatabaseReference allUsersRef;
    private DatabaseReference allProductsRef;
    private MutableLiveData<List<FrontPageFeed>> frontpageFeedList;

    private FrontpageFeedDAO(){}

    public static synchronized FrontpageFeedDAO getInstance() {
        if(instance == null)
            instance = new FrontpageFeedDAO();
        return instance;
    }


    public void init()
    {
        allProductsRef = FirebaseDatabase.getInstance("https://coffeebrewapp-2da9e-default-rtdb.europe-west1.firebasedatabase.app/").getReference("CoffeeProducts");
        allUsersRef= FirebaseDatabase.getInstance("https://coffeebrewapp-2da9e-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        frontpageFeedList = new MutableLiveData<>(new ArrayList<>());
    }


    public LiveData<List<FrontPageFeed>> getFrontpageFeed() {

        List<FrontPageFeed> coffeeList = new ArrayList<>();

        allProductsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                coffeeList.clear();
                for (DataSnapshot snap: snapshot.getChildren())
                {
                    FrontPageFeed feed = new FrontPageFeed();
                    CoffeeProduct product = snap.getValue(CoffeeProduct.class);
                    feed.setCoffeeProduct(product);

                        allUsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot snap2: snapshot.getChildren()) {
                                if (snap2.getValue(ProfileData.class).getUsername().equals(product.getUserId()))
                                {
                                    String imgURL = snap2.child("imageSource").getValue(String.class);
                                    feed.setURL(imgURL);
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                    coffeeList.add(feed);
                }
                frontpageFeedList.setValue(coffeeList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        return frontpageFeedList;
    }



}
