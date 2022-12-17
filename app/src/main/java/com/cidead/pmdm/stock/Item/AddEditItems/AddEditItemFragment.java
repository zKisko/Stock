package com.cidead.pmdm.stock.Item.AddEditItems;

import static com.cidead.pmdm.stock.DB.CategoriaProductosContract.CategoriaProductosEntry.CATEGORIA;
import static com.cidead.pmdm.stock.DB.CategoriaProductosContract.CategoriaProductosEntry._ID;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import com.cidead.pmdm.stock.DB.CategoriaProducto;
import com.cidead.pmdm.stock.DB.CategoriaProductosContract;
import com.cidead.pmdm.stock.DB.CategoriaProductosDBHelper;
import com.cidead.pmdm.stock.DB.Item;
import com.cidead.pmdm.stock.DB.ItemsContract;
import com.cidead.pmdm.stock.DB.ItemsDBHelper;
import com.cidead.pmdm.stock.DB.ProductosContract;
import com.cidead.pmdm.stock.DB.ProductosDBHelper;
import com.cidead.pmdm.stock.Item.ItemDetail.ItemDetailActivity;
import com.cidead.pmdm.stock.Item.Items.FileIOWorkstation;
import com.cidead.pmdm.stock.Item.Items.ItemsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.cidead.pmdm.stock.R;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

