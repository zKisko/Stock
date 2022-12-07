package com.cidead.pmdm.stock.Item.Items;

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

import com.cidead.pmdm.stock.Item.AddEditItems.AddEditItemsActivity;
import com.cidead.pmdm.stock.Item.DB.ItemsContract.ItemEntry;
import com.cidead.pmdm.stock.Item.DB.ItemsDBHelper;
import com.cidead.pmdm.stock.Item.ItemDetail.ItemDetailActivity;
import com.cidead.pmdm.stock.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import static com.cidead.pmdm.stock.Item.DB.CommonVar.*;

/** Vista para la lista de Elementos del puesto de trabajo */

public class ItemsFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_ITEM = 2;

    private ItemsDBHelper itemsDBHelper;

    private ListView ItemsList;
    private ItemsCursorAdapter ItemsAdapter;
    private FloatingActionButton AddButton;

    private String workStation;  // CREAMOS VARIABLE PARA GUARDAR EL WORKSTATION CON EL QUE TRABAJAMOS


    public ItemsFragment() {
        // CONSTRUCTOR VACIO
    }

    public static ItemsFragment newInstance() {
        return new ItemsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_items, container, false);

        // Referencias UI
        ItemsList = (ListView) root.findViewById(R.id.items_list);
        ItemsAdapter = new ItemsCursorAdapter(getActivity(), null);
        AddButton = (FloatingActionButton) getActivity().findViewById(R.id.savebtn);

        // Setup
        ItemsList.setAdapter(ItemsAdapter);

        //Eventos: agregamos una escucha con setOnItemClickListener
        ItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) ItemsAdapter.getItem(i);
                String currentItemId = currentItem.getString(
                        currentItem.getColumnIndex(ItemEntry._ID));

                showDetailScreen(currentItemId);
            }
        });
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });


        getActivity().deleteDatabase(DATABASE_NAME);

        // Instancia de helper
        itemsDBHelper = new ItemsDBHelper(getActivity());

        // CARGA DE DATOS DEL FICHERO TXT
        FileIOWorkstation fichero = new FileIOWorkstation();
        workStation = fichero.leerWorkstation(getContext());
        loadItems(workStation);

        return root;
    }

    //ACTULIZA LA LISTA SI EL RESULTADO ES POSITIVO
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditItemsActivity.REQUEST_ADD_ITEM:
                    showSuccessfullSavedMessage();
                    loadItems(workStation);
                    break;
                case REQUEST_UPDATE_DELETE_ITEM:
                    loadItems(workStation);
                    break;
            }
        }
    }

    private void loadItems() {
        new ItemsLoadTask().execute();
    }


    /** CARGA DE LISTADO DE ITEMS
     * @param workStation  */

    private void loadItems(String workStation) {
        new ItemsForWorkspaceLoadTask().execute();
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
                ItemsAdapter.swapCursor(cursor);
            } else {
                // Mostrar estado vacio
            }
        }
    }

    private class ItemsForWorkspaceLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return itemsDBHelper.getItemsByIdWorkstation(workStation);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                ItemsAdapter.swapCursor(cursor);
            } else {
                // Mostrar estado vacio
            }
        }
    }

}