package com.dgaviria.sistur.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dgaviria.sistur.clases.Usuarios;
import com.dgaviria.sistur.R;

import java.util.List;

public class AdaptadorListaUsuarios extends RecyclerView.Adapter<AdaptadorListaUsuarios.UsuariosViewHolder> {
    List<Usuarios> listadoUsuarios;
    Context contexto;

    public AdaptadorListaUsuarios(List<Usuarios> listadoUsuarios) {
        this.listadoUsuarios = listadoUsuarios;
    }

    public interface OnItemClick{
        void itemClick(Usuarios misUsuarios,int posicion);
        void modificaClick(Usuarios misUsuarios,int posicion);
        void eliminaClick(Usuarios misUsuarios,int posicion);
    }
    private OnItemClick listener;

    public static class UsuariosViewHolder extends RecyclerView.ViewHolder{
        ImageView modifica, elimina;
        TextView nombreU,nombres,rol;

        public UsuariosViewHolder(View vistaItem){
            super(vistaItem);
            nombreU=vistaItem.findViewById(R.id.txtUsuario);
            modifica =vistaItem.findViewById(R.id.imgModifica);
            nombres=vistaItem.findViewById(R.id.txtNombres);
            rol=vistaItem.findViewById(R.id.txtRol);
        }
        public void bind (final Usuarios misUsuarios, final int posicion, final OnItemClick onItemClick)
        {
            nombres.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.itemClick(misUsuarios,posicion);
                }
            });
            modifica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.modificaClick(misUsuarios,posicion);
                }
            });
        }
    }

    public AdaptadorListaUsuarios( Context contexto,List<Usuarios> listado, OnItemClick listener){
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
        String nombreRol="";
        Usuarios miUsuario=listadoUsuarios.get(position);
        if(miUsuario.getRolsuper())
            nombreRol="SuperUsuario";
        else if (miUsuario.getRoladmin())
            nombreRol="Administrador";
        else if (miUsuario.getRolgestor())
            nombreRol="Gestor";
        else if (miUsuario.getRolcompras())
            nombreRol="CompraGaleria";
        else if (miUsuario.getRolbasico())
            nombreRol="Basico";
        holder.nombreU.setText(miUsuario.getUsuario());
        holder.nombres.setText(miUsuario.getNombre());
        holder.rol.setText(nombreRol);
        holder.modifica.setImageResource(R.mipmap.usuarios);
        holder.bind(listadoUsuarios.get(position),position,listener);
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
