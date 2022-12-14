package com.cidead.pmdm.stock.Item.Items;

/** CON ESTA CLASE HAREMOS QUE CADA ITEM TENGA SU ICONO CORRESPONDIENTE*/

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.cidead.pmdm.stock.R;
import com.cidead.pmdm.stock.DB.ItemsContract.ItemEntry;


public class ItemsCursorAdapter extends CursorAdapter {
    private TextView nameText;
    private ImageView avatarImage;

    public ItemsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_items, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        //Referencia UI.
        nameText = (TextView) view.findViewById(R.id.tv_name);
        avatarImage = (ImageView) view.findViewById(R.id.iv_avatarurl);

        //Recoger los valores
        int idName = cursor.getColumnIndex(ItemEntry.NAME);
        int idAvatarURL = cursor.getColumnIndex(ItemEntry.AVATARURL);
        String name = cursor.getString(idName);
        String avatar = cursor.getString(idAvatarURL);
        avatar = "monitor.webp";
        nameText.setText(name);
        Uri URI = Uri.parse("file:app/src/main/assets/" + avatar);
        if (avatar != null) {
            Glide
                    .with(context)
                    .asBitmap()
                    .load(URI)
                    .error(R.drawable.icono_estudio_fondo_negro)
                    .centerCrop()
                    .into(new BitmapImageViewTarget(avatarImage) {
                        @Override
                        protected void setResource(Bitmap resource){
                            RoundedBitmapDrawable drawable
                                    = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            drawable.setCircular(true);
                            avatarImage.setImageDrawable(drawable);
                        }
                    });
        }
    }
}
