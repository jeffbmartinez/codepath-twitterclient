package com.codepath.apps.MySimpleTweets.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.MySimpleTweets.R;
import com.codepath.apps.MySimpleTweets.TwitterApplication;
import com.codepath.apps.MySimpleTweets.TwitterClient;
import com.codepath.apps.MySimpleTweets.fragments.UserTimelineFragment;
import com.codepath.apps.MySimpleTweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    TwitterClient client;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        client = TwitterApplication.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);

                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle("@" + user.getScreenName());
                }

                populateProfileHeader(user);
            }
        });

        String screenName = getIntent().getStringExtra("screenName");

        UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);

        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit();
        }
    }

    private void populateProfileHeader(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(user.getName());

        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        tvTagline.setText(user.getDescription());

        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        tvFollowers.setText("" + user.getFollowers() + " followers");

        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        tvFollowing.setText("" + user.getFollowing() + " following");

        TextView tvTweetsCount = (TextView) findViewById(R.id.tvTweetsCount);
        tvTweetsCount.setText("" + user.getTweetsCount() + " tweets");

        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);
    }
}
