package enghack.motivateme.Database.UserPreferencesTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;

import enghack.motivateme.Database.MotiveMeDatabaseUtils;

/**
 * Created by rowandempster on 2/20/17.
 */

public class UserPreferencesManager {
    private static UserPreferencesDbHelper _helper;
    private static int _openCount = 0;

    public static void init(Context context) {
        if(_helper == null) {
            _helper = new UserPreferencesDbHelper(context);
            _helper.getReadableDatabase();
        }
        _openCount++;
    }

    public static void destroy() {
        _openCount--;
        if(_helper!=null && _openCount==0) {
            _helper.close();
            _helper = null;
        }
    }

    public static void writeTextSize(int textSize) {
        if(_helper != null) {
            Log.d("asdf", "UserPreferencesManager writing textsize " + textSize);

            MotiveMeDatabaseUtils.replaceInt(_helper.getWritableDatabase(), UserPreferencesContract.UserPreferences.TABLE_NAME,
                    UserPreferencesContract.UserPreferences.COLUMN_NAME_TEXT_SIZE, textSize);
        }
    }

    public static void writeQuoteCategory(int category) {
        if(_helper != null) {
            MotiveMeDatabaseUtils.replaceInt(_helper.getWritableDatabase(), UserPreferencesContract.UserPreferences.TABLE_NAME,
                    UserPreferencesContract.UserPreferences.COLUMN_NAME_QUOTE_CATEGORY, category);
        }
    }

    public static void writeTextFont(String font) {
        if(_helper != null) {
            MotiveMeDatabaseUtils.replaceString(_helper.getWritableDatabase(), UserPreferencesContract.UserPreferences.TABLE_NAME,
                    UserPreferencesContract.UserPreferences.COLUMN_NAME_TEXT_FONT, font);
        }
    }

    public static void writeBackgroundUri(String uri) {
        if(_helper != null) {
            MotiveMeDatabaseUtils.replaceString(_helper.getWritableDatabase(), UserPreferencesContract.UserPreferences.TABLE_NAME,
                    UserPreferencesContract.UserPreferences.COLUMN_NAME_BACKGROUND_URI, uri);
        }
    }

    public static void writeTextColour(int colour) {
        if(_helper != null) {
            MotiveMeDatabaseUtils.replaceInt(_helper.getWritableDatabase(), UserPreferencesContract.UserPreferences.TABLE_NAME,
                    UserPreferencesContract.UserPreferences.COLUMN_NAME_TEXT_COLOUR, colour);
        }
    }

    public static void writeRefreshInterval(long interval) {
        if (_helper != null) {
            MotiveMeDatabaseUtils.replaceLong(_helper.getWritableDatabase(), UserPreferencesContract.UserPreferences.TABLE_NAME,
                    UserPreferencesContract.UserPreferences.COLUMN_NAME_REFRESH_INTERVAL, interval);
        }
    }

    public static int readTextSize() {
        if(_helper != null){
            int readSize = MotiveMeDatabaseUtils.readFirstInt(_helper.getReadableDatabase(), UserPreferencesContract.UserPreferences.TABLE_NAME,
                    UserPreferencesContract.UserPreferences._ID, UserPreferencesContract.UserPreferences.COLUMN_NAME_TEXT_SIZE);
            return readSize == -1 ? 60 : readSize;
        }
        else{
            return 60;
        }
    }

    public static int readQuoteCategory() {
        if(_helper != null){
            int cat = MotiveMeDatabaseUtils.readFirstInt(_helper.getReadableDatabase(), UserPreferencesContract.UserPreferences.TABLE_NAME,
                    UserPreferencesContract.UserPreferences._ID, UserPreferencesContract.UserPreferences.COLUMN_NAME_QUOTE_CATEGORY);
            return cat == -1 ? 0 : cat;
        }
        else{
            return 0;
        }
    }

    public static String readTextFont() {
        if(_helper != null){
            String font = MotiveMeDatabaseUtils.readFirstString(_helper.getReadableDatabase(), UserPreferencesContract.UserPreferences.TABLE_NAME,
                    UserPreferencesContract.UserPreferences._ID, UserPreferencesContract.UserPreferences.COLUMN_NAME_TEXT_FONT);
            return font == null ? "fonts/serif.ttf" : font;
        }
        else{
            return "fonts/serif.ttf";
        }
    }

    public static String readBackgroundUri() {
        if(_helper != null){
            String uri = MotiveMeDatabaseUtils.readFirstString(_helper.getReadableDatabase(), UserPreferencesContract.UserPreferences.TABLE_NAME,
                    UserPreferencesContract.UserPreferences._ID, UserPreferencesContract.UserPreferences.COLUMN_NAME_BACKGROUND_URI);
            return uri;
        }
        else{
            return null;
        }
    }

    public static int readTextColour() {
        if(_helper != null){
            int readColour = MotiveMeDatabaseUtils.readFirstInt(_helper.getReadableDatabase(), UserPreferencesContract.UserPreferences.TABLE_NAME,
                    UserPreferencesContract.UserPreferences._ID, UserPreferencesContract.UserPreferences.COLUMN_NAME_TEXT_COLOUR);
            return readColour == -1 ? Color.BLACK : readColour;
        }
        else{
            return 60;
        }
    }

    public static long readRefreshInterval() {
        if(_helper != null){
            int refresh = MotiveMeDatabaseUtils.readFirstInt(_helper.getReadableDatabase(), UserPreferencesContract.UserPreferences.TABLE_NAME,
                    UserPreferencesContract.UserPreferences._ID, UserPreferencesContract.UserPreferences.COLUMN_NAME_REFRESH_INTERVAL);
            return refresh == -1 ? 1000*60*60*24 : refresh;
        }
        else{
            return 1000*60*60*24;
        }
    }
}
