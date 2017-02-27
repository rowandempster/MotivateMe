package enghack.motivateme;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by rowandempster on 2/22/17.
 */

public class TwitterInstance {
    private static Twitter _instance;

    public static Twitter getInstance(){
        if(_instance == null){
            _instance = buildTwitter();
        }
        return _instance;
    }

    private static Twitter buildTwitter() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("vaZKWtXdTQCWhPFjiNAaJwuMz")
                .setOAuthConsumerSecret("KQzAKgUC1Y31YRH2AvuHCSZPa83fq42tLgnn080UtMDo5KjMPp")
                .setOAuthAccessToken("828006071377788929-YAmu75Lxyb8KzQ8MnNxiPQbJPhsrlgL")
                .setOAuthAccessTokenSecret("X4Te52AsnPktMCABDsNyGgevHPxDDZKrylDWdC1YeE7FT");
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();
        // set this with twitter result
    }
}
