package enghack.motivateme.Managers;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

import enghack.motivateme.Database.Exceptions.EmptyTableException;
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
import enghack.motivateme.Util.DisplayUtils;
import enghack.motivateme.Util.StringUtils;

/**
 * Created by rowandempster on 2/26/17.
 */

public class MotivateMeWallpaperManager {

    public static void refreshWallPaperWithCurrentSettingsAndCurrentQuoteIfBackgroundIsSet(final Context context) throws EmptyTableException {
        MotivateMeDbHelper.openHelper(context);
        UserPreferencesModel userPreferences = getUserPreferences(context);

        CreateWallPaperTask wallPaperTask = new CreateWallPaperTask(getWallpaperCallbackInterface(context));
        wallPaperTask.attachProgressDialog(new ProgressDialog(context));
        wallPaperTask.execute(getWallpaperParamsFromMostRecentQuote(userPreferences, context));

        MotivateMeDbHelper.closeHelper();
    }

    public static void updateWallPaperWithNewQuoteAndAddToUsedIfBackgroundIsSet(final Context context) {
        MotivateMeDbHelper.openHelper(context);
        UserPreferencesModel userPreferences = getUserPreferences(context);

        CreateWallPaperTask wallPaperTask = new CreateWallPaperTask(getWallpaperCallbackInterface(context));
        wallPaperTask.attachProgressDialog(new ProgressDialog(context));
        try {
            wallPaperTask.execute(getWallpaperParamsFromNewQuoteAndAddQuoteToUsed(userPreferences, context));
        } catch (EmptyTableException e) {
            Toast toast = Toast.makeText(context, "Error, please check your connection", Toast.LENGTH_LONG);
            toast.show();
        }

        MotivateMeDbHelper.closeHelper();
    }

    public static CreateWallpaperParams getWallpaperParamsFromNewQuoteAndAddQuoteToUsed(UserPreferencesModel userPreferences, Context context) throws EmptyTableException {
        MotivateMeDbHelper.openHelper(context);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), userPreferences.getTextFont());
        Bitmap background = getBackgroundBitmap(context);
        QuoteDatabaseModel quote = QuotesToUseTableInterface.getAndRemoveFirstQuoteAndPullMoreIfNeeded();
        UsedTweetsTableInterface.writeNewUsedTweet(quote);
        WindowManager window = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int style = userPreferences.getTextStyle();
        MotivateMeDbHelper.closeHelper();
        return new CreateWallpaperParams(window.getDefaultDisplay().getWidth(), window.getDefaultDisplay().getHeight(), quote.getText(), userPreferences.getTextSize(), userPreferences.getTextColour(), typeface, background, style);
    }


    //Private Factory methods

    private static CreateWallPaperTaskInterface getWallpaperCallbackInterface(Context context) {
        return new CreateWallPaperTaskInterface() {
            @Override
            public void onStart(int duration) {
            }

            @Override
            public void onProgress(CreateWallpaperProgress progress) {
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
            }

            @Override
            public void onFinishUiThread() {

            }
        };
    }

    private static CreateWallpaperParams getWallpaperParamsFromMostRecentQuote(UserPreferencesModel userPreferences, Context context) throws EmptyTableException {
        QuoteDatabaseModel quote = getQuote();
        WindowManager window = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), userPreferences.getTextFont());
        int style = userPreferences.getTextStyle();
        Bitmap background = getBackgroundBitmap(context);
        return new CreateWallpaperParams(window.getDefaultDisplay().getWidth(), window.getDefaultDisplay().getHeight(), quote.getText(), userPreferences.getTextSize(), userPreferences.getTextColour(), typeface, background, style);
    }

    private static QuoteDatabaseModel getQuote() throws EmptyTableException {
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
        DisplayUtils.WidthAndHeight dimens = DisplayUtils.getWidthAndHeight(context);
        if(StringUtils.isNullOrEmpty(UserPreferencesTableInterface.readBackgroundUri())){
            Bitmap emptyBackground = Bitmap.createBitmap(dimens.width, dimens.height, Bitmap.Config.ARGB_8888);
            emptyBackground.eraseColor(Color.WHITE);
            return emptyBackground;
        }
        Bitmap background = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = BitmapUtils.calculateSampleSize(context, Uri.parse(UserPreferencesTableInterface.readBackgroundUri()));
        try {
            background = BitmapUtils.decodeSampledBitmapFromUri(context, Uri.parse(UserPreferencesTableInterface.readBackgroundUri()), options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        background = Bitmap.createScaledBitmap(background, dimens.width, dimens.height, false);
        return background;
    }
}
