package com.dgaviria.sistur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.dgaviria.sistur.clases.Calendario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class CrearCalendario extends AppCompatActivity {
    Spinner spnCalendario, spnLunes, spnMartes, spnMiercoles, spnJueves, spnViernes;
    ArrayList<String> listaSemanas,listaMinutaLunes,listaMinutaMartes,listaMinutaMiercoles,listaMinutaJueves,listaMinutaViernes;
    DatabaseReference miReferenciaCal;
    ArrayAdapter<String> adaptadorSemana,adaptadorLunes,adaptadorMartes,adaptadorMiercoles,adaptadorJueves,adaptadorViernes;
    FloatingActionButton btnGuardarCal;
    String semanaSeleccionada="",minutaLunes="",minutaMartes="",minutaMiercoles="",minutaJueves="",minutaViernes="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_calendario);
        referenciar();
        llenarSemanas();

        spnCalendario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View vista, int item, long l) {
                semanaSeleccionada=spnCalendario.getSelectedItem().toString();
                buscarMinutas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnLunes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View vista, int item, long l) {
                minutaLunes=adapterView.getItemAtPosition(item).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnMartes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View vista, int item, long l) {
                minutaMartes=adapterView.getItemAtPosition(item).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnMiercoles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int item, long l) {
                minutaMiercoles=adapterView.getItemAtPosition(item).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnJueves.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int item, long l) {
                minutaJueves=adapterView.getItemAtPosition(item).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnViernes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int item, long l) {
                minutaViernes=adapterView.getItemAtPosition(item).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnGuardarCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizaSemana();
            }
        });
    }

    private void actualizaSemana(){
        if (minutaLunes!=null && minutaLunes.equals("") && !minutaLunes.equals("X") && !minutaLunes.equals("V")){
            miReferenciaCal= FirebaseDatabase.getInstance().getReference("semanas").child(semanaSeleccionada).child("minuta1");
            miReferenciaCal.setValue(minutaLunes);
        }
        if (minutaMartes!=null && minutaMartes.equals("") && !minutaMartes.equals("X") && !minutaMartes.equals("V")) {
            miReferenciaCal = FirebaseDatabase.getInstance().getReference("semanas").child(semanaSeleccionada).child("minuta2");
            miReferenciaCal.setValue(minutaMartes);
        }
        if (minutaMiercoles!=null && minutaMiercoles.equals("") && !minutaMiercoles.equals("X") && !minutaMiercoles.equals("V")) {
            miReferenciaCal = FirebaseDatabase.getInstance().getReference("semanas").child(semanaSeleccionada).child("minuta3");
            miReferenciaCal.setValue(minutaMiercoles);
        }
        if (minutaJueves!=null && minutaJueves.equals("")&& !minutaJueves.equals("X") && !minutaJueves.equals("V")) {
            miReferenciaCal = FirebaseDatabase.getInstance().getReference("semanas").child(semanaSeleccionada).child("minuta4");
            miReferenciaCal.setValue(minutaJueves);
        }
        if (minutaViernes!=null && minutaViernes.equals("")&& !minutaViernes.equals("X") && !minutaViernes.equals("V")) {
            miReferenciaCal = FirebaseDatabase.getInstance().getReference("semanas").child(semanaSeleccionada).child("minuta5");
            miReferenciaCal.setValue(minutaViernes);
        }
        Toast.makeText(this, "Se programó exitosamente la semana", Toast.LENGTH_SHORT).show();
    }

    private void buscarMinutas(){
        minutaLunes="X";
        minutaMartes="X";
        minutaMiercoles="X";
        minutaJueves="X";
        minutaViernes="X";
        miReferenciaCal= FirebaseDatabase.getInstance().getReference("semanas").child(semanaSeleccionada);
        miReferenciaCal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildren()!=null){
                    if (dataSnapshot.exists()){
                        Calendario miSemana=dataSnapshot.getValue(Calendario.class);
                        minutaLunes=miSemana.getMinuta1();
                        minutaMartes=miSemana.getMinuta2();
                        minutaMiercoles=miSemana.getMinuta3();
                        minutaJueves=miSemana.getMinuta4();
                        minutaViernes=miSemana.getMinuta5();
                    }
                    llenarDias();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error de lectura de minutas asociadas, contacte al administrador",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void llenarSemanas() {
        miReferenciaCal= FirebaseDatabase.getInstance().getReference("semanas");
        miReferenciaCal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildren()!=null){
                    for (DataSnapshot miSemana:dataSnapshot.getChildren()){
                        listaSemanas.add(miSemana.getKey());
                    }
                    adaptadorSemana=new ArrayAdapter<>(CrearCalendario.this,android.R.layout.simple_spinner_item,listaSemanas);

                    adaptadorSemana.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spnCalendario.setAdapter(adaptadorSemana);
                    spnCalendario.setSelected(false);
                    spnCalendario.setSelection(0, false); //no selecciona un elemento del spinner
                    //spnCalendario.setOnItemSelectedListener(null);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error de lectura de semanas, contacte al administrador",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void llenarDias(){
        miReferenciaCal= FirebaseDatabase.getInstance().getReference("minutas");
        miReferenciaCal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaMinutaLunes.clear();
                listaMinutaMartes.clear();
                listaMinutaMiercoles.clear();
                listaMinutaJueves.clear();
                listaMinutaViernes.clear();
                if (dataSnapshot.getChildren()!=null) {
                    for (DataSnapshot miDia : dataSnapshot.getChildren()) {
                        String nombreMinuta=miDia.getKey();
                        if (!minutaLunes.equals("X")) listaMinutaLunes.add(nombreMinuta);
                        if (!minutaMartes.equals("X")) listaMinutaMartes.add(nombreMinuta);
                        if (!minutaMiercoles.equals("X")) listaMinutaMiercoles.add(nombreMinuta);
                        if (!minutaJueves.equals("X")) listaMinutaJueves.add(nombreMinuta);
                        if (!minutaViernes.equals("X")) listaMinutaViernes.add(nombreMinuta);
                    }
                    adaptadorLunes = new ArrayAdapter<>(CrearCalendario.this, android.R.layout.simple_spinner_item, listaMinutaLunes);
                    adaptadorMartes = new ArrayAdapter<>(CrearCalendario.this, android.R.layout.simple_spinner_item, listaMinutaMartes);
                    adaptadorMiercoles = new ArrayAdapter<>(CrearCalendario.this, android.R.layout.simple_spinner_item, listaMinutaMiercoles);
                    adaptadorJueves = new ArrayAdapter<>(CrearCalendario.this, android.R.layout.simple_spinner_item, listaMinutaJueves);
                    adaptadorViernes = new ArrayAdapter<>(CrearCalendario.this, android.R.layout.simple_spinner_item, listaMinutaViernes);

                    adaptadorLunes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adaptadorMartes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adaptadorMiercoles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adaptadorJueves.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adaptadorViernes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spnLunes.setAdapter(adaptadorLunes);
                    spnMartes.setAdapter(adaptadorMartes);
                    spnMiercoles.setAdapter(adaptadorMiercoles);
                    spnJueves.setAdapter(adaptadorJueves);
                    spnViernes.setAdapter(adaptadorViernes);

                    //Selecciona el item de la lista del spinner solo si tiene una minuta válida guardada
                    if (!minutaLunes.equals("X") && !minutaLunes.equals("V") )
                        spnLunes.setSelection(listaMinutaLunes.indexOf(minutaLunes));
                    else
                    if (minutaLunes.equals("V")) {
                        spnLunes.setSelected(false);
                        spnLunes.setSelection(0, false); //no selecciona un elemento del spinner cuando no hay valor previo de minuta
                        //spnLunes.setOnItemSelectedListener(null);
                    }
                    if (!minutaMartes.equals("X") && !minutaMartes.equals("V"))
                        spnMartes.setSelection(listaMinutaMartes.indexOf(minutaMartes));
                    else
                    if (minutaMartes.equals("V")) {
                        spnMartes.setSelected(false);
                        spnMartes.setSelection(0, false);
                        //spnMartes.setOnItemSelectedListener(null);
                    }
                    if (!minutaMiercoles.equals("X") && !minutaMiercoles.equals("V"))
                        spnMiercoles.setSelection(listaMinutaMiercoles.indexOf(minutaMiercoles));
                    else
                    if (minutaMiercoles.equals("V")) {
                        spnMiercoles.setSelected(false);
                        spnMiercoles.setSelection(0, false);
                        //spnMiercoles.setOnItemSelectedListener(null);
                    }
                    if (!minutaJueves.equals("X") && !minutaJueves.equals("V"))
                        spnJueves.setSelection(listaMinutaJueves.indexOf(minutaJueves));
                    else
                    if (minutaJueves.equals("V")) {
                        spnJueves.setSelected(false);
                        spnJueves.setSelection(0, false);
                        //spnJueves.setOnItemSelectedListener(null);
                    }
                    if (!minutaViernes.equals("X") && !minutaViernes.equals("V"))
                        spnViernes.setSelection(listaMinutaViernes.indexOf(minutaViernes));
                    else
                    if (minutaViernes.equals("V")) {
                        spnViernes.setSelected(false);
                        spnViernes.setSelection(0, false);
                        //spnViernes.setOnItemSelectedListener(null);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error de lectura de minutas, contacte al administrador",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void referenciar() {
        spnCalendario =findViewById(R.id.spinSemana);
        spnLunes =findViewById(R.id.spinLun);
        spnMartes =findViewById(R.id.spinMar);
        spnMiercoles =findViewById(R.id.spinMie);
        spnJueves =findViewById(R.id.spinJue);
        spnViernes =findViewById(R.id.spinVie);
        btnGuardarCal =findViewById(R.id.botonGuardaCal);

        listaSemanas=new ArrayList<>();
        listaMinutaLunes=new ArrayList<>();
        listaMinutaMartes=new ArrayList<>();
        listaMinutaMiercoles=new ArrayList<>();
        listaMinutaJueves=new ArrayList<>();
        listaMinutaViernes=new ArrayList<>();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
