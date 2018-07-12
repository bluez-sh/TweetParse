package com.bluez.tweetparse.db;

/**
 * Created by bluez on 7/10/18.
 */

public final class TweetContract {
    static final String DB_NAME = "com.bluez.tweetparse.db";
    static final int DB_VERSION = 1;

    private TweetContract() {}

    public static class TweetEntry {
        public static final String TABLE_NAME = "tweets";
        public static final String COL_TWEETID = "tweetid";
        public static final String COL_COIN_NAME = "coin_name";
        public static final String COL_COIN_SYMBOL = "coin_symbol";
        public static final String COL_COIN_HANDLE = "coin_handle";
        public static final String COL_TWEET = "tweet";
        public static final String COL_URL = "url";
        public static final String COL_DATE = "date";
        public static final String COL_KEYWORD = "keyword";
    }
}
