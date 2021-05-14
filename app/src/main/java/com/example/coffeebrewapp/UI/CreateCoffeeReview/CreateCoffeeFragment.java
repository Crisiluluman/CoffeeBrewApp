package com.example.coffeebrewapp.UI.CreateCoffeeReview;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
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
import com.example.coffeebrewapp.UI.SearchCoffee.SearchCoffeeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class CreateCoffeeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private CoffeeProduct productToBeCreated;
    private CreateCoffeeViewModel viewModel;

    //Camera stuff
    static final int REQUEST_IMAGE_CAPTURE = 1; // For taking photos
    static final int PICK_IMAGE_REQUEST = 1;    // For picking a photo from gallery

    private Button imageChooser;
    private EditText coffeeName;
    private ImageView coffeeImage;

    private FloatingActionButton fabUploadButton;
    private Spinner brewMethodsSpinner;

    private RatingBar initialRating;
    private EditText coffeeDescription;
    private ProgressBar uploadingImageBar;

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
        imageChooser = layout.findViewById(R.id.choose_coffee_image);

        uploadingImageBar = layout.findViewById(R.id.upload_create_image);

        coffeeImage = layout.findViewById(R.id.chosen_coffee_image);
        coffeeName = layout.findViewById(R.id.enter_coffee_name);

        imageChooser.setOnClickListener(v -> {
            openFileChooser();
        });



        //Upload Floating button
        SearchCoffeeFragment.fab_add.hide();    // Accessing floating buttons from main activity and hiding their visibility
        MainActivity.fab_search.hide();

        fabUploadButton = (FloatingActionButton) layout.findViewById(R.id.floating_upload_button);
        fabUploadButton.setOnClickListener(v -> {
            // Enabling their visibility again for the other fragments
            SearchCoffeeFragment.fab_add.show();
            MainActivity.fab_search.show();

            //Sets variables for the CoffeeProduct
            String uriExtension = getFileExtension(imageUri);
            String sCoffeeName = coffeeName.getText().toString().trim();
            float rating = initialRating.getRating();
            String brew = brewMethod;
            String description = coffeeDescription.getText().toString();

            viewModel.uploadToFirebase(imageUri, uriExtension, sCoffeeName, rating, brew, description);

            //Navigates to list of coffees
            MainActivity.navController.navigate(R.id.nav_searchCoffee);
        });




        return layout;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                // TODO: Fix how to get string value
                Toast.makeText(getActivity().getApplicationContext(), "Filter", Toast.LENGTH_SHORT).show();
                brewMethod = "Filter";
                break;

            case 1:
                Toast.makeText(getActivity().getApplicationContext(), "Stempel", Toast.LENGTH_SHORT).show();
                brewMethod = "Stempel";
                break;

            case 2:
                Toast.makeText(getActivity().getApplicationContext(), "Mocca", Toast.LENGTH_SHORT).show();
                brewMethod = "Mocca";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getActivity().getApplicationContext(), "No brew method selected", Toast.LENGTH_SHORT).show();
    }


    // Upload image to firebase storage
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            imageUri = data.getData();
            Picasso.with(getActivity().getApplicationContext()).load(imageUri).into(coffeeImage);
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    //Returns the file extension of the selected image: F.eks jpg or png
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
