package com.example.coffeebrewapp.Repository.RemoteDataSource.User;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.coffeebrewapp.Data.ProfileData.ProfileData;
import com.example.coffeebrewapp.Data.User.UserLiveData;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UserRepository {

    private final UserLiveData currentUser; // The data we work with


    private final Application app;
    private static UserRepository instance;

    private DatabaseReference allUsersRef;
    private StorageReference storageReference;
    private ProfileData temp;
    private MutableLiveData<ProfileData> liveProfilData;

    private UserRepository(Application app) {
        this.app = app;
        currentUser = new UserLiveData();
    }


    public void init() {
        allUsersRef = FirebaseDatabase.getInstance("https://coffeebrewapp-2da9e-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
        storageReference = FirebaseStorage.getInstance().getReference("User_Images");
        liveProfilData  = new MutableLiveData<>();
        temp = new ProfileData();
    }


    public static synchronized UserRepository getInstance(Application app) {
        if (instance == null)
            instance = new UserRepository(app);
        return instance;
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return currentUser;
    }

    public void signOut() {
        AuthUI.getInstance().signOut(app.getApplicationContext());
    }


    public MutableLiveData<ProfileData> getCurrentUserProfileData() throws NullPointerException {

        allUsersRef.child(getCurrentUser().getValue().getDisplayName()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                temp = snapshot.getValue(ProfileData.class);
                liveProfilData.setValue(temp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return liveProfilData;
    }


    public void uploadProfileImage(Uri imageUri, String uriExtension) {

        //Current username
        String username = getCurrentUser().getValue().getDisplayName();

        StorageReference fileReference = storageReference.child(System.currentTimeMillis() + ":" + uriExtension);

        fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        allUsersRef.child(username).child("username").setValue(getCurrentUser().getValue().getDisplayName());
                        allUsersRef.child(username).child("imageSource").setValue(uri.toString());

                    }
                });
            }
        });

    }

    public void updateUsername(String oldUsername, String newUsername) {
        allUsersRef.child(getCurrentUser().getValue().getDisplayName()).child("username").setValue(newUsername);

    }

}