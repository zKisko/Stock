/*con esta clase java creo el esquema de los campos que va a contener la tabla para los items de la BD */

package com.cidead.pmdm.stock.DB;

import android.provider.BaseColumns;

public class CategoriaProductosContract {

    public static abstract class CategoriaProductosEntry implements BaseColumns{

        public static final String TABLE_NAME ="CategoriaProductos";

        public static final String _ID = "_id";
        public static String CATEGORIA = "Categoria";
        public static String IMAGEN = "Imagen";
    }
}
