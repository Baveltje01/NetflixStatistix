package com.netflixstatistix.connections;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseInterface {


    private ResultSet rs;


    // (INT) GET WATCH TIME
    public int getWatchTime(int programmaID, String profielnaam) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Percentage FROM BekekenProgramma WHERE ProgrammaID = '" + programmaID + "' AND Profielnaam = '" + profielnaam + "';");
            if (rs.next()) {
                return rs.getInt("Percentage");
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return 0;
        }

    }

    // (INT) GET AMOUNT OF EPISODES PER SERIE AMOUNT
    public int getTotalEpisodesInSerie(String serieNaam) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT COUNT(Serie) AS Total FROM Aflevering WHERE Serie = '" + serieNaam + "';");
            if (rs.next()) {
                return rs.getInt("Total");
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. "  + e.getMessage());
            return 0;
        }
    }

    // (String[]) GET PROFIELEN FROM ABONNEE
    public String[] getProfielenFromAbonnee(int profielID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Profielnaam FROM Profiel WHERE ProfielID = '" + profielID + "';");
            ArrayList<String> profielen = new ArrayList<String>();
            while (rs.next()) {
                profielen.add(rs.getString("Profielnaam"));
            }
            String[] array = new String[profielen.size()];
            array = profielen.toArray(array);
            return array;

        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            String[] array2 = new String[0];
            Arrays.fill(array2, "Error fetching profiles");
            return array2;
        }
    }

    // (STRING) GET LONGEST MOVIE BY MAX AGE -> age
    public String getLongestMovieByMaxAge(int age) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Titel FROM Film WHERE GeschikteLeeftijd < " + age + " ORDER BY Tijdsduur DESC;");
            if (rs.next()) {
                return rs.getString("Titel");
            } else {
                return "Geen film gevonden";
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return "Error fetching movie";
        }
    }



    // GET ALLE ACCOUNTS MET SLECHTS 1 ENKEL PROFIEL
    public String[] getAccountsWithSingleProfile () {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Abonnee.Naam FROM Profiel INNER JOIN Abonnee ON Profiel.ProfielID = Abonnee.AbonneeID GROUP BY Abonnee.Naam HAVING COUNT(*) = 1;");
            int i = 0;
            String[] accountsWithSingleProfile = new String[rs.getMetaData().getColumnCount()];
            if (!rs.next()) {
                return null;
            }
            while (rs.next()) {
                accountsWithSingleProfile[i] = rs.getString("Naam");
                i++;
            }
            return accountsWithSingleProfile;
        } catch(Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return null;
        }
    }

    // GET TOTAAL AANTAL PROFIELEN DIE SPECIFIEKE FILM IN GEHEEL (100%) HEBBEN BEKEKEN
    public int getHowManyViewersViewedThisMovieCompletely (String movie) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT COUNT(Film.Titel) FROM BekekenProgramma INNER JOIN Film ON Film.ProgrammaID = Bekekenprogramma.ProgrammaID WHERE (BekekenProgramma.Percentage = '100') AND (Film.Titel = '" + movie + "');");

            if (!rs.next()) {
                return 0;
            }

            return  rs.getInt(1);
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return 0;
        }
    }


}
