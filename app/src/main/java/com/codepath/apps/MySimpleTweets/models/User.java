package com.codepath.apps.MySimpleTweets.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private long uid;
    private String screenName;
    private String description;
    private String profileImageUrl;
    private int followers;
    private int following;
    private int tweetsCount;

    public static User fromJSON(JSONObject json) {
        User u = new User();

        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.description = json.getString("description");
            u.profileImageUrl = json.getString("profile_image_url");

            try {
                u.followers = json.getInt("followers_count");
            } catch (Exception e) {
                u.followers = 0;
            }

            try {
                u.following = json.getInt("friends_count");
            } catch (Exception e) {
                u.following = 0;
            }

            try {
                u.tweetsCount = json.getInt("statuses_count");
            } catch (Exception e) {
                u.tweetsCount = 0;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getDescription() {
        return description;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public int getTweetsCount() {
        return tweetsCount;
    }
}
