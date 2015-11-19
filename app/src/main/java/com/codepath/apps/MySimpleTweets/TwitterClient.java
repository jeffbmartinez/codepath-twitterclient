package com.codepath.apps.MySimpleTweets;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1/";
	public static final String REST_CONSUMER_KEY = "F8wrbKn4LWIYTcHQqFxX3IpdV";
	public static final String REST_CONSUMER_SECRET = "tA5A8KjXKGQkVmW3n3Akor75QZz4MNNISVS15Al1rCenLF1jlE";
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets";

    public static final int NUM_TWEETS_TO_RETRIEVE = 25;

    public static final long OLDEST_TWEET_ID = 1L;
    public static final long NULL_TWEET_ID = -1L;

    private long oldestIdRetrieved = NULL_TWEET_ID;

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void getHomeTimeline(long oldestTweetIdRetrieved, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");

		RequestParams params = new RequestParams();
		params.put("count", NUM_TWEETS_TO_RETRIEVE);
        params.put("since_id", OLDEST_TWEET_ID);

        if (oldestTweetIdRetrieved != NULL_TWEET_ID) {
            params.put("max_id", oldestTweetIdRetrieved - 1);
        }

        getClient().get(apiUrl, params, handler);
	}

    public void getMentionsTimeline(long oldestTweetIdRetrieved, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");

        RequestParams params = new RequestParams();
        params.put("count", NUM_TWEETS_TO_RETRIEVE);

        if (oldestTweetIdRetrieved != NULL_TWEET_ID) {
            params.put("max_id", oldestTweetIdRetrieved - 1);
        }

        getClient().get(apiUrl, params, handler);
        Log.d("DEBUG", apiUrl);
        Log.d("DEBUG", params.toString());
        Log.d("DEBUG", "\n");
    }

    public void getTweet(int tweetID, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/show.json");

        RequestParams params = new RequestParams();
        params.put("id", tweetID);

        getClient().get(apiUrl, params, handler);
    }

	public void postStatus(String status, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");

		RequestParams params = new RequestParams();
        params.put("status", status);

        getClient().post(apiUrl, params, handler);
	}
}