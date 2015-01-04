package se.rgson.da401a.bubblig.model;

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
		fetchContent(articleListener);
	}

	private void fetchContent(final ArticleListener articleListener) {
		if (mContent != null) {
			if (articleListener != null) {
				articleListener.onArticleLoaded(mContent);
			}
		}
		else {
			Readability.parse(getURL(), new ReadabilityListener() {
				@Override
				public void onSuccess(ReadabilityResponse response) {
					mContent = "<h1>" + response.getTitle() + "</h1>" + response.getContent();
					if (articleListener != null) {
						articleListener.onArticleLoaded(mContent);
					}
				}

				@Override
				public void onError() {
					Log.e(TAG, "Failed to fetch content for article " + mID);
					mContent = "";
					if (articleListener != null) {
						articleListener.onArticleLoaded(mContent);
					}
				}
			});
		}
	}

}
