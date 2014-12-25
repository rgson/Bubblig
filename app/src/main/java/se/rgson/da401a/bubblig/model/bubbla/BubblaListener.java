package se.rgson.da401a.bubblig.model.bubbla;


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
	 */
	void onError();
}