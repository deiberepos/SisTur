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
    public static ArrayList<String> nombresInfantes;

    public AdaptadorListaAsistencia(Context contexto, ArrayList<String> listaInfantes){
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
    public void onBindViewHolder(@NonNull AsistenciaViewHolder holder, int position) {
        String nombre = nombresInfantes.get(position);
        holder.nombre.setText(nombre);
    }

    @Override
    public int getItemCount() {
        return nombresInfantes.size();
    }

    class AsistenciaViewHolder extends RecyclerView.ViewHolder{
        CheckBox asiste, noAsiste;
        TextView nombre;

        public AsistenciaViewHolder(View itemView) {
            super(itemView);
            nombre=itemView.findViewById(R.id.txvnombreAsistente);
            asiste =itemView.findViewById(R.id.idchkSi);
            noAsiste=itemView.findViewById(R.id.idchkNo);
        }
    }
}
