package com.glowin.dto;

public class CategoriaServicioDetallesDTO {
    private String nombre;
    private String urlImagen;
    private String enlace;

    public CategoriaServicioDetallesDTO(String nombre, String urlImagen, String enlace) {
        this.nombre = nombre;
        this.urlImagen = urlImagen;
        this.enlace = enlace;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }
}