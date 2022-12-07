package com.cidead.pmdm.stock.Item.Items;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileIOWorkstation {

    public void guardarWorkStation(String extraWorkstationId, Context context){

        try {
            String direccion = context.getExternalFilesDir(null) + "/workstation.txt";
            File fich = new File(direccion);
            if(!fich.exists()){
                try {
                    fich.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            OutputStreamWriter oSw = new OutputStreamWriter(new FileOutputStream(fich));
            oSw.write(extraWorkstationId);
            oSw.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public String leerWorkstation(Context context){
        String direccion = context.getExternalFilesDir(null) + "/workstation.txt";
        File fich = new File(direccion);
        String workstation = "";
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(fich)));
            workstation = bf.readLine();
            bf.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return workstation;
    }

}
