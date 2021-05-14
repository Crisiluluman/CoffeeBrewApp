package com.example.coffeebrewapp.Data.CoffeProduct;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class CoffeeProductLiveData extends LiveData<CoffeeProduct> {

    private DatabaseReference databaseReference;
    private CoffeeProduct coffeeProduct;

    private final ValueEventListener listener = new ValueEventListener()
    {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            coffeeProduct = snapshot.getValue(CoffeeProduct.class);

            System.out.println("Testing coffee object from firebase: " + coffeeProduct.getCoffeeName() + " "
                    + coffeeProduct.getUserId() + " " + coffeeProduct.getImageSource() +
                    " " + coffeeProduct.getBrewmethod() + " " + coffeeProduct.getDescription() +
                    " " + coffeeProduct.getRating());

            //TODO: WHat does this below do?
            //setValue(coffeeProduct);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            //Display something back to the user
        }
    };

    public CoffeeProductLiveData(DatabaseReference ref) {
        databaseReference = ref;
    }

    @Override
    protected void onActive() {
        super.onActive();
        databaseReference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        databaseReference.removeEventListener(listener);
    }

}
