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
import com.dgaviria.sistur.clases.AlimentoCompra;
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
    Spinner selectorSemanas;
    String nombreSemana;
    DatabaseReference miReferenciaMin,miReferenciaSem;
    ArrayAdapter<String> adaptadorSemana;
    ArrayList<String> listadoSemanas, listadoMinutas;
    ArrayList<AlimentoCompra> listaGaleria;
    RecyclerView miRecyclerListaCompra;
    Button botonGuardar;
    int numItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_compras);

        referenciar();
        mostrarFecha();
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CompraGaleria.this, "Guardando la lista de compras", Toast.LENGTH_SHORT).show();
            }
        });
        selectorSemanas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adaptadorVista, View view, int i, long l) {
                nombreSemana=adaptadorVista.getItemAtPosition(i).toString();
                lecturaMinutas();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void listarIngredientes() {
        listaGaleria=new ArrayList<AlimentoCompra>();
        listaGaleria=lecturaIngredientes();
        miAdaptadorCompra=new AdaptadorListaCompra(this,listaGaleria);
        miRecyclerListaCompra.setAdapter(miAdaptadorCompra);
        miRecyclerListaCompra.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
    }

    private ArrayList<AlimentoCompra> lecturaIngredientes() {
        final ArrayList<AlimentoCompra> lista=new ArrayList<>();
        numItem=0;
        miReferenciaMin = FirebaseDatabase.getInstance().getReference("minutas");
        miReferenciaMin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    for(DataSnapshot miMinuta:dataSnapshot.getChildren()){
                        for (int item = 0; item< listadoMinutas.size(); item++){
                            if (miMinuta.getKey().equals(listadoMinutas.get(item))){
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
                                            miAdaptadorCompra.notifyDataSetChanged();
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
        selectorSemanas=findViewById(R.id.selectorSemana);
        miRecyclerListaCompra=findViewById(R.id.recyclerCompras);
        botonGuardar=findViewById(R.id.botonGuardaGal);
        llenarListaMinutas();
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
        listadoSemanas=new ArrayList<String>();
        miReferenciaSem = FirebaseDatabase.getInstance().getReference("semanas");
        miReferenciaSem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    for (DataSnapshot miSemana : dataSnapshot.getChildren()) {
                        String textoSemana=miSemana.getKey();
                        /*String texto="("+textoSemana.substring(0,2)+"):"+miSemana.child("mesinicial").getValue(String.class)+" "+
                                miSemana.child("diainicial").getValue(String.class)+" A "+
                                miSemana.child("mesfinal").getValue(String.class)+" "+
                                miSemana.child("diafinal").getValue(String.class);*/
                        listadoSemanas.add(miSemana.getKey());
                    }
                    adaptadorSemana = new ArrayAdapter<String>(CompraGaleria.this, android.R.layout.simple_spinner_item, listadoSemanas);
                    adaptadorSemana.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    selectorSemanas.setAdapter(adaptadorSemana);
                    //selectorSemanas.setSelected(false);
                    selectorSemanas.setSelection(0, true); //selecciona el primer elemento del spinner
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
        listadoMinutas =new ArrayList<String>();
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
                        listadoMinutas.add(minuta1);
                    }
                    if (!minuta2.equals("X") && !minuta2.equals("V")){
                        listadoMinutas.add(minuta2);
                    }
                    if (!minuta3.equals("X") && !minuta3.equals("V")){
                        listadoMinutas.add(minuta3);
                    }
                    if (!minuta4.equals("X") && !minuta4.equals("V")){
                        listadoMinutas.add(minuta4);
                    }
                    if (!minuta5.equals("X") && !minuta5.equals("V")){
                        listadoMinutas.add(minuta5);
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
        listarIngredientes();
    }
}
