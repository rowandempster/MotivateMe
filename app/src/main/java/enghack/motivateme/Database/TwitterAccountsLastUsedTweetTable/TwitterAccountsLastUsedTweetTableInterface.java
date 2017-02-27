package enghack.motivateme.Database.TwitterAccountsLastUsedTweetTable;

import enghack.motivateme.Database.MotivateMeDbHelper;
import enghack.motivateme.Database.MotivateMeDatabaseUtils;

/**
 * Created by rowandempster on 2/24/17.
 */

public class TwitterAccountsLastUsedTweetTableInterface {
    public static void putLastUsedTweet(String account, long tweetId) {
        if (MotivateMeDbHelper.getInstance() != null) {
            MotivateMeDatabaseUtils.replaceLong(MotivateMeDbHelper.getInstance().getReadableDatabase(),
                    TwitterAccountsLastUsedTweetTableContract.TABLE_NAME, account, tweetId);
        }
    }

    public static long getLastUsedTweet(String account){
        if (MotivateMeDbHelper.getInstance() != null) {
            return MotivateMeDatabaseUtils.readFirstLong(MotivateMeDbHelper.getInstance().getReadableDatabase(),
                    TwitterAccountsLastUsedTweetTableContract.TABLE_NAME, TwitterAccountsLastUsedTweetTableContract._ID, account);
        }
        return 0;
    }
}
