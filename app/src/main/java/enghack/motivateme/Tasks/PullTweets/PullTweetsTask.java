package enghack.motivateme.Tasks.PullTweets;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import enghack.motivateme.Database.MotivateMeDbHelper;
import enghack.motivateme.Database.MotivateMeDatabaseUtils;
import enghack.motivateme.Database.QuotesToUseTable.QuotesToUseTableContract;
import enghack.motivateme.Database.QuotesToUseTable.QuotesToUseTableInterface;
import enghack.motivateme.Database.TwitterAccountsLastUsedTweetTable.TwitterAccountsLastUsedTweetTableInterface;
import enghack.motivateme.Database.UsedTweetsTable.UsedTweetsTableInterface;
import enghack.motivateme.Database.UserPreferencesTable.UserPreferencesTableInterface;
import enghack.motivateme.Models.QuoteDatabaseModel;
import enghack.motivateme.TwitterInstance;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * Created by rowandempster on 2/22/17.
 */

public class PullTweetsTask extends AsyncTask<PullTweetsParams, Void, Void> {
    private static PullTweetsTask _currTask;

    public static void pullTweetsIfNeeded(SQLiteDatabase db, PullTweetsParams toPull) {
        if (MotivateMeDatabaseUtils.isTableEmpty(db, QuotesToUseTableContract.TABLE_NAME)) {
            pullTweetsNotSafe(toPull);
        } else {
            MotivateMeDbHelper.closeHelper();
        }
    }

    public static void pullTweetsNotSafe(PullTweetsParams toPull) {
        if (_currTask == null) {
            _currTask = new PullTweetsTask();
            _currTask.execute(toPull);
        }
    }

    @Override
    protected Void doInBackground(PullTweetsParams... pullTweetsParams) {
        List<twitter4j.Status> tweets = getTweets(pullTweetsParams);
        if(tweets == null){
            return null;
        }
        recordLastUsedQuotes(pullTweetsParams[0].getCategory(), tweets.get(tweets.size() - 1).getId()-1);
        tweets = filterTweets(tweets);
        putInDatabase(tweets);
        MotivateMeDbHelper.closeHelper();
        _currTask = null;
        return null;
    }

    private void putInDatabase(List<twitter4j.Status> quotes) {
        for (twitter4j.Status quote : quotes) {
            QuotesToUseTableInterface.addQuote(new QuoteDatabaseModel(quote.getText(), quote.getId()));
        }
    }

    private void recordLastUsedQuotes(String pulledAccount, long id) {
        TwitterAccountsLastUsedTweetTableInterface.putLastUsedTweet(pulledAccount, id);
    }


    private List<twitter4j.Status> filterTweets(List<twitter4j.Status> tweets) {
        List<twitter4j.Status> goodQuotes = new ArrayList<>();
        for (twitter4j.Status tweet : tweets) {
            if (worthyTweet(tweet)) {
                goodQuotes.add(tweet);
            }
        }
        return goodQuotes;
    }

    private boolean worthyTweet(twitter4j.Status tweet) {
        String text = tweet.getText();
        boolean longEnough = text.length() > 14;
        boolean shortEnough = text.length() < 116;
        boolean noBadCharacters = !(text.contains("@") || text.contains("RT") || text.contains("http") || text.contains("//"));
        return longEnough && shortEnough && noBadCharacters;
    }

    private List<twitter4j.Status> getTweets(PullTweetsParams[] pullTweetsParams) {
        try {
            Paging paging = new Paging();
            paging.setCount(pullTweetsParams[0].getNumTweetsToGet());
            long lastUsedId = TwitterAccountsLastUsedTweetTableInterface.getLastUsedTweet(pullTweetsParams[0].getCategory());
            if(lastUsedId>0) {
                paging.setMaxId(lastUsedId);
            }
            return TwitterInstance.getInstance().getUserTimeline(pullTweetsParams[0].getCategory(),
                    paging);
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
