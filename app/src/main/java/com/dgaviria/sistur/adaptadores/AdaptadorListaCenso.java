package com.dgaviria.sistur.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dgaviria.sistur.clases.Censo;
import com.dgaviria.sistur.R;

import java.util.List;
public class AdaptadorListaCenso extends RecyclerView.Adapter<AdaptadorListaCenso.CensoViewHolder> {
    List<Censo> listadoCenso;
    Context contexto;

    public AdaptadorListaCenso(List<Censo> listadoCenso) {
        this.listadoCenso=listadoCenso;
    }
    public interface OnItemClick{
        void itemClick(Censo miCenso, int posicion);
        void modificaClick(Censo miCenso,int posicion);
        void eliminaClick(Censo miCenso,int posicion);
    }
    private OnItemClick listener;

    public static  class CensoViewHolder extends RecyclerView.ViewHolder
    {
        ImageView modifica,elimina;
        TextView nombre,centroAsociado,apellido;

        public CensoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre=itemView.findViewById(R.id.txtNombreInfan);
            apellido=itemView.findViewById(R.id.txtCentroAsociado);
            //centroAsociado=itemView.findViewById(R.id.txtCentroAsociado);
            modifica=itemView.findViewById(R.id.imgModificaCenso);
            elimina=itemView.findViewById(R.id.imgEliminacenso);
        }

        public void bind(final Censo miCenso,final int posicion,final OnItemClick onItemClick)
        {
            nombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.itemClick(miCenso,posicion);
                }
            });
            elimina.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.eliminaClick(miCenso,posicion);
                }
            });
            modifica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.modificaClick(miCenso,posicion);
                }
            });
        }
    }

    public AdaptadorListaCenso(Context context,List<Censo>listado,OnItemClick listener)
    {
      this.contexto=context;
      this.listadoCenso=listado;
      this.listener=listener;

    }


    @NonNull
    @Override
    public CensoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vistados= LayoutInflater.from(parent.getContext()).inflate(R.layout.censo_lista,parent,false);
        CensoViewHolder fiauno=new CensoViewHolder(vistados);
        return fiauno;
    }

    @Override
    public void onBindViewHolder(@NonNull CensoViewHolder holder, int position)
    {
    Censo micensoo=listadoCenso.get(position);
        holder.nombre.setText(micensoo.getNombre());
        holder.apellido.setText(micensoo.getApellidos());
        holder.modifica.setImageResource(R.mipmap.ic_actualiza);
        holder.elimina.setImageResource(R.mipmap.ic_elimina);
        holder.bind(listadoCenso.get(position),position,listener);
    }

    @Override
    public int getItemCount() {
        return listadoCenso.size();
    }

    @Override
    public int getItemViewType(int posicion) {
        return posicion;
    }



}
