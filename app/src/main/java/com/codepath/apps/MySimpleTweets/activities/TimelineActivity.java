package com.codepath.apps.MySimpleTweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.MySimpleTweets.R;
import com.codepath.apps.MySimpleTweets.fragments.HomeTimelineFragment;
import com.codepath.apps.MySimpleTweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.MySimpleTweets.fragments.TweetsListFragment;
import com.codepath.apps.MySimpleTweets.models.Tweet;

public class TimelineActivity extends AppCompatActivity {

    private TweetsPagerAdapter tweetsPagerAdapter;

    public static final int RESULT_OK = 0;

    public static final int COMPOSE_TWEET = 10;
    public static final String NEW_TWEET_KEY = "new_tweet";

    static public int HOME_TIMELINE_FRAGMENT_ID = 0;
    static public int MENTIONS_TIMELINE_FRAGMENT_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        tweetsPagerAdapter = new TweetsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(tweetsPagerAdapter);

        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(vpPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.compose, menu);

        return true;
    }

    public void onComposeAction(MenuItem item) {
        Intent compose = new Intent(this, ComposeActivity.class);
        startActivityForResult(compose, COMPOSE_TWEET);
    }

    public void onProfileAction(MenuItem item) {
        Intent profile = new Intent(this, ProfileActivity.class);
        startActivity(profile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COMPOSE_TWEET && resultCode == RESULT_OK) {
            TweetsListFragment tweetsListFragment = (TweetsListFragment) tweetsPagerAdapter.getItem(HOME_TIMELINE_FRAGMENT_ID);

            if (data != null) {
                Tweet newTweet = (Tweet) data.getExtras().getSerializable(NEW_TWEET_KEY);
                tweetsListFragment.insert(newTweet, 0);
            }
        }
    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter {

        private Fragment cache[] = { null, null };

        private String tabTitles[] = {
                "Home",
                "Mentions"
        };

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position < 0 || position > cache.length) {
                Log.d("FATAL", "Something is wrong, trying to grab an unknown fragment");
                return null;
            }

            if (cache[position] == null) {
                if (position == HOME_TIMELINE_FRAGMENT_ID) {
                    cache[position] = new HomeTimelineFragment();
                } else if (position == MENTIONS_TIMELINE_FRAGMENT_ID) {
                    cache[position] = new MentionsTimelineFragment();
                }
            }

            return cache[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
