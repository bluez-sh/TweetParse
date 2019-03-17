package com.bluez.tweetparse.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bluez.tweetparse.R;
import com.bluez.tweetparse.adapters.RecyclerViewAdapter;
import com.bluez.tweetparse.db.TweetDbHelper;
import com.bluez.tweetparse.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.bluez.tweetparse.db.TweetContract.TweetEntry;

public class MainActivity extends AppCompatActivity {
    private final String URL = "https://cryptohype.live/tweets";
    private JsonObjectRequest request;
    private RequestQueue requestQueue;
    private List<Tweet> tweetList;
    private RecyclerView recyclerView;
    private TweetDbHelper mDbHelper;
    //private ProgressBar bar;

    WebView webView;
    SwipeRefreshLayout swipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_id);
        tweetList = new ArrayList<>();
        mDbHelper = new TweetDbHelper(this);

        /*bar = findViewById(R.id.indeterminateBar);
        bar.setVisibility(View.VISIBLE);*/
        recyclerView.setVisibility(View.GONE);

        swipe = findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                LoadWeb();
                jsonRequest();
            }
        });

        LoadWeb();

        jsonRequest();
    }

    public void LoadWeb() {

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.loadUrl("https://google.com");
        swipe.setRefreshing(true);
        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String URL){

                //Hide the SwipeRefreshLayout

                swipe.setRefreshing(false);
            }
        });
    }

    private void jsonRequest() {

        swipe.setRefreshing(true);

        request = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("tweets");
                    JSONObject json;

                    SQLiteDatabase db = mDbHelper.getWritableDatabase();
                    db.beginTransaction();

                    for(int i = 0; i < jsonArray.length(); i++) {
                        json = jsonArray.getJSONObject(i);

                        ContentValues values = new ContentValues();
                        values.put(TweetEntry.COL_TWEETID, Long.parseLong(json.getString("tweetid")));
                        values.put(TweetEntry.COL_COIN_NAME, json.getString("coin_name"));
                        values.put(TweetEntry.COL_COIN_SYMBOL, json.getString("coin_symbol"));
                        values.put(TweetEntry.COL_COIN_HANDLE, json.getString("coin_handle"));
                        values.put(TweetEntry.COL_TWEET, json.getString("tweet"));
                        values.put(TweetEntry.COL_URL, json.getString("url"));
                        values.put(TweetEntry.COL_DATE, json.getString("date"));
                        values.put(TweetEntry.COL_KEYWORD, json.getString("keyword"));

                        db.insertWithOnConflict(TweetEntry.TABLE_NAME, null, values,
                                SQLiteDatabase.CONFLICT_IGNORE);
                    }

                    Log.d("db", "Wrote to database...");

                    db.setTransactionSuccessful();
                    db.endTransaction();
                    db.close();

                    getTweetListData();
                    setupRecyclerView(tweetList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,
                        "Couldn't fetch data! \nShowing Cached Data...",
                        Toast.LENGTH_SHORT).show();

                Log.d("db", "Reading cached data...");

                getTweetListData();
                setupRecyclerView(tweetList);
            }
        });

        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }

    private void setupRecyclerView(List<Tweet> tweetList) {
        //bar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, tweetList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void getTweetListData() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.beginTransaction();

        String orderBy = TweetEntry.COL_DATE + " DESC";

        Cursor cursor = db.query(TweetEntry.TABLE_NAME,
                null, null, null,
                null, null, orderBy);

        while(cursor != null && cursor.moveToNext()) {
            Tweet tweet = new Tweet();

            tweet.setCoinName(cursor.getString(cursor.getColumnIndexOrThrow(TweetEntry.COL_COIN_NAME)));
            tweet.setCoinSymbol(cursor.getString(cursor.getColumnIndexOrThrow(TweetEntry.COL_COIN_SYMBOL)));
            tweet.setCoinHandle(cursor.getString(cursor.getColumnIndexOrThrow(TweetEntry.COL_COIN_HANDLE)));
            tweet.setTweet(cursor.getString(cursor.getColumnIndexOrThrow(TweetEntry.COL_TWEET)));
            tweet.setUrl(cursor.getString(cursor.getColumnIndexOrThrow(TweetEntry.COL_URL)));
            tweet.setDate(cursor.getString(cursor.getColumnIndexOrThrow(TweetEntry.COL_DATE)));
            tweet.setKeyword(cursor.getString(cursor.getColumnIndexOrThrow(TweetEntry.COL_KEYWORD)));

            tweetList.add(tweet);
        }

        Log.d("db", "Read from database...");

        if (cursor != null) {
            cursor.close();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }
}
