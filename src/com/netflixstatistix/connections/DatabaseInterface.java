package com.netflixstatistix.connections;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseInterface{


    private ResultSet rs;


    // (INT) GET WATCH TIME -> ProgrammaID, Profielnaam
    public int getWatchTime(int programmaID, String profielnaam) throws SQLException {
        rs = DatabaseConnection.giveStatementAndGetResult("SELECT Percentage FROM BekekenProgramma WHERE ProgrammaID = '" + programmaID + "' AND Profielnaam = '" + profielnaam + "';");
        if (rs.next()) {
            return rs.getInt("Percentage");
        } else {
            return 0;
        }
    }

    // (INT) GET AMOUNT OF EPISODES PER SERIE AMOUNT -> ProgrammaID
    public int getTotalEpisodesInSerie(String serieNaam) throws SQLException {
        rs = DatabaseConnection.giveStatementAndGetResult("SELECT COUNT(Serie) AS Total FROM Aflevering WHERE Serie = '" + serieNaam + "';");
        if (rs.next()) {
            return rs.getInt("Total");
        } else {
            return 0;
        }
    }

    public String[] getProfielenFromAbonnee(int profielID) throws SQLException {
        rs = DatabaseConnection.giveStatementAndGetResult("SELECT Profielnaam FROM Profiel WHERE ProfielID = '" + profielID + "';");
        ArrayList<String> profielen = new ArrayList<String>();
        while (rs.next()) {
            profielen.add(rs.getString("Profielnaam"));
        }
        String[] array = new String[profielen.size()];
        array = profielen.toArray(array);
        return array;
    }

    public String getLongestMovieByMaxAge(int age) throws SQLException {
        rs = DatabaseConnection.giveStatementAndGetResult("SELECT Titel FROM Film WHERE GeschikteLeeftijd < " + age + " ORDER BY Tijdsduur DESC;");
        if (rs.next()) {
            return rs.getString("Titel");
        } else {
            return "Geen film gevonden";
        }

    }
}
