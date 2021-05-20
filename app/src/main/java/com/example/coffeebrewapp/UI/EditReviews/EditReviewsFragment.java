package com.example.coffeebrewapp.UI.EditReviews;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;
import com.example.coffeebrewapp.R;
import com.example.coffeebrewapp.UI.Main.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class EditReviewsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    //Image stuff
    static final int REQUEST_IMAGE_CAPTURE = 2; // For taking photos
    static final int PICK_IMAGE_REQUEST = 1;    // For picking a photo from gallery

    private CoffeeProduct product;
    private EditReviewsViewModel viewModel;

    private EditText editCoffeeName;
    private Button editCoffeeImageFile;
    private ImageView editCoffeeImageCamera;
    private ImageView coffeeImage;
    private RatingBar editRating;
    private Spinner editBrew;
    private EditText editDescription;
    private FloatingActionButton saveEdits;

    private String newBrewMethod;
    private Uri newImageURI;
    private String oldCoffeeName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //This class runs similar to the CreateCoffeeProduct, but ideally there should be functionality to ONLY update coffee products that are tied to the specific user
        //That has not been implemented though
        View layout = inflater.inflate(R.layout.fragment_edit_reviews, container, false);
        viewModel = new ViewModelProvider(this).get(EditReviewsViewModel.class);

        viewModel.init();


        editCoffeeName = layout.findViewById(R.id.edit_coffee_name);
        editCoffeeImageFile = layout.findViewById(R.id.edit_image_gallary);
        editCoffeeImageCamera = layout.findViewById(R.id.edit_image_camera);
        coffeeImage = layout.findViewById(R.id.edit_coffee_image_provided);
        editRating = layout.findViewById(R.id.edit_rating_bar);
        editDescription = layout.findViewById(R.id.edit_coffee_description_edit);
        saveEdits = layout.findViewById(R.id.edit_save_changes);

        //Initializing spinner
        editBrew = layout.findViewById(R.id.edit_brew_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.brewMethod_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editBrew.setAdapter(adapter);
        editBrew.setOnItemSelectedListener(this);


        final Observer<CoffeeProduct> dataObserved = new Observer<CoffeeProduct>() {
            @Override
            public void onChanged(CoffeeProduct coffeeProduct) {
                product = coffeeProduct;
                oldCoffeeName = coffeeProduct.getCoffeeName();

                editCoffeeName.setText(product.getCoffeeName());

                editCoffeeImageFile.setOnClickListener(v -> {
                    openFileChooser();
                });

                editCoffeeImageCamera.setOnClickListener(v -> {
                    dispatchTakePictureIntent();
                });

                Picasso.with(getContext()).load(product.getImageSource()).into(coffeeImage);

                editRating.setRating(product.getRating());

                editDescription.setText(product.getDescription());
            }
        };
        viewModel.getProductFromName().observe(getViewLifecycleOwner(), dataObserved);


        saveEdits.setOnClickListener(v -> {

            if (editCoffeeName.getText().toString().isEmpty() || editCoffeeName.getText().toString().equals(null)) {
                Toast.makeText(getContext(), "Please enter a coffee name", Toast.LENGTH_SHORT).show();
            } else if (coffeeImage.equals(null)/* || imageUri == null*/) {
                Toast.makeText(getContext(), "Please select an Image", Toast.LENGTH_SHORT).show();
            } else if (newImageURI == null) {
                Toast.makeText(getContext(), "Something went wrong with the image, please select a new image", Toast.LENGTH_SHORT).show();

            } else if (editRating.getRating() == 0.0) {
                Toast.makeText(getContext(), "Please rate your coffee product", Toast.LENGTH_SHORT).show();
            } else if (editDescription.getText().toString().isEmpty() || editDescription.getText().toString().equals(null)) {
                Toast.makeText(getContext(), "Please add a small description", Toast.LENGTH_SHORT).show();
            } else {
                //Sets variables for the CoffeeProduct
                String uriExtension = getFileExtension(newImageURI);
                String sCoffeeName = editCoffeeName.getText().toString().trim();
                float rating = editRating.getRating();
                String brew = newBrewMethod;
                String description = editDescription.getText().toString();

                viewModel.uploadToFirebase(newImageURI, uriExtension, sCoffeeName, oldCoffeeName);
                viewModel.uploadObjectToFirebase(sCoffeeName, rating, brew, description, oldCoffeeName);

                //Navigates to list of coffees
                MainActivity.navController.navigate(R.id.nav_user_reviews);
            }
        });


        return layout;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Toast.makeText(getActivity().getApplicationContext(), "Filter selected", Toast.LENGTH_SHORT).show();
                editBrew.setSelection(position);
                newBrewMethod = "Filter";
                break;

            case 1:
                Toast.makeText(getActivity().getApplicationContext(), "Stempel selected", Toast.LENGTH_SHORT).show();
                editBrew.setSelection(position);
                newBrewMethod = "Stempel";
                break;

            case 2:
                Toast.makeText(getActivity().getApplicationContext(), "Mocca selected", Toast.LENGTH_SHORT).show();
                editBrew.setSelection(position);
                newBrewMethod = "Mocca";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    // Opens camera on a users phone
    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, newImageURI);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

    }

    // Opens images on a users phone
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    //Runs the activity result for either camera or images
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Update the switch to use requestCode and resultCode for a more error handling

        switch (requestCode) {
            case PICK_IMAGE_REQUEST:
                try {
                    newImageURI = data.getData();
                    Picasso.with(getActivity().getApplicationContext()).load(newImageURI).into(coffeeImage);
                } catch (NullPointerException e) {
                    Toast.makeText(getContext(), "No image was selected", Toast.LENGTH_SHORT).show();
                }
                break;

            case REQUEST_IMAGE_CAPTURE:

                try {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    coffeeImage.setImageBitmap(imageBitmap);
                    Uri tempUri = getImageUri(getContext(), imageBitmap);
                    newImageURI = tempUri;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    //This happens if the user first selects and image from their device, and thereafter tries to take a picture
                    //with the camera, whilst the image from their device has already been selected
                    MainActivity.navController.navigate(R.id.nav_searchCoffee);
                    Toast.makeText(getContext(), "Something went wrong, please try again", Toast.LENGTH_SHORT).show();

                }
        }

    }

    //Returns the Uri path of the image taken with Camera
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
