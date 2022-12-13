//CLASE QUE MANEJA LA BASE DE DATOS

package com.cidead.pmdm.stock.DB;

import static com.cidead.pmdm.stock.DB.ProductosContract.ProductosEntry.*;
import static com.cidead.pmdm.stock.Recursos.CommonVar.DATABASE_NAME;
import static com.cidead.pmdm.stock.Recursos.CommonVar.DATABASE_VERSION;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ProductosDBHelper extends SQLiteOpenHelper {

    public ProductosDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /** Creamos un Try Catch para comprobar si existe la tabla Workstation.
         * En caso contrario, la creamos y la rellenamos con el contenido del mock de pruebas. */
        try{
            getAllItems();
        }catch(Exception e){
            db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + PRODUCTOS + " TEXT )");
            mockData(db);
        }
    }

    // Insertamos datos ficticios para prueba inicial

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockItem(sqLiteDatabase, new Producto(1, "LG"));
        mockItem(sqLiteDatabase, new Producto(1, "Acer"));
        mockItem(sqLiteDatabase, new Producto(1, "Benq"));
        mockItem(sqLiteDatabase, new Producto(1, "Samsung"));
        mockItem(sqLiteDatabase, new Producto(2, "Corsair"));
        mockItem(sqLiteDatabase, new Producto(2, "Logitech"));
        mockItem(sqLiteDatabase, new Producto(2, "Razer"));
        mockItem(sqLiteDatabase, new Producto(3, "Corsair"));
        mockItem(sqLiteDatabase, new Producto(3, "Logitech"));
        mockItem(sqLiteDatabase, new Producto(3, "Razer"));
        mockItem(sqLiteDatabase, new Producto(4, "Lenovo"));
        mockItem(sqLiteDatabase, new Producto(4, "HP"));
        mockItem(sqLiteDatabase, new Producto(5, "Samsumg"));
        mockItem(sqLiteDatabase, new Producto(5, "Xiaomi"));
        mockItem(sqLiteDatabase, new Producto(5, "Huawei"));
        mockItem(sqLiteDatabase, new Producto(5, "Apple"));
        mockItem(sqLiteDatabase, new Producto(5, "LG"));
        mockItem(sqLiteDatabase, new Producto(6, "Logitech"));
        mockItem(sqLiteDatabase, new Producto(6, "Onikuma"));
        mockItem(sqLiteDatabase, new Producto(6, "HyperX"));
        mockItem(sqLiteDatabase, new Producto(7, "Acer"));
        mockItem(sqLiteDatabase, new Producto(7, "LG"));
        mockItem(sqLiteDatabase, new Producto(7, "Lenovo"));
        mockItem(sqLiteDatabase, new Producto(7, "Hp"));
        mockItem(sqLiteDatabase, new Producto(7, "Apple"));
        mockItem(sqLiteDatabase, new Producto(7, "Dell"));
        mockItem(sqLiteDatabase, new Producto(8, "Epson"));
        mockItem(sqLiteDatabase, new Producto(8, "Brother"));
        mockItem(sqLiteDatabase, new Producto(8, "Canon"));
        mockItem(sqLiteDatabase, new Producto(9, "HP"));
        mockItem(sqLiteDatabase, new Producto(9, "Canon"));
        mockItem(sqLiteDatabase, new Producto(10, "Samsumg"));
        mockItem(sqLiteDatabase, new Producto(10, "Huawei"));
        mockItem(sqLiteDatabase, new Producto(10, "Apple"));
        mockItem(sqLiteDatabase, new Producto(11, "Logitech"));
        mockItem(sqLiteDatabase, new Producto(11, "AverMedia"));

    }

    public long mockItem(SQLiteDatabase db, Producto producto) {
        return db.insert(
                TABLE_NAME,
                null,
                producto.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }
    // ESTE METODO SALVA (GUARDA) EL ITEM QUE CREEMOS
    public long saveProducto(Producto producto) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                TABLE_NAME,
                null,
                producto.toContentValues());

    }

    //METODO PARA OBTENER TODOS LOS ELEMENTOS
    public Cursor getAllItems() {
        return getReadableDatabase().query(
                        TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    //METODO PARA OBTENER LOS ELEMENTOS POR ID
    public Cursor getProductoById(String productoId) {
        Cursor c = getReadableDatabase().query(
                TABLE_NAME,
                null,
                _ID + " LIKE ?",
                new String[]{productoId},
                null,
                null,
                null);
        return c;
    }

   //METODO PARA LA ELIMINACIÓN DE ELEMENTOS

    /**
     * @author: Kisko
     * @param productoId
     * @return devuelve un entero con info sobre
     *          el estado del borrado de datos
     */
    public int deleteProducto(String productoId) {
        return getWritableDatabase().delete(
                TABLE_NAME,
                _ID + " LIKE ?",
                new String[]{productoId});
    }

    //METODO PARA ACTUALIZAR ELEMENTOS


    public int updateProducto(Producto producto, String productoId) {
        return getWritableDatabase().update(
                TABLE_NAME,
                producto.toContentValues(),
                _ID + " LIKE ?",
                new String[]{productoId}
        );
    }
}