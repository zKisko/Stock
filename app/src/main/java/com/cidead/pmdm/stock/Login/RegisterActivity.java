package com.cidead.pmdm.stock.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cidead.pmdm.stock.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText user;
    private EditText email;
    private TextInputLayout password;
    private Button btnRegister;
    private TextView lblLogin;

    private String userID;
    private FirebaseAuth authentication;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = findViewById(R.id.user);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        lblLogin = findViewById(R.id.lblLogin);
        btnRegister = findViewById(R.id.btnRegister);

        authentication = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(view -> {
            createuser();
        });

        lblLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });
    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void createuser(){

        String name = user.getText().toString();
        String mail = email.getText().toString();
        String password = this.password.getEditText().getText().toString();

        if (TextUtils.isEmpty(name)){
            email.setError("Ingrese un Nombre");
            email.requestFocus();
        }else if (TextUtils.isEmpty(mail)){
            email.setError("Ingrese un Correo");
            email.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            email.setError("Ingrese una Contraseña");
            email.requestFocus();
        }else {

            authentication.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        userID = authentication.getCurrentUser().getUid();
                        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);

                        Map<String,Object> user=new HashMap<>();
                        user.put("Nombre", name);
                        user.put("Correo", mail);
                        user.put("Contraseña", password);

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG", "onSuccess: Datos registrados"+userID);
                            }
                        });
                        Toast.makeText(RegisterActivity.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }else {
                        Toast.makeText(RegisterActivity.this, "Usuario no registrado"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}