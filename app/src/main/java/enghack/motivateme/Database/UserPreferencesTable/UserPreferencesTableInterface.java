package enghack.motivateme.Database.UserPreferencesTable;

import android.content.Context;
import android.database.Cursor;

import enghack.motivateme.Util.Constants;
import enghack.motivateme.Database.MotivateMeDbHelper;
import enghack.motivateme.Database.MotivateMeDatabaseUtils;
import enghack.motivateme.Managers.SchedulingManager;
import enghack.motivateme.Util.StringUtils;

import static enghack.motivateme.Database.UserPreferencesTable.UserPreferencesDefaults.BACKGROUND_URI;
import static enghack.motivateme.Database.UserPreferencesTable.UserPreferencesDefaults.QUOTE_CATEGORY;
import static enghack.motivateme.Database.UserPreferencesTable.UserPreferencesDefaults.REFRESH_INTERVAL;
import static enghack.motivateme.Database.UserPreferencesTable.UserPreferencesDefaults.TEXT_COLOUR;
import static enghack.motivateme.Database.UserPreferencesTable.UserPreferencesDefaults.TEXT_FONT;
import static enghack.motivateme.Database.UserPreferencesTable.UserPreferencesDefaults.TEXT_SIZE;
import static enghack.motivateme.Database.UserPreferencesTable.UserPreferencesDefaults.TEXT_STYLE;

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

    public static void writeTextStyle(int style) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotivateMeDatabaseUtils.replaceInt(MotivateMeDbHelper.getInstance().getWritableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract.COLUMN_NAME_TEXT_STYLE, style);
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

    public static void writeTextSizeAndRefreshWallpaper(int textSize, Context context) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotivateMeDatabaseUtils.replaceInt(MotivateMeDbHelper.getInstance().getWritableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract.COLUMN_NAME_TEXT_SIZE, textSize);
            SchedulingManager.settingChanged(context);
        }
    }

    public static void writeQuoteCategoryAndRefreshWallpaper(int category, Context context) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotivateMeDatabaseUtils.replaceInt(MotivateMeDbHelper.getInstance().getWritableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract.COLUMN_NAME_QUOTE_CATEGORY, category);
            SchedulingManager.settingChanged(context);
        }
    }

    public static void writeTextFontAndRefreshWallpaper(String font, Context context) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotivateMeDatabaseUtils.replaceString(MotivateMeDbHelper.getInstance().getWritableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract.COLUMN_NAME_TEXT_FONT, font);
            SchedulingManager.settingChanged(context);
        }
    }

    public static void writeBackgroundUriAndRefreshWallpaper(String uri, Context context) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotivateMeDatabaseUtils.replaceString(MotivateMeDbHelper.getInstance().getWritableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract.COLUMN_NAME_BACKGROUND_URI, uri);
            SchedulingManager.settingChanged(context);
        }
    }

    public static void writeTextColourAndRefreshWallpaper(int colour, Context context) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotivateMeDatabaseUtils.replaceInt(MotivateMeDbHelper.getInstance().getWritableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract.COLUMN_NAME_TEXT_COLOUR, colour);
            SchedulingManager.settingChanged(context);
        }
    }

    public static void writeRefreshIntervalAndRefreshWallpaper(long interval, Context context) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotivateMeDatabaseUtils.replaceLong(MotivateMeDbHelper.getInstance().getWritableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract.COLUMN_NAME_REFRESH_INTERVAL, interval);
            SchedulingManager.settingChanged(context);
        }
    }

    public static int readTextSize() {
        if (MotivateMeDbHelper.getInstance() != null) {
            int readSize = MotivateMeDatabaseUtils.readFirstInt(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract._ID, UserPreferencesTableContract.COLUMN_NAME_TEXT_SIZE);
            return readSize == 0 ? TEXT_SIZE : readSize;
        } else {
            return TEXT_SIZE;
        }
    }

    public static int readTextStyle() {
        if (MotivateMeDbHelper.getInstance() != null) {
            int readStyle = MotivateMeDatabaseUtils.readFirstInt(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract._ID, UserPreferencesTableContract.COLUMN_NAME_TEXT_STYLE);
            return readStyle == 0 ? TEXT_STYLE : readStyle;
        } else {
            return TEXT_STYLE;
        }
    }

    public static String readQuoteCategory() {
        if (MotivateMeDbHelper.getInstance() != null) {
            int cat = MotivateMeDatabaseUtils.readFirstInt(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract._ID, UserPreferencesTableContract.COLUMN_NAME_QUOTE_CATEGORY);
            return Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(cat);
        } else {
            return QUOTE_CATEGORY;
        }
    }

    public static String readTextFont() {
        if (MotivateMeDbHelper.getInstance() != null) {
            String font = MotivateMeDatabaseUtils.readFirstString(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract._ID, UserPreferencesTableContract.COLUMN_NAME_TEXT_FONT);
            return font == null ? TEXT_FONT : font;
        } else {
            return TEXT_FONT;
        }
    }

    public static String readBackgroundUri() {
        if (MotivateMeDbHelper.getInstance() != null) {
            String uri = MotivateMeDatabaseUtils.readFirstString(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract._ID, UserPreferencesTableContract.COLUMN_NAME_BACKGROUND_URI);
            return StringUtils.isNullOrEmpty(uri) ? BACKGROUND_URI : uri;
        } else {
            return BACKGROUND_URI;
        }
    }

    public static int readTextColour() {
        if (MotivateMeDbHelper.getInstance() != null) {
            int readColour = MotivateMeDatabaseUtils.readFirstInt(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract._ID, UserPreferencesTableContract.COLUMN_NAME_TEXT_COLOUR);
            return readColour == 0 ? TEXT_COLOUR : readColour;
        } else {
            return TEXT_COLOUR;
        }
    }

    public static long readRefreshInterval() {
        if (MotivateMeDbHelper.getInstance() != null) {
            long refresh = MotivateMeDatabaseUtils.readFirstLong(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesTableContract.TABLE_NAME,
                    UserPreferencesTableContract._ID, UserPreferencesTableContract.COLUMN_NAME_REFRESH_INTERVAL);
            return refresh == 0 ? REFRESH_INTERVAL : refresh;
        } else {
            return REFRESH_INTERVAL;
        }
    }

    public static UserPreferencesModel readUserPreferences() {
        UserPreferencesModel defaultModel = new UserPreferencesModel(REFRESH_INTERVAL, TEXT_COLOUR, BACKGROUND_URI, TEXT_FONT, TEXT_SIZE, TEXT_STYLE, QUOTE_CATEGORY);

        if (MotivateMeDbHelper.getInstance() != null) {

            Cursor userPrefsCursor = MotivateMeDatabaseUtils.getMostRecentRow(MotivateMeDbHelper.getInstance().getReadableDatabase(), UserPreferencesTableContract.TABLE_NAME);
            if (userPrefsCursor == null || !userPrefsCursor.moveToFirst()) {
                return defaultModel;
            }

            long refreshInterval = getRefreshInterval(userPrefsCursor);
            int textColour = getTextColour(userPrefsCursor);
            String backgroundUri = getBackgroundUri(userPrefsCursor);
            String textFont = getTextFont(userPrefsCursor);
            String quoteCategory = getQuoteCategory(userPrefsCursor);
            int textSize = getTextSize(userPrefsCursor);
            int textStyle = getTextStyle(userPrefsCursor);

            userPrefsCursor.close();

            return new UserPreferencesModel(refreshInterval, textColour, backgroundUri, textFont, textSize, textStyle, quoteCategory);
        }
        return defaultModel;
    }

    private static int getTextStyle(Cursor row) {
        int textStyle = row.getInt(row.getColumnIndex(UserPreferencesTableContract.COLUMN_NAME_TEXT_STYLE));
        textStyle = textStyle == 0 ? TEXT_STYLE : textStyle;
        return textStyle;
    }

    private static int getTextSize(Cursor row) {
        int textSize = row.getInt(row.getColumnIndex(UserPreferencesTableContract.COLUMN_NAME_TEXT_SIZE));
        textSize = textSize == 0 ? TEXT_SIZE : textSize;
        return textSize;
    }

    private static String getQuoteCategory(Cursor row) {
        int quoteCategory = row.getInt(row.getColumnIndex(UserPreferencesTableContract.COLUMN_NAME_QUOTE_CATEGORY));
        return Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(quoteCategory);
    }

    private static String getTextFont(Cursor row) {
        String textFont = row.getString(row.getColumnIndex(UserPreferencesTableContract.COLUMN_NAME_TEXT_FONT));
        textFont = StringUtils.isNullOrEmpty(textFont) ? TEXT_FONT : textFont;
        return textFont;
    }

    private static String getBackgroundUri(Cursor row) {
        String backgroundUri = row.getString(row.getColumnIndex(UserPreferencesTableContract.COLUMN_NAME_BACKGROUND_URI));
        backgroundUri = StringUtils.isNullOrEmpty(backgroundUri) ? BACKGROUND_URI : backgroundUri;
        return backgroundUri;
    }

    private static int getTextColour(Cursor row) {
        int textColour = row.getInt(row.getColumnIndex(UserPreferencesTableContract.COLUMN_NAME_TEXT_COLOUR));
        textColour = textColour == 0 ? TEXT_COLOUR : textColour;
        return textColour;
    }

    private static long getRefreshInterval(Cursor row) {
        long refreshInterval = row.getLong(row.getColumnIndex(UserPreferencesTableContract.COLUMN_NAME_REFRESH_INTERVAL));
        refreshInterval = refreshInterval == 0 ? REFRESH_INTERVAL : refreshInterval;
        return refreshInterval;
    }
}
