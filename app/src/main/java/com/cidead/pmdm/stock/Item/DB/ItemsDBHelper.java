//CLASE QUE MANEJA LA BASE DE DATOS

package com.cidead.pmdm.stock.Item.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.cidead.pmdm.stock.Item.DB.ItemsContract.ItemEntry;
import static com.cidead.pmdm.stock.Item.DB.CommonVar.*;



public class ItemsDBHelper extends SQLiteOpenHelper {

    public ItemsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ItemsContract.ItemEntry.TABLE_NAME + " ("
                + ItemsContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ItemEntry.IDWORKSTATION + " INTEGER NOT NULL,"
                + ItemsContract.ItemEntry.NAME + " TEXT NOT NULL,"
                + ItemsContract.ItemEntry.QUANTITY + " TEXT NOT NULL,"
                + ItemsContract.ItemEntry.CONDITION + " TEXT NOT NULL,"
                + ItemsContract.ItemEntry.DESCRIPTION + " TEXT NOT NULL,"
                + ItemsContract.ItemEntry.AVATARURL + " TEXT )");

        mockData(db);
    }

    // Insertamos datos ficticios para prueba inicial

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockItem(sqLiteDatabase, new Item(1,"Monitor", "3","Nuevo", "Monitor LG 24 pulgadas.",null));
        mockItem(sqLiteDatabase, new Item(1,"Teclado", "2", "Nuevo", "Monitor", null));
        mockItem(sqLiteDatabase, new Item(1,"Raton", "1", "Nuevo", "Monitor", null));
        mockItem(sqLiteDatabase, new Item(1,"Torre", "3", "Nuevo", "Monitor", null ));
        mockItem(sqLiteDatabase, new Item(2,"Cable HDMI", "2", "Nuevo", "Monitor", null));
        mockItem(sqLiteDatabase, new Item(2,"Cable de alimentación", "3", "Nuevo", "Monitor", null));
        mockItem(sqLiteDatabase, new Item(2,"Mesa de escritorio", "2", "Nuevo", "Monitor", null));
        mockItem(sqLiteDatabase, new Item(2,"Silla de oficina", "1", "Nuevo", "Monitor", null));

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
    // ESTE METODO SALVA (GUARDA) EL ITEM QUE CREEMOS
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
                ItemEntry._ID + " LIKE ?",
                new String[]{itemId},
                null,
                null,
                null);
        return c;
    }

    /**
     * @author: Kisko
     * @param idWorkstation
     * @return lista filtrada por el id de workstation
     */
    public Cursor getItemsByIdWorkstation(String idWorkstation) {
        Cursor c = getReadableDatabase().query(
                ItemEntry.TABLE_NAME,
                null,
                ItemEntry.IDWORKSTATION + " LIKE ?",
                new String[]{idWorkstation},
                null,
                null,
                null);
        return c;
    }

    //METODO PARA LA ELIMINACIÓN DE ELEMENTOS

    /**
     * @author: Kisko
     * @param itemId
     * @return devuelve un entero con info sobre
     *          el estado del borrado de datos
     */
    public int deleteItem(String itemId) {
        return getWritableDatabase().delete(
                ItemEntry.TABLE_NAME,
                ItemEntry._ID + " LIKE ?",
                new String[]{itemId});
    }

    //METODO PARA ACTUALIZAR ELEMENTOS


    public int updateItem(Item item, String itemId) {
        return getWritableDatabase().update(
                ItemEntry.TABLE_NAME,
                item.toContentValues(),
                ItemEntry._ID + " LIKE ?",
                new String[]{itemId}
        );
    }
}