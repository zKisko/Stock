/*con esta clase creamos una plantilla para los elementos
que añadiremos en los puestos de nuestro stock*/

package com.cidead.pmdm.stock.Item.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

/*inicio las variables de la plantilla*/

public class Item {

        private int _id; /* codigo del Item */
        private final int id_workstation; /* codigo del equipo */
        private final String name; /* nombre */
        private final String quantity; /* cantidad */
        private final String condition; /* estado del item */
        private final String description; /* descripcion adicional*/
        private final String avatarurl; /* imagen del item */

        public Item(int id_workstation, String name,
                    String quantity, String condition,
                    String description, String avatarurl) {
            this.id_workstation = id_workstation;
            this.name = name;
            this.quantity = quantity;
            this.condition = condition;
            this.description = description;
            this.avatarurl = avatarurl;
        }

    @SuppressLint("Range")
    public Item(Cursor cursor) {
        _id = cursor.getInt(cursor.getColumnIndex(ItemsContract.ItemEntry._ID));
        id_workstation = cursor.getInt(cursor.getColumnIndex(ItemsContract.ItemEntry.IDWORKSTATION));
        name = cursor.getString(cursor.getColumnIndex(ItemsContract.ItemEntry.NAME));
        quantity = cursor.getString(cursor.getColumnIndex(ItemsContract.ItemEntry.QUANTITY));
        condition = cursor.getString(cursor.getColumnIndex(ItemsContract.ItemEntry.CONDITION));
        description = cursor.getString(cursor.getColumnIndex(ItemsContract.ItemEntry.DESCRIPTION));
        avatarurl = cursor.getString(cursor.getColumnIndex(ItemsContract.ItemEntry.AVATARURL));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        //values.put(ItemsContract.ItemEntry._ID, _id);
        values.put(ItemsContract.ItemEntry.IDWORKSTATION, id_workstation);
        values.put(ItemsContract.ItemEntry.NAME, name);
        values.put(ItemsContract.ItemEntry.QUANTITY, quantity);
        values.put(ItemsContract.ItemEntry.CONDITION, condition);
        values.put(ItemsContract.ItemEntry.DESCRIPTION, description);
        values.put(ItemsContract.ItemEntry.AVATARURL, avatarurl);
        return values;
    }

    public int getId() {
        return _id;
    }

    public int getId_workstation(){return id_workstation;}

    public String getName() {
        return name;
    }

    public String getQuantity() { return quantity; }

    public String getCondition() { return condition; }

    public String getDescription() { return description; }

    public String getAvatarurl() { return avatarurl; }
}

