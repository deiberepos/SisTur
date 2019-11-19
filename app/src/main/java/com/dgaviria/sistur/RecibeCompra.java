package com.dgaviria.sistur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dgaviria.sistur.adaptadores.AdaptadorListaRecibe;
import com.dgaviria.sistur.clases.AlimentoCompra;
import com.dgaviria.sistur.clases.AlimentoEntrega;
import com.dgaviria.sistur.clases.CapturaQR;
import com.dgaviria.sistur.clases.ComparadorAlimentoEntrega;
import com.dgaviria.sistur.clases.OtrosEntrega;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

public class RecibeCompra extends AppCompatActivity {
    int ano, mes, dia;
    TextView totalInfantes,conteoIngredientes;
    EditText campoFechaE;
    AdaptadorListaRecibe miAdaptadorEntrega;
    Spinner selectorSemanasE,selectorCDIE;
    String nombreSemanaE,nombreSemanaV,nombreCDIE,nombreCDIV,fechaEntrega,recibeRol,recibeUsuario;
    DatabaseReference miReferenciaCDIE,miReferenciaG,miReferenciaListaE,miReferenciaSemE,miReferenciaPob;
    ArrayAdapter<String> adaptadorSemanaE,adaptadorCDIE;
    ArrayList<String> listadoSemanasE, listadoCDIE;
    ArrayList<AlimentoEntrega> listaEntrega;
    RecyclerView miRecyclerListaEntrega;
    Calendar miCalendarioE;
    FloatingActionButton botonAprobar;
    int numInfantes,numIngredientes;
    private long totalcenso;
    Bundle recibeParametros;
    private static final int CODIGO_PETICION_CAMARA = 100;
    public Boolean entregaExiste;
    OtrosEntrega datosEntrega;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_recibe);
        referenciar();
        mostrarFechaE();
        selectorCDIE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adaptadorVista, View view, int i, long l) {
                nombreCDIE=adaptadorVista.getItemAtPosition(i).toString();
                lecturaListaCompras();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        selectorSemanasE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adaptadorVista, View view, int i, long l) {
                nombreSemanaE=adaptadorVista.getItemAtPosition(i).toString();
                lecturaListaCompras();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        botonAprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escanearQR(view);
            }
        });

    }

    private void escanearQR(View vista) {
        if (entregaExiste) {
            Toast.makeText(this, "No puede aprobar una lista que ya ha sido aprobada anteriormente", Toast.LENGTH_SHORT).show();
        }
        else{
            if (listaEntrega.size()<1)
                Toast.makeText(this, "No puede aprobar una lista vacía", Toast.LENGTH_SHORT).show();
            else {
                //verifica los permisos de la cámara
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CODIGO_PETICION_CAMARA);
                    return;
                }

                IntentIntegrator miIntegrador = new IntentIntegrator(RecibeCompra.this);
                miIntegrador.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                miIntegrador.setPrompt("Escaneando código de entrega");
                miIntegrador.setOrientationLocked(true);
                miIntegrador.setCameraId(0);
                miIntegrador.setBeepEnabled(true);
                miIntegrador.setBarcodeImageEnabled(false);
                miIntegrador.setCaptureActivity(CapturaQR.class);
                miIntegrador.initiateScan();
            }
        }
    }

    private void referenciar() {
        campoFechaE=findViewById(R.id.editFechaEntrega);
        miCalendarioE = Calendar.getInstance();
        ano = miCalendarioE.get(Calendar.YEAR);
        mes = miCalendarioE.get(Calendar.MONTH)+1;
        dia = miCalendarioE.get(Calendar.DAY_OF_MONTH);
        selectorSemanasE=findViewById(R.id.selectorSemanaE);
        selectorCDIE=findViewById(R.id.selectorCDIE);
        miRecyclerListaEntrega =findViewById(R.id.recyclerEntregas);
        totalInfantes=findViewById(R.id.txtTotalInfantesE);
        conteoIngredientes=findViewById(R.id.txtTotalIngredientesE);
        botonAprobar=findViewById(R.id.botonApruebaE);
        recibeParametros = getIntent().getExtras();
        recibeRol= recibeParametros.getString("rol");
        recibeUsuario = recibeParametros.getString("usuario");
        totalcenso=recibeParametros.getLong("total");
        llenarListaCDIE();
        llenarListaSemanasE();
    }

    private void mostrarFechaE() {
        String fechaAux=dia + "/" + mes + "/" + ano;
        campoFechaE.setText(fechaAux);
    }
    public void mostrarCalendarioE(View view) {
        Calendar miCalendario = new GregorianCalendar();//Calendar.getInstance();
        miCalendario.setTime(new Date());
        new DatePickerDialog(this, R.style.TemaCalendario, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                ano = year;
                mes = monthOfYear + 1;
                dia = dayOfMonth;
                mostrarFechaE();
            }
        }, miCalendario.get(Calendar.YEAR), miCalendario.get(Calendar.MONTH), miCalendario.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void lecturaListaCompras() {
        listaEntrega = new ArrayList<>();
        listaEntrega = lecturaEntrega();
        miAdaptadorEntrega = new AdaptadorListaRecibe(RecibeCompra.this, listaEntrega);
        miRecyclerListaEntrega.setAdapter(miAdaptadorEntrega);
        miRecyclerListaEntrega.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        miAdaptadorEntrega.notifyDataSetChanged();
    }

    private ArrayList<AlimentoEntrega> lecturaEntrega(){
        final ArrayList<AlimentoEntrega> lista=new ArrayList<>();
        entregaExiste=false;
        fechaEntrega=campoFechaE.getText().toString();
        numIngredientes=0;
        numInfantes=0;
        if (nombreCDIE!=null && !nombreCDIE.isEmpty() && nombreSemanaE!=null && !nombreSemanaE.isEmpty() && !fechaEntrega.isEmpty()){
            //Conteo de infantes asociados al CDI seleccionado
            numInfantes=0;
            miReferenciaPob = FirebaseDatabase.getInstance().getReference("poblacionCentros").child(nombreCDIE);
            miReferenciaPob.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildren() != null) {
                        if (dataSnapshot.child("totalcenso").getValue(Integer.class)!=null)
                            numInfantes=dataSnapshot.child("totalcenso").getValue(Integer.class);
                        else {
                            numInfantes=0;
                            Toast.makeText(RecibeCompra.this, "Error en el número de infantes del CDI", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            totalInfantes.setText(String.valueOf(numInfantes));
            //Verifica la existencia de la lista de entregas para el CDI en la semana seleccionada
            miReferenciaListaE = FirebaseDatabase.getInstance().getReference("entregas").child(nombreCDIE).child(nombreSemanaE);
            miReferenciaListaE.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) { //Si existen entregas para el CDI
                        if (dataSnapshot.getKey().equals(nombreSemanaE)) { //verifica si la semana de entrega es la que seleccionó
                            datosEntrega = new OtrosEntrega();
                            datosEntrega.setEntregadopor(dataSnapshot.child("entregadopor").getValue(String.class));
                            datosEntrega.setRecibidopor(dataSnapshot.child("recibidopor").getValue(String.class));
                            datosEntrega.setQuiencompra(dataSnapshot.child("quiencompra").getValue(String.class));
                            datosEntrega.setFechacompra(dataSnapshot.child("fechacompra").getValue(String.class));
                            datosEntrega.setFechaentrega(dataSnapshot.child("fechaentrega").getValue(String.class));
                            datosEntrega.setNombreCDI(dataSnapshot.child("nombreCDI").getValue(String.class));
                            for (DataSnapshot miIngrediente : dataSnapshot.getChildren()) {
                                if (!miIngrediente.getKey().equals("entregadopor") && !miIngrediente.getKey().equals("recibidopor")
                                        && !miIngrediente.getKey().equals("quiencompra") && !miIngrediente.getKey().equals("fechacompra")
                                        && !miIngrediente.getKey().equals("fechaentrega") && !miIngrediente.getKey().equals("nombreCDI")) {
                                    AlimentoEntrega ingrediente = miIngrediente.getValue(AlimentoEntrega.class);
                                    lista.add(ingrediente);
                                    Collections.sort(lista, new ComparadorAlimentoEntrega()); //ordena la lista por el nombre del ingrediente
                                }
                            }
                            numIngredientes = lista.size();
                            conteoIngredientes.setText(String.valueOf(numIngredientes));
                            totalInfantes.setText(String.valueOf(numInfantes));
                            entregaExiste = true;
                        }
                        miAdaptadorEntrega.notifyDataSetChanged();
                    }
                    else{ //no existe lista de entrega para esa semana y CDI
                        numIngredientes=0;
                        //Lectura de las compras que hacen parte de la semana, cuando no existe una lista de compras previa
                        listadoCDIE = new ArrayList<>();
                        miReferenciaG= FirebaseDatabase.getInstance().getReference("galeria").child(nombreSemanaE);
                        miReferenciaG.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getChildren() != null) {
                                    datosEntrega=new OtrosEntrega();
                                    datosEntrega.setNombreCDI(nombreCDIE);
                                    datosEntrega.setFechaentrega(fechaEntrega);
                                    datosEntrega.setRecibidopor(recibeUsuario);
                                    datosEntrega.setQuiencompra(dataSnapshot.child("quiencompra").getValue(String.class));
                                    for(DataSnapshot miIngrediente:dataSnapshot.getChildren()) {
                                        if (!miIngrediente.getKey().equals("itemscomprados") && !miIngrediente.getKey().equals("totalcompra")&& !miIngrediente.getKey().equals("quiencompra")) {
                                            AlimentoCompra ingredienteC = miIngrediente.getValue(AlimentoCompra.class);
                                            AlimentoEntrega ingredienteE=new AlimentoEntrega();

                                            datosEntrega.setFechacompra(ingredienteC.getFechacompra());

                                            ingredienteE.setNombresemana(nombreSemanaE);
                                            ingredienteE.setCodigo(ingredienteC.getCodigo());
                                            ingredienteE.setIngrediente(ingredienteC.getIngrediente());
                                            ingredienteE.setMedida(ingredienteC.getMedida());
                                            int cantidadEntregada,miCantidad;
                                            miCantidad=Integer.valueOf(ingredienteC.getCantidad());
                                            //if (numInfantes>12)
                                            cantidadEntregada=Math.round((float) ((miCantidad/totalcenso)*numInfantes));
                                            //else
                                            //cantidadEntregada=miCantidad;
                                            ingredienteE.setCantidadentregada(String.valueOf(cantidadEntregada));
                                            ingredienteE.setEstadoBueno(true); //asume que todos los ingredientes se entregan en buen estado
                                            ingredienteE.setEstadoRegular(false);
                                            ingredienteE.setEstadoMalo(false);
                                            lista.add(ingredienteE);
                                            Collections.sort(lista, new ComparadorAlimentoEntrega()); //ordena la lista por el nombre del ingrediente
                                        }
                                    }
                                    numIngredientes=lista.size();
                                    conteoIngredientes.setText(String.valueOf(numIngredientes));
                                    totalInfantes.setText(String.valueOf(numInfantes));
                                    entregaExiste=false; //la entrega NO existe en la BD

                                    miAdaptadorEntrega.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error de lectura de las compras, contacte al administrador", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
            Toast.makeText(this, "Debes seleccionar todos los datos para continuar", Toast.LENGTH_SHORT).show();
        return lista;
    }

    private void llenarListaCDIE() {
        listadoCDIE=new ArrayList<>();
        miReferenciaCDIE= FirebaseDatabase.getInstance().getReference("Centros");
        miReferenciaCDIE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildren() != null) {
                    for (DataSnapshot miCentro : dataSnapshot.getChildren()) {
                        listadoCDIE.add(miCentro.getKey());
                    }
                    adaptadorCDIE = new ArrayAdapter<>(RecibeCompra.this, android.R.layout.simple_spinner_item, listadoCDIE);
                    adaptadorCDIE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    selectorCDIE.setAdapter(adaptadorCDIE);
                    //selectorCDIE.setSelected(false);
                    selectorCDIE.setSelection(0, true); //selecciona el primer elemento del spinner
                    adaptadorCDIE.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Error de lectura de CDI, contacte al administrador", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void llenarListaSemanasE() {
        listadoSemanasE=new ArrayList<>();
        miReferenciaSemE = FirebaseDatabase.getInstance().getReference("semanas");
        miReferenciaSemE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildren() != null) {
                    for (DataSnapshot miSemana : dataSnapshot.getChildren()) {
                        listadoSemanasE.add(miSemana.getKey());
                    }
                    adaptadorSemanaE = new ArrayAdapter<>(RecibeCompra.this, android.R.layout.simple_spinner_item, listadoSemanasE);
                    adaptadorSemanaE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    selectorSemanasE.setAdapter(adaptadorSemanaE);
                    //selectorSemanasE.setSelected(false);
                    selectorSemanasE.setSelection(0, true); //selecciona el primer elemento del spinner
                    adaptadorSemanaE.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Error de lectura de semanas, contacte al administrador", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult codigoLeido = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(codigoLeido != null) {
            if(codigoLeido.getContents() == null) {
                Toast.makeText(this, "Error en la lectura del código, entrega no aprobada", Toast.LENGTH_LONG).show();

            } else {
                //Toast.makeText(this, "Código leído: " +"\n" +codigoLeido.getContents(), Toast.LENGTH_LONG).show();
                guardarAprobacion(codigoLeido.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void guardarAprobacion(String datosLeidos) {
        //descompone los tres items del QR: nombre de la semana, nombre del CDI, nombre del usuario que recibe
        int indice=0,cuenta=0,pos1=0,pos2=0,pos=0;
        while (indice != -1){
            indice=datosLeidos.indexOf("|",pos);
            cuenta++;
            if (indice != -1){
                switch (cuenta){
                    case 1:
                        pos1=indice;
                        nombreSemanaV=datosLeidos.substring(0, pos1);
                        pos=indice+1;
                        break;
                    case 2:
                        pos2=indice;
                        nombreCDIV=datosLeidos.substring(pos1+1, pos2);
                        pos=indice+1;
                        break;
                }
            }
        }
        if (pos2>0){
            int tamanoDatos=datosLeidos.length();
            String nombreEntrega=datosLeidos.substring(pos2+1,tamanoDatos);
            datosEntrega.setEntregadopor(nombreEntrega);
        }
        if (!nombreSemanaV.isEmpty() && !nombreCDIV.isEmpty() && !datosEntrega.getEntregadopor().isEmpty()) {
            //los tres datos fueron leidos exitosamente
            if (entregaExiste) {
                Toast.makeText(this, "No se puede realizar la recepción ya que la lista a entregar ya ha sido aprobada anteriormente", Toast.LENGTH_SHORT).show();
                /*if (nombreSemanaE.equals(nombreSemanaV) && nombreCDIE.equals(nombreCDIV)) {
                    //si la entrega de compras existe la actualiza
                    miReferenciaListaE = FirebaseDatabase.getInstance().getReference("entregas").child(nombreCDIE).child(nombreSemanaE);
                    Map<String, Object> valoresNivel1 = new HashMap<String, Object>();
                    valoresNivel1.put("recibidopor", datosEntrega.getRecibidopor());
                    valoresNivel1.put("entregadopor", datosEntrega.getEntregadopor());
                    valoresNivel1.put("fechacompra", datosEntrega.getFechacompra());
                    valoresNivel1.put("fechaentrega", datosEntrega.getFechaentrega());
                    miReferenciaListaE.updateChildren(valoresNivel1);
                    Map<String, Object> valoresNivel2 = new HashMap<String, Object>();
                    for (int numItem = 0; numItem < listaEntrega.size(); numItem++) {
                        String codigoAlimento = listaEntrega.get(numItem).getCodigo();
                        valoresNivel2.put("/" + codigoAlimento + "/estadoBueno", listaEntrega.get(numItem).getEstadoBueno());
                        valoresNivel2.put("/" + codigoAlimento + "/estadoRegular", listaEntrega.get(numItem).getEstadoRegular());
                        valoresNivel2.put("/" + codigoAlimento + "/estadoMalo", listaEntrega.get(numItem).getEstadoMalo());
                    }
                    miReferenciaListaE.updateChildren((actualizaNivel2));
                    Toast.makeText(this, "La lista de entrega se ha actualizado", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                    Toast.makeText(this, "No puedes recibir estos productos, no corresponden con el código verificador", Toast.LENGTH_SHORT).show();*/
            }
            else{
                //crear la llave con sus hijos con los datos nuevos
                miReferenciaListaE = FirebaseDatabase.getInstance().getReference("entregas").child(nombreCDIE).child(nombreSemanaE);
                miReferenciaListaE.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()){
                            miReferenciaListaE.child("nombreCDI").setValue(datosEntrega.getNombreCDI());
                            miReferenciaListaE.child("fechacompra").setValue(datosEntrega.getFechacompra());
                            miReferenciaListaE.child("fechaentrega").setValue(datosEntrega.getFechaentrega());
                            miReferenciaListaE.child("entregadopor").setValue(datosEntrega.getEntregadopor());
                            miReferenciaListaE.child("recibidopor").setValue(datosEntrega.getRecibidopor());
                            miReferenciaListaE.child("quiencompra").setValue(datosEntrega.getQuiencompra());
                            for (int numItem = 0; numItem < listaEntrega.size(); numItem++) {
                                String codigoAlimento = listaEntrega.get(numItem).getCodigo();
                                AlimentoEntrega miAlimento=new AlimentoEntrega();
                                miAlimento.setCodigo(codigoAlimento);
                                miAlimento.setNombresemana(listaEntrega.get(numItem).getNombresemana());
                                miAlimento.setIngrediente(listaEntrega.get(numItem).getIngrediente());
                                miAlimento.setMedida(listaEntrega.get(numItem).getMedida());
                                miAlimento.setCantidadentregada(listaEntrega.get(numItem).getCantidadentregada());
                                miAlimento.setEstadoBueno(listaEntrega.get(numItem).getEstadoBueno());
                                miAlimento.setEstadoRegular(listaEntrega.get(numItem).getEstadoRegular());
                                miAlimento.setEstadoMalo(listaEntrega.get(numItem).getEstadoMalo());
                                miReferenciaListaE.child(codigoAlimento).setValue(miAlimento);
                            }
                            Toast.makeText(getApplicationContext(), "Se ha aprobado exitosamente la entrega de la lista de compras", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
    }
}
