package com.bluez.tweetparse.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bluez.tweetparse.R;
import com.bluez.tweetparse.adapters.RecyclerViewAdapter;
import com.bluez.tweetparse.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String URL = "http://198.199.90.139/tweets";
    private JsonObjectRequest request;
    private RequestQueue requestQueue;
    private List<Tweet> tweetList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_id);
        tweetList = new ArrayList<>();
        jsonRequest();
    }

    private void jsonRequest() {

        request = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("tweets");
                    JSONObject jsonObject = null;

                    for(int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        Tweet tweet = new Tweet();

                        tweet.setCoinName(jsonObject.getString("coin_name"));
                        tweet.setCoinSymbol(jsonObject.getString("coin_symbol"));
                        tweet.setCoinHandle(jsonObject.getString("coin_handle"));
                        tweet.setTweet(jsonObject.getString("tweet"));
                        tweet.setUrl(jsonObject.getString("url"));
                        tweet.setDate(jsonObject.getString("date"));
                        tweet.setKeyword(jsonObject.getString("keyword"));

                        tweetList.add(tweet);
                    }

                    setupRecyclerView(tweetList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,
                        "Couldn't fetch data!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }

    private void setupRecyclerView(List<Tweet> tweetList) {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, tweetList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}
