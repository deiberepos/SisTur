package com.dgaviria.sistur;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

public class RegistrarCDIHCB extends AppCompatActivity {
    private AnimationDrawable animacion;
    private ScrollView contenedorScroll;
    private EditText nombreCentro, nombreEncargado, nomContacto, dirEncargado, dirContacto, telEncargado,telContacto;
    private Button btnGuardar;
    ListView listaveredas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cdihcb);
        referenciar();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void referenciar() {
        nombreCentro=findViewById(R.id.idedtnombreregCentro);
        nombreEncargado=findViewById(R.id.idedtnombreencargado);
        nomContacto=findViewById(R.id.idedtnombreContacto);
        dirEncargado=findViewById(R.id.idedtdirEncargado);
        dirContacto=findViewById(R.id.idedtdirContacto);
        telEncargado=findViewById(R.id.idedttelEncargado);
        telContacto=findViewById(R.id.idedttelContacto);
       // listaveredas=findViewById(R.id.idlisVeredas);
        btnGuardar=findViewById(R.id.idbtnregCDI);
        contenedorScroll=findViewById(R.id.idcontenerRegCDI);
        //Inicio de la animación del fondo de pantalla
        animacion=(AnimationDrawable) contenedorScroll.getBackground();
        animacion.setEnterFadeDuration(2000);
        animacion.setExitFadeDuration(2000);
        //Final de la animación del fondo de pantalla
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.veredas,android.R.layout.simple_list_item_1);
        //listaveredas.setAdapter(adapter);

    }
}
