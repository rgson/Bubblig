package se.rgson.da401a.bubblig.model.readability;

/**
 * Interface used for parsing callbacks.
 */
public interface ReadabilityListener {
	/**
	 * Called upon successful parsing of an article.
	 *
	 * @param response The parsed response.
	 */
	void onSuccess(ReadabilityResponse response);

	/**
	 * Called upon failure when parsing an article.
	 */
	void onError();
}