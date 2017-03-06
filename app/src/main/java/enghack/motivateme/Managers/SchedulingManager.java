package enghack.motivateme.Managers;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import java.util.concurrent.CopyOnWriteArrayList;

import enghack.motivateme.Activities.SettingsActivity;
import enghack.motivateme.Database.MotivateMeDbHelper;
import enghack.motivateme.Database.UserPreferencesTable.UserPreferencesTableInterface;
import enghack.motivateme.Services.UpdateWallpaperService;

/**
 * Created by rowandempster on 2/26/17.
 */

public class SchedulingManager {

    public static void stopJobs(Context context){
        getAndResetScheduler(context);
    }

    public static void settingChanged(Context context) {
        cancelJobsAndStart(context);
        MotivateMeWallpaperManager.refreshWallPaperWithCurrentSettingsAndCurrentQuoteIfBackgroundIsSet(context);
    }

    public static void cancelJobsAndStart(Context context) {
        JobScheduler scheduler = getAndResetScheduler(context);
        JobInfo.Builder builder = getJobBuilderInfo(context);

        scheduler.schedule(builder.build());
    }

    public static void cancelJobsAndStart(Context context, long refreshPeriod) {
        JobScheduler scheduler = getAndResetScheduler(context);
        JobInfo.Builder builder = getJobBuilderInfo(context, refreshPeriod);

        scheduler.schedule(builder.build());
    }

    private static JobInfo.Builder getJobBuilderInfo(Context context) {
        MotivateMeDbHelper.openHelper(context);

        JobInfo.Builder builder = new JobInfo.Builder(1,
                new ComponentName(context.getPackageName(),
                        UpdateWallpaperService.class.getName()));
        long refreshInterval = UserPreferencesTableInterface.readRefreshInterval();
        builder.setPeriodic(refreshInterval);

        MotivateMeDbHelper.closeHelper();

        return builder;
    }

    private static JobInfo.Builder getJobBuilderInfo(Context context, long refresh) {

        JobInfo.Builder builder = new JobInfo.Builder(1,
                new ComponentName(context.getPackageName(),
                        UpdateWallpaperService.class.getName()));
        builder.setPeriodic(refresh);


        return builder;
    }


    private static JobScheduler getAndResetScheduler(Context context) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.cancelAll();
        return scheduler;
    }


}