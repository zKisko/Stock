package com.cidead.pmdm.stock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class contador extends Activity {

    public int numero; /* con esta variable mostraremos las unidades de objetos del inventario */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contador);

        numero = 0;
    }

    public void mas(View vista) {
        numero++; /* esta clase hace que aumente la cantidad del objeto */
        mostrarnumero();
    }

    public void menos(View vista) {
        numero--;/* esta clase hace que decrece la cantidad del objeto */

        if (numero < 0) {
            numero=0; /* con esta instrucción hacemos que el contador por mucho que se pulse el
                            negativo, no baje de 0, ya que no podemos tener stocks en negativo*/
        }
        mostrarnumero();
    }

    public void mostrarnumero() {
        TextView cantidad = (TextView) findViewById(R.id.contador1);
        cantidad.setText(" " + numero);

        InputMethodManager miteclado=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        miteclado.hideSoftInputFromWindow(cantidad.getWindowToken(),0);

    }
    /* con estos metodos ya estaría listo el contador*/
}
