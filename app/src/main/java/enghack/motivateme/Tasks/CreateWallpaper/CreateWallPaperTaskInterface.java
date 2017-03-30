package enghack.motivateme.Tasks.CreateWallpaper;

import android.graphics.Bitmap;

/**
 * Created by rowandempster on 3/2/17.
 */

public interface CreateWallPaperTaskInterface {
    void onStart(int duration);
    void onProgress(CreateWallpaperProgress progress);
    void onFinishNonUiThread(Bitmap wallpaper);
    void onFinishUiThread();
}
