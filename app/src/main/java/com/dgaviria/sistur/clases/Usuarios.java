package com.dgaviria.sistur.clases;

public class Usuarios {
    public String usuario;
    public String contrasena;
    public String nombre;
    public String correo;
    public Boolean rolsuper;
    public Boolean rolgestor;
    public Boolean rolcompras;
    public Boolean rolbasico;

    public Usuarios() {
        this.usuario = "";
        this.contrasena = "";
        this.nombre = "";
        this.correo = "";
        this.rolsuper = false;
        this.rolgestor = false;
        this.rolcompras = false;
        this.rolbasico = false;
    }

    public Usuarios(String usuario, String contrasena, String nombre, String correo, Boolean rolSuper, Boolean rolGestor, Boolean rolCompras, Boolean rolBasico) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.correo = correo;
        this.rolsuper = rolSuper;
        this.rolgestor = rolGestor;
        this.rolcompras = rolCompras;
        this.rolbasico = rolBasico;
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

    public Boolean getRolsuper() {
        return rolsuper;
    }

    public void setRolsuper(Boolean rolsuper) {
        this.rolsuper = rolsuper;
    }

    public Boolean getRolgestor() {
        return rolgestor;
    }

    public void setRolgestor(Boolean rolgestor) {
        this.rolgestor = rolgestor;
    }

    public Boolean getRolcompras() {
        return rolcompras;
    }

    public void setRolcompras(Boolean rolcompras) {
        this.rolcompras = rolcompras;
    }

    public Boolean getRolbasico() {
        return rolbasico;
    }

    public void setRolbasico(Boolean rolbasico) {
        this.rolbasico = rolbasico;
    }
}
