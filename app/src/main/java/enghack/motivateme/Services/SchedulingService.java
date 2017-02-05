package enghack.motivateme.Services;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;


public class SchedulingService extends Service {
    boolean fuck = true;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(1,
                new ComponentName(getPackageName(),
                        FetchQuoteUpdateBackgroundService.class.getName()));

        builder.setPeriodic(5000);

        scheduler.schedule(builder.build());
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
