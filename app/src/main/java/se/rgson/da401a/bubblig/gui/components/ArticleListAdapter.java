package se.rgson.da401a.bubblig.gui.components;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import se.rgson.da401a.bubblig.model.Article;
import se.rgson.da401a.bubblig.model.Category;
import se.rgson.da401a.bubblig.model.CategoryListener;

public class ArticleListAdapter extends ArrayAdapter<Article> {

	private static final String TAG = ArticleListAdapter.class.getSimpleName();

	private Category mCategory;
	private ArticleListAdapterListener mListener;

	public ArticleListAdapter(Context context, Category category, ArticleListAdapterListener listener) {
		super(context, android.R.layout.simple_list_item_1);
		if (category == null) {
			throw new IllegalArgumentException("Category must not be null.");
		}
		mCategory = category;
		mListener = listener;
		refresh();
	}

	public void refresh() {
		if (mListener != null) {
			mListener.isRefreshing(true);
		}
		mCategory.invalidate();
		mCategory.getArticles(new CategoryListener() {
			@Override
			public void onCategoryLoaded(ArrayList<Article> articles) {
				ArticleListAdapter.this.clear();
				ArticleListAdapter.this.addAll(articles);
				if (mListener != null) {
					mListener.isRefreshing(false);
				}
			}
		});
	}

	public interface ArticleListAdapterListener {
		void isRefreshing(boolean refreshing);
	}

}
