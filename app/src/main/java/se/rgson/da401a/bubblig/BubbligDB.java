package se.rgson.da401a.bubblig;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import se.rgson.da401a.bubblig.model.Article;

public class BubbligDB extends SQLiteOpenHelper {

	private static final String TAG = BubbligDB.class.getSimpleName();
	private static final String DB_NAME = "bubbligdb";
	private static final int DB_VERSION = 1;

	private static BubbligDB mInstance;

	public static void init(Context context) {
		mInstance = new BubbligDB(context.getApplicationContext());
	}

	public static BubbligDB getInstance() {
		return mInstance;
	}

	private BubbligDB(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(
				"CREATE TABLE article (" +
						"id INTEGER PRIMARY KEY," +
						"content TEXT NOT NULL," +
						"timestamp INTEGER DEFAULT (strftime('%s', 'now')));"
		);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public synchronized boolean saveArticleContent(Article article) {
		SQLiteDatabase database = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("id", article.getID());
		values.put("content", article.getContent());
		boolean success = database.insert("article", null, values) != -1;
		database.close();
		return success;
	}

	public synchronized String loadArticleContent(int id) {
		String content = null;
		SQLiteDatabase database = getReadableDatabase();
		Cursor cursor = database.rawQuery("SELECT content FROM article WHERE id = ?;", new String[]{Integer.toString(id)});
		if (cursor.moveToFirst()) {
			content = cursor.getString(0);
		}
		cursor.close();
		database.close();
		return content;
	}

	public synchronized void cleanupArticles() {
		SQLiteDatabase database = getWritableDatabase();
		database.rawQuery("DELETE FROM article WHERE timestamp < (strftime('%s', 'now', '-1 week'));", null);
		database.close();
	}

}
