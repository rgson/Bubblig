package se.rgson.da401a.bubblig.gui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import se.rgson.da401a.bubblig.Preferences;
import se.rgson.da401a.bubblig.R;

public class SettingsDialogFragment extends DialogFragment {

	private static final String TAG = SettingsDialogFragment.class.getSimpleName();

	private static final float MIN_TEXTSIZE = 10.f;
	private static final float MAX_TEXTSIZE = 30.f;

	private SeekBar mTextSizeSeekBar;
	private TextView mTextSizeExample;

	public static SettingsDialogFragment newInstance() {
		return new SettingsDialogFragment();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new AlertDialog.Builder(getActivity())
				.setTitle(R.string.settings_title)
				.setPositiveButton(R.string.settings_button_positive, new PositiveClickListener())
				.setNegativeButton(R.string.settings_button_negative, new NegativeClickListener())
				.setView(createView(getActivity().getLayoutInflater(), null, savedInstanceState))
				.create();
	}

	private View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_dialog_settings, container, false);

		float preferredTextSize = Preferences.getTextSize();

		mTextSizeExample = (TextView) root.findViewById(R.id.settings_textsize_example);
		mTextSizeExample.setTextSize(TypedValue.COMPLEX_UNIT_SP, preferredTextSize);

		mTextSizeSeekBar = (SeekBar) root.findViewById(R.id.settings_textsize_control);
		mTextSizeSeekBar.setProgress(textSizeToProgress(preferredTextSize));
		mTextSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				mTextSizeExample.setTextSize(TypedValue.COMPLEX_UNIT_SP, progressToTextSize(progress));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		return root;
	}

	private class PositiveClickListener implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			Preferences.setTextSize(progressToTextSize(mTextSizeSeekBar.getProgress()));
			dialog.dismiss();
		}
	}

	private class NegativeClickListener implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	}

	private float progressToTextSize(int progress) {
		return (progress / 100.f) * (MAX_TEXTSIZE - MIN_TEXTSIZE) + MIN_TEXTSIZE;
	}

	private int textSizeToProgress(float textSize) {
		return Math.round(100 * (textSize - MIN_TEXTSIZE) / (MAX_TEXTSIZE - MIN_TEXTSIZE));
	}

}
