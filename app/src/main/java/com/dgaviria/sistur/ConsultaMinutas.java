package com.dgaviria.sistur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dgaviria.sistur.adaptadores.AdaptadorListaMinutas;
import com.dgaviria.sistur.clases.Calendario;
import com.dgaviria.sistur.clases.GrupoMinuta;
import com.dgaviria.sistur.clases.Minutas;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.stream.Stream;

public class ConsultaMinutas extends Activity {
    SparseArray<GrupoMinuta> preparaciones;
    DatabaseReference miReferenciaMin;
    ArrayAdapter<String> adaptadorMinuta;
    ArrayList<String> listaMinutas,listaAlimentos;
    String nombreMinuta="";
    Spinner spnMinutas;
    int itemPrepara;
    ExpandableListView vistaLista;
    AdaptadorListaMinutas miAdaptadorLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minutas);

        referenciar();
        llenarListaMinutas();
        spnMinutas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int item, long l) {
                nombreMinuta=adapterView.getItemAtPosition(item).toString();
                llenarListaPreparaciones();
                miAdaptadorLista= new AdaptadorListaMinutas(ConsultaMinutas.this,preparaciones);
                vistaLista.setAdapter(miAdaptadorLista);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void llenarListaMinutas() {
        miReferenciaMin= FirebaseDatabase.getInstance().getReference("minutas");
        miReferenciaMin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot!=null && dataSnapshot.getChildren()!=null){
                    for (DataSnapshot miMinuta:dataSnapshot.getChildren()){
                        listaMinutas.add(miMinuta.getKey());
                    }
                    adaptadorMinuta=new ArrayAdapter<String>(ConsultaMinutas.this,android.R.layout.simple_spinner_item,listaMinutas);

                    adaptadorMinuta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spnMinutas.setAdapter(adaptadorMinuta);
                    spnMinutas.setSelected(false);
                    spnMinutas.setSelection(0, false); //no selecciona un elemento del spinner
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error de lectura de minutas, contacte al administrador",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void referenciar() {
        spnMinutas=findViewById(R.id.spinMinuta);
        listaMinutas=new ArrayList<String>();
        listaAlimentos=new ArrayList<String>();
        vistaLista = findViewById(R.id.listaPreparacion);
        preparaciones = new SparseArray<GrupoMinuta>();
    }

    public void llenarListaPreparaciones() {
        itemPrepara=0;
        miReferenciaMin= FirebaseDatabase.getInstance().getReference("minutas").child(nombreMinuta);
        miReferenciaMin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    for (DataSnapshot miPrepara:dataSnapshot.getChildren()){
                        String codPrepara=miPrepara.getKey();
                        String nombrePreparacion=miPrepara.child("preparacion").getValue(String.class);
                        String procedPreparacion=miPrepara.child("procedimiento").getValue(String.class);
                        String datosPreparacion=codPrepara+": "+nombrePreparacion+"\n"+"Procedimiento: "+procedPreparacion;
                        GrupoMinuta miGrupo=new GrupoMinuta(datosPreparacion);

                        for (DataSnapshot miAlimento:miPrepara.getChildren()) {
                            if (!miAlimento.getKey().equals("preparacion") && !miAlimento.getKey().equals("procedimiento")) {
                                Minutas miMinuta = miAlimento.getValue(Minutas.class);
                                //Construye la lista de ingredientes con sus detalles
                                String datosIngrediente = miMinuta.getCodigo() + ": " + miMinuta.getReal() + "\n" + "Cant.: " + miMinuta.getCantidad() + " " + miMinuta.getUnidad().toLowerCase() +
                                        ", Total Cant.: " + miMinuta.getTotal();
                                miGrupo.grupoPrepara.add(datosIngrediente);
                            }
                        }
                        preparaciones.append(itemPrepara,miGrupo);
                        itemPrepara++;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error de lectura de minutas asociadas, contacte al administrador", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
