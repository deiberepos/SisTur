package com.dgaviria.sistur.adaptadores;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.dgaviria.sistur.R;
import com.dgaviria.sistur.clases.GrupoMinuta;

public class AdaptadorListaMinutas extends BaseExpandableListAdapter {
    private final SparseArray<GrupoMinuta> misGrupos;
    public LayoutInflater miInflater;
    public Activity miActividad;

    public AdaptadorListaMinutas(Activity actividad,SparseArray<GrupoMinuta> grupos){
        miActividad=actividad;
        this.misGrupos=grupos;
        miInflater=actividad.getLayoutInflater();
    }

    @Override
    public Object getChild(int posicionPrepara, int posicionIngrediente) {
        return misGrupos.get(posicionPrepara).grupoPrepara.get(posicionIngrediente);
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public View getChildView(int posicionPrepara,final int posicionIngrediente, boolean esUltimoIngrediente, View vista, ViewGroup nombrePrepara) {
        final String nombreAlimento = (String) getChild(posicionPrepara, posicionIngrediente);
        TextView item1 = null;

        if (vista == null) {
            vista = miInflater.inflate(R.layout.lista_ingredientes,null);
        }
        item1 = vista.findViewById(R.id.txtIngrediente);

        item1.setText(nombreAlimento);

        vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(miActividad, nombreAlimento, Toast.LENGTH_SHORT).show();
            }
        });
        return vista;
    }


    @Override
    public int getChildrenCount(int posicionPrepara) {
        return misGrupos.get(posicionPrepara).grupoPrepara.size();
    }

    @Override
    public Object getGroup(int posicionPrepara) {
        return misGrupos.get(posicionPrepara);
    }

    @Override
    public int getGroupCount() {
        return misGrupos.size();
    }

    @Override
    public void onGroupCollapsed(int posicionPrepara) {
        super.onGroupCollapsed(posicionPrepara);
    }

    @Override
    public void onGroupExpanded(int posicionPrepara) {
        super.onGroupExpanded(posicionPrepara);
    }

    @Override
    public long getGroupId(int posicionPrepara) {
        return 0;
    }

    @Override
    public View getGroupView(int posicionPrepara, boolean estaExpandido, View vista, ViewGroup nombrePreparacion) {
        if (vista == null) {
            vista = miInflater.inflate(R.layout.lista_preparaciones, null);
        }
        GrupoMinuta miGrupo = (GrupoMinuta) getGroup(posicionPrepara);
        ((CheckedTextView) vista).setText(miGrupo.textoGrupo);
        ((CheckedTextView) vista).setChecked(estaExpandido);
        return vista;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int posicionGrupo, int posicionHijo) {
        return false;
    }
}
