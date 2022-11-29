//CLASE QUE MANEJA LA BASE DE DATOS

package com.cidead.pmdm.stock.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.cidead.pmdm.stock.DB.ItemsContract.ItemEntry;



public class ItemsDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Items.db";

    public ItemsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ItemsContract.ItemEntry.TABLE_NAME + " ("
                + ItemsContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ItemsContract.ItemEntry.ID + " TEXT NOT NULL,"
                + ItemsContract.ItemEntry.NAME + " TEXT NOT NULL,"
                + ItemsContract.ItemEntry.QUANTITY + " TEXT NOT NULL,"
                + ItemsContract.ItemEntry.CONDITION + " TEXT NOT NULL,"
                + ItemsContract.ItemEntry.DESCRIPTION + " TEXT NOT NULL,"
                + ItemsContract.ItemEntry.AVATARURL + " TEXT,"
                + "UNIQUE (" + ItemsContract.ItemEntry.ID + "))");

        mockData(db);
    }

    // Insertamos datos ficticios para prueba inicial

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockItem(sqLiteDatabase, new Item("Monitor", "3","Nuevo", "Monitor LG 24 pulgadas.",""));
        mockItem(sqLiteDatabase, new Item("Teclado", "", "", "", ""));
        mockItem(sqLiteDatabase, new Item("Raton", "", "", "", ""));
        mockItem(sqLiteDatabase, new Item("Torre", "", "", "", "" ));
        mockItem(sqLiteDatabase, new Item("Cable HDMI", "", "", "", ""));
        mockItem(sqLiteDatabase, new Item("Cable de alimentación", "", "", "", ""));
        mockItem(sqLiteDatabase, new Item("Mesa de escritorio", "", "", "", ""));
        mockItem(sqLiteDatabase, new Item("Silla de oficina", "", "", "", ""));
    }

    public long mockItem(SQLiteDatabase db, Item item) {
        return db.insert(
                ItemEntry.TABLE_NAME,
                null,
                item.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public long saveItem(Item item) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                ItemsContract.ItemEntry.TABLE_NAME,
                null,
                item.toContentValues());

    }

    //METODO PARA OBTENER TODOS LOS ELEMENTOS
    public Cursor getAllItems() {
        return getReadableDatabase().query(
                        ItemEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    //METODO PARA OBTENER LOS ELEMENTOS POR ID
    public Cursor getItemById(String itemId) {
        Cursor c = getReadableDatabase().query(
                ItemEntry.TABLE_NAME,
                null,
                ItemEntry.ID + " LIKE ?",
                new String[]{itemId},
                null,
                null,
                null);
        return c;
    }

    //METODO PARA LA ELIMINACIÓN DE ELEMENTOS
    public int deleteItem(String itemId) {
        return getWritableDatabase().delete(
                ItemEntry.TABLE_NAME,
                ItemEntry.ID + " LIKE ?",
                new String[]{itemId});
    }

    //METODO PARA ACTUALIZAR ELEMENTOS
    public int updateItem(Item item, String itemId) {
        return getWritableDatabase().update(
                ItemEntry.TABLE_NAME,
                item.toContentValues(),
                ItemEntry.ID + " LIKE ?",
                new String[]{itemId}
        );
    }
}