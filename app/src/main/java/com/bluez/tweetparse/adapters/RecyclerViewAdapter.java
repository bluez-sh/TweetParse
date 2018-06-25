package com.bluez.tweetparse.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluez.tweetparse.R;
import com.bluez.tweetparse.models.Tweet;

import java.util.List;

/**
 * Created by bluez on 6/25/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Tweet> tweetList;

    public RecyclerViewAdapter(Context mContext, List<Tweet> tweetList) {
        this.mContext = mContext;
        this.tweetList = tweetList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.model_tweet, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String coinData = tweetList.get(position).getCoinName() + " | "
                + tweetList.get(position).getCoinSymbol() + " | "
                + tweetList.get(position).getCoinHandle();
        holder.coinData.setText(coinData);
        holder.tweet.setText(tweetList.get(position).getTweet());
        holder.url.setText(tweetList.get(position).getUrl());
        holder.date.setText(tweetList.get(position).getDate());
        holder.keyword.setText(tweetList.get(position).getKeyword());
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView coinData;
        TextView tweet;
        TextView url;
        TextView date;
        TextView keyword;

        public MyViewHolder(View itemView) {
            super(itemView);

            coinData = itemView.findViewById(R.id.coin_data);
            tweet = itemView.findViewById(R.id.tweet);
            url = itemView.findViewById(R.id.url);
            date = itemView.findViewById(R.id.date);
            keyword = itemView.findViewById(R.id.keyword);
        }
    }
}
