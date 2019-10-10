package com.dgaviria.sistur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.dgaviria.sistur.adaptadores.AdaptadorListaCenso;
import com.dgaviria.sistur.clases.Censo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListarCensoPoblacion extends AppCompatActivity {

    RecyclerView miRecyclerCenso;
    Spinner spin;
    List<Censo> listaDeCenso;
    AdaptadorListaCenso adaptadorCenso;
    DatabaseReference miReferencia;
    FloatingActionButton btncrearInfante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_censo_poblacion);
        miReferencia = FirebaseDatabase.getInstance().getReference();
        referenciar();
        llenarRecyclerCenso();

        btncrearInfante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mi = new Intent(ListarCensoPoblacion.this, CensoPoblacional.class);
                mi.putExtra("opcion","crear");
                startActivity(mi);
            }
        });


        adaptadorCenso=new AdaptadorListaCenso(this,listaDeCenso, new AdaptadorListaCenso.OnItemClick() {
            @Override
            public void itemClick(Censo miCenso, int posicion) {
            }

            @Override
            public void modificaClick(Censo miCenso, int posicion) {
                int tipo=1;
                if(miCenso.getGenero().equals("femenino"))
                {
                    tipo=2;
                }
                int tipoact=1;
                if (miCenso.getActivo().equals("true"))
                {
                    tipoact=2;
                }
                Intent intent = new Intent(getApplicationContext(), CensoPoblacional.class);
                intent.putExtra("opcion","actualizar");
                intent.putExtra("nombre",miCenso.getNombre());
                intent.putExtra("apellido",miCenso.getApellidos());
                intent.putExtra("tipo",tipo);
                intent.putExtra("fecha",miCenso.getfecha());
                intent.putExtra("observaciones",miCenso.getObservaciones());
                intent.putExtra("nombrePadr",miCenso.getNombrepadre());
                intent.putExtra("telefonpadre",miCenso.getTelfonopadre());
                intent.putExtra("dirpadre",miCenso.getDirpadre());
                intent.putExtra("nombreMaadre",miCenso.getNombremadre());
                intent.putExtra("telefonoMadre",miCenso.getTelfonomadre());
                intent.putExtra("dirMadre",miCenso.getDirmadre());
                intent.putExtra("activo",tipoact);
                startActivity(intent);
            }

            @Override
            public void eliminaClick(Censo miCenso,int posicion ) {
            }

        });
        miRecyclerCenso.setAdapter(adaptadorCenso);
    }
    private void referenciar()
    {
        miRecyclerCenso=findViewById(R.id.listacenso);
        listaDeCenso=new ArrayList<>();
        btncrearInfante=findViewById(R.id.btncrear);
    }
    private void llenarRecyclerCenso()
    {
        miRecyclerCenso.setLayoutManager(new LinearLayoutManager(this));
        miReferencia.child("censoinfante").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaDeCenso.clear();
                for(DataSnapshot censoExistente : dataSnapshot.getChildren())
                {
                    Censo micenso=censoExistente.getValue(Censo.class);
                    if (micenso.getActivo()==true)
                        listaDeCenso.add(micenso);
                }
                adaptadorCenso.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListarCensoPoblacion.this, "ERROR EN LA LECTURA DE DATOS", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
