package enghack.motivateme.Util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by rowandempster on 2/26/17.
 */

public class DisplayUtils {

    public static WidthAndHeight getWidthAndHeight(int width, int height) {
        if (width > height) {
            int temp = height;
            height = width;
            width = temp;
        }

        return new WidthAndHeight(width, height);
    }

    public static WidthAndHeight getWidthAndHeight(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        return new WidthAndHeight(width, height);
    }

    public static class WidthAndHeight {
        public int width;
        public int height;

        public WidthAndHeight(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
}
