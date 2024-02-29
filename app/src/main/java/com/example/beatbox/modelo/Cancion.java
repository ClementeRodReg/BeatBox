package com.example.beatbox.modelo;

public class Cancion {
    private String path;

    private String nombre;

    public Cancion(String path, String nombre) {
        this.path = path;
        this.nombre = nombre;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
