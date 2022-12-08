package com.cidead.pmdm.stock.Workstation.Workstation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cidead.pmdm.stock.R;

public class WorkstationActivity extends AppCompatActivity {

    public static final String EXTRA_WORKSTATION_ID = "extra_workstation_id";

    private Button informacion; // VARIABLE QUE NOS SIRVE PARA EL MENSAJE EMERGENTE DE INFORMACIÓN

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workstation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // CON ESTOS METODOS PROGRAMAMOS LA VENTANA EMERGENTE CON LA INFORMACION QUE QUEREMOS MOSTRAR

        informacion = (Button)findViewById(R.id.informacion);
        informacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(WorkstationActivity.this);
                info.setMessage("En esta pantalla puedes añadir los puestos de trabajo pulsando el botón ubicado en la parte inferior derecha que contiene el simbolo + ")
                        .setCancelable(false)
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener(){ // CREO EL OK PARA CERRRAR LA PANTALLA
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                dialog.cancel();
                            }
                });
                AlertDialog titulo = info.create();
                titulo.setTitle("Información:");
                titulo.show(); // CON ESTOS METODOS AÑADIMOS TITULO Y HACEMOS QUE SE MUESTRE LA PANTALLA
            }
        });

        WorkstationFragment fragment = (WorkstationFragment)
                getSupportFragmentManager().findFragmentById(R.id.content_workstation);

        if (fragment == null) {
            fragment = WorkstationFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_workstation, fragment)
                    .commit();
        }
    }
}