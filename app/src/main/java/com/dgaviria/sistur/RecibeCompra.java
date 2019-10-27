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
import android.widget.Button;
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
    int año, mes, dia;
    TextView totalInfantes,conteoIngredientes;
    EditText campoFechaE;
    AdaptadorListaRecibe miAdaptadorEntrega;
    Spinner selectorSemanasE,selectorCDIE;
    String nombreSemanaE,nombreSemanaV,nombreCDIE,nombreCDIV,fechaEntrega,recibeRol,recibeUsuario,nombreEntrega;
    DatabaseReference miReferenciaCDI,miReferenciaSemC,miReferenciaLista,miReferenciaSemE,miReferenciaPob;
    ArrayAdapter<String> adaptadorSemanaE,adaptadorCDIE;
    ArrayList<String> listadoSemanasE, listadoCDIE;
    ArrayList<AlimentoEntrega> listaEntrega;
    RecyclerView miRecyclerListaEntrega;
    Calendar miCalendarioE;
    Button botonAprobar;
    int numInfantes,numIngredientes;
    Bundle recibeParametros;
    private static final int CODIGO_PETICION_CAMARA = 100;

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
                lecturaCDI();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        selectorSemanasE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adaptadorVista, View view, int i, long l) {
                nombreSemanaE=adaptadorVista.getItemAtPosition(i).toString();
                lecturaCDI();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        botonAprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verificaChequeo())
                    escanearQR(view);
            }
        });

    }

    private boolean verificaChequeo() {
        //verifica si chequeo cada alimento recibido
        for (int indiceIngrediente=0;indiceIngrediente<listaEntrega.size();indiceIngrediente++){
            if (listaEntrega.get(indiceIngrediente).getEstadoBueno() && listaEntrega.get(indiceIngrediente).getEstadoRegular()
                    && listaEntrega.get(indiceIngrediente).getEstadoMalo()){
                Toast.makeText(this, "Debe marcar el estado de cada uno de los alimentos que va a recibir", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void escanearQR(View vista) {
        //verifica los permisos de la cámara
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CODIGO_PETICION_CAMARA);
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

    private void referenciar() {
        campoFechaE=findViewById(R.id.editFechaEntrega);
        miCalendarioE = Calendar.getInstance();
        año = miCalendarioE.get(Calendar.YEAR);
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
        llenarListaCDIE();
        llenarListaSemanasE();
    }

    private void mostrarFechaE() {
        campoFechaE.setText(dia + "/" + mes + "/" + año);
    }
    public void mostrarCalendarioE(View view) {
        Calendar miCalendario = new GregorianCalendar();//Calendar.getInstance();
        miCalendario.setTime(new Date());
        new DatePickerDialog(this, R.style.TemaCalendario, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                año = year;
                mes = monthOfYear + 1;
                dia = dayOfMonth;
                mostrarFechaE();
            }
        }, miCalendario.get(Calendar.YEAR), miCalendario.get(Calendar.MONTH), miCalendario.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void lecturaCDI(){
        final ArrayList<AlimentoEntrega> lista=new ArrayList<>();
        fechaEntrega=campoFechaE.getText().toString();
        numIngredientes=0;
        if (nombreCDIE!=null && !nombreCDIE.isEmpty() && nombreSemanaE!=null && !nombreSemanaE.isEmpty() && fechaEntrega!=null && !fechaEntrega.isEmpty()){
            //Verifica la existencia de la lista de entregas para el CDI en la semana seleccionada
            miReferenciaLista= FirebaseDatabase.getInstance().getReference("entregas").child(nombreCDIE);
            miReferenciaLista.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){ //Si existen entregas para el CDI
                        for (DataSnapshot miSemana:dataSnapshot.getChildren()){
                            if (miSemana.getValue(String.class).equals(nombreSemanaE)){ //verifica si la semana de entrega es la que seleccionó
                                listaEntrega =new ArrayList<AlimentoEntrega>();
                                for (DataSnapshot miAlimento : dataSnapshot.getChildren()){
                                    if (!miAlimento.getKey().equals("totalcenso")){
                                        AlimentoEntrega ingrediente = miAlimento.getValue(AlimentoEntrega.class);
                                        listaEntrega.add(ingrediente);
                                        Collections.sort(listaEntrega, new ComparadorAlimentoEntrega()); //ordena la lista por el nombre del ingrediente
                                    }
                                    else{
                                        if (miAlimento.getValue(Integer.class)!=null)
                                            numInfantes=miAlimento.getValue(Integer.class);
                                        else
                                            numInfantes=0;
                                    }
                                }
                                totalInfantes.setText(String.valueOf(numInfantes));
                                numIngredientes=listaEntrega.size();
                                miAdaptadorEntrega = new AdaptadorListaRecibe(RecibeCompra.this, listaEntrega);
                                miRecyclerListaEntrega.setAdapter(miAdaptadorEntrega);
                                miRecyclerListaEntrega.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                miAdaptadorEntrega.notifyDataSetChanged();
                                conteoIngredientes.setText(String.valueOf(numIngredientes));
                            }
                        }
                    }
                    else{ //si no existe entrega para el CDI en la semana realiza la creación de una entrega de acuerdo con la lista de compras asociada a esa semana seleccionada
                        //Conteo de infantes asociados al CDI seleccionado
                        numInfantes=0;
                        miReferenciaPob = FirebaseDatabase.getInstance().getReference("poblacionCentros").child(nombreCDIE);
                        miReferenciaPob.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
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
                        numIngredientes=0;
                        //Lectura de las compras que hacen parte de la semana, cuando no existe una lista de compras previa
                        listadoCDIE = new ArrayList<String>();
                        miReferenciaSemC = FirebaseDatabase.getInstance().getReference("galeria").child(nombreSemanaE);
                        miReferenciaSemC.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                                    listaEntrega=new ArrayList<AlimentoEntrega>();
                                    for(DataSnapshot miIngrediente:dataSnapshot.getChildren()) {
                                        if (!miIngrediente.getKey().equals("itemscomprados") && !miIngrediente.getKey().equals("totalcompra")&& !miIngrediente.getKey().equals("quiencompra")) {
                                            AlimentoCompra ingredienteC = miIngrediente.getValue(AlimentoCompra.class);
                                            AlimentoEntrega ingredienteE=new AlimentoEntrega();
                                            ingredienteE.setNombresemana(nombreSemanaE);
                                            ingredienteE.setNombreCDI(nombreCDIE);
                                            ingredienteE.setFechacompra(ingredienteC.getFechacompra());
                                            ingredienteE.setFechaentrega(fechaEntrega);
                                            ingredienteE.setCodigo(ingredienteC.getCodigo());
                                            ingredienteE.setIngrediente(ingredienteC.getIngrediente());
                                            ingredienteE.setMedida(ingredienteC.getMedida());
                                            int cantidadEntregada,miCantidad;
                                            miCantidad=Integer.valueOf(ingredienteC.getCantidad());
                                            //if (numInfantes>12)
                                                cantidadEntregada=Math.round((float) ((numInfantes/12.0)*miCantidad));
                                            //else
                                                //cantidadEntregada=miCantidad;
                                            ingredienteE.setCantidadentregada(String.valueOf(cantidadEntregada));
                                            ingredienteE.setEstadoBueno(false);
                                            ingredienteE.setEstadoRegular(false);
                                            ingredienteE.setEstadoMalo(false);
                                            ingredienteE.setRecibidopor("Pendiente");
                                            listaEntrega.add(ingredienteE);
                                            Collections.sort(listaEntrega, new ComparadorAlimentoEntrega()); //ordena la lista por el nombre del ingrediente
                                        }
                                    }
                                    numIngredientes=listaEntrega.size();
                                    conteoIngredientes.setText(String.valueOf(numIngredientes));
                                    totalInfantes.setText(String.valueOf(numInfantes));
                                    miAdaptadorEntrega= new AdaptadorListaRecibe(RecibeCompra.this, listaEntrega);
                                    miRecyclerListaEntrega.setAdapter(miAdaptadorEntrega);
                                    miRecyclerListaEntrega.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
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
    }

    private void llenarListaCDIE() {
        listadoCDIE=new ArrayList<String>();
        miReferenciaCDI = FirebaseDatabase.getInstance().getReference("Centros");
        miReferenciaCDI.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    for (DataSnapshot miCentro : dataSnapshot.getChildren()) {
                        listadoCDIE.add(miCentro.getKey());
                    }
                    adaptadorCDIE = new ArrayAdapter<String>(RecibeCompra.this, android.R.layout.simple_spinner_item, listadoCDIE);
                    adaptadorCDIE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    selectorCDIE.setAdapter(adaptadorCDIE);
                    //selectorSemanas.setSelected(false);
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
        listadoSemanasE=new ArrayList<String>();
        miReferenciaSemE = FirebaseDatabase.getInstance().getReference("semanas");
        miReferenciaSemE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    for (DataSnapshot miSemana : dataSnapshot.getChildren()) {
                        listadoSemanasE.add(miSemana.getKey());
                    }
                    adaptadorSemanaE = new ArrayAdapter<String>(RecibeCompra.this, android.R.layout.simple_spinner_item, listadoSemanasE);
                    adaptadorSemanaE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    selectorSemanasE.setAdapter(adaptadorSemanaE);
                    //selectorSemanas.setSelected(false);
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
                Toast.makeText(this, "Error en la lectura del código", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Código leído: " +"\n" +codigoLeido.getContents(), Toast.LENGTH_LONG).show();
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
        pos=0;
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
            nombreEntrega=datosLeidos.substring(pos2+1,datosLeidos.length());
        }
        if (!nombreSemanaV.isEmpty() && !nombreCDIV.isEmpty() && !nombreEntrega.isEmpty()){
            //los tres datos fueron leidos exitosamente
            //si la lista de compras existe la actualiza
            //miReferenciaLista=FirebaseDatabase.getInstance().getReference("entrega").child(nombreSemana);
        }
    }


}
