package se.rgson.da401a.bubblig;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import se.rgson.da401a.bubblig.gui.ArticleFragment;
import se.rgson.da401a.bubblig.gui.ArticleListFragment;
import se.rgson.da401a.bubblig.gui.CategoryListFragment;
import se.rgson.da401a.bubblig.model.Article;
import se.rgson.da401a.bubblig.model.Category;


public class MainActivity extends Activity implements CategoryListFragment.CategoryListFragmentListener, ArticleListFragment.ArticleListFragmentListener {

	private static String TAG = MainActivity.class.getSimpleName();

	private boolean mTabletLayout = false;
	private float mDrawerOffset = 0.0f;
	private String mTitle;

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private CategoryListFragment mCategoryListFragment;
	private ArticleListFragment mArticleListFragment;
	private ArticleFragment mArticleFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mTabletLayout = (findViewById(R.id.article_container) != null);
		mTitle = getResources().getString(R.string.app_name);

		if (savedInstanceState == null) {

			mCategoryListFragment = CategoryListFragment.newInstance();
			mArticleListFragment = ArticleListFragment.newInstance();
			mArticleFragment = ArticleFragment.newInstance();

			if (!mTabletLayout) {
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

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View view) {
				getActionBar().setTitle(getResources().getString(R.string.app_name));
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				super.onDrawerSlide(drawerView, slideOffset);
				mDrawerOffset = slideOffset;
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		int alpha = (int) (255 * (1 - mDrawerOffset));
		for (int i = 0; i < menu.size(); i++) {
			MenuItem item = menu.getItem(i);
			if (item.getIcon() != null) {
				item.getIcon().setAlpha(alpha);
			}
			item.setVisible(mDrawerOffset < 1.0f);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void setTitle(CharSequence title) {
		super.setTitle(title);
		mTitle = (String) title;
	}

	@Override
	public void onCategorySelected(Category category) {
		if (!mTabletLayout) {
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
		if (!mTabletLayout) {
			getFragmentManager().beginTransaction()
					.replace(R.id.container, mArticleFragment)
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
					.commit();
		}
		mArticleFragment.setArticle(article);
	}
}
