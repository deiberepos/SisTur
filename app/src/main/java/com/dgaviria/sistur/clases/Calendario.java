package com.dgaviria.sistur.clases;

public class Calendario {
    public String nombresemana;
    public String diainicial;
    public String mesinicial;
    public String diafinal;
    public String mesfinal;
    public String ano;
    public String minuta1;
    public String minuta2;
    public String minuta3;
    public String minuta4;
    public String minuta5;

    public Calendario() {
    }

    public Calendario(String nombresemana, String diainicial, String mesinicial, String diafinal, String mesfinal, String ano, String minuta1, String minuta2, String minuta3, String minuta4, String minuta5) {
        this.nombresemana = nombresemana;
        this.diainicial = diainicial;
        this.mesinicial = mesinicial;
        this.diafinal = diafinal;
        this.mesfinal = mesfinal;
        this.ano = ano;
        this.minuta1 = minuta1;
        this.minuta2 = minuta2;
        this.minuta3 = minuta3;
        this.minuta4 = minuta4;
        this.minuta5 = minuta5;
    }

    public String getNombresemana() {
        return nombresemana;
    }

    public void setNombresemana(String nombresemana) {
        this.nombresemana = nombresemana;
    }

    public String getDiainicial() {
        return diainicial;
    }

    public void setDiainicial(String diainicial) {
        this.diainicial = diainicial;
    }

    public String getMesinicial() {
        return mesinicial;
    }

    public void setMesinicial(String mesinicial) {
        this.mesinicial = mesinicial;
    }

    public String getDiafinal() {
        return diafinal;
    }

    public void setDiafinal(String diafinal) {
        this.diafinal = diafinal;
    }

    public String getMesfinal() {
        return mesfinal;
    }

    public void setMesfinal(String mesfinal) {
        this.mesfinal = mesfinal;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getMinuta1() {
        return minuta1;
    }

    public void setMinuta1(String minuta1) {
        this.minuta1 = minuta1;
    }

    public String getMinuta2() {
        return minuta2;
    }

    public void setMinuta2(String minuta2) {
        this.minuta2 = minuta2;
    }

    public String getMinuta3() {
        return minuta3;
    }

    public void setMinuta3(String minuta3) {
        this.minuta3 = minuta3;
    }

    public String getMinuta4() {
        return minuta4;
    }

    public void setMinuta4(String minuta4) {
        this.minuta4 = minuta4;
    }

    public String getMinuta5() {
        return minuta5;
    }

    public void setMinuta5(String minuta5) {
        this.minuta5 = minuta5;
    }
}
