package com.dgaviria.sistur.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dgaviria.sistur.R;
import com.dgaviria.sistur.clases.PlanAlimenticio;

import java.util.List;

public class AdaptadorPlanAlimenticio extends RecyclerView.Adapter<AdaptadorPlanAlimenticio.AlimentosViewHolder> {
    List<PlanAlimenticio> listadoAlimentos;
    Context contexto;

    public interface OnItemClick{
        void itemClick(PlanAlimenticio misAlimentos, int posicion);
        void seleccionaClick(PlanAlimenticio misAlimentos,int posicion);
    }
    private OnItemClick listener;

    public static class AlimentosViewHolder extends RecyclerView.ViewHolder{
        ImageView selecciona;
        TextView grupoA,codigoA,nombreA,regionalA;

        public AlimentosViewHolder(View vistaItem){
            super(vistaItem);
            grupoA=vistaItem.findViewById(R.id.txtGrupoA);
            codigoA=vistaItem.findViewById(R.id.txtCodigoA);
            nombreA=vistaItem.findViewById(R.id.txtNombreA);
            regionalA=vistaItem.findViewById(R.id.txtARegional);
            selecciona =vistaItem.findViewById(R.id.imgSeleccionaA);
        }
        public void bind (final PlanAlimenticio misAlimentos, final int posicion, final OnItemClick onItemClick){
            nombreA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.itemClick(misAlimentos,posicion);
                }
            });
            selecciona.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.seleccionaClick(misAlimentos,posicion);
                }
            });
        }
    }

    public AdaptadorPlanAlimenticio(Context contexto,List<PlanAlimenticio> listadoA,AdaptadorPlanAlimenticio.OnItemClick listener){
        this.listadoAlimentos =listadoA;
        this.contexto=contexto;
        this.listener=listener;
    }

    @Override
    public AlimentosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vistaUno= LayoutInflater.from(parent.getContext()).inflate(R.layout.alimentos_lista,parent,false);
        AdaptadorPlanAlimenticio.AlimentosViewHolder filaUno=new AdaptadorPlanAlimenticio.AlimentosViewHolder(vistaUno);
        return filaUno;
    }

    @Override
    public void onBindViewHolder(AlimentosViewHolder holder, int position) {
        PlanAlimenticio miAlimento= listadoAlimentos.get(position);
        holder.grupoA.setText(miAlimento.getGrupo());
        holder.codigoA.setText(miAlimento.getCodigo());
        holder.nombreA.setText(miAlimento.getNombre());
        holder.regionalA.setText(miAlimento.getRegional());
        if (miAlimento.getActivo())
            holder.selecciona.setImageResource(R.mipmap.ic_actualiza);
        else
            holder.selecciona.setImageResource(R.mipmap.ic_elimina);
        holder.bind(listadoAlimentos.get(position),position,listener);
    }

    @Override
    public int getItemCount() {
        return listadoAlimentos.size();
    }

    @Override
    public int getItemViewType(int posicion) {
        return posicion;
    }


}
