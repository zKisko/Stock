package com.cidead.pmdm.stock.Workstation.WorkstationDetail;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.cidead.pmdm.stock.R;
import com.cidead.pmdm.stock.Workstation.AddEditWorkstation.AddEditWorkstationActivity;
import com.cidead.pmdm.stock.Workstation.DBW.Workstation;
import com.cidead.pmdm.stock.Workstation.DBW.WorkstationDBHelper;
import com.cidead.pmdm.stock.Workstation.Workstation.WorkstationActivity;
import com.cidead.pmdm.stock.Workstation.Workstation.WorkstationFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class WorkstationDetailFragment extends Fragment {
    public static final String ARG_WORKSTATION_ID = "workstationId";
    private String mWorkstationId;

    private CollapsingToolbarLayout mCollapsingView;

    private TextView mWDescription;

    private WorkstationDBHelper mWorkstationDBHelper;

    public WorkstationDetailFragment() {
        //CONSTRUCTOR VACIO
        }

    public static WorkstationDetailFragment newInstance(String mWorkstationId) {
        WorkstationDetailFragment fragment = new WorkstationDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_WORKSTATION_ID, mWorkstationId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mWorkstationId = getArguments().getString(ARG_WORKSTATION_ID);
        }
        setHasOptionsMenu(true); //CON ESTO HABILITAMOS EL FRAGMENTO EN LA TOOLBAR
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View root = inflater.inflate(R.layout.fragment_workstation_detail, container, false);
     mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);

     mWDescription = (TextView) root.findViewById(R.id.tw_description);

     mWorkstationDBHelper = new WorkstationDBHelper(getActivity());

     loadWorkstation();

        return root;
    }

    private void loadWorkstation(){
        new GetWorkstationByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem workstation) {
        switch (workstation.getItemId()){ // CREAMOS LAS OPCIONES DEL MENU EN EL TOOLBAR DEL FRAGMENTO
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteWorkstationTask().execute();
                break;
        }
        return super.onOptionsItemSelected(workstation);
    }

    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (requestCode == WorkstationFragment.REQUEST_UPDATE_DELETE_WORKSTATION) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }

    }


    private void showWorkstation(Workstation workstation){
        mCollapsingView.setTitle(workstation.getWName());

        mWDescription.setText(workstation.getWDescription());
    }

    //INICIA LA ACTIVIDAD DE EDICION EXPRESADA EN LA CLASE AddEditItemsActivity
    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditWorkstationActivity.class);
        intent.putExtra(WorkstationActivity.EXTRA_WORKSTATION_ID, mWorkstationId);
        startActivityForResult(intent, WorkstationFragment.REQUEST_UPDATE_DELETE_WORKSTATION);
    }

    /*SI EL ELIMINADO DEL ITEM ES CORRECTO SEGUIRÍA SU CURSO
   DE LO CONTRARIO MOSTRARIA UN ESTADO DE ERROR CON LA CLASE SHOWDELETEERROR*/
    private void showWorkstationScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showLoadError() { //AVISO DE ERROR EN LA CARGA DEL ITEM
        Toast.makeText(getActivity(),
            "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() { //AVISO DE ERROR AL ELIMINAR ELEMENTO DEL ITEM
            Toast.makeText(getActivity(),
                    "Error al eliminar elemento", Toast.LENGTH_SHORT).show();
        }

    private class GetWorkstationByIdTask extends AsyncTask<Void, Void, Cursor> {   //con esto vamos a obtener el Item por ID

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
            }
        }
    }

    //CON ESTA CLASE MANEJAMOS EL EVENTO DE BORRADO
    private class DeleteWorkstationTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mWorkstationDBHelper.deleteWorkstation(mWorkstationId);
        }

        @Override
        protected void onPostExecute(Integer integer){
            showWorkstationScreen(integer > 0);
        }
    }
}
