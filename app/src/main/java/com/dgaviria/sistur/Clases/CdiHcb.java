package com.dgaviria.sistur.Clases;

public class CdiHcb {

    private String nombreCDI, nombreEncargado, nombreContacto, ubicacion, dirEncargado, dirContacto;
    private String telEncargado, telContacto;
    private int tipo;

    public CdiHcb() {
        this.nombreCDI = "";
        this.nombreEncargado = "";
        this.nombreContacto = "";
        this.ubicacion = "";
        this.dirEncargado = "";
        this.dirContacto = "";
        this.telEncargado = "";
        this.telContacto = "";
        this.tipo = 0;
    }

    public CdiHcb(String nombreCDI, String nombreEncargado, String nombreContacto, String ubicacion, String dirEncargado, String dirContacto, String telEncargado, String telContacto, int tipo) {
        this.nombreCDI = nombreCDI;
        this.nombreEncargado = nombreEncargado;
        this.nombreContacto = nombreContacto;
        this.ubicacion = ubicacion;
        this.dirEncargado = dirEncargado;
        this.dirContacto = dirContacto;
        this.telEncargado = telEncargado;
        this.telContacto = telContacto;
        this.tipo = tipo;
    }

    public String getNombreCDI() {
        return nombreCDI;
    }

    public void setNombreCDI(String nombreCDI) {
        this.nombreCDI = nombreCDI;
    }

    public String getNombreEncargado() {
        return nombreEncargado;
    }

    public void setNombreEncargado(String nombreEncargado) {
        this.nombreEncargado = nombreEncargado;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDirEncargado() {
        return dirEncargado;
    }

    public void setDirEncargado(String dirEncargado) {
        this.dirEncargado = dirEncargado;
    }

    public String getDirContacto() {
        return dirContacto;
    }

    public void setDirContacto(String dirContacto) {
        this.dirContacto = dirContacto;
    }

    public String getTelEncargado() {
        return telEncargado;
    }

    public void setTelEncargado(String telEncargado) {
        this.telEncargado = telEncargado;
    }

    public String getTelContacto() {
        return telContacto;
    }

    public void setTelContacto(String telContacto) {
        this.telContacto = telContacto;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return nombreCDI + '\'' +
                ", nombreEncargado='" + nombreEncargado + '\'' +
                ", nombreContacto='" + nombreContacto + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", dirEncargado='" + dirEncargado + '\'' +
                ", dirContacto='" + dirContacto + '\'' +
                ", telEncargado='" + telEncargado + '\'' +
                ", telContacto='" + telContacto + '\'' +
                ", tipo=" + tipo;
    }



}
