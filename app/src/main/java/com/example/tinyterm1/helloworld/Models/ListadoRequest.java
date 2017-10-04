package com.example.tinyterm1.helloworld.Models;

import java.util.ArrayList;

/**
 * Created by TinyTerm1 on 01/01/2017.
 */

public class ListadoRequest {
    private String Usuario;
    private String TotalEntrada;
    private String TotalSalida;
    private String TotalTiempo;
    private ArrayList<Reporte> Reportes;

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        this.Usuario = usuario;
    }

    public String getTotalEntrada() {
        return TotalEntrada;
    }

    public void setTotalEntrada(String totalEntrada) {
        this.TotalEntrada = totalEntrada;
    }

    public String getTotalSalida() {
        return TotalSalida;
    }

    public void setTotalSalida(String totalSalida) {
        this.TotalSalida = totalSalida;
    }

    public String getTotalTiempo() {
        return TotalTiempo;
    }

    public void setTotalTiempo(String totalTiempo) {
        this.TotalTiempo = totalTiempo;
    }

    public ArrayList<Reporte> getReportes() {
        return Reportes;
    }

    public void setReportes(ArrayList<Reporte> reportes) {
        this.Reportes = reportes;
    }
}
