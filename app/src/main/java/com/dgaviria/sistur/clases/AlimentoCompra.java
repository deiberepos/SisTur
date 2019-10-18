package com.dgaviria.sistur.clases;

public class AlimentoCompra {
    private String fechacompra;
    private String ingrediente;
    private String medida;
    private String cantidad;
    private String valorcompra;
    private String total;
    private String entregado;
    private String porentregar;
    private String codigo;

    public AlimentoCompra() {
    }

    public AlimentoCompra(String codigo,String fechacompra,String ingrediente, String medida, String cantidad, String total,String entregado,String porentregar) {
        this.ingrediente = ingrediente;
        this.medida = medida;
        this.cantidad = cantidad;
        this.total = total;
        this.entregado = entregado;
        this.porentregar = porentregar;
        this.fechacompra = fechacompra;
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFechacompra() {
        return fechacompra;
    }

    public void setFechacompra(String fechacompra) {
        this.fechacompra = fechacompra;
    }

    public String getEntregado() {
        return entregado;
    }

    public void setEntregado(String entregado) {
        this.entregado = entregado;
    }

    public String getPorentregar() {
        return porentregar;
    }

    public void setPorentregar(String porentregar) {
        this.porentregar = porentregar;
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

    public String getValorcompra() {
        return valorcompra;
    }

    public void setValorcompra(String valorcompra) {
        this.valorcompra = valorcompra;
    }

}
