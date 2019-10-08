package com.dgaviria.sistur.clases;

import java.util.ArrayList;
import java.util.List;

public class GrupoMinuta {
    public String textoGrupo;
    public final List<String> grupoPrepara=new ArrayList<String>();

    public GrupoMinuta(String texto){
        this.textoGrupo=texto;
    }
}
