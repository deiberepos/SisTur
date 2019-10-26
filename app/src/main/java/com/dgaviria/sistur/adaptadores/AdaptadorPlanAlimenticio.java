package com.dgaviria.sistur.adaptadores;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dgaviria.sistur.R;

import com.dgaviria.sistur.clases.PlanAlimenticio;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorPlanAlimenticio extends RecyclerView.Adapter<AdaptadorPlanAlimenticio.AlimentosViewHolder> {
    private Context contexto;
    private List<PlanAlimenticio> listadoAlimentos;

    public static class AlimentosViewHolder extends RecyclerView.ViewHolder{
        TextView grupoA, codigoA,nombreA,regionalA;

        public AlimentosViewHolder(View vistaItem){
            super(vistaItem);
            grupoA=vistaItem.findViewById(R.id.txtGrupoA);
            codigoA =vistaItem.findViewById(R.id.txtCodigoA);
            nombreA=vistaItem.findViewById(R.id.txtNombreA);
            regionalA=vistaItem.findViewById(R.id.txtARegional);

        }
    }

    public AdaptadorPlanAlimenticio(Context contexto,List<PlanAlimenticio> listadoA) { //,AdaptadorPlanAlimenticio.OnItemClick elementoPresionado){
        this.contexto=contexto;
        this.listadoAlimentos =listadoA;
    }

    @Override
    public AlimentosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vistaUno= LayoutInflater.from(parent.getContext()).inflate(R.layout.alimentos_lista,parent,false);
        AdaptadorPlanAlimenticio.AlimentosViewHolder filaUno=new AdaptadorPlanAlimenticio.AlimentosViewHolder(vistaUno);
        return filaUno;
    }

    @Override
    public void onBindViewHolder(AlimentosViewHolder holder, final int posicion) {
        final PlanAlimenticio miAlimento= listadoAlimentos.get(posicion);
        holder.grupoA.setText(miAlimento.getGrupo());
        holder.codigoA.setText(miAlimento.getCodigo());
        holder.nombreA.setText(miAlimento.getCodigo()+": "+miAlimento.getNombre());
        holder.regionalA.setText(miAlimento.getRegional());
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
