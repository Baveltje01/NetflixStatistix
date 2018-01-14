package com.netflixstatistix.userinterface.About;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AboutContentPanel extends JPanel{
    public AboutContentPanel(){

        // Specifications

        Dimension size = getPreferredSize();
        size.width = 250;
        setPreferredSize(size);
        setBorder(BorderFactory.createTitledBorder("Abonnee selectie"));
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // UI components

        JLabel accountLabel = new JLabel("Over ons");                          // Header
        JFormattedTextField accountList = new JFormattedTextField("App gebouwd in Java door Bas van Rooten en Tom Schoonbeek");       // List of available accounts

        gc.anchor = GridBagConstraints.LINE_END;
        gc.weightx = 0.5;
        gc.weighty = 0.5;

        gc.gridx = 0;
        gc.gridy = 0;
        add(accountLabel,gc);

        gc.gridx = 0;
        gc.gridy = 1;
        add(accountList,gc);

        gc.gridx = 0;
        gc.gridy = 2;

    }

}
