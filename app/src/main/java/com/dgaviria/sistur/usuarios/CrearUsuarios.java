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
    String usuarioU,contrasenaU,contrasenaV,correoE,nombresU,nombreRol;
    LinearLayout miContenedor;
    Button botonRegistra,botonactualizar;
    RadioGroup grupoRoles;
    RadioButton rolgest,rolcomp,rolbasic;
    String opciones ="",usuario,nombre, contrasena,contrasenav,correo;
    Bundle recibeParametros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_usuarios);
        referenciar();

        if(opciones.equals("crear")) {
            botonactualizar.setVisibility(View.INVISIBLE);
        }else {
            botonRegistra.setVisibility(View.INVISIBLE);
            usuario= recibeParametros.getString("usuario");
            editTextUsuario.setText(usuario);
            nombre = recibeParametros.getString("nombre");
            editTextNombres.setText(nombre);
            contrasena = recibeParametros.getString("contrasena");
            editTextContrasena.setText(contrasena);
            contrasenav = contrasena;
            editTextVerificaContrasena.setText(contrasenav);
            correo = recibeParametros.getString("correo");
            editTextCorreo.setText(correo);
            switch (recibeParametros.getString("tipo")){
                case "gestor":
                    rolgest.setChecked(true);
                    break;
                case "compras":
                    rolcomp.setChecked(true);
                    break;
                case "basico":
                    rolbasic.setChecked(true);
                    break;
            }
        }
        botonactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarUsuario();
            }
        });
        botonRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarDatosUsuario();
            }
        });
        switch(grupoRoles.getCheckedRadioButtonId()){
            case R.id.rolbasi:
                nombreRol="basico";
                break;
            case R.id.rolcomp:
                nombreRol="compras";
                break;
            case R.id.rolgest:
                nombreRol="gestor";
                break;
        }

    }
    private void referenciar(){
        miContenedor=findViewById(R.id.contenedor);
        editTextUsuario =  findViewById(R.id.editTextUsuario);
        editTextContrasena= findViewById(R.id.editTextPassword);
        editTextVerificaContrasena= findViewById(R.id.editTextVerificaPassword);
        editTextCorreo=findViewById(R.id.editTextCorreo);
        editTextNombres=findViewById(R.id.editTextNombre);
        botonRegistra=findViewById(R.id.botonRegistrar);
        botonactualizar=findViewById(R.id.botonActualizar);
        grupoRoles=findViewById(R.id.grupoRoles);
        rolgest=findViewById(R.id.rolgest);
        rolcomp=findViewById(R.id.rolcomp);
        rolbasic=findViewById(R.id.rolbasi);
        recibeParametros =getIntent().getExtras();
        opciones= recibeParametros.getString("opcion");
    }

    private void verificarDatosUsuario(){
        miReferencia= FirebaseDatabase.getInstance().getReference("usuarios");
        //Verifica que escriba los valores en todos los campos requeridos
        if (editTextUsuario.getText().toString().trim().toLowerCase().isEmpty()){
            editTextUsuario.setError("Nombre de usuario requerido");
            editTextUsuario.requestFocus();
            return;
        }
        else{
            usuarioU=editTextUsuario.getText().toString().trim().toLowerCase();
            editTextUsuario.setText(usuarioU);
        }
        if (editTextContrasena.getText().toString().trim().isEmpty()){
            editTextContrasena.setError("Contraseña requerida");
            editTextContrasena.requestFocus();
            return;
        }
        else{
            contrasenaU=editTextContrasena.getText().toString().trim();
            editTextContrasena.setText(contrasenaU);
        }
        if (editTextVerificaContrasena.getText().toString().trim().isEmpty()){
            editTextVerificaContrasena.setError("Validación requerida");
            editTextVerificaContrasena.requestFocus();
            return;
        }
        else{
            //Verifica que las contraseñas coincidan
            contrasenaV=editTextVerificaContrasena.getText().toString().trim();
            editTextVerificaContrasena.setText(contrasenaV);
            if (!contrasenaU.equals(contrasenaV)){
                editTextVerificaContrasena.setError("Las contraseñas no coinciden");
                editTextVerificaContrasena.requestFocus();
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
                return;
            }
        }
        if (editTextNombres.getText().toString().trim().isEmpty()){
            editTextNombres.setError("Los nombres y apellidos son requeridos");
            editTextNombres.requestFocus();
            return;
        }
        else{
            nombresU=editTextNombres.getText().toString().trim();
            editTextNombres.setText(nombresU);
        }
        //verifica el rol seleccionado
        if (rolgest.isChecked())
            nombreRol="gestor";
        else if (rolcomp.isChecked())
            nombreRol="compras";
        else if (rolbasic.isChecked())
            nombreRol="basico";
        else nombreRol="";

        if (!nombreRol.isEmpty()) {
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
                            Intent intent = new Intent(getApplicationContext(),ListarUsuarios.class);
                            startActivity(intent);
                        }
                    }
                    else {
                        crearNuevoUsuario();
                        Intent intent = new Intent(getApplicationContext(),ListarUsuarios.class);
                        startActivity(intent);
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

    private void actualizarUsuario(){
        verificarDatosUsuario();
        miReferencia=FirebaseDatabase.getInstance().getReference();
        AlertDialog.Builder builder = new AlertDialog.Builder(CrearUsuarios.this);
        builder.setTitle("Desea actualizar: "+ usuarioU);
        builder.setMessage("Está seguro que desea actualizar este Usuario?");
        builder.setPositiveButton("ACTUALIZAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                miReferencia=FirebaseDatabase.getInstance().getReference();
                if(usuario.equals(usuarioU)){
                    miReferencia.child("usuarios").child(usuarioU).setValue(new Usuarios(usuarioU,contrasenaU,nombresU,correoE,nombreRol));
                }else {
                    miReferencia.child("usuarios").child(usuario).removeValue();
                    miReferencia.child("usuarios").child(usuarioU).setValue(new Usuarios(usuarioU,contrasenaU,nombresU,correoE,nombreRol));
                }
                Toast.makeText(CrearUsuarios.this,"Actualizado con éxito",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ListarUsuarios.class);
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
    }


    private void crearNuevoUsuario() {
        //construye el objeto que se va a guardar en la base de datos
        miReferencia= FirebaseDatabase.getInstance().getReference();
        //guarda los datos del usuario
        misDatos=miReferencia.child("usuarios").child(usuarioU);
        misDatos.setValue(new Usuarios(usuarioU,contrasenaU,nombresU,correoE,nombreRol));
        Toast.makeText(this,"Usuario creado exitosamente",Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //finish();
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
