package com.dgaviria.sistur.adaptadores;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dgaviria.sistur.R;
import com.dgaviria.sistur.clases.AlimentoEntrega;
import java.util.ArrayList;

public class AdaptadorListaRecibe extends RecyclerView.Adapter<AdaptadorListaRecibe.AlimentoViewHolder> {
    private LayoutInflater miInflater;
    public static ArrayList<AlimentoEntrega> estadoEntregaLista;

    public AdaptadorListaRecibe(Context contexto, ArrayList<AlimentoEntrega> listaEntrega){
        miInflater = LayoutInflater.from(contexto);
        this.estadoEntregaLista = listaEntrega;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdaptadorListaRecibe.AlimentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = miInflater.inflate(R.layout.recibe_lista, parent, false);
        AdaptadorListaRecibe.AlimentoViewHolder holder = new AdaptadorListaRecibe.AlimentoViewHolder(vista);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorListaRecibe.AlimentoViewHolder holder, final int posicion) {
        final AlimentoEntrega alimentoLista= estadoEntregaLista.get(posicion);
        holder.producto.setText(alimentoLista.getIngrediente());
        holder.unidad.setText(alimentoLista.getMedida());
        holder.cantidad.setText(alimentoLista.getCantidadentregada());
        holder.estadoBueno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alimentoLista.setEstadoBueno(holder.estadoBueno.isChecked());
                holder.estadoBueno.setChecked(holder.estadoBueno.isChecked());

                alimentoLista.setEstadoRegular(false);
                holder.estadoRegular.setChecked(false);

                alimentoLista.setEstadoMalo(false);
                holder.estadoMalo.setChecked(false);
            }
        });
        holder.estadoRegular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alimentoLista.setEstadoRegular(holder.estadoRegular.isChecked());
                holder.estadoRegular.setChecked(holder.estadoRegular.isChecked());

                alimentoLista.setEstadoBueno(false);
                holder.estadoBueno.setChecked(false);

                alimentoLista.setEstadoMalo(false);
                holder.estadoMalo.setChecked(false);
            }
        });
        holder.estadoMalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alimentoLista.setEstadoMalo(holder.estadoMalo.isChecked());
                holder.estadoMalo.setChecked(holder.estadoMalo.isChecked());

                alimentoLista.setEstadoBueno(false);
                holder.estadoBueno.setChecked(false);

                alimentoLista.setEstadoRegular(false);
                holder.estadoRegular.setChecked(false);
            }
        });

        holder.estadoBueno.setChecked(alimentoLista.getEstadoBueno());
        holder.estadoRegular.setChecked(alimentoLista.getEstadoRegular());
        holder.estadoMalo.setChecked(alimentoLista.getEstadoMalo());
        if (posicion+1==getItemCount()){
            ajusteMargenInferior(holder.itemView,(int)(68* Resources.getSystem().getDisplayMetrics().density));
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
        return estadoEntregaLista.size();
    }

    class AlimentoViewHolder extends RecyclerView.ViewHolder{
        RadioButton estadoBueno,estadoRegular,estadoMalo;
        TextView producto,unidad,cantidad;

        public AlimentoViewHolder(View itemView) {
            super(itemView);
            estadoBueno=itemView.findViewById(R.id.rbBueno);
            estadoMalo=itemView.findViewById(R.id.rbMalo);
            estadoRegular=itemView.findViewById(R.id.rbRegular);
            producto=itemView.findViewById(R.id.listaProductoE);
            unidad=itemView.findViewById(R.id.listaUnidadE);
            cantidad=itemView.findViewById(R.id.listaCantidadE);
        }
    }
}
