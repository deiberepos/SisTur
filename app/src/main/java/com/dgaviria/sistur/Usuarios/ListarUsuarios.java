package com.dgaviria.sistur.Usuarios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.dgaviria.sistur.Adaptadores.AdaptadorListaUsuarios;
import com.dgaviria.sistur.Clases.Usuarios;
import com.dgaviria.sistur.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListarUsuarios extends AppCompatActivity {
    RecyclerView miRecycler;
    List<Usuarios> listadoUsuarios;
    AdaptadorListaUsuarios adaptadorUsuarios;
    DatabaseReference miReferencia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_usuarios);
        referenciar();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        miRecycler.setLayoutManager(linearLayoutManager);
        llenarRecycler();

        adaptadorUsuarios=new AdaptadorListaUsuarios(listadoUsuarios, this, new AdaptadorListaUsuarios.OnItemClick() {
            @Override
            public void itemClick(Usuarios misUsuarios, int posicion) {
                Toast.makeText(getApplicationContext(),"Modificar datos",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void modificaClick(Usuarios misUsuarios, int posicion) {
                Toast.makeText(getApplicationContext(),"Modificar información",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void eliminaClick(Usuarios misUsuarios, int posicion) {
                Toast.makeText(getApplicationContext(),"Eliminar usuario",Toast.LENGTH_SHORT).show();
            }
        });

        miRecycler.setAdapter(adaptadorUsuarios);
    }

    private void referenciar() {
        miRecycler=findViewById(R.id.recyclerLista);
    }

    private void llenarRecycler() {
        miReferencia= FirebaseDatabase.getInstance().getReference();
        listadoUsuarios=new ArrayList<>();
        //busca el nombre de todos los usuarios y llena la lista
        miReferencia.child("usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listadoUsuarios.clear();
                for (DataSnapshot usuariosExisten : dataSnapshot.getChildren()) {
                    Usuarios miUsuario=usuariosExisten.getValue(Usuarios.class);
                    listadoUsuarios.add(miUsuario);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error en la lectura de usuarios, contacte al administrador",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
