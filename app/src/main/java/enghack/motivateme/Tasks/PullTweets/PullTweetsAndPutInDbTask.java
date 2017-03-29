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
import enghack.motivateme.Models.QuoteDatabaseModel;
import enghack.motivateme.Util.TwitterInstance;
import twitter4j.Paging;
import twitter4j.TwitterException;

/**
 * Created by rowandempster on 2/22/17.
 */

public class PullTweetsAndPutInDbTask extends AsyncTask<PullTweetsParams, Void, Void> {
    private static PullTweetsAndPutInDbTask _currTask;
    private PullTweetsCallback _callback;

    public static void pullTweetsIfNeeded(SQLiteDatabase db, PullTweetsParams toPull) {
        if (MotivateMeDatabaseUtils.isTableEmpty(db, QuotesToUseTableContract.TABLE_NAME)) {
            pullTweetsNotSafe(toPull);
        } else {
            MotivateMeDbHelper.closeHelper();
        }
    }

    public static void pullTweetsNotSafe(PullTweetsParams toPull) {
        if (_currTask == null) {
            _currTask = new PullTweetsAndPutInDbTask();
            _currTask.execute(toPull);
        }
    }

    public static void pullTweetsNotSafe(PullTweetsParams toPull, PullTweetsCallback callback) {
        if (_currTask == null) {
            _currTask = new PullTweetsAndPutInDbTask(callback);
            _currTask.execute(toPull);
        }
    }

    public PullTweetsAndPutInDbTask(PullTweetsCallback callback){
        _callback = callback;
    }

    public PullTweetsAndPutInDbTask(){
        _callback = new PullTweetsCallback() {
            @Override
            public void start() {

            }

            @Override
            public void done() {

            }
        };
    }


    @Override
    protected void onPreExecute() {
        _callback.start();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        _callback.done();
        _currTask = null;
    }

    @Override
    protected Void doInBackground(PullTweetsParams... pullTweetsParams) {
        List<twitter4j.Status> tweets = getTweets(pullTweetsParams[0]);
        if (tweets != null) {
            recordLastPulledQuotes(pullTweetsParams[0].getCategory(), tweets.get(tweets.size() - 1).getId() - 1);
            tweets = filterTweets(tweets);
            putInDatabase(tweets);
            MotivateMeDbHelper.closeHelper();
        }
        return null;
    }

    private void putInDatabase(List<twitter4j.Status> quotes) {
        for (twitter4j.Status quote : quotes) {
            QuotesToUseTableInterface.addQuote(new QuoteDatabaseModel(quote.getText(), quote.getId()));
        }
    }

    private void recordLastPulledQuotes(String pulledAccount, long id) {
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

    private List<twitter4j.Status> getTweets(PullTweetsParams params) {
        try {
            Paging paging = new Paging();
            paging.setCount(params.getNumTweetsToGet());
            long lastUsedId = TwitterAccountsLastUsedTweetTableInterface.getLastUsedTweet(params.getCategory());
            if (lastUsedId > 0) {
                paging.setMaxId(lastUsedId);
            }
            return TwitterInstance.getInstance().getUserTimeline(params.getCategory(), paging);
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
