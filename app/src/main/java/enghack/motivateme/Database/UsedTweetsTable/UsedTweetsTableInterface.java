package enghack.motivateme.Database.UsedTweetsTable;

import enghack.motivateme.Database.MotivateMeDbHelper;
import enghack.motivateme.Database.MotivateMeDatabaseUtils;

/**
 * Created by rowandempster on 2/21/17.
 */

public class UsedTweetsTableInterface {

    public static void writeNewUsedTweet(long tweetId) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotivateMeDatabaseUtils.writeLong(MotivateMeDbHelper.getInstance().getReadableDatabase(), UsedTweetsTableContract.TABLE_NAME,
                    UsedTweetsTableContract.COLUMN_NAME_TWEET_ID, tweetId);
        }
    }

    public static boolean isTweetUsed(long tweetId){
        if(MotivateMeDbHelper.getInstance()!= null){
            return MotivateMeDatabaseUtils.columnContainsLong(MotivateMeDbHelper.getInstance().getReadableDatabase(), UsedTweetsTableContract.TABLE_NAME,
                    UsedTweetsTableContract.COLUMN_NAME_TWEET_ID, tweetId);
        }
        return false;
    }
}
