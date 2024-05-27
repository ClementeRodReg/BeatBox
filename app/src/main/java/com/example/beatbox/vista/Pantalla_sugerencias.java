package com.example.beatbox.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beatbox.R;
import com.example.beatbox.modelo.Sugerencia;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Pantalla_sugerencias extends AppCompatActivity {
    private FirebaseFirestore myBBDD;
    Button botonEnviar;
    Button volver;
    EditText campoSugerencia;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_sugerencias);
        myBBDD = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        botonEnviar = findViewById(R.id.botonEnviar);
        campoSugerencia = findViewById(R.id.sugerencia1);
        volver = findViewById(R.id.volverbtn);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Pantalla_sugerencias.this, PantallaPrincipal.class);
                startActivity(intent);
            }
        });

        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(campoSugerencia.getText().toString().isEmpty()) {
                    Toast.makeText(Pantalla_sugerencias.this, "Si quieres enviar, tendrás que escribir tu sugerencia.", Toast.LENGTH_SHORT).show();
                }else{
                    String pattern = "MM-dd-yyyy HH:mm";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    String date = simpleDateFormat.format(new Date());
                    Sugerencia sugerencia = new Sugerencia(campoSugerencia.getText().toString());
                    myBBDD.collection("sugerencias").document(date+" - "+mAuth.getCurrentUser().getDisplayName()).set(sugerencia).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Pantalla_sugerencias.this, "Sugerencia enviada.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent intent = new Intent(Pantalla_sugerencias.this, PantallaPrincipal.class);
                    startActivity(intent);
                }
            }
        });


     }
}