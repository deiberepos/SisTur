package com.dgaviria.sistur.Clases;

public class MenuOpciones {
    private Boolean activo;
    private String minimorol;
    private Integer orden;
    private String titulo;
    private String subtitulo;
    private String imagen;

    public MenuOpciones() {
    }

    public MenuOpciones(Boolean activo, String minimorol, Integer orden, String titulo, String subtitulo, String imagen) {
        this.activo = activo;
        this.minimorol = minimorol;
        this.orden = orden;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.imagen = imagen;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getMinimorol() {
        return minimorol;
    }

    public void setMinimorol(String minimorol) {
        this.minimorol = minimorol;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }
}
