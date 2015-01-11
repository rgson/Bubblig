package se.rgson.da401a.bubblig;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashSet;

import se.rgson.da401a.bubblig.model.Article;

public class BubbligDB extends SQLiteOpenHelper {

	private static final String TAG = BubbligDB.class.getSimpleName();
	private static final String DB_NAME = "bubbligdb";
	private static final int DB_VERSION = 2;

	private static BubbligDB mInstance;
	private SQLiteDatabase mDatabase;

	public static void init(Context context) {
		mInstance = new BubbligDB(context.getApplicationContext());
		mInstance.open();
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
		db.execSQL(
				"CREATE TABLE history (" +
						"id INTEGER PRIMARY KEY," +
						"timestamp INTEGER DEFAULT (strftime('%s', 'now')));"
		);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion < 2) {
			db.execSQL(
					"CREATE TABLE history (" +
							"id INTEGER PRIMARY KEY," +
							"timestamp INTEGER DEFAULT (strftime('%s', 'now')));"
			);
		}
	}

	private void open() {
		mDatabase = getWritableDatabase();
	}

	public synchronized boolean saveArticleContent(Article article) {
		ContentValues values = new ContentValues();
		values.put("id", article.getID());
		values.put("content", article.getContent());
		boolean success = mDatabase.insert("article", null, values) != -1;
		return success;
	}

	public String loadArticleContent(int id) {
		String content = null;
		Cursor cursor = mDatabase.rawQuery("SELECT content FROM article WHERE id = ?;", new String[]{Integer.toString(id)});
		if (cursor.moveToFirst()) {
			content = cursor.getString(0);
		}
		cursor.close();
		return content;
	}

	public synchronized void saveArticleHistory(int articleID) {
		mDatabase.execSQL("INSERT OR IGNORE INTO history(id) VALUES (?);", new String[]{Integer.toString(articleID)});
	}

	public HashSet<Integer> loadArticleHistory() {
		HashSet<Integer> articleIDs = new HashSet<>();
		Cursor cursor = mDatabase.rawQuery("SELECT id FROM history;", null);
		while (cursor.moveToNext()) {
			articleIDs.add(cursor.getInt(0));
		}
		cursor.close();
		return articleIDs;
	}

	public synchronized void cleanup() {
		mDatabase.rawQuery("DELETE FROM article WHERE timestamp < (strftime('%s', 'now', '-1 week'));", null);
		mDatabase.rawQuery("DELETE FROM history WHERE timestamp < (strftime('%s', 'now', '-1 week'));", null);
	}

}
