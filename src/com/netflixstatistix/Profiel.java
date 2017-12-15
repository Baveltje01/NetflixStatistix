package com.netflixstatistix;

import com.netflixstatistix.connections.DatabaseConnection;

import java.sql.ResultSet;

public class Profiel {
    private static int profielID = 0;
    private int thisProfielID;
    private String geboorteDatum;
    private String profielNaam;

    public Profiel(String geboorteDatum, String profielNaam)    {
        profielID++;
        this.thisProfielID = profielID;
        this.geboorteDatum = geboorteDatum;
        this.profielNaam = profielNaam;

    }

    public void addProfile(String geboorteDatum, String profielNaam)    {
    }

    public String getGeboorteDatum() {
        return geboorteDatum;
    }

    public String getProfielNaam() {
        return profielNaam;
    }

    public static int getProfielID() {
        return profielID;
    }
}

