package se.rgson.da401a.bubblig.gui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.preference.Preference;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import se.rgson.da401a.bubblig.Preferences;
import se.rgson.da401a.bubblig.R;

public class AboutDialogFragment extends DialogFragment {

	private static final String TAG = AboutDialogFragment.class.getSimpleName();

	public static AboutDialogFragment newInstance() {
		return new AboutDialogFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_dialog_about, container, false);

		float textSize = Preferences.getTextSize();

		TextView title = (TextView) root.findViewById(R.id.about_title);
		title.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize * GuiUtility.TEXT_SCALE_TITLE);

		TextView text = (TextView) root.findViewById(R.id.about_text);
		text.setText(Html.fromHtml(getResources().getString(R.string.about_text)));
		text.setMovementMethod(LinkMovementMethod.getInstance());
		text.setTextSize(TypedValue.COMPLEX_UNIT_SP, Preferences.getTextSize());

		TextView detail = (TextView) root.findViewById(R.id.about_detail);
		detail.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize * GuiUtility.TEXT_SCALE_DETAIL);

		return root;
	}

}
