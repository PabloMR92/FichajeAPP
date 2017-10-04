package com.example.tinyterm1.helloworld.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TinyTerm1 on 16/01/2017.
 */

public class Domain {
    @SerializedName("TimeSheetDomainID")
    private int TimeSheetDomainID;
    @SerializedName("Domain")
    private String Domain;
    @SerializedName("Descripcion")
    private String Descripcion;

    public int getTimeSheetDomainID() {
        return TimeSheetDomainID;
    }

    public void setTimeSheetDomainID(int timeSheetDomainID) {
        TimeSheetDomainID = timeSheetDomainID;
    }

    public String getDomain() {
        return Domain;
    }

    public void setDomain(String domain) {
        Domain = domain;
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
        if(obj instanceof Domain){
            Domain c = (Domain)obj;
            if(c.getDescripcion().equals(Descripcion) && c.getTimeSheetDomainID()==TimeSheetDomainID ) return true;
        }

        return false;
    }
}
