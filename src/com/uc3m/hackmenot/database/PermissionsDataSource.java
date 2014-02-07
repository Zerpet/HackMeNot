package com.uc3m.hackmenot.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PermissionsDataSource {
	// Database fields
	private SQLiteDatabase database;
	private DataBaseHelper dbHelper;

	public PermissionsDataSource(Context context) {
		dbHelper = new DataBaseHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public List<Permission> getAllPermissions() {
		List<Permission> permissions = new ArrayList<Permission>();
		String selectQuery = "SELECT  * FROM " + DataBaseHelper.TABLE_PERMS;
		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Permission permission = cursorToPermission(cursor);
				// Adding contact to list
				permissions.add(permission);
				Log.d("test", permission.getPermission());
			} while (cursor.moveToNext());
		}

		// make sure to close the cursor
		cursor.close();
		
		return permissions;
	}

	private Permission cursorToPermission(Cursor cursor) {
		Permission permission = new Permission();
		permission.setPermission(cursor.getString(0));
		permission.setThreatLevel(Integer.parseInt(cursor.getString(1)));
		permission.setDescription(cursor.getString(2));
		return permission;
	}
}
