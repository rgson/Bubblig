package se.rgson.da401a.bubblig.gui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.rgson.da401a.bubblig.R;

public class ArticleFragment extends Fragment {

	private static final String TAG = ArticleFragment.class.getSimpleName();
	private static final String BUNDLE_ARTICLE = "BUNDLE_ARTICLE";

	public static ArticleFragment newInstance() {
		return new ArticleFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_article, container, false);
		//TODO load article from saved state
	}

}
