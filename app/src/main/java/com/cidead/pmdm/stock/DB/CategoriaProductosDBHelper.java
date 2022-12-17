package com.cidead.pmdm.stock.DB;

import static com.cidead.pmdm.stock.DB.CategoriaProductosContract.CategoriaProductosEntry.CATEGORIA;
import static com.cidead.pmdm.stock.DB.CategoriaProductosContract.CategoriaProductosEntry.IMAGEN;
import static com.cidead.pmdm.stock.DB.CategoriaProductosContract.CategoriaProductosEntry.TABLE_NAME;
import static com.cidead.pmdm.stock.DB.CategoriaProductosContract.CategoriaProductosEntry._ID;
import static com.cidead.pmdm.stock.Recursos.CommonVar.DATABASE_NAME;
import static com.cidead.pmdm.stock.Recursos.CommonVar.DATABASE_VERSION;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CategoriaProductosDBHelper extends SQLiteOpenHelper {

    public CategoriaProductosDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {   /** Creamos un Try Catch para comprobar si existe la
     tabla Workstation. En caso contrario, la creamos y la
     rellenamos con el contenido del mock de pruebas. */
        try{
            getAllCategoriasProducto();
        }catch(Exception e){
            db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + CATEGORIA + " TEXT,"
                    + IMAGEN + " TEXT )");
            mockData(db);

        }
    }

    // Insertamos datos ficticios para prueba inicial
    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockItem(sqLiteDatabase, new CategoriaProducto("Monitor","https://cdn-icons-png.flaticon.com/128/3451/3451513.png"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Teclado","https://cdn-icons-png.flaticon.com/128/1212/1212890.png"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Raton","https://cdn-icons-png.flaticon.com/128/1786/1786973.png"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Torre","https://cdn-icons-png.flaticon.com/128/4740/4740513.png"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Smartphone","https://cdn-icons-png.flaticon.com/128/977/977411.png"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Auriculares","https://cdn-icons-png.flaticon.com/128/2292/2292116.png"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Portatil","https://cdn-icons-png.flaticon.com/128/7533/7533214.png"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Impresora","https://cdn-icons-png.flaticon.com/128/3756/3756803.png"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Escaner","https://cdn-icons-png.flaticon.com/128/481/481755.png"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Tablet","https://cdn-icons-png.flaticon.com/128/900/900263.png"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Webcam","https://cdn-icons-png.flaticon.com/128/2866/2866726.png"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Cable","https://cdn-icons-png.flaticon.com/128/3432/3432620.png"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Mesa","https://cdn-icons-png.flaticon.com/128/1189/1189357.png"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Silla","https://cdn-icons-png.flaticon.com/128/5396/5396311.png"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Otros","https://cdn-icons-png.flaticon.com/128/2432/2432572.png"));
    }

    public long mockItem(SQLiteDatabase db, CategoriaProducto categoriaProducto) {
        return db.insert(
                TABLE_NAME,
                null,
                categoriaProducto.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    // ESTE METODO SALVA (GUARDA) EL ITEM QUE CREEMOS
    public long saveCategoriaProducto(CategoriaProducto categoriaProducto) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                TABLE_NAME,
                null,
                categoriaProducto.toContentValues());
    }

    //METODO PARA OBTENER TODOS LOS ELEMENTOS
    public Cursor getAllCategoriasProducto() {
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
    public Cursor getCategoriaProductoById(String CategoriaProductoId) {
        Cursor c = getReadableDatabase().query(
                TABLE_NAME,
                null,
                _ID + " LIKE ?",
                new String[]{CategoriaProductoId},
                null,
                null,
                null);
        return c;
    }

    //METODO PARA LA ELIMINACIÓN DE ELEMENTOS

    /** @author: Kisko
     * @param categoriaProductoId
     * @return devuelve un entero con info sobre el estado del borrado de datos */
    public int deleteCategoriaProducto(String categoriaProductoId) {
        return getWritableDatabase().delete(
                TABLE_NAME,
                _ID + " LIKE ?",
                new String[]{categoriaProductoId});
    }

    //METODO PARA ACTUALIZAR ELEMENTOS
    public int updateCategoriaProducto(CategoriaProducto categoriaProducto, String categoriaProductoId) {
        return getWritableDatabase().update(
                TABLE_NAME,
                categoriaProducto.toContentValues(),
                _ID + " LIKE ?",
                new String[]{categoriaProductoId}
        );
    }
}
