//package com.netflixstatistix;
//
//import com.netflixstatistix.connections.DatabaseConnection;
//import sun.java2d.cmm.Profile;
//import java.sql.ResultSet;
//
//public class Abonnee {
//    private static int abonneeID = 0;
//    private int thisAbonneeID;
//    private Adres adres;
//
//    public Abonnee(String straatNaam, int huisNummer, String postCode) {
//        abonneeID++;
//        this.thisAbonneeID = abonneeID;
//        this.adres = new Adres(straatNaam, huisNummer, postCode);
//    }
//
//    public Profile getProfilesOfAccount() {
//        ResultSet resultSet = DatabaseConnection.giveStatementAndGetResult("SELECT * FROM Profiel WHERE ProfielID = " + thisAbonneeID);
//
//        try {
//            while (resultSet.next()) {
//
//            }
//        }
//        catch (Exception e) {
//
//        }
//        return null;
//    }
//
//    public boolean addNewProfile(String profielNaam, String geboorteDatum) {
//        String SQL = "SELECT COUNT(*) FROM Profiel";
//        ResultSet rs = DatabaseConnection.giveStatementAndGetResult(SQL);
//        boolean output = false;
//
//        try {
//            if (){
//                ResultSet resultSet = DatabaseConnection.giveStatementAndGetResult(
//                        "INSERT INTO Profiel VALUES (" + profielNaam + ", " + geboorteDatum + ", " + thisAbonneeID);
//                output = true;
//            } else  {
//                // THROW EXCEPTION
//            }
//        }
//        catch (Exception e) {
//            output = false;
//        }
//
//        return output;
//    }
//
//    public int getAbonnementID() {
//        return thisAbonneeID;
//    }
//
//    public Adres getAdres() {
//        return adres;
//    }
//}
