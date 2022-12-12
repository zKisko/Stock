package com.cidead.pmdm.stock.Item.AddEditItems;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import com.cidead.pmdm.stock.Item.DB.Item;
import com.cidead.pmdm.stock.Item.DB.ItemsDBHelper;
import com.cidead.pmdm.stock.Item.ItemDetail.ItemDetailActivity;
import com.cidead.pmdm.stock.Item.Items.FileIOWorkstation;
import com.cidead.pmdm.stock.Item.Items.ItemsActivity;
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

/* Vista para creación/edición de un Item */

public class AddEditItemFragment extends Fragment {
    private static final String ARG_ITEM_ID = "arg_item_id";

    private String ItemId;

    private ItemsDBHelper itemsDBHelper;

    private FloatingActionButton SaveButton;
    private TextInputEditText NameField;
    private TextInputEditText QuantityField;
    private TextInputEditText ConditionField;
    private TextInputEditText DescriptionField;
    private TextInputLayout NameLabel;
    private TextInputLayout QuantityLabel;
    private TextInputLayout ConditionLabel;
    private TextInputLayout DescriptionLabel;

    private Integer idWorkstation;


    public AddEditItemFragment() {
        // CONTRUCTOR VACIO
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
            ItemId = getArguments().getString(ARG_ITEM_ID);
        }
    }

    @Override  //ESTA CLASE VERIFICA EL IDWORKSTATION DEL ITEM PARA CARGAR LOS DATOS DE ELEMENTOS EXISTENTES
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_item, container, false);

        // Referencias UI
        SaveButton = (FloatingActionButton) getActivity().findViewById(R.id.savebtn);

        //Campos
        NameField = (TextInputEditText) root.findViewById(R.id.f_name);
        QuantityField = (TextInputEditText) root.findViewById(R.id.f_quantity);
        ConditionField = (TextInputEditText) root.findViewById(R.id.f_condition);
        DescriptionField = (TextInputEditText) root.findViewById(R.id.f_description);
        //Etiquetas
        NameLabel = (TextInputLayout) root.findViewById(R.id.l_name);
        QuantityLabel = (TextInputLayout) root.findViewById(R.id.l_quantity);
        ConditionLabel = (TextInputLayout) root.findViewById(R.id.l_condition);
        DescriptionLabel = (TextInputLayout) root.findViewById(R.id.l_description);

        // Eventos  //AÑADE ITEMS A TRAVES DEL BOTON SAVEBUTTON
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditItem();
            }
        });

        itemsDBHelper = new ItemsDBHelper(getActivity());

        // Carga de datos
        if (ItemId != null) {
            loadItem();
        }

        return root;
    }

    private void loadItem() {
        new GetItemByIdTask().execute();
    }


    /*ESTE METODO EXTRAE LOS DATOS DE LOS CAMPOS DE TEXTO,
     COMPRUEBA QUE NO ESTAN VACIOS Y LUEGO CREAN EL NUEVO ITEM*/
    private void addEditItem() {
        boolean error = false;

        FileIOWorkstation fichero = new FileIOWorkstation(); //CREAMOS LA VARIABLE FICHERO CON LA CARGA DE LOS DATOS
        idWorkstation = Integer.valueOf(fichero.leerWorkstation(getContext())); // LEEMOS EL DATO idWorkstation DE FICHERO Y LO PASAMOS A UN ENTERO

        String name = NameField.getText().toString();
        String quantity = QuantityField.getText().toString();
        String condition = ConditionField.getText().toString();
        String description = DescriptionField.getText().toString();

        if (TextUtils.isEmpty(name)) {
            NameLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(quantity)) {
            QuantityLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(condition)) {
            ConditionLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(description)) {
            DescriptionLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }

        Item item = new Item(idWorkstation, name, quantity,condition,description,"");

        new AddEditItemTask().execute(item);

    }

    /**
     * @param requery
     * @param idItem
     *
     * Creamos este método para que una vez editado el item, vuelva a la pantalla del detalle de item
     */

    private void showItemScreen(Boolean requery, String idItem) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
            intent.putExtra(ItemsActivity.EXTRA_ITEM_ID, idItem);
            startActivityForResult(intent, 2);
        }
        //getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void showItem(Item item) {
        NameField.setText(item.getName());
        QuantityField.setText(item.getQuantity());
        ConditionField.setText(item.getCondition());
        DescriptionField.setText(item.getDescription());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar el elemento", Toast.LENGTH_SHORT).show();
    }

    private class GetItemByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return itemsDBHelper.getItemById(ItemId);
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
            if (ItemId != null) {
                return itemsDBHelper.updateItem(items[0], ItemId) > 0;

            } else {
                return itemsDBHelper.saveItem(items[0]) > 0;
            }
        }
         /* DEPENDIENDO DE LA TAREA ASINCRONA DE ARRIBA (UPDATE O SAVE)
         ESTA ACTIVIDAD NOS MOSTRARÁ EL NUEVO RESULTADO O UN MENSAJE DE ERROR */
        @Override
        protected void onPostExecute(Boolean result) {
            showItemScreen(result, ItemId);
        }
    }
}