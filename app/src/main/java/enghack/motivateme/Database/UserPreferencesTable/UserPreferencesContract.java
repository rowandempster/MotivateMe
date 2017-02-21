package enghack.motivateme.Database.UserPreferencesTable;

import android.provider.BaseColumns;

/**
 * Created by rowandempster on 2/20/17.
 */

public final class UserPreferencesContract {
    private UserPreferencesContract() {
    }

    public static class UserPreferences implements BaseColumns {
        public static final String TABLE_NAME = "user_preference";
        public static final String COLUMN_NAME_QUOTE_CATEGORY = "quote_category";
        public static final String COLUMN_NAME_TEXT_FONT = "text_font";
        public static final String COLUMN_NAME_TEXT_SIZE = "text_size";
        public static final String COLUMN_NAME_BACKGROUND_URI = "background_uri";
        public static final String COLUMN_NAME_TEXT_COLOUR = "text_colour";
        public static final String COLUMN_NAME_REFRESH_INTERVAL = "refresh_interval";
    }
}
