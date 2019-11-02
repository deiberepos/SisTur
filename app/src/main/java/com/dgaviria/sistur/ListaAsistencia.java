package com.dgaviria.sistur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.dgaviria.sistur.adaptadores.AdaptadorListaAsistencia;
import com.dgaviria.sistur.clases.Censo;
import com.dgaviria.sistur.clases.InfanteAsiste;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    int ano, mes, dia;
    TextView campoFechaAs;
    private String nombreCentro, nombreSemana, recibeRol, recibeUsuario, fechaReporte;
    Spinner spnCentros, spnSemanas;
    private ArrayAdapter adaptadorCentros, adaptadorSemanas;
    RecyclerView recyclerAsistencia;
    ArrayList<InfanteAsiste> nombresInfantes;
    private ArrayList<String> nombresCentros, listaSemanas;
    AdaptadorListaAsistencia adaptadorAsistencia;
    DatabaseReference bdReferencia, referenciaCentros, refPoblCentros, refSemanas, refRegistAsistencia;
    Calendar miCalendario;
    private Bundle bundle;
    FloatingActionButton btnAprobar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_asistencia);

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
                fechaReporte = campoFechaAs.getText().toString();
                refRegistAsistencia=bdReferencia.child("registroasistencia").child(nombreCentro).child(fechaReporte);
                for(int i = 0; i<nombresInfantes.size();i++){
                    InfanteAsiste infanteAsiste;
                    infanteAsiste = nombresInfantes.get(i);
                    refRegistAsistencia.child(infanteAsiste.getRegistroCivil()).setValue(infanteAsiste);
                }
                Toast.makeText(ListaAsistencia.this, "Lista de asistencia guardada", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getApplicationContext(),MenuPrincipal.class);
                intent.putExtra("rol",recibeRol);
                intent.putExtra("usuario",recibeUsuario);
                startActivity(intent);
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

    private ArrayList<InfanteAsiste> listaNombresInfantes(String nomCentro) {
        final ArrayList<InfanteAsiste> lista=new ArrayList<>();
        refPoblCentros = bdReferencia.child("poblacionCentros").child(nomCentro);
        refPoblCentros.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Censo censo =  new Censo();
                for (DataSnapshot datos:dataSnapshot.getChildren()) {

                    if(!datos.getKey().equals("totalcenso")){
                        Censo infante = datos.getValue(Censo.class);
                        InfanteAsiste infanteAsiste = new InfanteAsiste();
                        infanteAsiste.setAsistencia(false);
                        infanteAsiste.setNombreInfante(infante.getNombre()+" "+infante.getApellidos());
                        infanteAsiste.setRegistroCivil(infante.getRegistro());
                        //String nombreCompleto=infante.getNombre()+" "+infante.getApellidos();
                        lista.add(infanteAsiste);
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
                if (dataSnapshot.getChildren() != null) {
                    for (DataSnapshot miSemana : dataSnapshot.getChildren()) {
                        listaSemanas.add(miSemana.getKey());
                    }
                    adaptadorSemanas = new ArrayAdapter<>(ListaAsistencia.this, android.R.layout.simple_spinner_item, listaSemanas);
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
                if (dataSnapshot.getChildren() != null) {
                    for (DataSnapshot miCentro : dataSnapshot.getChildren()) {
                        nombresCentros.add(miCentro.getKey());
                    }
                    adaptadorCentros = new ArrayAdapter<>(ListaAsistencia.this, android.R.layout.simple_spinner_item, nombresCentros);
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
        String fechaAux=dia + "-" + mes + "-" + ano;
        campoFechaAs.setText(fechaAux);
    }
    public void mostrarCalendarioAs(View view) {
        Calendar miCalendario = new GregorianCalendar();//Calendar.getInstance();
        miCalendario.setTime(new Date());
        new DatePickerDialog(this, R.style.TemaCalendario, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                ano = year;
                mes = monthOfYear + 1;
                dia = dayOfMonth;
                mostrarFechaE();
            }
        }, miCalendario.get(Calendar.YEAR), miCalendario.get(Calendar.MONTH), miCalendario.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void referenciar() {
        campoFechaAs = findViewById(R.id.idedtFechaAsistencia);
        miCalendario = Calendar.getInstance();
        ano = miCalendario.get(Calendar.YEAR);
        mes = miCalendario.get(Calendar.MONTH)+1;
        dia = miCalendario.get(Calendar.DAY_OF_MONTH);
        spnCentros = findViewById(R.id.idspncentrosasistencia);
        spnSemanas = findViewById(R.id.idspnsemanaasistencia);
        recyclerAsistencia = findViewById(R.id.idrecyclerAsistencia);
        btnAprobar=findViewById(R.id.asistenciaGuardar);
        nombresInfantes = new ArrayList<>();
        nombresCentros = new ArrayList<>();
        listaSemanas = new ArrayList<>();
        bdReferencia = FirebaseDatabase.getInstance().getReference();
        bundle = getIntent().getExtras();
        recibeRol = bundle.getString("rol");
        recibeUsuario =bundle.getString("usuario");
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
