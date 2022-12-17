package com.cidead.pmdm.stock.DB;

import static com.cidead.pmdm.stock.DB.ProductosContract.ProductosEntry.PRODUCTO;
import static com.cidead.pmdm.stock.DB.ProductosContract.ProductosEntry._IDCATEGORIA;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

public class Producto {

    private int _id;
    private int _idCategoria;
    private String Producto;

    public Producto (int idCategoria, String producto){
        this._idCategoria = idCategoria;
        this.Producto = producto;
    }

    @SuppressLint("Range")
    public Producto (Cursor cursor){
        _id = cursor.getInt(cursor.getColumnIndex(ProductosContract.ProductosEntry._ID));
        _idCategoria = cursor.getInt(cursor.getColumnIndex(_IDCATEGORIA));
        Producto = String.valueOf(cursor.getColumnIndex(PRODUCTO));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(_IDCATEGORIA, _idCategoria);
        values.put(PRODUCTO, Producto);
        return values;
    }

    public int get_id() {
        return _id;
    }

    public String getProducto() {
        return Producto;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setProducto(String producto) {
        this.Producto = producto;
    }

    public int get_idCategoria() {
        return _idCategoria;
    }

    public void set_idCategoria(int _idCategoria) {
        this._idCategoria = _idCategoria;
    }
}
