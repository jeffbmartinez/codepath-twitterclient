package com.codepath.apps.MySimpleTweets;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.MySimpleTweets.activities.ProfileActivity;
import com.codepath.apps.MySimpleTweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, R.layout.item_tweet, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);

        tvUsername.setText(tweet.getUser().getScreenName());
        tvTimestamp.setText(tweet.getRelativeTimestamp());
        tvBody.setText(tweet.getBody());

        ivProfileImage.setImageResource(android.R.color.transparent);
        ivProfileImage.setTag(tweet.getUser().getScreenName());
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(getContext(), ProfileActivity.class);
                String screenName = (String) v.getTag();
                profile.putExtra("screenName", screenName);
                getContext().startActivity(profile);
            }
        });
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        return convertView;
    }
}
