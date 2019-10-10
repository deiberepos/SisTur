package com.dgaviria.sistur.clases;

import java.util.ArrayList;
import java.util.List;

public class GrupoAlimento {
    public String textoALimento;
    public String textoInformacion;
    public final List<String> grupoAlimento=new ArrayList<String>();

    public GrupoAlimento(String alimento,String informacion){
        this.textoALimento=alimento;
        this.textoInformacion=informacion;
    }
}
