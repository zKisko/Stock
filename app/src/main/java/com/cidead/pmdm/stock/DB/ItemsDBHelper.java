package com.cidead.pmdm.stock.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.cidead.pmdm.stock.DB.ItemsContract.ItemEntry;

//CLASE QUE MANEJA LA BASE DE DATOS

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

    // Insertar datos ficticios para prueba inicial

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockItem(sqLiteDatabase, new Item("Monitor", "3",
                "Nuevo", "Monitor LG 24 pulgadas.",
                ""));
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