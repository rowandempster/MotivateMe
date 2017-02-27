package enghack.motivateme.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by rowandempster on 2/20/17.
 */

public class MotivateMeDatabaseUtils {

    public static void replaceInt(SQLiteDatabase db, String table, String col, int toWrite) {
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(col, toWrite);
            if (isTableEmpty(db, table)) {
                writeInt(db, table, col, toWrite);
            } else {
                db.update(table, values, "_id=1", null);
            }
        }
    }

    public static void replaceLong(SQLiteDatabase db, String table, String col, long toWrite) {
        if (db != null) {
            if (db != null) {
                ContentValues values = new ContentValues();
                values.put(col, toWrite);
                if (isTableEmpty(db, table)) {
                    writeLong(db, table, col, toWrite);
                } else {
                    db.update(table, values, "_id=1", null);
                }
            }
        }
    }

    public static void replaceString(SQLiteDatabase db, String table, String col, String toWrite) {
        if (db != null) {
            if (db != null) {
                ContentValues values = new ContentValues();
                values.put(col, toWrite);
                if (isTableEmpty(db, table)) {
                    writeString(db, table, col, toWrite);
                } else {
                    db.update(table, values, "_id=1", null);
                }
            }
        }
    }

    public static void writeLong(SQLiteDatabase db, String table, String col, long toWrite) {
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(col, toWrite);
            db.insert(table, null, values);
        }
    }

    public static void writeInt(SQLiteDatabase db, String table, String col, int toWrite) {
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(col, toWrite);
            db.insert(table, null, values);
        }
    }

    public static void writeString(SQLiteDatabase db, String table, String col, String toWrite) {
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(col, toWrite);
            db.insert(table, null, values);
        }
    }

    public static int readFirstInt(SQLiteDatabase db, String table, String id, String col) {
        if (db == null) {
            return 0;
        }

        Cursor cursor = getCursor(db, table, id, col);
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex(col));
        } else {
            return 0;
        }
    }

    public static long readFirstLong(SQLiteDatabase db, String table, String id, String col){
        if (db == null) {
            return 0;
        }

        Cursor cursor = getCursor(db, table, id, col);
        if (cursor.moveToFirst()) {
            return cursor.getLong(cursor.getColumnIndex(col));
        } else {
            return 0;
        }
    }

    public static String readFirstString(SQLiteDatabase db, String table, String id, String col){
        if (db == null) {
            return null;
        }

        Cursor cursor = getCursor(db, table, id, col);
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(col));
        } else {
            return null;
        }
    }

    public static boolean columnContainsLong(SQLiteDatabase db, String table, String col, long toFind){

        Cursor cursor = db.rawQuery("SELECT * FROM " + table + " WHERE "+ col +"=" + toFind, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    private static Cursor getCursor(SQLiteDatabase db, String table, String id, String col) {
        return db.query(
                table,                     // The table to query
                getColumnProjection(id, col),                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
    }

    private static String[] getColumnProjection(String id, String col) {
        return new String[]{
                id,
                col
        };
    }

    public static boolean isTableEmpty(SQLiteDatabase db, String table) {
        String count = "SELECT count(*) FROM " + table;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        return icount < 1;
    }

    public static void deleteRow(SQLiteDatabase db, String table, long rowId){
        db.delete(table, "_id=?", new String[]{Long.toString(rowId)});
    }
}
