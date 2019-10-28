package com.dgaviria.sistur.clases;

public class InfanteAsiste {
    private String nombreInfante;
    private String nombresemana;
    private String centro;
    private String fecha;
    private Boolean asistencia;


    public InfanteAsiste() {
    }

    public InfanteAsiste(String nombresemana, String nombreInfante, String centro, String fecha, Boolean asistencia) {
        this.nombresemana = nombresemana;
        this.nombreInfante = nombreInfante;
        this.centro = centro;
        this.fecha = fecha;
        this.asistencia = asistencia;
    }

    public String getNombresemana() {
        return nombresemana;
    }

    public void setNombresemana(String nombresemana) {
        this.nombresemana = nombresemana;
    }

    public String getNombreInfante() {
        return nombreInfante;
    }

    public void setNombreInfante(String nombreInfante) {
        this.nombreInfante = nombreInfante;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Boolean getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Boolean asistencia) {
        this.asistencia = asistencia;
    }

}
