package com.netflixstatistix;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class UI implements Runnable {


    // Creating appversion object for version details
    AppDetails appDetails = new AppDetails();

    // Border variable for easy configuration
   Border grey = BorderFactory.createLineBorder(Color.lightGray);

    // Creating JFrame which acts as main container.
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
        this.frame.setResizable(false);

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
        loginMenuItem.setEnabled(loggedIn);                                                         //<< TESTING SETTING
        accountMenu.add(loginMenuItem);

        // logout
        logoutMenuItem = new JMenuItem("Afmelden");
        logoutMenuItem.setEnabled(!loggedIn);                                                       //<< TESTING SETTING
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

        frame.setJMenuBar(topMenuBar);
//END MENU

//GUI
        JLabel label = new JLabel("West", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        label.setBorder(grey);
        pane.add(label, BorderLayout.WEST);

        JLabel label2 = new JLabel("CENTER", JLabel.CENTER);
        label2.setFont(new Font("Arial", Font.BOLD, 36));
        label2.setBorder(grey);
        pane.add(label2, BorderLayout.CENTER);


        // ///////////////////////////////// //
        // Build the left menu               //
        // ///////////////////////////////// //

        JPanel userContainer = new JPanel(new BorderLayout());

        // User Dropdown Menu
        String[] userArray = {"Bas", "Tom", "Robin", "Jac"};
        JComboBox usersDropdown = new JComboBox(userArray);
        usersDropdown.setSelectedIndex(0);
        usersDropdown.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXX"); // Hack for altering size

        userContainer.add(usersDropdown, BorderLayout.NORTH);
        JPanel westContainer = new JPanel(new BorderLayout());
        westContainer.setBorder(grey);
        westContainer.add(userContainer, BorderLayout.NORTH);
        pane.add(westContainer, BorderLayout.WEST);





        // ///////////////////////////////// //
        // Build the credits menu            //
        // ///////////////////////////////// //

        JPanel creditsContainer = new JPanel(new BorderLayout());
        creditsContainer.setBorder(new EmptyBorder(3, 10, 3, 10));
        JLabel creditsAppVersion = new JLabel("Netflix Statistix versie " + appDetails.getVersion(), JLabel.LEFT);
        JLabel credits = new JLabel("Informatica 2017 - Klas E - " + appDetails.getAuthors(), JLabel.RIGHT);
        creditsContainer.add(creditsAppVersion, BorderLayout.WEST);
        creditsContainer.add(credits, BorderLayout.EAST);
        pane.add(creditsContainer, BorderLayout.SOUTH);


//END GUI
    }

    public JFrame getFrame() {
        return frame;
    }
}