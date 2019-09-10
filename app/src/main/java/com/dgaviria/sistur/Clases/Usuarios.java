package com.dgaviria.sistur.Clases;

import android.provider.ContactsContract;

import java.util.List;

public class Usuarios {
    String usuario;
    String contrasena;
    String nombre;
    String correo;
    Boolean rolsuper;
    Boolean roladmin;
    Boolean rolgestor;
    Boolean rolcompras;
    Boolean rolbasico;

    public Usuarios() {
    }

    public Usuarios(String usuario, String contrasena, String nombre, String correo, Boolean rolSuper, Boolean rolAdmin, Boolean rolGestor, Boolean rolCompras, Boolean rolBasico) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.correo = correo;
        this.rolsuper = rolSuper;
        this.roladmin = rolAdmin;
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

    public Boolean getRoladmin() {
        return roladmin;
    }

    public void setRoladmin(Boolean roladmin) {
        this.roladmin = roladmin;
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
