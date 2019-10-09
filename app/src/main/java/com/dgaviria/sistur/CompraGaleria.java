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

import com.dgaviria.sistur.adaptadores.AdaptadorListaCompra;
import com.dgaviria.sistur.adaptadores.AdaptadorListaMinutas;
import com.dgaviria.sistur.clases.AlimentoCompra;
import com.dgaviria.sistur.clases.Calendario;
import com.dgaviria.sistur.clases.Minutas;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CompraGaleria extends AppCompatActivity {
    private int año, mes, dia;
    EditText campoFecha;
    AdaptadorListaCompra miAdaptadorCompra;
    Spinner spnSemanas;
    String nombreSemana;
    DatabaseReference miReferenciaSem;
    ArrayAdapter<String> adaptadorSemana;
    ArrayList<String> listaSemanas,listaMinutas;
    ArrayList<AlimentoCompra> listaGaleria;
    RecyclerView miRecyclerLista;
    Button botonGuardar;
    int numItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_compras);

        referenciar();
        mostrarFecha();
        llenarListaMinutas();
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CompraGaleria.this, "Guardando la lista de compras", Toast.LENGTH_SHORT).show();
            }
        });
        spnSemanas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int item, long l) {
                nombreSemana=adapterView.getItemAtPosition(item).toString();
                lecturaMinutas();
                listarIngredientes();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void listarIngredientes() {
        listaGaleria=lecturaIngredientes();
        miAdaptadorCompra=new AdaptadorListaCompra(this,listaGaleria);
        miRecyclerLista.setAdapter(miAdaptadorCompra);
        miRecyclerLista.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
    }


    private ArrayList<AlimentoCompra> lecturaIngredientes() {
        final ArrayList<AlimentoCompra> lista=new ArrayList<>();
        numItem=0;
        miReferenciaSem = FirebaseDatabase.getInstance().getReference("minutas");
        miReferenciaSem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    for(DataSnapshot miMinuta:dataSnapshot.getChildren()){
                        for (int item=0;item<listaMinutas.size();item++){
                            if (miMinuta.getKey().equals(listaMinutas.get(item))){
                                for(DataSnapshot miPrepara:miMinuta.getChildren()){
                                    for(DataSnapshot miAlimento:miPrepara.getChildren()){
                                        if (!miAlimento.getKey().equals("preparacion") && !miAlimento.getKey().equals("procedimiento")) {
                                            Minutas alimento=miAlimento.getValue(Minutas.class);
                                            AlimentoCompra ingrediente=new AlimentoCompra();
                                            ingrediente.setOrden(String.valueOf(++numItem).trim()+".");
                                            ingrediente.setIngrediente(alimento.getReal());
                                            ingrediente.setMedida(alimento.getUnidad());
                                            ingrediente.setCantidad(alimento.getTotal());
                                            ingrediente.setEditValorCompra("0");
                                            ingrediente.setTotal("0");
                                            lista.add(ingrediente);

                                        }
                                    }
                                }
                            }

                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error de lectura de ingredientes, contacte al administrador", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return lista;
    }

    private void referenciar() {
        campoFecha=findViewById(R.id.editFechaCompra);
        Calendar miCalendario = Calendar.getInstance();
        año = miCalendario.get(Calendar.YEAR);
        mes = miCalendario.get(Calendar.MONTH)+1;
        dia = miCalendario.get(Calendar.DAY_OF_MONTH);
        spnSemanas=findViewById(R.id.spinSemana);
        listaSemanas=new ArrayList<String>();
        listaMinutas=new ArrayList<String>();
        miRecyclerLista=findViewById(R.id.recyclerCompras);
        botonGuardar=findViewById(R.id.botonGuardaGal);
    }

    private void mostrarFecha() {
        campoFecha.setText(dia + "/" + mes + "/" + año);
    }
    public void mostrarCalendario(View view) {
        Calendar miCalendario = new GregorianCalendar();//Calendar.getInstance();
        miCalendario.setTime(new Date());
        new DatePickerDialog(this, R.style.TemaCalendario, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                año = year;
                mes = monthOfYear + 1;
                dia = dayOfMonth;
                mostrarFecha();
            }
        }, miCalendario.get(Calendar.YEAR), miCalendario.get(Calendar.MONTH), miCalendario.get(Calendar.DAY_OF_MONTH)).show();
    }
    private void llenarListaMinutas() {
        miReferenciaSem = FirebaseDatabase.getInstance().getReference("semanas");
        miReferenciaSem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    for (DataSnapshot miMinuta : dataSnapshot.getChildren()) {
                        listaSemanas.add(miMinuta.getKey());
                    }
                    adaptadorSemana = new ArrayAdapter<String>(CompraGaleria.this, android.R.layout.simple_spinner_item, listaSemanas);
                    adaptadorSemana.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnSemanas.setAdapter(adaptadorSemana);
                    spnSemanas.setSelected(false);
                    spnSemanas.setSelection(0, false); //no selecciona un elemento del spinner
                } else {
                    Toast.makeText(getApplicationContext(), "Error de lectura de semanas, contacte al administrador", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void lecturaMinutas(){
        //Lectura de las minutas que hacen parte de la semana
        miReferenciaSem = FirebaseDatabase.getInstance().getReference("semanas").child(nombreSemana);
        miReferenciaSem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    String minuta1=dataSnapshot.child("minuta1").getValue(String.class);
                    String minuta2=dataSnapshot.child("minuta2").getValue(String.class);
                    String minuta3=dataSnapshot.child("minuta3").getValue(String.class);
                    String minuta4=dataSnapshot.child("minuta4").getValue(String.class);
                    String minuta5=dataSnapshot.child("minuta5").getValue(String.class);
                    if (!minuta1.equals("X") && !minuta1.equals("V")){
                        listaMinutas.add(minuta1);
                    }
                    if (!minuta2.equals("X") && !minuta2.equals("V")){
                        listaMinutas.add(minuta2);
                    }
                    if (!minuta3.equals("X") && !minuta3.equals("V")){
                        listaMinutas.add(minuta3);
                    }
                    if (!minuta4.equals("X") && !minuta4.equals("V")){
                        listaMinutas.add(minuta4);
                    }
                    if (!minuta5.equals("X") && !minuta5.equals("V")){
                        listaMinutas.add(minuta5);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error de lectura de minutas, contacte al administrador", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
