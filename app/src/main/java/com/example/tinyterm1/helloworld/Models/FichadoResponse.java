package com.example.tinyterm1.helloworld.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TinyTerm1 on 04/01/2017.
 */

public class FichadoResponse {
    @SerializedName("Mensaje")
    private String Mensaje;

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String descripcion) {
        Mensaje = descripcion;
    }
}
