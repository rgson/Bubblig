package se.rgson.da401a.bubblig.gui.components;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import se.rgson.da401a.bubblig.gui.ArticleFragment;
import se.rgson.da401a.bubblig.model.Article;

public class ArticleFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

	private static String TAG = ArticleFragmentStatePagerAdapter.class.getSimpleName();

	private ArrayList<Article> mArticles;

	public ArticleFragmentStatePagerAdapter(FragmentManager fm, ArrayList<Article> articles) {
		super(fm);
		if (articles == null) {
			throw new IllegalArgumentException("Articles must not be null.");
		}
		mArticles = articles;
	}

	@Override
	public Fragment getItem(int position) {
		return ArticleFragment.newInstance(mArticles.get(position));
	}

	@Override
	public int getCount() {
		return mArticles.size();
	}

}
