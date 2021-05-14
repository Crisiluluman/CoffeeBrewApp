package com.example.coffeebrewapp.Repository.RemoteDataSource.CoffeeProduct;

import android.net.Uri;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;
import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProductLiveData;
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

    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private MutableLiveData<List<CoffeeProduct>> allCoffees;
    private CoffeeProductLiveData coffeeProductLiveData;


    private CoffeeProductDAO()
    {
        //databaseReference = FirebaseDatabase.getInstance("https://coffeebrewapp-2da9e-default-rtdb.europe-west1.firebasedatabase.app/").getReference("CoffeeProducts");

        //storageReference = FirebaseStorage.getInstance().getReference("CoffeeProducts_Images");
        //coffeeProductLiveData = new CoffeeProductLiveData(databaseReference);
    }

    public static synchronized CoffeeProductDAO getInstance() {
        if(instance == null)
            instance = new CoffeeProductDAO();
        return instance;
    }


    public void init(String userId) {
        databaseReference = FirebaseDatabase.getInstance("https://coffeebrewapp-2da9e-default-rtdb.europe-west1.firebasedatabase.app/").getReference("CoffeeProducts");

        storageReference = FirebaseStorage.getInstance().getReference("CoffeeProducts_Images");
        //coffeeProductLiveData = new CoffeeProductLiveData(databaseReference);
        allCoffees = new MutableLiveData<>(new ArrayList<>());

    }



    public LiveData<List<CoffeeProduct>> getAllCoffeeProducts()
    {
        List<CoffeeProduct> coffeeList = new ArrayList<>();

        //TODO: Fix hardcoded userid here
        DatabaseReference dbRef = databaseReference.child("jOZtgUhkRyWmrsE1G9hrfWwHaHG2");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()) {
                    CoffeeProduct coffeeProduct = snapshot.getValue(CoffeeProduct.class);
                    coffeeList.add(coffeeProduct);
                    System.out.println("\t Do we get here in LiveData method?");
                }
                allCoffees.setValue(coffeeList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return allCoffees;
    }


    public void uploadToFirebase(Uri tempUri, String uriExtension, String userID, String sCoffeeName, float rating, String brew, String description)
    {

        //Maybe add some error handling if no image is selected
        StorageReference fileReference = storageReference.child(System.currentTimeMillis() + uriExtension);

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

                //Creating the coffee product
                CoffeeProduct productToBeUploaded = new CoffeeProduct(userID,sCoffeeName,taskSnapshot.getMetadata().getReference().getDownloadUrl().toString(),rating,brew,description);

                //String uploadId = databaseReference.push().getKey();
                databaseReference.child(sCoffeeName).setValue(productToBeUploaded);

                //TODO: REMEMBER THAT YOU CAN UPDATE BY USING .updateChildren() Instead of .setValue
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


}
