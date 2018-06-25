package com.bluez.tweetparse.models;

/**
 * Created by bluez on 6/25/18.
 */

public class Tweet {
    private String coinName;
    private String coinSymbol;
    private String coinHandle;
    private String tweet;
    private String url;
    private String date;
    private String keyword;

    public Tweet() {}

    public Tweet(String coinName, String coinSymbol, String coinHandle, String tweet, String url, String date, String keyword) {
        this.coinName = coinName;
        this.coinSymbol = coinSymbol;
        this.coinHandle = coinHandle;
        this.tweet = tweet;
        this.url = url;
        this.date = date;
        this.keyword = keyword;
    }

    public String getCoinName() {
        return coinName;
    }

    public String getCoinSymbol() {
        return coinSymbol;
    }

    public String getCoinHandle() {
        return coinHandle;
    }

    public String getTweet() {
        return tweet;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public void setCoinSymbol(String coinSymbol) {
        this.coinSymbol = coinSymbol;
    }

    public void setCoinHandle(String coinHandle) {
        this.coinHandle = coinHandle;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
