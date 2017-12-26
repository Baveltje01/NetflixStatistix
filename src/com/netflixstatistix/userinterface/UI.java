package com.netflixstatistix.userinterface;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

import com.netflixstatistix.connections.DatabaseInterface;
import com.netflixstatistix.jgravatar.*;

public class UI implements Runnable{


    // For testing purposes only
    public boolean loggedIn;
    public String user = "Bas";                                                                     //<< TESTING SETTING
    public String subscriberEmail = "basvanrooten@me.com";
    public String movieName = "Stranger Things";
    public String[] userArray = {"Bas", "Tom"};


    // Initializing different classes
    private AppDetails appDetails = new AppDetails();
    private TimeKeeper timeKeeper = new TimeKeeper();
    private Gravatar gravatar = new Gravatar();
    private DatabaseInterface di = new DatabaseInterface();

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

    @Override
    public void run() {
        this.frame = new JFrame("Netflix Statistix");
        this.frame.setPreferredSize(new Dimension(800, 800));
        this.frame.setMinimumSize(new Dimension(700,700));

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

        // //////////////////////////////// //
        // Build the right menu             //
        // //////////////////////////////// //

        JPanel centerContainer = new JPanel(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();

        JLabel detailTitle = new JLabel("Details van " + movieName, JLabel.CENTER);            // TESTING SETTING
        detailTitle.setFont(new Font("Arial", Font.BOLD, 20));
        gbc1.gridx = 0;
        gbc1.gridy = 1;

        centerContainer.add(detailTitle, gbc1);

        // TABLE HEADER
        String[] columns = new String[] {
                "Statistiek", "Resultaat"
        };

        // TABLE DATA
        Object[][] data = new Object[][] {
                {"Versiebeheer", appDetails.getAuthors()},
                {2, "B"},
                {2, "B"},
                {2, "C"},
                {2, "D"},
                {2, "E"},
                {2, "F"},
                {2, "G"},
                {2, "H"},
                {2, "I"},
                {2, "J"},
                {2, "K"},
                {2, "L"},
                {2, "M"},
                {3, "N"}
        };
        //create table with data
        JTable table = new JTable(data, columns);
        table.getTableHeader().setReorderingAllowed(false);

        gbc1.gridx = 0;
        gbc1.gridy = 2;
        centerContainer.add(new JScrollPane(table), gbc1);

        centerContainer.setBorder(grey);
        centerContainer.setBorder(new EmptyBorder(10,10,10,10));
        pane.add(centerContainer, BorderLayout.CENTER);


        // ///////////////////////////////// //
        // Build the left menu               //
        // ///////////////////////////////// //


        // User Dropdown Menu
        JPanel userContainer = new JPanel(new BorderLayout());
        JComboBox usersDropdown = new JComboBox(userArray);
        usersDropdown.setSelectedIndex(0);
        userContainer.add(usersDropdown, BorderLayout.NORTH);

        // UserDetails Menu
        JPanel userSubContainer = new JPanel(new BorderLayout());
        userSubContainer.setBorder(new EmptyBorder(20, 20, 20, 20));

        //Grabbing subscriber-gravatar image
        gravatar.setSize(150);
        byte[] gravatarByte = gravatar.download(subscriberEmail);
        JLabel gravatarImage = new JLabel(new ImageIcon(gravatarByte));
        userSubContainer.add(gravatarImage, BorderLayout.NORTH);

        JLabel greeting = new JLabel(timeKeeper.greeting() + " " + this.user, JLabel.CENTER);
        greeting.setFont(new Font("Arial", Font.PLAIN, 18));
        userSubContainer.add(greeting, BorderLayout.SOUTH);

        userContainer.add(userSubContainer, BorderLayout.SOUTH);


        // TV-Show Selector
        JPanel showSubContainer = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.weightx = 1;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.gridwidth = GridBagConstraints.REMAINDER;

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
        JButton show10 = new JButton("Serie 10");
        show10.setMargin(new Insets(5, 0, 5, 0));

        showSubContainer.add(show1, gbc2);
        showSubContainer.add(show2, gbc2);
        showSubContainer.add(show3, gbc2);
        showSubContainer.add(show4, gbc2);
        showSubContainer.add(show5, gbc2);
        showSubContainer.add(show6, gbc2);
        showSubContainer.add(show7, gbc2);
        showSubContainer.add(show8, gbc2);
        showSubContainer.add(show9, gbc2);
        showSubContainer.add(show10, gbc2);


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
