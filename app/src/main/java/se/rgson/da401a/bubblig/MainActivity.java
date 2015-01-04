package se.rgson.da401a.bubblig;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import se.rgson.da401a.bubblig.gui.AboutFragment;
import se.rgson.da401a.bubblig.gui.ArticleListFragment;
import se.rgson.da401a.bubblig.gui.ArticlePagerFragment;
import se.rgson.da401a.bubblig.gui.CategoryListFragment;
import se.rgson.da401a.bubblig.gui.GuiUtility;
import se.rgson.da401a.bubblig.model.Article;
import se.rgson.da401a.bubblig.model.Category;


public class MainActivity extends Activity
		implements CategoryListFragment.CategoryListFragmentListener, ArticleListFragment.ArticleListFragmentListener, ArticlePagerFragment.ArticlePagerFragmentListener {

	private static String TAG = MainActivity.class.getSimpleName();

	private static String FRAGMENT_CATEGORY_LIST = "FRAGMENT_CATEGORY_LIST";
	private static String FRAGMENT_ARTICLE_LIST = "FRAGMENT_ARTICLE_LIST";
	private static String FRAGMENT_ARTICLE_PAGER = "FRAGMENT_ARTICLE_PAGER";
	private static String FRAGMENT_ABOUT = "FRAGMENT_ABOUT";

	private boolean mTabletLayout = false;
	private float mDrawerOffset = 0.0f;
	private Category mCategory;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mTabletLayout = (findViewById(R.id.article_container) != null);
		mCategory = Category.NYHETER;

		if (savedInstanceState == null) {
			int articleListContainerID = mTabletLayout ? R.id.list_container : R.id.container;
			getFragmentManager().beginTransaction()
					.add(R.id.drawer_container, CategoryListFragment.newInstance(), FRAGMENT_CATEGORY_LIST)
					.add(articleListContainerID, ArticleListFragment.newInstance(mCategory), FRAGMENT_ARTICLE_LIST)
					.commit();
		}

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mCategory.toString());
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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		int alpha = (int) (255 * (1 - mDrawerOffset));
		for (int i = 0; i < menu.size(); i++) {
			MenuItem item = menu.getItem(i);
			if (item.getItemId() == R.id.action_about) {
				continue;
			}
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

		switch (item.getItemId()) {
			case R.id.action_about:
				showAbout();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCategorySelected(Category category) {
		mCategory = category;
		getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		int articleListContainerID = mTabletLayout ? R.id.list_container : R.id.container;
		getFragmentManager().beginTransaction()
				.replace(articleListContainerID, ArticleListFragment.newInstance(mCategory), FRAGMENT_ARTICLE_LIST)
				.commit();
		mDrawerLayout.closeDrawers();
	}

	@Override
	public void onArticleSelected(Article article) {
		int articlePagerContainerID = mTabletLayout ? R.id.article_container : R.id.container;
		getFragmentManager().beginTransaction()
				.replace(articlePagerContainerID, ArticlePagerFragment.newInstance(mCategory, article), FRAGMENT_ARTICLE_PAGER)
				.addToBackStack(null)
				.commit();
	}

	@Override
	public void onDisplayedArticleChanged(Article article) {
		if (mTabletLayout) {
			//TODO Update ListView to match displayed article.
		}
	}

	private void showAbout() {
		AboutFragment.newInstance().show(getFragmentManager(), FRAGMENT_ABOUT);
	}
}
