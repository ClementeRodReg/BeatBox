package com.example.beatbox.vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.beatbox.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    EditText txtCorreo, txtContra, txtConfirmarContra;
    Button btnRegistrar, btnLonIn;
    FirebaseFirestore mfirestore;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mfirestore = FirebaseFirestore.getInstance();

        txtCorreo = findViewById(R.id.txtCorreo);
        txtContra = findViewById(R.id.txtContra);
        txtConfirmarContra = findViewById(R.id.txtConfirmarContra);

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnLonIn = findViewById(R.id.btnLonIn);

        limpiarCampos();
        cargarDatos();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = txtCorreo.getText().toString().trim();
                String contra = txtContra.getText().toString().trim();
                String confirmarContra = txtConfirmarContra.getText().toString().trim();

                if(correo.isEmpty() || contra.isEmpty() || confirmarContra.isEmpty()){
                    Toast.makeText(MainActivity.this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    if(contra.equals(confirmarContra)){
                        registrar(correo, contra);
                    } else{
                        Toast.makeText(MainActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnLonIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = txtCorreo.getText().toString().trim();
                String contra = txtContra.getText().toString().trim();
                String confirmarContra = txtConfirmarContra.getText().toString().trim();

                if(correo.isEmpty() || contra.isEmpty() || confirmarContra.isEmpty()){
                    Toast.makeText(MainActivity.this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    if(contra.equals(confirmarContra)){
                        logIn(correo, contra);
                    } else{
                        Toast.makeText(MainActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
    public void limpiarCampos(){
        txtCorreo.setText("");
        txtContra.setText("");
        txtConfirmarContra.setText("");
    }

    public void registrar(String correo, String contra){
        mAuth.createUserWithEmailAndPassword(correo, contra).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(MainActivity.this, "Registrado correctamente", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error al registrar", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void logIn(String correo, String contra){
        mAuth.signInWithEmailAndPassword(correo, contra).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    guardarDatos();
                    Intent intent = new Intent(MainActivity.this, PantallaPrincipal.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(MainActivity.this, "Correo o contrasenya incorrecta", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error al iniciar sesion", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void guardarDatos() {
        SharedPreferences sharedPreferences = getSharedPreferences("USERS", Context.MODE_PRIVATE);

        String correo = txtCorreo.getText().toString().trim();
        String contra = txtContra.getText().toString().trim();
        String confirmarContra = txtConfirmarContra.getText().toString().trim();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("correo", correo);
        editor.putString("contra", contra);
        editor.putString("confirmarContra", confirmarContra);

        txtCorreo.setText(correo);
        txtContra.setText(contra);
        txtConfirmarContra.setText(confirmarContra);

        editor.commit();
    }
    public void cargarDatos() {
        SharedPreferences sharedPreferences = getSharedPreferences("USERS", Context.MODE_PRIVATE);

        String email = sharedPreferences.getString("correo", "");
        String pass = sharedPreferences.getString("contra", "");
        String confirmarpass = sharedPreferences.getString("confirmarContra", "");

        txtCorreo.setText(email);
        txtContra.setText(pass);
        txtConfirmarContra.setText(confirmarpass);
    }
}