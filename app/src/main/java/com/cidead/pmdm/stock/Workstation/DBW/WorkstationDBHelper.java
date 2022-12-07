//CLASE QUE MANEJA LA BASE DE DATOS WORKSTATION

package com.cidead.pmdm.stock.Workstation.DBW;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.cidead.pmdm.stock.Item.DB.CommonVar.*;


public class WorkstationDBHelper extends SQLiteOpenHelper {


    public WorkstationDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /** Creamos un Try Catch para comprobar si existe la tabla Workstation.
         * En caso contrario, la creamos y la rellenamos con el contenido del mock de pruebas. */
        if(db.isOpen()){
            // ABRIRIA LA DB SI NO HAY PROBLEMA
        }else{

        }
        try{
            getAllWorkstation();

        }catch(Exception e) {

            db.execSQL("CREATE TABLE " + WorkstationContract.WorkstationEntry.TABLE_NAME + " ("
                    + WorkstationContract.WorkstationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + WorkstationContract.WorkstationEntry.WNAME + " TEXT NOT NULL )");

            mockData(db);
        }
    }

    // Insertamos datos ficticios para prueba inicial

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockWorkstation(sqLiteDatabase, new Workstation("Puesto de Kisko"));
        mockWorkstation(sqLiteDatabase, new Workstation("Puesto de Laura"));

    }

    public long mockWorkstation(SQLiteDatabase db, Workstation workstation) {
        return db.insert(
                WorkstationContract.WorkstationEntry.TABLE_NAME,
                null,
                workstation.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public long saveWorkstation(Workstation workstation) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                WorkstationContract.WorkstationEntry.TABLE_NAME,
                null,
                workstation.toContentValues());

    }

    //METODO PARA OBTENER TODOS LOS ELEMENTOS
    public Cursor getAllWorkstation() {
        return getReadableDatabase().query(
                WorkstationContract.WorkstationEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    //METODO PARA OBTENER LOS PUESTOS DE TRABAJO POR ID
    public Cursor getWorkstationById(String WorkstationId) {
        Cursor c = getReadableDatabase().query(
                WorkstationContract.WorkstationEntry.TABLE_NAME,
                null,
                WorkstationContract.WorkstationEntry.ID + " LIKE ?",
                new String[]{WorkstationId},
                null,
                null,
                null);
        return c;
    }

    //METODO PARA LA ELIMINACIÓN DE PUESTOS DE TRABAJO
    public int deleteWorkstation(String WorkstationId) {
        return getWritableDatabase().delete(
                WorkstationContract.WorkstationEntry.TABLE_NAME,
                WorkstationContract.WorkstationEntry.ID + " LIKE ?",
                new String[]{WorkstationId});
    }

    //METODO PARA ACTUALIZAR ELEMENTOS
    public int updateWorkstation(Workstation workstation, String workstationId) {
        return getWritableDatabase().update(
                WorkstationContract.WorkstationEntry.TABLE_NAME,
                workstation.toContentValues(),
                WorkstationContract.WorkstationEntry.ID + " LIKE ?",
                new String[]{workstationId}
        );
    }
}