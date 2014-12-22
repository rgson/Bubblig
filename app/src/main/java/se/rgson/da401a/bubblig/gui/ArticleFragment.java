package se.rgson.da401a.bubblig.gui;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

	public static ArticleFragment newInstance() {
		return new ArticleFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_article, container, false);

		mArticleContent = (TextView) root.findViewById(R.id.article_content);

		if (savedInstanceState != null) {
			setArticle((Article) savedInstanceState.getSerializable(BUNDLE_ARTICLE));
		}

		return root;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_article, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.action_article_open:
				actionOpen();
				return true;
			case R.id.action_article_share:
				actionShare();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(BUNDLE_ARTICLE, mArticle);
	}

	public void setArticle(Article article) {
		mArticle = article;
		mArticle.getContent(new ArticleListener() {
			@Override
			public void onArticleLoaded(Spanned content) {
				mArticleContent.setText(content);
			}
		});
	}

	public Article getArticle() {
		return mArticle;
	}

	private void actionOpen() {
		//TODO Add open action
	}

	private void actionShare() {
		//TODO Add share action
	}
}
