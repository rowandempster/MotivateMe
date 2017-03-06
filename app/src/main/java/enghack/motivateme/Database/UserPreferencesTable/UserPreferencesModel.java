package enghack.motivateme.Database.UserPreferencesTable;

/**
 * Created by rowandempster on 2/27/17.
 */

public class UserPreferencesModel {
    private long refreshInterval;
    private int textColour;
    private String backgroundUri;
    private String textFont;
    private String quoteCategory;

    public UserPreferencesModel(long refreshInterval, int textColour, String backgroundUri, String textFont, String quoteCategory, int textSize) {
        this.refreshInterval = refreshInterval;
        this.textColour = textColour;
        this.backgroundUri = backgroundUri;
        this.textFont = textFont;
        this.quoteCategory = quoteCategory;
        this.textSize = textSize;
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

    private int textSize;

}
