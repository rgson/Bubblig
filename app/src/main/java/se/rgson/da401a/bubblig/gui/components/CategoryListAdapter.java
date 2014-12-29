package se.rgson.da401a.bubblig.gui.components;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import se.rgson.da401a.bubblig.R;
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
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.text.setText(getItem(position).toString());
		holder.color.setColor(findColorFor(getItem(position)));
		return convertView;
	}

	private int findColorFor(Category category) {
		Resources res = getContext().getResources();
		switch (category) {
			case NYHETER:
				return res.getColor(R.color.category_nyheter);
			case VÄRLDEN:
				return res.getColor(R.color.category_världen);
			case SVERIGE:
				return res.getColor(R.color.category_sverige);
			case BLANDAT:
				return res.getColor(R.color.category_blandat);
			case MEDIA:
				return res.getColor(R.color.category_media);
			case POLITIK:
				return res.getColor(R.color.category_politik);
			case OPINION:
				return res.getColor(R.color.category_opinion);
			case EUROPA:
				return res.getColor(R.color.category_europa);
			case USA:
				return res.getColor(R.color.category_usa);
			case ASIEN:
				return res.getColor(R.color.category_asien);
			case EKONOMI:
				return res.getColor(R.color.category_ekonomi);
			case TEKNIK:
				return res.getColor(R.color.category_teknik);
			case VETENSKAP:
				return res.getColor(R.color.category_vetenskap);
			default:
				return res.getColor(android.R.color.transparent);
		}
	}

	private class ViewHolder {
		TextView text;
		GradientDrawable color;
	}
}
