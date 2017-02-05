package enghack.motivateme.Services;

import android.app.job.JobParameters;
import android.app.job.JobService;

import java.util.List;

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

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("vaZKWtXdTQCWhPFjiNAaJwuMz")
                .setOAuthConsumerSecret("KQzAKgUC1Y31YRH2AvuHCSZPa83fq42tLgnn080UtMDo5KjMPp")
                .setOAuthAccessToken("828006071377788929-YAmu75Lxyb8KzQ8MnNxiPQbJPhsrlgL")
                .setOAuthAccessTokenSecret("X4Te52AsnPktMCABDsNyGgevHPxDDZKrylDWdC1YeE7FT");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();




        // set this with twitter result

        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Status> statuses = twitter.getUserTimeline("Inspire_Us", new Paging(1, 1));
                    for(Status tweet : statuses){
                        final String quote = tweet.getText();
                    }
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
        });
        newThread.start();


//
//        Intent intent = new Intent("intent");
//        // Adding some data
//        intent.putExtra("message", quote);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
