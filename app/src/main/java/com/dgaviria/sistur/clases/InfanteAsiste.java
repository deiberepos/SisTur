package com.dgaviria.sistur.clases;

public class InfanteAsiste {
    private String nombreInfante;
    private String registroCivil;
    private Boolean asisteLunes;
    private Boolean asisteMartes;
    private Boolean asisteMiercoles;
    private Boolean asisteJueves;
    private Boolean asisteViernes;


    public InfanteAsiste() {
    }


    public InfanteAsiste(String nombreInfante, String registroCivil, Boolean asisteLunes, Boolean asisteMartes, Boolean asisteMiercoles, Boolean asisteJueves, Boolean asisteViernes) {
        this.nombreInfante = nombreInfante;
        this.registroCivil = registroCivil;
        this.asisteLunes = asisteLunes;
        this.asisteMartes = asisteMartes;
        this.asisteMiercoles = asisteMiercoles;
        this.asisteJueves = asisteJueves;
        this.asisteViernes = asisteViernes;
    }
    public Boolean getAsisteMartes() {
        return asisteMartes;
    }

    public void setAsisteMartes(Boolean asisteMartes) {
        this.asisteMartes = asisteMartes;
    }

    public Boolean getAsisteMiercoles() {
        return asisteMiercoles;
    }

    public void setAsisteMiercoles(Boolean asisteMiercoles) {
        this.asisteMiercoles = asisteMiercoles;
    }

    public Boolean getAsisteJueves() {
        return asisteJueves;
    }

    public void setAsisteJueves(Boolean asisteJueves) {
        this.asisteJueves = asisteJueves;
    }

    public Boolean getAsisteViernes() {
        return asisteViernes;
    }

    public void setAsisteViernes(Boolean asisteViernes) {
        this.asisteViernes = asisteViernes;
    }
    public String getNombreInfante() {
        return nombreInfante;
    }

    public void setNombreInfante(String nombreInfante) {
        this.nombreInfante = nombreInfante;
    }


    public Boolean getAsisteLunes() {
        return asisteLunes;
    }

    public void setAsisteLunes(Boolean asisteLunes) {
        this.asisteLunes = asisteLunes;
    }

    public String getRegistroCivil() {
        return registroCivil;
    }

    public void setRegistroCivil(String registroCivil) {
        this.registroCivil = registroCivil;
    }

}
