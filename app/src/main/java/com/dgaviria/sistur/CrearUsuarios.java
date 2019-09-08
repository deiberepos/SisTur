package com.dgaviria.sistur;

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
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CrearUsuarios extends AppCompatActivity {
    DatabaseReference miReferencia;
    EditText editTextUsuario, editTextContrasena,editTextVerificaContrasena,editTextCorreo;
    ProgressBar barraProgreso;
    String nombreU,contrasenaU,contrasenaV,correoE;
    AnimationDrawable animacion;
    LinearLayout miContenedor;
    Button botonRegistra;
    RadioGroup rolSeleccionado;
    Integer rolUsuario=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_usuarios);
        referenciar();
        botonRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarDatosUsuario();
            }
        });
    }
    private void referenciar(){
        miContenedor=findViewById(R.id.contenedor);
        editTextUsuario =  findViewById(R.id.editTextUsuario);
        editTextContrasena= findViewById(R.id.editTextPassword);
        editTextVerificaContrasena= findViewById(R.id.editTextVerificaPassword);
        editTextCorreo=findViewById(R.id.editTextCorreo);
        barraProgreso = findViewById(R.id.barraProgreso);
        botonRegistra=findViewById(R.id.botonRegistrar);
        rolSeleccionado=findViewById(R.id.rgRoles);
        //Inicio de la animación del fondo de pantalla
        animacion=(AnimationDrawable) miContenedor.getBackground();
        animacion.setEnterFadeDuration(2000);
        animacion.setExitFadeDuration(2000);
        //Final de la animación del fondo de pantalla
    }

    private void verificarDatosUsuario(){
        Boolean finalizar=false;
        barraProgreso.setVisibility(View.VISIBLE);
        miReferencia= FirebaseDatabase.getInstance().getReference();
        //Verifica que escriba los valores en todos los campos requeridos
        if (editTextUsuario.getText().toString().trim().toLowerCase().isEmpty()){
            editTextUsuario.setError("Nombre de usuario requerido");
            editTextUsuario.requestFocus();
            barraProgreso.setVisibility(View.GONE);
            return;
        }
        else{
            nombreU=editTextUsuario.getText().toString().trim().toLowerCase();
            editTextUsuario.setText(nombreU);
        }
        if (editTextContrasena.getText().toString().trim().isEmpty()){
            editTextContrasena.setError("Contraseña requerida");
            editTextContrasena.requestFocus();
            barraProgreso.setVisibility(View.GONE);
            return;
        }
        else{
            contrasenaU=editTextContrasena.getText().toString().trim();
            editTextContrasena.setText(contrasenaU);
        }
        if (editTextVerificaContrasena.getText().toString().trim().isEmpty()){
            editTextVerificaContrasena.setError("Validación requerida");
            editTextVerificaContrasena.requestFocus();
            barraProgreso.setVisibility(View.GONE);
            return;
        }
        else{
            //Verifica que las contraseñas coincidan
            contrasenaV=editTextVerificaContrasena.getText().toString().trim();
            editTextVerificaContrasena.setText(contrasenaV);
            if (!contrasenaU.equals(contrasenaV)){
                editTextVerificaContrasena.setError("Las contraseñas no coinciden");
                editTextVerificaContrasena.requestFocus();
                barraProgreso.setVisibility(View.GONE);
                return;
            }
            else{
                contrasenaV=editTextVerificaContrasena.getText().toString().trim();
                editTextVerificaContrasena.setText(contrasenaV);
            }
        }
        if (editTextCorreo.getText().toString().trim().toLowerCase().isEmpty()){
            editTextCorreo.setError("El correo es requerido");
            editTextCorreo.requestFocus();
            barraProgreso.setVisibility(View.GONE);
            return;
        }
        else{
            //Verifica que el correo esté bien escrito
            correoE=editTextCorreo.getText().toString().trim().toLowerCase();
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(correoE).matches()){
                editTextCorreo.setText(correoE);
            }
            else{
                editTextCorreo.setError("El correo no es válido");
                editTextCorreo.requestFocus();
                barraProgreso.setVisibility(View.GONE);
                return;
            }
        }
        //verifica el rol seleccionado
        rolSeleccionado.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int cualRol) {
                switch (cualRol) {
                    case R.id.opbRolUno:
                        rolUsuario=1;
                        break;
                    case R.id.opbRolDos:
                        rolUsuario=2;
                        break;
                    case R.id.opbRolTres:
                        rolUsuario=3;
                        break;
                    default:
                        rolUsuario=0;
                        break;
                }
            }
        });
        barraProgreso.setVisibility(View.GONE);
        if (rolUsuario!=0) {
            if (nombreU.equals("deiber") || nombreU.equals("cristian") || nombreU.equals("felipe") || nombreU.equals("administrador")) {
                Toast.makeText(getApplicationContext(), "Usuario ya existe, intente de nuevo", Toast.LENGTH_SHORT).show();
            } else {
                Intent miIntento = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(miIntento);
                finish();
            }
        }
        else
            Toast.makeText(getApplicationContext(), "Debe seleccionar un rol", Toast.LENGTH_SHORT).show();
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
