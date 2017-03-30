package enghack.motivateme.Database.UserPreferencesTable;

import android.graphics.Color;
import android.graphics.Typeface;

import enghack.motivateme.Util.Constants;

/**
 * Created by rowandempster on 3/5/17.
 */

public class UserPreferencesDefaults {
    public static final long REFRESH_INTERVAL = 1000 * 60 * 60 * 24;
    public static final int TEXT_SIZE = 60;
    public static final int TEXT_STYLE = Typeface.NORMAL;
    public static final String QUOTE_CATEGORY = Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(0);
    public static final String TEXT_FONT = "fonts/serif.ttf";
    public static final int TEXT_COLOUR = Color.BLACK;
    public static final String BACKGROUND_URI = null;

}