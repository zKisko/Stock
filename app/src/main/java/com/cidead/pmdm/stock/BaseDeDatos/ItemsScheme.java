/*con esta clase java creo el esquema de la BD para los items*/

package com.cidead.pmdm.stock.BaseDeDatos;

import android.provider.BaseColumns;

public class ItemsScheme {

    public static abstract class ItemsColumsNames implements BaseColumns{
        public static final String TABLE_NAME = "Items";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String QUANTITY = "quantity";
        public static final String CONDITION = "condition";
        public static final String DESCRIPTION = "description";


    }


}
