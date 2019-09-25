package com.dgaviria.sistur.clases;

public class Censo {

    private String nombre;
    private String apellidos;
    private String genero;
    private String observaciones;
    private String nombreApePadre;
    private String telfonopadre;
    private String dirpadre;
    private String nombreApeMadre;
    private String telfonomadre;
    private String dirmadre;

    public Censo() {
    }

    public Censo(String nombre, String apellidos, String genero, String observaciones, String nombreApePadre, String telfonopadre, String dirpadre, String nombreApeMadre, String telfonomadre, String dirmadre) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.observaciones = observaciones;
        this.nombreApePadre = nombreApePadre;
        this.telfonopadre = telfonopadre;
        this.dirpadre = dirpadre;
        this.nombreApeMadre = nombreApeMadre;
        this.telfonomadre = telfonomadre;
        this.dirmadre = dirmadre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getNombreApePadre() {
        return nombreApePadre;
    }

    public void setNombreApePadre(String nombreApePadre) {
        this.nombreApePadre = nombreApePadre;
    }

    public String getTelfonopadre() {
        return telfonopadre;
    }

    public void setTelfonopadre(String telfonopadre) {
        this.telfonopadre = telfonopadre;
    }

    public String getDirpadre() {
        return dirpadre;
    }

    public void setDirpadre(String dirpadre) {
        this.dirpadre = dirpadre;
    }

    public String getNombreApeMadre() {
        return nombreApeMadre;
    }

    public void setNombreApeMadre(String nombreApeMadre) {
        this.nombreApeMadre = nombreApeMadre;
    }

    public String getTelfonomadre() {
        return telfonomadre;
    }

    public void setTelfonomadre(String telfonomadre) {
        this.telfonomadre = telfonomadre;
    }

    public String getDirmadre() {
        return dirmadre;
    }

    public void setDirmadre(String dirmadre) {
        this.dirmadre = dirmadre;
    }

    @Override
    public String toString() {
        return "Censo{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", genero=" + genero +
                ", observaciones='" + observaciones + '\'' +
                ", nombreApePadre='" + nombreApePadre + '\'' +
                ", telfonopadre='" + telfonopadre + '\'' +
                ", dirpadre='" + dirpadre + '\'' +
                ", nombreApeMadre='" + nombreApeMadre + '\'' +
                ", telfonomadre='" + telfonomadre + '\'' +
                ", dirmadre='" + dirmadre + '\'' +
                '}';
    }
}
