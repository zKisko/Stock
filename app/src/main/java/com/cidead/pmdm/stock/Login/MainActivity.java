package com.cidead.pmdm.stock.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cidead.pmdm.stock.R;
import com.cidead.pmdm.stock.Workstation.DBW.Workstation;
import com.cidead.pmdm.stock.Workstation.Workstation.WorkstationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        authentication = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = authentication.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }else{
            startActivity(new Intent(MainActivity.this, WorkstationActivity.class));
        }
    }
}