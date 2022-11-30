package com.cidead.pmdm.stock.Item.ItemDetail;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cidead.pmdm.stock.Item.Items.ItemsActivity;
import com.cidead.pmdm.stock.R;

public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra(ItemsActivity.EXTRA_ITEM_ID);

        ItemDetailFragment fragment = (ItemDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.content_item_detail);
        if (fragment == null) {
            fragment = ItemDetailFragment.newInstance(id);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_item_detail, fragment)
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