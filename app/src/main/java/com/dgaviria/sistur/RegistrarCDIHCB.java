package com.dgaviria.sistur;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.dgaviria.sistur.clases.CdiHcb;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarCDIHCB extends AppCompatActivity {
    private EditText edtnombreCentro, edtnombreEncargado, edtnomContacto, edtdirEncargado, edtdirContacto, edttelEncargado, edttelContacto;
    private Button btnGuardar, btnActualizar;
    private int actTipo,activoo;
    Bundle recibeParametros;
    private Spinner spnVeredas;
    private String opcion ="",actNombre, actnombreen, actdiren, acttelen, actnombrecon, actdircon, acttelcon;
    DatabaseReference miReferencia,misDatos;
    Boolean activo;
    CheckBox acti;
    String nombrecentro, nombreE, nombreC, direccionE, direccionC, telE, telC;
    public static String vereda, tipo;
    RadioGroup rgbtipo;
    RadioButton rbCentro, rbHogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_cdihcb);
        referenciar();

        if(opcion.equals("crear")) {
            btnActualizar.setVisibility(View.INVISIBLE);
        }
        else {
            btnGuardar.setVisibility(View.INVISIBLE);
            actNombre= recibeParametros.getString("nombrecentro");
            edtnombreCentro.setText(actNombre);
            actTipo= recibeParametros.getInt("tipo");
            if(actTipo==1){
                rbCentro.setChecked(true);
            }
            else{
                rbHogar.setChecked(true);
            }
            actnombreen= recibeParametros.getString("nombreen");
            edtnombreEncargado.setText(actnombreen);
            actdiren= recibeParametros.getString("direccionen");
            edtdirEncargado.setText(actdiren);
            acttelen= recibeParametros.getString("telefonoen");
            edttelEncargado.setText(acttelen);
            actnombrecon= recibeParametros.getString("nombrecon");
            edtnomContacto.setText(actnombrecon);
            actdircon= recibeParametros.getString("direccioncon");
            edtdirContacto.setText(actdircon);
            acttelcon= recibeParametros.getString("telefonocon");
            edttelContacto.setText(acttelcon);
            spnVeredas.setSelection(2);
            activoo= recibeParametros.getInt("activo");
            if(activoo==2){
                acti.setChecked(true);
            }
        }
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarDatos();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarDatosDos();
            }
        });

        rgbtipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.idrbCentro:
                        tipo="Centro de Desarrollo Infantil";
                        break;
                    case R.id.idrbHogar:
                        tipo="Hogar Comunitario Básico";
                        break;
                }
            }
        });

        spnVeredas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
             vereda= adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        acti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (acti.isChecked()){
                    activo=true;
                }
                else{
                    activo=false;
                }
            }
        });
        if(acti.isChecked()){
            activo=true;
        }
        else{
            activo=false;
        }
    }

    private void cargarDatos(){
        Boolean finalizar=false;
        if(edtnombreCentro.getText().toString().isEmpty()||edtdirEncargado.getText().toString().isEmpty()||
        edtnombreEncargado.getText().toString().isEmpty()||edttelEncargado.getText().toString().isEmpty()||
        edtnomContacto.getText().toString().isEmpty()||edtdirContacto.getText().toString().isEmpty()|| edttelContacto.getText().toString().isEmpty()){
            Toast.makeText(this,"Todos los campos son requeridos",Toast.LENGTH_SHORT).show();
        }else {
            nombrecentro = edtnombreCentro.getText().toString();
            nombreE = edtnombreEncargado.getText().toString();
            nombreC = edtnomContacto.getText().toString();
            direccionE = edtdirEncargado.getText().toString();
            direccionC = edtdirContacto.getText().toString();
            telE = edttelEncargado.getText().toString();
            telC = edttelContacto.getText().toString();
            guardarCentro();
        }

            /*
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
            }*/
            //verifica el rol seleccionado



            /*
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
        */
    }

    private void guardarCentro(){
        //construye el objeto que se va a guardar en la base de datos
        miReferencia= FirebaseDatabase.getInstance().getReference();
        //guarda los datos del usuario
        misDatos=miReferencia.child("Centros");
        misDatos.child(nombrecentro).setValue(new CdiHcb(nombrecentro,nombreE,nombreC,vereda,direccionE,direccionC,telE,telC,tipo,activo));
        Toast.makeText(getApplicationContext(),"Datos guardados correctamente",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), GestionarCDIHCB.class);
        startActivity(intent);
    }

    private void actualizarCentros(){
        miReferencia=FirebaseDatabase.getInstance().getReference();
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarCDIHCB.this);
        builder.setTitle("msj Actualizar");
        builder.setMessage("Está seguro que desea actualizar este centro?");
        builder.setPositiveButton("ACTUALIZAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(actNombre.equals(nombrecentro)){
                    miReferencia.child("Centros").child(nombrecentro).setValue(new CdiHcb(nombrecentro,nombreE,nombreC,vereda,direccionE,direccionC,telE,telC,tipo,activo));
                }else {
                    miReferencia.child("Centros").child(actNombre).removeValue();
                    miReferencia.child("Centros").child(nombrecentro).setValue(new CdiHcb(nombrecentro, nombreE, nombreC, vereda, direccionE, direccionC, telE, telC, tipo,activo));
                }
                Toast.makeText(getApplicationContext(),"Actualizado con éxito",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), GestionarCDIHCB.class);
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
        //Intent intent = new Intent(getApplicationContext(), GestionarCDIHCB.class);
        //startActivity(intent);
    }
    private void cargarDatosDos() {
        if (edtnombreCentro.getText().toString().isEmpty() || edtdirEncargado.getText().toString().isEmpty() ||
                edtnombreEncargado.getText().toString().isEmpty() || edttelEncargado.getText().toString().isEmpty() ||
                edtnomContacto.getText().toString().isEmpty() || edtdirContacto.getText().toString().isEmpty() || edttelContacto.getText().toString().isEmpty()) {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
        } else {
            nombrecentro = edtnombreCentro.getText().toString();
            nombreE = edtnombreEncargado.getText().toString();
            nombreC = edtnomContacto.getText().toString();
            direccionE = edtdirEncargado.getText().toString();
            direccionC = edtdirContacto.getText().toString();
            telE = edttelEncargado.getText().toString();
            telC = edttelContacto.getText().toString();
            actualizarCentros();
        }
    }

    private void referenciar() {
        edtnombreCentro =findViewById(R.id.idedtnombreregCentro);
        edtnombreEncargado =findViewById(R.id.idedtnombreencargado);
        edtnomContacto =findViewById(R.id.idedtnombreContacto);
        edtdirEncargado =findViewById(R.id.idedtdirEncargado);
        edtdirContacto =findViewById(R.id.idedtdirContacto);
        edttelEncargado =findViewById(R.id.idedttelEncargado);
        edttelContacto =findViewById(R.id.idedttelContacto);
        rgbtipo=findViewById(R.id.idrgUbicaRegCDI);
        rbCentro=findViewById(R.id.idrbCentro);
        rbHogar=findViewById(R.id.idrbHogar);
        spnVeredas=findViewById(R.id.idspnVeredas);
        btnGuardar=findViewById(R.id.idbtnregCDI);
        btnActualizar=findViewById(R.id.idbtnactCDI);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.veredas,android.R.layout.simple_spinner_item);
        spnVeredas.setAdapter(adapter);
        acti=findViewById(R.id.activocdi);
        recibeParametros =getIntent().getExtras();
        opcion= recibeParametros.getString("opcion");
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
