package com.dgaviria.sistur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dgaviria.sistur.adaptadores.AdaptadorListaProgramacion;
import com.dgaviria.sistur.clases.Calendario;
import com.dgaviria.sistur.clases.Preparacion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PublicarMinutas extends AppCompatActivity {
    Spinner spnSemanas;
    String semanaSeleccionada;
    TextView minuLunes,minuMartes,minuMiercoles,minuJueves,minuViernes,fechaDiaL,fechaDiaM,fechaDiaMi,fechaDiaJ,fechaDiaV;
    DatabaseReference miReferenciaPub;
    ArrayAdapter<String> adaptadorSemana;
    ArrayList<String> listaSemanas;
    ArrayList<Preparacion> preparacionesLunes,preparacionesMartes,preparacionesMiercoles,preparacionesJueves,preparacionesViernes;
    RecyclerView miRecyclerDiarioL,miRecyclerDiarioM,miRecyclerDiarioMi,miRecyclerDiarioJ,miRecyclerDiarioV;
    AdaptadorListaProgramacion adaptadorLunes,adaptadorMartes,adaptadorMiercoles,adaptadorJueves,adaptadorViernes;
    String fechaDias[];
    static String[] menuDias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_minutas);
        referenciar();
        llenarSemanas();
        spnSemanas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View vista, int item, long l) {
                semanaSeleccionada=spnSemanas.getSelectedItem().toString();
                buscarMinutas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void referenciar() {
        spnSemanas=findViewById(R.id.spinPublicarSemana);
        minuLunes=findViewById(R.id.minutaLunes);
        minuMartes=findViewById(R.id.minutaMartes);
        minuMiercoles=findViewById(R.id.minutaMiercoles);
        minuJueves=findViewById(R.id.minutaJueves);
        minuViernes=findViewById(R.id.minutaViernes);
        miRecyclerDiarioL=findViewById(R.id.listaLunes);
        miRecyclerDiarioM=findViewById(R.id.listaMartes);
        miRecyclerDiarioMi=findViewById(R.id.listaMiercoles);
        miRecyclerDiarioJ=findViewById(R.id.listarJueves);
        miRecyclerDiarioV=findViewById(R.id.listaViernes);
        fechaDiaL=findViewById(R.id.fechaLunes);
        fechaDiaM=findViewById(R.id.fechaMartes);
        fechaDiaMi=findViewById(R.id.fechaMiercoles);
        fechaDiaJ=findViewById(R.id.fechaJueves);
        fechaDiaV=findViewById(R.id.fechaViernes);
        listaSemanas=new ArrayList<>();
        menuDias=new String[5];
    }

    private void llenarSemanas(){
        miReferenciaPub= FirebaseDatabase.getInstance().getReference("semanas");
        miReferenciaPub.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot!=null && dataSnapshot.getChildren()!=null){
                    for (DataSnapshot miSemana:dataSnapshot.getChildren()){
                        listaSemanas.add(miSemana.getKey());
                    }
                    adaptadorSemana=new ArrayAdapter<String>(PublicarMinutas.this,android.R.layout.simple_spinner_item,listaSemanas);
                    adaptadorSemana.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnSemanas.setAdapter(adaptadorSemana);
                    adaptadorSemana.notifyDataSetChanged();
                    //spnSemanas.setSelected(false);
                    spnSemanas.setSelection(0, true); //no selecciona un elemento del spinner
                    //spnCalendario.setOnItemSelectedListener(null);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error de lectura de semanas, contacte al administrador",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void buscarMinutas(){
        for(int dia=0;dia<5;dia++)
            menuDias[dia] ="X";
        miReferenciaPub= FirebaseDatabase.getInstance().getReference("semanas").child(semanaSeleccionada);
        miReferenciaPub.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot!=null && dataSnapshot.getChildren()!=null){
                    if (dataSnapshot.exists()){
                        Calendario miSemana=dataSnapshot.getValue(Calendario.class);
                        menuDias[0] =miSemana.getMinuta1();
                        menuDias[1]=miSemana.getMinuta2();
                        menuDias[2] =miSemana.getMinuta3();
                        menuDias[3] =miSemana.getMinuta4();
                        menuDias[4] =miSemana.getMinuta5();
                        String fechaDia="01/01/2019",diaInicio="",mesInicio="",mesFecha="";
                        Date fechaInicio;
                        diaInicio=miSemana.getDiainicial();
                        mesInicio=miSemana.getMesinicial();
                        switch (mesInicio){
                            case "ENERO":
                                mesFecha="01";
                                break;
                            case "FEBRERO":
                                mesFecha="02";
                                break;
                            case "MARZO":
                                mesFecha="03";
                                break;
                            case "ABRIL":
                                mesFecha="04";
                                break;
                            case "MAYO":
                                mesFecha="05";
                                break;
                            case "JUNIO":
                                mesFecha="06";
                                break;
                            case "JULIO":
                                mesFecha="07";
                                break;
                            case "AGOSTO":
                                mesFecha="08";
                                break;
                            case "SEPTIEMBRE":
                                mesFecha="09";
                                break;
                            case "OCTUBRE":
                                mesFecha="10";
                                break;
                            case "NOVIEMBRE":
                                mesFecha="11";
                                break;
                            case "DICIEMBRE":
                                mesFecha="12";
                                break;
                        }
                        fechaDia=diaInicio+"/"+mesFecha+"/2019";
                        SimpleDateFormat convierteFecha=new SimpleDateFormat("dd/mm/yyyy");
                        try{
                            fechaInicio=convierteFecha.parse(fechaDia);
                        }catch (ParseException e){
                            Toast.makeText(PublicarMinutas.this, "Error de conversión de fecha, contacte al administrador", Toast.LENGTH_SHORT).show();
                            fechaInicio=new Date();
                        }
                        fechaDias=new String[5];
                        for (int numDia=1;numDia<=5;numDia++){
                            fechaDias[numDia-1]=convierteFecha.format(fechaInicio);
                            Calendar dia=Calendar.getInstance();
                            dia.setTime(fechaInicio);
                            dia.add(Calendar.DATE,1);
                            fechaInicio=dia.getTime();
                        }
                        publicarDatos();
                        llenarListaPreparaciones();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error de lectura de minutas asociadas, contacte al administrador",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void publicarDatos() {
        for (int dia=0;dia<5;dia++){
            switch (dia){
                case 0:
                    switch (menuDias[dia]){
                        case "X":
                            minuLunes.setText("FESTIVO");
                            break;
                        case "V":
                            minuLunes.setText("---");
                            break;
                        default:
                            minuLunes.setText(menuDias[dia]);
                            break;
                    }
                    fechaDiaL.setText(fechaDias[dia]);
                    break;
                case 1:
                    switch (menuDias[dia]){
                        case "X":
                            minuMartes.setText("FESTIVO");
                            break;
                        case "V":
                            minuMartes.setText("---");
                            break;
                        default:
                            minuMartes.setText(menuDias[dia]);
                            break;
                    }
                    fechaDiaM.setText(fechaDias[dia]);
                    break;
                case 2:
                    switch (menuDias[dia]){
                        case "X":
                            minuMiercoles.setText("FESTIVO");
                            break;
                        case "V":
                            minuMiercoles.setText("---");
                            break;
                        default:
                            minuMiercoles.setText(menuDias[dia]);
                            break;
                    }
                    fechaDiaMi.setText(fechaDias[dia]);
                    break;
                case 3:
                    switch (menuDias[dia]){
                        case "X":
                            minuJueves.setText("FESTIVO");
                            break;
                        case "V":
                            minuJueves.setText("---");
                            break;
                        default:
                            minuJueves.setText(menuDias[dia]);
                            break;
                    }
                    fechaDiaJ.setText(fechaDias[dia]);
                    break;
                case 4:
                    switch (menuDias[dia]){
                        case "X":
                            minuViernes.setText("FESTIVO");
                            break;
                        case "V":
                            minuViernes.setText("---");
                            break;
                        default:
                            minuViernes.setText(menuDias[dia]);
                            break;
                    }
                    fechaDiaV.setText(fechaDias[dia]);
                    break;
            }
        }
    }

    private void llenarListaPreparaciones() {
        //Preparaciones del lunes
        preparacionesLunes = new ArrayList<>();
        if (!menuDias[0].equals("X") && !menuDias[0].equals("V")) {
            LinearLayoutManager linearLayoutManagerL=new LinearLayoutManager(this);
            miRecyclerDiarioL.setLayoutManager(linearLayoutManagerL);

            miReferenciaPub = FirebaseDatabase.getInstance().getReference("minutas").child(menuDias[0]);
            miReferenciaPub.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                        preparacionesLunes.clear();
                        for (DataSnapshot miPrepara : dataSnapshot.getChildren()) {
                            String numeroPreparacion=miPrepara.getKey();
                            String nombrePreparacion=miPrepara.child("preparacion").getValue(String.class);
                            Preparacion miPreparacion=new Preparacion(numeroPreparacion,nombrePreparacion);
                            preparacionesLunes.add(miPreparacion);
                        }
                        adaptadorLunes.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error de lectura de minutas asociadas, contacte al administrador", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        adaptadorLunes=new AdaptadorListaProgramacion(this, preparacionesLunes);
        miRecyclerDiarioL.setAdapter(adaptadorLunes);
    //Preparaciones del martes
        preparacionesMartes = new ArrayList<>();
        if (!menuDias[1].equals("X") && !menuDias[1].equals("V")) {
            LinearLayoutManager linearLayoutManagerM=new LinearLayoutManager(this);
            miRecyclerDiarioM.setLayoutManager(linearLayoutManagerM);
            miReferenciaPub = FirebaseDatabase.getInstance().getReference("minutas").child(menuDias[1]);
            miReferenciaPub.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                        preparacionesMartes.clear();
                        for (DataSnapshot miPrepara : dataSnapshot.getChildren()) {
                            String numeroPreparacion=miPrepara.getKey();
                            String nombrePreparacion=miPrepara.child("preparacion").getValue(String.class);
                            Preparacion miPreparacion=new Preparacion(numeroPreparacion,nombrePreparacion);
                            preparacionesMartes.add(miPreparacion);
                        }
                        adaptadorMartes.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error de lectura de minutas asociadas, contacte al administrador", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        adaptadorMartes=new AdaptadorListaProgramacion(this, preparacionesMartes);
        miRecyclerDiarioM.setAdapter(adaptadorMartes);
        //Preparaciones del miércoles
        preparacionesMiercoles = new ArrayList<>();
        if (!menuDias[2].equals("X") && !menuDias[2].equals("V")) {
            LinearLayoutManager linearLayoutManagerMi=new LinearLayoutManager(this);
            miRecyclerDiarioMi.setLayoutManager(linearLayoutManagerMi);
            miReferenciaPub = FirebaseDatabase.getInstance().getReference("minutas").child(menuDias[2]);
            miReferenciaPub.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                        preparacionesMiercoles.clear();
                        for (DataSnapshot miPrepara : dataSnapshot.getChildren()) {
                            String numeroPreparacion=miPrepara.getKey();
                            String nombrePreparacion=miPrepara.child("preparacion").getValue(String.class);
                            Preparacion miPreparacion=new Preparacion(numeroPreparacion,nombrePreparacion);
                            preparacionesMiercoles.add(miPreparacion);
                        }
                        adaptadorMiercoles.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error de lectura de minutas asociadas, contacte al administrador", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        adaptadorMiercoles=new AdaptadorListaProgramacion(this, preparacionesMiercoles);
        miRecyclerDiarioMi.setAdapter(adaptadorMiercoles);
        //Preparaciones del jueves
        preparacionesJueves = new ArrayList<>();
        if (!menuDias[3].equals("X") && !menuDias[3].equals("V")) {
            LinearLayoutManager linearLayoutManagerJ=new LinearLayoutManager(this);
            miRecyclerDiarioJ.setLayoutManager(linearLayoutManagerJ);
            miReferenciaPub = FirebaseDatabase.getInstance().getReference("minutas").child(menuDias[3]);
            miReferenciaPub.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                        preparacionesJueves.clear();
                        for (DataSnapshot miPrepara : dataSnapshot.getChildren()) {
                            String numeroPreparacion=miPrepara.getKey();
                            String nombrePreparacion=miPrepara.child("preparacion").getValue(String.class);
                            Preparacion miPreparacion=new Preparacion(numeroPreparacion,nombrePreparacion);
                            preparacionesJueves.add(miPreparacion);
                        }
                        adaptadorJueves.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error de lectura de minutas asociadas, contacte al administrador", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        adaptadorJueves=new AdaptadorListaProgramacion(this, preparacionesJueves);
        miRecyclerDiarioJ.setAdapter(adaptadorJueves);
        //Preparaciones del viernes
        preparacionesViernes = new ArrayList<>();
        if (!menuDias[4].equals("X") && !menuDias[4].equals("V")) {
            LinearLayoutManager linearLayoutManagerV=new LinearLayoutManager(this);
            miRecyclerDiarioV.setLayoutManager(linearLayoutManagerV);
            miReferenciaPub = FirebaseDatabase.getInstance().getReference("minutas").child(menuDias[4]);
            miReferenciaPub.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                        preparacionesViernes.clear();
                        for (DataSnapshot miPrepara : dataSnapshot.getChildren()) {
                            String numeroPreparacion=miPrepara.getKey();
                            String nombrePreparacion=miPrepara.child("preparacion").getValue(String.class);
                            Preparacion miPreparacion=new Preparacion(numeroPreparacion,nombrePreparacion);
                            preparacionesViernes.add(miPreparacion);
                        }
                        adaptadorViernes.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error de lectura de minutas asociadas, contacte al administrador", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        adaptadorViernes=new AdaptadorListaProgramacion(this, preparacionesViernes);
        miRecyclerDiarioV.setAdapter(adaptadorViernes);
    }

}
