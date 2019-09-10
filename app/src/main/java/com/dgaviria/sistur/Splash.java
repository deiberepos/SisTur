package com.dgaviria.sistur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
    ImageView imagen;
    private final int DURACION_SPLASH = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        imagen = findViewById(R.id.imagenSplash);
        new Handler().postDelayed(new Runnable(){
            public void run(){
                // Cuando pasen los 2 segundos, pasamos a la actividad principal de la aplicación
                Intent intento = new Intent(Splash.this,LogueoUsuarios.class);
                startActivity(intento);
                finish();
            }
        }, DURACION_SPLASH);
    }
}
