package com.example.coffeebrewapp.UI.CreateCoffeeReview;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;
import com.example.coffeebrewapp.UI.Main.MainActivity;
import com.example.coffeebrewapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class CreateCoffeeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private CoffeeProduct productToBeCreated;
    private CreateCoffeeViewModel viewModel;

    //Image stuff
    static final int REQUEST_IMAGE_CAPTURE = 2; // For taking photos
    static final int PICK_IMAGE_REQUEST = 1;    // For picking a photo from gallery

    private Button imageChooser;
    private EditText coffeeName;
    private ImageView coffeeImage;
    private ImageView cameraIcon;
    private RatingBar initialRating;
    private EditText coffeeDescription;
    private ProgressBar uploadingImageBar;

    private FloatingActionButton fabUploadButton;
    private Spinner brewMethodsSpinner;

    private String brewMethod; // Saves selected String from Spinner to be put into new CoffeeProduct

    private Uri imageUri;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_create_coffee_review, container, false);
        viewModel = new ViewModelProvider(this).get(CreateCoffeeViewModel.class);
        viewModel.init();

        // Fixing up the spinner with pre-defined data (Filter, Stempel and Mocca)
        brewMethodsSpinner = layout.findViewById(R.id.brewMethodSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.brewMethod_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brewMethodsSpinner.setAdapter(adapter);
        brewMethodsSpinner.setOnItemSelectedListener(this);

        //Ratingbar
        initialRating = layout.findViewById(R.id.enter_coffee_rating);

        //Coffee description
        coffeeDescription = layout.findViewById(R.id.enter_coffee_description);

        // Image handling
        imageChooser = layout.findViewById(R.id.choose_filter_image);
        cameraIcon = layout.findViewById(R.id.choose_camera_image);

        uploadingImageBar = layout.findViewById(R.id.upload_create_image);
        coffeeImage = layout.findViewById(R.id.chosen_coffee_image);
        coffeeName = layout.findViewById(R.id.enter_coffee_name);

        cameraIcon.setOnClickListener(v -> {
            //makePhoto();
            dispatchTakePictureIntent();
        });

        imageChooser.setOnClickListener(v -> {
            openFileChooser();
        });



        fabUploadButton = (FloatingActionButton) layout.findViewById(R.id.floating_upload_button);
        fabUploadButton.setOnClickListener(v -> {

            if (coffeeName.getText().toString().isEmpty()|| coffeeName.getText().toString().equals(null))
            {
                Toast.makeText(getContext(), "Please enter a coffee name", Toast.LENGTH_SHORT).show();
            }
            else if (coffeeImage.equals(null)/* || imageUri == null*/)
            {
                Toast.makeText(getContext(), "Please select an Image", Toast.LENGTH_SHORT).show();
            }

            else if (imageUri == null)
            {
                Toast.makeText(getContext(), "Image uri miustake", Toast.LENGTH_SHORT).show();

            }

            else if (initialRating.getRating() == 0.0)
            {
                Toast.makeText(getContext(), "Please rate your coffee product", Toast.LENGTH_SHORT).show();
            }
            else if (coffeeDescription.getText().toString().isEmpty() || coffeeDescription.getText().toString().equals(null))
            {
                Toast.makeText(getContext(), "Please add a small description", Toast.LENGTH_SHORT).show();
            }
            else
            {
                //Sets variables for the CoffeeProduct
                String uriExtension = getFileExtension(imageUri);
                String sCoffeeName = coffeeName.getText().toString().trim();
                float rating = initialRating.getRating();
                String brew = brewMethod;
                String description = coffeeDescription.getText().toString();

                viewModel.uploadToFirebase(imageUri, uriExtension, sCoffeeName, sCoffeeName); //This makes it so that you can change products when creating.. Not optimal
                viewModel.uploadObjectToFirebase(sCoffeeName ,rating, brew, description, sCoffeeName);

                //Navigates to list of coffees
                MainActivity.navController.navigate(R.id.nav_searchCoffee);
            }

        });

        return layout;
    }

    @Override
    public void onResume() {
        fabUploadButton.show();
        super.onResume();
    }

    @Override
    public void onPause() {
        fabUploadButton.hide();
        super.onPause();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                Toast.makeText(getActivity().getApplicationContext(), "Filter selected", Toast.LENGTH_SHORT).show();
                brewMethod = "Filter";
                break;

            case 1:
                Toast.makeText(getActivity().getApplicationContext(), "Stempel selected", Toast.LENGTH_SHORT).show();
                brewMethod = "Stempel";
                break;

            case 2:
                Toast.makeText(getActivity().getApplicationContext(), "Mocca selected", Toast.LENGTH_SHORT).show();
                brewMethod = "Mocca";
                break;
        }
    }

    @Override //
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getActivity().getApplicationContext(), "No brew method selected", Toast.LENGTH_SHORT).show();
    }




    // Opens camera on a users phone
    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

    }

    // Opens images on a users phone
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    //Runs the activity result for either camera or images
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode)
        {
            case PICK_IMAGE_REQUEST:
                try {
                    imageUri = data.getData();
                    Picasso.with(getActivity().getApplicationContext()).load(imageUri).into(coffeeImage);
                } catch (NullPointerException e) {
                    Toast.makeText(getContext(), "No image was selected", Toast.LENGTH_SHORT).show();
                }
                break;

            case REQUEST_IMAGE_CAPTURE:

                try {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    coffeeImage.setImageBitmap(imageBitmap);
                    Uri tempUri = getImageUri(getContext(),imageBitmap);
                    imageUri = tempUri;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    //This happens if the user first selects and image from their device, and thereafter tries to take a picture
                    //with the camera, whilst the image from their device has already been selected
                    MainActivity.navController.navigate(R.id.nav_searchCoffee);
                    Toast.makeText(getContext(), "Something went wrong, please try again", Toast.LENGTH_SHORT).show();

                }

        }



    }

    //Retuns camera URI
    private Uri getImageUri(Context context, Bitmap imageBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), imageBitmap, "Title", null);
        return Uri.parse(path);
    }

    //Returns the file extension of the selected image: F.eks jpg or png
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
