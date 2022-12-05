package com.cidead.pmdm.stock.Workstation.Workstation;

/* Esta clase hace de adaptardor de los Puestos de trabajo*/

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.cidead.pmdm.stock.R;
import com.cidead.pmdm.stock.Workstation.DBW.WorkstationContract.WorkstationEntry;


public class WorkstationCursorAdapter extends CursorAdapter {
    public WorkstationCursorAdapter(Context context, Cursor c) { super(context, c, 0); }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_workstation, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        //Referencia UI.
        TextView nameText = (TextView) view.findViewById(R.id.tw_name);

        //Recoger los valores
       String wname = cursor.getString(cursor.getColumnIndex(WorkstationEntry.WNAME));

        //Setup
        nameText.setText(wname);

    }

}
