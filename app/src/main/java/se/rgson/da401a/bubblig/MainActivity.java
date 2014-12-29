package se.rgson.da401a.bubblig;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import se.rgson.da401a.bubblig.gui.ArticleListFragment;
import se.rgson.da401a.bubblig.gui.ArticlePagerFragment;
import se.rgson.da401a.bubblig.gui.CategoryListFragment;
import se.rgson.da401a.bubblig.gui.components.AboutFragment;
import se.rgson.da401a.bubblig.model.Article;
import se.rgson.da401a.bubblig.model.Category;


public class MainActivity extends Activity implements
		CategoryListFragment.CategoryListFragmentListener, ArticleListFragment.ArticleListFragmentListener,
		ArticlePagerFragment.ArticlePagerFragmentListener {

	private static String TAG = MainActivity.class.getSimpleName();

	private Button gotoAbout;

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
					.add(R.id.drawer_container, CategoryListFragment.newInstance())
					.add(articleListContainerID, ArticleListFragment.newInstance(mCategory))
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
		int articleListContainerID = mTabletLayout ? R.id.list_container : R.id.container;
		getFragmentManager().beginTransaction()
				.replace(articleListContainerID, ArticleListFragment.newInstance(mCategory))
				.commit();
		setTitle(mCategory.toString());
		mDrawerLayout.closeDrawers();
	}

	@Override
	public void onArticleSelected(Article article) {
		int articlePagerContainerID = mTabletLayout ? R.id.article_container : R.id.container;
		getFragmentManager().beginTransaction()
				.replace(articlePagerContainerID, ArticlePagerFragment.newInstance(mCategory, article))
				.commit();
	}

	@Override
	public void onDisplayedArticleChanged(Article article) {
		if (mTabletLayout) {
			// Update ListView to match displayed article.
		}
	}

	private void showAbout() {
		AboutFragment.newInstance().show(getFragmentManager(), "about");
	}
}
