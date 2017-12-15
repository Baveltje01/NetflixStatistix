package com.netflixstatistix.userinterface;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UI implements Runnable {


    // For testing purposes only
    public String user = "Bas";                                                                     //<< TESTING SETTING


    // Initializing different classes
    private AppDetails appDetails = new AppDetails();
    private TimeKeeper timeKeeper = new TimeKeeper();

    // Variables for easy configuration
    private Border grey = BorderFactory.createLineBorder(Color.lightGray);

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
        JLabel label2 = new JLabel(timeKeeper.greeting(), JLabel.CENTER);
        label2.setFont(new Font("Arial", Font.BOLD, 36));
        label2.setBorder(grey);
        pane.add(label2, BorderLayout.CENTER);


        // ///////////////////////////////// //
        // Build the left menu               //
        // ///////////////////////////////// //


        // User Dropdown Menu
        JPanel userContainer = new JPanel(new BorderLayout());

        String[] userArray = {"Bas", "Tom", "Robin", "Jac"};
        JComboBox usersDropdown = new JComboBox(userArray);
        usersDropdown.setSelectedIndex(0);
        userContainer.add(usersDropdown, BorderLayout.NORTH);

        // UserDetails Menu
        JPanel userSubContainer = new JPanel(new BorderLayout());
        userSubContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel greeting = new JLabel(timeKeeper.greeting() + " " + this.user, JLabel.CENTER);
        greeting.setFont(new Font("Arial", Font.PLAIN, 18));
        userSubContainer.add(greeting);

        userContainer.add(userSubContainer, BorderLayout.SOUTH);


        // TV-Show Selector
        JPanel showSubContainer = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JButton show1 = new JButton("Serie 1");
        show1.setMargin(new Insets(5, 0, 5, 0));
        JButton show2 = new JButton("Serie 2");
        show2.setMargin(new Insets(5, 0, 5, 0));
        JButton show3 = new JButton("Serie 3");
        show3.setMargin(new Insets(5, 0, 5, 0));
        JButton show4 = new JButton("Serie 4");
        show4.setMargin(new Insets(5, 0, 5, 0));
        JButton show5 = new JButton("Serie 5");
        show5.setMargin(new Insets(5, 0, 5, 0));
        JButton show6 = new JButton("Serie 6");
        show6.setMargin(new Insets(5, 0, 5, 0));
        JButton show7 = new JButton("Serie 7");
        show7.setMargin(new Insets(5, 0, 5, 0));
        JButton show8 = new JButton("Serie 8");
        show8.setMargin(new Insets(5, 0, 5, 0));
        JButton show9 = new JButton("Serie 9");
        show9.setMargin(new Insets(5, 0, 5, 0));
        JButton selector = new JButton("Nieuwe serie");
        selector.setMargin(new Insets(5, 0, 5, 0));

        showSubContainer.add(show1, gbc);
        showSubContainer.add(show2, gbc);
        showSubContainer.add(show3, gbc);
        showSubContainer.add(show4, gbc);
        showSubContainer.add(show5, gbc);
        showSubContainer.add(show6, gbc);
        showSubContainer.add(show7, gbc);
        showSubContainer.add(show8, gbc);
        showSubContainer.add(show9, gbc);
        showSubContainer.add(selector, gbc);


        JPanel westContainer = new JPanel(new BorderLayout());
        westContainer.setBorder(grey);
        westContainer.add(userContainer, BorderLayout.NORTH);
        westContainer.add(showSubContainer, BorderLayout.SOUTH);

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
