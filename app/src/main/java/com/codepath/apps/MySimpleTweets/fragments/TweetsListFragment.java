package com.codepath.apps.MySimpleTweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.MySimpleTweets.R;
import com.codepath.apps.MySimpleTweets.TweetsArrayAdapter;
import com.codepath.apps.MySimpleTweets.listeners.EndlessScrollListener;
import com.codepath.apps.MySimpleTweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public abstract class TweetsListFragment extends Fragment {

    private List<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private EndlessScrollListener endlessScrollListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);

        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);
        endlessScrollListener = new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                populateTimeline();
                Toast.makeText(getActivity(), "Getting more tweets...", Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        lvTweets.setOnScrollListener(endlessScrollListener);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
    }

    public void addAll(List<Tweet> tweets) {
        aTweets.addAll(tweets);
    }

    public void insert(Tweet tweet, int index) {
        aTweets.insert(tweet, index);
    }

    abstract protected void populateTimeline();
}
