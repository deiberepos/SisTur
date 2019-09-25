package com.dgaviria.sistur.clases;

public class PlanAlimenticio {
    public String codigo;
    public String nombre;
    public String regional;
    public Boolean activo;
    public String grupo;

    public PlanAlimenticio() {
    }

    public PlanAlimenticio(String codigo, String nombre, String nombreregional, Boolean activo, String grupo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.regional = nombreregional;
        this.activo = activo;
        this.grupo = grupo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
}
