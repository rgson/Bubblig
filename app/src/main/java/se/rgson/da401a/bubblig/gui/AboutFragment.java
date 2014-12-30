package se.rgson.da401a.bubblig.gui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import se.rgson.da401a.bubblig.R;

public class AboutFragment extends DialogFragment {

	private static final String TAG = AboutFragment.class.getSimpleName();

	public static AboutFragment newInstance() {
		return new AboutFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_about, container, false);

		TextView title = (TextView) root.findViewById(R.id.about_title);
		title.setText(R.string.about_title);

		TextView text = (TextView) root.findViewById(R.id.about_text);
		text.setText(Html.fromHtml(getResources().getString(R.string.about_text)));
		text.setMovementMethod(LinkMovementMethod.getInstance());

		return root;
	}

}
