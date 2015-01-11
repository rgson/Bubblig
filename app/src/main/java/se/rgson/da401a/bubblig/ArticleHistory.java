package se.rgson.da401a.bubblig;

import java.util.ArrayList;
import java.util.HashSet;

import se.rgson.da401a.bubblig.model.Article;

public class ArticleHistory {

	private static final String TAG = ArticleHistory.class.getSimpleName();

	private static HashSet<Integer> mHistoryCache = BubbligDB.getInstance().loadArticleHistory();
	private static ArrayList<ArticleHistoryListener> mListeners = new ArrayList<>();

	private ArticleHistory() {
	}

	public static void add(Article article) {
		BubbligDB.getInstance().saveArticleHistory(article.getID());
		mHistoryCache.add(article.getID());
		for (ArticleHistoryListener listener : mListeners) {
			listener.hasReadArticle(article);
		}
	}

	public static boolean contains(Article article) {
		return mHistoryCache.contains(article.getID());
	}

	public static void attachArticleHistoryListener(ArticleHistoryListener listener) {
		if (mListeners != null) {
			mListeners.add(listener);
		}
	}

	public static void detachArticleHistoryListener(ArticleHistoryListener listener) {
		if (mListeners != null) {
			mListeners.remove(listener);
		}
	}

	public interface ArticleHistoryListener {
		void hasReadArticle(Article article);
	}

}
