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
import com.cidead.pmdm.stock.Workstation.DBW.Workstation;
import com.cidead.pmdm.stock.Workstation.DBW.WorkstationDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/* Vista para creación/edición de un Workstation */

public class AddEditWorkstationFragment extends Fragment {
    private static final String ARG_WORKSTATION_ID = "arg_workstation_id";

    private String WorkstationId;

    private WorkstationDBHelper WorkstationDBHelper;

    private FloatingActionButton wSaveButton;
    private TextInputEditText wNameField;
    private TextInputEditText wDescriptionField;
    private TextInputLayout wNameLabel;
    private TextInputLayout wDescriptionLabel;


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

    @Override  //ESTA CLASE VERIFICA EL ID DEL WORKSTATION PARA CARGAR LOS DATOS DE ELEMENTOS EXISTENTES
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_workstation, container, false);

        // Referencias UI
        wSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.work);
        wNameField = (TextInputEditText) root.findViewById(R.id.f_wname);
        wDescriptionField = (TextInputEditText) root.findViewById(R.id.f_wdescription);
        wNameLabel = (TextInputLayout) root.findViewById(R.id.l_wname);
        wDescriptionLabel = (TextInputLayout) root.findViewById(R.id.l_wdescription);

        // Eventos  //AÑADE WORKSTATION A TRAVES DEL BOTON SAVEBUTTON
        wSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditWorkstation();
            }
        });

        WorkstationDBHelper = new WorkstationDBHelper(getActivity());

        // CARGA DE DATOS
        if (WorkstationId != null) {
            loadWorkstation();
        }

        return root;
    }

    private void loadWorkstation() {
        new GetWorkstationByIdTask().execute();
    }


    /*ESTE METODO EXTRAE LOS DATOS DE LOS CAMPOS DE TEXTO,
     COMPRUEBA QUE NO ESTAN VACIOS Y LUEGO CREAN EL NUEVO ITEM*/
    private void addEditWorkstation() {
        boolean error = false;

        String wname = wNameField.getText().toString();
        String wdescription = wDescriptionField.getText().toString();

        if (TextUtils.isEmpty(wname)) {
            wNameLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(wdescription)) {
            wDescriptionLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }

        Workstation workstation = new Workstation(wname, wdescription);

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
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void showWorkstation(Workstation workstation) {
        wNameField.setText(workstation.getWName());
        wDescriptionField.setText(workstation.getWDescription());
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
         /* DEPENDIENDO DE LA TAREA ASINCRONA DE ARRIBA (UPDATE O SAVE)
         ESTA ACTIVIDAD NOS MOSTRARÁ EL NUEVO RESULTADO O UN MENSAJE DE ERROR */
        @Override
        protected void onPostExecute(Boolean result) {
            showWorkstationScreen(result);
        }

    }

}