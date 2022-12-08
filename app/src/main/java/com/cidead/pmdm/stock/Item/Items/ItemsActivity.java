package com.cidead.pmdm.stock.Item.Items;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cidead.pmdm.stock.R;

public class ItemsActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM_ID = "extra_item_id";
    public static final String EXTRA_WORKSTATION_ID = "extra_workstation_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        Intent intent=getIntent();
        String extraWorkstationId = intent.getStringExtra(EXTRA_WORKSTATION_ID);

        /** INSTANCIAMOS EL FICHERO FileIOWorkstation PARA DESPÚES LLAMAR
          AL MÉTODO guardarWorkstation Y GUARDAR EL DATO PARA FILTRAR */

        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para leer.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i("Mensaje", "Se tiene permiso para leer y escribir!");
        }

        FileIOWorkstation fichero = new FileIOWorkstation();
        fichero.guardarWorkStation(extraWorkstationId, getApplicationContext());

        // CON ESTAS LINEAS LO QUE ESTAMOS PIDIENDO PERMISO PARA AÑADIR ARCHIVOS EXTERNAMENTE


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //CON ESTA LINEA AÑADIMOS EL ICONO DE FLECHA DE RETROCESO


        ItemsFragment fragment = (ItemsFragment)
                getSupportFragmentManager().findFragmentById(R.id.content_items);

        if (fragment == null) {
            fragment = ItemsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_items, fragment)
                    .commit();
        }
    }

    //CON ESTE METODO HACEMOS QUE NUESTRA FLECHA REGRESE A LA PANTALLA ANTERIOR
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}