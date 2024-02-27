package com.example.beatbox.modelo;

import java.util.List;

public class Album {

    private List<Cancion> canciones;
    private String nombre;

    public Album(List<Cancion> canciones, String nombre) {
        this.canciones = canciones;
        this.nombre = nombre;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
