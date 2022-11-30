package com.cidead.pmdm.stock.Item.Items;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.cidead.pmdm.stock.R;

public class ItemsActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM_ID = "extra_item_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ItemsFragment fragment = (ItemsFragment)
                getSupportFragmentManager().findFragmentById(R.id.items_container);

        if (fragment == null) {
            fragment = ItemsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.items_container, fragment)
                    .commit();
        }
    }
}