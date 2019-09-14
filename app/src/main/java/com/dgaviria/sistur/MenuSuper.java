package com.dgaviria.sistur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MenuSuper extends AppCompatActivity implements View.OnClickListener{
    private ImageButton btnregUsuarios, btnregHogares,btnCensoPoblacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_super);
        referenciar();
    }

    private void referenciar() {
        btnregUsuarios=findViewById(R.id.idBtnRegisMenuSuper);
        btnregHogares=findViewById(R.id.idBtnCdiMenuSuper);
        btnCensoPoblacion=findViewById(R.id.idBtnCensoPoblacion);
        btnregUsuarios.setOnClickListener(this);
        btnregHogares.setOnClickListener(this);
        btnCensoPoblacion.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.idBtnRegisMenuSuper:
                Intent miIntento = new Intent(getApplicationContext(), CrearUsuarios.class);
                startActivity(miIntento);
                break;
            case R.id.idBtnCdiMenuSuper:
                Intent intent = new Intent(getApplicationContext(), RegistrarCDIHCB.class);
                startActivity(intent);
                break;
            case R.id.idBtnCensoPoblacion:
                Intent intentcenso = new Intent(getApplicationContext(), CensoPoblacional.class);
                startActivity(intentcenso);
                break;

        }
    }
}
