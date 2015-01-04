package se.rgson.da401a.bubblig.gui.components;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import se.rgson.da401a.bubblig.R;
import se.rgson.da401a.bubblig.gui.GuiUtility;
import se.rgson.da401a.bubblig.model.Article;
import se.rgson.da401a.bubblig.model.Category;

public class ArticleListAdapter extends ArrayAdapter<Article> {

	private static final String TAG = ArticleListAdapter.class.getSimpleName();

	private Category mCategory;
	private ArticleListAdapterListener mListener;

	public ArticleListAdapter(Context context, Category category, ArticleListAdapterListener listener) {
		super(context, R.layout.listrow_article);
		if (category == null) {
			throw new IllegalArgumentException("Category must not be null.");
		}
		mCategory = category;
		mListener = listener;
		refresh();
	}

	public void refresh() {
		new AsyncRefreshHandler().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}

	//Sets the row color in Article ListView depending on the category!
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);

		Resources res = parent.getResources();
		if (position % 2 == 0) {
			view.setBackgroundColor(res.getColor(R.color.row_lst_item_even));
		}
		else {
			view.setBackgroundColor(GuiUtility.findLighterColorForRow(mCategory, parent));
		}

		return view;
	}

	public interface ArticleListAdapterListener {
		void isRefreshing(boolean refreshing);
	}

	private class AsyncRefreshHandler extends AsyncTask<Void, Void, List<Article>> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mListener != null) {
				mListener.isRefreshing(true);
			}
		}

		@Override
		protected List<Article> doInBackground(Void... params) {
			mCategory.invalidate();
			return mCategory.getArticles();
		}

		@Override
		protected void onPostExecute(List<Article> articles) {
			super.onPostExecute(articles);
			ArticleListAdapter.this.clear();
			ArticleListAdapter.this.addAll(articles);
			if (mListener != null) {
				mListener.isRefreshing(false);
			}
		}
	}
}