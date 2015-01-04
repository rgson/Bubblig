package se.rgson.da401a.bubblig.gui.components;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v13.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import se.rgson.da401a.bubblig.gui.ArticleFragment;
import se.rgson.da401a.bubblig.model.Article;

public class ArticleFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

	private static String TAG = ArticleFragmentStatePagerAdapter.class.getSimpleName();

	private static final String BUNDLE_SUPER = "BUNDLE_SUPER";
	private static final String BUNDLE_ARTICLES = "BUNDLE_ARTICLES";

	private ArrayList<Article> mArticles;

	public ArticleFragmentStatePagerAdapter(FragmentManager fm, List<Article> articles) {
		super(fm);
		if (articles == null) {
			throw new IllegalArgumentException("Articles must not be null.");
		}
		mArticles = new ArrayList<>(articles);
	}

	@Override
	public Fragment getItem(int position) {
		return ArticleFragment.newInstance(mArticles.get(position));
	}

	@Override
	public int getCount() {
		return mArticles.size();
	}

	@Override
	public Parcelable saveState() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(BUNDLE_SUPER, super.saveState());
		bundle.putSerializable(BUNDLE_ARTICLES, mArticles);
		return bundle;
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
		Bundle bundle = (Bundle) state;
		super.restoreState(bundle.getParcelable(BUNDLE_SUPER), loader);
		mArticles = (ArrayList<Article>) bundle.getSerializable(BUNDLE_ARTICLES);
	}

	public Article getArticle(int position) {
		return mArticles.get(position);
	}
}
