package se.rgson.da401a.bubblig.gui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import se.rgson.da401a.bubblig.R;
import se.rgson.da401a.bubblig.gui.components.ArticleListAdapter;
import se.rgson.da401a.bubblig.model.Article;
import se.rgson.da401a.bubblig.model.Category;

public class ArticleListFragment extends Fragment implements ArticleListAdapter.ArticleListAdapterListener {

	private static final String TAG = ArticleListFragment.class.getSimpleName();
	private static final String BUNDLE_CATEGORY = "BUNDLE_CATEGORY";

	private ArticleListFragmentListener mListener;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private ListView mArticleList;
	private Category mCategory;

	public static ArticleListFragment newInstance(Category category) {
		ArticleListFragment fragment = new ArticleListFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(BUNDLE_CATEGORY, category);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_article_list, container, false);

		mArticleList = (ListView) root.findViewById(R.id.article_list);
		mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.article_swipe_refresh_layout);

		final ArticleListAdapter adapter = new ArticleListAdapter(getActivity(), mCategory, this);
		mArticleList.setAdapter(adapter);

		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				adapter.refresh();
			}
		});

		mArticleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				view.setSelected(true);
				if (mListener != null) {
					mListener.onArticleSelected(adapter.getItem(position));
				}
			}
		});

		return root;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(BUNDLE_CATEGORY, mCategory);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (ArticleListFragmentListener) activity;
		}
		catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ArticleListFragmentListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void isRefreshing(boolean refreshing) {
		mSwipeRefreshLayout.setRefreshing(refreshing);
	}

	public interface ArticleListFragmentListener {
		public void onArticleSelected(Article article);
	}

}
