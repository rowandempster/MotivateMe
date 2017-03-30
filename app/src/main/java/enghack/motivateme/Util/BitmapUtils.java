package enghack.motivateme.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by rowandempster on 3/4/17.
 */

public class BitmapUtils {
    public static Bitmap decodeSampledBitmapFromUri(Context context, Uri imageUri, BitmapFactory.Options options) throws FileNotFoundException {
        if (options == null) {
            options = new BitmapFactory.Options();
        }
        InputStream iStream = context.getContentResolver().openInputStream(imageUri);
        return BitmapFactory.decodeStream(iStream, null, options);
    }

    public static int calculateSampleSize(Context context, Uri bitmap) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream iStream = null;
        try {
            iStream = context.getContentResolver().openInputStream(bitmap);
        } catch (FileNotFoundException e) {
            return 1;
        }
        BitmapFactory.decodeStream(iStream, null, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        if(imageHeight < imageWidth){
            int temp = imageHeight;
            imageHeight = imageWidth;
            imageWidth = temp;
        }
        DisplayUtils.WidthAndHeight dimens = DisplayUtils.getWidthAndHeight(context);
        int scaledHeight = imageHeight/dimens.height;
        int scaledWidth = imageWidth/dimens.width;
        return Math.min(scaledHeight, scaledWidth);
    }
}
