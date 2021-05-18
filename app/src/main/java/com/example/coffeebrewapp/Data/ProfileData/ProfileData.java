package com.example.coffeebrewapp.Data.ProfileData;

public class ProfileData {

    private String username;
    private String imageURL;

    public ProfileData(String imageURL, String username) {
        this.username = username;
        this.imageURL = imageURL;
    }

    public ProfileData() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
