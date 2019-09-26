package com.dgaviria.sistur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.dgaviria.sistur.adaptadores.AdaptadorListaCenso;
import com.dgaviria.sistur.clases.Censo;
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
    List<Censo> listaDeCenso;
    AdaptadorListaCenso adaptadorCenso;
    DatabaseReference miReferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_censo_poblacion);
        miReferencia = FirebaseDatabase.getInstance().getReference();
        referenciar();
        llenarRecyclerCenso();


        adaptadorCenso=new AdaptadorListaCenso(this,listaDeCenso, new AdaptadorListaCenso.OnItemClick() {
            @Override
            public void itemClick(Censo miCenso, int posicion) {
            }

            @Override
            public void modificaClick(Censo miCenso, int posicion) {
                Intent mi = new Intent(ListarCensoPoblacion.this, CensoPoblacional.class);
                startActivity(mi);
                Toast.makeText(getApplicationContext(),"MODIFICAR",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void eliminaClick(Censo miCenso,int posicion ) {
                Toast.makeText(getApplicationContext(),"ELIMINADO",Toast.LENGTH_SHORT).show();
            }

        });
        miRecyclerCenso.setAdapter(adaptadorCenso);
    }
    private void referenciar()
    {
        miRecyclerCenso=findViewById(R.id.listacenso);
        listaDeCenso=new ArrayList<>();
    }
    private void llenarRecyclerCenso()
    {
        miRecyclerCenso.setLayoutManager(new LinearLayoutManager(this));
        miReferencia.child("CensoInfante").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot censoExistente : dataSnapshot.getChildren())
                {
                    Censo micenso=censoExistente.getValue(Censo.class);
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
