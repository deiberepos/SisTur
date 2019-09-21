package com.dgaviria.sistur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dgaviria.sistur.Adaptadores.AdaptadorListaUsuarios;
import com.dgaviria.sistur.Adaptadores.AdptadorCDI;
import com.dgaviria.sistur.Clases.CdiHcb;
import com.dgaviria.sistur.Clases.Usuarios;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GestionarCDIHCB extends AppCompatActivity {
    RecyclerView miRecyclerCDI;
    List<CdiHcb> listaDeCDI;
    AdptadorCDI adaptadorCentros;
    DatabaseReference miReferencia;
    Button btncrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_cdihcb);
        miReferencia = FirebaseDatabase.getInstance().getReference();
        referenciar();
        llenarRecyclerCDI();

        adaptadorCentros = new AdptadorCDI(this, listaDeCDI, new AdptadorCDI.OnItemClick() {
            @Override
            public void itemClick(CdiHcb centros, int posicion) {
                Toast.makeText(getApplicationContext(),"Tipo de centro",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void modificaClick(CdiHcb centros, int posicion) {
                Toast.makeText(getApplicationContext(),"Modificar datos",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void eliminaClick(CdiHcb centros, int posicion) {
                Toast.makeText(getApplicationContext(),"Eliminar centros",Toast.LENGTH_SHORT).show();
            }
        });
        miRecyclerCDI.setAdapter(adaptadorCentros);

        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrarCDIHCB.class);
                startActivity(intent);
            }
        });
    }
    private void referenciar(){
        miRecyclerCDI=findViewById(R.id.idrecyclerGestCDI);
        listaDeCDI=new ArrayList<>();
        btncrear=findViewById(R.id.idbtnCrearCDI);
    }

    private void llenarRecyclerCDI(){
        miRecyclerCDI.setLayoutManager(new LinearLayoutManager(this));
        miReferencia.child("Centros").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datosbd : dataSnapshot.getChildren()) {
                    CdiHcb cdiHcb=datosbd.getValue(CdiHcb.class);
                    listaDeCDI.add(cdiHcb);
                }
                adaptadorCentros.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error en la lectura de los centros, contacte al administrador",Toast.LENGTH_SHORT).show();

            }
        });
    }
}

