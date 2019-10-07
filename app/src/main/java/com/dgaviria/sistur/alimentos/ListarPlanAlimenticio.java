package com.dgaviria.sistur.alimentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
    RecyclerView miRecyclerA;
    List<PlanAlimenticio> listaDeAlimentos;
    AdaptadorPlanAlimenticio adaptadorAlimentos;
    DatabaseReference miReferenciaA;
    TextView numSelecc,numTotal;
    EditText buscarAlimento;
    Button botonGuarda, botonVolver,botonBuscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_plan_alimenticio);

        referenciar();
        llenarRecyclerAlimentos();
        adaptadorAlimentos=new AdaptadorPlanAlimenticio(this,listaDeAlimentos);


        miRecyclerA.setAdapter(adaptadorAlimentos);
        adaptadorAlimentos.setOnClickListener(new AdaptadorPlanAlimenticio.EscuchaPresionaClick() {
            @Override
            public void itemClick(View vista, PlanAlimenticio misAlimentos, int posicion) {
                if (adaptadorAlimentos.numeroDeAlimentosSeleccionados()>0){
                    habilitarAcciones(posicion);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Mantenga presionado para iniciar con la selección" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void itemClickLargo(View vista, PlanAlimenticio misAlimentos, int posicion) {
                habilitarAcciones(posicion);
            }
        });
/*        botonGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Guardando lista alimentos...",Toast.LENGTH_SHORT).show();
            }
        });*/
/*        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
        /*botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoBuscar = buscarAlimento.getText().toString().trim();
                Toast.makeText(getApplicationContext(),"Buscando "+textoBuscar,Toast.LENGTH_SHORT).show();
            }
        });
*/
    }

    private void habilitarAcciones(int posicion) {
        adaptadorAlimentos.habilitaSeleccion(posicion);
        int conteo=adaptadorAlimentos.numeroDeAlimentosSeleccionados();
        numSelecc.setText(String.valueOf(conteo).trim());
        PlanAlimenticio miAlimento=adaptadorAlimentos.traigaAlimento(posicion);
        miAlimento.setActivo(!miAlimento.getActivo());
    }

    private void referenciar() {
        miRecyclerA =findViewById(R.id.recyclerListaA);
        numSelecc=findViewById(R.id.numSeleccionados);
        numTotal=findViewById(R.id.numAlimentos);

        miRecyclerA.setLayoutManager(new LinearLayoutManager(this));
        miReferenciaA=FirebaseDatabase.getInstance().getReference();
        listaDeAlimentos =new ArrayList<>();
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
                numTotal.setText(String.valueOf(listaDeAlimentos.size()).trim());
                adaptadorAlimentos.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error en la lectura de Plan nutricional, contacte al administrador",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
