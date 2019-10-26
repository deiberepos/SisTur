package com.dgaviria.sistur.clases;

public class Usuarios {
    public String usuario;
    public String contrasena;
    public String nombre;
    public String correo;
    public String rolusuario;

    public Usuarios() {
        this.usuario = "";
        this.contrasena = "";
        this.nombre = "";
        this.correo = "";
        this.rolusuario = "";
    }

    public Usuarios(String usuario, String contrasena, String nombre, String correo, String rolusuario) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.correo = correo;
        this.rolusuario=rolusuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRolusuario() {
        return rolusuario;
    }

    public void setRolusuario(String rolusuario) {
        this.rolusuario = rolusuario;
    }
}
