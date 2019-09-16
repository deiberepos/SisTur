package com.dgaviria.sistur.Usuarios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dgaviria.sistur.Clases.Usuarios;
import com.dgaviria.sistur.Menus.MenuPrincipal;
import com.dgaviria.sistur.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogueoUsuarios extends AppCompatActivity{
    DatabaseReference miReferencia;
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
        /*Inicio de la animación del fondo de pantalla
        animacion=(AnimationDrawable) miContenedor.getBackground();
        animacion.setEnterFadeDuration(2000);
        animacion.setExitFadeDuration(2000);
        //Final de la animación del fondo de pantalla*/

    }

    private void verificarPermiso(){
        barraProgreso.setVisibility(View.VISIBLE);
        miReferencia= FirebaseDatabase.getInstance().getReference("usuarios");

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
        miReferencia.child(nombreU).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    //se crea un objeto para que lea uno a uno los campos de la BD,
                    //se hace uno a uno para garantizar el paso de los Boolean correctamente
                    Usuarios usuarioC=new Usuarios(
                            dataSnapshot.child("usuario").getValue(String.class),
                            dataSnapshot.child("contrasena").getValue(String.class),
                            dataSnapshot.child("nombre").getValue(String.class),
                            dataSnapshot.child("correo").getValue(String.class),
                            dataSnapshot.child("rolsuper").getValue(Boolean.class),
                            dataSnapshot.child("roladmin").getValue(Boolean.class),
                            dataSnapshot.child("rolgestor").getValue(Boolean.class),
                            dataSnapshot.child("rolcompras").getValue(Boolean.class),
                            dataSnapshot.child("rolbasico").getValue(Boolean.class));
                    //valida el nombre del usuario
                    if (usuarioC.getUsuario().equals(nombreU)) {
                        //valida la contraseña
                        if(usuarioC.getContrasena().equals(contrasenaU)) {
                            //verifica si tiene el rol de administrador
                            Intent miIntento = new Intent(getApplicationContext(), MenuPrincipal.class);
                            startActivity(miIntento);
                        }
                        else
                            Toast.makeText(getApplicationContext(), "La contraseña está errada", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "El usuario "+nombreU+" no existe", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "El usuario "+nombreU+" no existe", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        barraProgreso.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*if (animacion!=null && !animacion.isRunning())
            animacion.start();*/
    }
    @Override
    protected void onPause() {
        super.onPause();
        /*if (animacion!=null && !animacion.isRunning())
            animacion.stop();*/
    }
}
