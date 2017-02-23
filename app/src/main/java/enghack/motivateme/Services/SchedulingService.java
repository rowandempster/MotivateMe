package enghack.motivateme.Services;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.os.IBinder;
import android.support.annotation.Nullable;

import enghack.motivateme.Database.UserPreferencesTable.UserPreferencesTableInterface;


public class SchedulingService extends Service {
    private JobScheduler scheduler;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.cancelAll();
        JobInfo.Builder builder = new JobInfo.Builder(1,
                new ComponentName(getPackageName(),
                        FetchQuoteUpdateBackgroundService.class.getName()));

        builder.setPeriodic(UserPreferencesTableInterface.readRefreshInterval());

        scheduler.schedule(builder.build());
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean stopService(Intent name) {
        scheduler.cancelAll();
        return super.stopService(name);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
