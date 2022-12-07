package com.cidead.pmdm.stock.Workstation.Workstation;

import static com.cidead.pmdm.stock.Item.DB.CommonVar.*;

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

import com.cidead.pmdm.stock.Item.Items.ItemsActivity;
import com.cidead.pmdm.stock.R;
import com.cidead.pmdm.stock.Workstation.AddEditWorkstation.AddEditWorkstationActivity;
import com.cidead.pmdm.stock.Workstation.DBW.WorkstationContract;
import com.cidead.pmdm.stock.Workstation.DBW.WorkstationDBHelper;
import com.cidead.pmdm.stock.Workstation.WorkstationDetail.WorkstationDetailActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/* Vista para la lista de Elementos del puesto de trabajo */

public class WorkstationFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_WORKSTATION = 2;
    public static final int REQUEST_ITEMS = 1;

    private WorkstationDBHelper workstationDBHelper;

    private ListView WorkstationList;
    private WorkstationCursorAdapter WorkstationAdapter;
    private FloatingActionButton wAddButton;


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
        WorkstationList = (ListView) root.findViewById(R.id.workstation_list);
        WorkstationAdapter = new WorkstationCursorAdapter(getActivity(), null);
        wAddButton = (FloatingActionButton) getActivity().findViewById(R.id.work);

        // Setup
        WorkstationList.setAdapter(WorkstationAdapter);

        //Eventos: agregamos una escucha con setOnItemClickListener
        WorkstationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentWorkstation = (Cursor) WorkstationAdapter.getItem(i);
                String currentWorkstationId= currentWorkstation.getString(
                        currentWorkstation.getColumnIndex(WorkstationContract.WorkstationEntry._ID));

                showItemsScreen(currentWorkstationId);
            }
        });
        wAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });


        getActivity().deleteDatabase(DATABASE_NAME);

        // Instancia de helper
        workstationDBHelper = new WorkstationDBHelper(getActivity());

        // Carga de datos
        loadWorkstation();

        return root;
    }

    //ACTULIZA LA LISTA SI EL RESULTADO ES POSITIVO
    @Override
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

    //INICIAMOS LA ACTIVIDAD DE DETALLE
    private void showDetailScreen(String WorkstationId) {
        Intent intent = new Intent(getActivity(), WorkstationDetailActivity.class);
        intent.putExtra(WorkstationActivity.EXTRA_WORKSTATION_ID, WorkstationId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_WORKSTATION);
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
            } else {
                // Mostrar estado vacio
            }
        }
    }

}