package com.dgaviria.sistur.usuarios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.dgaviria.sistur.adaptadores.AdaptadorListaUsuarios;
import com.dgaviria.sistur.clases.Usuarios;
import com.dgaviria.sistur.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListarUsuarios extends AppCompatActivity {

    RecyclerView miRecyclerUsuarios;
    List<Usuarios> listaDeUsuarios;
    AdaptadorListaUsuarios adaptadorUsuarios;
    DatabaseReference miReferenciaU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_usuarios);

        referenciar();
        llenarRecyclerUsuarios();

        adaptadorUsuarios=new AdaptadorListaUsuarios(this,listaDeUsuarios, new AdaptadorListaUsuarios.OnItemClick() {
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
        miRecyclerUsuarios.setAdapter(adaptadorUsuarios);
    }

    private void referenciar() {
        miRecyclerUsuarios=findViewById(R.id.recyclerListaU);
        listaDeUsuarios =new ArrayList<>();
        miRecyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));
        miReferenciaU = FirebaseDatabase.getInstance().getReference();
    }

    private void llenarRecyclerUsuarios() {
        //busca el nombre de todos los usuarios y llena la lista
        miReferenciaU.child("usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaDeUsuarios.clear();
                for (DataSnapshot usuariosExisten : dataSnapshot.getChildren()) {
                    Usuarios miUsuario=usuariosExisten.getValue(Usuarios.class);
                    listaDeUsuarios.add(miUsuario);
                }
                adaptadorUsuarios.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error en la lectura de usuarios, contacte al administrador",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
