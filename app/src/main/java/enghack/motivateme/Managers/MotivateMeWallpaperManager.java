package enghack.motivateme.Managers;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import java.io.FileNotFoundException;
import java.io.IOException;

import enghack.motivateme.Database.MotivateMeDbHelper;
import enghack.motivateme.Database.QuotesToUseTable.QuotesToUseTableInterface;
import enghack.motivateme.Database.UsedTweetsTable.UsedTweetsTableInterface;
import enghack.motivateme.Database.UserPreferencesTable.UserPreferencesModel;
import enghack.motivateme.Database.UserPreferencesTable.UserPreferencesTableInterface;
import enghack.motivateme.Models.QuoteDatabaseModel;
import enghack.motivateme.Tasks.CreateWallpaper.CreateWallPaperTaskInterface;
import enghack.motivateme.Tasks.CreateWallpaper.CreateWallpaperProgress;
import enghack.motivateme.Tasks.CreateWallpaper.CreateWallPaperTask;
import enghack.motivateme.Tasks.CreateWallpaper.CreateWallpaperParams;
import enghack.motivateme.Util.BitmapUtils;
import enghack.motivateme.Util.StringUtils;

/**
 * Created by rowandempster on 2/26/17.
 */

public class MotivateMeWallpaperManager {
    private static ProgressDialog _settingWallPaperProgressDialog;

    public static void refreshWallPaperWithCurrentSettingsAndCurrentQuoteIfBackgroundIsSet(final Context context) {
        UserPreferencesModel userPreferences = getUserPreferences(context);
        if (StringUtils.isNullOrEmpty(userPreferences.getBackgroundUri())) {
            return;
        }

        initProgressDialog(context);

        CreateWallPaperTask wallPaperTask = new CreateWallPaperTask(getWallpaperCallbackInterface(context));
        wallPaperTask.execute(getWallpaperParamsFromMostRecentQuote(userPreferences, context));

        MotivateMeDbHelper.closeHelper();
    }

    public static void updateWallPaperWithNewQuoteAndAddToUsedIfBackgroundIsSet(final Context context) {
        UserPreferencesModel userPreferences = getUserPreferences(context);
        if (StringUtils.isNullOrEmpty(userPreferences.getBackgroundUri())) {
            return;
        }

        initProgressDialog(context);

        CreateWallPaperTask wallPaperTask = new CreateWallPaperTask(getWallpaperCallbackInterface(context));
        wallPaperTask.execute(getWallpaperParamsFromNewQuoteAndAddQuoteToUsed(userPreferences, context));

        MotivateMeDbHelper.closeHelper();
    }

    public static CreateWallpaperParams getWallpaperParamsFromNewQuoteAndAddQuoteToUsed(UserPreferencesModel userPreferences, Context context) {
        MotivateMeDbHelper.openHelper(context);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), userPreferences.getTextFont());
        Bitmap background = getBackgroundBitmap(context);
        QuoteDatabaseModel quote = QuotesToUseTableInterface.getAndRemoveFirstQuoteAndPullMoreIfNeeded();
        UsedTweetsTableInterface.writeNewUsedTweet(quote);
        WindowManager window = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        MotivateMeDbHelper.closeHelper();
        return new CreateWallpaperParams(window.getDefaultDisplay().getWidth(), window.getDefaultDisplay().getHeight(), quote.getText(), userPreferences.getTextSize(), userPreferences.getTextColour(), typeface, background);
    }


    //Private Factory methods

    private static CreateWallPaperTaskInterface getWallpaperCallbackInterface(Context context) {
        return new CreateWallPaperTaskInterface() {
            @Override
            public void onStart(int duration) {
                _settingWallPaperProgressDialog.show();
            }

            @Override
            public void onProgress(CreateWallpaperProgress progress) {
                int percent = (int) ((progress.getProgress() / progress.getMax()) * 100);
                _settingWallPaperProgressDialog.setProgress(percent);
            }

            @Override
            public void onFinishNonUiThread(Bitmap wallpaper) {
                android.app.WallpaperManager wallpaperManager = android.app.WallpaperManager.getInstance(context);
                wallpaperManager.forgetLoadedWallpaper();
                try {
                    wallpaperManager.setBitmap(wallpaper);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                _settingWallPaperProgressDialog.dismiss();
            }

            @Override
            public void onFinishUiThread() {

            }
        };
    }

    private static void initProgressDialog(Context context) {
        _settingWallPaperProgressDialog = new ProgressDialog(context);
        _settingWallPaperProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        _settingWallPaperProgressDialog.setMessage("Updating your wallpaper...");
        _settingWallPaperProgressDialog.setIndeterminate(false);
        _settingWallPaperProgressDialog.setMax(100);
    }

    private static CreateWallpaperParams getWallpaperParamsFromMostRecentQuote(UserPreferencesModel userPreferences, Context context) {
        QuoteDatabaseModel quote = getQuote();
        WindowManager window = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), userPreferences.getTextFont());
        Bitmap background = getBackgroundBitmap(context);
        return new CreateWallpaperParams(window.getDefaultDisplay().getWidth(), window.getDefaultDisplay().getHeight(), quote.getText(), userPreferences.getTextSize(), userPreferences.getTextColour(), typeface, background);
    }

    private static QuoteDatabaseModel getQuote() {
        QuoteDatabaseModel quote = UsedTweetsTableInterface.getMostRecentTweet();
        if (quote == null) {
            quote = QuotesToUseTableInterface.getAndRemoveFirstQuoteAndPullMoreIfNeeded();
            UsedTweetsTableInterface.writeNewUsedTweet(quote);
        }
        return quote;
    }

    private static UserPreferencesModel getUserPreferences(Context context) {
        MotivateMeDbHelper.openHelper(context);
        return UserPreferencesTableInterface.readUserPreferences();
    }

    private static Bitmap getBackgroundBitmap(Context context) {
        Bitmap background = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        try {
            background = BitmapUtils.decodeSampledBitmapFromUri(context, Uri.parse(UserPreferencesTableInterface.readBackgroundUri()), options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return background;
    }
}
