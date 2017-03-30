package enghack.motivateme.Database.UsedTweetsTable;

import android.provider.BaseColumns;

/**
 * Created by rowandempster on 2/21/17.
 */

public class UsedTweetsTableContract implements BaseColumns {

    private UsedTweetsTableContract() {
    }

    public static final String TABLE_NAME = "used_tweets";
    public static final String COLUMN_NAME_QUOTE_TEXT= "quote_text";
    public static final String COLUMN_NAME_TWEET_ID = "tweet_id";
}