// ESTA CLASE INTERACCIONA CON EL LAYOUT FRAGMENT_ADD_EDIT_iTEM PARA LA CREACION/EDICION DE LOS ELEMENTOS DE LOS PUESTOS

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

    private Spinner sCategoria;
    private Spinner sProducto;
    private Cursor categorias;
    private ProductosDBHelper ProductosDBHelper;
    private CategoriaProductosDBHelper categoriasDBHelper;
    private SimpleCursorAdapter categoriaCursorAdapter;
    private SimpleCursorAdapter productosCursorAdapter;
    private Cursor cursorCategorias;
    private Cursor cursorProductos;
    private int productoActual;
    private int categoriaActual;
    private Button SalvarCategoria;
    private CategoriaProductosDBHelper categoriaProductosDBHelper;


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
        QuantityField = (TextInputEditText) root.findViewById(R.id.f_quantity);
        ConditionField = (TextInputEditText) root.findViewById(R.id.f_condition);
        DescriptionField = (TextInputEditText) root.findViewById(R.id.f_description);
        //Etiquetas
        QuantityLabel = (TextInputLayout) root.findViewById(R.id.l_quantity);
        ConditionLabel = (TextInputLayout) root.findViewById(R.id.l_condition);
        DescriptionLabel = (TextInputLayout) root.findViewById(R.id.l_description);

        /** inicio las variables para el spinner y las acciones del spinner si hay algun elemento seleccionado o no*/
        sCategoria = (Spinner) root.findViewById(R.id.s_Categoria);
        sProducto = (Spinner) root.findViewById(R.id.s_Productos);

        sCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (ItemId == null) {
                    cargarSpinnerProductos(id); //CARGA EL SPINNER SI NO HAY UN REGISTRO ANTERIOR
                }                               //ESTO SE HACE PARA QUE AL EDITAR NO SE MUEVA DEL CAMPO SELECCIONADO
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sProducto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                com.cidead.pmdm.stock.DB.ProductosDBHelper productoSeleccionado = new ProductosDBHelper(getContext());
                Cursor producto = productoSeleccionado.getProductoById(String.valueOf(id));
                int productoIndex = producto.getColumnIndex(ProductosContract.ProductosEntry._ID);
                if (producto.getCount()>0){
                    while (producto.moveToNext()) {
                        productoActual = producto.getInt(productoIndex);
                        Log.i("AddEditItemFragment", "");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        llenarCategorias();

        SaveButton.setOnClickListener(new View.OnClickListener() { /**AÑADE ITEMS A TRAVES DEL BOTON SAVEBUTTON*/
            @Override
            public void onClick(View view) {
                addEditItem();
            }
        });
        itemsDBHelper = new ItemsDBHelper(getActivity()); // Carga de datos
        if (ItemId != null) {
            loadItem();
        }
        return root;
    }

        //SalvarCategoria = (Button) SalvarCategoria.findViewById(R.id.salvarcategoria);
       /*SalvarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCategoria();
            }
        });*/


   private void addCategoria() {            /** CON ESTE METODO VAMOS A EDITAR
                                            LA VENTANA EMERGENTE PARA QUE NOS MUESTRE UNA FRASE Y LAS OPCIONES DE EDITAR Y BORRAR*/

       /* AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Añadir categoria");

        final EditText input = new EditText(this.getContext()); // Configuramos el input

        input.setFocusable(true); //Enviamos el foco al campo a editar
        input.requestFocus();

        input.setInputType(InputType.TYPE_CLASS_TEXT); /** Especificamos el tipo de imput que
         queremos, en nuestro caso, tipo de texto plano

        input.setText("Categoría"); // Añadimos el input al dialog.
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombreCategoria = input.getText().toString();
                CategoriaProducto categoria = new CategoriaProducto( nombreCategoria, "");
                categoriasDBHelper.saveCategoriaProducto(categoria);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();*/
    }

    /**@author: Kisko
     * Recargamos el spinner de productos filtrando por categoría
     * @param id */
    private void cargarSpinnerProductos(long id) {
        String[] queryCols = new String[]{_ID, ProductosContract.ProductosEntry.PRODUCTO};
        String[] idCategoria = new String[]{String.valueOf(id)};
        ProductosDBHelper = new ProductosDBHelper(this.getContext());

        SQLiteDatabase db = new ProductosDBHelper(this.getContext()).getReadableDatabase();
        ProductosDBHelper.onCreate(db);
        cursorProductos  = db.query(
                ProductosContract.ProductosEntry.TABLE_NAME, // the table to query
                queryCols,                // the columns to return
                ProductosContract.ProductosEntry._IDCATEGORIA+ " LIKE ?", // the columns for the WHERE clause
                idCategoria,              // the values for the WHERE clause
                null,                     // don't group the rows
                null,                     // don't filter by row groups
                null                      // don't sort
        );

        String[] adapterCols = new String[]{ProductosContract.ProductosEntry.PRODUCTO};
        int[] adapterRowViews = new int[]{android.R.id.text1};

        productosCursorAdapter = new SimpleCursorAdapter(
                this.getContext(), android.R.layout.simple_spinner_item, cursorProductos, adapterCols, adapterRowViews, 0);
        productosCursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sProducto.setAdapter(productosCursorAdapter);
    }

    private void llenarCategorias() {
        String[] queryCols = new String[]{_ID, CATEGORIA};
        SQLiteDatabase db = new CategoriaProductosDBHelper(this.getContext()).getReadableDatabase();
        categoriaProductosDBHelper = new CategoriaProductosDBHelper(this.getContext());
        categoriaProductosDBHelper.onCreate(db);
        cursorCategorias = db.query(
                CategoriaProductosContract.CategoriaProductosEntry.TABLE_NAME, // the table to query
                queryCols,                // the columns to return
                null,                     // the columns for the WHERE clause
                null,                     // the values for the WHERE clause
                null,                     // don't group the rows
                null,                     // don't filter by row groups
                null                      // don't sort
        );

        String[] adapterCols = new String[]{CATEGORIA};
        int[] adapterRowViews = new int[]{android.R.id.text1};

        categoriaCursorAdapter = new SimpleCursorAdapter(
                this.getContext(), android.R.layout.simple_spinner_item, cursorCategorias, adapterCols, adapterRowViews, 0);
        categoriaCursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCategoria.setAdapter(categoriaCursorAdapter);
    }

    private ArrayList<String> llenarArrayCategorias(Cursor categorias, ArrayList<String> aCategorias) {
        return null;
    }

    private void loadItem() {
        new GetItemByIdTask().execute();
    }

    private void addEditItem() {  /**ESTE METODO EXTRAE LOS DATOS DE LOS CAMPOS DE TEXTO,
                                    COMPRUEBA QUE NO ESTAN VACIOS Y LUEGO CREAN EL NUEVO ITEM*/
        boolean error = false;

        FileIOWorkstation fichero = new FileIOWorkstation(); /**CREAMOS LA VARIABLE FICHERO CON LA CARGA DE LOS DATOS*/
        idWorkstation = Integer.valueOf(fichero.leerWorkstation(getContext())); /**LEEMOS EL DATO idWorkstation DE FICHERO Y LO PASAMOS A UN ENTERO*/

        int idProducto = 0;
        ProductosDBHelper productoDBHelper = new ProductosDBHelper(getContext());
        Cursor idProductoGuardar = productoDBHelper.getProductoById(String.valueOf(productoActual));
        int index = idProductoGuardar.getColumnIndex(ProductosContract.ProductosEntry._ID);
        if (idProductoGuardar.getCount()>0){
            while (idProductoGuardar.moveToNext()){
                idProducto = idProductoGuardar.getInt(index);
            }
        }

        String quantity = QuantityField.getText().toString();
        String condition = ConditionField.getText().toString();
        String description = DescriptionField.getText().toString();

        if (idProducto == 0) {
            NameLabel.setError("Selecciona un Producto");
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
       }else{

        Item item = new Item(idWorkstation, idProducto, quantity,condition,description,"");
        new AddEditItemTask().execute(item);
        }
    }

    /** @param requery
     * @param idItem
     * Creamos este método para que una vez editado el item, vuelva a la pantalla del detalle de item     */

    private void showItemScreen(Boolean requery, String idItem) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
            intent.putExtra(ItemsActivity.EXTRA_ITEM_ID, idItem);
            startActivityForResult(intent, 2);
        }
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void showItem(Item item) {
        productoActual = item.getIdProducto();
        categoriaActual = 0;
        ProductosDBHelper productosDbHelper = new ProductosDBHelper(this.getContext());
        Cursor producto = productosDbHelper.getProductoById(String.valueOf(productoActual));
        int columIndexIdCat = producto.getColumnIndex(ProductosContract.ProductosEntry._IDCATEGORIA);
        if(producto.getCount()>0){
            while (producto.moveToNext()){
                categoriaActual = Integer.parseInt(producto.getString(columIndexIdCat));
            }
        }

        int prodPosIndex;

        if (ItemId != null){
            ItemsDBHelper itemsDBHelperS = new ItemsDBHelper(this.getContext());
            Cursor itemById = itemsDBHelperS.getItemById(ItemId);
            if (itemById.getCount()>0){
                while (itemById.moveToNext()){
                    prodPosIndex = itemById.getColumnIndex(ItemsContract.ItemEntry.IDPRODUCTO);
                    int prodId = itemById.getInt(prodPosIndex);
                    ProductosDBHelper ProdDBHelper = new ProductosDBHelper(this.getContext());
                    Cursor cursorProdById = ProdDBHelper.getProductoById(String.valueOf(prodId));
                    int indexId = cursorProdById.getColumnIndex(ProductosContract.ProductosEntry._ID);
                    int idProducto = 0;
                    if (cursorProdById.getCount()>0){
                        while (cursorProdById.moveToNext()){
                            idProducto = cursorProdById.getInt(indexId);
                        }
                    }

                    llenarCategorias();
                    cargarSpinnerProductos(categoriaActual);

                    String[] queryCols = new String[]{_ID, ProductosContract.ProductosEntry.PRODUCTO};
                    String[] idCategoria = new String[]{String.valueOf(categoriaActual)};
                    SQLiteDatabase db = new ProductosDBHelper(this.getContext()).getReadableDatabase();
                    ProductosDBHelper.onCreate(db);
                    cursorProductos  = db.query(
                            ProductosContract.ProductosEntry.TABLE_NAME, // the table to query
                            queryCols,                // the columns to return
                            ProductosContract.ProductosEntry._IDCATEGORIA+ " LIKE ?", // the columns for the WHERE clause
                            idCategoria,              // the values for the WHERE clause
                            null,                     // don't group the rows
                            null,                     // don't filter by row groups
                            null                      // don't sort
                    );
                    int posItemProd = 0;
                    if (cursorProductos.getCount() > 0){
                        while (cursorProductos.moveToNext()){
                            int idProdAct = cursorProductos.getInt(indexId);
                            if (idProdAct != idProducto){
                                posItemProd++;
                            }else{
                                break;
                            }
                        }
                    }
                    sCategoria.setSelection(categoriaActual-1);
                    sProducto.setSelection(posItemProd);
                }
            }
        }
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
         /** DEPENDIENDO DE LA TAREA ASINCRONA DE ARRIBA (UPDATE O SAVE)
         ESTA ACTIVIDAD NOS MOSTRARÁ EL NUEVO RESULTADO O UN MENSAJE DE ERROR */

        /** @param result
         * Cambiamos el método al que llama al terminar la orden de modificación */

        @Override
        protected void onPostExecute(Boolean result) {
            if (ItemId  == null){
                Cursor cursor = itemsDBHelper.getAllItems();
                cursor.moveToLast();
                int columIndex_Id = cursor.getColumnIndex(ItemsContract.ItemEntry._ID);
                ItemId = cursor.getString(columIndex_Id);
            }
            showItemScreen(result, ItemId);
        }
    }
}