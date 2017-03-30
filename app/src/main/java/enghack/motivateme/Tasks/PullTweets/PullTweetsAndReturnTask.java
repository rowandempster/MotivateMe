package enghack.motivateme.Tasks.PullTweets;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import enghack.motivateme.Database.MotivateMeDatabaseUtils;
import enghack.motivateme.Database.MotivateMeDbHelper;
import enghack.motivateme.Database.QuotesToUseTable.QuotesToUseTableContract;
import enghack.motivateme.Database.QuotesToUseTable.QuotesToUseTableInterface;
import enghack.motivateme.Database.TwitterAccountsLastUsedTweetTable.TwitterAccountsLastUsedTweetTableInterface;
import enghack.motivateme.Models.QuoteDatabaseModel;
import enghack.motivateme.Util.TwitterInstance;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * Created by rowandempster on 2/22/17.
 */

public class PullTweetsAndReturnTask extends AsyncTask<PullTweetsParams, Void, List<Status>> {
    private PullTweetsToReturnCallback _callback;
    private ProgressDialog _progressDialog;

    public PullTweetsAndReturnTask(PullTweetsToReturnCallback callback) {
        _callback = callback;
    }

    public PullTweetsAndReturnTask() {
        _callback = new PullTweetsToReturnCallback() {
            @Override
            public void start() {

            }

            @Override
            public void done(List<twitter4j.Status> quotes) {

            }
        };
    }

    public void attachProgressDialog(ProgressDialog progressDialog){
        _progressDialog = progressDialog;
        initDialog();
    }

    private void initDialog() {
        _progressDialog.setCancelable(false);
        _progressDialog.setCanceledOnTouchOutside(false);
        _progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        _progressDialog.setIndeterminate(false);
        _progressDialog.setMessage("Getting new quotes...");
    }

    @Override
    protected void onPreExecute() {
        _callback.start();
        if(_progressDialog != null){
            _progressDialog.show();
        }
    }

    @Override
    protected void onPostExecute(List<twitter4j.Status> quotes) {
        _callback.done(quotes);
        if(_progressDialog != null){
            _progressDialog.cancel();
        }
    }

    @Override
    protected List<twitter4j.Status> doInBackground(PullTweetsParams... pullTweetsParams) {
        List<twitter4j.Status> tweets = getTweets(pullTweetsParams[0]);
        tweets = filterTweets(tweets);
        return tweets;
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
            return TwitterInstance.getInstance().getUserTimeline(params.getCategory(), paging);
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
