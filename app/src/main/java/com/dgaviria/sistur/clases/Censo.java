package com.dgaviria.sistur.clases;

public class Censo {

    private String nombre;
    private String apellidos;
    private String genero;
    private String fecha;
    private String observaciones;
    private String nombrepadre;
    private String telfonopadre;
    private String dirpadre;
    private String nombremadre;
    private String telfonomadre;
    private String dirmadre;
    private String centroasociado;
    private Boolean activo;


    public Censo() {
        this.nombre = "";
        this.apellidos = "";
        this.genero = "";
        this.fecha = "";
        this.observaciones = "";
        this.nombrepadre = "";
        this.telfonopadre = "";
        this.dirpadre = "";
        this.nombremadre = "";
        this.telfonomadre = "";
        this.dirmadre = "";
        this.centroasociado="";
    }

    public Censo(String nombre, String apellidos, String genero, String fecha, String observaciones, String centroasociado,String nombrepadre, String telfonopadre, String dirpadre, String nombremadre, String telfonomadre, String dirmadre, Boolean activo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.nombrepadre = nombrepadre;
        this.telfonopadre = telfonopadre;
        this.dirpadre = dirpadre;
        this.nombremadre = nombremadre;
        this.telfonomadre = telfonomadre;
        this.dirmadre = dirmadre;
        this.activo = activo;
        this.centroasociado=centroasociado;

    }

    public String getNombrepadre() {
        return nombrepadre;
    }

    public void setNombrepadre(String nombrepadre) {
        this.nombrepadre = nombrepadre;
    }

    public String getNombremadre() {
        return nombremadre;
    }

    public void setNombremadre(String nombremadre) {
        this.nombremadre = nombremadre;
    }

    public String getCentroasociado() {
        return centroasociado;
    }

    public void setCentroasociado(String centroasociado) {
        this.centroasociado = centroasociado;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getfecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        fecha = fecha;
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
                ", genero='" + genero + '\'' +
                ", Fecha='" + fecha + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", nombrepadre='" + nombrepadre + '\'' +
                ", telfonopadre='" + telfonopadre + '\'' +
                ", dirpadre='" + dirpadre + '\'' +
                ", nombremadre='" + nombremadre + '\'' +
                ", telfonomadre='" + telfonomadre + '\'' +
                ", dirmadre='" + dirmadre + '\'' +
                ", centroasociado='" + centroasociado + '\'' +
                ", activo=" + activo +
                '}';
    }
}
