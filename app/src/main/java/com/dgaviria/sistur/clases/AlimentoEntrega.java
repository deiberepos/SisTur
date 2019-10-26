package com.dgaviria.sistur.clases;

import android.widget.RadioButton;

public class AlimentoEntrega {
    private String nombresemana;
    private String nombreCDI;
    private String fechacompra;
    private String fechaentrega;
    private String codigo;
    private String ingrediente;
    private String medida;
    private String cantidadentregada;
    private Boolean estadoBueno;
    private Boolean estadoRegular;
    private Boolean estadoMalo;
    private String recibidopor;

    public AlimentoEntrega() {
    }

    public AlimentoEntrega(String nombresemana, String nombreCDI, String fechacompra, String fechaentrega, String codigo, String ingrediente, String medida, String cantidadentregada, Boolean estadoBueno, Boolean estadoRegular, Boolean estadoMalo, String recibidopor) {
        this.nombresemana = nombresemana;
        this.nombreCDI = nombreCDI;
        this.fechacompra = fechacompra;
        this.fechaentrega = fechaentrega;
        this.codigo = codigo;
        this.ingrediente = ingrediente;
        this.medida = medida;
        this.cantidadentregada = cantidadentregada;
        this.estadoBueno = estadoBueno;
        this.estadoRegular = estadoRegular;
        this.estadoMalo = estadoMalo;
        this.recibidopor = recibidopor;
    }

    public String getNombresemana() {
        return nombresemana;
    }

    public void setNombresemana(String nombresemana) {
        this.nombresemana = nombresemana;
    }

    public String getNombreCDI() {
        return nombreCDI;
    }

    public void setNombreCDI(String nombreCDI) {
        this.nombreCDI = nombreCDI;
    }

    public String getFechacompra() {
        return fechacompra;
    }

    public void setFechacompra(String fechacompra) {
        this.fechacompra = fechacompra;
    }

    public String getFechaentrega() {
        return fechaentrega;
    }

    public void setFechaentrega(String fechaentrega) {
        this.fechaentrega = fechaentrega;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public String getCantidadentregada() {
        return cantidadentregada;
    }

    public void setCantidadentregada(String cantidadentregada) {
        this.cantidadentregada = cantidadentregada;
    }

    public Boolean getEstadoBueno() {
        return estadoBueno;
    }

    public void setEstadoBueno(Boolean estadoBueno) {
        this.estadoBueno = estadoBueno;
    }

    public Boolean getEstadoRegular() {
        return estadoRegular;
    }

    public void setEstadoRegular(Boolean estadoRegular) {
        this.estadoRegular = estadoRegular;
    }

    public Boolean getEstadoMalo() {
        return estadoMalo;
    }

    public void setEstadoMalo(Boolean estadoMalo) {
        this.estadoMalo = estadoMalo;
    }

    public String getRecibidopor() {
        return recibidopor;
    }

    public void setRecibidopor(String recibidopor) {
        this.recibidopor = recibidopor;
    }
}
