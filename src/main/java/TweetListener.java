import twitter4j.Logger;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class TweetListener implements StatusListener {

	private static final Logger logger = Logger.getLogger(TweetListener.class);
	private static final Producer producer = new Producer();

	@Override
	public void onStatus(Status status) {
		Tweet tweet = new Tweet(status.getUser().getName(), status.getText(), status.getCreatedAt());
		producer.SendMessage("my-topic", tweet);
		logger.info("New Tweet: " + tweet.toString() + "\n\n");
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		logger.info("delete");
		producer.Close();
	}

	@Override
	public void onTrackLimitationNotice(int i) {
		logger.info("track");
	}

	@Override
	public void onScrubGeo(long l, long l1) {

	}

	@Override
	public void onStallWarning(StallWarning stallWarning) {
		logger.info("stall");
	}

	@Override
	public void onException(Exception e) {
		logger.info("exception:\n" + e.getStackTrace().toString() + "\n\n");
		e.printStackTrace();
	}
}
