package com.example.beatbox.vista;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.beatbox.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class Descargas extends AppCompatActivity {

    Button btnAtras;
    ListView listAlbum;
    private ArrayList<String> canciones;
    StorageReference storageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descargas);

        storageRef = FirebaseStorage.getInstance().getReference();

        canciones = new ArrayList<>();

        btnAtras = findViewById(R.id.btnAtras);
        listAlbum = findViewById(R.id.listAlbum);

        mostrarCanciones(canciones);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Descargas.this, PantallaPrincipal.class);
                startActivity(intent);
            }
        });
    }

    public void mostrarCanciones(ArrayList<String> canciones){
        StorageReference ref = storageRef.child("/Prueba/Rock");
        ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for(StorageReference item : listResult.getItems()){
                    String cancion = cambiarFormato(item.getName());
                    canciones.add(cancion);
                }
                ArrayAdapter <String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, canciones);

                listAlbum.setAdapter(adapter);
                listAlbum.setBackgroundColor(Color.WHITE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_LONG).show();
            }
        });
    }


    public String cambiarFormato(String cancion){

        String quitarPunto[] = cancion.split("\\.");
        String cancionSinPunto = quitarPunto[0];

        String[] partes = cancionSinPunto.split("-");
        partes[0] = partes[0].substring(0, 1).toUpperCase() + partes[0].substring(1).toLowerCase();

        String resultado = String.join(" ", partes);

        return resultado;
    }
}