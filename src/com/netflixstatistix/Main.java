package com.netflixstatistix;

import com.netflixstatistix.connections.DatabaseConnection;
import com.netflixstatistix.userinterface.UI;
import javax.swing.SwingUtilities;
import com.netflixstatistix.debug.Debugger;

public class Main {

    public static void main(String[] args){
        DatabaseConnection.connect();

        Debugger debug = new Debugger();
        debug.run();

        SwingUtilities.invokeLater(new UI());
        DatabaseConnection.disconnect();


    }
}