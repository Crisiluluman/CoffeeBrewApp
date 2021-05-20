package com.example.coffeebrewapp.Data.CoffeProduct;

public class CoffeeProduct {


    private String userId;
    private String coffeeName;
    private String imageSource;
    private float rating;
    private String brewmethod;
    private String description;

    public CoffeeProduct(String userId, String coffeeName, float rating, String brewmethod, String description) {
        if (coffeeName.trim().equals(""))
        {
            coffeeName = "No name written";
        }
        this.userId = userId;
        this.coffeeName = coffeeName;
        this.rating = rating;
        this.brewmethod = brewmethod;
        this.description = description;
    }

    public CoffeeProduct() {
        //Empty constructor needed
    }


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

