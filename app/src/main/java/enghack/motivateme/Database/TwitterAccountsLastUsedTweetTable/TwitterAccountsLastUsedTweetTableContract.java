package enghack.motivateme.Database.TwitterAccountsLastUsedTweetTable;

import android.provider.BaseColumns;

import enghack.motivateme.Constants;

/**
 * Created by rowandempster on 2/24/17.
 */

public class TwitterAccountsLastUsedTweetTableContract implements BaseColumns {
    private TwitterAccountsLastUsedTweetTableContract() {
    }

    public static final String TABLE_NAME = "twitter_accounts_last_used_tweet";
    public static final String COLUMN_NAME_INSPIRE_US = Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(0);
    public static final String COLUMN_NAME_LOVE_QUOTES = Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(1);
    public static final String COLUMN_NAME_SPORTS_GREATS = Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(2);
    public static final String COLUMN_NAME_BOOKS_TEXTS = Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(3);
    public static final String COLUMN_NAME_UPLIFTING_QUOTES = Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(4);
    public static final String COLUMN_NAME_PHILOSOPHY_TWEETS = Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(5);
}
