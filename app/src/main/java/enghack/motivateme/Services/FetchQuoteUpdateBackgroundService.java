package enghack.motivateme.Services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by itwasarainyday on 04/02/17.
 */

public class FetchQuoteUpdateBackgroundService extends JobService {
    private Twitter twitter;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {



        twitterConnection();

        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String quote;
                    int searchIndex = 1;
                    scavenge: while (true) {
                        List<Status> statuses = twitter.getUserTimeline("Inspire_Us", new Paging(50 * searchIndex, 1));
                        for (Status tweet : statuses) {
                            if (worthyQuote(quote = tweet.getText())) {
                                final String id = Long.toString(tweet.getId());
                                SharedPreferences sp = getApplicationContext().getSharedPreferences("MotivateMeSP", 0);
                                Set<String> oldSet = sp.getStringSet("usedTweets", new HashSet<String>());
                                oldSet.add(id);
                                sp.edit().putStringSet("usedTweets", oldSet).apply();
                                break scavenge;
                            }
                        }
                        ++searchIndex;
                    }
                    // quote is good here
                    //sendGoodQuote(quote);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }

        });
        newThread.start();

//        Intent intent = new Intent("intent");
//        // Adding some data
//        intent.putExtra("message", quote);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        return false;
    }

    private boolean worthyQuote(String text) {
        if (text.contains("@") || text.contains("RT") || text.contains("http")
                || text.contains("//"))
            return false;
        return true;
    }

    private void twitterConnection() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("vaZKWtXdTQCWhPFjiNAaJwuMz")
                .setOAuthConsumerSecret("KQzAKgUC1Y31YRH2AvuHCSZPa83fq42tLgnn080UtMDo5KjMPp")
                .setOAuthAccessToken("828006071377788929-YAmu75Lxyb8KzQ8MnNxiPQbJPhsrlgL")
                .setOAuthAccessTokenSecret("X4Te52AsnPktMCABDsNyGgevHPxDDZKrylDWdC1YeE7FT");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
        // set this with twitter result
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
