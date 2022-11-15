/*con esta clase creamos una plantilla para los elementos que añadiremos
        en los puestos de nuestro stock*/

package com.cidead.pmdm.stock.BaseDeDatos;

import android.content.ContentValues;

import java.util.UUID;

/*inicio las variables de la plantilla*/
public class Items {

        private String id; /* codigo */
        private String name; /* nombre */
        private String quantity; /* cantidad */
        private String condition; /* estado del item */
        private String description; /* descripcion adicional*/
    /* private String avatarUri; */

        public Items(String name,
                          String quantity,
                          String condition,
                          String description)
                /* String avatarUri*/  {
            UUID UUID = null;
            this.id = UUID.randomUUID().toString();
            this.name = name;
            this.quantity = quantity;
            this.condition = condition;
            this.description = description;
            /*this.avatarUri = avatarUri;*/
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getQuantity() {
            return quantity;
        }

        public String getCondition() {
            return condition;
        }

        public String getDescription() {
            return description;
        }

    public ContentValues toContentValues() {
            ContentValues values = new ContentValues();
            values.put(ItemsScheme.ItemsColumsNames.ID, id);
            values.put(ItemsScheme.ItemsColumsNames.NAME, name);
            values.put(ItemsScheme.ItemsColumsNames.QUANTITY, quantity);
            values.put(ItemsScheme.ItemsColumsNames.CONDITION, condition);
            values.put(ItemsScheme.ItemsColumsNames.DESCRIPTION, description);
            return values;
    }

       /* public String getAvatarUri() {
            return avatarUri;
        }*/
    }

