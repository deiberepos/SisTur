package com.dgaviria.sistur;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.dgaviria.sistur.usuarios.LogueoUsuarios;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class Splash extends AwesomeSplash {
    /*ImageView imagen;
    private final int DURACION_SPLASH = 2000;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        imagen = findViewById(R.id.imagenSplash);
        new Handler().postDelayed(new Runnable(){
            public void run(){
                // Cuando pasen los 2 segundos, pasamos a la actividad principal de la aplicación
                Intent intento = new Intent(Splash.this, LogueoUsuarios.class);
                startActivity(intento);
                finish();
            }
        }, DURACION_SPLASH);
    }*/


    @Override
    public void initSplash(ConfigSplash configSplash) {

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        configSplash.setBackgroundColor(R.color.colorAzulpastel);
        configSplash.setAnimCircularRevealDuration(900);
        configSplash.setRevealFlagX(Flags.REVEAL_LEFT);
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM);


        configSplash.setLogoSplash(R.drawable.nuevo__logo3);
        configSplash.setAnimLogoSplashDuration(5000);
        configSplash.setAnimLogoSplashTechnique(Techniques.BounceInLeft);

        configSplash.setTitleSplash("Sistur Turmina");
        configSplash.setTitleTextColor(R.color.colorNegro);
        configSplash.setTitleTextSize(30f);
        configSplash.setAnimTitleDuration(3500);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);


    }

    @Override
    public void animationsFinished() {
        startActivity(new Intent(Splash.this , LogueoUsuarios.class));
    }
}
