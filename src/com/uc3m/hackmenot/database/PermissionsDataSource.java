package com.uc3m.hackmenot.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PermissionsDataSource {
	// Database fields
	private SQLiteDatabase database;
	private DataBaseHelper dbHelper;
	private String[] allColumns = { "permission", "threat_level", "description" };

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

		Cursor cursor = database.query(DataBaseHelper.TABLE_PERMS, allColumns,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Permission permission = cursorToPermission(cursor);
			permissions.add(permission);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return permissions;
	}

	private Permission cursorToPermission(Cursor cursor) {
		Permission permission = new Permission();
		permission.setPermission(cursor.getString(0));
		permission.setThreatLevel(cursor.getInt(1));
		permission.setDescription(cursor.getString(2));
		return permission;
	}
}
