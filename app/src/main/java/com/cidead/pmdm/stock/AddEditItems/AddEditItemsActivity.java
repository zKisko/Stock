package com.cidead.pmdm.stock.AddEditItems;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.cidead.pmdm.stock.Items.ItemsActivity;
import com.cidead.pmdm.stock.R;

public class AddEditItemsActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_ITEM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String ItemsId = getIntent().getStringExtra(ItemsActivity.EXTRA_ITEM_ID);

        setTitle(ItemsId == null ? "Añadir elemento" : "Editar elemento");

        AddEditItemFragment addEditItemsFragment = (AddEditItemFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_items_container);
        if (addEditItemsFragment == null) {
            addEditItemsFragment = AddEditItemFragment.newInstance(ItemsId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_items_container, addEditItemsFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}