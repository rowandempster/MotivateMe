package enghack.motivateme.Services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;


import java.io.IOException;

import enghack.motivateme.Database.MotivateMeDbHelper;
import enghack.motivateme.Database.UserPreferencesTable.UserPreferencesModel;
import enghack.motivateme.Database.UserPreferencesTable.UserPreferencesTableInterface;
import enghack.motivateme.Tasks.CreateWallpaper.CreateWallPaperTaskInterface;
import enghack.motivateme.Tasks.CreateWallpaper.CreateWallpaperProgress;
import enghack.motivateme.Tasks.CreateWallpaper.CreateWallPaperTask;
import enghack.motivateme.Tasks.CreateWallpaper.CreateWallpaperParams;
import enghack.motivateme.Managers.MotivateMeWallpaperManager;


/**
 * Created by itwasarainyday on 04/02/17.
 */

public class UpdateWallpaperService extends JobService {

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        MotivateMeDbHelper.openHelper(this);

        Thread newThread = new Thread(() -> {
            UserPreferencesModel userPrefs = UserPreferencesTableInterface.readUserPreferences();
            CreateWallpaperParams wallpaperParams = MotivateMeWallpaperManager.getWallpaperParamsFromNewQuoteAndAddQuoteToUsed(userPrefs, UpdateWallpaperService.this);

            CreateWallPaperTask wallPaperTask = getCreationTask(jobParameters);
            wallPaperTask.execute(wallpaperParams);
        });
        newThread.start();
        return false;
    }

    private CreateWallPaperTask getCreationTask(final JobParameters jobParameters) {
        return new CreateWallPaperTask(new CreateWallPaperTaskInterface() {
                    @Override
                    public void onStart(int duration) {

                    }

                    @Override
                    public void onProgress(CreateWallpaperProgress progress) {

                    }

                    @Override
                    public void onFinishNonUiThread(Bitmap wallpaper) {
                        android.app.WallpaperManager wallpaperManager = android.app.WallpaperManager.getInstance(UpdateWallpaperService.this);
                        wallpaperManager.forgetLoadedWallpaper();
                        try {
                            wallpaperManager.setBitmap(wallpaper);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        jobFinished(jobParameters, false);
                        MotivateMeDbHelper.closeHelper();
                    }

                    @Override
                    public void onFinishUiThread() {

                    }
                });
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
