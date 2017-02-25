package enghack.motivateme.Services;

import android.app.WallpaperManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.view.Display;
import android.view.WindowManager;


import java.io.IOException;

import android.net.Uri;

import java.util.List;

import enghack.motivateme.Constants;
import enghack.motivateme.Database.MotivateMeDbHelper;
import enghack.motivateme.Database.QuotesToUseTable.QuotesToUseTableInterface;
import enghack.motivateme.Database.UsedTweetsTable.UsedTweetsTableInterface;
import enghack.motivateme.Database.UserPreferencesTable.UserPreferencesTableInterface;
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
    public boolean onStartJob(final JobParameters jobParameters) {
        twitterConnection();
        MotivateMeDbHelper.openHelper(this);

        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                    setBackground(QuotesToUseTableInterface.getAndRemoveFirstQuoteAndPullMoreIfNeeded().getText());
                    // "Anger is an acid that can do more harm to the vessel in which it is stored than to anything on which it is poured filling words. -Mark Twain"
                    jobFinished(jobParameters, false); //success
                    MotivateMeDbHelper.closeHelper();
            }

        });
        newThread.start();
        return false;
    }

    private void setBackground(String quote) {
        String[] words = quote.split("\\s+");
        int width, height;

        WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = window.getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();

        if(width>height){
            int temp = height;
            height = width;
            width = temp;
        }
        int textSize = UserPreferencesTableInterface.readTextSize();
        int textColor = UserPreferencesTableInterface.readTextColour();

        int textHeight = (int) (height * 0.05);
                //((height*0.80 - ((words.length / 2) * (textSize + Constants.NEWLINE_BUFFER))) / 2);
                //(int) (height / (height*0.70 / (textSize + Constants.NEWLINE_BUFFER))); // height*0.70 is usable space on screen
                /*(int) (height / (0.4 * words.length))*/;

        String partialQuote = "";

        Bitmap background = null;
        try {
            background = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(UserPreferencesTableInterface.readBackgroundUri()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), UserPreferencesTableInterface.readTextFont()));
        paint.setTextAlign(Paint.Align.LEFT);

        float[] space = new float[1];
        paint.getTextWidths(" ", space);
        int spaceWidth = (int)space[0];

        int currLineNum = 1;
        int left = 0, right = words.length;
        while (left < right) {
            int currLineWidth = 0, wordsOnLine = 0;
            if (currLineNum >= 5) currLineNum = 1;

            partialQuote = "";
            for (int i = left; i < right; ++i) {
                int currWordWidth = 0;
                float[] widths = new float[words[i].length()];
                paint.getTextWidths(words[i], widths);
                for (int k = 0; k < widths.length; ++k) {
                    currLineWidth += widths[k];
                    currWordWidth += widths[k];
                }
                if (currLineWidth >= width - Constants.WIDTH_BUFFER *2*currLineNum) {
                    currLineWidth -= currWordWidth;
                    break;
                }
                currLineWidth += spaceWidth;
                partialQuote += words[i] + " ";
                ++wordsOnLine;
            }
            left = left + wordsOnLine;

           if (partialQuote.equals("")) {
                currLineNum = 1;
                continue;
            }
            Bitmap text = textAsBitmap(partialQuote, paint);

            background = combineImages(background, text, width, height, textHeight, width/2 - currLineWidth/2);

            textHeight += textSize + Constants.NEWLINE_BUFFER;
            ++currLineNum;
        }
        try {
            if(background != null) {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
                wallpaperManager.forgetLoadedWallpaper();
                wallpaperManager.setBitmap(background);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap combineImages(Bitmap background, Bitmap foreground, int width, int height, int vertStart, int horizStart) {
        Bitmap cs;

        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas comboImage = new Canvas(cs);
        background = Bitmap.createScaledBitmap(background, width, height, true);
        comboImage.drawBitmap(background, 0, 0, null);
        comboImage.drawBitmap(foreground, horizStart, vertStart, null);

        return cs;
    }

    private Bitmap textAsBitmap(String text, Paint paint) {
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    private void twitterConnection() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("vaZKWtXdTQCWhPFjiNAaJwuMz")
                .setOAuthConsumerSecret("KQzAKgUC1Y31YRH2AvuHCSZPa83fq42tLgnn080UtMDo5KjMPp")
                .setOAuthAccessToken("828006071377788929-YAmu75Lxyb8KzQ8MnNxiPQbJPhsrlgL")
                .setOAuthAccessTokenSecret("X4Te52AsnPktMCABDsNyGgevHPxDDZKrylDWdC1YeE7FT");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
        // set this with twitter result
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
