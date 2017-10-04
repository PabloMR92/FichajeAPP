package com.example.tinyterm1.helloworld.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TinyTerm1 on 15/01/2017.
 */

public class TipoDeHorario {
    @SerializedName("TipoHorarioID")
    private int TipoHorarioID;
    @SerializedName("Descripcion")
    private String Descripcion;

    public int getTipoHorarioID() {
        return TipoHorarioID;
    }

    public void setTipoHorarioID(int tipoHorarioID) {
        TipoHorarioID = tipoHorarioID;
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
        if(obj instanceof TipoDeHorario){
            TipoDeHorario c = (TipoDeHorario )obj;
            if(c.getDescripcion().equals(Descripcion) && c.getTipoHorarioID()==TipoHorarioID ) return true;
        }

        return false;
    }
}
