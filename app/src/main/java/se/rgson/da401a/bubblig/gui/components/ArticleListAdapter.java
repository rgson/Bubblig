package se.rgson.da401a.bubblig.gui.components;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.rgson.da401a.bubblig.Preferences;
import se.rgson.da401a.bubblig.R;
import se.rgson.da401a.bubblig.gui.GuiUtility;
import se.rgson.da401a.bubblig.model.Article;
import se.rgson.da401a.bubblig.model.Category;

public class ArticleListAdapter extends ArrayAdapter<Article> {

	private static final String TAG = ArticleListAdapter.class.getSimpleName();
    static ArrayList<Integer> visitedArticleId = new ArrayList<>(); //Contains visited articles.

	private Category mCategory;
	private ArticleListAdapterListener mListener;
    private Article mArticle;

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

		TextView textView = (TextView) view.findViewById(android.R.id.text1);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, Preferences.getTextSize());

		Resources res = parent.getResources();
		if (position % 2 == 0) {
			view.setBackgroundColor(res.getColor(R.color.row_lst_item_even));
		}
		else {
			view.setBackgroundColor(GuiUtility.findLighterColorForRow(mCategory, parent));
		}

        //Set the text color on visited articles.
        if (!visitedArticleId.isEmpty()) {
            mArticle  = getItem(position);
            Integer articleId = mArticle.getID();
            if(visitedArticleId.contains(articleId)) {
                System.out.println(articleId);
                textView.setTextColor(res.getColor(R.color.row_lst_item_clicked));
            } else {
                textView.setTextColor(res.getColor(R.color.row_standard_text_color));
            }
        }

		return view;
	}

    public void AddVisitedArticle(Integer id) {
        visitedArticleId.add(id);
    }

    //Check if article has been visited before.
    public boolean IsArticleVisited(Integer id) {
        if (!visitedArticleId.isEmpty()) {
            boolean contains = visitedArticleId.contains(id);
            return contains;
        }
        return false;
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
			if (articles != null) {
				ArticleListAdapter.this.clear();
				ArticleListAdapter.this.addAll(articles);
			}
			if (mListener != null) {
				mListener.isRefreshing(false);
			}
		}
	}

    //Overriding equals for ListArray.
    @Override
    public boolean equals(Object rhs){
        if (this == null) return false;
        if (rhs == null) return false;
        if (this == rhs) return true;

        return false;
    }
}