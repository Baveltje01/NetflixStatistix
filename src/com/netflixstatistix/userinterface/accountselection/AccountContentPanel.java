package com.netflixstatistix.userinterface.accountselection;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AccountContentPanel extends JPanel{
    public AccountContentPanel(){

        // Specifications

        Dimension size = getPreferredSize();
        size.width = 250;
        setPreferredSize(size);
        setBorder(BorderFactory.createTitledBorder("Abonnee selectie"));
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // UI components

        JLabel accountLabel = new JLabel("Abonnee: ");                          // Header
        JList<ArrayList<String>> accountList = new JList<ArrayList<String>>();       // List of available accounts
        JButton confirmationButton = new JButton("OK");                         // Button with text "OK"

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
        add(confirmationButton,gc);

    }

}
