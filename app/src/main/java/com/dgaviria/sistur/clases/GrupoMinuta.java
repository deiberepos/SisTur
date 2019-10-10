package com.dgaviria.sistur.clases;

import java.util.ArrayList;
import java.util.List;

public class GrupoMinuta {
    public String textoPrepara;
    public String textoProcedimiento;
    public final List<GrupoAlimento> grupoPrepara=new ArrayList<GrupoAlimento>();

    public GrupoMinuta(String preparacion,String procedimiento){
        this.textoPrepara=preparacion;
        this.textoProcedimiento=procedimiento;
    }
}
