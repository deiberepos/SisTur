package com.dgaviria.sistur.clases;

public class Minutas {
    private String cantidad;
    private String codigo;
    private String numalimento;
    private String numminuta;
    private String numpreparacion;
    private String preparacion;
    private String procedimiento;
    private String real;
    private String total;
    private String unidad;

    public Minutas() {
    }

    public Minutas(String cantidad, String codigo, String numalimento, String numminuta, String numpreparacion, String preparacion, String procedimiento, String real, String total, String unidad) {
        this.cantidad = cantidad;
        this.codigo = codigo;
        this.numalimento = numalimento;
        this.numminuta = numminuta;
        this.numpreparacion = numpreparacion;
        this.preparacion = preparacion;
        this.procedimiento = procedimiento;
        this.real = real;
        this.total = total;
        this.unidad = unidad;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNumalimento() {
        return numalimento;
    }

    public void setNumalimento(String numalimento) {
        this.numalimento = numalimento;
    }

    public String getNumminuta() {
        return numminuta;
    }

    public void setNumminuta(String numminuta) {
        this.numminuta = numminuta;
    }

    public String getNumpreparacion() {
        return numpreparacion;
    }

    public void setNumpreparacion(String numpreparacion) {
        this.numpreparacion = numpreparacion;
    }

    public String getPreparacion() {
        return preparacion;
    }

    public void setPreparacion(String preparacion) {
        this.preparacion = preparacion;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public String getReal() {
        return real;
    }

    public void setReal(String real) {
        this.real = real;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}
