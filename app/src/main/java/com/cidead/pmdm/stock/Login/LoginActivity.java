package com.cidead.pmdm.stock.Login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cidead.pmdm.stock.Item.Items.ItemsActivity;
import com.cidead.pmdm.stock.R;
import com.cidead.pmdm.stock.Workstation.Workstation.WorkstationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private TextInputLayout password;
    private Button btnLogin;
    private TextView btnRegister;

    private FirebaseAuth authentication;
    private String login = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Guardamos Login para no entrar en la pantalla de logueo

        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para leer.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i("Mensaje", "Se tiene permiso para leer y escribir!");
        }

        FileIOLogin fichero = new FileIOLogin();
        login = fichero.leerLogin(this.getApplicationContext());
        if (!login.equals("logueado")){
            fichero = new FileIOLogin();
            fichero.guardarLogin(getApplicationContext());
        }else{
            startActivity(new Intent(LoginActivity.this, WorkstationActivity.class));
        }

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnRegister = findViewById(R.id.lblRegister);
        btnLogin = findViewById(R.id.btnLogin);

        authentication = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> {
            userLogin();
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });
    }

    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void userLogin(){
        String mail = email.getText().toString();
        String password = this.password.getEditText().getText().toString();

        if (TextUtils.isEmpty(mail)){
            email.setError("Ingrese un correo");
            email.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
            this.password.requestFocus();
        }else{

            authentication.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Bienvenid@", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, WorkstationActivity.class));
                    }else {
                        Toast.makeText(LoginActivity.this, "Email o contraseña no valido", Toast.LENGTH_SHORT).show();
                        Log.w("TAG", "Error:", task.getException());
                    }
                }
            });
        }
    }
}