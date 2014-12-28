package se.rgson.da401a.bubblig.model;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;

import java.io.Serializable;

import se.rgson.da401a.bubblig.model.readability.Readability;
import se.rgson.da401a.bubblig.model.readability.ReadabilityListener;
import se.rgson.da401a.bubblig.model.readability.ReadabilityResponse;

public class Article implements Comparable<Article>, Serializable {

	private static final String TAG = Article.class.getSimpleName();

	private int mID;
	private String mTitle;
	private String mURL;
	private Category mCategory;
	private String mContent;

	Article(int id, String title, String url, Category category) {
		mID = id;
		mTitle = title;
		mURL = url;
		mCategory = category;
	}

	public int getID() {
		return mID;
	}

	public String getTitle() {
		return mTitle;
	}

	public String getURL() {
		return mURL;
	}

	public Category getCategory() {
		return mCategory;
	}

	@Override
	public String toString() {
		return mTitle;
	}

	@Override
	public int compareTo(Article another) {
		return mID - another.mID;
	}

	/**
	 * Gets the content of the article.
	 *
	 * @param articleListener
	 */
	public void getContent(final ArticleListener articleListener) {
		if (articleListener == null) {
			throw new IllegalArgumentException("Argument must not be null.");
		}
		if (mContent != null) {
			articleListener.onArticleLoaded(Html.fromHtml(mContent));
		} else {
			Readability.parse(getURL(), new ReadabilityListener() {
				@Override
				public void onSuccess(ReadabilityResponse response) {
					mContent = prepareContent(response);
					articleListener.onArticleLoaded(Html.fromHtml(mContent));
				}

				@Override
				public void onError() {
					Log.e(TAG, "Failed to fetch content for article " + mID);
				}
			});
		}
		mCategory.prefetchAroundArticle(this);
	}

	/**
	 * Fetches the article.
	 * Does nothing if the article has already been fetched.
	 */
	void fetch() {
		if (mContent == null) {
			Readability.parse(mURL, new ReadabilityListener() {
				@Override
				public void onSuccess(ReadabilityResponse response) {
					mContent = prepareContent(response);
				}

				@Override
				public void onError() {
					Log.e(TAG, "Failed to fetch content for article " + mID);
					mContent = "<h1>Failed to fetch content for article.</h1><p>Please visit the original source by pressing the icon on the action bar.</p>";
				}
			});
		}
	}

	private String prepareContent(ReadabilityResponse response) {
		return ("<h1>" + response.getTitle() + "</h1>" + response.getContent())
				.replaceAll("<img.+?>", "");
	}
}
