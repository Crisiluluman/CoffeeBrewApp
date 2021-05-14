package com.example.coffeebrewapp.Data.CoffeProduct;

public class CoffeeProduct {


    private String userId;
    private String coffeeName;
    private String imageSource;
    private float rating;
    private String brewmethod;
    private String description;

    //TODO: How to handle unique ID? Everytime a product is created, it should have a unique ID
    public CoffeeProduct(/*int ID, */String userId, String coffeeName, String imageUrl, float rating, String brewmethod, String description) {
        //this.ID = ID;

        if (coffeeName.trim().equals(""))
        {
            coffeeName = "No name written";
        }
        this.userId = userId;
        this.coffeeName = coffeeName;
        this.imageSource = imageUrl;
        this.rating = rating;
        this.brewmethod = brewmethod;
        this.description = description;
    }

    public CoffeeProduct() {
        //Empty constructor needed
    }

    //TODO: Delete after figuring out Firebase
    private int ID; // Some ID that makes each item unique

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    //Real stuff
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getBrewmethod() {
        return brewmethod;
    }

    public void setBrewmethod(String brewmethod) {
        this.brewmethod = brewmethod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

