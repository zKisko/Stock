package com.cidead.pmdm.stock.AddEditItems;
/*
import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.cidead.pmdm.stock.DB.Item;
import com.cidead.pmdm.stock.DB.ItemsDBHelper;
import com.cidead.pmdm.stock.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class AddEditItemFragment extends Fragment {
    private static final String ARG_ITEMS_ID = "arg_items_id";

    private String mItemsId;

    private ItemsDBHelper mItemsDBHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText nNameField;
    private TextInputEditText nQuantityField;
    private TextInputEditText nConditionField;
    private TextInputEditText nDescriptionField;
    private TextInputLayout nNameLabel;
    private TextInputLayout nQuantityLabel;
    private TextInputLayout nConditionLabel;
    private TextInputLayout nDescriptionLabel;

    public AddEditItemFragment() {
        // Constructor publico vacio
    }

    public static AddEditItemFragment newInstance(String itemsId) {
      AddEditItemFragment fragment = new AddEditItemFragment();
      Bundle args = new Bundle();
      args.putString(ARG_ITEMS_ID, itemsId);
      fragment.setArguments(args);
      return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
      if (getArguments() != null){
           mItemsId = getArguments().getString(ARG_ITEMS_ID);
        }
    }

    @Override //ESTA CLASE VERIFICA EL ID DEL ITEM PARA CARGAR LOS DATOS DE ELEMENTOS EXISTENTES
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_edit_items, container, false);

        //REFERENCIAS UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        nNameField = (TextInputEditText) root.findViewById(R.id.et_name);
        nQuantityField = (TextInputEditText) root.findViewById(R.id.et_quantity);
        nConditionField = (TextInputEditText) root.findViewById(R.id.et_condition);
        nDescriptionField = (TextInputEditText) root.findViewById(R.id.et_description);
        nNameLabel = (TextInputLayout) root.findViewById(R.id.til_name);
        nQuantityLabel = (TextInputLayout) root.findViewById(R.id.til_quantity);
        nConditionLabel = (TextInputLayout) root.findViewById(R.id.til_condition);
        nDescriptionLabel = (TextInputLayout) root.findViewById(R.id.til_description);

        //EVENTOS
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override   //AÑADE ITEMS A TRAVES DEL BOTON SAVEBUTTON
            public void onClick(View view) {
                addEditItems();
            }
        });

        mItemsDBHelper = new ItemsDBHelper(getActivity());

        //CARGA DE DATOS
        if (mItemsId != null){
            loadItems();
        }
        return root;
    }

    private void loadItems() {
        new GetItemsByIdTask().execute();
    }

    private void addEditItems() { /*ESTE METODO EXTRAE LOS DATOS DE LOS CAMPOS DE TEXTO
                                    COMPRUEBA QUE NO ESTAN VACIOS Y LUEGO CREAN EL NUEVO ITEM*/
    /*    boolean error = false;

        String name = nNameField.getText().toString();
        String quantity = nQuantityField.getText().toString();
        String condition = nConditionField.getText().toString();
        String description = nDescriptionField.getText().toString();

        if (TextUtils.isEmpty(name)){
            nNameField.setError("Ingresa un valor");
            error = true;
        }

        if (TextUtils.isEmpty(quantity)){
            nQuantityField.setError("Ingresa un valor");
            error = true;
        }

        if (TextUtils.isEmpty(condition)){
            nConditionField.setError("Ingresa un valor");
            error = true;
        }

        if (TextUtils.isEmpty(description)){
            nDescriptionField.setError("Ingresa un valor");
            error = true;
        }

        if (error){
            return;
        }

        Item item = new Item(name, quantity, condition, description, "");

        new AddEditItemsTask().execute(item);
    }
    private class AddEditItemsTask extends AsyncTask<Item, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Item... items) {
            if (mItemsId != null) {
                return mItemsDBHelper.updateItems(items[0], mItemsId) > 0;

            } else {
                return mItemsDBHelper.saveItems(items[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showItemsScreen(result);
        }

    }
    /* DEPENDIENDO DE LA TAREA ASINCRONA DE ARRIBA (UPDATE O SAVE)
         ESTA ACTIVIDAD NOS MOSTRARÁ EL NUEVO RESULTADO O
          UN MENSAJE DE ERROR */
