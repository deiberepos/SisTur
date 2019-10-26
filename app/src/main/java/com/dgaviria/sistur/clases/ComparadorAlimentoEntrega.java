package com.dgaviria.sistur.clases;

import java.util.Comparator;

public class ComparadorAlimentoEntrega implements Comparator<AlimentoEntrega> {
    public int compare(AlimentoEntrega alimento1, AlimentoEntrega alimento2) {
        return alimento1.getIngrediente().compareTo(alimento2.getIngrediente());
    }
}
