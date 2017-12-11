package com.netflixstatistix;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        String connectionUrl = "jdbc:sqlserver://localhost\\SQLEXPRESS;"
                                + "databaseName=Bibliotheek;integratedSecurity=true;";
        Connection con = null;                                              // Connection beheert informatie over de connectie met de database.
        Statement stmt = null;                                              // Statement zorgt dat we een SQL query kunnen uitvoeren.
        ResultSet rs = null;                                                // ResultSet is de tabel die we van de database terugkrijgen.
                                                                            // We kunnen door de rows heen stappen en iedere kolom lezen.

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  // 'Importeer' de driver die je gedownload hebt.
            con = DriverManager.getConnection(connectionUrl);               // Maak de verbinding met de database.

            String SQL = "SELECT TOP 10 * FROM Boek";                       // Stel een SQL query samen.
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);                                    // Voer de query uit op de database.

            System.out.print(String.format("| %7s | %-32s | %-24s |\n", " ", " ", " ").replace(" ", "-"));

            // Als de resultset waarden bevat dan lopen we hier door deze waarden en printen ze.
            while (rs.next()) {
                int ISBN = rs.getInt("ISBN");                   // Vraag per row de kolommen in die row op.
                String title = rs.getString("Titel");
                String author = rs.getString("Auteur");

                System.out.println(ISBN + " " + title + " " + author);     // Print de kolomwaarden.

                // Met 'format' kun je de string die je print het juiste formaat geven, als je dat wilt.
                // %d = decimal, %s = string, %-32s = string, links uitgelijnd, 32 characters breed.
                System.out.format("| %7d | %-32s | %-24s | \n", ISBN, title, author);
            }
            System.out.println(String.format("| %7s | %-32s | %-24s |\n", " ", " ", " ").replace(" ", "-"));

        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }
        SwingUtilities.invokeLater(new UI());

    }
}