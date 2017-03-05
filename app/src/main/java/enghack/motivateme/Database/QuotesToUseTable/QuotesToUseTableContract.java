package enghack.motivateme.Database.QuotesToUseTable;

import android.provider.BaseColumns;

/**
 * Created by rowandempster on 2/22/17.
 */

public class QuotesToUseTableContract implements BaseColumns {
    private QuotesToUseTableContract() {
    }

    public static final String TABLE_NAME = "quotes_to_use";
    public static final String COLUMN_NAME_QUOTE_TEXT = "quote_text";
    public static final String COLUMN_NAME_TWEET_ID = "tweet_id";
}
