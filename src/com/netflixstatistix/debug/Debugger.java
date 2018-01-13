package com.netflixstatistix.debug;

import com.netflixstatistix.connections.DatabaseConnection;
import com.netflixstatistix.connections.DatabaseInterface;

import java.util.Arrays;

public class Debugger {

    public void run() {
        DatabaseConnection.connect();

        DatabaseInterface di = new DatabaseInterface();

        System.out.println(di.getTotalEpisodesInSerie("Sherlock"));
        System.out.println(di.getWatchTime(1001, "Frank"));
        System.out.println(di.getLongestMovieByMaxAge(16));
        System.out.println(Arrays.toString(di.getProfielenFromAbonnee(1215426)));
        System.out.println(di.getHowManyViewersViewedThisMovieCompletely("The Life of Brian"));
        System.out.println(Arrays.toString(di.getAccountsWithSingleProfile()));

        DatabaseConnection.disconnect();

    }
}
