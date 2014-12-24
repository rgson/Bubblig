package se.rgson.da401a.bubblig;

import android.app.Activity;
import android.os.Bundle;

import se.rgson.da401a.bubblig.gui.ArticleFragment;
import se.rgson.da401a.bubblig.gui.ArticleListFragment;
import se.rgson.da401a.bubblig.gui.CategoryListFragment;
import se.rgson.da401a.bubblig.model.Article;
import se.rgson.da401a.bubblig.model.Category;


public class MainActivity extends Activity implements CategoryListFragment.CategoryListFragmentListener, ArticleListFragment.ArticleListFragmentListener {

	private static String TAG = MainActivity.class.getSimpleName();

	private boolean tabletLayout = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tabletLayout = (findViewById(R.id.article_container) != null);

		if (savedInstanceState == null) {

			if (!tabletLayout) {
				getFragmentManager().beginTransaction()
						.add(R.id.drawer_container, CategoryListFragment.newInstance())
						.add(R.id.container, ArticleListFragment.newInstance())
						.commit();
			} else {
				getFragmentManager().beginTransaction()
						.add(R.id.drawer_container, CategoryListFragment.newInstance())
						.add(R.id.list_container, ArticleListFragment.newInstance())
						.add(R.id.article_container, ArticleFragment.newInstance())
						.commit();
			}

		}

	}

	@Override
	public void onArticleSelected(Article article) {
		//TODO implement
	}

	@Override
	public void onCategorySelected(Category category) {
		//TODO implement
	}
}
