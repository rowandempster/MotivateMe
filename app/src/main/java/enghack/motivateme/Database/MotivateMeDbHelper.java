package enghack.motivateme.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import enghack.motivateme.Database.QuotesToUseTable.QuotesToUseTableContract;
import enghack.motivateme.Database.TwitterAccountsLastUsedTweetTable.TwitterAccountsLastUsedTweetTableContract;
import enghack.motivateme.Database.UsedTweetsTable.UsedTweetsTableContract;
import enghack.motivateme.Database.UserPreferencesTable.UserPreferencesTableContract;

/**
 * Created by rowandempster on 2/20/17.
 */

public class MotivateMeDbHelper extends SQLiteOpenHelper {
    private static MotivateMeDbHelper _instance;
    private static int _openCount;

    public static final int DATABASE_VERSION = 16;
    public static final String DATABASE_NAME = "MotivateMe.db";

    public static final String SQL_CREATE_USER_PREFS_TABLE =
            "CREATE TABLE " + UserPreferencesTableContract.TABLE_NAME + " (" +
                    UserPreferencesTableContract._ID + " INTEGER PRIMARY KEY," +
                    UserPreferencesTableContract.COLUMN_NAME_BACKGROUND_URI + " TEXT," +
                    UserPreferencesTableContract.COLUMN_NAME_REFRESH_INTERVAL + " TEXT," +
                    UserPreferencesTableContract.COLUMN_NAME_TEXT_COLOUR + " TEXT," +
                    UserPreferencesTableContract.COLUMN_NAME_TEXT_FONT + " TEXT," +
                    UserPreferencesTableContract.COLUMN_NAME_TEXT_SIZE + " TEXT," +
                    UserPreferencesTableContract.COLUMN_NAME_QUOTE_CATEGORY + " TEXT)";

    public static final String SQL_CREATE_USED_TWEETS_TABLE =
            "CREATE TABLE " + UsedTweetsTableContract.TABLE_NAME + " (" +
                    UsedTweetsTableContract._ID + " INTEGER PRIMARY KEY," +
                    UsedTweetsTableContract.COLUMN_NAME_QUOTE_TEXT + " TEXT," +
                    UsedTweetsTableContract.COLUMN_NAME_TWEET_ID + " TEXT)";

    public static final String SQL_CREATE_QUOTES_TO_USE_TABLE =
            "CREATE TABLE " + QuotesToUseTableContract.TABLE_NAME + " (" +
                    QuotesToUseTableContract._ID + " INTEGER PRIMARY KEY," +
                    QuotesToUseTableContract.COLUMN_NAME_QUOTE_TEXT + " TEXT," +
                    QuotesToUseTableContract.COLUMN_NAME_TWEET_ID + " TEXT)";

    public static final String SQL_CREATE_TWITTER_ACCOUNTS_LAST_USED_TWEET_TABLE =
            "CREATE TABLE " + TwitterAccountsLastUsedTweetTableContract.TABLE_NAME + " (" +
                    TwitterAccountsLastUsedTweetTableContract._ID + " INTEGER PRIMARY KEY," +
                    TwitterAccountsLastUsedTweetTableContract.COLUMN_NAME_INSPIRE_US + " TEXT," +
                    TwitterAccountsLastUsedTweetTableContract.COLUMN_NAME_LOVE_QUOTES + " TEXT," +
                    TwitterAccountsLastUsedTweetTableContract.COLUMN_NAME_SPORTS_GREATS + " TEXT," +
                    TwitterAccountsLastUsedTweetTableContract.COLUMN_NAME_BOOKS_TEXTS + " TEXT," +
                    TwitterAccountsLastUsedTweetTableContract.COLUMN_NAME_UPLIFTING_QUOTES + " TEXT," +
                    TwitterAccountsLastUsedTweetTableContract.COLUMN_NAME_PHILOSOPHY_TWEETS + " TEXT)";

    public static final String SQL_DELETE_USER_PREF_TABLE =
            "DROP TABLE IF EXISTS " + UserPreferencesTableContract.TABLE_NAME;

    public static final String SQL_DELETE_USED_TWEETS_TABLE =
            "DROP TABLE IF EXISTS " + UsedTweetsTableContract.TABLE_NAME;

    public static final String SQL_DELETE_QUOTES_TO_USE_TABLE =
            "DROP TABLE IF EXISTS " + QuotesToUseTableContract.TABLE_NAME;

    public static final String SQL_DELETE_TWITTER_ACCOUNTS_LAST_USED_TWEET_TABLE =
            "DROP TABLE IF EXISTS " + TwitterAccountsLastUsedTweetTableContract.TABLE_NAME;

    public static void openHelper(Context context) {
        if (_instance == null) {
            _instance = new MotivateMeDbHelper(context);
        }
        if (_openCount == 0) {
            _instance.getReadableDatabase();
        }
        _openCount++;
    }

    public static void closeHelper() {
        _openCount--;
        if (_instance != null && _openCount == 0) {
            _instance.close();
        }
    }

    public static MotivateMeDbHelper getInstance() {
        return _instance;
    }

    private MotivateMeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_USER_PREFS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_USED_TWEETS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_QUOTES_TO_USE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TWITTER_ACCOUNTS_LAST_USED_TWEET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_USER_PREF_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_USED_TWEETS_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_QUOTES_TO_USE_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_TWITTER_ACCOUNTS_LAST_USED_TWEET_TABLE);
        onCreate(sqLiteDatabase);
    }
}
