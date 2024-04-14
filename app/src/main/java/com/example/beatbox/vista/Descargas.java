package com.example.beatbox.vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.beatbox.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.AppCheckToken;
import com.google.firebase.appcheck.AppCheckTokenResult;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class Descargas extends AppCompatActivity {

    Button btnAtras, btnDescargar;
    ListView listAlbum;
    private ArrayList<String> canciones;
    StorageReference storageRef;
    String usuario, nAlbum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descargas);
        Bundle bundle =  getIntent().getExtras();
        usuario=bundle.getString("usuario");
        nAlbum=bundle.getString("nAlbum");

        nAlbum = "Rock";
        usuario = "Prueba";

        storageRef = FirebaseStorage.getInstance().getReference();

        FirebaseApp.initializeApp(/*context=*/ this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                PlayIntegrityAppCheckProviderFactory.getInstance());


        firebaseAppCheck.getToken(true).addOnSuccessListener(new OnSuccessListener<AppCheckTokenResult>()
        {
            @Override
            public void onSuccess(AppCheckTokenResult token)
            {
                token.getToken();
            }
        });

        canciones = new ArrayList<>();

        btnAtras = findViewById(R.id.btnAtras);
        btnDescargar = findViewById(R.id.btnDescargar);
        listAlbum = findViewById(R.id.listAlbum);

        StorageReference ref = storageRef.child("/" + usuario + "/"+ nAlbum + "/");

        mostrarCanciones(canciones, ref);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Descargas.this, PantallaPrincipal.class);
                startActivity(intent);
            }
        });

        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descargar(ref);
            }
        });
    }

    public void mostrarCanciones(ArrayList<String> canciones, StorageReference ref) {

        ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()) {
                    String cancion = cambiarFormato(item.getName());
                    canciones.add(cancion);
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

    public String cambiarFormato(String cancion) {

        String quitarPunto[] = cancion.split("\\.");
        String cancionSinPunto = quitarPunto[0];

        String[] partes = cancionSinPunto.split("-");
        partes[0] = partes[0].substring(0, 1).toUpperCase() + partes[0].substring(1).toLowerCase();

        String resultado = String.join(" ", partes);

        return resultado;
    }

    public void descargar(StorageReference ref) {
        ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()) {
                    descargarArchivo(item.getName(), ref);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void descargarArchivo(String itemName, StorageReference ref) {

        ref = storageRef.child("/" + usuario + "/"+ nAlbum + "/" + itemName);

        /*
        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC + "/" + usuario).getAbsolutePath());
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                nAlbum=files[i].getName();
            }
        }
        */

        String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC + "/" + usuario + "/" + nAlbum).getAbsolutePath();
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                downloadFile(Descargas.this, itemName, directoryPath, url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al descargar", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void downloadFile(Context context, String itemName, String destinationDirectory, String url) {
        DownloadManager downloadmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, itemName);

        downloadmanager.enqueue(request);
    }
}