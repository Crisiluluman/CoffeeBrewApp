package com.example.coffeebrewapp.UI.ProfilePage;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.coffeebrewapp.Data.ProfileData.ProfileData;
import com.example.coffeebrewapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

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
        View layout = inflater.inflate(R.layout.fragment_profile_page, container, false);
        viewModel = new ViewModelProvider(this).get(ProfilePageViewModel.class);

        //viewModel.init();

        usernameInput = layout.findViewById(R.id.edit_username_edittext);
        usernameInput.setText(viewModel.getCurrentUser().getValue().getDisplayName());

        profileImage = layout.findViewById(R.id.display_profile_image);

        acceptChanges = layout.findViewById(R.id.floating_change_information_button);
        acceptChanges.setOnClickListener(v -> {



            if (!usernameInput.getText().toString().equals(viewModel.getCurrentUser().getValue().getDisplayName()))
            {
                //Should ideally update the username, but that is not implemented yet
            }
            else {

                try {
                    String uriExtension = getFileExtension(imageUri); //Nullpointer here if the user hasnt changed image
                    viewModel.uploadProfileImage(imageUri, uriExtension);
                    Toast.makeText(getContext(), "Changes Saved", Toast.LENGTH_SHORT).show();

                } catch (NullPointerException e) {
                    Toast.makeText(getContext(), "No changes were made", Toast.LENGTH_SHORT).show();
                }
            }

        });

        editIcon = layout.findViewById(R.id.edit_profile_page);
        editIcon.setOnClickListener(v -> {

            openFileChooser();
        });

            profileImage = layout.findViewById(R.id.display_profile_image);

            final Observer<ProfileData> dataObserved = new Observer<ProfileData>() {
                @Override
                public void onChanged(ProfileData profileData) {

                    try {
                        Picasso.get().load(profileData.getImageSource()).into(profileImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                        profileImage.setImageResource(R.mipmap.ic_launcher);
                    }
                }
            };
            viewModel.getCurrentProfileData().observe(getViewLifecycleOwner(), dataObserved);



        return layout;
    }

    //Runs the activity result for either camera or images
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(profileImage);
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
