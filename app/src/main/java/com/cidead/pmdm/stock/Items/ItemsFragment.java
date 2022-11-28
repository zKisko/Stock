package com.cidead.pmdm.stock.Items;
/*
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cidead.pmdm.stock.AddEditItems.AddEditItemsActivity;
import com.cidead.pmdm.stock.DB.ItemsContract;
import com.cidead.pmdm.stock.ItemDetail.ItemDetailActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.cidead.pmdm.stock.DB.ItemsDBHelper;
import com.cidead.pmdm.stock.R;

/* Vista para la lista de Item de nuestra Workstation */
/*

public class ItemsFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_ITEM = 2;

    private ItemsDBHelper mItemsDBHelper;

    private ListView mItemsList;
    private ItemsCursorAdapter mItemsAdapter;
    private FloatingActionButton mAddButton;

    public ItemsFragment() {
        //CONSTRUCTOR VACÍO
    }

    public static ItemsFragment newInstance() {
        return new ItemsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_items, container, false);

        //Referencias de UI
        mItemsList = (ListView) root.findViewById(R.id.items_list);
        mItemsAdapter = new ItemsCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        //Setup
        mItemsList.setAdapter(mItemsAdapter);

        //Eventos: agregamos una escucha con setOnItemClickListener

        mItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mItemsAdapter.getItem(i);
                @SuppressLint("Range") String currentLawyerId = currentItem.getString(
                        currentItem.getColumnIndex(ItemsContract.ItemsEntr.ID));

                showDetailScreen(currentLawyerId);
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });

        getActivity().deleteDatabase(ItemsDBHelper.DATABASE_NAME);

        //Instancia de helper
        mItemsDBHelper = new ItemsDBHelper(getActivity());

        //Carga de datos
        loadItems();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditItemsActivity.REQUEST_ADD_ITEMS:
                    showSuccessfullSavedMessage();
                    loadItems();
                    break;
                case REQUEST_UPDATE_DELETE_ITEM:
                    loadItems();
                    break;
            }
        }
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Elemento guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditItemsActivity.class);
        startActivityForResult(intent, AddEditItemsActivity.REQUEST_ADD_ITEMS);
    }

    public void onAcivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) { //ACTULIZA LA LISTA SI EL RESULTADO ES POSITIVO
            switch (requestCode) {
                case AddEditItemsActivity.REQUEST_ADD_ITEMS:
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

    private void showDetailScreen(String ItemsId) { //INICIAMOS LA ACTIVIDAD DE DETALLE
        Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
        intent.putExtra(ItemsActivity.EXTRA_ITEM_ID, ItemsId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_ITEM);
    }
        private class ItemsLoadTask extends AsyncTask<Void, Void, Cursor> {

            @Override
            protected Cursor doInBackground(Void... voids) {
                return mItemsDBHelper.getAllItems();
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                if (cursor != null && cursor.getCount() > 0) {
                    mItemsAdapter.swapCursor(cursor);
                } else {
                    //Mostrar estado vacío
                }
            }
        }

}
------------------------------------------------------------------------------------------------- */



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

/**
 * Vista para la lista de abogados del gabinete
 */
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

        // Eventos
        mItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mItemsAdapter.getItem(i);
                String currentLawyerId = currentItem.getString(
                        currentItem.getColumnIndex(ItemEntry.ID));

                showDetailScreen(currentLawyerId);
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
                "Abogado guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditItemsActivity.class);
        startActivityForResult(intent, AddEditItemsActivity.REQUEST_ADD_ITEM);
    }

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
                // Mostrar empty state
            }
        }
    }

}