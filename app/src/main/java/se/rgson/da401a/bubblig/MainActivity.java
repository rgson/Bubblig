package se.rgson.da401a.bubblig;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import se.rgson.da401a.bubblig.gui.ArticleFragment;
import se.rgson.da401a.bubblig.gui.ArticleListFragment;
import se.rgson.da401a.bubblig.gui.CategoryListFragment;
import se.rgson.da401a.bubblig.model.Article;
import se.rgson.da401a.bubblig.model.Category;


public class MainActivity extends Activity implements CategoryListFragment.CategoryListFragmentListener, ArticleListFragment.ArticleListFragmentListener {

	private static String TAG = MainActivity.class.getSimpleName();

	private boolean tabletLayout = false;
	private DrawerLayout mDrawerLayout;
	private CategoryListFragment mCategoryListFragment;
	private ArticleListFragment mArticleListFragment;
	private ArticleFragment mArticleFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		tabletLayout = (findViewById(R.id.article_container) != null);

		if (savedInstanceState == null) {

			mCategoryListFragment = CategoryListFragment.newInstance();
			mArticleListFragment = ArticleListFragment.newInstance();
			mArticleFragment = ArticleFragment.newInstance();

			if (!tabletLayout) {
				getFragmentManager().beginTransaction()
						.add(R.id.drawer_container, mCategoryListFragment)
						.add(R.id.container, mArticleListFragment)
						.commit();
			} else {
				getFragmentManager().beginTransaction()
						.add(R.id.drawer_container, mCategoryListFragment)
						.add(R.id.list_container, mArticleListFragment)
						.add(R.id.article_container, mArticleFragment)
						.commit();
			}

		}

	}

	@Override
	public void onCategorySelected(Category category) {
		if (!tabletLayout) {
			getFragmentManager().beginTransaction()
					.replace(R.id.container, mArticleListFragment)
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
					.commit();
		}
		mArticleListFragment.setCategory(category);
		setTitle(category.toString());
		mDrawerLayout.closeDrawers();
	}

	@Override
	public void onArticleSelected(Article article) {
		if (!tabletLayout) {
			getFragmentManager().beginTransaction()
					.replace(R.id.container, mArticleFragment)
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
					.commit();
		}
		mArticleFragment.setArticle(article);
	}
}
