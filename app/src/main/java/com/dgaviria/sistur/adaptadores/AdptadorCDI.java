package com.dgaviria.sistur.adaptadores;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.dgaviria.sistur.R;
import com.dgaviria.sistur.clases.CdiHcb;

import java.util.List;

public class AdptadorCDI extends RecyclerView.Adapter <AdptadorCDI.CDIViewHolder>{
    List<CdiHcb> listadoCentros;
    Context contexto;

    public AdptadorCDI(List<CdiHcb> listacentros){
        this.listadoCentros = listacentros;
    }

    public AdptadorCDI(Context context, List<CdiHcb> listacentros, OnItemClick listener){
        this.contexto = context;
        this.listadoCentros = listacentros;
        this.listener = listener;
    }

    public interface OnItemClick{
        void itemClick(CdiHcb centros, int posicion);
        void modificaClick(CdiHcb centros,int posicion);
    }
    private OnItemClick listener;

    public static class CDIViewHolder extends RecyclerView.ViewHolder{
        ImageView modifica;
        TextView nombreCDI,ubicacion,tipo;

        public CDIViewHolder(View vistaItem){
            super(vistaItem);
            tipo=vistaItem.findViewById(R.id.txtNombres);
            nombreCDI=vistaItem.findViewById(R.id.txtUsuario);
            ubicacion=vistaItem.findViewById(R.id.txtRol);
            modifica=vistaItem.findViewById(R.id.imgModifica);
        }

        public void bind(final CdiHcb centros, final int pos, final OnItemClick onItemClick){
            tipo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.itemClick(centros,pos);
                }
            });
            modifica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.modificaClick(centros,pos);
                }
            });
        }

    }
    @NonNull
    @Override
    public AdptadorCDI.CDIViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.usuarios_lista,parent,false);
        CDIViewHolder fila = new CDIViewHolder(vista);
        return fila;
    }

    @Override
    public void onBindViewHolder(@NonNull AdptadorCDI.CDIViewHolder holder, int posicion) {
        String tipo = "";
        CdiHcb centro = listadoCentros.get(posicion);
        holder.nombreCDI.setText(centro.getNombreCDI());
        holder.tipo.setText(centro.getTipo());
        holder.ubicacion.setText(centro.getUbicacion());
        holder.modifica.setImageResource(R.mipmap.cdi);
        holder.bind(listadoCentros.get(posicion),posicion,listener);
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
        return listadoCentros.size();
    }
    @Override
    public int getItemViewType(int posicion) {
        return posicion;
    }
}
