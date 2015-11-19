package com.codepath.apps.MySimpleTweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.MySimpleTweets.TwitterApplication;
import com.codepath.apps.MySimpleTweets.TwitterClient;
import com.codepath.apps.MySimpleTweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class MentionsTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    private long oldestTweetIdRetrieved = TwitterClient.NULL_TWEET_ID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient();

        populateTimeline();
    }

    protected void populateTimeline() {
        client.getMentionsTimeline(oldestTweetIdRetrieved, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                List<Tweet> tweets = Tweet.fromJSONArray(json);
                if (tweets.size() > 0) {
                    addAll(tweets);

                    Tweet oldestTweet = tweets.get(tweets.size() - 1);
                    oldestTweetIdRetrieved = oldestTweet.getUid();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getActivity(), "Error (" + errorResponse.toString() + ")", Toast.LENGTH_SHORT).show();
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }
}
