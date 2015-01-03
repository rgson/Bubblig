package se.rgson.da401a.bubblig.gui;

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import se.rgson.da401a.bubblig.R;
import se.rgson.da401a.bubblig.model.Article;
import se.rgson.da401a.bubblig.model.ArticleListener;

public class ArticleFragment extends Fragment {

	private static final String TAG = ArticleFragment.class.getSimpleName();
	private static final String BUNDLE_ARTICLE = "BUNDLE_ARTICLE";

	private Article mArticle;
	private TextView mArticleContent;

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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_article, container, false);
		mArticleContent = (TextView) root.findViewById(R.id.article_content);
		if (mArticle != null) {
			new AsyncContentHandler().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}

		return root;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(BUNDLE_ARTICLE, mArticle);
	}

	private class AsyncContentHandler extends AsyncTask<Void, Void, Spanned> {
		@Override
		protected Spanned doInBackground(Void... params) {
			final Spanned[] spanned = new Spanned[1];
			mArticle.getContent(new ArticleListener() {
				@Override
				public void onArticleLoaded(String content) {
					if (content.isEmpty()) {
						content = getResources().getString(R.string.article_loading_failed);
					}
					spanned[0] = Html.fromHtml(content);
				}
			});
			return spanned[0];
		}

		@Override
		protected void onPostExecute(Spanned result) {
			//TODO Huge time sink! Process in parts while scrolling, using append()
			mArticleContent.setText(result);
		}
	}

}
