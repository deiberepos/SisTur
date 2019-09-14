package com.dgaviria.sistur;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class CensoPoblacional extends AppCompatActivity {
Button btnguardar;
EditText nombreInfante,apellidoInfante,observaciones,nombrePadre,nombreMadre,TeleMadre,TelePadre,DirPadre,DirMadre;
RadioButton genero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.censo_poblacional);
        referenciar();
    }


    public void referenciar()
    {

        btnguardar=findViewById(R.id.btnguardarcenso);

    }
}
