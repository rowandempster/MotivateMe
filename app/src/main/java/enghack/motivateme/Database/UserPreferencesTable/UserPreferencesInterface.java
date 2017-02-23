package enghack.motivateme.Database.UserPreferencesTable;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import enghack.motivateme.Database.MotivateMeDbHelper;
import enghack.motivateme.Database.MotiveMeDatabaseUtils;

/**
 * Created by rowandempster on 2/20/17.
 */

public class UserPreferencesInterface {

    public static void writeTextSize(int textSize) {
        if (MotivateMeDbHelper.getInstance() != null) {
            Log.d("asdf", "UserPreferencesInterface writing textsize " + textSize);

            MotiveMeDatabaseUtils.replaceInt(MotivateMeDbHelper.getInstance().getWritableDatabase(), UserPreferencesContract.TABLE_NAME,
                    UserPreferencesContract.COLUMN_NAME_TEXT_SIZE, textSize);
        }
    }

    public static void writeQuoteCategory(int category) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotiveMeDatabaseUtils.replaceInt(MotivateMeDbHelper.getInstance().getWritableDatabase(), UserPreferencesContract.TABLE_NAME,
                    UserPreferencesContract.COLUMN_NAME_QUOTE_CATEGORY, category);
        }
    }

    public static void writeTextFont(String font) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotiveMeDatabaseUtils.replaceString(MotivateMeDbHelper.getInstance().getWritableDatabase(), UserPreferencesContract.TABLE_NAME,
                    UserPreferencesContract.COLUMN_NAME_TEXT_FONT, font);
        }
    }

    public static void writeBackgroundUri(String uri) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotiveMeDatabaseUtils.replaceString(MotivateMeDbHelper.getInstance().getWritableDatabase(), UserPreferencesContract.TABLE_NAME,
                    UserPreferencesContract.COLUMN_NAME_BACKGROUND_URI, uri);
        }
    }

    public static void writeTextColour(int colour) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotiveMeDatabaseUtils.replaceInt(MotivateMeDbHelper.getInstance().getWritableDatabase(), UserPreferencesContract.TABLE_NAME,
                    UserPreferencesContract.COLUMN_NAME_TEXT_COLOUR, colour);
        }
    }

    public static void writeRefreshInterval(long interval) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotiveMeDatabaseUtils.replaceLong(MotivateMeDbHelper.getInstance().getWritableDatabase(), UserPreferencesContract.TABLE_NAME,
                    UserPreferencesContract.COLUMN_NAME_REFRESH_INTERVAL, interval);
        }
    }

    public static int readTextSize() {
        if (MotivateMeDbHelper.getInstance() != null) {
            int readSize = MotiveMeDatabaseUtils.readFirstInt(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesContract.TABLE_NAME,
                    UserPreferencesContract._ID, UserPreferencesContract.COLUMN_NAME_TEXT_SIZE);
            return readSize == 0 ? 60 : readSize;
        } else {
            return 60;
        }
    }

    public static int readQuoteCategory() {
        if (MotivateMeDbHelper.getInstance() != null) {
            int cat = MotiveMeDatabaseUtils.readFirstInt(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesContract.TABLE_NAME,
                    UserPreferencesContract._ID, UserPreferencesContract.COLUMN_NAME_QUOTE_CATEGORY);
            return cat == 0 ? 0 : cat;
        } else {
            return 0;
        }
    }

    public static String readTextFont() {
        if (MotivateMeDbHelper.getInstance() != null) {
            String font = MotiveMeDatabaseUtils.readFirstString(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesContract.TABLE_NAME,
                    UserPreferencesContract._ID, UserPreferencesContract.COLUMN_NAME_TEXT_FONT);
            return font == null ? "fonts/serif.ttf" : font;
        } else {
            return "fonts/serif.ttf";
        }
    }

    public static String readBackgroundUri() {
        if (MotivateMeDbHelper.getInstance() != null) {
            String uri = MotiveMeDatabaseUtils.readFirstString(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesContract.TABLE_NAME,
                    UserPreferencesContract._ID, UserPreferencesContract.COLUMN_NAME_BACKGROUND_URI);
            return uri;
        } else {
            return null;
        }
    }

    public static int readTextColour() {
        if (MotivateMeDbHelper.getInstance() != null) {
            int readColour = MotiveMeDatabaseUtils.readFirstInt(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesContract.TABLE_NAME,
                    UserPreferencesContract._ID, UserPreferencesContract.COLUMN_NAME_TEXT_COLOUR);
            return readColour == 0 ? Color.BLACK : readColour;
        } else {
            return 60;
        }
    }

    public static long readRefreshInterval() {
        if (MotivateMeDbHelper.getInstance() != null) {
            long refresh = MotiveMeDatabaseUtils.readFirstLong(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesContract.TABLE_NAME,
                    UserPreferencesContract._ID, UserPreferencesContract.COLUMN_NAME_REFRESH_INTERVAL);
            return refresh == 0 ? 1000 * 60 * 60 * 24 : refresh;
        } else {
            return 1000 * 60 * 60 * 24;
        }
    }
}
