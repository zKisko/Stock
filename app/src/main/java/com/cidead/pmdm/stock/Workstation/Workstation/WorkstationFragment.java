package com.cidead.pmdm.stock.Workstation.Workstation;

import static com.cidead.pmdm.stock.Recursos.CommonVar.DATABASE_NAME;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.cidead.pmdm.stock.DB.ItemsDBHelper;
import com.cidead.pmdm.stock.Item.Items.ItemsActivity;
import com.cidead.pmdm.stock.R;
import com.cidead.pmdm.stock.Workstation.AddEditWorkstation.AddEditWorkstationActivity;
import com.cidead.pmdm.stock.DB.Workstation;
import com.cidead.pmdm.stock.DB.WorkstationContract;
import com.cidead.pmdm.stock.DB.WorkstationDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/** Vista para la lista de Elementos del puesto de trabajo */

public class WorkstationFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_WORKSTATION = 2;

    private WorkstationDBHelper workstationDBHelper;
    private ItemsDBHelper itemsDBHelper;


    private GridView WorkstationList;
    private WorkstationCursorAdapter WorkstationAdapter;
    private FloatingActionButton wAddButton;
    private String WorkstationId; /** CREO LA VARIABLE PARA ALMACENAR LA ID DE WORKSTATION PARA USARLA
                                        EN EL BORRADO O EDICION DE LA PANTALLA EMERGENTE */
    private String tPuestoEditar;
    private Context context;
    private Workstation workstation;

    public WorkstationFragment() {
        // CONTRUCTOR VACIO
    }

    public static WorkstationFragment newInstance() {
        return new WorkstationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workstation, container, false);

        // Referencias UI
        WorkstationList = (GridView) root.findViewById(R.id.workstation_list);
        WorkstationAdapter = new WorkstationCursorAdapter(getActivity(), null);
        wAddButton = (FloatingActionButton) getActivity().findViewById(R.id.work);
        context = getContext();

        // Setup
        WorkstationList.setAdapter(WorkstationAdapter);

        /**Eventos: agregamos una escucha con setOnItemClickListener
        CON ESTE METODO IMPLEMENTAMOS LO QUE HACE NUESTRAS WORKSTATION CON UN CLICK*/

        WorkstationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Cursor currentWorkstation = (Cursor) WorkstationAdapter.getItem(i);
            int columIndex = currentWorkstation.getColumnIndex(WorkstationContract.WorkstationEntry._ID);
            String currentWorkstationId= currentWorkstation.getString(columIndex);
            showItemsScreen(currentWorkstationId);
            }
        });
        /**IMPLEMENTAMOS ESTE METODO PARA QUE CON UN PULSADO LARGO NO APAREZCA UNA VENTANA EMERGENTE DONDE
         * PODAMOS EDITAR O BORRAR LAS WORKSTATION */

        WorkstationList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor currentWorkstation = (Cursor) WorkstationAdapter.getItem(position);
                int columIndex = currentWorkstation.getColumnIndex(WorkstationContract.WorkstationEntry._ID);
                int columIndexName = currentWorkstation.getColumnIndex(WorkstationContract.WorkstationEntry.WNAME);
                String currentWorkstationId= currentWorkstation.getString(columIndex);
                String currentWorkstationName= currentWorkstation.getString(columIndexName);
                AlertDialog.Builder info = new AlertDialog.Builder(context);
                info.setMessage("¿Desea eliminar o editar el puesto de trabajo? " + currentWorkstationName)
                        .setCancelable(false)
                        //Botón cerrar Ventana de dialogo
                        .setNeutralButton("Cerrar", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                dialog.cancel();
                            }
                        })
                        //Botón para eliminar el workstation seleccionado
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                DeleteWorkstation(currentWorkstationId);
                               // loadWorkstation();
                            }
                        })
                        //Botón para editar el workstation seleccionado
                        .setNegativeButton("Editar", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                EditarAlertDialog(currentWorkstation);
                                dialog.cancel();
                            }
                        });
                AlertDialog titulo = info.create();
                titulo.setTitle("Editar/Borrar:");
                titulo.show(); // CON ESTOS METODOS AÑADIMOS TITULO Y HACEMOS QUE SE MUESTRE LA PANTALLA
                loadWorkstation();
                return true;
            }

            private void EditarAlertDialog(Cursor currentWorkstation) { /** CON ESTE METODO VAMOS A EDITAR
                     LA VENTANA EMERGENTE PARA QUE NOS MUESTRE UNA FRASE Y LAS OPCIONES DE EDITAR Y BORRAR*/
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Editar puesto de trabajo");
                int nombre = currentWorkstation.getColumnIndex(WorkstationContract.WorkstationEntry.WNAME);
                int id = currentWorkstation.getColumnIndex(WorkstationContract.WorkstationEntry._ID);
                String columIndexName = currentWorkstation.getString(nombre);
                String columIndexId = currentWorkstation.getString(id);

                final EditText input = new EditText(context); // Configuramos el input

                input.setFocusable(true); //Enviamos el foco al campo a editar
                input.requestFocus();

                input.setInputType(InputType.TYPE_CLASS_TEXT); /** Especificamos el tipo de imput que
                                                        queremos, en nuestro caso, tipo de texto plano*/

                input.setText(columIndexName); // Añadimos el input al dialog.
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tPuestoEditar = input.getText().toString();
                        workstation = new Workstation(tPuestoEditar);
                        EditWorkstation(tPuestoEditar, columIndexId);
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }

            private void DeleteWorkstation(String currentWorkstationId) { //BORRADO DE WORKSTATIONS

                WorkstationId = currentWorkstationId;
                new DeleteWorkstationTask().execute();

            }
            private void EditWorkstation(String newName, String workstationId){ //EDICION DE WORKSTAIONS
                WorkstationId = workstationId;
                workstation.setWname(newName);
                workstation.setId(workstationId);
                new AddEditWorkstationTask().execute(workstation);
            }
        });
        wAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });

        SQLiteDatabase db = getActivity().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

        // Instancia de helper
        workstationDBHelper = new WorkstationDBHelper(getActivity());
        workstationDBHelper.onCreate(db);

        loadWorkstation(); // Carga de datos

        return root;
    }

    @Override    //ACTULIZA LA LISTA SI EL RESULTADO ES POSITIVO
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditWorkstationActivity.REQUEST_ADD_WORKSTATION:
                    showSuccessfullSavedMessage();
                    loadWorkstation();
                    break;
                case REQUEST_UPDATE_DELETE_WORKSTATION:
                    loadWorkstation();
                    break;
            }
        }
    }

    private void loadWorkstation() {
        new WorkstationLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Elemento guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditWorkstationActivity.class);
        startActivityForResult(intent, AddEditWorkstationActivity.REQUEST_ADD_WORKSTATION);
    }

    private void showItemsScreen(String WorkstationId) {

        Intent intent = new Intent(getActivity(), ItemsActivity.class);
        intent.putExtra(WorkstationActivity.EXTRA_WORKSTATION_ID, WorkstationId);
        startActivity(intent);
    }

    private class WorkstationLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return workstationDBHelper.getAllWorkstation();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                WorkstationAdapter.swapCursor(cursor);
            } else if(cursor.getCount() == 0) {
                //TODO:Incluir pantalla de sin registros que mostrar
            }else{
            }
        }
    }

    private class DeleteWorkstationTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {

            if (WorkstationId != null) {
                itemsDBHelper = new ItemsDBHelper(getActivity());
                int items = itemsDBHelper.getItemsByIdWorkstation(WorkstationId).getCount();
                if (items>0){
                    workstationDBHelper.deleteItems(WorkstationId);
                }
                workstationDBHelper.deleteWorkstation(WorkstationId);
                Cursor cursor = workstationDBHelper.getAllWorkstation();
                return cursor;
            } else {
                return null;
                //TODO:No se que poner aquí.
            }
        }   /** DEPENDIENDO DE LA TAREA ASINCRONA DE ARRIBA (UPDATE O SAVE)
            ESTA ACTIVIDAD NOS MOSTRARÁ EL NUEVO RESULTADO O UN MENSAJE DE ERROR */
        @Override
        protected void onPostExecute(Cursor cursor) {
            WorkstationAdapter = new WorkstationCursorAdapter(getActivity(), cursor);
            WorkstationList.setAdapter(WorkstationAdapter);
        }
    }

    private class AddEditWorkstationTask extends AsyncTask<Workstation, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Workstation... workstation) {
            boolean retorno = false;
            if (WorkstationId != null) {
                retorno = workstationDBHelper.updateWorkstation(workstation[0], WorkstationId) > 0;

                return retorno;

            } else {
                retorno = workstationDBHelper.saveWorkstation(workstation[0]) > 0;
                return retorno;
            }
        }
        /** DEPENDIENDO DE LA TAREA ASINCRONA DE ARRIBA (UPDATE O SAVE)
         ESTA ACTIVIDAD NOS MOSTRARÁ EL NUEVO RESULTADO O UN MENSAJE DE ERROR */
        @Override
        protected void onPostExecute(Boolean result) {
            Cursor cursor = workstationDBHelper.getAllWorkstation();
            showWorkstationScreen(cursor);
        }
    }

    private void showWorkstationScreen(Cursor cursor) {

        WorkstationAdapter = new WorkstationCursorAdapter(getActivity(), cursor);
        WorkstationList.setAdapter(WorkstationAdapter);
    }
}