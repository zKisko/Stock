package com.cidead.pmdm.stock.Workstation.WorkstationDetail;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cidead.pmdm.stock.R;
import com.cidead.pmdm.stock.Workstation.Workstation.WorkstationActivity;

public class WorkstationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workstation_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra(WorkstationActivity.EXTRA_WORKSTATION_ID);

        WorkstationDetailFragment fragment = (WorkstationDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.content_workstation_detail);
        if (fragment == null) {
            fragment = WorkstationDetailFragment.newInstance(id);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_workstation_detail, fragment)
                    .commit();
        }
    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_items_detail, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onSupportNavigateUp(){
            onBackPressed();
            return true;
        }
    }