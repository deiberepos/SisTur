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
                Toast.makeText(getApplicationContext(),"Modificar Nombre",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void correoClick(Usuarios misUsuarios, int posicion) {
                Toast.makeText(getApplicationContext(),"Modificar Nombre",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void telefonoClick(Usuarios misUsuarios, int posicion) {
                Toast.makeText(getApplicationContext(),"Modificar Correo",Toast.LENGTH_SHORT).show();
            }
        });

        miRecycler.setAdapter(adaptadorUsuarios);
    }

    private void referenciar() {
        miRecycler=findViewById(R.id.recycler);
    }

    private void llenarRecycler() {
        miReferencia= FirebaseDatabase.getInstance().getReference();
        listadoUsuarios=new ArrayList<>();
        //verifica que no se repita el nombre del usuario
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

            }
        });
    }
}
