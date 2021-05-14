package com.example.coffeebrewapp.Data.CreatedProduct;



public class CreatedCoffeeReview {


    private int userId; // <-- The unique ID of the user that created the review
    private String coffeeName;
    private int imageSource;
    private int rating;
    private String brewmethod;
    private String description;

    public CreatedCoffeeReview(int userId, String coffeeName, int imageSource, int rating, String brewmethod, String description) {
        this.userId = userId;
        this.coffeeName = coffeeName;
        this.imageSource = imageSource;
        this.rating = rating;
        this.brewmethod = brewmethod;
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public int getImageSource() {
        return imageSource;
    }

    public void setImageSource(int imageSource) {
        this.imageSource = imageSource;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
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
