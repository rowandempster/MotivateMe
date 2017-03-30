package enghack.motivateme.Tasks.PullTweets;

/**
 * Created by rowandempster on 2/22/17.
 */

public class PullTweetsParams {
    private String _category;
    private int _numTweetsToGet;

    public String getCategory() {
        return _category;
    }

    public int getNumTweetsToGet() {
        return _numTweetsToGet;
    }

    public PullTweetsParams(String category, int numToGet){
        _category = category;
        _numTweetsToGet = numToGet;
    }
}
