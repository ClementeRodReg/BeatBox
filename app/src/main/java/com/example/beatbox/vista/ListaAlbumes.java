package com.example.beatbox.vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.beatbox.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.AppCheckTokenResult;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ListaAlbumes extends AppCompatActivity {

    String usuario;
    Button atras;
    Button buscar;
    EditText barraDeBusqueda;
    StorageReference storageRef;
    private ArrayList<String> canciones;
    private ArrayList<String> urls;
    ListView listAlbum;
    String textoBusqueda;
    FirebaseAppCheck firebaseAppCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_albumes);
        Bundle bundle =  getIntent().getExtras();
        usuario=bundle.getString("usuario");
        listAlbum = findViewById(R.id.listAlbum);
        barraDeBusqueda = findViewById(R.id.barraDeBusqueda);
        textoBusqueda=bundle.getString("busqueda");
        barraDeBusqueda.setText(textoBusqueda);
        storageRef = FirebaseStorage.getInstance().getReference();

        FirebaseApp.initializeApp(/*context=*/ this);
        firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                PlayIntegrityAppCheckProviderFactory.getInstance());

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
                buscarCancion(storageRef);
            }
        });

    }

    public void volverInicio(){
        Intent volver = new Intent(this, PantallaPrincipal.class);
        volver.putExtra("usuario", usuario);
        startActivity(volver);
    }


    public void buscarCancion(StorageReference ref){

        firebaseAppCheck.getToken(true).addOnSuccessListener(new OnSuccessListener<AppCheckTokenResult>()
        {
            @Override
            public void onSuccess(AppCheckTokenResult token)
            {
                ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference item : listResult.getItems()) {
                            String cancion = item.getName();

                            item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                            {
                                @Override
                                public void onSuccess(Uri downloadUrl)
                                {
                                    System.out.println(downloadUrl.toString());
                                }
                            });

                            if (cancion.toUpperCase().contains(textoBusqueda.toUpperCase())) {
                                canciones.add(cancion);
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, canciones);
                        listAlbum.setAdapter(adapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }
}