/*con esta clase java creo el esquema de los campos que va a contener la tabla para los items de la BD */

package com.cidead.pmdm.stock.DB;

import android.provider.BaseColumns;

public class ProductosContract {

    public static abstract class ProductosEntry implements BaseColumns{

        public static final String TABLE_NAME ="Productos";

        public static final String _ID = "_id";
        public static String _IDCATEGORIA = "_idCategoria";
        public static final String PRODUCTOS = "productos";
    }
}
