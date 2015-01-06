package se.rgson.da401a.bubblig.gui.components;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import se.rgson.da401a.bubblig.Preferences;
import se.rgson.da401a.bubblig.R;
import se.rgson.da401a.bubblig.gui.GuiUtility;
import se.rgson.da401a.bubblig.model.Category;

public class CategoryListAdapter extends ArrayAdapter<Category> {

	private static final String TAG = CategoryListAdapter.class.getSimpleName();

	private LayoutInflater mLayoutInflater;

	public CategoryListAdapter(Context context) {
		super(context, R.layout.listrow_category, Category.getCategories());
		mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.listrow_category, parent, false);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.listrow_category_text);
			holder.color = (GradientDrawable) convertView.findViewById(R.id.listrow_category_color).getBackground();
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.text.setText(getItem(position).toString());
		holder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, Preferences.getTextSize());
		holder.color.setColor(GuiUtility.findColorFor(getContext(), getItem(position)));
		return convertView;
	}

	private class ViewHolder {
		TextView text;
		GradientDrawable color;
	}
}
