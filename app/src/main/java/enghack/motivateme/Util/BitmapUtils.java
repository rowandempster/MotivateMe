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
        if(options == null){
            options = new BitmapFactory.Options();
        }
        InputStream iStream = context.getContentResolver().openInputStream(imageUri);
        return BitmapFactory.decodeStream(iStream, null, options);
    }
}
