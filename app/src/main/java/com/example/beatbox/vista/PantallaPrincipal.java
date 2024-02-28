package com.example.beatbox.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beatbox.R;

import java.io.File;

public class PantallaPrincipal extends AppCompatActivity {

    ImageView imageAlbumes;
    ImageView imageCanciones;
    Button btn_Buscar;
    TextView txt_buscador;
    LinearLayout linearLayoutContenedor;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        imageAlbumes = findViewById(R.id.imageAlbumes);
        imageCanciones = findViewById(R.id.imageCanciones);
        btn_Buscar = findViewById(R.id.btn_Buscar);
        txt_buscador = findViewById(R.id.txt_buscador);
        linearLayoutContenedor = findViewById(R.id.linearLayoutAlbum);

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
    }
}
