package se.rgson.da401a.bubblig.gui.components;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.ListIterator;

import se.rgson.da401a.bubblig.R;
import se.rgson.da401a.bubblig.gui.GuiUtility;
import se.rgson.da401a.bubblig.model.Article;
import se.rgson.da401a.bubblig.model.Category;
import se.rgson.da401a.bubblig.model.CategoryListener;

public class ArticleListAdapter extends ArrayAdapter<Article> {

    private static final String TAG = ArticleListAdapter.class.getSimpleName();

    private Category mCategory;
    private ArticleListAdapterListener mListener;

    public ArticleListAdapter(Context context, Category category, ArticleListAdapterListener listener) {
        super(context, android.R.layout.simple_list_item_1);
        if (category == null) {
            throw new IllegalArgumentException("Category must not be null.");
        }
        mCategory = category;
        mListener = listener;
        refresh();
    }

    public void refresh() {
        if (mListener != null) {
            mListener.isRefreshing(true);
        }
        mCategory.invalidate();
        mCategory.getArticles(new CategoryListener() {
            @Override
            public void onCategoryLoaded(ArrayList<Article> articles) {
                ArticleListAdapter.this.clear();
                ArticleListAdapter.this.addAll(articles);
                if (mListener != null) {
                    mListener.isRefreshing(false);
                }
            }
        });
    }

    //Sets the row color in Article ListView depending on the category!
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        Resources res = parent.getResources();
        if (position % 2 == 0) {
            view.setBackgroundColor(res.getColor(R.color.row_lst_item_even));
        } else {
            view.setBackgroundColor(GuiUtility.findLighterColorForRow(mCategory, parent));
        }

        return view;
    }

    public interface ArticleListAdapterListener {
        void isRefreshing(boolean refreshing);
    }
}