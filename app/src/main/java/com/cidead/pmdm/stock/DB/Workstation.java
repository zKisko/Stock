/*con esta clase creamos las caracteristicas que tendran los puestos de nuestra empresa*/

package com.cidead.pmdm.stock.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;


/**inicio las variables de la plantilla para Workstations*/

public class Workstation {
        private String _id; /* codigo */
        private String wname; /* nombre */


    public Workstation(String wname) {
            this.wname = wname;
    }

    @SuppressLint("Range")
    public Workstation(Cursor cursor) {
        _id = cursor.getString(cursor.getColumnIndex(WorkstationContract.WorkstationEntry._ID));
        wname = cursor.getString(cursor.getColumnIndex(WorkstationContract.WorkstationEntry.WNAME));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(WorkstationContract.WorkstationEntry._ID, _id);
        values.put(WorkstationContract.WorkstationEntry.WNAME, wname);
        return values;
    }

    public String getId() {
            return _id;
        }

    public String getWName() {
            return wname;
        }

    public void setWname(String name){ wname = name; }

    public void setId(String id){ _id = id; }

    }

