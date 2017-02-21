package enghack.motivateme.Util;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;

import enghack.motivateme.Constants;

/**
 * Created by itwasarainyday on 20/02/17.
 */

public class UserFontSize {

    private static String[] alphabet = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

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

            paint.setTextSize(maxSize);

            for (int i = 0; i < 26; ++i) {
                paint.getTextWidths(alphabet[i], space);
                if ((int) space[0] > wideLetter) wideLetter = (int)space[0];
            }

            int numberOfLines = height / (maxSize + Constants.NEWLINE_BUFFER);
            int charsPerLine = (width - Constants.WIDTH_BUFFER) / wideLetter;

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
