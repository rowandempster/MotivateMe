package enghack.motivateme.Services;

import android.app.WallpaperManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Gallery;
import android.widget.ImageView;


import java.io.File;
import java.io.IOException;

import android.net.Uri;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import enghack.motivateme.Constants;
import enghack.motivateme.R;
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

        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    setBackground(findQuote());
                    jobFinished(jobParameters, false); //success
                } catch (TwitterException e) {
                    e.printStackTrace();
                    jobFinished(jobParameters, false); //failure
                }
            }

        });
        newThread.start();
        return false;
    }

    private void setBackground(String quote) {
        String[] words = quote.split("\\s+");
        final int widthBuffer = 60;
        int width, height;
        WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = window.getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        int textHeight = (int) (height*0.1);

        int textSize = getSharedPreferences(Constants.MASTER_SP_KEY, 0).getInt(Constants.TEXT_SIZE_SP_KEY, 60);
        int textColor = getSharedPreferences(Constants.MASTER_SP_KEY, 0).getInt(Constants.TEXT_COLOR_SP_KEY, Color.BLACK);
        String partialQuote = "";

        Bitmap background = null;
        try {
            Log.d("asdf", "uri is " + getSharedPreferences(Constants.MASTER_SP_KEY, 0).getString(Constants.BACKGROUND_URI_SP_KEY, ""));
            background = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(getSharedPreferences(Constants.MASTER_SP_KEY, 0).getString(Constants.BACKGROUND_URI_SP_KEY, "")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), getSharedPreferences(Constants.MASTER_SP_KEY, 0).getString(Constants.TEXT_FONT_SP_KEY, "fonts/serif.ttf")));
        paint.setTextAlign(Paint.Align.LEFT);

        float[] space = new float[1];
        paint.getTextWidths(" ", space);
        int spaceWidth = (int)space[0];

        int currLineNum = 1;
        int left = 0, right = words.length;
        while (left < right) {
            int currLineWidth = 0, wordsOnLine = 0;
            partialQuote = "";
            for (int i = left; i < right; ++i) {
                int currWordWidth = 0;
                float[] widths = new float[words[i].length()];
                paint.getTextWidths(words[i], widths);
                for (int k = 0; k < widths.length; ++k) {
                    currLineWidth += widths[k];
                    currWordWidth += widths[k];
                }
                if (currLineWidth >= width - widthBuffer *(currLineNum*2)) {
                    currLineWidth -= currWordWidth;
                    break;
                }
                currLineWidth += spaceWidth;
                partialQuote += words[i] + " ";
                ++wordsOnLine;
            }
            left = left + wordsOnLine;

            Log.d("asdf", "partialQuote = " + partialQuote);
            if (partialQuote == null || partialQuote.equals("")) {
                currLineNum = 1;
                continue;
            }
            Bitmap text = textAsBitmap(partialQuote, paint);

            background = combineImages(background, text, width, height, textHeight, width/2 - currLineWidth/2);

            textHeight += textSize + 30;
            ++currLineNum;
        }
        try {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
            wallpaperManager.setBitmap(background);
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
    private String findQuote() throws TwitterException {
        String quote;
        int searchIndex = 1;
        scavenge: while (true) {
            List<Status> statuses = twitter.getUserTimeline(Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.
                    get(getSharedPreferences(Constants.MASTER_SP_KEY, 0).getInt(Constants.QUOTE_CATEGORY_SP_KEY, 0)),
                    new Paging(searchIndex, 20));
            for (Status tweet : statuses) {
                String tweetID = Long.toString(tweet.getId());
                if (worthyQuote(quote = tweet.getText(), tweetID)) {
                    addIDtoUsedTweets(tweetID);
                    break scavenge;
                }
            }
            ++searchIndex;
        }
        return quote;
    }

    private void addIDtoUsedTweets(String id) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MotivateMeSP", 0);
        Set<String> oldSet = sp.getStringSet("usedTweets", new HashSet<String>());
        oldSet.add(id);
        sp.edit().putStringSet("usedTweets", oldSet).apply();
    }

    private boolean worthyQuote(String text, String id) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MotivateMeSP", 0);
        Set<String> usedTweets = sp.getStringSet("usedTweets", new HashSet<String>());
        if (text.length() > 115 || text.length() < 15 ||
                usedTweets.contains(id) ||
                (text.contains("@") || text.contains("RT") || text.contains("http") || text.contains("//")))
            return false;
        return true;
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