/*
    private void showItemsScreen(Boolean requery) {
        if (!requery){
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        }else{
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void showItems (Item item){
        nNameField.setText(item.getName());
        nQuantityField.setText(item.getQuantity());
        nConditionField.setText(item.getCondition());
        nDescriptionField.setText(item.getDescription());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar elemento", Toast.LENGTH_SHORT).show();
    }

    private class GetItemsByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mItemsDBHelper.getItemById(mItemsId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showItems(new Item(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }
    }
}
--------------------------------------------------------------------------------------------------*/

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import com.cidead.pmdm.stock.DB.Item;
import com.cidead.pmdm.stock.DB.ItemsDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cidead.pmdm.stock.R;

import androidx.fragment.app.Fragment;
/**
 * Vista para creación/edición de un abogado
 */
public class AddEditItemFragment extends Fragment {
    private static final String ARG_ITEM_ID = "arg_item_id";

    private String mItemId;

    private ItemsDBHelper mitemsDBHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText mNameField;
    private TextInputEditText mQuantityField;
    private TextInputEditText mConditionField;
    private TextInputEditText mDescriptionField;
    private TextInputLayout mNameLabel;
    private TextInputLayout mQuantityLabel;
    private TextInputLayout mConditionLabel;
    private TextInputLayout mDescriptionLabel;


    public AddEditItemFragment() {
        // Required empty public constructor
    }

    public static AddEditItemFragment newInstance(String itemId) {
        AddEditItemFragment fragment = new AddEditItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_ID, itemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItemId = getArguments().getString(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_item, container, false);

        // Referencias UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mNameField = (TextInputEditText) root.findViewById(R.id.et_name);
        mQuantityField = (TextInputEditText) root.findViewById(R.id.et_quantity);
        mConditionField = (TextInputEditText) root.findViewById(R.id.et_condition);
        mDescriptionField = (TextInputEditText) root.findViewById(R.id.et_description);
        mNameLabel = (TextInputLayout) root.findViewById(R.id.til_name);
        mQuantityLabel = (TextInputLayout) root.findViewById(R.id.til_quantity);
        mConditionLabel = (TextInputLayout) root.findViewById(R.id.til_condition);
        mDescriptionLabel = (TextInputLayout) root.findViewById(R.id.til_description);

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditItem();
            }
        });

        mitemsDBHelper = new ItemsDBHelper(getActivity());

        // Carga de datos
        if (mItemId != null) {
            loadItem();
        }

        return root;
    }

    private void loadItem() {
        new GetItemByIdTask().execute();
    }

    private void addEditItem() {
        boolean error = false;

        String name = mNameField.getText().toString();
        String quantity = mQuantityField.getText().toString();
        String condition = mConditionField.getText().toString();
        String description = mDescriptionField.getText().toString();

        if (TextUtils.isEmpty(name)) {
            mNameLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(quantity)) {
            mQuantityLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(condition)) {
            mConditionLabel.setError(getString(R.string.field_error));
            error = true;
        }


        if (TextUtils.isEmpty(description)) {
            mDescriptionLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }

        Item item = new Item(name, condition, quantity, description, "");

        new AddEditItemTask().execute(item);

    }

    private void showItemsScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void showItem(Item item) {
        mNameField.setText(item.getName());
        mQuantityField.setText(item.getQuantity());
        mConditionField.setText(item.getCondition());
        mDescriptionField.setText(item.getDescription());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar abogado", Toast.LENGTH_SHORT).show();
    }

    private class GetItemByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mitemsDBHelper.getItemById(mItemId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showItem(new Item(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditItemTask extends AsyncTask<Item, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Item... items) {
            if (mItemId != null) {
                return mitemsDBHelper.updateItem(items[0], mItemId) > 0;

            } else {
                return mitemsDBHelper.saveItem(items[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showItemsScreen(result);
        }

    }

}