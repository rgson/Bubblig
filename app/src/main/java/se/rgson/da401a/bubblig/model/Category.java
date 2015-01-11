package se.rgson.da401a.bubblig.model;

import android.util.Log;

import org.unbescape.html.HtmlEscape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.rgson.da401a.bubblig.model.bubbla.Bubbla;
import se.rgson.da401a.bubblig.model.bubbla.BubblaArticle;
import se.rgson.da401a.bubblig.model.bubbla.BubblaFeed;
import se.rgson.da401a.bubblig.model.bubbla.BubblaListener;

public enum Category {

	NYHETER,
	VÄRLDEN,
	SVERIGE,
	BLANDAT,
	MEDIA,
	POLITIK,
	OPINION,
	EUROPA,
	USA,
	ASIEN,
	EKONOMI,
	TEKNIK,
	VETENSKAP;

	private static final String TAG = Category.class.getSimpleName();

	private List<Article> mArticles;

	@Override
	public String toString() {
		if (this == USA) {
			return name();
		}
		return name().substring(0, 1) + name().substring(1).toLowerCase();
	}

	/**
	 * Gets a list of all the available categories.
	 *
	 * @return A list of all the categories.
	 */
	public static Category[] getCategories() {
		return Category.values();
	}

	/**
	 * Gets the articles in this category.
	 *
	 * @return An unmodifiable list of articles.
	 */
	public List<Article> getArticles() {
		if (mArticles == null) {
			List<BubblaArticle> bubblaArticles = Bubbla.read(BubblaFeed.valueOf(this.name()));
			if (bubblaArticles != null) {
				mArticles = new ArrayList<>(bubblaArticles.size());
				for (BubblaArticle bubblaArticle : bubblaArticles) {
					mArticles.add(new Article(bubblaArticle.getID(), HtmlEscape.unescapeHtml(bubblaArticle.getTitle()), bubblaArticle.getURL(), Category.this));
				}
				mArticles = Collections.unmodifiableList(mArticles);
			}
			else {
				Log.e(TAG, "Failed to fetch articles for " + name());
			}
		}
		return mArticles;
	}

	/**
	 * Invalidates the cached article list.
	 */
	public void invalidate() {
		mArticles = null;
	}
}
