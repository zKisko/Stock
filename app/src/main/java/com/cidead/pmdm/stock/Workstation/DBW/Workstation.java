/*con esta clase creamos las caracteristicas que tendran los puestos de nuestra empresa*/

package com.cidead.pmdm.stock.Workstation.DBW;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

/*inicio las variables de la plantilla*/

public class Workstation {
        private String id; /* codigo */
        private String wname; /* nombre */
        private String wdescription; /* descripcion adicional*/


        public Workstation(String wname,
                           String wdescription) {
            this.id = UUID.randomUUID().toString();
            this.wname = wname;
            this.wdescription = wdescription;
        }

    public Workstation(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(WorkstationContract.WorkstationEntry.ID));
        wname = cursor.getString(cursor.getColumnIndex(WorkstationContract.WorkstationEntry.WNAME));
        wdescription = cursor.getString(cursor.getColumnIndex(WorkstationContract.WorkstationEntry.WDESCRIPTION));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(WorkstationContract.WorkstationEntry.ID, id);
        values.put(WorkstationContract.WorkstationEntry.WNAME, wname);
        values.put(WorkstationContract.WorkstationEntry.WDESCRIPTION, wdescription);
        return values;
    }

        public String getId() {
            return id;
        }

        public String getWName() {
            return wname;
        }

        public String getWDescription() { return wdescription; }

    }

