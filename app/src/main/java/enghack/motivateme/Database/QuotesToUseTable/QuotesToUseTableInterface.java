package enghack.motivateme.Database.QuotesToUseTable;

import android.content.ContentValues;
import android.database.Cursor;

import enghack.motivateme.Database.MotivateMeDbHelper;
import enghack.motivateme.Database.MotiveMeDatabaseUtils;
import enghack.motivateme.Models.QuoteDatabaseModel;

/**
 * Created by rowandempster on 2/22/17.
 */

public class QuotesToUseTableInterface {

    public static void clearTable(){
        if(MotivateMeDbHelper.getInstance() != null){
            MotivateMeDbHelper.getInstance().getReadableDatabase().execSQL(MotivateMeDbHelper.SQL_DELETE_TWEETS_TO_USE_TABLE);
            MotivateMeDbHelper.getInstance().getReadableDatabase().execSQL(MotivateMeDbHelper.SQL_CREATE_QUOTES_TO_USE_TABLE);
        }
    }
    public static void addQuote(QuoteDatabaseModel quote){
        if (MotivateMeDbHelper.getInstance() != null) {
            ContentValues values = new ContentValues();
            values.put(QuotesToUseTableContract.COLUMN_NAME_QUOTE_TEXT, quote.getText());
            values.put(QuotesToUseTableContract.COLUMN_NAME_TWEET_ID, quote.getTweetId());
            MotivateMeDbHelper.getInstance().getReadableDatabase().insert(QuotesToUseTableContract.TABLE_NAME, null, values);
        }
    }
    public static QuoteDatabaseModel getAndRemoveFirstQuote(){
        if (MotivateMeDbHelper.getInstance() != null) {
            Cursor cursor = MotivateMeDbHelper.getInstance().getReadableDatabase().query(QuotesToUseTableContract.TABLE_NAME, new String[] { "*" },
                    null,
                    null, null, null, null, null);
            if(cursor == null){
                return null;
            }
            long rowId = cursor.getLong(cursor.getColumnIndex(QuotesToUseTableContract._ID));
            String text = cursor.getString(cursor.getColumnIndex(QuotesToUseTableContract.COLUMN_NAME_QUOTE_TEXT));
            long tweetId = cursor.getLong(cursor.getColumnIndex(QuotesToUseTableContract.COLUMN_NAME_TWEET_ID));
            MotiveMeDatabaseUtils.deleteRow(MotivateMeDbHelper.getInstance().getReadableDatabase(), QuotesToUseTableContract.TABLE_NAME, rowId);
            return new QuoteDatabaseModel(text, tweetId);
        }
        return null;
    }

}
