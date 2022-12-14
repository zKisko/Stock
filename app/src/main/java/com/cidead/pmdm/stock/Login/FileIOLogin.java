package com.cidead.pmdm.stock.Login;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileIOLogin {

    public void guardarLogin(Context context){

        try {
            String direccion = context.getExternalFilesDir(null) + "/login.txt";
            File fich = new File(direccion);
            if(!fich.exists()){
                try {
                    fich.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            OutputStreamWriter oSw = new OutputStreamWriter(new FileOutputStream(fich));
            oSw.write("logueado");
            oSw.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public String leerLogin(Context context){
        String direccion = context.getExternalFilesDir(null) + "/login.txt";
        File fich = new File(direccion);
        String login = "No Login";
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(fich)));
            login = bf.readLine();
            bf.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return login;
    }
}
