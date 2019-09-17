package com.dgaviria.sistur.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dgaviria.sistur.Clases.MenuOpciones;
import com.dgaviria.sistur.R;

import java.util.List;

public class AdaptadorMenuPpal extends RecyclerView.Adapter<AdaptadorMenuPpal.OpcionesViewHolder> {
    List<MenuOpciones> listadoOpciones;
    Context contexto;
    public interface OnItemClick{
        void itemClick(MenuOpciones misOpciones,int posicion);
        void imagenClick(MenuOpciones misOpciones,int posicion);

    }
    private OnItemClick listener;

    public static class OpcionesViewHolder extends RecyclerView.ViewHolder{
        ImageView imagenOpcion;
        TextView txtTitulo, txtSubtitulo;

        public OpcionesViewHolder(View vistaItem){
            super(vistaItem);
            txtTitulo =vistaItem.findViewById(R.id.txtTitulo);
            txtSubtitulo =vistaItem.findViewById(R.id.txtSubtitulo);
            imagenOpcion=vistaItem.findViewById(R.id.imagenOpcion);
        }
        public void bind (final MenuOpciones misOpciones, final int posicion, final OnItemClick onItemClick){
            txtTitulo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.itemClick(misOpciones,posicion);
                }
            });
            txtSubtitulo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.itemClick(misOpciones,posicion);
                }
            });
            imagenOpcion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.imagenClick(misOpciones,posicion);
                }
            });
        }
    }

    public AdaptadorMenuPpal(Context contexto,List<MenuOpciones> listado, AdaptadorMenuPpal.OnItemClick listener){
        this.listadoOpciones=listado;
        this.contexto=contexto;
        this.listener=listener;
    }

    @Override
    public OpcionesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vistaUno= LayoutInflater.from(parent.getContext()).inflate(R.layout.opciones_menu_ppal,parent,false);
        AdaptadorMenuPpal.OpcionesViewHolder filaUno=new AdaptadorMenuPpal.OpcionesViewHolder(vistaUno);
        return filaUno;
    }

    @Override
    public void onBindViewHolder(OpcionesViewHolder holder, int position) {
        Integer imagen;
        MenuOpciones misOpciones=listadoOpciones.get(position);
        //verifica el rol del usuario vs el que está permitido en el menú
        switch (misOpciones.getMinimorol()){
            case "superusuario":
                break;
            case "administrador":
                break;
            case "gestor":
                break;
            case "compras":
                break;
            case "basico":
                break;
        }
        imagen=contexto.getResources().getIdentifier(misOpciones.getImagen(),"mipmap","com.dgaviria.sistur");
        holder.txtTitulo.setText(misOpciones.getTitulo());
        holder.txtSubtitulo.setText(misOpciones.getSubtitulo());
        holder.imagenOpcion.setImageResource(imagen);
        holder.bind(listadoOpciones.get(position),position,listener);
    }

    @Override
    public int getItemCount() {
        return listadoOpciones.size();
    }

    @Override
    public int getItemViewType(int posicion) {
        return posicion;
    }
}
