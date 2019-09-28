package com.dgaviria.sistur.alimentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dgaviria.sistur.adaptadores.AdaptadorPlanAlimenticio;
import com.dgaviria.sistur.clases.PlanAlimenticio;
import com.dgaviria.sistur.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListarPlanAlimenticio extends AppCompatActivity {
    EditText buscar;
    ImageButton buscarImagen;
    RecyclerView miRecyclerA;
    List<PlanAlimenticio> listaDeAlimentos;
    AdaptadorPlanAlimenticio adaptadorAlimentos;
    DatabaseReference miReferenciaA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_plan_alimenticio);


        referenciar();
        llenarRecyclerAlimentos();
        adaptadorAlimentos=new AdaptadorPlanAlimenticio(this, listaDeAlimentos, new AdaptadorPlanAlimenticio.OnItemClick() {
            @Override
            public void itemClick(PlanAlimenticio misAlimentos, int posicion) {
                Toast.makeText(getApplicationContext(), "Seleccionado 1", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void seleccionaClick(PlanAlimenticio misAlimentos, int posicion) {
                Toast.makeText(getApplicationContext(), "Seleccionado 2", Toast.LENGTH_SHORT).show();
            }
        });
        miRecyclerA.setAdapter(adaptadorAlimentos);
    }

    private void referenciar() {
        buscar = findViewById(R.id.txtBuscar);
        buscarImagen = findViewById(R.id.imgBuscar);
        miRecyclerA =findViewById(R.id.recyclerListaA);
        miRecyclerA.setLayoutManager(new LinearLayoutManager(this));
        miReferenciaA=FirebaseDatabase.getInstance().getReference();
        listaDeAlimentos =new ArrayList<>();

       /* buscarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebasePlanesSearch();
            }
        });*/
    }



    public class planViewHolder extends RecyclerView.ViewHolder{

        View view;

        public planViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
        }
    }

    private void llenarRecyclerAlimentos() {
        //busca el nombre de todos los alimentos y llena la lista
        miReferenciaA.child("plan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaDeAlimentos.clear();
                for (DataSnapshot alimentosExisten : dataSnapshot.getChildren()) {
                    PlanAlimenticio miAlimento=alimentosExisten.getValue(PlanAlimenticio.class);
                    listaDeAlimentos.add(miAlimento);
                }
                adaptadorAlimentos.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error en la lectura de Plan nutricional, contacte al administrador",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
