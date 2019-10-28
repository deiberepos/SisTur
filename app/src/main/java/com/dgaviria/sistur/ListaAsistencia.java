package com.dgaviria.sistur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dgaviria.sistur.adaptadores.AdaptadorListaAsistencia;
import com.dgaviria.sistur.clases.Censo;
import com.dgaviria.sistur.clases.InfanteAsiste;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ListaAsistencia extends AppCompatActivity {
    int año, mes, dia;
    EditText campoFechaE;
    private String nombreCentro, nombreSemana;
    Spinner spnCentros, spnSemanas;
    private ArrayAdapter adaptadorCentros, adaptadorSemanas;
    RecyclerView recyclerAsistencia;
    ArrayList<String> nombresInfantes, nombresCentros, listaSemanas;
    AdaptadorListaAsistencia adaptadorAsistencia;
    DatabaseReference bdReferencia, referenciaCentros, refPoblCentros, refSemanas, refRegistAsistencia;
    Button btnAprobar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_asistencia);

        referenciar();
        mostrarFechaE();
        llenarListaCentros();
        llenarListaSemanas();
        spnCentros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nombreCentro = adapterView.getItemAtPosition(i).toString();
                lecturaNombresInfantes(nombreCentro);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnSemanas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nombreSemana=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refRegistAsistencia=bdReferencia.child("registroasistencia").child(nombreCentro).child(nombreSemana);
                for(int i = 0; i<nombresInfantes.size();i++){
                    InfanteAsiste infanteAsiste = new InfanteAsiste();
                    infanteAsiste.setNombreInfante(nombresInfantes.get(i));
                    infanteAsiste.setAsistencia(true);
                    refRegistAsistencia.child(infanteAsiste.getNombreInfante()).setValue(infanteAsiste);
                }
            }
        });

    }

    private void lecturaNombresInfantes(String centro) {
        nombresInfantes = listaNombresInfantes(centro);
        adaptadorAsistencia =new AdaptadorListaAsistencia(ListaAsistencia.this,nombresInfantes);
        recyclerAsistencia.setAdapter(adaptadorAsistencia);
        recyclerAsistencia.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        adaptadorAsistencia.notifyDataSetChanged();
    }

    private ArrayList<String> listaNombresInfantes(String nomCentro) {
        final ArrayList<String> lista=new ArrayList<>();
        refPoblCentros = bdReferencia.child("poblacionCentros").child(nomCentro);
        refPoblCentros.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Censo censo =  new Censo();
                for (DataSnapshot datos:dataSnapshot.getChildren()) {

                    if(datos.getKey().equals("totalcenso")){

                    }else {
                        Censo infante = datos.getValue(Censo.class);
                        lista.add(infante.getNombre());
                    }

                }
                adaptadorAsistencia.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return lista;
    }

    private void llenarListaSemanas() {
        refSemanas=bdReferencia.child("semanas");
        refSemanas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    for (DataSnapshot miSemana : dataSnapshot.getChildren()) {
                        listaSemanas.add(miSemana.getKey());
                    }
                    adaptadorSemanas = new ArrayAdapter<String>(ListaAsistencia.this, android.R.layout.simple_spinner_item, listaSemanas);
                    adaptadorSemanas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnSemanas.setAdapter(adaptadorSemanas);
                    //selectorSemanasE.setSelected(false);
                    spnSemanas.setSelection(0, true); //selecciona el primer elemento del spinner
                    adaptadorSemanas.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Error de lectura de semanas, contacte al administrador", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void llenarListaCentros() {
        referenciaCentros = bdReferencia.child("Centros");
        referenciaCentros.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    for (DataSnapshot miCentro : dataSnapshot.getChildren()) {
                        nombresCentros.add(miCentro.getKey());
                    }
                    adaptadorCentros = new ArrayAdapter<String>(ListaAsistencia.this, android.R.layout.simple_spinner_item, nombresCentros);
                    adaptadorCentros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnCentros.setAdapter(adaptadorCentros);
                    //selectorCDIE.setSelected(false);
                    spnCentros.setSelection(0, true); //selecciona el primer elemento del spinner
                    adaptadorCentros.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Error de lectura de CDI, contacte al administrador", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void mostrarFechaE() {
        campoFechaE.setText(dia + "/" + mes + "/" + año);
    }
    public void mostrarCalendarioE(View view) {
        Calendar miCalendario = new GregorianCalendar();//Calendar.getInstance();
        miCalendario.setTime(new Date());
        new DatePickerDialog(this, R.style.TemaCalendario, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                año = year;
                mes = monthOfYear + 1;
                dia = dayOfMonth;
                mostrarFechaE();
            }
        }, miCalendario.get(Calendar.YEAR), miCalendario.get(Calendar.MONTH), miCalendario.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void referenciar() {
        campoFechaE = findViewById(R.id.idedtFechaAsistencia);
        spnCentros = findViewById(R.id.idspncentrosasistencia);
        spnSemanas = findViewById(R.id.idspnsemanaasistencia);
        recyclerAsistencia = findViewById(R.id.idrecyclerAsistencia);
        nombresInfantes = new ArrayList<>();
        nombresCentros = new ArrayList<>();
        listaSemanas = new ArrayList<>();
        bdReferencia = FirebaseDatabase.getInstance().getReference();
        btnAprobar = findViewById(R.id.idbtnAprobarAsistencia);
    }
}
