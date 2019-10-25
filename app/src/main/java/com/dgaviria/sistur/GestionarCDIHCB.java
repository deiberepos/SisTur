package com.dgaviria.sistur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dgaviria.sistur.adaptadores.AdptadorCDI;
import com.dgaviria.sistur.clases.CdiHcb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    FloatingActionButton btncrear;


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
                int tipo=2;
                if(centros.getTipo().equals("Centro de Desarrollo Infantil")){
                    tipo=1;
                }
                int tipoact=1;
                if (centros.getActivo().equals("true"))
                {
                    tipoact=2;
                }
                Intent intent = new Intent(getApplicationContext(), RegistrarCDIHCB.class);
                intent.putExtra("opcion","actualizar");
                intent.putExtra("nombrecentro",centros.getNombreCDI());
                intent.putExtra("tipo",tipo);
                intent.putExtra("nombreen",centros.getNombreEncargado());
                intent.putExtra("direccionen",centros.getDirEncargado());
                intent.putExtra("telefonoen",centros.getTelEncargado());
                intent.putExtra("nombrecon",centros.getNombreContacto());
                intent.putExtra("direccioncon",centros.getDirContacto());
                intent.putExtra("telefonocon",centros.getTelContacto());
                intent.putExtra("activo",tipoact);
                startActivity(intent);
            }

            @Override
            public void eliminaClick(CdiHcb centros, int posicion) {

                final String nombreeli = centros.getNombreCDI();
                AlertDialog.Builder builder = new AlertDialog.Builder(GestionarCDIHCB.this);
                builder.setTitle("msj Eliminar"+nombreeli);
                builder.setMessage("Está seguro que desea eliminar a "+centros.getNombreCDI()+" de la base de datos?");
                builder.setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        miReferencia.child("Centros").child(nombreeli).removeValue();
                        //llenarRecyclerCDI();
                        //miRecyclerCDI.setAdapter(adaptadorCentros);
                        //miRecyclerCDI.
                        adaptadorCentros.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(),"Centro eliminado de la base de datos",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"Acción de eliminación cancelada",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
        miRecyclerCDI.setAdapter(adaptadorCentros);

        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrarCDIHCB.class);
                intent.putExtra("opcion","crear");
                startActivity(intent);
            }
        });
    }
    private void referenciar(){
        miRecyclerCDI=findViewById(R.id.idrecyclerGestCDI);
        listaDeCDI=new ArrayList<>();
        btncrear=findViewById(R.id.idbtnCrearCDI);

    }

    private CdiHcb obtenercentro(String nombre){
        final CdiHcb centro= new CdiHcb();
        miReferencia.child("Centros").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //centro=dataSnapshot.getChildren().equals()
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return centro;
    }

    private void llenarRecyclerCDI(){
        miRecyclerCDI.setLayoutManager(new LinearLayoutManager(this));
        miReferencia.child("Centros").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaDeCDI.clear();
                for (DataSnapshot datosbd : dataSnapshot.getChildren()) {
                    CdiHcb cdiHcb=datosbd.getValue(CdiHcb.class);
                    if (cdiHcb.getActivo()==true)
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

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

