package com.dgaviria.sistur.menus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.dgaviria.sistur.adaptadores.AdaptadorMenuPpal;
import com.dgaviria.sistur.CensoPoblacional;
import com.dgaviria.sistur.clases.MenuOpciones;
import com.dgaviria.sistur.alimentos.ListarPlanAlimenticio;
import com.dgaviria.sistur.R;
import com.dgaviria.sistur.RegistrarCDIHCB;
import com.dgaviria.sistur.usuarios.OpcionesUsuarios;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuPrincipal extends AppCompatActivity {
    RecyclerView miRecyclerM;
    List<MenuOpciones> listadoOpciones;
    AdaptadorMenuPpal adaptadorMenu;
    DatabaseReference miReferenciaM;
    FirebaseAnalytics miAnalisis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        referenciar();
        llenarRecyclerOpciones();

        adaptadorMenu=new AdaptadorMenuPpal(this,listadoOpciones, new AdaptadorMenuPpal.OnItemClick() {
            @Override
            public void itemClick(MenuOpciones misOpciones, int posicion) {
                switch (posicion){
                    case 0:
                        Intent miIntento1 = new Intent(MenuPrincipal.this, OpcionesUsuarios.class);
                        startActivity(miIntento1);
                        //crearAlertaDialogo();
                        break;
                    case 1:
                        Intent miIntento2 = new Intent(MenuPrincipal.this, RegistrarCDIHCB.class);
                        startActivity(miIntento2);
                        break;
                    case 2:
                        Intent miIntento3 = new Intent(MenuPrincipal.this, CensoPoblacional.class);
                        startActivity(miIntento3);
                        break;
                    case 3:
                        Intent miIntento4 = new Intent(MenuPrincipal.this, ListarPlanAlimenticio.class);
                        startActivity(miIntento4);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"Opción seleccionada",Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void imagenClick(MenuOpciones misOpciones, int posicion) {
                switch (posicion) {
                    case 0:
                        Intent miIntento1 = new Intent(MenuPrincipal.this, OpcionesUsuarios.class);
                        startActivity(miIntento1);
                        break;
                    case 1:
                        Intent miIntento2 = new Intent(MenuPrincipal.this, RegistrarCDIHCB.class);
                        startActivity(miIntento2);
                        break;
                    case 2:
                        Intent miIntento3 = new Intent(MenuPrincipal.this, CensoPoblacional.class);
                        startActivity(miIntento3);
                        break;
                    case 3:
                        Intent miIntento4 = new Intent(MenuPrincipal.this, ListarPlanAlimenticio.class);
                        startActivity(miIntento4);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"Opción seleccionada",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        miRecyclerM.setAdapter(adaptadorMenu);
    }
    private void referenciar() {
        miRecyclerM =findViewById(R.id.recyclerOpciones);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        miRecyclerM.setLayoutManager(linearLayoutManager);
        miReferenciaM = FirebaseDatabase.getInstance().getReference();
        miAnalisis=FirebaseAnalytics.getInstance(this);
        listadoOpciones=new ArrayList<>();
    }

    private void llenarRecyclerOpciones() {
        //busca las opciones del menú y llena la lista
        miReferenciaM.child("menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listadoOpciones.clear();
                for (DataSnapshot opcionesExisten : dataSnapshot.getChildren()) {
                    MenuOpciones misOpciones=opcionesExisten.getValue(MenuOpciones.class);
                    //solo adiciona las opciones del menú activas
                    if (misOpciones.getActivo()==true)
                        listadoOpciones.add(misOpciones);
                }
                adaptadorMenu.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error en la lectura de opciones de menú, contacte al administrador",Toast.LENGTH_SHORT).show();
            }
        });
    }
}