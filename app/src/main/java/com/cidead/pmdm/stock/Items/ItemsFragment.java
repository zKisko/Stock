package com.cidead.pmdm.stock.Items;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.cidead.pmdm.stock.AddEditItems.AddEditItemsActivity;
import com.cidead.pmdm.stock.DB.ItemsContract.ItemEntry;
import com.cidead.pmdm.stock.DB.ItemsDBHelper;
import com.cidead.pmdm.stock.ItemDetail.ItemDetailActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.cidead.pmdm.stock.R;

/* Vista para la lista de Elementos del puesto de trabajo */

public class ItemsFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_ITEM = 2;

    private ItemsDBHelper itemsDBHelper;

    private ListView mItemsList;
    private ItemsCursorAdapter mItemsAdapter;
    private FloatingActionButton mAddButton;


    public ItemsFragment() {
        // Required empty public constructor
    }

    public static ItemsFragment newInstance() {
        return new ItemsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_items, container, false);

        // Referencias UI
        mItemsList = (ListView) root.findViewById(R.id.items_list);
        mItemsAdapter = new ItemsCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        mItemsList.setAdapter(mItemsAdapter);

        //Eventos: agregamos una escucha con setOnItemClickListener
        mItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mItemsAdapter.getItem(i);
                String currentItemId = currentItem.getString(
                        currentItem.getColumnIndex(ItemEntry.ID));

                showDetailScreen(currentItemId);
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });


        getActivity().deleteDatabase(itemsDBHelper.DATABASE_NAME);

        // Instancia de helper
        itemsDBHelper = new ItemsDBHelper(getActivity());

        // Carga de datos
        loadItems();

        return root;
    }

    //ACTULIZA LA LISTA SI EL RESULTADO ES POSITIVO
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditItemsActivity.REQUEST_ADD_ITEM:
                    showSuccessfullSavedMessage();
                    loadItems();
                    break;
                case REQUEST_UPDATE_DELETE_ITEM:
                    loadItems();
                    break;
            }
        }
    }

    private void loadItems() {
        new ItemsLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Elemento guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditItemsActivity.class);
        startActivityForResult(intent, AddEditItemsActivity.REQUEST_ADD_ITEM);
    }

    //INICIAMOS LA ACTIVIDAD DE DETALLE
    private void showDetailScreen(String ItemId) {
        Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
        intent.putExtra(ItemsActivity.EXTRA_ITEM_ID, ItemId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_ITEM);
    }

    private class ItemsLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return itemsDBHelper.getAllItems();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mItemsAdapter.swapCursor(cursor);
            } else {
                // Mostrar estado vacio
            }
        }
    }

}