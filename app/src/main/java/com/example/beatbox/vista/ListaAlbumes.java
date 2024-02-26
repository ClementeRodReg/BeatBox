package com.example.beatbox.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.beatbox.R;

public class ListaAlbumes extends AppCompatActivity {

    String usuario;
    Button atras;
    Button buscar;
    EditText barraDeBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_albumes);
        Bundle bundle =  getIntent().getExtras();
        usuario=bundle.getString("usuario");

        barraDeBusqueda = findViewById(R.id.barraDeBusqueda);
        barraDeBusqueda.setText(bundle.getString("busqueda"));


        atras = findViewById(R.id.btn_atras);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverInicio();
            }
        });


        buscar = findViewById(R.id.btn_buscarListaAlbumes);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    public void volverInicio(){
        Intent volver = new Intent(this, PantallaPrincipal.class);
        volver.putExtra("usuario", usuario);
        startActivity(volver);
    }
}