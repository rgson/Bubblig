package se.rgson.da401a.bubblig.gui.components;

import android.content.Context;
import android.widget.ArrayAdapter;

import se.rgson.da401a.bubblig.model.Category;

public class CategoryListAdapter extends ArrayAdapter<Category> {

	private static final String TAG = CategoryListAdapter.class.getSimpleName();

	public CategoryListAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_1, Category.getCategories());
	}

}
