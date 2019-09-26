package com.dgaviria.sistur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dgaviria.sistur.clases.Censo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CensoPoblacional extends AppCompatActivity {
    Button btnguardar,btnlistar;
    EditText editnombreInfante, editapellidoInfante, editobservaciones, editnombrePadre, editnombreMadre, editTeleMadre, editTelePadre, editDirPadre, editDirMadre;
    DatabaseReference miReferencia, misDatos;
    String nombre, apellido, observacion, nombreMadr, nombrePadr, telM, telP, dirM, dirP,genero;
    RadioGroup gen;


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
        btnlistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mi = new Intent(CensoPoblacional.this, ListarCensoPoblacion.class);
                startActivity(mi);
            }
        });
        gen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.masculino:
                        genero="masculino";
                        break;
                    case R.id.femenino:
                        genero="femenino";
                        break;
                }
            }
        });

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
        gen=findViewById(R.id.radiogenero);
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
