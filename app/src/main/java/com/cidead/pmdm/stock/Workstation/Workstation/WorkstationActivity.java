package com.cidead.pmdm.stock.Workstation.Workstation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cidead.pmdm.stock.R;

public class WorkstationActivity extends AppCompatActivity {

    public static final String EXTRA_WORKSTATION_ID = "extra_workstation_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workstation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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