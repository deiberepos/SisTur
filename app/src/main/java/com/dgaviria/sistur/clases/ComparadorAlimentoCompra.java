package com.dgaviria.sistur.clases;

import java.util.Comparator;

public class ComparadorAlimentoCompra  implements Comparator<AlimentoCompra> {
    public int compare(AlimentoCompra alimento1, AlimentoCompra alimento2) {
        return alimento1.getIngrediente().compareTo(alimento2.getIngrediente());
    }
}
