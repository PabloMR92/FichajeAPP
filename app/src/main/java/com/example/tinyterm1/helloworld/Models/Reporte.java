package com.example.tinyterm1.helloworld.Models;

/**
 * Created by TinyTerm1 on 01/01/2017.
 */

public class Reporte {
    private String Descripcion;
    private String Entrada;
    private String Salida;
    private String Total;
    private String Fecha;

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = descripcion;
    }

    public String getEntrada() {
        return Entrada;
    }

    public void setEntrada(String entrada) {
        this.Entrada = entrada;
    }

    public String getSalida() {
        return Salida;
    }

    public void setSalida(String salida) {
        this.Salida = salida;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        this.Total = total;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        this.Fecha = fecha;
    }
}
