package com.dgaviria.sistur.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dgaviria.sistur.Clases.Usuarios;
import com.dgaviria.sistur.R;

import java.util.List;

public class AdaptadorListaUsuarios extends RecyclerView.Adapter<AdaptadorListaUsuarios.UsuariosViewHolder> {
    List<Usuarios> listadoUsuarios;
    Context contexto;
    public interface OnItemClick{
        void itemClick(Usuarios misUsuarios,int posicion);
        void correoClick(Usuarios misUsuarios,int posicion);
        void telefonoClick(Usuarios misUsuarios,int posicion);

    }
    private OnItemClick listener;

    public static class UsuariosViewHolder extends RecyclerView.ViewHolder{
        ImageView correo,telefono,nombre;
        TextView nombreU;

        public UsuariosViewHolder(View vistaItem){
            super(vistaItem);
            nombreU=vistaItem.findViewById(R.id.txtUsuario);
            correo=vistaItem.findViewById(R.id.imgCorreo);
            telefono=vistaItem.findViewById(R.id.imgTelefono);

        }
        public void bind (final Usuarios misUsuarios, final int posicion, final OnItemClick onItemClick){
            nombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.itemClick(misUsuarios,posicion);
                }
            });
            telefono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.telefonoClick(misUsuarios,posicion);
                }
            });
            correo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.correoClick(misUsuarios,posicion);
                }
            });
        }
    }

    public AdaptadorListaUsuarios(List<Usuarios> listado, Context contexto, OnItemClick listener){
        this.listadoUsuarios=listado;
        this.contexto=contexto;
        this.listener=listener;
    }

    @Override
    public UsuariosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View vistaUno= LayoutInflater.from(parent.getContext()).inflate(R.layout.usuarios_lista,parent,false);
            UsuariosViewHolder filaUno=new UsuariosViewHolder(vistaUno);
            return filaUno;

    }

    @Override
    public void onBindViewHolder(UsuariosViewHolder holder, int position) {
        Usuarios miUsuario=listadoUsuarios.get(position);
        holder.nombreU.setText(miUsuario.getNombre());
        holder.correo.setImageResource(R.mipmap.ic_email);
        holder.telefono.setImageResource(R.mipmap.ic_telefono);
        //holder.nombre.setImageResource(R.mipmap.ic_nombre);

    }

    @Override
    public int getItemCount() {
        return listadoUsuarios.size();
    }

    @Override
    public int getItemViewType(int posicion) {
       return posicion;
    }
}
