package com.dgaviria.sistur.adaptadores;

import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
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

public class AdaptadorListaCompra extends RecyclerView.Adapter<AdaptadorListaCompra.AlimentoViewHolder>{
    private LayoutInflater miInflater;
    public static ArrayList<AlimentoCompra> editValorCompraLista;

    public AdaptadorListaCompra(Context contexto, ArrayList<AlimentoCompra> listaCompras){
        miInflater = LayoutInflater.from(contexto);
        this.editValorCompraLista = listaCompras;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdaptadorListaCompra.AlimentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = miInflater.inflate(R.layout.compras_lista, parent, false);
        AlimentoViewHolder holder = new AlimentoViewHolder(vista);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AlimentoViewHolder holder, final int posicion) {
        AlimentoCompra alimentoLista=editValorCompraLista.get(posicion);
        holder.producto.setText(alimentoLista.getIngrediente());
        holder.unidad.setText(alimentoLista.getMedida());
        holder.cantidad.setText(alimentoLista.getCantidad());
        holder.valorEditar.setText(alimentoLista.getValorcompra());
        holder.total.setText(alimentoLista.getTotal());
        TextWatcher calculaTotal = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int miValorUnitario,miTotal,miCantidad;
                try {
                    //Calcula el valor total y lo guarda
                    miCantidad = Integer.valueOf(holder.cantidad.getText().toString());
                    miValorUnitario = Integer.valueOf(holder.valorEditar.getText().toString());
                    miTotal = miCantidad * miValorUnitario;
                    holder.total.setText(String.valueOf(miTotal));
                }catch (Exception e) {
                    holder.total.setText(editValorCompraLista.get(posicion).getTotal());
                }
            }
        };
        holder.valorEditar.addTextChangedListener(calculaTotal);
        if (posicion+1==getItemCount()){
            ajusteMargenInferior(holder.itemView,(int)(76* Resources.getSystem().getDisplayMetrics().density));
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
                    int miValorUnitario,miTotal,miCantidad;
                    //Guarda el valor digitado en el valor unitario
                    editValorCompraLista.get(getAdapterPosition()).setValorcompra(valorEditar.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int miValorUnitario,miTotal,miCantidad;

                    try {
                        //Calcula el valor total y lo guarda
                        miCantidad = Integer.valueOf(cantidad.getText().toString());
                        miValorUnitario = Integer.valueOf(valorEditar.getText().toString());
                        miTotal = miCantidad * miValorUnitario;
                        editValorCompraLista.get(getAdapterPosition()).setTotal(String.valueOf(miTotal));
                    }catch (Exception e) {
                        editValorCompraLista.get(getAdapterPosition()).setTotal(total.getText().toString());
                    }
                }
            });
        }
    }
    public int conteoParcial(){
        int conteo=0,valor;
        for (int items=0;items<editValorCompraLista.size();items++) {
            valor = Integer.valueOf(editValorCompraLista.get(items).getTotal());
            if (valor!=0)
                conteo += 1;
        }
        return conteo;
    }

    public int conteoTotal(){
        return editValorCompraLista.size();
    }

    public int sumaTotal(){
        int subTotal=0,valor=0;
        for (int items=0;items<editValorCompraLista.size();items++) {
            valor = Integer.valueOf(editValorCompraLista.get(items).getTotal());
            subTotal += valor;
        }
        return subTotal;
    }
}
