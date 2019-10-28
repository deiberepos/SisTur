package com.dgaviria.sistur.clases;

public class InfanteAsiste {
    private String nombreInfante;
    //private String nombresemana;
    //private String centro;
    //private String fecha;
    private Boolean asistencia;


    public InfanteAsiste() {
    }
    public InfanteAsiste( String nombreInfante, Boolean asistencia) {
        this.nombreInfante = nombreInfante;
        this.asistencia = asistencia;
    }
/*
    public InfanteAsiste(String nombresemana, String nombreInfante, String centro, String fecha, Boolean asistencia) {
        this.nombresemana = nombresemana;
        this.nombreInfante = nombreInfante;
        this.centro = centro;
        this.fecha = fecha;
        this.asistencia = asistencia;
    }*/



    public String getNombreInfante() {
        return nombreInfante;
    }

    public void setNombreInfante(String nombreInfante) {
        this.nombreInfante = nombreInfante;
    }


    public Boolean getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Boolean asistencia) {
        this.asistencia = asistencia;
    }

}
