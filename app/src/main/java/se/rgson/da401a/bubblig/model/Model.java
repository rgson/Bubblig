package se.rgson.da401a.bubblig.model;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import se.rgson.da401a.bubblig.model.bubbla.Bubbla;
import se.rgson.da401a.bubblig.model.bubbla.BubblaArticle;
import se.rgson.da401a.bubblig.model.bubbla.BubblaFeed;
import se.rgson.da401a.bubblig.model.bubbla.BubblaListener;
import se.rgson.da401a.bubblig.model.readability.Readability;
import se.rgson.da401a.bubblig.model.readability.ReadabilityListener;
import se.rgson.da401a.bubblig.model.readability.ReadabilityResponse;

/**
 * The data model of the Bubblig app.
 * Handles the fetching of data, loading of articles and caching of content.
 */
public class Model {

	private static String TAG = Model.class.getSimpleName();

	private static final int PREFETCH_INITIAL = 10;
	private static final int PREFETCH_FOLLOWING = 20;
	private static final int PREFETCH_MARGIN = 5;

	private static Map<BubblaFeed, List<BubblaArticle>> feeds = new EnumMap<>(BubblaFeed.class);
	private static Map<BubblaArticle, ReadabilityResponse> articles = new TreeMap<>();

	/**
	 * Private constructor to disallow instantiation (static class).
	 */
	private Model() {
	}

	/**
	 * Gets information about the provided feed, and pre-fetches some articles if necessary.
	 *
	 * @param feed             The feed to fetch.
	 * @param categoryListener A listener for receiving the result upon completion.
	 */
	public static void get(final BubblaFeed feed, final ModelCategoryListener categoryListener) {
		if (feed == null || categoryListener == null) {
			throw new IllegalArgumentException("Arguments must not be null.");
		}
		if (feeds.containsKey(feed)) {
			List<BubblaArticle> bubblaArticles;
			synchronized (feeds) {
				bubblaArticles = feeds.get(feed);
			}
			categoryListener.onCategoryLoaded(bubblaArticles);
		} else {
			Bubbla.read(feed, new BubblaListener() {
				@Override
				public void onSuccess(List<BubblaArticle> articles) {
					feeds.put(feed, articles);
					prefetchArticles(articles.subList(0, PREFETCH_INITIAL));
					categoryListener.onCategoryLoaded(feeds.get(feed));
				}

				@Override
				public void onError(Exception e) {

				}
			});
		}
	}

	/**
	 * Gets the content of the provided article.
	 *
	 * @param article         The article to fetch.
	 * @param feed            The feed to which the article belongs.
	 * @param articleListener A listener for receiving the result upon completion.
	 */
	public static void get(final BubblaArticle article, final BubblaFeed feed, final ModelArticleListener articleListener) {
		if (article == null || articleListener == null) {
			throw new IllegalArgumentException("Arguments must not be null.");
		}
		if (articles.containsKey(article)) {
			ReadabilityResponse articleContent;
			synchronized (articles) {
				articleContent = articles.get(article);
			}
			articleListener.onArticleLoaded(articleContent);
			performPotentialPrefetch(feed, article);
		} else {
			Readability.parse(article.getURL(), new ReadabilityListener() {
				@Override
				public void onSuccess(ReadabilityResponse response) {
					articles.put(article, response);
					performPotentialPrefetch(feed, article);
					articleListener.onArticleLoaded(response);
				}

				@Override
				public void onError(Exception e) {

				}
			});
		}
	}

	/**
	 * Preloads the articles from the provided list.
	 *
	 * @param bubblaArticles The list of articles.
	 */
	private static void prefetchArticles(List<BubblaArticle> bubblaArticles) {
		for (final BubblaArticle bubblaArticle : bubblaArticles) {
			if (articles.containsKey(bubblaArticle)) {
				continue;
			}
			Readability.parse(bubblaArticle.getURL(), new ReadabilityListener() {
				@Override
				public void onSuccess(ReadabilityResponse response) {
					articles.put(bubblaArticle, response);
				}

				@Override
				public void onError(Exception e) {
				}
			});
		}
	}

	private static void performPotentialPrefetch(BubblaFeed feed, BubblaArticle article) {
		List<BubblaArticle> articles = feeds.get(feed);
		int articleIndex = articles.indexOf(article);
		int numberOfPrefetched = numberOfPrefetchedArticles(feed);
		if (numberOfPrefetched - articleIndex < PREFETCH_MARGIN) {
			int start = numberOfPrefetched;
			int end = numberOfPrefetched + PREFETCH_FOLLOWING;
			if (end > articles.size()) {
				end = articles.size();
			}
			if (start != end) {
				prefetchArticles(articles.subList(start, end));
			}
		}
	}

	private static int numberOfPrefetchedArticles(BubblaFeed feed) {
		List<BubblaArticle> articleList = feeds.get(feed);
		for (int i = 0; i < articleList.size(); i++) {
			if (!articles.containsKey(articleList.get(i))) {
				return i;
			}
		}
		return articleList.size();
	}

}
