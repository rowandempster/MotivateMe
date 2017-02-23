package enghack.motivateme.Database.UsedTweetsTable;

import enghack.motivateme.Database.MotivateMeDbHelper;
import enghack.motivateme.Database.MotiveMeDatabaseUtils;

/**
 * Created by rowandempster on 2/21/17.
 */

public class UsedTweetsInterface {

    public static void writeNewUsedTweet(long tweetId) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotiveMeDatabaseUtils.writeLong(MotivateMeDbHelper.getInstance(), UsedTweetsContract.TABLE_NAME,
                    UsedTweetsContract.COLUMN_NAME_QUOTE_CATEGORY, tweetId);
        }
    }

    public static boolean isTweetUsed(long tweetId){

    }
}
