/* con esta clase java creo el esquema de la BD para los Workstation */

package com.cidead.pmdm.stock.Workstation.DBW;

import android.provider.BaseColumns;

public class WorkstationContract {

    public static abstract class WorkstationEntry implements BaseColumns{
        public static final String TABLE_NAME ="Workstation";
        public static final String ID = "id";
        public static final String WNAME = "wname";
        public static final String WDESCRIPTION = "wdescription";

    }
}
