package com.dgaviria.sistur.adaptadores;

import android.content.Context;
import android.content.res.Resources;
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
    public void onBindViewHolder(@NonNull final AsistenciaViewHolder holder, final int posicion) {
        final InfanteAsiste infante = nombresInfantes.get(posicion);
        String nombre = infante.getNombreInfante();
        holder.nombre.setText(nombre);
        holder.chkLunes.setChecked(infante.getAsisteLunes());
        holder.chkMartes.setChecked(infante.getAsisteMartes());
        holder.chkMiercoles.setChecked(infante.getAsisteMiercoles());
        holder.chkJueves.setChecked(infante.getAsisteJueves());
        holder.chkViernes.setChecked(infante.getAsisteViernes());

        boolean estado = nombresInfantes.get(0).isEstadolunes();
        if(estado){
            holder.chkLunes.setEnabled(false);
            holder.chkMartes.setEnabled(false);
            holder.chkMiercoles.setEnabled(false);
            holder.chkJueves.setEnabled(false);
            holder.chkViernes.setEnabled(false);
        }

        holder.chkLunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.chkLunes.isChecked()){
                    nombresInfantes.get(posicion).setAsisteLunes(true);
                   // holder.chkLunes.setEnabled(false);
                }
                else {nombresInfantes.get(posicion).setAsisteLunes(false);}
            }
        });
        holder.chkMartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.chkMartes.isChecked()){nombresInfantes.get(posicion).setAsisteMartes(true);}
                else {nombresInfantes.get(posicion).setAsisteMartes(false);}
            }
        });
        holder.chkMiercoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.chkMiercoles.isChecked()){nombresInfantes.get(posicion).setAsisteMiercoles(true);}
                else {nombresInfantes.get(posicion).setAsisteMiercoles(false);}
            }
        });
        holder.chkJueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.chkJueves.isChecked()){nombresInfantes.get(posicion).setAsisteJueves(true);}
                else {nombresInfantes.get(posicion).setAsisteJueves(false);}
            }
        });
        holder.chkViernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.chkViernes.isChecked()){nombresInfantes.get(posicion).setAsisteViernes(true);}
                else {nombresInfantes.get(posicion).setAsisteViernes(false);}
            }
        });
        if (posicion+1==getItemCount()){
            ajusteMargenInferior(holder.itemView,(int)(64* Resources.getSystem().getDisplayMetrics().density));
        }
        else{
            ajusteMargenInferior(holder.itemView,0);
        }
    }
    private void ajusteMargenInferior(View itemView, int i) {
        if (itemView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
            params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, i);
            itemView.requestLayout();
        }
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
            nombre=itemView.findViewById(R.id.txvNombreAsistente);
            chkLunes =itemView.findViewById(R.id.idChkLunes);
            chkMartes = itemView.findViewById(R.id.idChkMartes);
            chkMiercoles=itemView.findViewById(R.id.idChkMiercoles);
            chkJueves=itemView.findViewById(R.id.idChkJueves);
            chkViernes=itemView.findViewById(R.id.idChkViernes);
        }
    }
}
