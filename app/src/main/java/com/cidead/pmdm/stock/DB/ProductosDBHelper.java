package com.cidead.pmdm.stock.DB;

import static com.cidead.pmdm.stock.DB.ProductosContract.ProductosEntry.PRODUCTOS;
import static com.cidead.pmdm.stock.DB.ProductosContract.ProductosEntry.TABLE_NAME;
import static com.cidead.pmdm.stock.DB.ProductosContract.ProductosEntry._ID;
import static com.cidead.pmdm.stock.DB.ProductosContract.ProductosEntry._IDCATEGORIA;
import static com.cidead.pmdm.stock.Recursos.CommonVar.DATABASE_NAME;
import static com.cidead.pmdm.stock.Recursos.CommonVar.DATABASE_VERSION;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ProductosDBHelper extends SQLiteOpenHelper {

    public ProductosDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { /** Creamos un Try Catch para comprobar si existe la
                                            tabla Workstation. En caso contrario, la creamos y la
                                            rellenamos con el contenido del mock de pruebas. */
        try{
            getAllItems();
        }catch(Exception e){
            db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + _IDCATEGORIA + " INTEGER,"
                    + PRODUCTOS + " TEXT )");
            mockData(db);
            Log.i("CategoriaProductosDBHelper", "Tabla creada");
        }
    }

    // Insertamos datos ficticios para prueba inicial
    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockItem(sqLiteDatabase, new Producto(1,"LG"));
        mockItem(sqLiteDatabase, new Producto(2,"Trust"));
        mockItem(sqLiteDatabase, new Producto(3,"NewSkill"));
        mockItem(sqLiteDatabase, new Producto(4,"Acer"));
        mockItem(sqLiteDatabase, new Producto(5,"HP"));
        mockItem(sqLiteDatabase, new Producto(6,"Samsumg"));
        mockItem(sqLiteDatabase, new Producto(7,"Huawei"));
        mockItem(sqLiteDatabase, new Producto(8,"Apple"));
        mockItem(sqLiteDatabase, new Producto(9,"Canon"));
        mockItem(sqLiteDatabase, new Producto(10,"Epson"));
        mockItem(sqLiteDatabase, new Producto(11,"Mitsai"));
        mockItem(sqLiteDatabase, new Producto(12,"Otra"));
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

    /** @author: Kisko
     * @param productoId
     * @return devuelve un entero con info sobre el estado del borrado de datos */
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
