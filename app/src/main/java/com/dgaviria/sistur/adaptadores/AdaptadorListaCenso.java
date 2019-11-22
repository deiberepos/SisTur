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
import com.dgaviria.sistur.clases.Censo;
import com.dgaviria.sistur.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    }
    private OnItemClick listener;

    public static  class CensoViewHolder extends RecyclerView.ViewHolder{
        ImageView modifica;
        TextView registro,nombres,centro;

        public CensoViewHolder(@NonNull View itemView) {
            super(itemView);

            registro=itemView.findViewById(R.id.txtRegistro);
            nombres=itemView.findViewById(R.id.txtNombreInfante);
            centro=itemView.findViewById(R.id.txtCentro);
            modifica=itemView.findViewById(R.id.imgModificaCenso);
        }

        public void bind(final Censo miCenso,final int posicion,final OnItemClick onItemClick)
        {
            nombres.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.itemClick(miCenso,posicion);
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

    public AdaptadorListaCenso(Context context,List<Censo>listado,OnItemClick listener){
      this.contexto=context;
      this.listadoCenso=listado;
      this.listener=listener;
    }

    @NonNull
    @Override
    public CensoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vistaDos= LayoutInflater.from(parent.getContext()).inflate(R.layout.censo_lista,parent,false);
        CensoViewHolder filauno=new CensoViewHolder(vistaDos);
        return filauno;
    }

    @Override
    public void onBindViewHolder(@NonNull CensoViewHolder holder, final int posicion){
        String nombreCompuesto;
        Censo miCenso=listadoCenso.get(posicion);
        nombreCompuesto=miCenso.getNombre()+" "+miCenso.getApellidos();
        holder.registro.setText(miCenso.getRegistro());
        holder.nombres.setText(nombreCompuesto);
        holder.centro.setText(miCenso.getCentroasociado());
        if (miCenso.getGenero().equals("F"))
            holder.modifica.setImageResource(R.mipmap.femenino);
        else
            holder.modifica.setImageResource(R.mipmap.masculino);
        if (posicion+1==getItemCount()){
            ajusteMargenInferior(holder.itemView,(int)(60* Resources.getSystem().getDisplayMetrics().density));
        }
        else{
            ajusteMargenInferior(holder.itemView,0);
        }
        holder.bind(listadoCenso.get(posicion),posicion,listener);
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
        return listadoCenso.size();
    }

    @Override
    public int getItemViewType(int posicion) {
        return posicion;
    }



}
