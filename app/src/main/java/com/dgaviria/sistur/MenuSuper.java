package com.dgaviria.sistur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.dgaviria.sistur.Usuarios.CrearUsuarios;

public class MenuSuper extends AppCompatActivity implements View.OnClickListener{
    private ImageButton btnregUsuarios, btnregHogares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_super);
        referenciar();
    }

    private void referenciar() {
        btnregUsuarios=findViewById(R.id.idBtnRegisMenuSuper);
        btnregHogares=findViewById(R.id.idBtnCdiMenuSuper);
        btnregUsuarios.setOnClickListener(this);
        btnregHogares.setOnClickListener(this);
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

        }
    }
}
