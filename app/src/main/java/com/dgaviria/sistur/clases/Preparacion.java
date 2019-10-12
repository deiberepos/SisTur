package com.dgaviria.sistur.clases;

public class Preparacion {
    String numeroPreparacion;
    String nombrePreparacion;

    public Preparacion() {
    }

    public Preparacion(String numeroPreparacion, String nombrePreparacion) {
        this.numeroPreparacion = numeroPreparacion;
        this.nombrePreparacion = nombrePreparacion;
    }

    public String getNumeroPreparacion() {
        return numeroPreparacion;
    }

    public void setNumeroPreparacion(String numeroPreparacion) {
        this.numeroPreparacion = numeroPreparacion;
    }

    public String getNombrePreparacion() {
        return nombrePreparacion;
    }

    public void setNombrePreparacion(String nombrePreparacion) {
        this.nombrePreparacion = nombrePreparacion;
    }
}
