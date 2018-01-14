package com.netflixstatistix.connections;

import java.sql.*;
import java.time.LocalDateTime;//fap
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseInterface {


    private ResultSet rs;


    // (INT) GET WATCH TIME
    public int getWatchTime(int ProgrammaID, String Profielnaam, int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Percentage FROM BekekenProgramma WHERE ProgrammaID = '" + ProgrammaID + "' AND Profielnaam = '" + Profielnaam + "' AND AbonneeID = '" + AbonneeID + "';");
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
    public String[] getProfielenFromAbonnee(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Profielnaam FROM Profiel JOIN Abonnee ON Abonnee.AbonneeID = Profiel.AbonneeID WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
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
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT DISTINCT Film.Titel FROM Film JOIN Programma ON Film.ProgrammaID = Programma.ProgrammaID JOIN BekekenProgramma ON Programma.ProgrammaID = BekekenProgramma.ProgrammaID JOIN Profiel ON Profiel.Profielnaam = BekekenProgramma.Profielnaam JOIN Abonnee ON Abonnee.AbonneeID = Profiel.AbonneeID WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
            ArrayList<String> moviesViewedByAccount = new ArrayList<String>();
            while (rs.next()) {
                moviesViewedByAccount.add(rs.getString("Titel"));
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
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Abonnee.Naam FROM Profiel JOIN Abonnee ON Profiel.AbonneeID = Abonnee.AbonneeID GROUP BY Abonnee.Naam HAVING COUNT(*) = 1;");
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

    // ADD PROFILE TO ACCOUNT (Profielnaam + AbonneeID = Composite PK. ProfielID = FK to Abonnee.AbonneeID)
    public void addProfile (String Profielnaam, String Geboortedatum, int AbonneeID) {
        try {
            if(getAmountOfProfilesOnAccount(AbonneeID) < 5){
                rs = DatabaseConnection.giveStatementAndGetResult("INSERT INTO Profiel (Profielnaam,Geboortedatum,AbonneeID) VALUES ('" + Profielnaam + "','" + Geboortedatum + "','" + AbonneeID + "');");
            } else {
                System.out.println("Profile cannot be added. Max number of profiles on account " + AbonneeID + " reached.");
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // DELETE PROFILE FROM ACCOUNT
    public void deleteProfile (String Profielnaam, int AbonneeID) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("DELETE FROM Profiel WHERE Profiel.Profielnaam = '" + Profielnaam + "' AND Profiel.AbonneeID = '" + AbonneeID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // PROFILE NAME ADJUST
    public void changeProfileName (String nieuweProfielnaam, String oudeProfielnaam, int AbonneeID) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("UPDATE Abonnee SET Profiel.Profielnaam = '" + nieuweProfielnaam + "' WHERE  Profiel.Profielnaam = '" + oudeProfielnaam + "' AND Profiel.AbonneeID = '" + AbonneeID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // PROFILE DATE OF BIRTH ADJUST
    public void changeProfileDateOfBirth (String nieuweGeboortedatum, String Profielnaam, int AbonneeID) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("UPDATE Abonnee SET Profiel.Geboortedatum = '" + nieuweGeboortedatum + "' WHERE  Profiel.Profielnaam = '" + Profielnaam + "' AND Profiel.AbonneeID = '" + AbonneeID + "';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ADD VIEWED PROGRAM
    public void addWatchedProgram (int Percentage, String Profielnaam, int AbonneeID, int ProgrammaID, String Titel) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("INSERT INTO BekekenProgramma(Percentage,Profielnaam,AbonneeID,ProgrammaID,Titel,LaatstBekeken) VALUES ('" + Percentage + "','" + Profielnaam + "','" + AbonneeID + "','" + ProgrammaID + "','" + Titel + "','" + LocalDateTime.now() + "');");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // DELETE VIEWED PROGRAM
    public void deleteWatchedProgram (String Profielnaam, int AbonneeID, int ProgrammaID) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("DELETE FROM BekekenProgramma WHERE BekekenProgramma.Profielnaam = '" + Profielnaam + "' AND BekekenProgramma.AbonneeID = '" + AbonneeID + " AND BekekenProgramma.ProgrammaID = '" + ProgrammaID + "'';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // ADJUST PERCENTAGE VIEWED OF VIEWED PROGRAM AND LAST VIEWED MOMENT TO CURRENT TIMESTAMP
    public void adjustWatchedPercentage (int NieuwPercentage, String Profielnaam, int AbonneeID, int ProgrammaID) {
        try{
            rs = DatabaseConnection.giveStatementAndGetResult("UPDATE BekekenProgramma SET BekekenProgramma.Percentage = '" + NieuwPercentage + "' WHERE BekekenProgramma.Profielnaam = '" + Profielnaam + "' AND BekekenProgramma.AbonneeID = '" + AbonneeID + " AND BekekenProgramma.ProgrammaID = '" + ProgrammaID + "''; UPDATE BekekenProgramma SET BekekenProgramma.LaatstBekeken = '" + LocalDateTime.now() + "' WHERE BekekenProgramma.Profielnaam = '" + Profielnaam + "' AND BekekenProgramma.AbonneeID = '" + AbonneeID + " AND BekekenProgramma.ProgrammaID = '" + ProgrammaID + "'';");
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
        }
    }

    // (String) GET TOP 1 ACCOUNT (FOR STARTING REFERENCE)
    public String getTopAccount() {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT TOP 1 Abonnee.Naam FROM Abonnee;");
            if (rs.next()) {
                return rs.getString("Naam");
            } else {
                return "Geen account gevonden";
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return "Error fetching top account";
        }
    }

    // (String[]) GET TOP 10 LAST VIEWED MOVIES AND EPISODES FROM PROFILE
    public String[] getTopTenLastViewedMoviesAndSeries(String Profielnaam, int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT TOP 10 BekekenProgramma.Titel AS LaatstBekeken FROM BekekenProgramma WHERE BekekenProgramma.Profielnaam = '" + Profielnaam + "' AND BekekenProgramma.AbonneeID = '" + AbonneeID + "' ORDER BY BekekenProgramma.LaatstBekeken DESC;");
            ArrayList<String> lastViewedMoviesAndSeries = new ArrayList<String>();
            while (rs.next()) {
                lastViewedMoviesAndSeries.add(rs.getString("LaatstBekeken"));
            }
            String[] array = new String[lastViewedMoviesAndSeries.size()];
            array = lastViewedMoviesAndSeries.toArray(array);
            return array;

        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            String[] array2 = new String[0];
            Arrays.fill(array2, "Error fetching top 10 last viewed");
            return array2;
        }
    }

    // (String[]) GET LIST OF ACCOUNT NAMES
    public String[] getListOfAccountNames() {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Abonnee.Naam FROM Abonnee;");
            ArrayList<String> listOfAccountNames = new ArrayList<String>();
            while (rs.next()) {
                listOfAccountNames.add(rs.getString("Naam"));
            }
            String[] array = new String[listOfAccountNames.size()];
            array = listOfAccountNames.toArray(array);
            return array;

        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            String[] array2 = new String[0];
            Arrays.fill(array2, "Error fetching list of account names");
            return array2;
        }
    }

    // (String[]) GET LIST OF PROFILE NAMES FROM ACCOUNT
    public String[] getListOfProfilesFromAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Profiel.Naam FROM Abonnee JOIN Abonnee ON Abonnee.AbonneeID = Profiel.AbonneeID WHERE Abonnee.AbonneeID = '"  + AbonneeID +  "';");
            ArrayList<String> listOfProfilesFromAccount = new ArrayList<String>();
            while (rs.next()) {
                listOfProfilesFromAccount.add(rs.getString("Naam"));
            }
            String[] array = new String[listOfProfilesFromAccount.size()];
            array = listOfProfilesFromAccount.toArray(array);
            return array;

        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            String[] array2 = new String[0];
            Arrays.fill(array2, "Error fetching list of profile names");
            return array2;
        }
    }

    // (int) GET AMOUNT OF PROFILES ON ACCOUNT
    public int getAmountOfProfilesOnAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT COUNT(Profiel.Profielnaam) AS Total FROM Abonnee JOIN Profiel ON Abonnee.AbonneeID = Profiel.AbonneeID WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
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

    // (STRING) GET EMAIL FROM ACCOUNT
    public String getEmailFromAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Email FROM Abonnee WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Email");
            } else {
                return "Geen Email gevonden";
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return "Error fetching Email from Account";
        }
    }

    // (STRING) GET PASSWORD FROM ACCOUNT
    public String getPasswordFromAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Wachtwoord FROM Abonnee WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Wachtwoord");
            } else {
                return "Geen Wachtwoord gevonden";
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return "Error fetching Password from Account";
        }
    }

    // (STRING) GET NAME FROM ACCOUNT
    public String getNameFromAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Naam FROM Abonnee WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Naam");
            } else {
                return "Geen Naam gevonden";
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return "Error fetching Name from Account";
        }
    }

    // (STRING) GET STREET FROM ACCOUNT
    public String getStreetFromAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Straat FROM Abonnee WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Straat");
            } else {
                return "Geen Straat gevonden";
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return "Error fetching Street from Account";
        }
    }

    // (STRING) GET HOUSENUMBER FROM ACCOUNT
    public String getHouseNumberFromAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Huisnummer FROM Abonnee WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Huisnummer");
            } else {
                return "Geen Huisnummer gevonden";
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return "Error fetching HouseNumber from Account";
        }
    }

    // (STRING) GET ZIPCODE FROM ACCOUNT
    public String getZipCodeFromAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Postcode FROM Abonnee WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Postcode");
            } else {
                return "Geen Postcode gevonden";
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return "Error fetching Zip code from Account";
        }
    }

    // (STRING) GET CITY FROM ACCOUNT
    public String getCityFromAccount(int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Woonplaats FROM Abonnee WHERE Abonnee.AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Woonplaats");
            } else {
                return "Geen Woonplaats gevonden";
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return "Error fetching City from Account";
        }
    }

    // (STRING) GET NAME FROM PROFILE
    public String getNameFromProfile(String Profielnaam, int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Profielnaam FROM Profiel WHERE Profielnaam = '" + Profielnaam + "' AND AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Profielnaam");
            } else {
                return "Geen Naam gevonden";
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return "Error fetching Name from Profile";
        }
    }

    // (STRING) GET DATEOFBIRTH FROM PROFILE
    public String getDateOfBirthFromProfile(String Profielnaam, int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Geboortedatum FROM Profiel WHERE Profielnaam = '" + Profielnaam + "' AND AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Geboortedatum");
            } else {
                return "Geen Geboortedatum gevonden";
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return "Error fetching Date of Birth from Profile";
        }
    }

    // (STRING) GET ATTACHED ACCOUNT NAME FROM PROFILE
    public String getAttachedAccountNameFromProfile(String Profielnaam, int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Abonnee.Naam FROM Profiel JOIN Abonnee ON Abonnee.AbonneeID = Profiel.AbonneeID WHERE Profielnaam = '" + Profielnaam + "' AND AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("Naam");
            } else {
                return "Geen AbonneeNaam gevonden";
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return "Error fetching attached AbonneeNaam from Profile";
        }
    }

    // (STRING) GET ATTACHED ACCOUNT ID FROM PROFILE
    public String getAttachedAccountIDFromProfile(String Profielnaam, int AbonneeID) {
        try {
            rs = DatabaseConnection.giveStatementAndGetResult("SELECT Abonnee.AbonneeID FROM Profiel JOIN Abonnee ON Abonnee.AbonneeID = Profiel.AbonneeID WHERE Profielnaam = '" + Profielnaam + "' AND AbonneeID = '" + AbonneeID + "';");
            if (rs.next()) {
                return rs.getString("AbonneeID");
            } else {
                return "Geen AbonneeID gevonden";
            }
        } catch (Exception e) {
            System.out.println("An Error Occurred.. " + e.getMessage());
            return "Error fetching attached AbonneeID from Profile";
        }
    }



}

