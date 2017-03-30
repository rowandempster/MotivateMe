package enghack.motivateme.Tasks.CreateWallpaper;

/**
 * Created by rowandempster on 3/2/17.
 */

public class CreateWallpaperProgress {
    private int progress;
    private int max;

    public double getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public double getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public CreateWallpaperProgress(int progress, int max) {

        this.progress = progress;
        this.max = max;
    }
}
