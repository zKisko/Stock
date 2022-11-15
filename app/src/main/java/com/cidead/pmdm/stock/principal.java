package com.cidead.pmdm.stock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cidead.pmdm.stock.BaseDeDatos.Items;

public class principal extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.principal);
   }
    public void ejecutarinfo (View vista) {

        Intent i=new Intent(this, info.class);
        startActivity(i);
    }

    public void agregarpuesto (View vista) {

        Intent p=new Intent(this, puestos.class);
        startActivity(p);


    }

    public void guardardatos (Items gestiondedatos){

    }

    public void saliraplicacion (View vista){

        finish();

    }



}
