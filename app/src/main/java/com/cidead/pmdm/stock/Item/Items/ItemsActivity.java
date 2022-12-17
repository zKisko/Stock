package com.cidead.pmdm.stock.Item.Items;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cidead.pmdm.stock.R;
import com.cidead.pmdm.stock.Workstation.Workstation.WorkstationActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ItemsActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM_ID = "extra_item_id";
    public static final String EXTRA_WORKSTATION_ID = "extra_workstation_id";

    private FloatingActionButton informacion; // VARIABLE QUE NOS SIRVE PARA EL MENSAJE EMERGENTE DE INFORMACIÓN

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        Intent intent = getIntent();
        String extraWorkstationId = intent.getStringExtra(EXTRA_WORKSTATION_ID);

        /** INSTANCIAMOS EL FICHERO FileIOWorkstation PARA DESPÚES LLAMAR
         AL MÉTODO guardarWorkstation Y GUARDAR EL DATO PARA FILTRAR */

        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        } else {
        }
        if (extraWorkstationId != null) {
            FileIOWorkstation fichero = new FileIOWorkstation();
            fichero.guardarWorkStation(extraWorkstationId, getApplicationContext());
        }
        // CON ESTAS LINEAS LO QUE ESTAMOS PIDIENDO PERMISO PARA AÑADIR ARCHIVOS EXTERNAMENTE

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //CON ESTA LINEA AÑADIMOS EL ICONO DE FLECHA DE RETROCESO

        // CON ESTOS METODOS PROGRAMAMOS LA VENTANA EMERGENTE CON LA INFORMACION QUE QUEREMOS MOSTRAR

        informacion = (FloatingActionButton) findViewById(R.id.informacionItems);
        informacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(ItemsActivity.this);
                info.setMessage("Añade los elementos a los puestos de trabajo pulsando el botón de la parte inferior derecha con el simbolo + ")
                        .setCancelable(false)
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() { // CREO EL OK PARA CERRRAR LA PANTALLA
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog titulo = info.create();
                titulo.setTitle("Información:");
                titulo.show();       // CON ESTOS METODOS AÑADIMOS TITULO Y HACEMOS QUE SE MUESTRE LA PANTALLA
            }
        });

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

    @Override
    public boolean onSupportNavigateUp() { /** METODO QUE AL PULSAR LA FLECHA ATRAS VUELVA A LA PANTALLA
                                            INMEDIATAMENTE ANTERIOR*/
        Context context = this.getBaseContext();
        Intent intent = new Intent(context, WorkstationActivity.class);
        startActivityForResult(intent, 2);
        return true;
    }
}