package se.rgson.da401a.bubblig.gui.components;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.List;

import se.rgson.da401a.bubblig.model.Article;
import se.rgson.da401a.bubblig.model.Category;
import se.rgson.da401a.bubblig.model.CategoryListener;

public class ArticleListAdapter extends ArrayAdapter<Article> {

	private static final String TAG = ArticleListAdapter.class.getSimpleName();

	private Category mCategory;

	public ArticleListAdapter(Context context, Category category) {
		super(context, android.R.layout.simple_list_item_1);
		setCategory(category);
	}

	public void setCategory(Category category) {
		if (category == null) {
			throw new IllegalArgumentException("Category must not be null.");
		}
		mCategory = category;
		mCategory.getArticles(new CategoryListener() {
			@Override
			public void onCategoryLoaded(List<Article> articles) {
				ArticleListAdapter.this.addAll(articles);
			}
		});
	}

	public Category getCategory() {
		return mCategory;
	}
}
