package se.rgson.da401a.bubblig.model.bubbla;

import android.os.AsyncTask;
import android.util.Log;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
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
	 * The articles are returned through the supplied BubblaListener upon completion.
	 *
	 * @param feed     The feed to read.
	 * @param listener The BubblaListener containing the callback methods.
	 */
	public static void read(BubblaFeed feed, BubblaListener listener) {
		if (feed == null || listener == null) {
			throw new IllegalArgumentException("Arguments must not be null.");
		}
		new AsyncReader().execute(feed, listener);
	}

	/**
	 * Performs the reading as an AsyncTask.
	 */
	private static class AsyncReader extends AsyncTask<Object, Void, List<BubblaArticle>> {
		private BubblaListener listener;

		@Override
		protected List<BubblaArticle> doInBackground(Object... params) {
			BubblaFeed feed = (BubblaFeed) params[0];
			listener = (BubblaListener) params[1];

			try {
				BubblaRSS rss = null;

				URL url = new URL(feed.getURL());
				URLConnection urlConnection = url.openConnection();
				InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

				try {
					Serializer serializer = new Persister();
					rss = serializer.read(BubblaRSS.class, inputStream);
				} finally {
					inputStream.close();
				}

				return rss.getChannel().getArticles();

			} catch (Exception e) {
				if (e.getMessage() != null) {
					Log.e(TAG, e.getMessage() + " (" + feed.name() + ")");
				} else {
					Log.e(TAG, e.toString());
				}
				listener.onError(e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(List<BubblaArticle> result) {
			super.onPostExecute(result);
			listener.onSuccess(Collections.unmodifiableList(result));
		}
	}

}
