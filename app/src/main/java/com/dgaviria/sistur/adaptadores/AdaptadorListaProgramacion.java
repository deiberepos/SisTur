package com.dgaviria.sistur.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dgaviria.sistur.R;

import com.dgaviria.sistur.clases.Preparacion;

import java.util.List;

public class AdaptadorListaProgramacion extends RecyclerView.Adapter<AdaptadorListaProgramacion.OpcionesViewHolder> {
    List<Preparacion> listadoDiario;
    Context contexto;

    public static class OpcionesViewHolder extends RecyclerView.ViewHolder{
        TextView txtPrepara;
        TextView txtNumero;

        public OpcionesViewHolder(View vistaItem){
            super(vistaItem);
            txtPrepara =vistaItem.findViewById(R.id.txtPreparaDiario);
            txtNumero=vistaItem.findViewById(R.id.txtNumPrepara);
        }
    }

    public AdaptadorListaProgramacion(Context contexto,List<Preparacion> listado){
        this.listadoDiario=listado;
        this.contexto=contexto;
    }

    @Override
    public AdaptadorListaProgramacion.OpcionesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vistaUno= LayoutInflater.from(parent.getContext()).inflate(R.layout.minutas_publicar,parent,false);
        AdaptadorListaProgramacion.OpcionesViewHolder filaUno=new AdaptadorListaProgramacion.OpcionesViewHolder(vistaUno);
        return filaUno;
    }

    @Override
    public void onBindViewHolder(AdaptadorListaProgramacion.OpcionesViewHolder holder, int position) {
        holder.txtNumero.setText(listadoDiario.get(position).getNumeroPreparacion());
        holder.txtPrepara.setText(listadoDiario.get(position).getNombrePreparacion());
    }

    @Override
    public int getItemCount() {
        return listadoDiario.size();
    }

    @Override
    public int getItemViewType(int posicion) {
        return posicion;
    }
}
