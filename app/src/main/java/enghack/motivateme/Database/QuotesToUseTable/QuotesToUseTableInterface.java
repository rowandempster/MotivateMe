package enghack.motivateme.Database.QuotesToUseTable;

import android.content.ContentValues;
import android.database.Cursor;

import enghack.motivateme.Constants;
import enghack.motivateme.Database.MotivateMeDbHelper;
import enghack.motivateme.Database.MotivateMeDatabaseUtils;
import enghack.motivateme.Database.UserPreferencesTable.UserPreferencesTableInterface;
import enghack.motivateme.Models.QuoteDatabaseModel;
import enghack.motivateme.Tasks.PullTweets.PullTweetsParams;
import enghack.motivateme.Tasks.PullTweets.PullTweetsTask;

/**
 * Created by rowandempster on 2/22/17.
 */

public class QuotesToUseTableInterface {

    public static void clearTable(){
        if(MotivateMeDbHelper.getInstance() != null){
            MotivateMeDbHelper.getInstance().getReadableDatabase().execSQL(MotivateMeDbHelper.SQL_DELETE_QUOTES_TO_USE_TABLE);
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
    public static QuoteDatabaseModel getAndRemoveFirstQuoteAndPullMoreIfNeeded(){
        if (MotivateMeDbHelper.getInstance() != null) {
            Cursor cursor = MotivateMeDbHelper.getInstance().getReadableDatabase().query(QuotesToUseTableContract.TABLE_NAME, new String[] { "*" },
                    null,
                    null, null, null, null, null);
            if(cursor == null || !cursor.moveToFirst()){
                return null;
            }
            long rowId = cursor.getLong(cursor.getColumnIndex(QuotesToUseTableContract._ID));
            String text = cursor.getString(cursor.getColumnIndex(QuotesToUseTableContract.COLUMN_NAME_QUOTE_TEXT));
            long tweetId = cursor.getLong(cursor.getColumnIndex(QuotesToUseTableContract.COLUMN_NAME_TWEET_ID));
            cursor.close();
            MotivateMeDatabaseUtils.deleteRow(MotivateMeDbHelper.getInstance().getReadableDatabase(), QuotesToUseTableContract.TABLE_NAME, rowId);
            String accountToPull = UserPreferencesTableInterface.readQuoteCategory();
            PullTweetsTask.pullTweetsIfNeeded(MotivateMeDbHelper.getInstance().getReadableDatabase(), new PullTweetsParams(accountToPull, Constants.TWEETS_TO_PULL_NORMAL_AMOUNT));
            return new QuoteDatabaseModel(text, tweetId);
        }
        return null;
    }

}
