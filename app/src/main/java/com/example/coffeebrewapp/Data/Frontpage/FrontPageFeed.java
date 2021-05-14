package com.example.coffeebrewapp.Data.Frontpage;

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

    private int ID;
    private int userID;
    private int userImage;
    private String username;
    private int followerIcon;
    private int shareIcon;
    private int mapsIcon;
    private int coffeeRating;
    private String coffeeName;
    private int feedRating;
    private int coffeeImage;
    private String brewIcon;

    /*
    public FrontPageFeed(int ID, int userID, int userImage, String username, int followerIcon, int shareIcon, int mapsIcon, int coffeeRating, String coffeeName, int feedRating, int coffeeImage, int brewIcon) {
        this.ID = ID;
        this.userID = userID;
        this.userImage = userImage;
        this.username = username;
        this.followerIcon = followerIcon;
        this.shareIcon = shareIcon;
        this.mapsIcon = mapsIcon;
        this.coffeeRating = coffeeRating;
        this.coffeeName = coffeeName;
        this.feedRating = feedRating;
        this.coffeeImage = coffeeImage;
        this.brewIcon = brewIcon;
    }
    */


    public FrontPageFeed(int ID, int userID, int userImage, String username, int coffeeRating, String coffeeName, int feedRating, int coffeeImage, String brewIcon) {
        this.ID = ID;
        this.userID = userID;
        this.userImage = userImage;
        this.username = username;
        this.coffeeRating = coffeeRating;
        this.coffeeName = coffeeName;
        this.feedRating = feedRating;
        this.coffeeImage = coffeeImage;
        this.brewIcon = brewIcon;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserImage() {
        return userImage;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFollowerIcon() {
        return followerIcon;
    }

    public void setFollowerIcon(int followerIcon) {
        this.followerIcon = followerIcon;
    }

    public int getShareIcon() {
        return shareIcon;
    }

    public void setShareIcon(int shareIcon) {
        this.shareIcon = shareIcon;
    }

    public int getMapsIcon() {
        return mapsIcon;
    }

    public void setMapsIcon(int mapsIcon) {
        this.mapsIcon = mapsIcon;
    }

    public int getCoffeeRating() {
        return coffeeRating;
    }

    public void setCoffeeRating(int coffeeRating) {
        this.coffeeRating = coffeeRating;
    }

    public int getFeedRating() {
        return feedRating;
    }

    public void setFeedRating(int feedRating) {
        this.feedRating = feedRating;
    }

    public int getCoffeeImage() {
        return coffeeImage;
    }

    public void setCoffeeImage(int coffeeImage) {
        this.coffeeImage = coffeeImage;
    }

    public String getBrewIcon() {
        return brewIcon;
    }

    public void setBrewIcon(String brewIcon) {
        this.brewIcon = brewIcon;
    }
}
