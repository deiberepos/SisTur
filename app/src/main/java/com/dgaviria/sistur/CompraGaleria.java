package com.dgaviria.sistur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.dgaviria.sistur.adaptadores.AdaptadorListaCompra;
import com.dgaviria.sistur.clases.AlimentoCompra;
import com.dgaviria.sistur.clases.ComparadorAlimentoCompra;
import com.dgaviria.sistur.clases.Minutas;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class CompraGaleria extends AppCompatActivity {
    int año, mes, dia,sumaTotal,conteoTotal,conteoParcial;
    EditText campoFecha;
    AdaptadorListaCompra miAdaptadorCompra;
    Spinner selectorSemanas;
    String nombreSemana;
    DatabaseReference miReferenciaMin,miReferenciaSem,miReferenciaLista;
    ArrayAdapter<String> adaptadorSemana;
    ArrayList<String> listadoSemanas, listadoMinutas;
    ArrayList<AlimentoCompra> listaGaleria;
    public static String cantidadReal;
    Bundle recibeParametros;
    public static long resultadoCantidad, auxcantidad, totalInfantes;
    RecyclerView miRecyclerListaCompra;
    Button botonGuardar,botonCalcular;
    Calendar miCalendario;
    Boolean existeLista=false;
    int numItem;
    TextView totalCompra,totalConteo,parcialConteo;
    public static String recibeRol, recibeUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_compras);

        referenciar();
        mostrarFecha();
        Toast.makeText(getApplicationContext(),"El total de niños es: " + totalInfantes,Toast.LENGTH_LONG).show();
        botonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizaConteos();
            }
        });
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizaConteos();
                guardarLista();
            }
        });
        selectorSemanas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adaptadorVista, View view, int i, long l) {
                nombreSemana=adaptadorVista.getItemAtPosition(i).toString();
                lecturaMinutas();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void referenciar() {
        campoFecha=findViewById(R.id.editFechaCompra);
        miCalendario = Calendar.getInstance();
        año = miCalendario.get(Calendar.YEAR);
        mes = miCalendario.get(Calendar.MONTH)+1;
        dia = miCalendario.get(Calendar.DAY_OF_MONTH);
        selectorSemanas=findViewById(R.id.selectorSemana);
        miRecyclerListaCompra=findViewById(R.id.recyclerCompras);
        botonGuardar=findViewById(R.id.botonGuardaGal);
        botonCalcular=findViewById(R.id.botonCalcular);
        totalConteo=findViewById(R.id.txtTotalIngredientes);
        parcialConteo=findViewById(R.id.txtSubconteoIngredientes);
        totalCompra=findViewById(R.id.txtValorCompra);
        recibeParametros = getIntent().getExtras();
        totalInfantes = recibeParametros.getLong("total");
        recibeRol= recibeParametros.getString("rol");
        recibeUsuario = recibeParametros.getString("usuario");
        llenarListaSemanas();
    }

    private void mostrarFecha() {
        campoFecha.setText(dia + "/" + mes + "/" + año);
    }
    public void mostrarCalendario(View view) {
        Calendar miCalendario = new GregorianCalendar();//Calendar.getInstance();
        miCalendario.setTime(new Date());
        new DatePickerDialog(this, R.style.TemaCalendario, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                año = year;
                mes = monthOfYear + 1;
                dia = dayOfMonth;
                mostrarFecha();
            }
        }, miCalendario.get(Calendar.YEAR), miCalendario.get(Calendar.MONTH), miCalendario.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void actualizaConteos() {
        conteoParcial=miAdaptadorCompra.conteoParcial();
        conteoTotal=miAdaptadorCompra.conteoTotal();
        sumaTotal=miAdaptadorCompra.sumaTotal();

        parcialConteo.setText(String.valueOf(conteoParcial));
        totalConteo.setText(String.valueOf(conteoTotal));
        totalCompra.setText(String.valueOf(sumaTotal));
    }

    private void lecturaMinutas(){
        //Verifica la existencia de la lista de compras para la semana
        miReferenciaLista= FirebaseDatabase.getInstance().getReference("galeria").child(nombreSemana);
        miReferenciaLista.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){ //Si existe
                    existeLista=true;
                    listaGaleria=new ArrayList<AlimentoCompra>();
                    for(DataSnapshot miIngrediente:dataSnapshot.getChildren()) {
                        if (!miIngrediente.getKey().equals("itemscomprados") && !miIngrediente.getKey().equals("totalcompra") && !miIngrediente.getKey().equals("quiencompra")) {
                            AlimentoCompra ingrediente = miIngrediente.getValue(AlimentoCompra.class);
                            listaGaleria.add(ingrediente);
                            Collections.sort(listaGaleria, new ComparadorAlimentoCompra()); //ordena la lista por el nombre del ingrediente
                        }
                    }
                    miAdaptadorCompra = new AdaptadorListaCompra(CompraGaleria.this, listaGaleria);
                    miRecyclerListaCompra.setAdapter(miAdaptadorCompra);
                    miRecyclerListaCompra.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    miAdaptadorCompra.notifyDataSetChanged();
                    actualizaConteos();
                }
                else{ //si no existe realiza la lectura de las minutas asociadas en la semana seleccionada
                    //Lectura de las minutas que hacen parte de la semana, cuando no existe una lista de compras previa
                    existeLista=false;
                    listadoMinutas = new ArrayList<String>();
                    miReferenciaSem = FirebaseDatabase.getInstance().getReference("semanas").child(nombreSemana);
                    miReferenciaSem.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                                Boolean hayMinutas=false;
                                String minuta1 = dataSnapshot.child("minuta1").getValue(String.class);
                                String minuta2 = dataSnapshot.child("minuta2").getValue(String.class);
                                String minuta3 = dataSnapshot.child("minuta3").getValue(String.class);
                                String minuta4 = dataSnapshot.child("minuta4").getValue(String.class);
                                String minuta5 = dataSnapshot.child("minuta5").getValue(String.class);
                                if (!minuta1.equals("X") && !minuta1.equals("V")) {
                                    listadoMinutas.add(minuta1);
                                    hayMinutas=true;
                                }
                                if (!minuta2.equals("X") && !minuta2.equals("V")) {
                                    listadoMinutas.add(minuta2);
                                    hayMinutas=true;
                                }
                                if (!minuta3.equals("X") && !minuta3.equals("V")) {
                                    listadoMinutas.add(minuta3);
                                    hayMinutas=true;
                                }
                                if (!minuta4.equals("X") && !minuta4.equals("V")) {
                                    listadoMinutas.add(minuta4);
                                    hayMinutas=true;
                                }
                                if (!minuta5.equals("X") && !minuta5.equals("V")) {
                                    listadoMinutas.add(minuta5);
                                }
                                if (!hayMinutas)
                                    Toast.makeText(CompraGaleria.this, "No hay minutas asociadas en esta semana", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error de lectura de minutas, contacte al administrador", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    listarIngredientes();
                    actualizaConteos();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void llenarListaSemanas() {
        listadoSemanas=new ArrayList<String>();
        miReferenciaSem = FirebaseDatabase.getInstance().getReference("semanas");
        miReferenciaSem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    for (DataSnapshot miSemana : dataSnapshot.getChildren()) {
                        listadoSemanas.add(miSemana.getKey());
                    }
                    adaptadorSemana = new ArrayAdapter<String>(CompraGaleria.this, android.R.layout.simple_spinner_item, listadoSemanas);
                    adaptadorSemana.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    selectorSemanas.setAdapter(adaptadorSemana);
                    //selectorSemanas.setSelected(false);
                    selectorSemanas.setSelection(0, true); //selecciona el primer elemento del spinner
                    adaptadorSemana.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Error de lectura de semanas, contacte al administrador", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listarIngredientes() {
        listaGaleria=new ArrayList<AlimentoCompra>();
        listaGaleria=lecturaIngredientes();
        miAdaptadorCompra=new AdaptadorListaCompra(this,listaGaleria);
        miRecyclerListaCompra.setAdapter(miAdaptadorCompra);
        miRecyclerListaCompra.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        miAdaptadorCompra.notifyDataSetChanged();
    }

    private ArrayList<AlimentoCompra> lecturaIngredientes() {
        final ArrayList<AlimentoCompra> lista=new ArrayList<>();
        numItem=0;
        miReferenciaMin = FirebaseDatabase.getInstance().getReference("minutas");
        miReferenciaMin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    for(DataSnapshot miMinuta:dataSnapshot.getChildren()){
                        for (int item = 0; item< listadoMinutas.size(); item++){
                            if (miMinuta.getKey().equals(listadoMinutas.get(item))){
                                for(DataSnapshot miPrepara:miMinuta.getChildren()){
                                    for(DataSnapshot miAlimento:miPrepara.getChildren()){
                                        if (!miAlimento.getKey().equals("preparacion") && !miAlimento.getKey().equals("procedimiento")) {
                                            Boolean guarda=true;
                                            Minutas alimento=miAlimento.getValue(Minutas.class);
                                            AlimentoCompra ingrediente=new AlimentoCompra();
                                            if (lista.size()>0) {
                                                for (int numItem = 0; numItem < lista.size(); numItem++) {
                                                    if (alimento.getReal().equals(lista.get(numItem).getIngrediente())) {
                                                        String textoAntes=lista.get(numItem).getCantidad();
                                                        String textoMas=alimento.getTotal();
                                                        textoAntes=textoAntes.replace(",",".");
                                                        textoMas=textoMas.replace(",",".");
                                                        float cantAntes=Float.parseFloat(textoAntes);
                                                        float cantMas=Float.parseFloat(textoMas);
                                                        //cantMas = cantMas + cantAntes;
                                                        float resultado = (cantMas/12)*totalInfantes;
                                                        cantMas = resultado + cantAntes;
                                                        int valorSuma=Math.round(cantMas);
                                                        lista.get(numItem).setCantidad(String.valueOf(valorSuma));
                                                        //miAdaptadorCompra.notifyDataSetChanged();
                                                        guarda=false;
                                                        break;
                                                    } else {
                                                        guarda=true;
                                                    }
                                                }
                                            }
                                            if (guarda){
                                                ingrediente.setIngrediente(alimento.getReal());
                                                ingrediente.setCodigo(alimento.getCodigo());
                                                ingrediente.setMedida(alimento.getUnidad());
                                                //ingrediente.setCantidad(alimento.getTotal());
                                                auxcantidad = Long.parseLong(alimento.getTotal());
                                                resultadoCantidad = (auxcantidad/12)*totalInfantes;
                                                cantidadReal = Long.toString(resultadoCantidad);
                                                ingrediente.setCantidad(cantidadReal);
                                                ingrediente.setValorcompra("0");
                                                ingrediente.setTotal("0");
                                                lista.add(ingrediente);
                                                Collections.sort(lista, new ComparadorAlimentoCompra());

                                                totalConteo.setText(String.valueOf(lista.size()));
                                            }
                                        }
                                    }
                                }
                            }
                            miAdaptadorCompra.notifyDataSetChanged();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error de lectura de ingredientes, contacte al administrador", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return lista;
    }

   private void guardarLista(){
        if (existeLista) {
            //si la lista de compras existe la actualiza
            miReferenciaLista=FirebaseDatabase.getInstance().getReference("galeria").child(nombreSemana);
            Map<String,Object> actualizaNivel1=new HashMap<String,Object>();
            actualizaNivel1.put("totalcompra",sumaTotal);
            actualizaNivel1.put("itemscomprados",conteoTotal);
            miReferenciaLista.updateChildren(actualizaNivel1);
            miReferenciaLista=FirebaseDatabase.getInstance().getReference("galeria").child(nombreSemana);
            Map<String,Object> actualizaNivel2=new HashMap<String,Object>();
            for (int numItem = 0; numItem < listaGaleria.size(); numItem++) {
                String codigoAlimento = listaGaleria.get(numItem).getCodigo();
                actualizaNivel2.put("/"+codigoAlimento+"/fechacompra",String.valueOf(campoFecha.getText()));
                actualizaNivel2.put("/"+codigoAlimento+"/total",listaGaleria.get(numItem).getTotal());
                actualizaNivel2.put("/"+codigoAlimento+"/porentregar",listaGaleria.get(numItem).getTotal());
                actualizaNivel2.put("/"+codigoAlimento+"/valorcompra",listaGaleria.get(numItem).getValorcompra());
            }
            miReferenciaLista.updateChildren((actualizaNivel2));
            Toast.makeText(this, "Lista de compras actualizada", Toast.LENGTH_SHORT).show();
        }
        else{
            //crear la llave con sus hijos con los datos nuevos
            miReferenciaLista = FirebaseDatabase.getInstance().getReference("galeria").child(nombreSemana);
            Map<String,Object> valoresNivel1=new HashMap<String,Object>();
            valoresNivel1.put("totalcompra",sumaTotal);
            valoresNivel1.put("itemscomprados",conteoTotal);
            valoresNivel1.put("quiencompra",recibeUsuario);
            miReferenciaLista.updateChildren(valoresNivel1);
            miReferenciaLista = FirebaseDatabase.getInstance().getReference("galeria").child(nombreSemana);
            Map<String,Object> valoresNivel2=new HashMap<String,Object>();
            for (int numItem = 0; numItem < listaGaleria.size(); numItem++) {
                String codigoAlimento = listaGaleria.get(numItem).getCodigo();
                valoresNivel1.put("/"+codigoAlimento+"/codigo",listaGaleria.get(numItem).getIngrediente());
                valoresNivel1.put("/"+codigoAlimento+"/fechacompra",String.valueOf(campoFecha.getText()));
                valoresNivel1.put("/"+codigoAlimento+"/medida",listaGaleria.get(numItem).getMedida());
                valoresNivel1.put("/"+codigoAlimento+"/valorcompra",listaGaleria.get(numItem).getValorcompra());
                valoresNivel1.put("/"+codigoAlimento+"/total",listaGaleria.get(numItem).getTotal());
                valoresNivel1.put("/"+codigoAlimento+"/cantidad",listaGaleria.get(numItem).getCantidad());
                valoresNivel1.put("/"+codigoAlimento+"/entregado","0");
                valoresNivel1.put("/"+codigoAlimento+"/cantidad",listaGaleria.get(numItem).getCantidad());
                valoresNivel1.put("/"+codigoAlimento+"/porentregar",listaGaleria.get(numItem).getCantidad());
            }
            miReferenciaLista.updateChildren(valoresNivel2);
            Toast.makeText(this, "Lista de compras guardada", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}