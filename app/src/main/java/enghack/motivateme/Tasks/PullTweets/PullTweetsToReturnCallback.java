package enghack.motivateme.Tasks.PullTweets;

import java.util.List;

import twitter4j.Status;

/**
 * Created by rowandempster on 3/5/17.
 */

public interface PullTweetsToReturnCallback {
    void start();
    void done(List<Status> quotes);
}
