package com.example.tinyterm1.helloworld.ApiCall;

import com.example.tinyterm1.helloworld.Models.Establecimiento;
import com.example.tinyterm1.helloworld.Models.ListadoRequest;

import java.util.ArrayList;

public interface EstablecimientosSuccessInterface {
    void GetEstablecimientos(ArrayList<Establecimiento> establecimientos);
}

