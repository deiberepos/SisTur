package com.dgaviria.sistur.clases;

public class OtrosEntrega {
    private String nombreCDI;
    private String fechacompra;
    private String fechaentrega;
    private String entregadopor;
    private String recibidopor;
    private String quiencompra;

    public OtrosEntrega() {
    }

    public OtrosEntrega(String nombreCDI, String fechacompra, String fechaentrega, String entregadopor, String recibidopor, String quiencompra) {
        this.nombreCDI = nombreCDI;
        this.fechacompra = fechacompra;
        this.fechaentrega = fechaentrega;
        this.entregadopor = entregadopor;
        this.recibidopor = recibidopor;
        this.quiencompra = quiencompra;
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

    public String getEntregadopor() {
        return entregadopor;
    }

    public void setEntregadopor(String entregadopor) {
        this.entregadopor = entregadopor;
    }

    public String getRecibidopor() {
        return recibidopor;
    }

    public void setRecibidopor(String recibidopor) {
        this.recibidopor = recibidopor;
    }

    public String getQuiencompra() {
        return quiencompra;
    }

    public void setQuiencompra(String quiencompra) {
        this.quiencompra = quiencompra;
    }
}
