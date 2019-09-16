package com.dgaviria.sistur;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.dgaviria.sistur.Clases.CdiHcb;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CensoPoblacional extends AppCompatActivity {
    Button btnguardar;
    EditText editnombreInfante, editapellidoInfante, editobservaciones, editnombrePadre, editnombreMadre, editTeleMadre, editTelePadre, editDirPadre, editDirMadre;
    RadioButton genero;
    DatabaseReference miReferencia, misDatos;
    String nombreinfante, nombre, apellido, observacion, nombreMadr, nombrePadr, telM, telP, dirM, dirP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.censo_poblacional);
        referenciar();

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarDatos();
            }
        });
    }

    public void referenciar() {
        btnguardar = findViewById(R.id.btnguardarcenso);
        editnombreInfante = findViewById(R.id.nombreinfante);
        editapellidoInfante = findViewById(R.id.apellidoinfante);
        editobservaciones = findViewById(R.id.editobsrvaciones);
        editnombrePadre = findViewById(R.id.editnom_padre);
        editnombreMadre = findViewById(R.id.editnom_madre);
        editTeleMadre = findViewById(R.id.edittelefonomadre);
        editTelePadre = findViewById(R.id.edittelefonopadre);
        editDirMadre = findViewById(R.id.editdireccionmadre);
        editDirPadre = findViewById(R.id.editdireccionpadre);
    }

    private void guardarCenso() {
        //construye el objeto que se va a guardar en la base de datos
        miReferencia = FirebaseDatabase.getInstance().getReference();
        //guarda los datos del usuario
        misDatos = miReferencia.child("CensoInfante");
        misDatos.child(nombreinfante).setValue(new CdiHcb(nombre, apellido, observacion, nombreMadr, nombrePadr, telM, telP, dirM, dirP));
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
            nombreinfante = editnombreInfante.getText().toString();
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
