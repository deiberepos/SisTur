package com.dgaviria.sistur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dgaviria.sistur.clases.Censo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class CensoPoblacional extends AppCompatActivity{


    private int año , mes,dia;
    CalendarView calendarView;
    TextView myDate;
    Button btnguardar,btnlistar;
    EditText  campoFecha ,editnombreInfante, editapellidoInfante, editobservaciones, editnombrePadre, editnombreMadre, editTeleMadre, editTelePadre, editDirPadre, editDirMadre;
    DatabaseReference miReferencia, misDatos;
    String nombre, apellido, observacion, nombreMadr, nombrePadr, telM, telP, dirM, dirP,genero;
    private static final int TIPO_DIALOGO = 0;
    private static DatePickerDialog.OnDateSetListener selectorFecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.censo_poblacional);
        referenciar();



       /* calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = i2 + "/" + (i1 + 1)  + "/" + i;
                myDate.setText(date);

            }
        });*/
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarDatos();
            }
        });
        btnlistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mi = new Intent(CensoPoblacional.this, ListarCensoPoblacion.class);
                startActivity(mi);
            }
        });

        Calendar calendar = Calendar.getInstance();
        año = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH + 1);
        dia = calendar.get(Calendar.DAY_OF_MONTH);

        mostrarFecha();

        selectorFecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1  , int i2) {
               año = i;
               mes = i1 + 1;
               dia = i2;
               mostrarFecha();
            }
        };
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:

                return new DatePickerDialog(this , selectorFecha , año, mes + 1, dia);

        }

        return null;
    }

    public void mostrarCalendario(View view){
        showDialog(TIPO_DIALOGO);
    }

    public void mostrarFecha (){

        campoFecha.setText(año + " / " + mes + "/" + dia);

    }
    public void referenciar() {
        btnlistar=findViewById(R.id.btnlistar);
        btnguardar = findViewById(R.id.btnguardarcenso);
        editnombreInfante = findViewById(R.id.nombreinfante);
        editapellidoInfante = findViewById(R.id.apellidoinfante);
        editobservaciones = findViewById(R.id.editobsrvaciones);
        editnombrePadre = findViewById(R.id.nom_ape_padre);
        editnombreMadre = findViewById(R.id.nom_ape_madre);
        editTeleMadre = findViewById(R.id.telefonomadre);
        editTelePadre = findViewById(R.id.telefonopadre);
        editDirMadre = findViewById(R.id.direccionmadre);
        editDirPadre = findViewById(R.id.direccionpadre);
        //calendarView = findViewById(R.id.calendario);
        myDate = findViewById(R.id.myDate);
        campoFecha = (EditText) findViewById(R.id.fecha);

    }


    private void guardarCenso() {
        //construye el objeto que se va a guardar en la base de datos
        miReferencia = FirebaseDatabase.getInstance().getReference();
        //guarda los datos del usuario
        misDatos = miReferencia.child("CensoInfante");
        misDatos.child(nombre).setValue(new Censo(nombre, apellido, genero, observacion, nombreMadr, nombrePadr, telM, telP, dirM, dirP));
        Toast.makeText(getApplicationContext(),"DATOS GUARDADOS CORRECTAMENTE",Toast.LENGTH_SHORT).show();
    }

    private void cargarDatos() {
        if (editnombreInfante.getText().toString().isEmpty() || editapellidoInfante.getText().toString().isEmpty() ||
                editobservaciones.getText().toString().isEmpty() || editnombrePadre.getText().toString().isEmpty() ||
                editnombreMadre.getText().toString().isEmpty() || editTelePadre.getText().toString().isEmpty() ||
                editTeleMadre.getText().toString().isEmpty() || editDirMadre.getText().toString().isEmpty() ||
                editDirPadre.getText().toString().isEmpty()) {
            Toast.makeText(this, "TODOS LOS CAMPOS SON REQUERIDOS", Toast.LENGTH_SHORT).show();
        } else {
            miReferencia = FirebaseDatabase.getInstance().getReference("CensoInfante");
            nombre = editnombreInfante.getText().toString();
            apellido = editapellidoInfante.getText().toString();
            observacion = editobservaciones.getText().toString();
            nombreMadr = editnombreMadre.getText().toString();
            nombrePadr = editTelePadre.getText().toString();
            telM = editTeleMadre.getText().toString();
            telP = editTelePadre.getText().toString();
            dirM = editDirMadre.getText().toString();
            dirP = editDirPadre.getText().toString();
            guardarCenso();
        }
    }
}
