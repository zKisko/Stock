package com.cidead.pmdm.stock.Items;

/* Esta clase hace de adaptardor de los Item*/

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.cidead.pmdm.stock.R;
import com.cidead.pmdm.stock.DB.ItemsContract.ItemEntry;


public class ItemsCursorAdapter extends CursorAdapter {
    public ItemsCursorAdapter(Context context, Cursor c) { super(context, c, 0); }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_items, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        //Referencia UI.
        TextView nameText = (TextView) view.findViewById(R.id.tv_name);
        final ImageView avatarImage = (ImageView) view.findViewById(R.id.iv_avatar);

        //Recoger los valores
        String name = cursor.getString(cursor.getColumnIndex(ItemEntry.NAME));
        String avatar = cursor.getString(cursor.getColumnIndex(ItemEntry.AVATARURL));

        //Setup
        nameText.setText(name);
       /* Glide
                .with(context)
                .asBitmap()
                .load(Uri.parse("file:app/src/main/assets" + avatar))
                .error(R.drawable.ic_menu_mapmode)
                .centerCrop()
                .into(new BitmapImageViewTarget(avatarImage) {
                    @Override
                    protected void setResource(Bitmap resource){
                        RoundedBitmapDrawable drawable
                                = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        drawable.setCircular(true);
                        avatarImage.setImageDrawable(drawable);
                    }
                });*/

    }

}
