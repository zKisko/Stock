//CLASE QUE MANEJA LA BASE DE DATOS

package com.cidead.pmdm.stock.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.cidead.pmdm.stock.DB.ItemsContract.ItemEntry;
import static com.cidead.pmdm.stock.Recursos.CommonVar.*;



public class ItemsDBHelper extends SQLiteOpenHelper {

    public ItemsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { /** Creamos un Try Catch para comprobar si existe la tabla Workstation.
                                            * En caso contrario, la creamos y la rellenamos con el contenido del mock de pruebas. */
        if(db.isOpen()){
        }else {
        }
            try{
            getAllItems();
        }catch(Exception e){
            db.execSQL("CREATE TABLE " + ItemsContract.ItemEntry.TABLE_NAME + " ("
                    + ItemsContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + ItemEntry.IDWORKSTATION + " INTEGER NOT NULL,"
                    + ItemsContract.ItemEntry.IDPRODUCTO + " TEXT NOT NULL,"
                    + ItemsContract.ItemEntry.QUANTITY + " TEXT NOT NULL,"
                    + ItemsContract.ItemEntry.CONDITION + " TEXT NOT NULL,"
                    + ItemsContract.ItemEntry.DESCRIPTION + " TEXT NOT NULL,"
                    + ItemsContract.ItemEntry.AVATARURL + " TEXT )");
            mockData(db);
        }
    }

    // Insertamos datos ficticios para prueba inicial
    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockItem(sqLiteDatabase, new Item(1,1, "3","Nuevo", "Monitor LG 24 pulgadas.",null));
        mockItem(sqLiteDatabase, new Item(1,2, "2", "Nuevo", "Monitor", null));
        mockItem(sqLiteDatabase, new Item(1,3, "1", "Nuevo", "Monitor", null));
        mockItem(sqLiteDatabase, new Item(1,4, "3", "Nuevo", "Monitor", null ));
        mockItem(sqLiteDatabase, new Item(2,5, "2", "Nuevo", "Monitor", null));
        mockItem(sqLiteDatabase, new Item(2,6, "3", "Nuevo", "Monitor", null));
        mockItem(sqLiteDatabase, new Item(2,7, "2", "Nuevo", "Monitor", null));
        mockItem(sqLiteDatabase, new Item(2,8, "1", "Nuevo", "Monitor", null));
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

    public long saveItem(Item item) { // ESTE METODO SALVA (GUARDA) EL ITEM QUE CREEMOS
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                ItemsContract.ItemEntry.TABLE_NAME,
                null,
                item.toContentValues());
    }

    public Cursor getAllItems() { //METODO PARA OBTENER TODOS LOS ELEMENTOS
        return getReadableDatabase().query(
                        ItemEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getItemById(String itemId) {  //METODO PARA OBTENER LOS ELEMENTOS POR ID
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

    /**@author: Kisko
     * @param idWorkstation
     * @return lista filtrada por el id de workstation */
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

    /**@author: Kisko
     * @param itemId
     * @return devuelve un entero con info sobre el estado del borrado de datos */
    public int deleteItem(String itemId) {  //METODO PARA LA ELIMINACIÓN DE ELEMENTOS
        return getWritableDatabase().delete(
                ItemEntry.TABLE_NAME,
                ItemEntry._ID + " LIKE ?",
                new String[]{itemId});
    }

    public int updateItem(Item item, String itemId) { //METODO PARA ACTUALIZAR ELEMENTOS
        return getWritableDatabase().update(
                ItemEntry.TABLE_NAME,
                item.toContentValues(),
                ItemEntry._ID + " LIKE ?",
                new String[]{itemId}
        );
    }
}