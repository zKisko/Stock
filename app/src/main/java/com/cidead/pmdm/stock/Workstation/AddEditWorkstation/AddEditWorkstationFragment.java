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

    private String mWorkstationId;

    private WorkstationDBHelper mWorkstationDBHelper;

    private FloatingActionButton mWSaveButton;
    private TextInputEditText mWNameField;
    private TextInputEditText mWDescriptionField;
    private TextInputLayout mWNameLabel;
    private TextInputLayout mWDescriptionLabel;


    public AddEditWorkstationFragment() {
        // Required empty public constructor
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
            mWorkstationId = getArguments().getString(ARG_WORKSTATION_ID);
        }
    }

    @Override  //ESTA CLASE VERIFICA EL ID DEL Workstation PARA CARGAR LOS DATOS DE ELEMENTOS EXISTENTES
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_workstation, container, false);

        // Referencias UI
        mWSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.work);
        mWNameField = (TextInputEditText) root.findViewById(R.id.et_wname);

        mWDescriptionField = (TextInputEditText) root.findViewById(R.id.et_wdescription);
        mWNameLabel = (TextInputLayout) root.findViewById(R.id.til_wname);

        mWDescriptionLabel = (TextInputLayout) root.findViewById(R.id.til_wdescription);

        // Eventos  //AÑADE ITEMS A TRAVES DEL BOTON SAVEBUTTON
        mWSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditWorkstation();
            }
        });

        mWorkstationDBHelper = new WorkstationDBHelper(getActivity());

        // Carga de datos
        if (mWorkstationId != null) {
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

        String wname = mWNameField.getText().toString();
        String wdescription = mWDescriptionField.getText().toString();

        if (TextUtils.isEmpty(wname)) {
            mWNameLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(wdescription)) {
            mWDescriptionLabel.setError(getString(R.string.field_error));
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
        mWNameField.setText(workstation.getWName());
        mWDescriptionField.setText(workstation.getWDescription());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar el elemento", Toast.LENGTH_SHORT).show();
    }

    private class GetWorkstationByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mWorkstationDBHelper.getWorkstationById(mWorkstationId);
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
            if (mWorkstationId != null) {
                return mWorkstationDBHelper.updateWorkstation(workstation[0], mWorkstationId) > 0;

            } else {
                return mWorkstationDBHelper.saveWorkstation(workstation[0]) > 0;
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