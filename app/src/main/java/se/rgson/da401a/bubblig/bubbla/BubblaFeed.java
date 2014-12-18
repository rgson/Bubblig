package se.rgson.da401a.bubblig.bubbla;

/**
 * Enum representing the available RSS feeds.
 */
public enum BubblaFeed {
	NYHETER		("http://bubb.la/rss/nyheter"),
	VÄRLDEN		("http://bubb.la/rss/varlden"),
	SVERIGE		("http://bubb.la/rss/sverige"),
	BLANDAT		("http://bubb.la/rss/blandat"),
	MEDIA		("http://bubb.la/rss/media"),
	POLITIK		("http://bubb.la/rss/politik"),
	OPINION		("http://bubb.la/rss/opinion"),
	EUROPA		("http://bubb.la/rss/europa"),
	USA			("http://bubb.la/rss/usa"),
	ASIEN		("http://bubb.la/rss/asien"),
	EKONOMI		("http://bubb.la/rss/ekonomi"),
	TEKNIK		("http://bubb.la/rss/teknik"),
	VETENSKAP	("http://bubb.la/rss/vetenskap");

	private final String url;

	BubblaFeed(String url) {
		this.url = url;
	}

	/**
	 * Gets the feed's URL.
	 * (Note: package-level visibility.)
	 *
	 * @return The feed's URL.
	 */
	String getURL() {
		return url;
	}

}