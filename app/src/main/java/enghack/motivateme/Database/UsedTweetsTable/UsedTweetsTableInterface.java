package enghack.motivateme.Database.UsedTweetsTable;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import enghack.motivateme.Database.MotivateMeDbHelper;
import enghack.motivateme.Database.MotivateMeDatabaseUtils;
import enghack.motivateme.Models.QuoteDatabaseModel;

/**
 * Created by rowandempster on 2/21/17.
 */

public class UsedTweetsTableInterface {

    public static void writeNewUsedTweet(QuoteDatabaseModel quote) {
        if (MotivateMeDbHelper.getInstance() != null) {
            ContentValues values = getNewTweetContentValues(quote);
            MotivateMeDbHelper.getInstance().getReadableDatabase().insert(UsedTweetsTableContract.TABLE_NAME, null, values);
        }
    }

    public static boolean isTweetUsed(long tweetId) {
        if (MotivateMeDbHelper.getInstance() != null) {
            return MotivateMeDatabaseUtils.columnContainsLong(MotivateMeDbHelper.getInstance().getReadableDatabase(), UsedTweetsTableContract.TABLE_NAME,
                    UsedTweetsTableContract.COLUMN_NAME_TWEET_ID, tweetId);
        }
        return false;
    }

    public static QuoteDatabaseModel getMostRecentTweet() {
        if (MotivateMeDbHelper.getInstance() != null) {
            Cursor cursor = MotivateMeDatabaseUtils.getMostRecentRow(MotivateMeDbHelper.getInstance().getReadableDatabase(), UsedTweetsTableContract.TABLE_NAME);
            if (cursor == null || !cursor.moveToLast()) {
                return null;
            }

            QuoteDatabaseModel quote = getQuoteModel(cursor);
            cursor.close();

            return quote;
        }
        return null;
    }

    private static QuoteDatabaseModel getQuoteModel(Cursor cursor) {
        String quoteText = cursor.getString(cursor.getColumnIndex(UsedTweetsTableContract.COLUMN_NAME_QUOTE_TEXT));
        long tweetId = cursor.getLong(cursor.getColumnIndex(UsedTweetsTableContract.COLUMN_NAME_TWEET_ID));
        return new QuoteDatabaseModel(quoteText, tweetId);
    }

    private static ContentValues getNewTweetContentValues(QuoteDatabaseModel quote) {
        ContentValues values = new ContentValues();
        values.put(UsedTweetsTableContract.COLUMN_NAME_QUOTE_TEXT, quote.getText());
        values.put(UsedTweetsTableContract.COLUMN_NAME_TWEET_ID, quote.getTweetId());
        return values;
    }
}
