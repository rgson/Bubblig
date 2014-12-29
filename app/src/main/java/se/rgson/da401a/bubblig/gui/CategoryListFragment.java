package se.rgson.da401a.bubblig.gui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import se.rgson.da401a.bubblig.R;
import se.rgson.da401a.bubblig.gui.components.CategoryListAdapter;
import se.rgson.da401a.bubblig.model.Category;

public class CategoryListFragment extends Fragment {

	public static final String TAG = CategoryListFragment.class.getSimpleName();
	public static final String BUNDLE_SELECTED = "BUNDLE_SELECTED";

	private CategoryListFragmentListener mListener;
	private ListView mCategoryList;

	public static CategoryListFragment newInstance() {
		return new CategoryListFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_category_list, container, false);

		mCategoryList = (ListView) root.findViewById(R.id.category_list);
		mCategoryList.setAdapter(new CategoryListAdapter(getActivity()));

		if (savedInstanceState != null) {
			mCategoryList.setSelection(savedInstanceState.getInt(BUNDLE_SELECTED));
		}

		mCategoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				view.setSelected(true);
				if (mListener != null) {
					mListener.onCategorySelected((Category) mCategoryList.getItemAtPosition(position));

                    Resources res = getResources();

                    //Changes the actionbar color depending on the news subject.
				    if (position == Category.NYHETER.ordinal()) {
                        ChangeActionBarColor(res.getColor(R.color.category_nyheter));

                    } else if (position == Category.VÄRLDEN.ordinal()) {
                        ChangeActionBarColor(res.getColor(R.color.category_världen));

                    }  else if (position == Category.SVERIGE.ordinal()) {
                        ChangeActionBarColor(res.getColor(R.color.category_sverige));

                    }  else if (position == Category.BLANDAT.ordinal()) {
                        ChangeActionBarColor(res.getColor(R.color.category_blandat));

                    }  else if (position == Category.MEDIA.ordinal()) {
                        ChangeActionBarColor(res.getColor(R.color.category_media));

                    }  else if (position == Category.POLITIK.ordinal()) {
                        ChangeActionBarColor(res.getColor(R.color.category_politik));

                    }  else if (position == Category.OPINION.ordinal()) {
                        ChangeActionBarColor(res.getColor(R.color.category_opinion));

                    }  else if (position == Category.EUROPA.ordinal()) {
                        ChangeActionBarColor(res.getColor(R.color.category_europa));

                    }  else if (position == Category.USA.ordinal()) {
                        ChangeActionBarColor(res.getColor(R.color.category_usa));

                    } else if (position == Category.ASIEN.ordinal()) {
                        ChangeActionBarColor(res.getColor(R.color.category_asien));

                    } else if (position == Category.EKONOMI.ordinal()) {
                        ChangeActionBarColor(res.getColor(R.color.category_ekonomi));

                    } else if (position == Category.TEKNIK.ordinal()) {
                        ChangeActionBarColor(res.getColor(R.color.category_teknik));

                    } else if (position == Category.VETENSKAP.ordinal()) {
                        ChangeActionBarColor(res.getColor(R.color.category_vetenskap));
                    }
                }
			}
		});

		return root;
	}

    public void ChangeActionBarColor(int color) {
        ActionBar actionbar =  getActivity().getActionBar();
        actionbar.setBackgroundDrawable(new ColorDrawable(color));
    }


	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(BUNDLE_SELECTED, mCategoryList.getSelectedItemPosition());
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (CategoryListFragmentListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement CategoryListFragmentListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	public interface CategoryListFragmentListener {
		public void onCategorySelected(Category category);
	}

}
