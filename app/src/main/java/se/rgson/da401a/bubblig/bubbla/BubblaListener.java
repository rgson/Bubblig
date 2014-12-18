package se.rgson.da401a.bubblig.bubbla;


import java.util.List;

/**
 * Interface used for RSS parsing callbacks.
 */
public interface BubblaListener {
	/**
	 * Called upon successful parsing of an RSS feed.
	 *
	 * @param articles The parsed response.
	 */
	void onSuccess(List<BubblaArticle> articles);

	/**
	 * Called upon failure when parsing an RSS feed.
	 *
	 * @param e The exception that occurred during parsing.
	 */
	void onError(Exception e);
}