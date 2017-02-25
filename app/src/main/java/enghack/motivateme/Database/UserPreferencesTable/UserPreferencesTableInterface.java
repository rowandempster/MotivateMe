package enghack.motivateme.Database.UserPreferencesTable;

import android.graphics.Color;
import android.util.Log;

import enghack.motivateme.Constants;
import enghack.motivateme.Database.MotivateMeDbHelper;
import enghack.motivateme.Database.MotivateMeDatabaseUtils;

/**
 * Created by rowandempster on 2/20/17.
 */

public class UserPreferencesTableInterface {

    public static void writeTextSize(int textSize) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotivateMeDatabaseUtils.replaceInt(MotivateMeDbHelper.getInstance().getWritableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract.COLUMN_NAME_TEXT_SIZE, textSize);
        }
    }

    public static void writeQuoteCategory(int category) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotivateMeDatabaseUtils.replaceInt(MotivateMeDbHelper.getInstance().getWritableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract.COLUMN_NAME_QUOTE_CATEGORY, category);
        }
    }

    public static void writeTextFont(String font) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotivateMeDatabaseUtils.replaceString(MotivateMeDbHelper.getInstance().getWritableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract.COLUMN_NAME_TEXT_FONT, font);
        }
    }

    public static void writeBackgroundUri(String uri) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotivateMeDatabaseUtils.replaceString(MotivateMeDbHelper.getInstance().getWritableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract.COLUMN_NAME_BACKGROUND_URI, uri);
        }
    }

    public static void writeTextColour(int colour) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotivateMeDatabaseUtils.replaceInt(MotivateMeDbHelper.getInstance().getWritableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract.COLUMN_NAME_TEXT_COLOUR, colour);
        }
    }

    public static void writeRefreshInterval(long interval) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotivateMeDatabaseUtils.replaceLong(MotivateMeDbHelper.getInstance().getWritableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract.COLUMN_NAME_REFRESH_INTERVAL, interval);
        }
    }

    public static int readTextSize() {
        if (MotivateMeDbHelper.getInstance() != null) {
            int readSize = MotivateMeDatabaseUtils.readFirstInt(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract._ID, UserPreferencesTableContract.COLUMN_NAME_TEXT_SIZE);
            return readSize == 0 ? 60 : readSize;
        } else {
            return 60;
        }
    }

    public static String readQuoteCategory() {
        if (MotivateMeDbHelper.getInstance() != null) {
            int cat = MotivateMeDatabaseUtils.readFirstInt(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract._ID, UserPreferencesTableContract.COLUMN_NAME_QUOTE_CATEGORY);
            return Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(cat);
        } else {
            return Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(0);
        }
    }

    public static String readTextFont() {
        if (MotivateMeDbHelper.getInstance() != null) {
            String font = MotivateMeDatabaseUtils.readFirstString(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract._ID, UserPreferencesTableContract.COLUMN_NAME_TEXT_FONT);
            return font == null ? "fonts/serif.ttf" : font;
        } else {
            return "fonts/serif.ttf";
        }
    }

    public static String readBackgroundUri() {
        if (MotivateMeDbHelper.getInstance() != null) {
            String uri = MotivateMeDatabaseUtils.readFirstString(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract._ID, UserPreferencesTableContract.COLUMN_NAME_BACKGROUND_URI);
            return uri;
        } else {
            return null;
        }
    }

    public static int readTextColour() {
        if (MotivateMeDbHelper.getInstance() != null) {
            int readColour = MotivateMeDatabaseUtils.readFirstInt(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract._ID, UserPreferencesTableContract.COLUMN_NAME_TEXT_COLOUR);
            return readColour == 0 ? Color.BLACK : readColour;
        } else {
            return 60;
        }
    }

    public static long readRefreshInterval() {
        if (MotivateMeDbHelper.getInstance() != null) {
            long refresh = MotivateMeDatabaseUtils.readFirstLong(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract._ID, UserPreferencesTableContract.COLUMN_NAME_REFRESH_INTERVAL);
            return refresh == 0 ? 1000 * 60 * 60 * 24 : refresh;
        } else {
            return 1000 * 60 * 60 * 24;
        }
    }
}
