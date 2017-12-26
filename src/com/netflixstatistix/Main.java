package com.netflixstatistix;

import com.netflixstatistix.connections.DatabaseConnection;
import com.netflixstatistix.userinterface.UI;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        DatabaseConnection.connect();
        SwingUtilities.invokeLater(new UI());
        DatabaseConnection.disconnect();


    }
}