package com.codepath.apps.MySimpleTweets.models;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Tweet implements Serializable {
    private String body;
    private long uid;
    private String createdAt;
    private User user;

    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();

        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    // Using modified code from https://gist.github.com/nesquena/f786232f5ef72f6e10a7
    public String getRelativeTimestamp() {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);
        String relativeDate = "";
        try {
            long dateMillis = sf.parse(this.getCreatedAt()).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(
                    dateMillis,
                    System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public User getUser() {
        return user;
    }

    public static List<Tweet> fromJSONArray(JSONArray json) {
        List<Tweet> tweets = new ArrayList<>();

        for (int i = 0 ; i < json.length() ; i++) {
            try {
                Tweet tweet = Tweet.fromJSON(json.getJSONObject(i));
                if (tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return tweets;
    }
}
