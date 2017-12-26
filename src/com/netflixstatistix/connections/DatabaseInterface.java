package com.netflixstatistix.connections;

import java.sql.*;

public class DatabaseInterface {


    public ResultSet rs;




    // (INT) GET WATCH TIME -> ProgrammaID, Profielnaam
    public int getWatchTime(int programmaID, String profielnaam) throws SQLException
    {
        rs = DatabaseConnection.giveStatementAndGetResult( "SELECT Percentage FROM BekekenProgramma WHERE ProgrammaID = '" + programmaID + "' AND Profielnaam = '" + profielnaam + "';");
        rs.next();
            int watchTime = rs.getInt("Percentage");
            return watchTime;
    }
}
