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
    public void onBindViewHolder(@NonNull CensoViewHolder holder, final int position){
        String nombreCompuesto;
        Censo miCenso=listadoCenso.get(position);
        nombreCompuesto=miCenso.getNombre()+" "+miCenso.getApellidos();
        holder.registro.setText(miCenso.getRegistro());
        holder.nombres.setText(nombreCompuesto);
        holder.centro.setText(miCenso.getCentroasociado());
        if (miCenso.getGenero().equals("F"))
            holder.modifica.setImageResource(R.mipmap.femenino);
        else
            holder.modifica.setImageResource(R.mipmap.masculino);
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
