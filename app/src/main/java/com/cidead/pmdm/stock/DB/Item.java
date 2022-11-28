/*con esta clase creamos una plantilla para los elementos que añadiremos
        en los puestos de nuestro stock*/

package com.cidead.pmdm.stock.DB;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

/*inicio las variables de la plantilla*/

public class Item {
        private String id; /* codigo */
        private String name; /* nombre */
        private String quantity; /* cantidad */
        private String condition; /* estado del item */
        private String description; /* descripcion adicional*/
        private String avatarurl; /* imagen del item */

        public Item(String name,
                    String quantity, String condition,
                    String description, String avatarurl) {
            this.id = UUID.randomUUID().toString();
            this.name = name;
            this.quantity = quantity;
            this.condition = condition;
            this.description = description;
            this.avatarurl = avatarurl;
        }

    public Item(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(ItemsContract.ItemEntry.ID));
        name = cursor.getString(cursor.getColumnIndex(ItemsContract.ItemEntry.NAME));
        quantity = cursor.getString(cursor.getColumnIndex(ItemsContract.ItemEntry.QUANTITY));
        condition = cursor.getString(cursor.getColumnIndex(ItemsContract.ItemEntry.CONDITION));
        description = cursor.getString(cursor.getColumnIndex(ItemsContract.ItemEntry.DESCRIPTION));
        avatarurl = cursor.getString(cursor.getColumnIndex(ItemsContract.ItemEntry.AVATARURL));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(ItemsContract.ItemEntry.ID, id);
        values.put(ItemsContract.ItemEntry.NAME, name);
        values.put(ItemsContract.ItemEntry.QUANTITY, quantity);
        values.put(ItemsContract.ItemEntry.CONDITION, condition);
        values.put(ItemsContract.ItemEntry.DESCRIPTION, description);
        values.put(ItemsContract.ItemEntry.AVATARURL, avatarurl);
        return values;
    }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getQuantity() { return quantity; }

        public String getCondition() { return condition; }

        public String getDescription() { return description; }

        public String getAvatarurl() { return avatarurl; }
    }

