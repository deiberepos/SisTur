package com.dgaviria.sistur.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
        holder.asiste.setChecked(infante.getAsistencia());
        holder.asiste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.asiste.isChecked()){nombresInfantes.get(position).setAsistencia(true);}
                else {nombresInfantes.get(position).setAsistencia(false);}
            }
        });
    }

    @Override
    public int getItemCount() {
        return nombresInfantes.size();
    }

    class AsistenciaViewHolder extends RecyclerView.ViewHolder{
        CheckBox asiste;
        TextView nombre;

        public AsistenciaViewHolder(View itemView) {
            super(itemView);
            nombre=itemView.findViewById(R.id.txvnombreAsistente);
            asiste =itemView.findViewById(R.id.idchkSi);
        }
    }
}
