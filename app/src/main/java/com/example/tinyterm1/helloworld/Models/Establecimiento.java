package com.example.tinyterm1.helloworld.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TinyTerm1 on 15/01/2017.
 */

public class Establecimiento {
    @SerializedName("TimeSheetLocationID")
    private int EstablecimientoID;
    @SerializedName("Descripcion")
    private String Descripcion;

    public int getEstablecimientoID() {
        return EstablecimientoID;
    }

    public void setEstablecimientoID(int tipoHorarioID) {
        EstablecimientoID = tipoHorarioID;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    @Override
    public String toString() {
        return Descripcion;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Establecimiento){
            Establecimiento c = (Establecimiento)obj;
            if(c.getDescripcion().equals(Descripcion) && c.getEstablecimientoID()==EstablecimientoID ) return true;
        }

        return false;
    }
}
