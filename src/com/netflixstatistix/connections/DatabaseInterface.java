package com.netflixstatistix.connections;

import java.sql.*;
import java.time.LocalDateTime;
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

    // (String[]) GET ALL MOVIES VIEWED BY SINGLE ACCOUNT(via AbonneeID)
    public String[] getAllMoviesViewedByAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Film.Titel FROM Film JOIN Programma ON Film.ProgrammaID = Programma.ProgrammaID JOIN BekekenProgramma ON Programma.ProgrammaID = BekekenProgramma.ProgrammaID JOIN Profiel ON Profiel.Profielnaam = BekekenProgramma.Profielnaam AND Profiel.Geboortedatum = BekekenProgramma.Geboortedatum JOIN Abonnee ON Abonnee.AbonneeID = Profiel.AbonneeID WHERE Abonnee.AbonneeID = '" + AbonneeID + "'");
            ArrayList<String> moviesViewedByAccount = new ArrayList<String>();
            while (rs.next()) {
                moviesViewedByAccount.add(rs.getString("Profielnaam"));
            }
            String[] array = new String[moviesViewedByAccount.size()];
            array = moviesViewedByAccount.toArray(array);
            return array;

        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            String[] array2 = new String[0];
            Arrays.fill(array2, "Error fetching list of movies watched by this account ID");
            return array2;
        }
    }


    // (String[]) GET ALL ACCOUNTS WITH ONLY A SINGLE PROFILE
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

    // (int) GET TOTAL QTY OF PROFILES ('Profiel' IN DATABASE) WHO VIEWED SPECIFIC MOVIE FOR FULL DURATION (100%)
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

    // ADD ACCOUNT (AbonneeID = PK)
    public void addAccount (int AbonneeID, String Email, String Wachtwoord, String Naam, String Straat, String Huisnummer, String Postcode, String Woonplaats) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("INSERT INTO Abonnee (AbonneeID,Email,Wachtwoord,Naam,Straat,Huisnummer,Postcode,Woonplaats) VALUES ('" + AbonneeID + "','" + Email + "','" + Wachtwoord + "','" + Naam + "','" + Straat + "','" + Huisnummer + "','" + Postcode + "','" + Woonplaats + "');");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // DELETE ACCOUNT
    public void deleteAccount (int AbonneeID) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("DELETE FROM Abonnee WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ACCOUNT ADJUST EMAIL
    public void changeAccountEmail (int AccountID, String newEmail) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("UPDATE Abonnee SET Abonnee.Email = '" + newEmail + "' WHERE  Abonnee.AbonneeID = '" + AccountID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ACCOUNT ADJUST PASSWORD
    public void changeAccountPassword (int AccountID, String newPassword) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("UPDATE Abonnee SET Abonnee.Wachtwoord = '" + newPassword + "' WHERE  Abonnee.AbonneeID = '" + AccountID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ACCOUNT ADJUST NAME
    public void changeAccountName (int AccountID, String newName) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("UPDATE Abonnee SET Abonnee.Naam = '" + newName + "' WHERE  Abonnee.AbonneeID = '" + AccountID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ACCOUNT ADJUST STREET
    public void changeAccountStreet (int AccountID, String newStreet) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("UPDATE Abonnee SET Abonnee.Straat = '" + newStreet + "' WHERE  Abonnee.AbonneeID = '" + AccountID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ACCOUNT ADJUST HOUSENUMBER
    public void changeAccountHouseNumber (int AccountID, String newHouseNumber) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("UPDATE Abonnee SET Abonnee.Huisnummer = '" + newHouseNumber + "' WHERE  Abonnee.AbonneeID = '" + AccountID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ACCOUNT ADJUST ZIP CODE
    public void changeAccountZIP (int AccountID, String newZIP) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("UPDATE Abonnee SET Abonnee.Postcode = '" + newZIP + "' WHERE  Abonnee.AbonneeID = '" + AccountID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ACCOUNT ADJUST CITY
    public void changeCity (int AccountID, String newCity) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("UPDATE Abonnee SET Abonnee.Woonplaats = '" + newCity + "' WHERE  Abonnee.AbonneeID = '" + AccountID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ADD PROFILE TO ACCOUNT (Profielnaam + Geboortedatum = Composite PK. ProfielID = FK to Abonnee.AbonneeID)
    public void addProfile (String Profielnaam, String Geboortedatum, int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("INSERT INTO Profiel (Profielnaam,Geboortedatum,AbonneeID) VALUES ('" + Profielnaam + "','" + Geboortedatum + "','" + AbonneeID + "');");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // DELETE PROFILE FROM ACCOUNT
    public void deleteProfile (String Profielnaam, String Geboortedatum) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("DELETE FROM Profiel WHERE Profiel.Profielnaam = '" + Profielnaam + "' AND Profiel.Geboortedatum = '" + Geboortedatum + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // PROFILE NAME ADJUST
    public void changeProfileName (String nieuweProfielnaam, String oudeProfielnaam, String Geboortedatum) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("UPDATE Abonnee SET Profiel.Profielnaam = '" + nieuweProfielnaam + "' WHERE  Profiel.Profielnaam = '" + oudeProfielnaam + "' AND Profiel.Geboortedatum = '" + Geboortedatum + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // PROFILE DATE OF BIRTH ADJUST
    public void changeProfileDateOfBirth (String nieuweGeboortedatum, String Profielnaam, String oudeGeboortedatum) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("UPDATE Abonnee SET Profiel.Geboortedatum = '" + nieuweGeboortedatum + "' WHERE  Profiel.Profielnaam = '" + Profielnaam + "' AND Profiel.Geboortedatum = '" + oudeGeboortedatum + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ADD VIEWED PROGRAM
    public void addWatchedProgram (int Percentage, String Profielnaam, String Geboortedatum, int ProgrammaID) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("INSERT INTO BekekenProgramma(Percentage,Profielnaam,Geboortedatum,ProgrammaID,LaatstBekeken) VALUES ('" + Percentage + "','" + Profielnaam + "','" + Geboortedatum + "','" + ProgrammaID + "','" + LocalDateTime.now() + "');");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // DELETE VIEWED PROGRAM
    public void deleteWatchedProgram (String Profielnaam, String Geboortedatum, int ProgrammaID) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("DELETE FROM BekekenProgramma WHERE BekekenProgramma.Profielnaam = '" + Profielnaam + "' AND BekekenProgramma.Geboortedatum = '" + Geboortedatum + " AND BekekenProgramma.ProgrammaID = '" + ProgrammaID + "'';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ADJUST PERCENTAGE VIEWED OF VIEWED PROGRAM AND LAST VIEWED MOMENT TO CURRENT TIMESTAMP
    public void adjustWatchedPercentage (int NieuwPercentage, String Profielnaam, String Geboortedatum, int ProgrammaID) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("UPDATE BekekenProgramma SET BekekenProgramma.Percentage = '" + NieuwPercentage + "' WHERE BekekenProgramma.Profielnaam = '" + Profielnaam + "' AND BekekenProgramma.Geboortedatum = '" + Geboortedatum + " AND BekekenProgramma.ProgrammaID = '" + ProgrammaID + "''; UPDATE BekekenProgramma SET BekekenProgramma.LaatstBekeken = '" + LocalDateTime.now() + "' WHERE BekekenProgramma.Profielnaam = '" + Profielnaam + "' AND BekekenProgramma.Geboortedatum = '" + Geboortedatum + " AND BekekenProgramma.ProgrammaID = '" + ProgrammaID + "'';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // SELECT TOP 1 ACCOUNT (FOR STARTING REFERENCE)
    public String getTopAccount() {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT TOP 1 Abonnee.Naam FROM Abonnee;");
            if (rs.next()) {
                return rs.getString("Top 1 account");
            } else {
                return "Geen account gevonden";
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return "Error fetching top account";
        }
    }


















}
