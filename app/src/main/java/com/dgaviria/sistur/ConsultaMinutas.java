package com.dgaviria.sistur;

import androidx.annotation.NonNull;
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
import com.dgaviria.sistur.clases.GrupoAlimento;
import com.dgaviria.sistur.clases.GrupoMinuta;
import com.dgaviria.sistur.clases.Minutas;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

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
        setContentView(R.layout.consulta_minutas);

        referenciar();
        llenarListaMinutas();
        spnMinutas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int item, long l) {
                nombreMinuta=adapterView.getItemAtPosition(item).toString();
                llenarListaPreparaciones();
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
                if (dataSnapshot.getChildren()!=null){
                    for (DataSnapshot miMinuta:dataSnapshot.getChildren()){
                        listaMinutas.add(miMinuta.getKey());
                    }
                    adaptadorMinuta=new ArrayAdapter<>(ConsultaMinutas.this,android.R.layout.simple_spinner_item,listaMinutas);
                    adaptadorMinuta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnMinutas.setAdapter(adaptadorMinuta);
                    spnMinutas.setSelection(0, true); //selecciona el primer elemento del spinner
                    adaptadorMinuta.notifyDataSetChanged();
                    Toast.makeText(ConsultaMinutas.this, "Seleccione la minuta a consultar", Toast.LENGTH_SHORT).show();
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
        listaMinutas=new ArrayList<>();
        listaAlimentos=new ArrayList<>();
        vistaLista = findViewById(R.id.listaPreparacion);
        preparaciones = new SparseArray<>();
    }

    public void llenarListaPreparaciones() {
        itemPrepara=0;
        miReferenciaMin= FirebaseDatabase.getInstance().getReference("minutas").child(nombreMinuta);
        miReferenciaMin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildren() != null) {
                    for (DataSnapshot miPrepara:dataSnapshot.getChildren()){
                        String codPrepara=miPrepara.getKey();
                        String nombrePreparacion=miPrepara.child("preparacion").getValue(String.class);
                        String procedPreparacion=miPrepara.child("procedimiento").getValue(String.class);
                        String datosPreparacion="Preparación ("+codPrepara+"): "+nombrePreparacion;
                        String datosProcedimiento="Procedimiento: "+procedPreparacion;
                        GrupoMinuta miGrupo=new GrupoMinuta(datosPreparacion,datosProcedimiento);

                        for (DataSnapshot miAlimento:miPrepara.getChildren()) {
                            if (!miAlimento.getKey().equals("preparacion") && !miAlimento.getKey().equals("procedimiento")) {
                                Minutas miMinuta = miAlimento.getValue(Minutas.class);
                                //Construye la lista de ingredientes con sus detalles
                                String datosIngrediente = miAlimento.getKey()+"-Ingrediente ("+miMinuta.getCodigo() + "): " + miMinuta.getReal();
                                String datosInformacion = "Cantidad: " + miMinuta.getCantidad() + " " + miMinuta.getUnidad().toLowerCase() +
                                        ", Total Cantidad: " + miMinuta.getTotal();
                                GrupoAlimento miIngrediente=new GrupoAlimento(datosIngrediente,datosInformacion);
                                miGrupo.grupoPrepara.add(miIngrediente);
                            }
                        }
                        preparaciones.append(itemPrepara,miGrupo);
                        itemPrepara++;
                    }
                    miAdaptadorLista= new AdaptadorListaMinutas(ConsultaMinutas.this,preparaciones);
                    vistaLista.setAdapter(miAdaptadorLista);
                } else {
                    Toast.makeText(getApplicationContext(), "Error de lectura de minutas asociadas, contacte al administrador", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
