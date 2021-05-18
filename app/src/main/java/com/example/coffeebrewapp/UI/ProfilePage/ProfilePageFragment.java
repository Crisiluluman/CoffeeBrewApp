package com.example.coffeebrewapp.UI.ProfilePage;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.coffeebrewapp.R;
import com.example.coffeebrewapp.UI.Main.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ProfilePageFragment extends Fragment {

    static final int PICK_IMAGE_REQUEST = 1;    // For picking a photo from gallery

    private ProfilePageViewModel viewModel;
    private EditText usernameInput;
    private CircleImageView profileImage;
    private CircleImageView editIcon;
    private FloatingActionButton acceptChanges;

    private Uri imageUri;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.profile_page_fragment, container, false);
        viewModel = new ViewModelProvider(this).get(ProfilePageViewModel.class);

        //viewModel.init();

        usernameInput = layout.findViewById(R.id.edit_username_edittext);
        profileImage = layout.findViewById(R.id.display_profile_image);

        acceptChanges = layout.findViewById(R.id.floating_change_information_button);
        acceptChanges.setOnClickListener(v -> {
            String uriExtension = getFileExtension(imageUri);
            viewModel.uploadProfileImage(imageUri, uriExtension);

            Toast.makeText(getContext(), "Changes Saved", Toast.LENGTH_SHORT).show();
        });

        editIcon = layout.findViewById(R.id.edit_profile_page);
        editIcon.setOnClickListener(v -> {
            openFileChooser();
        });


        try {
            CircleImageView profileImage = MainActivity.header.findViewById(R.id.nav_profile_image);
            Picasso.with(MainActivity.header.getContext()).load(viewModel.getCurrentProfileData().getImageURL()).into(profileImage);
            System.out.println("COME ONE MAN\t\t" + viewModel.getCurrentProfileData().getImageURL());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Select a profile picture", Toast.LENGTH_SHORT).show();
        }


        return layout;
    }

    //Runs the activity result for either camera or images
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            System.out.println("HELLLLLLLLLLLLLLLLLLLOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO: " + data.getData());
            imageUri = data.getData();
            Picasso.with(getActivity().getApplicationContext()).load(imageUri).into(profileImage);
        }

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
