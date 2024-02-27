package com.example.beatbox.modelo;

import java.util.List;

public class Album {

    private List<Cancion> canciones;


    public Album(List<Cancion> canciones) {
        this.canciones = canciones;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }
}
