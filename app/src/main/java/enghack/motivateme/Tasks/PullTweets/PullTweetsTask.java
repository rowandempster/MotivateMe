package enghack.motivateme.Tasks.PullTweets;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import enghack.motivateme.Database.MotivateMeDbHelper;
import enghack.motivateme.Database.QuotesToUseTable.QuotesToUseTableInterface;
import enghack.motivateme.Database.UsedTweetsTable.UsedTweetsTableInterface;
import enghack.motivateme.Models.QuoteDatabaseModel;
import enghack.motivateme.TwitterInstance;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * Created by rowandempster on 2/22/17.
 */

public class PullTweetsTask extends AsyncTask<PullTweetsParams, Void, Void> {
    private Context _context;

    public PullTweetsTask(Context context) {
        _context = context;
    }

    @Override
    protected Void doInBackground(PullTweetsParams... pullTweetsParams) {
        MotivateMeDbHelper.openHelper(_context);
        List<twitter4j.Status> tweets = getTweets(pullTweetsParams);
        filterTweets(tweets);
        putInDatabase(tweets);
        MotivateMeDbHelper.closeHelper();
        return null;
    }

    private void putInDatabase(List<twitter4j.Status> quotes) {
        for(twitter4j.Status quote : quotes){
            QuotesToUseTableInterface.addQuote(new QuoteDatabaseModel(quote.getText(), quote.getId()));
        }
    }

    private void filterTweets(List<twitter4j.Status> tweets) {
        for(twitter4j.Status tweet : tweets){
            if(!worthyTweet(tweet)){
                tweets.remove(tweet);
            }
        }
    }

    private boolean worthyTweet(twitter4j.Status tweet) {
        boolean notUsed = !UsedTweetsTableInterface.isTweetUsed(tweet.getId());
        String text = tweet.getText();
        boolean longEnough = text.length() > 14;
        boolean shortEnough = text.length() < 116;
        boolean noBadCharacters = !(text.contains("@") || text.contains("RT") || text.contains("http") || text.contains("//"));
        return notUsed && longEnough && shortEnough && noBadCharacters;
    }

    private List<twitter4j.Status> getTweets(PullTweetsParams[] pullTweetsParams) {
        try {
            return TwitterInstance.getInstance().getUserTimeline(pullTweetsParams[0].getCategory(),
                    new Paging(0, pullTweetsParams[0].getNumTweetsToGet()));
        } catch (TwitterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
