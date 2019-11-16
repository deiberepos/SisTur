package com.dgaviria.sistur;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.dgaviria.sistur.clases.CdiHcb;
import com.dgaviria.sistur.clases.Censo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CensoPoblacional extends AppCompatActivity {

    int ano, mes, dia;
    Button btnguardar, btnactualizar;
    EditText editRegistroInfante, editNombreInfante, editApellidoInfante, campoFecha, editObservaciones, editNombrePadre, editNombreMadre, editTeleMadre, editTelePadre, editDirPadre, editDirMadre;
    DatabaseReference miReferencia, misDatos, miReferenciaCentro, poblacionC;
    String registro, nombre, apellido, fecha, observacion, nombreDeMadre, nombreDePadre, telMadre, telPadre, dirMadre, dirPadre, genero, centroAsociado, recibeCentro;
    Boolean activoCenso;
    RadioGroup generoInfante;
    RadioButton masculino, femenino;
    Bundle recibeParametros;
    CheckBox estaActivo;
    private String opcion = "", recibeRegistro, nombresAux, apellidoAux, fechaAux, observacionAux, nombreMadreAux, telefonoMadreAux, direccionMadreAux;
    private String nombrePadreAux, telefonoPadreAux, direccionPadreAux,generoCenso;
    private static final int TIPO_DIALOGO = 0;
    Calendar miCalendarioC;
    private Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.censo_poblacional);
        referenciar();
        mostrarFecha();
        if (opcion.equals("crear")) {
            llernarspinner();
            btnactualizar.setVisibility(View.INVISIBLE);
        }
        else {
            int estaActivo;
            btnguardar.setVisibility(View.INVISIBLE);
            recibeRegistro = recibeParametros.getString("registro");
            editRegistroInfante.setText(recibeRegistro);
            nombresAux = recibeParametros.getString("nombre");
            editNombreInfante.setText(nombresAux);
            apellidoAux = recibeParametros.getString("apellido");
            editApellidoInfante.setText(apellidoAux);
            recibeCentro = recibeParametros.getString("centroa");
            generoCenso = recibeParametros.getString("genero");
            if (generoCenso.equals("M")) {
                masculino.setChecked(true);
                femenino.setChecked(false);
            }
            if (generoCenso.equals("F")) {
                femenino.setChecked(true);
                masculino.setChecked(false);
            }
            fechaAux = recibeParametros.getString("fecha");
            campoFecha.setText(fechaAux);
            observacionAux = recibeParametros.getString("observaciones");
            editObservaciones.setText(observacionAux);
            nombrePadreAux = recibeParametros.getString("nombrepadre");
            editNombrePadre.setText(nombrePadreAux);
            telefonoPadreAux = recibeParametros.getString("telefonopadre");
            editTelePadre.setText(telefonoPadreAux);
            direccionPadreAux = recibeParametros.getString("dirpadre");
            editDirPadre.setText(direccionPadreAux);
            nombreMadreAux = recibeParametros.getString("nombremadre");
            editNombreMadre.setText(nombreMadreAux);
            telefonoMadreAux = recibeParametros.getString("telefonomadre");
            editTeleMadre.setText(telefonoMadreAux);
            direccionMadreAux = recibeParametros.getString("dirmadre");
            editDirMadre.setText(direccionMadreAux);
            estaActivo = recibeParametros.getInt("activo");
            if (estaActivo == 1) {
                this.estaActivo.setChecked(true);
            }
            llernarspinner();
        }
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarDatos();
                //guardarCenso();
            }
        });
        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarDatosDos();
                //actualizarCenso();
            }
        });
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                centroAsociado = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        estaActivo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (estaActivo.isChecked())
                    activoCenso = true;
                else
                    activoCenso = false;
            }
        });
        generoInfante.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.masculino:
                        genero = "M";
                        break;
                    case R.id.femenino:
                        genero = "F";
                        break;
                }
            }
        });
        if (estaActivo.isChecked())
            activoCenso = true;
        else
            activoCenso = false;
    }

    public void mostrarCalendario(View view) {
        Calendar miCalendario=new GregorianCalendar();
        miCalendario.setTime(new Date());
        new DatePickerDialog(this, R.style.TemaCalendario, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                ano=year;
                mes=month+1;
                dia=dayOfMonth;
                mostrarFecha();
            }
        }, miCalendario.get(Calendar.YEAR),miCalendario.get(Calendar.MONTH),miCalendario.get(Calendar.DAY_OF_MONTH)).show();
    }


    private void mostrarFecha() {
        String fechaAux=dia + "/" + mes + "/" + ano;
        campoFecha.setText(fechaAux);
    }

    public void referenciar() {
        btnguardar = findViewById(R.id.btnguardarcenso);
        btnactualizar = findViewById(R.id.btnactualizar);
        editRegistroInfante=findViewById(R.id.registroinfante);
        editNombreInfante = findViewById(R.id.nombreinfante);
        editApellidoInfante = findViewById(R.id.apellidoinfante);
        femenino = findViewById(R.id.femenino);
        masculino = findViewById(R.id.masculino);
        campoFecha = findViewById(R.id.fecha);
        miCalendarioC=Calendar.getInstance();
        ano = miCalendarioC.get(Calendar.YEAR);
        mes = miCalendarioC.get(Calendar.MONTH)+1;
        dia = miCalendarioC.get(Calendar.DAY_OF_MONTH);
        editObservaciones = findViewById(R.id.editobsrvaciones);
        spin = findViewById(R.id.spiner);
        editNombrePadre = findViewById(R.id.nom_ape_padre);
        editNombreMadre = findViewById(R.id.nom_ape_madre);
        editTeleMadre = findViewById(R.id.telefonomadre);
        editTelePadre = findViewById(R.id.telefonopadre);
        editDirMadre = findViewById(R.id.direccionmadre);
        editDirPadre = findViewById(R.id.direccionpadre);
        generoInfante = findViewById(R.id.radiogenero);
        estaActivo = findViewById(R.id.chekactivo);
        recibeParametros = getIntent().getExtras();
        opcion = recibeParametros.getString("opcion");
    }
    private void llernarspinner() {
        miReferenciaCentro = FirebaseDatabase.getInstance().getReference();
        miReferenciaCentro.child("Centros").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> titleList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    CdiHcb nombre = dataSnapshot1.getValue(CdiHcb.class);
                    titleList.add(nombre.getNombreCDI());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CensoPoblacional.this, android.R.layout.simple_spinner_item, titleList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void cargarDatos() {
        if (editRegistroInfante.getText().toString().trim().isEmpty()) {
            editRegistroInfante.setError("Registro requerido");
            editRegistroInfante.requestFocus();
            return;
        } else {
            registro = editRegistroInfante.getText().toString().trim().toLowerCase();
            editRegistroInfante.setText(registro);
        }
        if (editNombreInfante.getText().toString().trim().isEmpty()) {
            editNombreInfante.setError("Nombre requerido");
            editNombreInfante.requestFocus();
            return;
        } else {
            nombre = editNombreInfante.getText().toString().trim().toLowerCase();
            editNombreInfante.setText(nombre);
        }
        if (editApellidoInfante.getText().toString().trim().isEmpty()) {
            editApellidoInfante.setError("Apellido requerido");
            editApellidoInfante.requestFocus();
            return;
        } else {
            apellido = editApellidoInfante.getText().toString().trim();
            editApellidoInfante.setText(apellido);
        }
        if (campoFecha.getText().toString().trim().isEmpty()) {
            campoFecha.setError("Campo Requerido");
            campoFecha.requestFocus();
            return;
        } else {
            fecha = campoFecha.getText().toString().trim();
            campoFecha.setText(fecha);

        }
        if (editNombrePadre.getText().toString().trim().isEmpty()) {
            editNombrePadre.setError("Los nombres y apellidos son requeridos");
            editNombrePadre.requestFocus();
            return;
        } else {
            nombreDePadre = editNombrePadre.getText().toString().trim();
            editNombrePadre.setText(nombreDePadre);
        }
        if (editTelePadre.getText().toString().trim().isEmpty()) {
            editTelePadre.setError("Teléfono requerido");
            editTelePadre.requestFocus();
            return;
        } else {
            telPadre = editTelePadre.getText().toString().trim();
            editTelePadre.setText(telPadre);
        }
        if (editDirPadre.getText().toString().trim().isEmpty()) {
            editDirPadre.setError("Direccién requerido");
            editDirPadre.requestFocus();
            return;
        } else {
            dirPadre = editDirPadre.getText().toString().trim();
            editDirPadre.setText(dirPadre);
        }
        if (editNombreMadre.getText().toString().trim().isEmpty()) {
            editNombreMadre.setError("Los nombres y apellidos son requeridos");
            editNombreMadre.requestFocus();
            return;
        } else {
            nombreDeMadre = editNombreMadre.getText().toString().trim();
            editNombreMadre.setText(nombreDeMadre);
        }
        if (editTeleMadre.getText().toString().trim().isEmpty()) {
            editTeleMadre.setError("Teléfono requerido");
            editTeleMadre.requestFocus();
            return;
        } else {
            telMadre = editTeleMadre.getText().toString().trim();
            editTeleMadre.setText(telMadre);
        }
        if (editDirMadre.getText().toString().trim().isEmpty()) {
            editDirMadre.setError("Dirección requerida");
            editDirMadre.requestFocus();
            return;
        } else {
            dirMadre = editDirMadre.getText().toString().trim();
            editDirMadre.setText(dirMadre);
        }
        guardarCenso();
    }
    public void cargarDatosDos() {
        if (editRegistroInfante.getText().toString().trim().isEmpty()) {
            editRegistroInfante.setError("Registro requerido");
            editRegistroInfante.requestFocus();
            return;
        } else {
            registro = editRegistroInfante.getText().toString().trim().toLowerCase();
            editRegistroInfante.setText(registro);
        }
        if (editNombreInfante.getText().toString().trim().isEmpty()) {
            editNombreInfante.setError("Nombres requeridos");
            editNombreInfante.requestFocus();
            return;
        } else {
            nombre = editNombreInfante.getText().toString().trim().toLowerCase();
            editNombreInfante.setText(nombre);
        }
        if (editApellidoInfante.getText().toString().trim().isEmpty()) {
            editApellidoInfante.setError("Apellidos requeridos");
            editApellidoInfante.requestFocus();
            return;
        } else {
            apellido = editApellidoInfante.getText().toString().trim();
            editApellidoInfante.setText(apellido);
        }
        if (campoFecha.getText().toString().trim().isEmpty()) {
            campoFecha.setError("Campo Requerido");
            campoFecha.requestFocus();
            return;
        } else {
            fecha = campoFecha.getText().toString().trim();
            campoFecha.setText(fecha);

        }
        if (editNombrePadre.getText().toString().trim().isEmpty()) {
            editNombrePadre.setError("Los nombres y apellidos son requeridos");
            editNombrePadre.requestFocus();
            return;
        } else {
            nombreDePadre = editNombrePadre.getText().toString().trim();
            editNombrePadre.setText(nombreDePadre);
        }
        if (editTelePadre.getText().toString().trim().isEmpty()) {
            editTelePadre.setError("Teléfono requerido");
            editTelePadre.requestFocus();
            return;
        } else {
            telPadre = editTelePadre.getText().toString().trim();
            editTelePadre.setText(telPadre);
        }
        if (editDirPadre.getText().toString().trim().isEmpty()) {
            editDirPadre.setError("Dirección requerida");
            editDirPadre.requestFocus();
            return;
        } else {
            dirPadre = editDirPadre.getText().toString().trim();
            editDirPadre.setText(dirPadre);
        }
        if (editNombreMadre.getText().toString().trim().isEmpty()) {
            editNombreMadre.setError("Los nombres y apellidos son requeridos");
            editNombreMadre.requestFocus();
            return;
        } else {
            nombreDeMadre = editNombreMadre.getText().toString().trim();
            editNombreMadre.setText(nombreDeMadre);
        }
        if (editTeleMadre.getText().toString().trim().isEmpty()) {
            editTeleMadre.setError("Teléfono requerido");
            editTeleMadre.requestFocus();
            return;
        } else {
            telMadre = editTeleMadre.getText().toString().trim();
            editTeleMadre.setText(telMadre);
        }
        if (editDirMadre.getText().toString().trim().isEmpty()) {
            editDirMadre.setError("Dirección requerida");
            editDirMadre.requestFocus();
            return;
        } else {
            dirMadre = editDirMadre.getText().toString().trim();
            editDirMadre.setText(dirMadre);
        }
        actualizarCenso();
    }


    private void guardarCenso() {
        miReferencia = FirebaseDatabase.getInstance().getReference();
        misDatos = miReferencia.child("censoinfante");
        poblacionC = miReferencia.child("poblacionCentros");
        misDatos.child(registro).setValue(new Censo(registro,nombre, apellido, genero, fecha, observacion, centroAsociado, nombreDePadre, telPadre, dirPadre, nombreDeMadre, telMadre, dirMadre, activoCenso));

        if(activoCenso){
            poblacionC.child(centroAsociado).child(registro).setValue(new Censo(registro,nombre, apellido, genero, fecha, observacion, centroAsociado, nombreDePadre, telPadre, dirPadre, nombreDeMadre, telMadre, dirMadre, activoCenso));
            poblacionC.child(centroAsociado).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long totalchild = dataSnapshot.getChildrenCount();
                    totalchild = totalchild-1;
                    poblacionC.child(centroAsociado).child("totalcenso").setValue(totalchild);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        Toast.makeText(getApplicationContext(), "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), ListarCensoPoblacion.class);
        startActivity(intent);
    }

    private void actualizarCenso() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CensoPoblacional.this);
        builder.setTitle("Desea actualizar: " + registro);
        builder.setMessage("¿Está seguro que desea actualizar este Infante?");
        builder.setPositiveButton("ACTUALIZAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                miReferencia = FirebaseDatabase.getInstance().getReference();
                poblacionC = miReferencia.child("poblacionCentros");
                final Censo censo = new Censo();
                if (recibeRegistro.equals(registro)&& recibeCentro.equals(centroAsociado)) {
                    miReferencia.child("censoinfante").child(registro).setValue(new Censo(registro,nombre, apellido, genero, fecha, observacion, centroAsociado, nombreDePadre, telPadre, dirPadre, nombreDeMadre, telMadre, dirMadre, activoCenso));
                    if(activoCenso){
                        poblacionC.child(centroAsociado).child(registro).setValue(new Censo(registro,nombre, apellido, genero, fecha, observacion, centroAsociado, nombreDePadre, telPadre, dirPadre, nombreDeMadre, telMadre, dirMadre, activoCenso));
                    }else {
                        poblacionC.child(centroAsociado).child(registro).removeValue();
                    }
                    poblacionC.child(centroAsociado).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long totalchild = dataSnapshot.getChildrenCount();
                            totalchild = totalchild-1;
                            poblacionC.child(centroAsociado).child("totalcenso").setValue(totalchild);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else if(recibeRegistro.equals(registro)&& !(recibeCentro.equals(centroAsociado))){
                    miReferencia.child("censoinfante").child(registro).setValue(new Censo(registro,nombre, apellido, genero, fecha, observacion, centroAsociado, nombreDePadre, telPadre, dirPadre, nombreDeMadre, telMadre, dirMadre, activoCenso));
                    poblacionC.child(recibeCentro).child(recibeRegistro).removeValue();
                    if(activoCenso){
                        poblacionC.child(centroAsociado).child(registro).setValue(new Censo(registro,nombre, apellido, genero, fecha, observacion, centroAsociado, nombreDePadre, telPadre, dirPadre, nombreDeMadre, telMadre, dirMadre, activoCenso));
                    }
                    poblacionC.child(recibeCentro).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long totalchild = dataSnapshot.getChildrenCount();
                            totalchild = totalchild-1;
                            poblacionC.child(recibeCentro).child("totalcenso").setValue(totalchild);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    poblacionC.child(centroAsociado).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long totalchild = dataSnapshot.getChildrenCount();
                            totalchild = totalchild-1;
                            poblacionC.child(centroAsociado).child("totalcenso").setValue(totalchild);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else if(!(recibeRegistro.equals(registro))){
                    miReferencia.child("censoinfante").child(recibeRegistro).removeValue();
                    poblacionC.child(recibeCentro).child(recibeRegistro).removeValue();
                    miReferencia.child("censoinfante").child(registro).setValue(new Censo(registro,nombre, apellido, genero, fecha, observacion, centroAsociado, nombreDePadre, telPadre, dirPadre, nombreDeMadre, telMadre, dirMadre, activoCenso));
                    if(activoCenso){
                        poblacionC.child(centroAsociado).child(registro).setValue(new Censo(registro,nombre, apellido, genero, fecha, observacion, centroAsociado, nombreDePadre, telPadre, dirPadre, nombreDeMadre, telMadre, dirMadre, activoCenso));
                    }
                    poblacionC.child(centroAsociado).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long totalchild = dataSnapshot.getChildrenCount();
                            totalchild = totalchild-1;
                            poblacionC.child(centroAsociado).child("totalcenso").setValue(totalchild);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    poblacionC.child(recibeCentro).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long totalchild = dataSnapshot.getChildrenCount();
                            totalchild = totalchild-1;
                            poblacionC.child(recibeCentro).child("totalcenso").setValue(totalchild);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                Toast.makeText(CensoPoblacional.this, "Actualizado con éxito", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CensoPoblacional.this, ListarCensoPoblacion.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "No se realizó ninguna actualización", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }



    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
