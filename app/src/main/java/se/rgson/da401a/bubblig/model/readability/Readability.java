package se.rgson.da401a.bubblig.model.readability;

import android.util.Log;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * A static helper class for handling Readability API requests.
 */
public class Readability {

	private static final String TAG = Readability.class.getSimpleName();

	private static final String BASE_URL = "http://bubblig.rgson.se/readability.php?url=";

	/**
	 * Private constructor to disallow instantiation (static class).
	 */
	private Readability() {
	}

	/**
	 * Parses the content of the article at the supplied URL.
	 * The parsed article content is returned through the supplied ReadabilityListener upon completion.
	 *
	 * @param articleUrl The article's URL.
	 * @return A ReadabilityResponse containing the parsed article, or null if fetching failed.
	 */
	public static ReadabilityResponse parse(String articleUrl) {
		if (articleUrl == null) {
			throw new IllegalArgumentException("Article URL must not be null.");
		}

		try {
			ReadabilityResponse response = null;

			URL url = new URL(BASE_URL + articleUrl);
			URLConnection urlConnection = url.openConnection();
			InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

			try {
				Serializer serializer = new Persister();
				response = serializer.read(ReadabilityResponse.class, inputStream);
			}
			finally {
				inputStream.close();
			}

			return response;

		}
		catch (Exception e) {
			Log.e(TAG, e.getMessage() + " (" + articleUrl + ")");
			return null;
		}
	}

}
