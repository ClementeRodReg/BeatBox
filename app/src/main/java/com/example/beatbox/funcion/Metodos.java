package com.example.beatbox.funcion;

import android.os.Environment;

import com.example.beatbox.modelo.Album;
import com.example.beatbox.modelo.Cancion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Metodos {

    public void carpetaUsuario(String usuario){
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC+"/"+usuario);
        try {
            dir.mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nuevoAlbum(String usuario, String nombreAlbum) {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC+"/"+usuario+"/"+nombreAlbum);
        try {
            dir.mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Album> comprobarAlbumes(String usuario) {
        List<Album> albumes = new ArrayList<>();

        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC+"/"+usuario).getAbsolutePath());

        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {

                List<Cancion> canciones = new ArrayList<>();

                File Dir = new File(files[i].getAbsolutePath());
                File[] filesInDir = Dir.listFiles();
                for (int num = 0; num < filesInDir.length; num++) {
                    Cancion cancion = new Cancion(filesInDir[num].getAbsolutePath(), filesInDir[num].getName());
                    canciones.add(cancion);
                }

                Album album = new Album(canciones, Dir.getName());
                albumes.add(album);
            }
        }

        return albumes;
    }

}
