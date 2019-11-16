package com.dgaviria.sistur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.dgaviria.sistur.adaptadores.AdaptadorListaCenso;
import com.dgaviria.sistur.clases.Censo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ListarCensoPoblacion extends AppCompatActivity {

    RecyclerView miRecyclerCenso;
    List<Censo> listaDeCenso;
    AdaptadorListaCenso adaptadorCenso;
    FloatingActionButton btnCrearInfante;
    Toolbar barraOpciones;
    LinearLayout bucarPor;
    DatabaseReference miReferenciaBuscar;
    ArrayList<String> nombresCentros;
    ArrayAdapter adaptadorCentros;
    Spinner listadoCentros;
    String nombreCentroBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_censo_poblacion);
        referenciar();
        llenarRecyclerCenso("2");
        btnCrearInfante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent miIntento = new Intent(ListarCensoPoblacion.this, CensoPoblacional.class);
                miIntento.putExtra("opcion","crear");
                startActivity(miIntento);
            }
        });

        listadoCentros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nombreCentroBuscar =adapterView.getItemAtPosition(i).toString();
                llenarRecyclerCenso("1");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adaptadorCenso=new AdaptadorListaCenso(this,listaDeCenso, new AdaptadorListaCenso.OnItemClick() {
            @Override
            public void itemClick(Censo miCenso, int posicion) {
            }

            @Override
            public void modificaClick(Censo miCenso, int posicion) {
                int tipoact=1;
                if (miCenso.getActivo())
                    tipoact=2;
                Intent intent = new Intent(getApplicationContext(), CensoPoblacional.class);
                intent.putExtra("opcion","actualizar");
                intent.putExtra("registro",miCenso.getRegistro());
                intent.putExtra("nombre",miCenso.getNombre());
                intent.putExtra("apellido",miCenso.getApellidos());
                intent.putExtra("genero",miCenso.getGenero());
                intent.putExtra("fecha",miCenso.getfecha());
                intent.putExtra("observaciones",miCenso.getObservaciones());
                intent.putExtra("nombrepadre",miCenso.getNombrepadre());
                intent.putExtra("centroa",miCenso.getCentroasociado());
                intent.putExtra("telefonopadre",miCenso.getTelfonopadre());
                intent.putExtra("dirpadre",miCenso.getDirpadre());
                intent.putExtra("nombremadre",miCenso.getNombremadre());
                intent.putExtra("telefonomadre",miCenso.getTelfonomadre());
                intent.putExtra("dirmadre",miCenso.getDirmadre());
                intent.putExtra("activo",tipoact);
                startActivity(intent);
            }
        });
        miRecyclerCenso.setAdapter(adaptadorCenso);
    }
    private void referenciar(){
        miRecyclerCenso=findViewById(R.id.listacenso);
        listaDeCenso=new ArrayList<>();
        btnCrearInfante =findViewById(R.id.btncrear);
        barraOpciones=findViewById(R.id.tb_opciones);
        bucarPor=findViewById(R.id.lay_buscarPor);
        bucarPor.setVisibility(View.GONE);
        listadoCentros=findViewById(R.id.listaCentroBuscar);
        setSupportActionBar(barraOpciones);
        nombresCentros=new ArrayList<>();
    }

    private void llenarListaCentrosBuscar() {
        nombresCentros.clear();
        miReferenciaBuscar = FirebaseDatabase.getInstance().getReference("Centros");
        miReferenciaBuscar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildren() != null) {
                    for (DataSnapshot miCentro : dataSnapshot.getChildren()) {
                        nombresCentros.add(miCentro.getKey());
                    }
                    adaptadorCentros = new ArrayAdapter<>(ListarCensoPoblacion.this, android.R.layout.simple_spinner_item, nombresCentros);
                    adaptadorCentros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    listadoCentros.setAdapter(adaptadorCentros);
                    //selectorCDIE.setSelected(false);
                    listadoCentros.setSelection(0, true); //selecciona el primer elemento del spinner
                    adaptadorCentros.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Error de lectura de Centros, contacte al administrador", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void llenarRecyclerCenso(String tipo){
        miRecyclerCenso.setLayoutManager(new LinearLayoutManager(this));
        listaDeCenso.clear();
        switch (tipo){
            case "1":
                if (nombreCentroBuscar !=null) {
                    miReferenciaBuscar=FirebaseDatabase.getInstance().getReference("poblacionCentros").child(nombreCentroBuscar);
                    miReferenciaBuscar.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot censoExistente : dataSnapshot.getChildren()) {
                                    if (!censoExistente.getKey().equals("totalcenso")) {
                                        Censo micenso = censoExistente.getValue(Censo.class);
                                        if (micenso.getActivo())
                                            listaDeCenso.add(micenso);
                                    }
                                }
                            }
                            adaptadorCenso.notifyDataSetChanged();
                            if (listaDeCenso.size()==0)
                                Toast.makeText(ListarCensoPoblacion.this, "No hay infantes asociados", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(ListarCensoPoblacion.this, "Hay "+String.valueOf(listaDeCenso.size())+" infantes asociados", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(ListarCensoPoblacion.this, "Error en la lectura de los datos", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                break;
            case "2":
                miReferenciaBuscar=FirebaseDatabase.getInstance().getReference("censoinfante");
                miReferenciaBuscar.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot censoExistente : dataSnapshot.getChildren())                        {
                            Censo micenso=censoExistente.getValue(Censo.class);
                            if (micenso.getActivo())
                                listaDeCenso.add(micenso);
                        }
                        adaptadorCenso.notifyDataSetChanged();
                        if (listaDeCenso.size()==0)
                            Toast.makeText(ListarCensoPoblacion.this, "No hay infantes asociados", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(ListarCensoPoblacion.this, "Hay "+String.valueOf(listaDeCenso.size())+" infantes asociados", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(ListarCensoPoblacion.this, "Error en la lectura de los datos", Toast.LENGTH_SHORT).show();

                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.itemslistarpor,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch(id){
            case R.id.itemUno:
                llenarListaCentrosBuscar();
                bucarPor.setVisibility(View.VISIBLE);
                return true;
            case R.id.itemDos:
                bucarPor.setVisibility(View.GONE);
                llenarRecyclerCenso("2");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}


