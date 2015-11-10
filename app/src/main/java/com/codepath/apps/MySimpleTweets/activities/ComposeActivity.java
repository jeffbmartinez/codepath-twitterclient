package com.codepath.apps.MySimpleTweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.MySimpleTweets.R;
import com.codepath.apps.MySimpleTweets.TwitterApplication;
import com.codepath.apps.MySimpleTweets.TwitterClient;
import com.codepath.apps.MySimpleTweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class ComposeActivity extends AppCompatActivity {

    public static final int MAX_TWEET_CHARS = 140;

    private TextView tvNumCharacters;
    private EditText etTweetBody;

    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        tvNumCharacters = (TextView) findViewById(R.id.tvNumCharacters);

        etTweetBody = (EditText) findViewById(R.id.etTweetBody);
        showCharactersRemaining(etTweetBody);
        etTweetBody.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                showCharactersRemaining(etTweetBody);

                return false;
            }
        });

        client = TwitterApplication.getRestClient();
    }

    public void onSubmitClick(View view) {
        if (getCharactersRemaining(etTweetBody) >= 0) {
            String newStatus = etTweetBody.getText().toString();
            client.postStatus(newStatus, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                    Tweet tweet = Tweet.fromJSON(json);

                    Toast.makeText(ComposeActivity.this, "Posted new status :) (" + tweet.getUid() + ")", Toast.LENGTH_SHORT).show();

                    Intent data = new Intent();
                    data.putExtra(TimelineActivity.NEW_TWEET_KEY, tweet);
                    setResult(TimelineActivity.RESULT_OK, data);
                    finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Toast.makeText(ComposeActivity.this, "Problem posting new status :(", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Message is too long :(", Toast.LENGTH_SHORT).show();
        }
    }

    private void showCharactersRemaining(EditText tweetInput) {
        int charactersRemaining = getCharactersRemaining(tweetInput);

        tvNumCharacters.setText("" + charactersRemaining + " of " + MAX_TWEET_CHARS + " characters left");
        if (charactersRemaining >= 0) {
            tvNumCharacters.setTextColor(getResources().getColor(R.color.under_limit_color));
        } else {
            tvNumCharacters.setTextColor(getResources().getColor(R.color.over_limit_color));
        }
    }

    private int getCharactersRemaining(EditText tweetInput) {
        int tweetLength = tweetInput.getText().toString().length();
        int charactersRemaining = MAX_TWEET_CHARS - tweetLength;
        return charactersRemaining;
    }
}
