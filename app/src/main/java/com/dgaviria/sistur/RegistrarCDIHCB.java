package com.dgaviria.sistur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dgaviria.sistur.clases.CdiHcb;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarCDIHCB extends AppCompatActivity {
    private AnimationDrawable animacion;
    private ScrollView contenedorScroll;
    private EditText edtnombreCentro, edtnombreEncargado, edtnomContacto, edtdirEncargado, edtdirContacto, edttelEncargado, edttelContacto;
    private Button btnGuardar;
    ListView listaveredas;
    private Spinner spnVeredas;
    DatabaseReference miReferencia,misDatos;

    ProgressBar barraProgreso;
    String nombrecentro, nombreE, nombreC, direccionE, direccionC, telE, telC;
    public static String vereda, tipo;
    RadioGroup rgbtipo, rgbvereda;
    //public static int tipo=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cdihcb);
        referenciar();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarDatos();
                //guardarCentro();
            }
        });
        rgbtipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.idrbCentro:
                        tipo="Centro de Desarrollo Infantil";
                        break;
                    case R.id.rbHogar:
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
    }

    private void cargarDatos(){
            Boolean finalizar=false;
//            barraProgreso.setVisibility(View.VISIBLE);
        if(edtnombreCentro.getText().toString().isEmpty()||edtdirEncargado.getText().toString().isEmpty()||
        edtnombreEncargado.getText().toString().isEmpty()||edttelEncargado.getText().toString().isEmpty()||
        edtnomContacto.getText().toString().isEmpty()||edtdirContacto.getText().toString().isEmpty()||edttelContacto.getText().toString().isEmpty()){
            Toast.makeText(this,"Todos los campos son requeridos",Toast.LENGTH_SHORT).show();
        }else {
            miReferencia = FirebaseDatabase.getInstance().getReference("CDI&HCB");
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
        misDatos.child(nombrecentro).setValue(new CdiHcb(nombrecentro,nombreE,nombreC,vereda,direccionE,direccionC,telE,telC,tipo));
        Toast.makeText(getApplicationContext(),"Datos guardados correctamente",Toast.LENGTH_SHORT).show();
        /*Usar para actualizar
        Map<String,Usuarios> usuariosMap=new HashMap<>();
        usuariosMap.put(usuarioU,new Usuarios(
                usuarioU,contrasenaU,nombresU,correoE,false,rolUsuario==1,rolUsuario==2,rolUsuario==3,rolUsuario==4));
        misDatos.setValue(usuariosMap);*/
        Intent intent = new Intent(getApplicationContext(), GestionarCDIHCB.class);
        startActivity(intent);

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
        spnVeredas=findViewById(R.id.idspnVeredas);
        //rgbvereda=findViewById(R.id.idrgveredaCDI);
       // listaveredas=findViewById(R.id.idlisVeredas);
        btnGuardar=findViewById(R.id.idbtnregCDI);
        contenedorScroll=findViewById(R.id.idcontenerRegCDI);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.veredas,android.R.layout.simple_spinner_item);
        spnVeredas.setAdapter(adapter);
        //listaveredas.setAdapter(adapter);


    }
}
