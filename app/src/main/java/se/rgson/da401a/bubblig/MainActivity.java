package se.rgson.da401a.bubblig;

import android.app.Activity;
import android.os.Bundle;

import se.rgson.da401a.bubblig.gui.ArticleFragment;
import se.rgson.da401a.bubblig.gui.ArticleListFragment;
import se.rgson.da401a.bubblig.gui.CategoryListFragment;


public class MainActivity extends Activity {

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

}
