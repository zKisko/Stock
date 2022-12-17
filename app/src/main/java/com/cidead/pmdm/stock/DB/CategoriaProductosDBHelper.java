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
        mockItem(sqLiteDatabase, new CategoriaProducto("Monitor","monitor.xml"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Teclado","keyboard_outline.xml"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Raton","mouse.xml"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Torre","desktop_classic.xml"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Smartphone","cellphone.xml"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Auriculares","headphones.xml"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Portatil","laptop.xml"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Impresora","printer.xml"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Escaner","scanner.xml"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Tablet","tablet.xml"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Webcam","webcam.xml"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Cable","cable-48.xml"));
        mockItem(sqLiteDatabase, new CategoriaProducto("Mesa",""));
        mockItem(sqLiteDatabase, new CategoriaProducto("Silla",""));
        mockItem(sqLiteDatabase, new CategoriaProducto("Otros","icono_estudio_fondo_blanco.jpg"));
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
