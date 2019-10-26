package com.dgaviria.sistur.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dgaviria.sistur.R;
import com.dgaviria.sistur.clases.AlimentoEntrega;
import java.util.ArrayList;

public class AdaptadorListaEntrega extends RecyclerView.Adapter<AdaptadorListaEntrega.AlimentoViewHolder> {
    private LayoutInflater miInflater;
    public static ArrayList<AlimentoEntrega> estadoEntregaLista;

    public AdaptadorListaEntrega(Context contexto, ArrayList<AlimentoEntrega> listaEntrega){
        miInflater = LayoutInflater.from(contexto);
        this.estadoEntregaLista = listaEntrega;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdaptadorListaEntrega.AlimentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = miInflater.inflate(R.layout.entrega_lista, parent, false);
        AdaptadorListaEntrega.AlimentoViewHolder holder = new AdaptadorListaEntrega.AlimentoViewHolder(vista);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorListaEntrega.AlimentoViewHolder holder, final int posicion) {
        final AlimentoEntrega alimentoLista= estadoEntregaLista.get(posicion);
        holder.producto.setText(alimentoLista.getIngrediente());
        holder.unidad.setText(alimentoLista.getMedida());
        holder.cantidad.setText(alimentoLista.getCantidadentregada());
        holder.estadoGeneral.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int itemSeleccionado) {
                switch (itemSeleccionado){
                    case R.id.rbBueno:
                        break;
                    case R.id.rbMalo:
                        break;
                    case R.id.rbRegular:
                        break;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return estadoEntregaLista.size();
    }

    class AlimentoViewHolder extends RecyclerView.ViewHolder{
        protected RadioGroup estadoGeneral;
        protected RadioButton estadoBueno,estadoRegular,estadoMalo;
        protected TextView producto,unidad,cantidad;

        public AlimentoViewHolder(View itemView) {
            super(itemView);
            estadoGeneral=itemView.findViewById(R.id.rgEstado);
            estadoBueno=itemView.findViewById(R.id.rbBueno);
            estadoMalo=itemView.findViewById(R.id.rbMalo);
            estadoRegular=itemView.findViewById(R.id.rbRegular);
            producto=itemView.findViewById(R.id.listaProductoE);
            unidad=itemView.findViewById(R.id.listaUnidadE);
            cantidad=itemView.findViewById(R.id.listaCantidadE);

        }
    }
}
