package com.example.beatbox.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beatbox.R;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.util.ArrayList;

public class PantallaPrincipal extends AppCompatActivity {

    ImageView imageAlbumes;
    ImageView imageCanciones;
    Button btn_Buscar;
    TextView txt_buscador;
    LinearLayout linearLayoutContenedor;
    private static final int PERMISSION_REQUEST_STORAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        imageAlbumes = findViewById(R.id.imageAlbumes);
        imageCanciones = findViewById(R.id.imageCanciones);
        btn_Buscar = findViewById(R.id.btn_Buscar);
        txt_buscador = findViewById(R.id.txt_buscador);
        linearLayoutContenedor = findViewById(R.id.linearLayoutAlbum);

        btn_Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PantallaPrincipal.this, ListaAlbumes.class);
                startActivity(intent);
            }
        });
        imageAlbumes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PantallaPrincipal.this);
                builder.setTitle("Nombre del álbum");

                final EditText input = new EditText(PantallaPrincipal.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String texto = input.getText().toString();

                        TextView albumText = new TextView(PantallaPrincipal.this);
                        albumText.setText("\n" + texto + "\n");
                        albumText.setTextColor(Color.WHITE);
                        albumText.setTextSize(25);
                        albumText.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        linearLayoutContenedor.addView(albumText, 0);
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                builder.show();
            }
        });
        imageCanciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Lista de nombres de canciones
                final ArrayList<String> canciones = new ArrayList<>();

                // Acceder al directorio de música y buscar archivos .mp3
                File musicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
                File[] files = musicDirectory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")) {
                            canciones.add(file.getName());
                        }
                    }
                }
                Log.d("DEBUG", "Total de archivos en el directorio de música: " + files.length);
                // Mostrar un diálogo con la lista de canciones para que el usuario elija una
                AlertDialog.Builder builder = new AlertDialog.Builder(PantallaPrincipal.this);
                builder.setTitle("Selecciona una canción");
                builder.setItems(canciones.toArray(new String[0]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Obtener el nombre de la canción seleccionada
                        String selectedSong = canciones.get(which);

                        // Mostrar el nombre de la canción seleccionada
                        Toast.makeText(PantallaPrincipal.this, "Canción seleccionada: " + selectedSong, Toast.LENGTH_SHORT).show();

                        // Crear un nuevo TextView con el nombre de la canción seleccionada
                        TextView songTextView = new TextView(PantallaPrincipal.this);
                        songTextView.setText(selectedSong);
                        songTextView.setTextColor(Color.WHITE);
                        songTextView.setTextSize(18);

                        // Obtener el LinearLayout
                        LinearLayout linearLayoutCanciones = findViewById(R.id.linearLayoutCanciones);

                        // Agregar el TextView al LinearLayout
                        linearLayoutCanciones.addView(songTextView, 0);
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                builder.show();
            }
        });

    }
}
