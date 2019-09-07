package com.dgaviria.sistur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogueoUsuarios extends AppCompatActivity implements View.OnClickListener{
    //FirebaseAuth miAuth;
    DatabaseReference miReferencia;
    DatabaseReference miHija;
    EditText editTextUsuario, editTextContrasena;
    ProgressBar barraProgreso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logueo_usuarios);
        referenciar();
    }

    private void referenciar() {
        //miAuth = FirebaseAuth.getInstance();

        editTextUsuario =  findViewById(R.id.editTextUsuario);
        editTextContrasena= findViewById(R.id.editTextPassword);
        barraProgreso = findViewById(R.id.barraProgreso);

        findViewById(R.id.botonIngreso).setOnClickListener(this);
    }

    private void verificarPermiso(){
        Boolean finalizar=false;
        miReferencia= FirebaseDatabase.getInstance().getReference();
        String nombreU=editTextUsuario.getText().toString().trim();
        final String contrasenaU=editTextContrasena.getText().toString().trim();
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
            default:
                finalizar=true;
                break;
        }
        if (!finalizar) {
            miHija.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String miValidar = dataSnapshot.getValue(String.class);
                    if (miValidar.equals(contrasenaU)) {
                        Toast.makeText(getApplicationContext(), "Ya tienes acceso", Toast.LENGTH_SHORT).show();
                        Intent miIntento=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(miIntento);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "No tienes acceso, "+contrasenaU, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
    /*private void userLogin() {
        String usuario = editTextUsuario.getText().toString().trim();
        String contrasena = editTextContrasena.getText().toString().trim();

        if (usuario.trim().isEmpty()) {
            editTextUsuario.setError("Nombre de usuario requerido");
            editTextUsuario.requestFocus();
            return;
        }

        if (contrasena.isEmpty()) {
            editTextContrasena.setError("Contraseña obligatoria");
            editTextContrasena.requestFocus();
            return;
        }

        if (contrasena.length() < 8) {
            editTextContrasena.setError("EL número de caracteres debe ser mínimo de 8");
            editTextContrasena.requestFocus();
            return;
        }

        barraProgreso.setVisibility(View.VISIBLE);

        miAuth.signInWithEmailAndPassword(usuario, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                barraProgreso.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(LogueoUsuarios.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (miAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.botonIngreso:
                verificarPermiso();
                break;
        }
    }
}
