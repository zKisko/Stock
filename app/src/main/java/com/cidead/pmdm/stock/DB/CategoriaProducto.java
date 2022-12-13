package com.cidead.pmdm.stock.DB;

import static com.cidead.pmdm.stock.DB.CategoriaProductosContract.CategoriaProductosEntry.CATEGORIA;
import static com.cidead.pmdm.stock.DB.CategoriaProductosContract.CategoriaProductosEntry.IMAGEN;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

public class CategoriaProducto {

    private int _id;
    private final String Categoria;
    private final String Imagen;


    public CategoriaProducto(String categoria, String imagen) {

        this.Categoria = categoria;
        this.Imagen = imagen;
    }

    @SuppressLint("Range")
    public CategoriaProducto (Cursor cursor){
        _id = cursor.getInt(cursor.getColumnIndex(ProductosContract.ProductosEntry._ID));
        Categoria = cursor.getString(cursor.getColumnIndex(CATEGORIA));
        Imagen = cursor.getString(cursor.getColumnIndex(IMAGEN));
    }


    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(CATEGORIA, Categoria);
        values.put(IMAGEN,Imagen);
        return values;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCategoria() {
        return Categoria;
    }

    public String getImagen() {return Imagen;}

}
