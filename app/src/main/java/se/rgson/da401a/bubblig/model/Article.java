package se.rgson.da401a.bubblig.model;

import android.util.Log;

import java.io.Serializable;

import se.rgson.da401a.bubblig.BubbligDB;
import se.rgson.da401a.bubblig.model.readability.Readability;
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
	 * @return The content of the article in HTML, or null if fetching failed.
	 */
	public String getContent() {
		if (mContent != null) {
			return mContent;
		}
		else {
			String content = BubbligDB.getInstance().loadArticleContent(getID());
			if (content != null) {
				mContent = content;
			}
			else {
				ReadabilityResponse response = Readability.parse(getURL());
				if (response != null) {
					mContent = "<h1>" + response.getTitle() + "</h1>" + response.getContent();
					BubbligDB.getInstance().saveArticleContent(this);
				}
				else {
					Log.e(TAG, "Failed to fetch content for article " + mID);
				}
			}
			return mContent;
		}
	}
}
