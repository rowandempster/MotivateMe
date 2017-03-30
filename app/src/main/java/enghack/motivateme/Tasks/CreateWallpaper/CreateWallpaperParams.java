package enghack.motivateme.Tasks.CreateWallpaper;

import android.graphics.Bitmap;
import android.graphics.Typeface;

/**
 * Created by rowandempster on 2/27/17.
 */

public class CreateWallpaperParams {
    private int screenWidth;
    private int screenHeight;
    private String quote;
    private int textSize;
    private int textStyle;
    private int textColour;
    private Typeface textFont;
    private Bitmap background;

    public int getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(int textStyle) {
        this.textStyle = textStyle;
    }

    public CreateWallpaperParams(int screenWidth, int screenHeight, String quote, int textSize, int textColour, Typeface textFont, Bitmap background, int textStyle) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.quote = quote;
        this.textSize = textSize;
        this.textColour = textColour;
        this.textFont = textFont;
        this.background = background;
        this.textStyle = textStyle;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextColour() {
        return textColour;
    }

    public void setTextColour(int textColour) {
        this.textColour = textColour;
    }

    public Typeface getTextFont() {
        return textFont;
    }

    public void setTextFont(Typeface textFont) {
        this.textFont = textFont;
    }

    public Bitmap getBackground() {
        return background;
    }

    public void setBackground(Bitmap background) {
        this.background = background;
    }
}
