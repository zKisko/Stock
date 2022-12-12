package com.cidead.pmdm.stock.Workstation.AddEditWorkstation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cidead.pmdm.stock.R;
import com.cidead.pmdm.stock.Workstation.Workstation.WorkstationActivity;

public class AddEditWorkstationActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_WORKSTATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_workstation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String WorkstationId = getIntent().getStringExtra(WorkstationActivity.EXTRA_WORKSTATION_ID);

        setTitle(WorkstationId == null ? "Añadir puesto de trabajo" : "Editar elemento");

        AddEditWorkstationFragment addEditWorkstationFragment = (AddEditWorkstationFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_workstation_container);
        if (addEditWorkstationFragment == null) {
            addEditWorkstationFragment = AddEditWorkstationFragment.newInstance(WorkstationId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_workstation_container, addEditWorkstationFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}