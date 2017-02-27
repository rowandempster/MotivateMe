package enghack.motivateme.Models;

/**
 * Created by rowandempster on 2/22/17.
 */

public class QuoteDatabaseModel {
    private String text;
    private long tweetId;

    public QuoteDatabaseModel(String text, long tweetId) {
        this.text = text;
        this.tweetId = tweetId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }
}
