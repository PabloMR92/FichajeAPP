package com.example.tinyterm1.helloworld.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tinyterm1.helloworld.Models.Reporte;
import com.example.tinyterm1.helloworld.R;

import java.util.ArrayList;

/**
 * Created by TinyTerm1 on 02/01/2017.
 */

public class ReporteListAdapter extends BaseAdapter {
    private ArrayList<Reporte> listData;
    private LayoutInflater layoutInflater;

    public ReporteListAdapter(Context aContext, ArrayList<Reporte> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData != null ? listData.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.lista_fila_reporte_layout, parent, false);
            holder = new ViewHolder();
            holder.fecha = (TextView) convertView.findViewById(R.id.fecha);
            holder.entrada = (TextView) convertView.findViewById(R.id.fechaEntrada);
            holder.salida = (TextView) convertView.findViewById(R.id.fechaSalida);
            holder.observaciones = (TextView) convertView.findViewById(R.id.observacionesDesc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.fecha.setText(listData.get(position).getFecha());
        holder.entrada.setText(listData.get(position).getEntrada());
        holder.salida.setText(listData.get(position).getSalida());
        holder.observaciones.setText(listData.get(position).getDescripcion());
        return convertView;
    }

    static class ViewHolder {
        TextView fecha;
        TextView entrada;
        TextView salida;
        TextView observaciones;
    }
}
