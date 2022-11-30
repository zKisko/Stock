/*con esta clase java creo el esquema de la BD para los items*/

package com.cidead.pmdm.stock.Item.DB;

import android.provider.BaseColumns;

public class ItemsContract {

    public static abstract class ItemEntry implements BaseColumns{
        public static final String TABLE_NAME ="Item";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String QUANTITY = "quantity";
        public static final String CONDITION = "condition";
        public static final String DESCRIPTION = "description";
        public static final String AVATARURL = "avatarurl";
    }
}
