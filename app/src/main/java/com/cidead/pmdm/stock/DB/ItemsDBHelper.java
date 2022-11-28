package com.cidead.pmdm.stock.DB;
/*
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.cidead.pmdm.stock.DB.ItemsContract.ItemsEntr;

//CLASE QUE MANEJA LA BASE DE DATOS

public class ItemsDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Item.db";
    public static final String TABLE_ITEMS = "t_elementos";

    public ItemsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ItemsEntr.TABLE_NAME + " ("
                + ItemsEntr._ID + " INTERGER PRIMARY KEY AUTOINCREMENT,"
                + ItemsEntr.ID + " TEXT NOT NULL,"
                + ItemsEntr.NAME + " TEXT NOT NULL,"
                + ItemsEntr.QUANTITY + " TEXT NOT NULL,"
                + ItemsEntr.CONDITION + " TEXT NOT NULL,"
                + ItemsEntr.DESCRIPTION + " TEXT NOT NULL,"
                + ItemsEntr.AVATAR + " TEXT,"
                + "UNIQUE (" + ItemsEntr.ID + "))");
        mockData(db);
    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockItems(sqLiteDatabase, new Item("Monitor", "2",
                "Nuevo", "Monitor marca LG 24 pulgadas", ""));
        mockItems(sqLiteDatabase, new Item("Teclado", "1",
                "Usado", "Teclado trust", ""));
    }

    public long mockItems (SQLiteDatabase db, Item item) {
        return db.insert(
                ItemsEntr.TABLE_NAME,
                null,
                item.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public long saveItems (Item item) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                ItemsEntr.TABLE_NAME,
                null,
                item.toContentValues());
    }

    //METODO PARA OBTENER TODOS LOS ELEMENTOS
    public Cursor getAllItems () {
        return getReadableDatabase().query(
                        ItemsEntr.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    //METODO PARA OBTENER LOS ELEMENTOS POR ID
    public Cursor getItemById(String ItemsId){
        Cursor c = getReadableDatabase().query(
                ItemsEntr.TABLE_NAME,
                null,
                ItemsEntr.ID + " LIKE ?",
                new String[]{ItemsId},
                null,
                null,
                null);
        return c;
    }

    //METODO PARA LA ELIMINACIÓN DE ELEMENTOS
    public int deleteItem(String itemsId){
        return getWritableDatabase().delete(
            ItemsEntr.TABLE_NAME,
            ItemsEntr.ID + " LIKE ?",
            new String[]{itemsId});
    }

    //METODO PARA ACTUALIZAR ELEMENTOS
    public int updateItems(Item item, String mItemsId) {
        return getWritableDatabase().update(
                ItemsEntr.TABLE_NAME,
                item.toContentValues(),
                ItemsEntr.ID + " LIKE ?",
                new String[]{mItemsId}
        );
    }
}
----------------------------------------------------------------------------------------------*/


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
                + "UNIQUE (" + ItemsContract                .ItemEntry.ID + "))");



        // Insertar datos ficticios para prueba inicial
        mockData(db);

    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockItem(sqLiteDatabase, new Item("Monitor", "3",
                "Nuevo", "Gran profesional con experiencia de 5 años en casos penales.",
                "carlos_perez.jpg"));
        mockItem(sqLiteDatabase, new Item("Daniel Samper", "Abogado accidentes de tráfico",
                "300 200 2222", "Gran profesional con experiencia de 5 años en accidentes de tráfico.",
                "daniel_samper.jpg"));
        mockItem(sqLiteDatabase, new Item("Lucia Aristizabal", "Abogado de derechos laborales",
                "300 200 3333", "Gran profesional con más de 3 años de experiencia en defensa de los trabajadores.",
                "lucia_aristizabal.jpg"));
        mockItem(sqLiteDatabase, new Item("Marina Acosta", "Abogado de familia",
                "300 200 4444", "Gran profesional con experiencia de 5 años en casos de familia.",
                "marina_acosta.jpg"));
        mockItem(sqLiteDatabase, new Item("Olga Ortiz", "Abogado de administración pública",
                "300 200 5555", "Gran profesional con experiencia de 5 años en casos en expedientes de urbanismo.",
                "olga_ortiz.jpg"));
        mockItem(sqLiteDatabase, new Item("Pamela Briger", "Abogado fiscalista",
                "300 200 6666", "Gran profesional con experiencia de 5 años en casos de derecho financiero",
                "pamela_briger.jpg"));
        mockItem(sqLiteDatabase, new Item("Rodrigo Benavidez", "Abogado Mercantilista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en redacción de contratos mercantiles",
                "rodrigo_benavidez.jpg"));
        mockItem(sqLiteDatabase, new Item("Tom Bonz", "Abogado penalista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en casos penales.",
                "tom_bonz.jpg"));
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

    public int deleteItem(String itemId) {
        return getWritableDatabase().delete(
                ItemEntry.TABLE_NAME,
                ItemEntry.ID + " LIKE ?",
                new String[]{itemId});
    }

    public int updateItem(Item item, String itemId) {
        return getWritableDatabase().update(
                ItemEntry.TABLE_NAME,
                item.toContentValues(),
                ItemEntry.ID + " LIKE ?",
                new String[]{itemId}
        );
    }
}