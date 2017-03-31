package enghack.motivateme.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rowandempster on 2/5/17.
 */

public class Constants {
    //********SHARED PREFS KEYS
    public static final String MASTER_SP_KEY = "MotivateMeSP";

    public static final String QUOTE_CATEGORY_SP_KEY = "quote_category";
    public static final String TEXT_FONT_SP_KEY = "text_font";
    public static final String TEXT_SIZE_SP_KEY = "text_size";
    public static final String BACKGROUND_URI_SP_KEY = "background_uri";
    public static final String TEXT_COLOR_SP_KEY = "text_color";
    public static final String REFRESH_INTERVAL_SP_KEY = "refresh_interval";

    //*********SP VALUE (int) TO TWITTER ACCOUNT
    public static final Map<Integer, String> QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP = new HashMap<Integer, String>()
    {{
        put(0, "Inspire_Us");
        put(1, "LoveQuotes");
        put(2, "Sports_Greats");
        put(3, "Bookstexts");
        put(4, "UpliftingQuotes");
        put(5, "philosophytweet");
    }};

    //********ARITHMETIC
    public static final int WIDTH_BUFFER = 60;
    public static final int NEWLINE_BUFFER = 30;
    public static final int TWEET_LENGTH = 140;

    //Pulling tweets
    public static final int TWEETS_TO_PULL_NORMAL_AMOUNT = 100;


}
