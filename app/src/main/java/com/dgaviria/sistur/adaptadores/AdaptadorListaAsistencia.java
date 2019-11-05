package com.dgaviria.sistur.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dgaviria.sistur.R;
import com.dgaviria.sistur.clases.InfanteAsiste;

import java.util.ArrayList;

public class AdaptadorListaAsistencia extends RecyclerView.Adapter<AdaptadorListaAsistencia.AsistenciaViewHolder> {
    private LayoutInflater miInflater;
    public static ArrayList<InfanteAsiste> nombresInfantes;

    public AdaptadorListaAsistencia(Context contexto, ArrayList<InfanteAsiste> listaInfantes){
        miInflater = LayoutInflater.from(contexto);
        this.nombresInfantes = listaInfantes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AsistenciaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = miInflater.inflate(R.layout.asistencia_lista, parent, false);
        AdaptadorListaAsistencia.AsistenciaViewHolder holder = new AdaptadorListaAsistencia.AsistenciaViewHolder(vista);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AsistenciaViewHolder holder, final int position) {
        final InfanteAsiste infante = nombresInfantes.get(position);
        String nombre = infante.getNombreInfante();
        holder.nombre.setText(nombre);
        holder.chkLunes.setChecked(infante.getAsisteLunes());
        holder.chkMartes.setChecked(infante.getAsisteMartes());
        holder.chkMiercoles.setChecked(infante.getAsisteMiercoles());
        holder.chkJueves.setChecked(infante.getAsisteJueves());
        holder.chkViernes.setChecked(infante.getAsisteViernes());
        holder.chkLunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.chkLunes.isChecked()){nombresInfantes.get(position).setAsisteLunes(true);}
                else {nombresInfantes.get(position).setAsisteLunes(false);}
            }
        });
        holder.chkMartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.chkMartes.isChecked()){nombresInfantes.get(position).setAsisteMartes(true);}
                else {nombresInfantes.get(position).setAsisteMartes(false);}
            }
        });
        holder.chkMiercoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.chkMiercoles.isChecked()){nombresInfantes.get(position).setAsisteMiercoles(true);}
                else {nombresInfantes.get(position).setAsisteMiercoles(false);}
            }
        });
        holder.chkJueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.chkJueves.isChecked()){nombresInfantes.get(position).setAsisteJueves(true);}
                else {nombresInfantes.get(position).setAsisteJueves(false);}
            }
        });
        holder.chkViernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.chkViernes.isChecked()){nombresInfantes.get(position).setAsisteViernes(true);}
                else {nombresInfantes.get(position).setAsisteViernes(false);}
            }
        });
    }

    @Override
    public int getItemCount() {
        return nombresInfantes.size();
    }

    class AsistenciaViewHolder extends RecyclerView.ViewHolder{
        CheckBox chkLunes;
        CheckBox chkMartes;
        CheckBox chkMiercoles;
        CheckBox chkJueves;
        CheckBox chkViernes;
        TextView nombre;

        public AsistenciaViewHolder(View itemView) {
            super(itemView);
            nombre=itemView.findViewById(R.id.txvnombreAsistente);
            chkLunes =itemView.findViewById(R.id.idchkLunes);
            chkMartes = itemView.findViewById(R.id.idchkMartes);
            chkMiercoles=itemView.findViewById(R.id.idchkMiercoles);
            chkJueves=itemView.findViewById(R.id.idchkJueves);
            chkViernes=itemView.findViewById(R.id.idchkViernes);
        }
    }
}
