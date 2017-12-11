package com.netflixstatistix;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class UI implements Runnable {

    private JFrame frame;

    private JMenuBar topMenuBar;
        private JMenu accountMenu;
            private JMenuItem loginMenuItem;
            private JMenuItem logoutMenuItem;
        private JMenu dataMenu;
            private JMenuItem refreshDataMenuItem;
        private JMenu infoMenu;
            private JMenuItem avansItemMenu;
            private JMenuItem aboutItemMenu;

    private boolean loggedIn;

    @Override
    public void run() {
        this.frame = new JFrame("Netflix Statistix");
        this.frame.setPreferredSize(new Dimension(800, 800));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(frame.getContentPane());


        // For testing purposes set loggedIn to true                                                //<< TESTING SETTING
        loggedIn = false;

        frame.pack();
        frame.setVisible(true);
    }

    private void createComponents(Container container) {
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());


//MENU
        // build the menu bar container
        JMenuBar topMenuBar = new JMenuBar();

        // ///////////////////////////////// //
        // Build the account menu            //
        // ///////////////////////////////// //
        accountMenu = new JMenu("Account");

        // login
        loginMenuItem = new JMenuItem("Inloggen");
        loginMenuItem.setEnabled(loggedIn);                                                           //<< TESTING SETTING
        accountMenu.add(loginMenuItem);

        // logout
        logoutMenuItem = new JMenuItem("Afmelden");
        logoutMenuItem.setEnabled(!loggedIn);                                                           //<< TESTING SETTING
        accountMenu.add(logoutMenuItem);

        // ///////////////////////////////// //
        // Build the data menu               //
        // ///////////////////////////////// //
        dataMenu = new JMenu("Gegevens");

        // Refresh gegevens
        refreshDataMenuItem = new JMenuItem("Refresh gegevens");
        dataMenu.add(refreshDataMenuItem);

        // add menus to menubar
        topMenuBar.add(accountMenu);
        topMenuBar.add(dataMenu);



        // ///////////////////////////////// //
        // Build the info menu               //
        // ///////////////////////////////// //
        infoMenu = new JMenu("Info");

        // avans website
        avansItemMenu = new JMenuItem("Avans website");
        infoMenu.add(avansItemMenu);

        // about the app
        aboutItemMenu = new JMenuItem("Over deze app");
        infoMenu.add(aboutItemMenu);


        // add menus to menubar
        topMenuBar.add(accountMenu);
        topMenuBar.add(dataMenu);
        topMenuBar.add(infoMenu);

        frame.setJMenuBar(topMenuBar);                                                                //<< CHECK OPT
//END MENU

//GUI
        JLabel label = new JLabel("West", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        label.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        pane.add(label, BorderLayout.WEST);

        JLabel label2 = new JLabel("CENTER", JLabel.CENTER);
        label2.setFont(new Font("Arial", Font.BOLD, 36));
        label2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        pane.add(label2, BorderLayout.CENTER);
//END GUI
    }

    public JFrame getFrame() {
        return frame;
    }
}