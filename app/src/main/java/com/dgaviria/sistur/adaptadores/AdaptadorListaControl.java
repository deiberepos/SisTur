package com.dgaviria.sistur.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dgaviria.sistur.R;
import com.dgaviria.sistur.clases.CdiHcb;
import com.dgaviria.sistur.clases.OtrosEntrega;

import java.util.List;

public class AdaptadorListaControl extends RecyclerView.Adapter <AdaptadorListaControl.ControlViewHolder> {
    Context contexto;
    List<OtrosEntrega> listadoEntregas;

    public static class ControlViewHolder extends RecyclerView.ViewHolder{
        TextView nombreCDI;

        public ControlViewHolder(View vistaItem){
            super(vistaItem);
            nombreCDI=vistaItem.findViewById(R.id.txtHogarCDI);
        }
    }

    public AdaptadorListaControl(Context context, List<OtrosEntrega> listaentregas){
        this.contexto = context;
        this.listadoEntregas = listaentregas;
    }


    @Override
    public AdaptadorListaControl.ControlViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.control_lista,parent,false);
        AdaptadorListaControl.ControlViewHolder fila = new AdaptadorListaControl.ControlViewHolder(vista);
        return fila;
    }

    @Override
    public void onBindViewHolder(AdaptadorListaControl.ControlViewHolder holder, int posicion) {
        OtrosEntrega miControl = listadoEntregas.get(posicion);
        holder.nombreCDI.setText(miControl.getNombreCDI());
    }
    @Override
    public int getItemCount() {
        return listadoEntregas.size();
    }
    @Override
    public int getItemViewType(int posicion) {
        return posicion;
    }
}
