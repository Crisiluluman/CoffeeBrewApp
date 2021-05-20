package com.example.coffeebrewapp.Data.Frontpage;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;

public class FrontPageFeed {

    // The individual feed on the frontpage
    // Displays the most popular coffee around

    /*
     Profile Image
     Username
     Follow Icon <-- Follows the user
     Share Icon <-- Share the review to others? (In app open?)
     Location Icon <-- Map location
     Coffee name
     Coffee rating
     Thumbs up/ down <-- Should determine how visible the feed is on the frontpage
     Coffee Icon <-- Which brew method did the user use
    */

    private String URL;
    private String username;
    private CoffeeProduct coffeeProduct;
    private int feedRating;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public FrontPageFeed() {
    }

    public FrontPageFeed(CoffeeProduct coffeeProduct, String URL) {
        this.URL = URL;
        this.coffeeProduct = coffeeProduct;

    }

    public int getFeedRating() {
        return feedRating;
    }

    public void setFeedRating(int feedRating) {
        this.feedRating = feedRating;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public CoffeeProduct getCoffeeProduct() {
        return coffeeProduct;
    }

    public void setCoffeeProduct(CoffeeProduct coffeeProduct) {
        this.coffeeProduct = coffeeProduct;
    }
}

