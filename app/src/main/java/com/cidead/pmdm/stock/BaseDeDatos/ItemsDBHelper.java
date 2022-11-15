package com.cidead.pmdm.stock.BaseDeDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ItemsDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Items.db";

    public ItemsDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE" + ItemsScheme.ItemsColumsNames.TABLE_NAME + " ("
                + ItemsScheme.ItemsColumsNames._ID + "INTERGER PRIMERY KEY AUTOINCREMENT,"
                + ItemsScheme.ItemsColumsNames.ID + "TEXT NOT NULL,"
                + ItemsScheme.ItemsColumsNames.NAME + "TEXT NOT NULL,"
                + ItemsScheme.ItemsColumsNames.QUANTITY + "TEXT NOT NULL,"
                + ItemsScheme.ItemsColumsNames.CONDITION + "TEXT NOT NULL,"
                + ItemsScheme.ItemsColumsNames.DESCRIPTION + "TEXT NOT NULL,"
                + "UNIQUE (" + ItemsScheme.ItemsColumsNames.ID + "))");
        mockData(db);
    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {

        mockItems(sqLiteDatabase, new Items("Monitor", "2", "Nuevo", "Monitor marca LG 24 pulgadas"));
        mockItems(sqLiteDatabase, new Items("Teclado", "1", "Usado", "Teclado trust" ));
    }

    public long mockItems (SQLiteDatabase db, Items items) {
        return db.insert(
                ItemsScheme.ItemsColumsNames.TABLE_NAME,
                null,
                items.toContentValues()
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public long saveItems (Items items) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                ItemsScheme.ItemsColumsNames.TABLE_NAME,
                null,
                items.toContentValues());
    }

    public Cursor getAllItems () {
        return getReadableDatabase().query(
                        ItemsScheme.ItemsColumsNames.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getItemsById (String ItemsId){
        Cursor c = getReadableDatabase().query(
                ItemsScheme.ItemsColumsNames.TABLE_NAME,
                null,
                ItemsScheme.ItemsColumsNames.ID + " LIKE ?",
                new String[]{ItemsId},
                null,
                null,
                null);
        return c;
    }
}
