import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.Serializable;

public class TwitterLifecycleManager implements LifecycleManager, Serializable {

	private static final Logger logger = Logger.getLogger(TwitterLifecycleManager.class);
	private static final String _consumerKey = System.getenv().get("CONSUMER_KEY");
	private static final String _consumerSecret = System.getenv().get("CONSUMER_SECRET");
	private static final String _accessToken = System.getenv().get("ACCESS_TOKEN");
	private static final String _accessTokenSecret = System.getenv().get("ACCESS_TOKEN_SECRET");

//	private static final Twitter twitterInstance = getTwitterInstance();
	private static final TwitterStream twitterStream = getTwitterStreamInstance();

//	public static Twitter getTwitterInstance() {
//		ConfigurationBuilder cb = new ConfigurationBuilder();
//		cb.setDebugEnabled(true)
//				.setOAuthConsumerKey(_consumerKey)
//				.setOAuthConsumerSecret(_consumerSecret)
//				.setOAuthAccessToken(_accessToken)
//				.setOAuthAccessTokenSecret(_accessTokenSecret);
//
//		TwitterFactory tf = new TwitterFactory(cb.build());
//		return tf.getInstance();
//	}

	public static TwitterStream getTwitterStreamInstance() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey(_consumerKey)
				.setOAuthConsumerSecret(_consumerSecret)
				.setOAuthAccessToken(_accessToken)
				.setOAuthAccessTokenSecret(_accessTokenSecret);

		TwitterStreamFactory tf = new TwitterStreamFactory(cb.build());
		return tf.getInstance();
	}

	@Override
	public void start() {
		logger.info("Starting Stream...");
		twitterStream.addListener(new TweetListener());
		twitterStream.filter(getFilter());
		logger.info("Stream created! " + twitterStream.toString());
	}

	@Override
	public void stop() {
		logger.info("Stopping stream...");
		twitterStream.cleanUp();
		twitterStream.clearListeners();
		logger.info("Stream Stopped...");
	}

	private FilterQuery getFilter() {
		String trackedTerms = System.getenv().get("TWITTER_TRACKED_TERMS");
		FilterQuery query = new FilterQuery();
		query.track(trackedTerms.split(","));
		return query;
	}
}
