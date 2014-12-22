package se.rgson.da401a.bubblig.model;

import android.util.Log;

import java.util.ArrayList;
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

	private static final int PREFETCH_COUNT = 10;

	private List<Article> mArticles;

	@Override
	public String toString() {
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
	 * @param categoryListener The listener to receive a callback upon completion.
	 */
	public void getArticles(final CategoryListener categoryListener) {
		if (categoryListener == null) {
			throw new IllegalArgumentException("Argument must not be null.");
		}
		if (mArticles != null) {
			categoryListener.onCategoryLoaded(mArticles);
		} else {
			Bubbla.read(BubblaFeed.valueOf(this.name()), new BubblaListener() {
				@Override
				public void onSuccess(List<BubblaArticle> articles) {
					mArticles = new ArrayList<>(articles.size());
					for (BubblaArticle bubblaArticle : articles) {
						mArticles.add(new Article(bubblaArticle.getID(), bubblaArticle.getTitle(), bubblaArticle.getURL(), Category.this));
					}
					prefetchArticles(0);
					categoryListener.onCategoryLoaded(mArticles);
				}

				@Override
				public void onError(Exception e) {
					Log.e(TAG, "Failed to fetch articles for " + name());
				}
			});
		}
	}

	/**
	 * Checks if more articles should be pre-fetched.
	 *
	 * @param article The article that has been requested.
	 */
	public void prefetchAroundArticle(Article article) {
		int articleIndex = mArticles.indexOf(article);

		if (articleIndex == -1) {
			throw new RuntimeException("The given article is not in this category.");
		} else {
			prefetchArticles(articleIndex);
		}
	}

	/**
	 * Pre-fetches the content of articles surrounding the provided position.
	 *
	 * @param position The position to center pre-fetching on.
	 */
	private void prefetchArticles(int position) {
		int start = position - PREFETCH_COUNT;
		start = start < 0 ? 0 : start;

		int end = position + 1 + PREFETCH_COUNT;
		end = end > mArticles.size() ? mArticles.size() : end;

		for (int i = start; i < end; i++) {
			mArticles.get(i).fetch();
		}
	}

}
