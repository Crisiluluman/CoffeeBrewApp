package com.example.coffeebrewapp.Data;

public class FollowedUser {

    // Contains data on the users followed

    /*
    User image
    Username
    Coffee icon <-- The followed users overall prefered brew method
    Unfollow icon <-- Removed the followed person from the list
    Share icon <-- Share the profile with others

     */

    private String followedUserUsername;
    // private Image followedUserProfilePicture
    private String brewIcon; // <-- String with brew information, use this in a boolean or switch statement to determine the icon. Should be saved locally
    private String shareIcon; // Share icon is loaded in the Activity from local storage?
    // private String unfollowIcon;

}
