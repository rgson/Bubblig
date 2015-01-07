package se.rgson.da401a.bubblig.model.bubbla;

import android.util.Log;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * A static helper class for handling Bubb.la RSS requests.
 */
public class Bubbla {

	private static String TAG = Bubbla.class.getSimpleName();

	/**
	 * Private constructor to disallow instantiation (static class).
	 */
	private Bubbla() {
	}

	/**
	 * Reads a list of articles from the specified RSS feed.
	 *
	 * @param feed The feed to read.
	 * @return A list of BubblaArticles, or null if fetching failed.
	 */
	public static List<BubblaArticle> read(BubblaFeed feed) {
		if (feed == null) {
			throw new IllegalArgumentException("Feed must not be null.");
		}

		try {
			BubblaRSS rss = null;

			URL url = new URL(feed.getURL());
			URLConnection urlConnection = url.openConnection();
			InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

			try {
				Serializer serializer = new Persister();
				rss = serializer.read(BubblaRSS.class, inputStream);
			}
			finally {
				inputStream.close();
			}

			return rss.getChannel().getArticles();
		}
		catch (Exception e) {
			Log.e(TAG, e.toString() + " (" + feed.name() + ")");
			return null;
		}
	}

}
