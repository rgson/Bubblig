package se.rgson.da401a.bubblig.gui;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import se.rgson.da401a.bubblig.Preferences;
import se.rgson.da401a.bubblig.R;
import se.rgson.da401a.bubblig.model.Article;

public class ArticleFragment extends Fragment {

	private static final String TAG = ArticleFragment.class.getSimpleName();
	private static final String BUNDLE_ARTICLE = "BUNDLE_ARTICLE";

	private Article mArticle;
	private TextView mArticleContent;
	private Preferences.PreferenceListener mPreferenceListener;

	public static ArticleFragment newInstance(Article article) {
		ArticleFragment fragment = new ArticleFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(BUNDLE_ARTICLE, article);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			mArticle = (Article) savedInstanceState.getSerializable(BUNDLE_ARTICLE);
		}
		else if (getArguments() != null) {
			mArticle = (Article) getArguments().getSerializable(BUNDLE_ARTICLE);
		}
		else {
			throw new IllegalArgumentException("An article must be provided.");
		}
		mPreferenceListener = new Preferences.PreferenceListener() {
			@Override
			public void onTextSizePreferenceChanged(float textSize) {
				if (mArticleContent != null) {
					mArticleContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
				}
			}
		};
		Preferences.attachPreferenceListener(mPreferenceListener);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_article, container, false);
		mArticleContent = (TextView) root.findViewById(R.id.article_content);
		mArticleContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, Preferences.getTextSize());
		new AsyncContentHandler().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		return root;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(BUNDLE_ARTICLE, mArticle);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Preferences.detachPreferenceListener(mPreferenceListener);
	}

	private class AsyncContentHandler extends AsyncTask<Void, Void, Spanned> {
		@Override
		protected Spanned doInBackground(Void... params) {
			Spanned content = Html.fromHtml(mArticle.getContent());
			if (content == null) {
				content = new SpannableString(getResources().getString(R.string.article_loading_failed));
			}
			return content;
		}

		@Override
		protected void onPostExecute(Spanned result) {
			//TODO Dangerous time sink for long articles. Process in parts while scrolling, using append().
			mArticleContent.setText(result);
		}
	}

}
