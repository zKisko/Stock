package com.cidead.pmdm.stock.Workstation.AddEditWorkstation;

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

import com.cidead.pmdm.stock.R;
import com.cidead.pmdm.stock.DB.Workstation;
import com.cidead.pmdm.stock.DB.WorkstationDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/** ESTA CLASE IMPLEMENTA LA LOGICA PARA INTERACTURA CON EL LAYOUT FRAGMENT_ADD_EDIT_WORKSTATION */

public class AddEditWorkstationFragment extends Fragment {
    private static final String ARG_WORKSTATION_ID = "arg_workstation_id";

    private String WorkstationId;

    private WorkstationDBHelper WorkstationDBHelper;

    private FloatingActionButton wSaveButton;
    private TextInputEditText wNameField;
    private TextInputLayout wNameLabel;

    public AddEditWorkstationFragment() {
        // CONSTRUCTOR VACIO
    }

    public static AddEditWorkstationFragment newInstance(String workstationId) {
        AddEditWorkstationFragment fragment = new AddEditWorkstationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_WORKSTATION_ID, workstationId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            WorkstationId = getArguments().getString(ARG_WORKSTATION_ID);
        }
    }

    @Override  //ESTA CLASE VERIFICA EL IDWORKSTATION DEL WORKSTATION PARA CARGAR LOS DATOS DE ELEMENTOS EXISTENTES
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_workstation, container, false);

        // INICIAMOS LAS VARIABLES PARA PODER ACTUAR CON ELLAS
        wSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.work);
        wNameField = (TextInputEditText) root.findViewById(R.id.f_wname);
        wNameLabel = (TextInputLayout) root.findViewById(R.id.l_wname);

        //A??ADE WORKSTATION A TRAVES DEL BOTON SAVEBUTTON
        wSaveButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        addEditWorkstation();
        }
        });
        WorkstationDBHelper = new WorkstationDBHelper(getActivity());
        if (WorkstationId != null) {
            loadWorkstation(); // CARGA DE DATOS
        }
        return root;
    }

    private void loadWorkstation() {
        new GetWorkstationByIdTask().execute();
    }

    private void addEditWorkstation() { /**ESTE METODO EXTRAE LOS DATOS DE LOS CAMPOS DE TEXTO,
                                        COMPRUEBA QUE NO ESTAN VACIOS Y LUEGO CREAN EL NUEVO ITEM*/
        boolean error = false;

        String wname = wNameField.getText().toString();

        if (TextUtils.isEmpty(wname)) {
            wNameLabel.setError(getString(R.string.field_error));
            error = true;
        }if (error) {
            return;
        }
        Workstation workstation = new Workstation(wname);
        new AddEditWorkstationTask().execute(workstation);
    }

    private void showWorkstationScreen(Boolean requery) {
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
                "Error al agregar nueva informaci??n", Toast.LENGTH_SHORT).show();
    }

    private void showWorkstation(Workstation workstation) {
        wNameField.setText(workstation.getWName());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar el elemento", Toast.LENGTH_SHORT).show();
    }

    private class GetWorkstationByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return WorkstationDBHelper.getWorkstationById(WorkstationId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showWorkstation(new Workstation(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }
    }

    private class AddEditWorkstationTask extends AsyncTask<Workstation, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Workstation... workstation) {
            if (WorkstationId != null) {
                return WorkstationDBHelper.updateWorkstation(workstation[0], WorkstationId) > 0;
            } else {
                return WorkstationDBHelper.saveWorkstation(workstation[0]) > 0;
            }
        }
         /** DEPENDIENDO DE LA TAREA ASINCRONA DE ARRIBA (UPDATE O SAVE)
         ESTA ACTIVIDAD NOS MOSTRAR?? EL NUEVO RESULTADO O UN MENSAJE DE ERROR */
        @Override
        protected void onPostExecute(Boolean result) { showWorkstationScreen(result); }
    }
}