package se.rgson.da401a.bubblig.gui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import se.rgson.da401a.bubblig.R;
import se.rgson.da401a.bubblig.gui.components.ArticleFragmentStatePagerAdapter;
import se.rgson.da401a.bubblig.model.Article;
import se.rgson.da401a.bubblig.model.Category;
import se.rgson.da401a.bubblig.model.CategoryListener;

public class ArticlePagerFragment extends Fragment {

	private static final String TAG = ArticlePagerFragment.class.getSimpleName();
	private static final String BUNDLE_CATEGORY = "BUNDLE_CATEGORY";
	private static final String BUNDLE_ARTICLE = "BUNDLE_ARTICLE";
	private static final String BUNDLE_CURRENT = "BUNDLE_CURRENT";
	private static final String BUNDLE_ADAPTER = "BUNDLE_ADAPTER";

	private ArticlePagerFragmentListener mListener;
	private ViewPager mViewPager;
	private ArticleFragmentStatePagerAdapter mAdapter;
	private Category mCategory;

	public static ArticlePagerFragment newInstance(Category category, Article article) {
		ArticlePagerFragment fragment = new ArticlePagerFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(BUNDLE_CATEGORY, category);
		bundle.putSerializable(BUNDLE_ARTICLE, article);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (savedInstanceState != null) {
			mCategory = (Category) savedInstanceState.getSerializable(BUNDLE_CATEGORY);
		}
		else if (getArguments() != null) {
			mCategory = (Category) getArguments().getSerializable(BUNDLE_CATEGORY);
		}
		else {
			throw new IllegalArgumentException("A category must be provided.");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_article_pager, container, false);

		mViewPager = (ViewPager) root.findViewById(R.id.article_view_pager);
		//mViewPager.setOffscreenPageLimit(2);

		if (savedInstanceState != null) {
			mAdapter = new ArticleFragmentStatePagerAdapter(getFragmentManager(), new ArrayList<Article>());
			mAdapter.restoreState(savedInstanceState.getParcelable(BUNDLE_ADAPTER), getActivity().getClassLoader());
			mViewPager.setAdapter(mAdapter);
			mViewPager.setCurrentItem(savedInstanceState.getInt(BUNDLE_CURRENT), false);
		}
		else {
			mCategory.getArticles(new CategoryListener() {
				@Override
				public void onCategoryLoaded(ArrayList<Article> articles) {
					mAdapter = new ArticleFragmentStatePagerAdapter(getFragmentManager(), articles);
					mViewPager.setAdapter(mAdapter);
					Article currentArticle = (Article) getArguments().getSerializable(BUNDLE_ARTICLE);
					mViewPager.setCurrentItem(articles.indexOf(currentArticle));
				}
			});
		}

		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				if (mListener != null) {
					mListener.onDisplayedArticleChanged(mAdapter.getArticle(position));
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		return root;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(BUNDLE_CATEGORY, mCategory);
		outState.putParcelable(BUNDLE_ADAPTER, mViewPager.getAdapter().saveState());
		outState.putInt(BUNDLE_CURRENT, mViewPager.getCurrentItem());
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_article, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_article_open:
				return openArticle();
			case R.id.action_article_share:
				return shareArticle();
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (ArticlePagerFragmentListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ArticlePagerFragmentListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	public interface ArticlePagerFragmentListener {
		void onDisplayedArticleChanged(Article article);
	}

	private boolean openArticle() {
		if (mViewPager.getCurrentItem() != -1) {
			Article currentArticle = mAdapter.getArticle(mViewPager.getCurrentItem());
			Intent intent = new Intent(Intent.ACTION_VIEW)
					.setData(Uri.parse(currentArticle.getURL()));
			startActivity(intent);
			return true;
		}
		return false;
	}

	private boolean shareArticle() {
		if (mViewPager.getCurrentItem() != -1) {
			Article currentArticle = mAdapter.getArticle(mViewPager.getCurrentItem());
			Intent intent = new Intent(Intent.ACTION_SEND)
					.setType("text/plain")
					.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.action_article_share))
					.putExtra(Intent.EXTRA_TEXT, currentArticle.getURL());
			startActivity(Intent.createChooser(intent, getResources().getString(R.string.action_article_share)));
			return true;
		}
		return false;
	}

}
