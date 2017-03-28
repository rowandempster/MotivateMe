package enghack.motivateme.Database.UserPreferencesTable;

/**
 * Created by rowandempster on 2/27/17.
 */

public class UserPreferencesModel {
    private long refreshInterval;
    private int textColour;
    private String backgroundUri;
    private String textFont;
    private int textSize;

    public int getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(int textStyle) {
        this.textStyle = textStyle;
    }

    private int textStyle;
    private String quoteCategory;

    public UserPreferencesModel(long refreshInterval, int textColour, String backgroundUri, String textFont, int textSize, int textStyle, String quoteCategory) {
        this.refreshInterval = refreshInterval;
        this.textColour = textColour;
        this.backgroundUri = backgroundUri;
        this.textFont = textFont;
        this.textSize = textSize;
        this.textStyle = textStyle;
        this.quoteCategory = quoteCategory;
    }

    public int getTextSize() {

        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String getQuoteCategory() {
        return quoteCategory;
    }

    public void setQuoteCategory(String quoteCategory) {
        this.quoteCategory = quoteCategory;
    }

    public String getTextFont() {
        return textFont;
    }

    public void setTextFont(String textFont) {
        this.textFont = textFont;
    }

    public String getBackgroundUri() {
        return backgroundUri;
    }

    public void setBackgroundUri(String backgroundUri) {
        this.backgroundUri = backgroundUri;
    }

    public int getTextColour() {
        return textColour;
    }

    public void setTextColour(int textColour) {
        this.textColour = textColour;
    }

    public long getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(long refreshInterval) {
        this.refreshInterval = refreshInterval;
    }


}
