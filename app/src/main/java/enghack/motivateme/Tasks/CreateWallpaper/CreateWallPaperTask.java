package enghack.motivateme.Tasks.CreateWallpaper;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;

import enghack.motivateme.Util.Constants;
import enghack.motivateme.Util.DisplayUtils;

/**
 * Created by rowandempster on 2/27/17.
 */

public class CreateWallPaperTask extends AsyncTask<CreateWallpaperParams, CreateWallpaperProgress, Void> {
    private CreateWallPaperTaskInterface _callback;
    private ProgressDialog _dialog;

    public CreateWallPaperTask(CreateWallPaperTaskInterface callback) {
        _callback = callback;
    }

    public void attachProgressDialog(ProgressDialog dialog){
        _dialog = dialog;
        initDialog();
    }
    private void initDialog() {
        _dialog.setCancelable(false);
        _dialog.setCanceledOnTouchOutside(false);
        _dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        _dialog.setIndeterminate(false);
        _dialog.setMessage("Creating your wallpaper...");
    }


    @Override
    protected Void doInBackground(CreateWallpaperParams... wallpaperParams) {
        CreateWallpaperParams params = wallpaperParams[0];
        createWallpaper(params.getScreenWidth(), params.getScreenHeight(), params.getQuote(), params.getTextSize(),
                params.getTextColour(), Typeface.create(params.getTextFont(), params.getTextStyle()), params.getBackground());
        return null;
    }

    @Override
    protected void onProgressUpdate(CreateWallpaperProgress... info) {
        double max = info[0].getMax();
        double progress = info[0].getProgress();
        if (progress == 0) {
            _callback.onStart((int) max);
            if(_dialog!= null) {
                _dialog.show();
            }
        } else {
            _callback.onProgress(info[0]);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        _callback.onFinishUiThread();
        if(_dialog != null) {
            _dialog.cancel();
        }
    }

    private Void createWallpaper(int width, int height, String quote, int textSize, int textColor, Typeface typeface, Bitmap background) {
        String[] words = getWords(quote);
        DisplayUtils.WidthAndHeight widthAndHeight = DisplayUtils.getWidthAndHeight(width, height);

        int textHeight = (int) (widthAndHeight.height * 0.05);

        Paint paint = getTextPaint(textSize, textColor, typeface);

        background = doMathAndGetBitmap(textSize, background, words, widthAndHeight, textHeight, paint);

        _callback.onFinishNonUiThread(background);
        if(_dialog != null) {
            _dialog.cancel();
        }

        return null;
    }

    private Bitmap doMathAndGetBitmap(int textSize, Bitmap background, String[] words, DisplayUtils.WidthAndHeight widthAndHeight, int textHeight, Paint paint) {
        String partialQuote;
        int currLineNum = 1;
        background = Bitmap.createScaledBitmap(background, widthAndHeight.width, widthAndHeight.height, true);
        int left = 0, right = words.length;
        while (left < right) {
            publishProgress(new CreateWallpaperProgress(left, right));
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
                if (currLineWidth >= widthAndHeight.width - Constants.WIDTH_BUFFER * 2 * currLineNum) {
                    currLineWidth -= currWordWidth;
                    break;
                }
                currLineWidth += getSpaceWidth(paint);
                partialQuote += words[i] + " ";
                ++wordsOnLine;
            }
            left = left + wordsOnLine;

            if (partialQuote.equals("")) {
                currLineNum = 1;
                continue;
            }
            Bitmap text = textAsBitmap(partialQuote, paint);

            background = combineImages(background, text, widthAndHeight.width, widthAndHeight.height, textHeight, widthAndHeight.width / 2 - currLineWidth / 2);

            textHeight += textSize + Constants.NEWLINE_BUFFER;
            ++currLineNum;
        }
        return background;
    }

    private int getSpaceWidth(Paint paint) {
        float[] space = new float[1];
        paint.getTextWidths(" ", space);
        return (int) space[0];
    }

    private Paint getTextPaint(int textSize, int textColor, Typeface typeface) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTypeface(typeface);
        paint.setTextAlign(Paint.Align.LEFT);
        return paint;
    }

    private String[] getWords(String quote) {
        return quote.split("\\s+");
    }

    private Bitmap combineImages(Bitmap background, Bitmap foreground, int width, int height, int vertStart, int horizStart) {
        Bitmap comboImage = Bitmap.createBitmap(width, height, background.getConfig());
        Canvas comboCanvas = new Canvas(comboImage);
        comboCanvas.drawBitmap(background, new Matrix(), null);
        comboCanvas.drawBitmap(foreground, horizStart, vertStart, null);

        return comboImage;
    }

    private Bitmap textAsBitmap(String text, Paint paint) {
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.5f);
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }
}
