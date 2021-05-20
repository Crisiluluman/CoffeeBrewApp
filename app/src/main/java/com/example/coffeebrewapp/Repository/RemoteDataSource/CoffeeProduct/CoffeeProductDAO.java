package com.example.coffeebrewapp.Repository.RemoteDataSource.CoffeeProduct;

import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class CoffeeProductDAO {


    private static CoffeeProductDAO instance;

    private DatabaseReference databaseAllCoffee;

    private StorageReference storageReference;

    private MutableLiveData<List<CoffeeProduct>> allCoffees;


    private CoffeeProductDAO()
    {

    }

    public static synchronized CoffeeProductDAO getInstance() {
        if(instance == null)
            instance = new CoffeeProductDAO();
        return instance;
    }


    public void init() {
        databaseAllCoffee = FirebaseDatabase.getInstance("https://coffeebrewapp-2da9e-default-rtdb.europe-west1.firebasedatabase.app/").getReference("CoffeeProducts");

        storageReference = FirebaseStorage.getInstance().getReference("CoffeeProducts_Images");
        allCoffees = new MutableLiveData<>(new ArrayList<>());

    }

    public LiveData<CoffeeProduct> getProductFromName(String productName)
    {
        MutableLiveData<CoffeeProduct> liveProduct = new MutableLiveData<>();

        databaseAllCoffee.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot snap: snapshot.getChildren())
                {
                    CoffeeProduct coffeeProduct = snap.getValue(CoffeeProduct.class);
                    if (coffeeProduct.getCoffeeName().equals(productName))
                    {
                        coffeeProduct = snap.getValue(CoffeeProduct.class);

                        liveProduct.setValue(coffeeProduct);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return liveProduct;
    }

    /*
    public MutableLiveData<List<CoffeeProduct>> getProductFromName(String productName)
    {
        List<CoffeeProduct> coffeeList = new ArrayList<>();

        databaseAllCoffee.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                coffeeList.clear();
                for (DataSnapshot snap: snapshot.getChildren())
                {
                    CoffeeProduct coffeeProduct = snap.getValue(CoffeeProduct.class);
                    if (coffeeProduct.getCoffeeName().equals(productName))
                    {
                        coffeeList.add(coffeeProduct);
                    }
                    else if (productName.isEmpty())
                    {
                        coffeeList.add(coffeeProduct);
                    }
                }

                allCoffees.setValue(coffeeList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return allCoffees;
    }
     */

    public LiveData<List<CoffeeProduct>> getAllCoffeeProducts()
    {

        List<CoffeeProduct> coffeeList = new ArrayList<>();

        databaseAllCoffee.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                coffeeList.clear();
                for (DataSnapshot snap: snapshot.getChildren()) {
                    CoffeeProduct coffeeProduct = snap.getValue(CoffeeProduct.class);

                    coffeeList.add(coffeeProduct);

                }

                allCoffees.setValue(coffeeList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

/*
        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                allCoffees.getValue().add(snapshot.getValue(CoffeeProduct.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/
        return allCoffees;
    }


    public void uploadImageToFirebase(Uri tempUri, String uriExtension, String sCoffeeName, String oldCoffeName)
    {

        //Maybe add some error handling if no image is selected
        StorageReference fileReference = storageReference.child(System.currentTimeMillis() + ":" + uriExtension);


        fileReference.putFile(tempUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Sets the progress bar to zeo, as the file has been uploaded
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Delays the reset of progress bar for half a second
                        // Than sets the progress bar to zero again
                    }
                }, 500);
                //Toast "Upload successfull" or something

                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        if (oldCoffeName.equals(sCoffeeName))
                        {
                            databaseAllCoffee.child(sCoffeeName).child("imageSource").setValue(uri.toString());
                            databaseAllCoffee.child(sCoffeeName).child("coffeeName").setValue(sCoffeeName);
                        }
                        else {
                            databaseAllCoffee.child(oldCoffeName).setValue(sCoffeeName); //This should change the name of the child product, but duplicates the data instead
                            databaseAllCoffee.child(oldCoffeName).child("imageSource").setValue(uri.toString());
                            databaseAllCoffee.child(oldCoffeName).child("coffeeName").setValue(sCoffeeName);
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) throws NullPointerException {
                //Should give the user a warning if no image is selected
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                //Should update the Progress bar not sure how to do that from here
            }
        });
    }

    public void uploadObjectToFirebase(String userID, String sCoffeeName, float rating, String brew, String description, String oldCoffeName)
    {
        CoffeeProduct productToBeUploaded = new CoffeeProduct(userID,sCoffeeName,rating,brew,description);
        //databaseAllCoffee.child(sCoffeeName).setValue(productToBeUploaded);

        if (oldCoffeName.equals(sCoffeeName)){
            databaseAllCoffee.child(sCoffeeName).setValue(productToBeUploaded);
        }

        else {
        databaseAllCoffee.child(oldCoffeName).setValue(sCoffeeName);
        databaseAllCoffee.child(oldCoffeName).child("coffeeName").setValue(sCoffeeName);
        databaseAllCoffee.child(oldCoffeName).child("brewMethod").setValue(brew);
        databaseAllCoffee.child(oldCoffeName).child("description").setValue(description);
        databaseAllCoffee.child(oldCoffeName).child("userId").setValue(userID);
        databaseAllCoffee.child(oldCoffeName).child("rating").setValue(rating);
        }
        /*if (databaseAllCoffee.child(s).equals(sCoffeeName))
        {
            databaseAllCoffee.child(sCoffeeName).setValue(sCoffeeName);
            databaseAllCoffee.child(sCoffeeName).setValue(productToBeUploaded);
        }
        else
            {



            }*/

    }


    public LiveData<List<CoffeeProduct>> getUserReviews(String displayName)throws NullPointerException {

        MutableLiveData<List<CoffeeProduct>> userReviews = new MutableLiveData<>(new ArrayList<>());
        DatabaseReference dbRef = FirebaseDatabase.getInstance("https://coffeebrewapp-2da9e-default-rtdb.europe-west1.firebasedatabase.app/").getReference("CoffeeProducts");
        List<CoffeeProduct> coffeeList = new ArrayList<>();


        dbRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                coffeeList.clear();
                for (DataSnapshot snap: snapshot.getChildren()) {
                    CoffeeProduct coffeeProduct = snap.getValue(CoffeeProduct.class);

                    if (coffeeProduct.getUserId().equals(displayName))
                    {
                        coffeeList.add(coffeeProduct);

                    }

                }
                System.out.println("THE SLICE IS: " + coffeeList.size());
                userReviews.setValue(coffeeList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        System.out.println("THE SIZE IS: " + userReviews.getValue().size());

        return userReviews;
    }
}
