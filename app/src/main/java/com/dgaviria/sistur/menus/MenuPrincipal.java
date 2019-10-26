package com.dgaviria.sistur.menus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.dgaviria.sistur.CompraGaleria;
import com.dgaviria.sistur.ConsultaMinutas;
import com.dgaviria.sistur.CrearCalendario;
import com.dgaviria.sistur.ListarCensoPoblacion;
import com.dgaviria.sistur.PublicarMinutas;
import com.dgaviria.sistur.adaptadores.AdaptadorMenuPpal;
import com.dgaviria.sistur.alimentos.ListarPlanAlimenticio;
import com.dgaviria.sistur.clases.MenuOpciones;
import com.dgaviria.sistur.GestionarCDIHCB;
import com.dgaviria.sistur.R;
import com.dgaviria.sistur.usuarios.ListarUsuarios;
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
    DatabaseReference miReferencia, childpoblacion;
    private Bundle bundle;
    public static String recibeRol, recibeUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        referenciar();

        Toast.makeText(getApplicationContext(),"Bienvenido: "+recibeUsuario+" al menú "+recibeRol, Toast.LENGTH_SHORT).show();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        miRecycler.setLayoutManager(linearLayoutManager);
        llenarRecyclerOpciones();
        adaptadorMenu=new AdaptadorMenuPpal(this,listadoOpciones, new AdaptadorMenuPpal.OnItemClick() {
            @Override
            public void itemClick(MenuOpciones misOpciones, int posicion) {
                switch (misOpciones.getOrden()){
                    case 1:
                        Intent miIntento1 = new Intent(MenuPrincipal.this, ListarUsuarios.class);
                        startActivity(miIntento1);
                        break;
                    case 2:
                        Intent intent = new Intent(MenuPrincipal.this, GestionarCDIHCB.class);
                        startActivity(intent);
                        break;
                    case 3:
                        Intent miIntento3 = new Intent(MenuPrincipal.this, ListarCensoPoblacion.class);
                        startActivity(miIntento3);
                        break;
                    case 4:
                        Intent miIntento4 = new Intent(MenuPrincipal.this, ListarPlanAlimenticio.class);
                        startActivity(miIntento4);
                        break;
                    case 5:
                        Intent miIntento5 = new Intent(MenuPrincipal.this, ConsultaMinutas.class);
                        startActivity(miIntento5);
                        break;
                    case 6:
                        Intent miIntento6 = new Intent(MenuPrincipal.this, CrearCalendario.class);
                        startActivity(miIntento6);
                        break;
                    case 7:
                        //Antes de acceder al menu de galeria se saca el totaldeniños asociados a los Centros
                        // y se envia para realizar el cálculo de el total de compras que se debe realizar
                        miReferencia= FirebaseDatabase.getInstance().getReference();
                        childpoblacion=miReferencia.child("poblacionCentros");
                        childpoblacion.addValueEventListener(new ValueEventListener() {
                            long total = 0;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot datos:dataSnapshot.getChildren()) {
                                    total += datos.getChildrenCount()-1;
                                }
                                Intent miIntento7 = new Intent(MenuPrincipal.this, CompraGaleria.class);
                                miIntento7.putExtra("total",total);
                                startActivity(miIntento7);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    case 8:
                        Intent miIntento8 = new Intent(MenuPrincipal.this, PublicarMinutas.class);
                        startActivity(miIntento8);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"Opción "+misOpciones.getTitulo(),Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void imagenClick(MenuOpciones misOpciones, int posicion) {
                switch (misOpciones.getOrden()) {
                    case 1:
                        Intent miIntento1 = new Intent(MenuPrincipal.this, ListarUsuarios.class);
                        startActivity(miIntento1);
                        break;
                    case 2:
                        Intent miIntento2 = new Intent(MenuPrincipal.this, GestionarCDIHCB.class);
                        startActivity(miIntento2);
                        break;
                    case 3:
                        Intent miIntento3 = new Intent(MenuPrincipal.this, ListarCensoPoblacion.class);
                        startActivity(miIntento3);
                        break;
                    case 4:
                        Intent miIntento4 = new Intent(MenuPrincipal.this, ListarPlanAlimenticio.class);
                        startActivity(miIntento4);
                        break;
                    case 5:
                        Intent miIntento5 = new Intent(MenuPrincipal.this, ConsultaMinutas.class);
                        startActivity(miIntento5);
                        break;
                    case 6:
                        Intent miIntento6 = new Intent(MenuPrincipal.this, CrearCalendario.class);
                        startActivity(miIntento6);
                        break;
                    case 7:
                        miReferencia= FirebaseDatabase.getInstance().getReference();
                        childpoblacion=miReferencia.child("poblacionCentros");
                        childpoblacion.addValueEventListener(new ValueEventListener() {
                            long total = 0;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot datos:dataSnapshot.getChildren()) {
                                     total += datos.getChildrenCount()-1;
                                }
                                Intent miIntento7 = new Intent(MenuPrincipal.this, CompraGaleria.class);
                                miIntento7.putExtra("total",total);
                                startActivity(miIntento7);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    case 8:
                        Intent miIntento8 = new Intent(MenuPrincipal.this, PublicarMinutas.class);
                        startActivity(miIntento8);
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
        bundle = getIntent().getExtras();
        recibeRol = bundle.getString("rol");
        recibeUsuario= bundle.getString("usuario");
    }

    private void llenarRecyclerOpciones() {
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
                    //if (misOpciones.getActivo()==true)
                      //  listadoOpciones.add(misOpciones);
                    if(recibeRol.equals("compras")&& misOpciones.getActivo()){
                        //if(misOpciones.getOrden().equals(5) ||misOpciones.getOrden().equals(7) ||misOpciones.getOrden().equals(8)){
                        if(misOpciones.getMinimorol().equals("compras")||misOpciones.getMinimorol().equals("basico"))  {
                        listadoOpciones.add(misOpciones);
                        }
                    }else
                        if(recibeRol.equals("basico")&& misOpciones.getActivo()){
                            //if(misOpciones.getOrden().equals(5) ||misOpciones.getOrden().equals(8)){
                            if(misOpciones.getMinimorol().equals("basico"))  {
                                listadoOpciones.add(misOpciones);
                        }
                    }else
                        if (recibeRol.equals("gestor") && misOpciones.getActivo()){
                        listadoOpciones.add(misOpciones);
                    }
                }

                adaptadorMenu.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error en la lectura de opciones de menú, contacte al administrador",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
