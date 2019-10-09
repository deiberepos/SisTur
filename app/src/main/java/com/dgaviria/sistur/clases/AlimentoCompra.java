package com.dgaviria.sistur.clases;

public class AlimentoCompra {
    private String orden;
    private String ingrediente;
    private String medida;
    private String cantidad;
    private String editValorCompra;
    private String total;

    public AlimentoCompra() {
    }

    public AlimentoCompra(String orden,String ingrediente, String medida, String cantidad, String total) {
        this.orden = orden;
        this.ingrediente = ingrediente;
        this.medida = medida;
        this.cantidad = cantidad;
        this.total = total;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public String getMedida() {
        return medida;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getTotal() {
        return total;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getEditValorCompra() {
        return editValorCompra;
    }

    public void setEditValorCompra(String editValorCompra) {
        this.editValorCompra = editValorCompra;
    }

}
