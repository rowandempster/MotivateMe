package enghack.motivateme;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.widget.Toast;

import enghack.motivateme.Services.FetchQuoteJob;

public class MainActivity extends AppCompatActivity {
    private String currentQuote;
    private Twitter twitter;
    private int currTweet = 1;

    BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            currentQuote = intent.getStringExtra("message");
            Thread newThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        currTweet++;
                        List<Status> statuses = twitter.getUserTimeline("Inspire_Us", new Paging(currTweet, 1));
                        for(Status tweet : statuses){
                            Toast.makeText(getApplicationContext(), tweet.getText(), Toast.LENGTH_LONG).show();
                        }
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                }
            });
            newThread.start();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("vaZKWtXdTQCWhPFjiNAaJwuMz")
                .setOAuthConsumerSecret("KQzAKgUC1Y31YRH2AvuHCSZPa83fq42tLgnn080UtMDo5KjMPp")
                .setOAuthAccessToken("828006071377788929-YAmu75Lxyb8KzQ8MnNxiPQbJPhsrlgL")
                .setOAuthAccessTokenSecret("X4Te52AsnPktMCABDsNyGgevHPxDDZKrylDWdC1YeE7FT");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();




        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver,
                        new IntentFilter("intent"));

        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(1,
                new ComponentName(getPackageName(),
                        FetchQuoteJob.class.getName()));
        builder.setPeriodic(5000);
        scheduler.schedule(builder.build());
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mMessageReceiver);
    }
}
