package com.dgaviria.sistur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class RecibeCompra extends AppCompatActivity {
    ImageView codigoQR;
    Button botonGenerar;
    Bitmap imagenQR;
    int año, mes, dia;
    EditText campoFechaEI;
    Spinner selectorSemanasEI,selectorCDIEI;
    String nombreSemanaEI,nombreCDIEI,fechaEntregaI,recibeRol,recibeUsuario;
    DatabaseReference miReferenciaCDIEI,miReferenciaSemEI,miReferenciaLista;
    ArrayAdapter<String> adaptadorSemanaEI,adaptadorCDIEI;
    ArrayList<String> listadoSemanasEI, listadoCDIEI;
    Calendar miCalendarioEI;
    Boolean aprobarQR;
    Bundle recibeParametros;

    public final static int anchoCodigoQR = 350 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrega_compras);

        referenciar();
        mostrarFechaEI();
        selectorCDIEI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adaptadorVista, View view, int i, long l) {
                nombreCDIEI=adaptadorVista.getItemAtPosition(i).toString();
                lecturaCDIEI();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        selectorSemanasEI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adaptadorVista, View view, int i, long l) {
                nombreSemanaEI=adaptadorVista.getItemAtPosition(i).toString();
                lecturaCDIEI();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        botonGenerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aprobarQR && nombreSemanaEI!=null && !nombreSemanaEI.isEmpty() && nombreCDIEI!=null && !nombreCDIEI.isEmpty())
                    try {
                        String textoQR=nombreSemanaEI+"|"+nombreCDIEI+"|"+recibeUsuario;
                        imagenQR = convierteTextoAQR(textoQR);
                        codigoQR.setImageBitmap(imagenQR);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                else
                    Toast.makeText(RecibeCompra.this, "Debe seleccionar una semana y un CDI", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void referenciar() {
        codigoQR= findViewById(R.id.imagenQR);
        botonGenerar = findViewById(R.id.botonQR);
        campoFechaEI=findViewById(R.id.editFechaEntregaEI);
        miCalendarioEI = Calendar.getInstance();
        año = miCalendarioEI.get(Calendar.YEAR);
        mes = miCalendarioEI.get(Calendar.MONTH)+1;
        dia = miCalendarioEI.get(Calendar.DAY_OF_MONTH);
        selectorSemanasEI=findViewById(R.id.selectorSemanaEI);
        selectorCDIEI=findViewById(R.id.selectorCDIEI);
        recibeRol= recibeParametros.getString("rol");
        recibeUsuario = recibeParametros.getString("usuario");
        aprobarQR=false;
        llenarListaCDIEI();
        llenarListaSemanasEI();
    }

    Bitmap convierteTextoAQR(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,anchoCodigoQR, anchoCodigoQR, null
            );
        } catch (IllegalArgumentException Illegalargumentexception) {
            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];
        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.colorNegro):getResources().getColor(R.color.colorBlanco);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 350, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    private void mostrarFechaEI() {
        campoFechaEI.setText(dia + "/" + mes + "/" + año);
    }
    public void mostrarCalendarioEI(View view) {
        Calendar miCalendario = new GregorianCalendar();//Calendar.getInstance();
        miCalendario.setTime(new Date());
        new DatePickerDialog(this, R.style.TemaCalendario, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                año = year;
                mes = monthOfYear + 1;
                dia = dayOfMonth;
                mostrarFechaEI();
            }
        }, miCalendario.get(Calendar.YEAR), miCalendario.get(Calendar.MONTH), miCalendario.get(Calendar.DAY_OF_MONTH)).show();
    }
    private void lecturaCDIEI(){
        fechaEntregaI=campoFechaEI.getText().toString();
        if (nombreCDIEI!=null && !nombreCDIEI.isEmpty() && nombreSemanaEI!=null && !nombreSemanaEI.isEmpty() && fechaEntregaI!=null && !fechaEntregaI.isEmpty()){
            //Verifica la existencia de la lista de entregas para el CDI en la semana seleccionada
            aprobarQR=true;
            miReferenciaLista= FirebaseDatabase.getInstance().getReference("entregas").child(nombreCDIEI);
            miReferenciaLista.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){ //Si existen entregas para el CDI
                        for (DataSnapshot miSemana:dataSnapshot.getChildren()){
                            if (miSemana.getValue(String.class).equals(nombreSemanaEI)){ //verifica si la semana de entrega es la que seleccionó
                                //Encontró que ya ha existe una entrega
                                aprobarQR=false;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            if (!aprobarQR)
                Toast.makeText(this, "Ya se ha realizado una entrega, no se puede volver a realizar", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Debes seleccionar todos los datos para continuar", Toast.LENGTH_SHORT).show();
    }

    private void llenarListaCDIEI() {
        listadoCDIEI=new ArrayList<String>();
        miReferenciaCDIEI = FirebaseDatabase.getInstance().getReference("Centros");
        miReferenciaCDIEI.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    for (DataSnapshot miCentro : dataSnapshot.getChildren()) {
                        listadoCDIEI.add(miCentro.getKey());
                    }
                    adaptadorCDIEI = new ArrayAdapter<String>(RecibeCompra.this, android.R.layout.simple_spinner_item, listadoCDIEI);
                    adaptadorCDIEI.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    selectorCDIEI.setAdapter(adaptadorCDIEI);
                    //selectorSemanas.setSelected(false);
                    selectorCDIEI.setSelection(0, true); //selecciona el primer elemento del spinner
                    adaptadorCDIEI.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Error de lectura de CDI, contacte al administrador", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void llenarListaSemanasEI() {
        listadoSemanasEI=new ArrayList<String>();
        miReferenciaSemEI = FirebaseDatabase.getInstance().getReference("semanas");
        miReferenciaSemEI.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildren() != null) {
                    for (DataSnapshot miSemana : dataSnapshot.getChildren()) {
                        listadoSemanasEI.add(miSemana.getKey());
                    }
                    adaptadorSemanaEI = new ArrayAdapter<String>(RecibeCompra.this, android.R.layout.simple_spinner_item, listadoSemanasEI);
                    adaptadorSemanaEI.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    selectorSemanasEI.setAdapter(adaptadorSemanaEI);
                    //selectorSemanas.setSelected(false);
                    selectorSemanasEI.setSelection(0, true); //selecciona el primer elemento del spinner
                    adaptadorSemanaEI.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Error de lectura de semanas, contacte al administrador", Toast.LENGTH_SHORT).show();
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
