package com.cidead.pmdm.stock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class puestos extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puestos);
    }

    public void agregarcontador (View vista) {

        Intent mas=new Intent(this, contador.class);
        startActivity(mas);

    }

    public void atras (View vista) {

        Intent back=new Intent(this, puestos.class);
        finish();

    }


}
