package se.rgson.da401a.bubblig;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class Preferences {

	private static final String TAG = Preferences.class.getSimpleName();
	private static final String PREFERENCE_KEY_TEXTSIZE = "PREFERENCE_KEY_TEXTSIZE";
	private static final float DEFAULT_TEXTSIZE = 20.f;

	private static SharedPreferences mPreferences;
	private static ArrayList<PreferenceListener> mListeners = new ArrayList<>();

	private Preferences() {
	}

	public static void init(Activity activity) {
		if (activity == null) {
			throw new IllegalArgumentException("Activity must not be null.");
		}
		mPreferences = activity.getPreferences(Context.MODE_PRIVATE);
	}

	public static float getTextSize() {
		if (mPreferences == null) {
			throw new IllegalStateException("\"Preferences\" is not initialized. Please call init() first.");
		}
		return mPreferences.getFloat(PREFERENCE_KEY_TEXTSIZE, DEFAULT_TEXTSIZE);
	}

	public static boolean setTextSize(float textSize) {
		if (mPreferences == null) {
			throw new IllegalStateException("\"Preferences\" is not initialized. Please call init() first.");
		}
		boolean success = mPreferences.edit().putFloat(PREFERENCE_KEY_TEXTSIZE, textSize).commit();
		if (success) {
			for (PreferenceListener listener : mListeners) {
				listener.onTextSizePreferenceChanged(textSize);
			}
		}
		return success;
	}

	public static void attachPreferenceListener(PreferenceListener listener) {
		if (mListeners != null) {
			mListeners.add(listener);
		}
	}

	public static void detachPreferenceListener(PreferenceListener listener) {
		if (mListeners != null) {
			mListeners.remove(listener);
		}
	}

	public static abstract class PreferenceListener {
		public void onTextSizePreferenceChanged(float textSize) {
		}
	}
}
