package enghack.motivateme.Util;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import enghack.motivateme.Constants;

/**
 * Created by itwasarainyday on 20/02/17.
 */

public class UserFontSize {

    private static String[] alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","x","w","y","z"};

    public static int getMaxFontSize(int width, int height, Context context) {
        int temp, wideLetter = 0;
        if (width > height) {
            temp = width;
            width = height;
            height = temp;
        }

        int maxSize = 60;
        float[] space = new float[1];

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTypeface(Typeface.createFromAsset(context.getAssets(), context.getSharedPreferences(Constants.MASTER_SP_KEY, 0).getString(Constants.TEXT_FONT_SP_KEY, "fonts/serif.ttf")));

        while(true) {
            wideLetter=0;
            paint.setTextSize(maxSize);

            for (int i = 0; i < 26; ++i) {
                paint.getTextWidths(alphabet[i], space);
                wideLetter += (int)space[0];
                Log.d(alphabet[i], Integer.toString((int)space[0]));
            }
            wideLetter = wideLetter / 26 + 1;

            int numberOfLines = (int)(height*0.70/ (maxSize + Constants.NEWLINE_BUFFER)); // height*0.70 is usable space on screen
            int charsPerLine = (int)((width*0.80 - Constants.WIDTH_BUFFER) / wideLetter);

            if (charsPerLine * numberOfLines < Constants.TWEET_LENGTH) {
                maxSize -= 1;
                break;
            } else {
                ++maxSize;
            }
        }
        return maxSize;
    }
}
