package com.codepath.apps.MySimpleTweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.MySimpleTweets.R;
import com.codepath.apps.MySimpleTweets.TweetsArrayAdapter;
import com.codepath.apps.MySimpleTweets.TwitterApplication;
import com.codepath.apps.MySimpleTweets.TwitterClient;
import com.codepath.apps.MySimpleTweets.listeners.EndlessScrollListener;
import com.codepath.apps.MySimpleTweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private List<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;

    private EndlessScrollListener endlessScrollListener;

    public static final int RESULT_OK = 0;

    public static final int COMPOSE_TWEET = 10;
    public static final String NEW_TWEET_KEY = "new_tweet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(this, tweets);

        lvTweets = (ListView) findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);
        endlessScrollListener = new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                populateTimeline();
                Toast.makeText(TimelineActivity.this, "Scrolling past...", Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        lvTweets.setOnScrollListener(endlessScrollListener);

        client = TwitterApplication.getRestClient();

        populateTimeline();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.compose, menu);

        return true;
    }

    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());

                List<Tweet> tweets = Tweet.fromJSONArray(json);
                if (tweets.size() > 0) {
                    aTweets.addAll(tweets);

                    Tweet oldestTweet = tweets.get(tweets.size() - 1);
                    client.setOldestIdRetrieved(oldestTweet.getUid());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

    public void onComposeAction(MenuItem item) {
        Intent compose = new Intent(this, ComposeActivity.class);
        startActivityForResult(compose, COMPOSE_TWEET);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COMPOSE_TWEET && resultCode == RESULT_OK) {
            Tweet newTweet = (Tweet) data.getExtras().getSerializable(NEW_TWEET_KEY);
            aTweets.insert(newTweet, 0);
        }
    }
}
