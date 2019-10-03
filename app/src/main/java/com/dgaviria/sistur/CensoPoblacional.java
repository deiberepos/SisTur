package com.dgaviria.sistur;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dgaviria.sistur.clases.Censo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.Date;

public class CensoPoblacional extends AppCompatActivity {

    private int año , mes,dia;
    CalendarView calendarView;
    Button btnguardar,btnlistar, btnstore ;
    EditText campoFecha ,editnombreInfante, editapellidoInfante, editobservaciones, editnombrePadre, editnombreMadre, editTeleMadre, editTelePadre, editDirPadre, editDirMadre;
    DatabaseReference miReferencia, misDatos;
    String nombre, apellido, observacion, nombreMadr, nombrePadr, telM, telP, dirM, dirP,genero,imagenn;
    RadioGroup gen;
    ImageView imagen;
    private static final int TIPO_DIALOGO = 0;
    private static DatePickerDialog.OnDateSetListener selectorFecha;

    FirebaseStorage storage;
    StorageReference storageReference;
    private static final int GALLERY_INTENT=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.censo_poblacional);
        referenciar();
        storageReference = FirebaseStorage.getInstance().getReference();

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

        btnstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               intentoGaleria();


            }
        });
        Calendar calendar = Calendar.getInstance();
        año = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);

        mostrarFecha();

        selectorFecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
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



    private void mostrarFecha() {
        campoFecha.setText(dia + " / " + mes + "/" + año);
    }

    private void intentoGaleria(){
        Intent miIntento=new Intent(Intent.ACTION_PICK);
        miIntento.setType("image/*");
        startActivityForResult(miIntento,GALLERY_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode==RESULT_OK)
        {
            Uri miUri=data.getData();
            final StorageReference rutaArchivo=storageReference.child("fotos").child(miUri.getLastPathSegment());
            rutaArchivo.putFile(miUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> otraUri=taskSnapshot.getStorage().getDownloadUrl();
                    while (!otraUri.isComplete());
                    Uri miUrl=otraUri.getResult();
                    Glide.with(CensoPoblacional.this).load(miUrl).fitCenter().into(imagen);
                    Toast.makeText(getApplicationContext(),"Acción finalizada",Toast.LENGTH_SHORT).show();
                }
            });
        }
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
        btnstore=findViewById(R.id.btnimagen);
        imagen=findViewById(R.id.imagen);
        gen=findViewById(R.id.radiogenero);
        campoFecha = findViewById(R.id.fecha);

    }


    private void guardarCenso() {
        miReferencia = FirebaseDatabase.getInstance().getReference();
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
