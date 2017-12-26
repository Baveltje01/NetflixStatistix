package com.netflixstatistix.connections;

import java.sql.*;

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
        int i = 0;
        String[] profielen = new String[rs.getMetaData().getColumnCount()];
        while (rs.next()) {
            profielen[i] = rs.getString("Profielnaam");
            i++;
        }
        return profielen;
    }
}
