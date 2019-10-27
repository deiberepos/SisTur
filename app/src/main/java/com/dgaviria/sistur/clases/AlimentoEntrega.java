package com.dgaviria.sistur.clases;


public class AlimentoEntrega {
    private String nombresemana;
    private String codigo;
    private String ingrediente;
    private String medida;
    private String cantidadentregada;
    private Boolean estadoBueno;
    private Boolean estadoRegular;
    private Boolean estadoMalo;


    public AlimentoEntrega() {
    }

    public AlimentoEntrega(String nombresemana, String codigo, String ingrediente, String medida, String cantidadentregada, Boolean estadoBueno, Boolean estadoRegular, Boolean estadoMalo) {
        this.nombresemana = nombresemana;
        this.codigo = codigo;
        this.ingrediente = ingrediente;
        this.medida = medida;
        this.cantidadentregada = cantidadentregada;
        this.estadoBueno = estadoBueno;
        this.estadoRegular = estadoRegular;
        this.estadoMalo = estadoMalo;
    }

    public String getNombresemana() {
        return nombresemana;
    }

    public void setNombresemana(String nombresemana) {
        this.nombresemana = nombresemana;
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
}
