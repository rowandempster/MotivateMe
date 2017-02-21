package enghack.motivateme.Database.UserPreferencesTable;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rowandempster on 2/20/17.
 */

public class UserPreferencesDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UserPreferences.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserPreferencesContract.UserPreferences.TABLE_NAME + " (" +
                    UserPreferencesContract.UserPreferences._ID + " INTEGER PRIMARY KEY," +
                    UserPreferencesContract.UserPreferences.COLUMN_NAME_BACKGROUND_URI + " TEXT," +
                    UserPreferencesContract.UserPreferences.COLUMN_NAME_REFRESH_INTERVAL + " TEXT," +
                    UserPreferencesContract.UserPreferences.COLUMN_NAME_TEXT_COLOUR + " TEXT," +
                    UserPreferencesContract.UserPreferences.COLUMN_NAME_TEXT_FONT + " TEXT," +
                    UserPreferencesContract.UserPreferences.COLUMN_NAME_TEXT_SIZE + " TEXT," +
                    UserPreferencesContract.UserPreferences.COLUMN_NAME_QUOTE_CATEGORY + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserPreferencesContract.UserPreferences.TABLE_NAME;

    public UserPreferencesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
