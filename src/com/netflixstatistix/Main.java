package com.netflixstatistix;

import com.netflixstatistix.connections.DatabaseConnection;
import com.netflixstatistix.connections.DatabaseInterface;
import com.netflixstatistix.userinterface.UI;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) throws Exception{

        Thread.setDefaultUncaughtExceptionHandler(new ExeptionHandler());
        DatabaseConnection.connect();
        SwingUtilities.invokeLater(new UI());

        DatabaseInterface di = new DatabaseInterface();


        //Testing DatabaseInterface
        System.out.println(di.getTotalEpisodesInSerie("Sherlock"));
        System.out.println(di.getWatchTime(1001, "Frank"));
        System.out.println(di.getLongestMovieByMaxAge(16));
        System.out.println(di.getProfielenFromAbonnee(1215426));
        DatabaseConnection.disconnect();


    }
}