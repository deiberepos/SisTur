package com.dgaviria.sistur.adaptadores;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dgaviria.sistur.R;
import com.dgaviria.sistur.clases.AlimentoCompra;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorListaCompra extends RecyclerView.Adapter<AdaptadorListaCompra.AlimentoViewHolder>{
    private LayoutInflater miInflater;
    public static ArrayList<AlimentoCompra> editValorCompraLista;


    public AdaptadorListaCompra(Context contexto, ArrayList<AlimentoCompra> listaCompras){

        miInflater = LayoutInflater.from(contexto);
        this.editValorCompraLista = listaCompras;
    }

    @NonNull
    @Override
    public AdaptadorListaCompra.AlimentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = miInflater.inflate(R.layout.compras_lista, parent, false);
        AlimentoViewHolder holder = new AlimentoViewHolder(vista);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlimentoViewHolder holder, int position) {
        holder.numItem.setText(editValorCompraLista.get(position).getOrden());
        holder.producto.setText(editValorCompraLista.get(position).getIngrediente());
        holder.unidad.setText(editValorCompraLista.get(position).getMedida());
        holder.cantidad.setText(editValorCompraLista.get(position).getCantidad());
        holder.total.setText(editValorCompraLista.get(position).getTotal());
        holder.valorEditar.setText(editValorCompraLista.get(position).getEditValorCompra());
        //Log.d("print","yes");
    }

    @Override
    public int getItemCount() {
        return editValorCompraLista.size();
    }

    class AlimentoViewHolder extends RecyclerView.ViewHolder{

        protected EditText valorEditar;
        protected TextView producto,unidad,cantidad,total,numItem;

        public AlimentoViewHolder(View itemView) {
            super(itemView);

            valorEditar = itemView.findViewById(R.id.editValor);
            numItem=itemView.findViewById(R.id.itemLista);
            producto=itemView.findViewById(R.id.listaProducto);
            unidad=itemView.findViewById(R.id.listaUnidad);
            cantidad=itemView.findViewById(R.id.listaCantidad);
            total=itemView.findViewById(R.id.listaTotal);

            valorEditar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    editValorCompraLista.get(getAdapterPosition()).setEditValorCompra(valorEditar.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
}
