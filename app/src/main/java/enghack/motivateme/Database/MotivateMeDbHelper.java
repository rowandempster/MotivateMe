package enghack.motivateme.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import enghack.motivateme.Database.UserPreferencesTable.UserPreferencesContract;

/**
 * Created by rowandempster on 2/20/17.
 */

public class MotivateMeDbHelper extends SQLiteOpenHelper {
    private static MotivateMeDbHelper _instance;
    private static int _openCount;

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "MotivateMe.db";

    private static final String SQL_CREATE_USER_PREFS_TABLE =
            "CREATE TABLE " + UserPreferencesContract.TABLE_NAME + " (" +
                    UserPreferencesContract._ID + " INTEGER PRIMARY KEY," +
                    UserPreferencesContract.COLUMN_NAME_BACKGROUND_URI + " TEXT," +
                    UserPreferencesContract.COLUMN_NAME_REFRESH_INTERVAL + " TEXT," +
                    UserPreferencesContract.COLUMN_NAME_TEXT_COLOUR + " TEXT," +
                    UserPreferencesContract.COLUMN_NAME_TEXT_FONT + " TEXT," +
                    UserPreferencesContract.COLUMN_NAME_TEXT_SIZE + " TEXT," +
                    UserPreferencesContract.COLUMN_NAME_QUOTE_CATEGORY + " TEXT)";

    private static final String SQL_DELETE_USER_PREF_TABLE =
            "DROP TABLE IF EXISTS " + UserPreferencesContract.TABLE_NAME;

    public static void openHelper(Context context){
        if(_instance == null){
            _instance = new MotivateMeDbHelper(context);
        }
        if(_openCount == 0){
            _instance.getReadableDatabase();
        }
        _openCount++;
    }

    public static void closeHelper(){
        _openCount--;
        if(_instance != null && _openCount == 0){
            _instance.close();
        }
    }

    public static MotivateMeDbHelper getInstance(){
        return _instance;
    }

    private MotivateMeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_USER_PREFS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_USER_PREF_TABLE);
        onCreate(sqLiteDatabase);
    }
}
