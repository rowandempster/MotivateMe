package enghack.motivateme.Services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by itwasarainyday on 04/02/17.
 */

public class FetchQuoteJob extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        String quote = "worlds best quote"; // set this with twitter result
        Intent intent = new Intent("intent");
        // Adding some data
        intent.putExtra("message", quote);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
