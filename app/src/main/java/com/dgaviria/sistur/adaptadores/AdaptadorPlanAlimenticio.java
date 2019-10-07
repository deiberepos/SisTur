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
    private EscuchaPresionaClick elementoPresionado=null;

    public interface EscuchaPresionaClick{
        void itemClick(View vista,PlanAlimenticio misAlimentos, int posicion);
        void itemClickLargo(View vista,PlanAlimenticio misAlimentos,int posicion);
    }
    private SparseBooleanArray itemsSeleccionados;
    private int indicadorItemSeleccionado = -1;

    public void setOnClickListener(EscuchaPresionaClick elementoPresionado) {
        this.elementoPresionado = elementoPresionado;
    }

    public static class AlimentosViewHolder extends RecyclerView.ViewHolder{
        ImageView selecciona;
        TextView grupoA, codigoA,nombreA,regionalA,primeraLetra;
        RelativeLayout lyt_marcado,lyt_imagen;
        View lyt_padre;
        Button botonBuscar;

        //private CircularImageView imagen;

        public AlimentosViewHolder(View vistaItem){
            super(vistaItem);
            grupoA=vistaItem.findViewById(R.id.txtGrupoA);
            codigoA =vistaItem.findViewById(R.id.txtCodigoA);
            nombreA=vistaItem.findViewById(R.id.txtNombreA);
            regionalA=vistaItem.findViewById(R.id.txtARegional);
            selecciona =vistaItem.findViewById(R.id.imgSeleccionaA);
            lyt_marcado=vistaItem.findViewById(R.id.lytMarcado);
            lyt_padre=vistaItem.findViewById(R.id.lytPadre);
            lyt_imagen=vistaItem.findViewById(R.id.lytImagen);

        }
        /*public void bind (final PlanAlimenticio misAlimentos, final int posicion, final OnItemClick onItemClick){
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
        }*/
    }

    public AdaptadorPlanAlimenticio(Context contexto,List<PlanAlimenticio> listadoA) { //,AdaptadorPlanAlimenticio.OnItemClick elementoPresionado){
        this.contexto=contexto;
        this.listadoAlimentos =listadoA;
        itemsSeleccionados=new SparseBooleanArray();
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

        holder.lyt_padre.setActivated(itemsSeleccionados.get(posicion,false));
        holder.lyt_padre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vista) {
                if (elementoPresionado== null) return;
                elementoPresionado.itemClick(vista,miAlimento,posicion);
            }
        });
        holder.lyt_padre.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View vista) {
                if (elementoPresionado==null) return false;
                elementoPresionado.itemClickLargo(vista,miAlimento,posicion);
                return true;
            }
        });
        activaAlimentoSeleccionado(holder,posicion);
        muestraCodigo(holder,miAlimento);
    }


    private void activaAlimentoSeleccionado(AlimentosViewHolder holder, int posicion) {
        if (itemsSeleccionados.get(posicion,false)){
            holder.lyt_imagen.setVisibility(View.GONE);
            holder.lyt_marcado.setVisibility(View.VISIBLE);
            if(indicadorItemSeleccionado==posicion) reajustaIndicadorSeleccionado();
        }
        else{
            holder.lyt_marcado.setVisibility(View.GONE);
            holder.lyt_imagen.setVisibility(View.VISIBLE);
            if(indicadorItemSeleccionado==posicion) reajustaIndicadorSeleccionado();
        }
    }

    private void reajustaIndicadorSeleccionado() {
        indicadorItemSeleccionado=-1;
    }

    public List<Integer> obtenerAlimentosSeleccionados(){
        List<Integer> alimentos = new ArrayList<>(itemsSeleccionados.size());
        for (int i = 0; i < itemsSeleccionados.size(); i++) {
            alimentos.add(itemsSeleccionados.keyAt(i));
        }
        return alimentos;
    }

    //Aquì se muestra la imagen asociada al elemento desplegado
    private void muestraCodigo(AlimentosViewHolder holder,PlanAlimenticio miAlimento){
        holder.codigoA.setVisibility(View.VISIBLE);
        /*if (miAlimento.getActivo()){
            holder.codigoA.setVisibility(View.GONE);
        }
        else{
            holder.codigoA.setVisibility(View.VISIBLE);
        }*/
    }

    public void habilitaSeleccion(int posicion){
        indicadorItemSeleccionado = posicion;
        if (itemsSeleccionados.get(posicion, false)) {
            itemsSeleccionados.delete(posicion);
        } else {
            itemsSeleccionados.put(posicion, true);
        }
        notifyItemChanged(posicion);
    }
    public int numeroDeAlimentosSeleccionados() {
        return itemsSeleccionados.size();
    }

    public PlanAlimenticio traigaAlimento(int posicion){
        return listadoAlimentos.get(posicion);
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
