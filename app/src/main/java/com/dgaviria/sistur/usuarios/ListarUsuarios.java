package com.dgaviria.sistur.usuarios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dgaviria.sistur.adaptadores.AdaptadorListaUsuarios;
import com.dgaviria.sistur.clases.Usuarios;
import com.dgaviria.sistur.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    DatabaseReference miReferencia;

    private FloatingActionButton btnguardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_usuarios);
        miReferencia= FirebaseDatabase.getInstance().getReference();
        referenciar();
        llenarRecyclerUsuarios();

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mi = new Intent(ListarUsuarios.this, CrearUsuarios.class);
                mi.putExtra("opcion","crear");
                startActivity(mi);
            }
        });
        adaptadorUsuarios=new AdaptadorListaUsuarios(this,listaDeUsuarios, new AdaptadorListaUsuarios.OnItemClick() {
            @Override
            public void itemClick(Usuarios misUsuarios, int posicion) {
                Toast.makeText(getApplicationContext(),"Modificar datos",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void modificaClick(Usuarios misUsuarios, int posicion) {
                Intent intent = new Intent(getApplicationContext(), CrearUsuarios.class);
                intent.putExtra("opcion","actualizar");
                intent.putExtra("usuario",misUsuarios.getUsuario());
                intent.putExtra("nombre",misUsuarios.getNombre());
                intent.putExtra("contrasena",misUsuarios.getContrasena());
                intent.putExtra("correo",misUsuarios.getCorreo());
                intent.putExtra("tipo",misUsuarios.getRolusuario());
                startActivity(intent);
            }

        });
        miRecyclerUsuarios.setAdapter(adaptadorUsuarios);
    }

    private void referenciar() {
        miRecyclerUsuarios=findViewById(R.id.recyclerListaU);
        listaDeUsuarios =new ArrayList<>();
        btnguardar =findViewById(R.id.guardar);
    }

    private void llenarRecyclerUsuarios() {
        miRecyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));
        //busca el nombre de todos los usuarios y llena la lista
        miReferencia.child("usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaDeUsuarios.clear();
                for (DataSnapshot usuariosExisten : dataSnapshot.getChildren())
                {
                    Usuarios miUsuario=usuariosExisten.getValue(Usuarios.class);
                    //if (miUsuario.getActivar()==true)
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

    @Override
    protected void onResume() {
        super.onResume();
        //finish();
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
