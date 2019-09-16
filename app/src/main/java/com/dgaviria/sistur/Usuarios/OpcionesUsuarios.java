package com.dgaviria.sistur.Usuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dgaviria.sistur.R;
import com.dgaviria.sistur.Usuarios.ListarUsuarios;

public class OpcionesUsuarios extends AppCompatActivity {
    Button botonCrear,botonGestionar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones_usuarios);
        referenciar();
    }

    private void referenciar() {
        botonCrear=findViewById(R.id.botonCrea);
        botonGestionar=findViewById(R.id.botonGestion);
        botonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent miIntento = new Intent(getApplicationContext(), CrearUsuarios.class);
                startActivity(miIntento);
            }
        });
        botonGestionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent miIntento = new Intent(getApplicationContext(), ListarUsuarios.class);
                startActivity(miIntento);
            }
        });
    }
}
