package se.rgson.da401a.bubblig.gui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import se.rgson.da401a.bubblig.R;
import se.rgson.da401a.bubblig.gui.components.ArticleListAdapter;
import se.rgson.da401a.bubblig.model.Article;
import se.rgson.da401a.bubblig.model.Category;
import se.rgson.da401a.bubblig.model.readability.ReadabilityResponse;

public class ArticleListFragment extends Fragment {

	private static final String TAG = ArticleListFragment.class.getSimpleName();
	private static final String BUNDLE_SELECTED = "BUNDLE_SELECTED";
	private static final String BUNDLE_CATEGORY = "BUNDLE_CATEGORY";

	private ArticleListFragmentListener mListener;
	private ListView mArticleList;
	private ArticleListAdapter mArticleAdapter;

	public static ArticleListFragment newInstance() {
		return new ArticleListFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_article_list, container, false);

		mArticleList = (ListView) root.findViewById(R.id.article_list);
		mArticleAdapter = new ArticleListAdapter(getActivity(), Category.NYHETER);
		mArticleList.setAdapter(mArticleAdapter);

		if (savedInstanceState != null) {
			mArticleAdapter.setCategory((Category) savedInstanceState.getSerializable(BUNDLE_CATEGORY));
			mArticleList.setSelection(savedInstanceState.getInt(BUNDLE_SELECTED));
		}

		mArticleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mArticleList.setSelected(true);
				if (mListener != null) {
					mListener.onArticleSelected((Article) mArticleList.getSelectedItem());
				}
			}
		});

		return root;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(BUNDLE_SELECTED, mArticleList.getSelectedItemPosition());
		outState.putSerializable(BUNDLE_CATEGORY, mArticleAdapter.getCategory());
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (ArticleListFragmentListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ArticleListFragmentListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	public interface ArticleListFragmentListener {

		public void onArticleSelected(Article article);

	}

}
