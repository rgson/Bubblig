package se.rgson.da401a.bubblig.gui.components;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.List;

import se.rgson.da401a.bubblig.model.Article;
import se.rgson.da401a.bubblig.model.Category;
import se.rgson.da401a.bubblig.model.CategoryListener;

public class ArticleListAdapter extends ArrayAdapter<Article> {

	private static final String TAG = ArticleListAdapter.class.getSimpleName();

	private Category mCategory;
	private SwipeRefreshLayout mSwipeRefreshLayout;

	public ArticleListAdapter(Context context, Category category) {
		super(context, android.R.layout.simple_list_item_1);
		setCategory(category);
	}

	public Category getCategory() {
		return mCategory;
	}

	public void setCategory(Category category) {
		if (category == null) {
			throw new IllegalArgumentException("Category must not be null.");
		}
		mCategory = category;
		refresh();
	}

	public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
		if (mSwipeRefreshLayout != null) {
			mSwipeRefreshLayout.setOnRefreshListener(null);
		}
		mSwipeRefreshLayout = swipeRefreshLayout;
		if (mSwipeRefreshLayout != null) {
			mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
				@Override
				public void onRefresh() {
					refresh();
				}
			});
		}
	}

	public void refresh() {
		if (mSwipeRefreshLayout != null) {
			mSwipeRefreshLayout.setRefreshing(true);
		}
		mCategory.getArticles(new CategoryListener() {
			@Override
			public void onCategoryLoaded(List<Article> articles) {
				ArticleListAdapter.this.clear();
				ArticleListAdapter.this.addAll(articles);
				if (mSwipeRefreshLayout != null) {
					mSwipeRefreshLayout.setRefreshing(false);
				}
			}
		});
	}

}
