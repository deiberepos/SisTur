package com.dgaviria.sistur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogueoUsuarios extends AppCompatActivity{
    DatabaseReference miReferencia;
    DatabaseReference miHija;
    EditText editTextUsuario, editTextContrasena;
    ProgressBar barraProgreso;
    String nombreU,contrasenaU;
    AnimationDrawable animacion;
    LinearLayout miContenedor;
    Button botonIngresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logueo_usuarios);
        referenciar();
        botonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarPermiso();
            }
        });

    }

    private void referenciar() {
        miContenedor=findViewById(R.id.contenedor);
        editTextUsuario =  findViewById(R.id.editTextUsuario);
        editTextContrasena= findViewById(R.id.editTextPassword);
        barraProgreso = findViewById(R.id.barraProgreso);
        botonIngresar=findViewById(R.id.botonIngreso);
        //Inicio de la animación del fondo de pantalla
        animacion=(AnimationDrawable) miContenedor.getBackground();
        animacion.setEnterFadeDuration(2000);
        animacion.setExitFadeDuration(2000);
        //Final de la animación del fondo de pantalla

    }

    private void verificarPermiso(){
        Boolean finalizar=false;
        barraProgreso.setVisibility(View.VISIBLE);
        miReferencia= FirebaseDatabase.getInstance().getReference();
        if (editTextUsuario.getText().toString().trim().toLowerCase().isEmpty()) {
            editTextUsuario.setError("Nombre de usuario requerido");
            editTextUsuario.requestFocus();
            return;
        }
        else {
            nombreU = editTextUsuario.getText().toString().trim().toLowerCase();
            editTextUsuario.setText(nombreU);
        }
        if (editTextContrasena.getText().toString().trim().isEmpty()) {
            editTextContrasena.setError("Contraseña requerida");
            editTextContrasena.requestFocus();
            return;
        }
        else {
            contrasenaU = editTextContrasena.getText().toString().trim();
            editTextContrasena.setText(contrasenaU);
        }
        switch (nombreU){
            case "deiber":
                miHija=miReferencia.child("deiber");
                break;
            case "cristian":
                miHija=miReferencia.child("cristian");
                break;
            case "felipe":
                miHija=miReferencia.child("felipe");
                break;
            case "administrador":
                miHija=miReferencia.child("administrador");
                break;
            default:
                Toast.makeText(getApplicationContext(), "El usuario no es válido", Toast.LENGTH_SHORT).show();
                finalizar=true;
                break;
        }
        barraProgreso.setVisibility(View.GONE);
        if (!finalizar) {
            miHija.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String miValidar = dataSnapshot.getValue(String.class);
                    if (miValidar.equals(contrasenaU) && !contrasenaU.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Ya tienes acceso", Toast.LENGTH_SHORT).show();
                        if (nombreU.equals("administrador")){
                            Intent miIntento = new Intent(getApplicationContext(), CrearUsuarios.class);
                            startActivity(miIntento);
                            finish();
                        }
                        else{
                            Intent miIntento = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(miIntento);
                            finish();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Tu clave está errada, intenta de nuevo", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animacion!=null && !animacion.isRunning())
            animacion.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (animacion!=null && !animacion.isRunning())
            animacion.stop();
    }
}
