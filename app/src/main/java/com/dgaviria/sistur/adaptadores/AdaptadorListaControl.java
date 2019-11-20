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
    List<OtrosEntrega> listadoEntregas;
    Context contexto;

    public AdaptadorListaControl(List<OtrosEntrega> listaentregas){
        this.listadoEntregas = listaentregas;
    }

    public AdaptadorListaControl(Context context, List<OtrosEntrega> listaentregas, AdaptadorListaControl.OnItemClick listener){
        this.contexto = context;
        this.listadoEntregas = listaentregas;
        this.listener = listener;


    }
    private OnItemClick listener;

    public interface OnItemClick{
        void itemClick(OtrosEntrega control, int posicion);
    }

    public static class ControlViewHolder extends RecyclerView.ViewHolder{
        TextView nombreCDI,entregadopor,recibidopor,quiencompra;

        public ControlViewHolder(View vistaItem){
            super(vistaItem);
            entregadopor=vistaItem.findViewById(R.id.txtNombres);
            nombreCDI=vistaItem.findViewById(R.id.txtUsuario);
            recibidopor=vistaItem.findViewById(R.id.txtRol);
        }
    }
    @NonNull
    @Override
    public AdaptadorListaControl.ControlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.usuarios_lista,parent,false);
        ControlViewHolder fila = new ControlViewHolder(vista);
        return fila;
    }

    @Override
    public void onBindViewHolder(@NonNull ControlViewHolder holder, int position) {
        OtrosEntrega control = listadoEntregas.get(position);
        holder.nombreCDI.setText(control.getNombreCDI());
        holder.entregadopor.setText(control.getQuiencompra());
        holder.recibidopor.setText(control.getRecibidopor());

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
