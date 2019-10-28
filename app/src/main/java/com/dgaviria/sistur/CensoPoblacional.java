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

import java.util.List;

public class CensoPoblacional extends AppCompatActivity {

    private int año, mes, dia;
    Button btnguardar, btnactualizar;
    EditText editRegistroInfante,editnombreInfante, editapellidoInfante, campoFecha, editobservaciones, editnombrePadre, editnombreMadre, editTeleMadre, editTelePadre, editDirPadre, editDirMadre;
    DatabaseReference miReferencia, misDatos, miReferenciaCentro, poblacionC;
    String registro, nombre, apellido, fecha, observacion, nombreMadr, nombrePadr, telM, telP, dirM, dirP, genero, centroasociado, recibecentro;
    Boolean activo;
    RadioGroup gen;
    RadioButton mas, fem;
    Bundle recibeParametros;
    CheckBox acti;
    private String opcion = "", reciberegistro,nombree, apellidoo, fechaa, observacionn, nombreMadrr, telMM, dirMM, nombrePadrr, telPP, dirPP;
    private static final int TIPO_DIALOGO = 0;
    private static DatePickerDialog.OnDateSetListener selectorFecha;
    private Spinner spin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.censo_poblacional);
        referenciar();
        if (opcion.equals("crear")) {
            llernarspinner();
            btnactualizar.setVisibility(View.INVISIBLE);
        } else {
            int actgeneroo, activoo;
            btnguardar.setVisibility(View.INVISIBLE);
            reciberegistro = recibeParametros.getString("registro");
            editRegistroInfante.setText(reciberegistro);
            nombree = recibeParametros.getString("nombre");
            editnombreInfante.setText(nombree);
            apellidoo = recibeParametros.getString("apellido");
            editapellidoInfante.setText(apellidoo);
            recibecentro = recibeParametros.getString("centroa");
            actgeneroo = recibeParametros.getInt("tipo");
            if (actgeneroo == 1) {
                mas.setChecked(true);
            }
            if (actgeneroo == 2) {
                fem.setChecked(true);
            }
            fechaa = recibeParametros.getString("fecha");
            campoFecha.setText(fechaa);
            observacionn = recibeParametros.getString("observaciones");
            editobservaciones.setText(observacionn);
            nombrePadrr = recibeParametros.getString("nombrePadr");
            editnombrePadre.setText(nombrePadrr);
            telPP = recibeParametros.getString("telefonpadre");
            editTelePadre.setText(telPP);
            dirPP = recibeParametros.getString("dirpadre");
            editDirPadre.setText(dirPP);
            nombreMadrr = recibeParametros.getString("nombreMaadre");
            editnombreMadre.setText(nombreMadrr);
            telMM = recibeParametros.getString("telefonoMadre");
            editTeleMadre.setText(telMM);
            dirMM = recibeParametros.getString("dirMadre");
            editDirMadre.setText(dirMM);
            activoo = recibeParametros.getInt("activo");
            if (activoo == 1) {
                acti.setChecked(true);
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
                centroasociado = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        acti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (acti.isChecked()) {
                    activo = true;
                } else {
                    activo = false;
                }
            }
        });
        gen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.masculino:
                        genero = "masculino";
                        break;
                    case R.id.femenino:
                        genero = "femenino";
                        break;

                }
            }
        });
        if (acti.isChecked()) {
            activo = true;
        } else {
            activo = false;
        }

        Calendar calendar = Calendar.getInstance();
        año = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);

        mostrarFecha();

        selectorFecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                año = i;
                mes = i1 + 1;
                dia = i2;
                mostrarFecha();
            }
        };
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:

                return new DatePickerDialog(this, selectorFecha, año, mes + 1, dia);
        }
        return null;
    }

    public void mostrarCalendario(View view) {
        showDialog(TIPO_DIALOGO);
    }


    private void mostrarFecha() {
        campoFecha.setText(dia + "/" + mes + "/" + año);
    }


    private void llernarspinner() {
        miReferenciaCentro = FirebaseDatabase.getInstance().getReference();
        miReferenciaCentro.child("Centros").addValueEventListener(new ValueEventListener() {
            final CdiHcb centro = new CdiHcb();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> titleList = new ArrayList<String>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    CdiHcb nombre = dataSnapshot1.getValue(CdiHcb.class);
                    titleList.add(nombre.getNombreCDI());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CensoPoblacional.this, android.R.layout.simple_spinner_item, titleList);
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
        if (editnombreInfante.getText().toString().trim().isEmpty()) {
            editnombreInfante.setError("Nombre requerido");
            editnombreInfante.requestFocus();
            return;
        } else {
            nombre = editnombreInfante.getText().toString().trim().toLowerCase();
            editnombreInfante.setText(nombre);
        }
        if (editapellidoInfante.getText().toString().trim().isEmpty()) {
            editapellidoInfante.setError("Apellido requerido");
            editapellidoInfante.requestFocus();
            return;
        } else {
            apellido = editapellidoInfante.getText().toString().trim();
            editapellidoInfante.setText(apellido);
        }
        if (campoFecha.getText().toString().trim().isEmpty()) {
            campoFecha.setError("Campo Requerido");
            campoFecha.requestFocus();
            return;
        } else {
            fecha = campoFecha.getText().toString().trim();
            campoFecha.setText(fecha);

        }
        if (editnombrePadre.getText().toString().trim().isEmpty()) {
            editnombrePadre.setError("Los nombres y apellidos son requeridos");
            editnombrePadre.requestFocus();
            return;
        } else {
            nombrePadr = editnombrePadre.getText().toString().trim();
            editnombrePadre.setText(nombrePadr);
        }
        if (editTelePadre.getText().toString().trim().isEmpty()) {
            editTelePadre.setError("Telefono Requerido");
            editTelePadre.requestFocus();
            return;
        } else {
            telP = editTelePadre.getText().toString().trim();
            editTelePadre.setText(telP);
        }
        if (editDirPadre.getText().toString().trim().isEmpty()) {
            editDirPadre.setError("Direccion Requerido");
            editDirPadre.requestFocus();
            return;
        } else {
            dirP = editDirPadre.getText().toString().trim();
            editDirPadre.setText(dirP);
        }
        if (editnombreMadre.getText().toString().trim().isEmpty()) {
            editnombreMadre.setError("Los nombres y apellidos son requeridos");
            editnombreMadre.requestFocus();
            return;
        } else {
            nombreMadr = editnombreMadre.getText().toString().trim();
            editnombreMadre.setText(nombreMadr);
        }
        if (editTeleMadre.getText().toString().trim().isEmpty()) {
            editTeleMadre.setError("Telefono Requerido");
            editTeleMadre.requestFocus();
            return;
        } else {
            telM = editTeleMadre.getText().toString().trim();
            editTeleMadre.setText(telM);
        }
        if (editDirMadre.getText().toString().trim().isEmpty()) {
            editDirMadre.setError("Dirección requerida");
            editDirMadre.requestFocus();
            return;
        } else {
            dirM = editDirMadre.getText().toString().trim();
            editDirMadre.setText(dirM);
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
        if (editnombreInfante.getText().toString().trim().isEmpty()) {
            editnombreInfante.setError("Nombre requerido");
            editnombreInfante.requestFocus();
            return;
        } else {
            nombre = editnombreInfante.getText().toString().trim().toLowerCase();
            editnombreInfante.setText(nombre);
        }
        if (editapellidoInfante.getText().toString().trim().isEmpty()) {
            editapellidoInfante.setError("Apellido requerido");
            editapellidoInfante.requestFocus();
            return;
        } else {
            apellido = editapellidoInfante.getText().toString().trim();
            editapellidoInfante.setText(apellido);
        }
        if (campoFecha.getText().toString().trim().isEmpty()) {
            campoFecha.setError("Campo Requerido");
            campoFecha.requestFocus();
            return;
        } else {
            fecha = campoFecha.getText().toString().trim();
            campoFecha.setText(fecha);

        }
        if (editnombrePadre.getText().toString().trim().isEmpty()) {
            editnombrePadre.setError("Los nombres y apellidos son requeridos");
            editnombrePadre.requestFocus();
            return;
        } else {
            nombrePadr = editnombrePadre.getText().toString().trim();
            editnombrePadre.setText(nombrePadr);
        }
        if (editTelePadre.getText().toString().trim().isEmpty()) {
            editTelePadre.setError("Telefono Requerido");
            editTelePadre.requestFocus();
            return;
        } else {
            telP = editTelePadre.getText().toString().trim();
            editTelePadre.setText(telP);
        }
        if (editDirPadre.getText().toString().trim().isEmpty()) {
            editDirPadre.setError("Direccion Requerido");
            editDirPadre.requestFocus();
            return;
        } else {
            dirP = editDirPadre.getText().toString().trim();
            editDirPadre.setText(dirP);
        }
        if (editnombreMadre.getText().toString().trim().isEmpty()) {
            editnombreMadre.setError("Los nombres y apellidos son requeridos");
            editnombreMadre.requestFocus();
            return;
        } else {
            nombreMadr = editnombreMadre.getText().toString().trim();
            editnombreMadre.setText(nombreMadr);
        }
        if (editTeleMadre.getText().toString().trim().isEmpty()) {
            editTeleMadre.setError("Teléfono requerido");
            editTeleMadre.requestFocus();
            return;
        } else {
            telM = editTeleMadre.getText().toString().trim();
            editTeleMadre.setText(telM);
        }
        if (editDirMadre.getText().toString().trim().isEmpty()) {
            editDirMadre.setError("Dirección requerida");
            editDirMadre.requestFocus();
            return;
        } else {
            dirM = editDirMadre.getText().toString().trim();
            editDirMadre.setText(dirM);
        }
        actualizarCenso();
    }


    private void guardarCenso() {
        miReferencia = FirebaseDatabase.getInstance().getReference();
        misDatos = miReferencia.child("censoinfante");
        poblacionC = miReferencia.child("poblacionCentros");
        misDatos.child(registro).setValue(new Censo(registro,nombre, apellido, genero, fecha, observacion, centroasociado, nombrePadr, telP, dirP, nombreMadr, telM, dirM, activo));

        if(activo){
            poblacionC.child(centroasociado).child(registro).setValue(new Censo(registro,nombre, apellido, genero, fecha, observacion, centroasociado, nombrePadr, telP, dirP, nombreMadr, telM, dirM, activo));
            poblacionC.child(centroasociado).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    long totalchild = dataSnapshot.getChildrenCount();
                    totalchild = totalchild-1;
                    poblacionC.child(centroasociado).child("totalcenso").setValue(totalchild);
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
        builder.setMessage("Está seguro que desea actualizar este Infante?");
        builder.setPositiveButton("ACTUALIZAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                miReferencia = FirebaseDatabase.getInstance().getReference();
                poblacionC = miReferencia.child("poblacionCentros");
                final Censo censo = new Censo();
                if (reciberegistro.equals(registro)&& recibecentro.equals(centroasociado)) {
                    miReferencia.child("censoinfante").child(registro).setValue(new Censo(registro,nombre, apellido, genero, fecha, observacion, centroasociado, nombrePadr, telP, dirP, nombreMadr, telM, dirM, activo));
                    if(activo){
                        poblacionC.child(centroasociado).child(registro).setValue(new Censo(registro,nombre, apellido, genero, fecha, observacion, centroasociado, nombrePadr, telP, dirP, nombreMadr, telM, dirM, activo));
                    }else {
                        poblacionC.child(centroasociado).child(registro).removeValue();
                    }
                    poblacionC.child(centroasociado).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long totalchild = dataSnapshot.getChildrenCount();
                            totalchild = totalchild-1;
                            poblacionC.child(centroasociado).child("totalcenso").setValue(totalchild);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else if(reciberegistro.equals(registro)&& !(recibecentro.equals(centroasociado))){
                    miReferencia.child("censoinfante").child(registro).setValue(new Censo(registro,nombre, apellido, genero, fecha, observacion, centroasociado, nombrePadr, telP, dirP, nombreMadr, telM, dirM, activo));
                    poblacionC.child(recibecentro).child(reciberegistro).removeValue();
                    if(activo){
                        poblacionC.child(centroasociado).child(registro).setValue(new Censo(registro,nombre, apellido, genero, fecha, observacion, centroasociado, nombrePadr, telP, dirP, nombreMadr, telM, dirM, activo));
                    }
                    poblacionC.child(recibecentro).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long totalchild = dataSnapshot.getChildrenCount();
                            totalchild = totalchild-1;
                            poblacionC.child(recibecentro).child("totalcenso").setValue(totalchild);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    poblacionC.child(centroasociado).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long totalchild = dataSnapshot.getChildrenCount();
                            totalchild = totalchild-1;
                            poblacionC.child(centroasociado).child("totalcenso").setValue(totalchild);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else if(!(reciberegistro.equals(registro))){
                    miReferencia.child("censoinfante").child(reciberegistro).removeValue();
                    poblacionC.child(recibecentro).child(reciberegistro).removeValue();
                    miReferencia.child("censoinfante").child(registro).setValue(new Censo(registro,nombre, apellido, genero, fecha, observacion, centroasociado, nombrePadr, telP, dirP, nombreMadr, telM, dirM, activo));
                    if(activo){
                        poblacionC.child(centroasociado).child(registro).setValue(new Censo(registro,nombre, apellido, genero, fecha, observacion, centroasociado, nombrePadr, telP, dirP, nombreMadr, telM, dirM, activo));
                    }
                    poblacionC.child(centroasociado).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long totalchild = dataSnapshot.getChildrenCount();
                            totalchild = totalchild-1;
                            poblacionC.child(centroasociado).child("totalcenso").setValue(totalchild);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    poblacionC.child(recibecentro).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long totalchild = dataSnapshot.getChildrenCount();
                            totalchild = totalchild-1;
                            poblacionC.child(recibecentro).child("totalcenso").setValue(totalchild);
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

    public void referenciar() {
        btnguardar = findViewById(R.id.btnguardarcenso);
        btnactualizar = findViewById(R.id.btnactualizar);
        editRegistroInfante=findViewById(R.id.registroinfante);
        editnombreInfante = findViewById(R.id.nombreinfante);
        editapellidoInfante = findViewById(R.id.apellidoinfante);
        fem = findViewById(R.id.femenino);
        mas = findViewById(R.id.masculino);
        campoFecha = findViewById(R.id.fecha);
        editobservaciones = findViewById(R.id.editobsrvaciones);
        spin = findViewById(R.id.spiner);
        editnombrePadre = findViewById(R.id.nom_ape_padre);
        editnombreMadre = findViewById(R.id.nom_ape_madre);
        editTeleMadre = findViewById(R.id.telefonomadre);
        editTelePadre = findViewById(R.id.telefonopadre);
        editDirMadre = findViewById(R.id.direccionmadre);
        editDirPadre = findViewById(R.id.direccionpadre);
        gen = findViewById(R.id.radiogenero);
        acti = findViewById(R.id.chekactivo);
        recibeParametros = getIntent().getExtras();
        opcion = recibeParametros.getString("opcion");
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
