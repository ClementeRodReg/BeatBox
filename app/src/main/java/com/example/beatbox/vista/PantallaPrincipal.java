package com.example.beatbox.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.beatbox.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class PantallaPrincipal extends AppCompatActivity {
    Button btn_Buscar;
    Button btn_Descargar;
    EditText txt_buscador;
    String usuario;
    int posicion;
    ListView listAlbum;
    private FirebaseFirestore myBBDD;
    private ArrayList<String> canciones;
    private ArrayList<String> urls;
    Button btnSugerenciasVista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        myBBDD = FirebaseFirestore.getInstance();
        btn_Buscar = findViewById(R.id.btn_Buscar);
        btn_Descargar = findViewById(R.id.botonDescargar);
        btn_Descargar.setVisibility(View.INVISIBLE);
        txt_buscador = findViewById(R.id.txt_buscador);
        listAlbum = findViewById(R.id.listCanciones);
        Bundle bundle =  getIntent().getExtras();
        usuario=bundle.getString("usuario");
        btnSugerenciasVista = findViewById(R.id.btnSugerenciasVista);
        btnSugerenciasVista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PantallaPrincipal.this, Pantalla_sugerencias.class);
                startActivity(intent);
            }
        });

        btn_Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarCanciones();
            }
        });

        buscarCanciones();

        listAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                posicion = position;
                btn_Descargar.setText("Descargar - "+canciones.get(posicion));
                btn_Descargar.setVisibility(View.VISIBLE);
            }
        });

        btn_Descargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri link = Uri.parse(urls.get(posicion));
                Intent intent = new Intent(Intent.ACTION_VIEW, link);
                startActivity(intent);
                Log.d("link", urls.get(posicion));
            }
        });

    }

    public void buscarCanciones(){
        canciones = new ArrayList<>();
        urls = new ArrayList<>();
        Task coleccion = myBBDD.collection("songs").get();
        coleccion.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot cancion : task.getResult()) {

                    if(cancion.getId().toUpperCase().contains(txt_buscador.getText().toString().toUpperCase())) {
                        String cancionnombre = cancion.getId();
                        canciones.add(cancionnombre);
                        String url = cancion.getData().get("url").toString();
                        urls.add(url);
                    }

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, canciones);
                listAlbum.setAdapter(adapter);
            }

        });

    }
}
