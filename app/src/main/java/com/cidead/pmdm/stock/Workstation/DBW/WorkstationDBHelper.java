//CLASE QUE MANEJA LA BASE DE DATOS WORKSTATION

package com.cidead.pmdm.stock.Workstation.DBW;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class WorkstationDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Workstation.db";

    public WorkstationDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + WorkstationContract.WorkstationEntry.TABLE_NAME + " ("
                + WorkstationContract.WorkstationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + WorkstationContract.WorkstationEntry.ID + " TEXT NOT NULL,"
                + WorkstationContract.WorkstationEntry.WNAME + " TEXT NOT NULL,"
                + WorkstationContract.WorkstationEntry.WDESCRIPTION + " TEXT NOT NULL,"
                + "UNIQUE (" + WorkstationContract.WorkstationEntry.ID + "))");

        mockData(db);
    }

    // Insertamos datos ficticios para prueba inicial

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockWorkstation(sqLiteDatabase, new Workstation("Puesto de Kisko", "Monitor LG 24 pulgadas."));
        mockWorkstation(sqLiteDatabase, new Workstation("Puesto de Laura", "Remoto."));

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

    //METODO PARA OBTENER LOS ELEMENTOS POR ID
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

    //METODO PARA LA ELIMINACIÓN DE ELEMENTOS
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