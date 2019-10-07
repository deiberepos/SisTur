package com.dgaviria.sistur.usuarios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dgaviria.sistur.CensoPoblacional;
import com.dgaviria.sistur.ListarCensoPoblacion;
import com.dgaviria.sistur.clases.Censo;
import com.dgaviria.sistur.clases.Roles;
import com.dgaviria.sistur.clases.Usuarios;
import com.dgaviria.sistur.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CrearUsuarios extends AppCompatActivity {
    DatabaseReference miReferencia,misDatos;
    EditText editTextUsuario, editTextContrasena,editTextVerificaContrasena,editTextCorreo,editTextNombres ;
    ProgressBar barraProgreso;
    String usuarioU,contrasenaU,contrasenaV,correoE,nombresU,nombreRol;
    LinearLayout miContenedor;
    Button botonRegistra,botonactualizar;
    RadioGroup rolSeleccionado;
    Integer rolUsuario=0;
    RadioButton roladmin,rolgest,rolcomp,rolbasic;
    private String opciones ="",usuarioo,nombre,contraseña,correo;
    private int tipo;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_usuarios);
        referenciar();

        if(opciones.equals("crear")) {
            botonactualizar.setVisibility(View.INVISIBLE);
        }else {
            botonRegistra.setVisibility(View.INVISIBLE);
            usuarioo = bundle.getString("usuario");
            editTextUsuario.setText(usuarioo);
            nombre = bundle.getString("nombre");
            editTextNombres.setText(nombre);
            contraseña = bundle.getString("contrasena");
            editTextContrasena.setText(contraseña);
            contraseña = bundle.getString("contrasena");
            editTextVerificaContrasena.setText(contraseña);
            correo = bundle.getString("correo");
            editTextCorreo.setText(correo);
            tipo = bundle.getInt("tipo");
            if (tipo==1)
            {
                roladmin.setChecked(true);
            }
            if (tipo==2)
            {
                rolgest.setChecked(true);
            }
            if (tipo==3)
            {
                rolcomp.setChecked(true);
            }
            if (tipo==4)
            {
                rolbasic.setChecked(true);
            }

        }

        botonactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  actualizarUsuario();
            }
        });
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
        editTextNombres=findViewById(R.id.editTextNombre);
        barraProgreso = findViewById(R.id.barraProgreso);
        botonRegistra=findViewById(R.id.botonRegistrar);
        rolSeleccionado=findViewById(R.id.rgRoles);
        botonactualizar=findViewById(R.id.botonActualizar);
        roladmin=findViewById(R.id.roladmin);
        rolgest=findViewById(R.id.rolgest);
        rolcomp=findViewById(R.id.rolcomp);
        rolbasic=findViewById(R.id.rolbasi);
        bundle=getIntent().getExtras();
        opciones=bundle.getString("opcion");


    }

    private void verificarDatosUsuario(){
        barraProgreso.setVisibility(View.VISIBLE);
        miReferencia= FirebaseDatabase.getInstance().getReference("usuarios");
        //Verifica que escriba los valores en todos los campos requeridos
        if (editTextUsuario.getText().toString().trim().toLowerCase().isEmpty()){
            editTextUsuario.setError("Nombre de usuario requerido");
            editTextUsuario.requestFocus();
            barraProgreso.setVisibility(View.GONE);
            return;
        }
        else{
            usuarioU=editTextUsuario.getText().toString().trim().toLowerCase();
            editTextUsuario.setText(usuarioU);
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
        if (editTextNombres.getText().toString().trim().isEmpty()){
            editTextNombres.setError("Los nombres y apellidos son requeridos");
            editTextNombres.requestFocus();
            barraProgreso.setVisibility(View.GONE);
            return;
        }
        else{
            nombresU=editTextNombres.getText().toString().trim();
            editTextNombres.setText(nombresU);
        }
        //verifica el rol seleccionado
        rolSeleccionado.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int cualRol) {
                switch (cualRol) {
                    case R.id.roladmin:
                        rolUsuario=1;
                        nombreRol="administrador";
                        break;
                    case R.id.rolgest:
                        rolUsuario=2;
                        nombreRol="gestor";
                        break;
                    case R.id.rolcomp:
                        rolUsuario=3;
                        nombreRol="compras";
                        break;
                    case R.id.rolbasi:
                        rolUsuario=4;
                        nombreRol="basico";
                        break;
                    default:
                        rolUsuario=0;
                        nombreRol="";
                        break;
                }
            }
        });

        barraProgreso.setVisibility(View.GONE);
        if (rolUsuario!=0) {
            //verifica que no se repita el nombre del usuario
            miReferencia.child(usuarioU).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        //valida el nombre del usuario
                        if (dataSnapshot.child("usuario").getValue(String.class).equals(usuarioU)) {
                            Toast.makeText(getApplicationContext(), "Este usuario ya existe, intente otro nombre", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            crearNuevoUsuario();
                            finish();
                        }
                    }
                    else {
                        crearNuevoUsuario();
                        finish();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
            Toast.makeText(getApplicationContext(), "Debe seleccionar un rol", Toast.LENGTH_SHORT).show();
    }

    /*private void actualizarUsuario(){

        miReferencia=FirebaseDatabase.getInstance().getReference();
        AlertDialog.Builder builder = new AlertDialog.Builder(CrearUsuarios.this);
        builder.setTitle("Desea actualizar: "+usuarioU);
        builder.setMessage("Está seguro que desea actualizar este Infante?");
        builder.setPositiveButton("ACTUALIZAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                miReferencia=FirebaseDatabase.getInstance().getReference();
                if(usuarioo.equals(usuarioU)){
                    miReferencia.child("usuarios").child(usuarioU).setValue(new Usuarios(usuarioU,contrasenaU,nombresU,correoE,false,rolUsuario==1,rolUsuario==2,rolUsuario==3,rolUsuario==4));
                }else {
                    miReferencia.child("usuarios").child(usuarioo).removeValue();
                    miReferencia.child("usuarios").child(usuarioU).setValue(new Usuarios(usuarioU,contrasenaU,nombresU,correoE,false,rolUsuario==1,rolUsuario==2,rolUsuario==3,rolUsuario==4));
                    misDatos=miReferencia.child("rol").child(nombreRol).child("miembros");
                    misDatos.child(usuarioU).setValue(new Roles(true));
                }
                Toast.makeText(CrearUsuarios.this,"Actualizado con éxito",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ListarCensoPoblacion.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"No se realizó ninguna actualización",Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }*/


    private void crearNuevoUsuario() {
        //construye el objeto que se va a guardar en la base de datos
        miReferencia= FirebaseDatabase.getInstance().getReference();
        //guarda los datos del usuario
        misDatos=miReferencia.child("usuarios");
        misDatos.child(usuarioU).setValue(new Usuarios(usuarioU,contrasenaU,nombresU,correoE,false,rolUsuario==1,rolUsuario==2,rolUsuario==3,rolUsuario==4));
        misDatos=miReferencia.child("rol").child(nombreRol).child("miembros");
        misDatos.child(usuarioU).setValue(new Roles(true));

        Toast.makeText(this,"Usuario creado exitosamente",Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    protected void onPause() {
        super.onPause();

    }
}
