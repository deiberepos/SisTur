package com.dgaviria.sistur.Menus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.dgaviria.sistur.Adaptadores.AdaptadorListaUsuarios;
import com.dgaviria.sistur.Adaptadores.AdaptadorMenuPpal;
import com.dgaviria.sistur.CensoPoblacional;
import com.dgaviria.sistur.Clases.MenuOpciones;
import com.dgaviria.sistur.Clases.Usuarios;
import com.dgaviria.sistur.R;
import com.dgaviria.sistur.RegistrarCDIHCB;
import com.dgaviria.sistur.Usuarios.CrearUsuarios;
import com.dgaviria.sistur.Usuarios.OpcionesUsuarios;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuPrincipal extends AppCompatActivity {
    RecyclerView miRecycler;
    List<MenuOpciones> listadoOpciones;
    AdaptadorMenuPpal adaptadorMenu;
    DatabaseReference miReferencia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        referenciar();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        miRecycler.setLayoutManager(linearLayoutManager);
        llenarRecycler();

        adaptadorMenu=new AdaptadorMenuPpal(listadoOpciones, this, new AdaptadorMenuPpal.OnItemClick() {
            @Override
            public void itemClick(MenuOpciones misOpciones, int posicion) {
                switch (posicion){
                    case 0:
                        Intent miIntento1 = new Intent(getApplicationContext(), OpcionesUsuarios.class);
                        startActivity(miIntento1);
                        break;
                    case 1:
                        Intent miIntento2 = new Intent(getApplicationContext(), RegistrarCDIHCB.class);
                        startActivity(miIntento2);
                        break;
                    case 2:
                        Intent miIntento3 = new Intent(getApplicationContext(), CensoPoblacional.class);
                        startActivity(miIntento3);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"Opción "+misOpciones.getTitulo(),Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void imagenClick(MenuOpciones misOpciones, int posicion) {
                switch (posicion) {
                    case 0:
                        Intent miIntento1 = new Intent(getApplicationContext(), OpcionesUsuarios.class);
                        startActivity(miIntento1);
                        break;
                    case 1:
                        Intent miIntento2 = new Intent(getApplicationContext(), RegistrarCDIHCB.class);
                        startActivity(miIntento2);
                        break;
                    case 2:
                        Intent miIntento3 = new Intent(getApplicationContext(), CensoPoblacional.class);
                        startActivity(miIntento3);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Imagen " + misOpciones.getTitulo(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        miRecycler.setAdapter(adaptadorMenu);
    }
    private void referenciar() {
        miRecycler=findViewById(R.id.recyclerOpciones);
    }

    private void llenarRecycler() {
        miReferencia= FirebaseDatabase.getInstance().getReference();
        listadoOpciones=new ArrayList<>();
        //busca las opciones del menú y llena la lista
        miReferencia.child("menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listadoOpciones.clear();
                for (DataSnapshot opcionesExisten : dataSnapshot.getChildren()) {
                    MenuOpciones misOpciones=opcionesExisten.getValue(MenuOpciones.class);
                    //solo adiciona las opciones del menú activas
                    if (misOpciones.getActivo()==true)
                        listadoOpciones.add(misOpciones);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error en la lectura de opciones de menú, contacte al administrador",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
