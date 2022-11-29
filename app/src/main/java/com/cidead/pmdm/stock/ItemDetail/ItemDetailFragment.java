package com.cidead.pmdm.stock.ItemDetail;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cidead.pmdm.stock.AddEditItems.AddEditItemsActivity;
import com.cidead.pmdm.stock.DB.Item;
import com.cidead.pmdm.stock.DB.ItemsDBHelper;
import com.cidead.pmdm.stock.Items.ItemsActivity;
import com.cidead.pmdm.stock.Items.ItemsFragment;
import com.cidead.pmdm.stock.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class ItemDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "itemId";
    private String mItemId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatarurl;
    private TextView mQuantity;
    private TextView mCondition;
    private TextView mDescription;

    private ItemsDBHelper mItemsDBHelper;

    public ItemDetailFragment() {
        //CONSTRUCTOR VACIO
        }

    public static ItemDetailFragment newInstance(String mItemsId) {
        ItemDetailFragment fragment = new ItemDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_ID, mItemsId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItemId = getArguments().getString(ARG_ITEM_ID);
        }
        setHasOptionsMenu(true); //CON ESTO HABILITAMOS EL FRAGMENTO EN LA TOOLBAR
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View root = inflater.inflate(R.layout.fragment_items_detail, container, false);
     mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
     mAvatarurl = (ImageView) getActivity().findViewById(R.id.iv_avatar);
     mQuantity = (TextView) root.findViewById(R.id.tv_quantity);
     mCondition = (TextView) root.findViewById(R.id.tv_condition);
     mDescription = (TextView) root.findViewById(R.id.tv_description);

     mItemsDBHelper = new ItemsDBHelper(getActivity());

     loadItems();

        return root;
    }

    private void loadItems(){
        new GetItemsByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()){ // CREAMOS LAS OPCIONES DEL MENU EN EL TOOLBAR DEL FRAGMENTO
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteItemTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (requestCode == ItemsFragment.REQUEST_UPDATE_DELETE_ITEM) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }

    }


    private void showItem(Item item){
        mCollapsingView.setTitle(item.getName());
        Glide.with(getContext())
                .load(Uri.parse("file:///android_asset/" + item.getAvatarurl()))
                .centerCrop()
                .into(mAvatarurl);
        mQuantity.setText(item.getQuantity());
        mCondition.setText(item.getCondition());
        mDescription.setText(item.getDescription());
    }

    //INICIA LA ACTIVIDAD DE EDICION EXPRESADA EN LA CLASE AddEditItemsActivity
    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditItemsActivity.class);
        intent.putExtra(ItemsActivity.EXTRA_ITEM_ID, mItemId);
        startActivityForResult(intent, ItemsFragment.REQUEST_UPDATE_DELETE_ITEM);
    }

    /*SI EL ELIMINADO DEL ITEM ES CORRECTO SEGUIRÍA SU CURSO
   DE LO CONTRARIO MOSTRARIA UN ESTADO DE ERROR CON LA CLASE SHOWDELETEERROR*/
    private void showItemsScreen(boolean requery) {
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

    private class GetItemsByIdTask extends AsyncTask<Void, Void, Cursor> {   //con esto vamos a obtener el Item por ID

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mItemsDBHelper.getItemById(mItemId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showItem(new Item(cursor));
            } else {
                showLoadError();
            }
        }
    }

    //CON ESTA CLASE MANEJAMOS EL EVENTO DE BORRADO
    private class DeleteItemTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mItemsDBHelper.deleteItem(mItemId);
        }

        @Override
        protected void onPostExecute(Integer integer){
            showItemsScreen(integer > 0);
        }
    }
}
