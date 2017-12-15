package com.netflixstatistix;

import com.netflixstatistix.connections.DatabaseConnection;
import com.netflixstatistix.userinterface.UI;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        DatabaseConnection.connect();


        DatabaseConnection.disconnect();

        SwingUtilities.invokeLater(new UI());


    }
}
