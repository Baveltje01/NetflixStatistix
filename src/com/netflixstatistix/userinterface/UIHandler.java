package com.netflixstatistix.userinterface;

        import javax.swing.*;
        import javax.swing.border.Border;
        import javax.swing.border.EmptyBorder;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.beans.PropertyVetoException;
        import java.lang.reflect.Array;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Vector;

        import com.netflixstatistix.connections.DatabaseConnection;
        import com.netflixstatistix.connections.DatabaseInterface;
        import com.netflixstatistix.jgravatar.*;
        import com.netflixstatistix.session.CurrentSession;
        import com.netflixstatistix.userinterface.about.AboutInterface;


        import static java.awt.GridBagConstraints.FIRST_LINE_START;

public class UIHandler extends CurrentSession {



    // GLOBAL DECLARATIONS
    private DatabaseInterface di = new DatabaseInterface();
    private Gravatar gravatar = new Gravatar();
    private TimeKeeper timeKeeper = new TimeKeeper();
    private AppDetails appDetails = new AppDetails();
    private Border grey = BorderFactory.createLineBorder(Color.lightGray);

    public JMenuBar mainMenu() {
        JMenuBar topMenuBar = new JMenuBar();

        JMenu accountMenu;
        JMenuItem changeAccountMenuItem;
        JMenu dataMenu;
        JMenuItem refreshDataMenuItem;
        JMenu infoMenu;
        JMenuItem avansItemMenu;
        JMenuItem aboutItemMenu;


        accountMenu = new JMenu("Account");

        changeAccountMenuItem = new JMenuItem("Accountgegevens aanpassen");
        accountMenu.add(changeAccountMenuItem);

        changeAccountMenuItem.addActionListener(new ActionListener() {                                                      // NEW WINDOW OPENS ON PRESSING 'changeAccountMenuItem'
            public void actionPerformed(ActionEvent e){
                                                                                                                            // STUB
            }
        });

        dataMenu = new JMenu("Gegevens");

        refreshDataMenuItem = new JMenuItem("Refresh gegevens");
        dataMenu.add(refreshDataMenuItem);

        topMenuBar.add(accountMenu);
        topMenuBar.add(dataMenu);


        infoMenu = new JMenu("Info");

        avansItemMenu = new JMenuItem("Avans website");
        infoMenu.add(avansItemMenu);

        avansItemMenu.addActionListener(new ActionListener() {                                                              // BROWSER WINDOW OPENS AND GOES TO http://www.avans.nl
            public void actionPerformed(ActionEvent e){
                try {
                    Desktop.getDesktop().browse(new URL("http://www.avans.nl").toURI());
                } catch (Exception z) {
                    System.out.println("Error opening webpage.");
                }
            }
        });

        aboutItemMenu = new JMenuItem("Over deze app");
        infoMenu.add(aboutItemMenu);

        aboutItemMenu.addActionListener(new ActionListener() {                                                              // NEW WINDOW OPENS ON PRESSING 'aboutItemMenu'
            public void actionPerformed(ActionEvent e){
                AboutInterface openAboutItemMenu = new AboutInterface("Over deze app");
            }
        });

        topMenuBar.add(accountMenu);
        topMenuBar.add(dataMenu);
        topMenuBar.add(infoMenu);

        return topMenuBar;
    }

    public JPanel createLeftProfileMenu(int abonneeID, String profielNaam, String profielEmail) {
        DatabaseConnection.connect();
        JPanel leftProfileMenu = new JPanel(new BorderLayout());

        JPanel userDropdownContainer = new JPanel(new BorderLayout());
        JComboBox usersDropdown = new JComboBox(di.getProfielenFromAbonnee(abonneeID));                                                             // FIXFIXFIXFIX
        usersDropdown.setSelectedIndex(0);
        userDropdownContainer.add(usersDropdown, BorderLayout.NORTH);

        // UserDetails Menu
        JPanel userSubContainer = new JPanel(new BorderLayout());
        userSubContainer.setBorder(new EmptyBorder(20, 20, 20, 20));

        //Grabbing subscriber-gravatar image
        gravatar.setSize(150);
        byte[] gravatarByte = gravatar.download(profielEmail);                                                                                      // FIXFIXFIXFIX
        JLabel gravatarImage = new JLabel(new ImageIcon(gravatarByte));
        userSubContainer.add(gravatarImage, BorderLayout.NORTH);

        JLabel greeting = new JLabel(timeKeeper.greeting() + " " + profielNaam, JLabel.CENTER);
        greeting.setFont(new Font("Arial", Font.PLAIN, 18));

        userSubContainer.add(gravatarImage, BorderLayout.NORTH);
        userSubContainer.add(greeting, BorderLayout.SOUTH);

        leftProfileMenu.add(userDropdownContainer, BorderLayout.NORTH);
        leftProfileMenu.add(userSubContainer, BorderLayout.SOUTH);

        DatabaseConnection.disconnect();
        leftProfileMenu.validate();
        leftProfileMenu.repaint();
        return leftProfileMenu;
    }

    public JPanel createFooter() {
        JPanel creditsContainer = new JPanel(new BorderLayout());
        creditsContainer.setBorder(new EmptyBorder(3, 10, 3, 10));
        JLabel creditsAppVersion = new JLabel("Netflix Statistix versie " + appDetails.getVersion(), JLabel.LEFT);
        JLabel credits = new JLabel("Informatica 2017 - Klas E - " + appDetails.getAuthors(), JLabel.RIGHT);
        creditsContainer.add(creditsAppVersion, BorderLayout.WEST);
        creditsContainer.add(credits, BorderLayout.EAST);

        creditsContainer.validate();
        creditsContainer.repaint();
        return creditsContainer;
    }

    public JPanel createShowSelector(String profielNaam, ArrayList<String> latestVideoTitleArray) {

        DatabaseConnection.connect();

        JPanel showSubContainer = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.weightx = 1;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.gridwidth = GridBagConstraints.REMAINDER;

        for (String title : latestVideoTitleArray) {
            JButton show = new JButton(title);
            FrameSwitcher frameSwitcher = new FrameSwitcher(show);
            show.setMargin(new Insets(5, 0, 5, 0));
            show.addActionListener(frameSwitcher);
            showSubContainer.add(show, gbc2);
        }

        showSubContainer.validate();
        showSubContainer.repaint();

        DatabaseConnection.disconnect();

        return showSubContainer;
    }

    public JPanel createDetails(int ProgrammaID, String movieName) {

        DatabaseConnection.connect();

        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.setBorder(grey);
        centerContainer.setBorder(new EmptyBorder(10,10,10,10));

        JPanel buttonContainer = new JPanel(new GridBagLayout());
        JDesktopPane internalFrameContainer = new JDesktopPane();

        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        JLabel detailTitle = new JLabel("Details van " + movieName, JLabel.CENTER);            // TESTING SETTING
        detailTitle.setFont(new Font("Arial", Font.BOLD, 20));

        JButton functie1 = new JButton("Functie1");
        JButton functie2 = new JButton("Functie2");
        JButton functie3 = new JButton("Functie3");
        JButton functie4 = new JButton("Functie4");
        JButton functie5 = new JButton("Accounts met enkel Profiel");
        JButton functie6 = new JButton("Functie6");
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        buttonContainer.add(functie1,gbc1);

        gbc1.gridx = 1;
        gbc1.gridy = 0;
        buttonContainer.add(functie2,gbc1);

        gbc1.gridx = 2;
        gbc1.gridy = 0;
        buttonContainer.add(functie3,gbc1);

        gbc1.gridx = 3;
        gbc1.gridy = 0;
        buttonContainer.add(functie4,gbc1);

        gbc1.gridx = 4;
        gbc1.gridy = 0;
        buttonContainer.add(functie5,gbc1);

        gbc1.gridx = 5;
        gbc1.gridy = 0;
        buttonContainer.add(functie6,gbc1);


        centerContainer.add(buttonContainer, BorderLayout.NORTH);
        centerContainer.add(internalFrameContainer, BorderLayout.CENTER);
        GridBagConstraints gbc = new GridBagConstraints();

        functie1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                internalFrameContainer.removeAll();
                JInternalFrame frame = new JInternalFrame("Functie 1", false, false, false, false);
                frame.setLayout(new GridBagLayout());

                // UI components and logic for frame
                JTextField textField1 = new JTextField("DO YOU");
                gbc.anchor = GridBagConstraints.NORTH;
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weighty = 0.0;
                frame.add(textField1, gbc);

                JTextField textField2 = new JTextField("KNOW DA WAE");
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.weighty = 1.0;
                frame.add(textField2, gbc);


                // ADDING frames to internalcontainer
                internalFrameContainer.add(frame, BorderLayout.CENTER);


                // MAXIMIZE FRAME
                try {
                    frame.setMaximum(true);
                } catch (PropertyVetoException exception) {
                    System.out.println("Error full-screening frame.");
                }

                centerContainer.revalidate();
                centerContainer.repaint();
                frame.setVisible(true);
            }
        });

        functie2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                internalFrameContainer.removeAll();
                JInternalFrame frame = new JInternalFrame("Functie 2", false, false, false, false);
                frame.setLayout(new GridBagLayout());


                // UI components and logic for frame
                JTextField textField1 = new JTextField("DDDDASDDDD YOU");
                gbc.anchor = GridBagConstraints.NORTH;
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weighty = 0.0;
                frame.add(textField1, gbc);

                JTextField textField2 = new JTextField("KNOW DA WAE");
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.weighty = 1.0;
                frame.add(textField2, gbc);


                // ADDING frames to internalcontainer
                internalFrameContainer.add(frame, BorderLayout.CENTER);


                // MAXIMIZE FRAME
                try {
                    frame.setMaximum(true);
                } catch (PropertyVetoException exception) {
                    System.out.println("Error full-screening frame.");
                }

                centerContainer.revalidate();
                centerContainer.repaint();
                frame.setVisible(true);
            }
        });

        functie3.addActionListener(new ActionListener() { // FILMS VIEWED BY SELECTED ACCOUNT
            public void actionPerformed(ActionEvent e){

                internalFrameContainer.removeAll();
                JInternalFrame frame = new JInternalFrame("Functie 3", false, false, false, false);
                frame.setLayout(new GridBagLayout());

                // UI components and logic for frame
                JLabel header = new JLabel("Films bekeken door geselecteerde abonnee");
                JButton execute = new JButton("Bekijk films");

                gbc.anchor = GridBagConstraints.NORTH;
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weighty = 0.0;
                frame.add(header, gbc);

                DatabaseConnection.connect();
                ArrayList<String> accountArray = di.getListOfAccountNamesArrayList(); // RETRIEVES ACCOUNTS FROM DB NTO ARRAYLIST
                DatabaseConnection.disconnect();

                JComboBox<String> accountField = new JComboBox<String>(new Vector<>(accountArray));
                gbc.weightx = 2;
                gbc.weighty = 2;
                gbc.gridy = 1;
                frame.add(accountField, gbc);

                gbc.gridy = 2;
                frame.add(execute, gbc);

                execute.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        try{
                        Object accountSelection = accountField.getSelectedItem();


                        DatabaseConnection.connect();
                        ArrayList<String> movieArray = di.getAllMoviesViewedByAccountName(String.valueOf(accountSelection));
                        DatabaseConnection.disconnect();

                        JList movieField = new JList(new Vector<>(movieArray));
                        movieField.setSize(30,60);

                        gbc.gridy = 3;

                        header.setText("Films bekeken door " + String.valueOf(accountSelection));
                        frame.add(movieField,gbc);
                        execute.setVisible(false);
                        accountField.setVisible(false);

                        movieField.setVisible(true);
                        centerContainer.revalidate();
                        centerContainer.repaint();
                        }

                        catch(Exception a){
                            System.out.println("Error outputting list of movies viewed by account.");
                        }
                    }
                });

                // ADDING frames to internalcontainer
                internalFrameContainer.add(frame, BorderLayout.CENTER);


                // MAXIMIZE FRAME
                try {
                    frame.setMaximum(true);
                } catch (PropertyVetoException exception) {
                    System.out.println("Error full-screening frame.");
                }

                centerContainer.revalidate();
                centerContainer.repaint();
                frame.setVisible(true);
            }
        });

        functie4.addActionListener(new ActionListener() { // LONGEST MOVIE RATED UNDER 16 YRS
            public void actionPerformed(ActionEvent e){

                internalFrameContainer.removeAll();
                JInternalFrame frame = new JInternalFrame("Functie 4", false, false, false, false);
                frame.setLayout(new GridBagLayout());


                // UI components and logic for frame
                JLabel textField1 = new JLabel("Langste film voor kijkers jonger dan 16 jaar");
                gbc.anchor = GridBagConstraints.NORTH;
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weighty = 0.0;
                frame.add(textField1, gbc);

                String output = session.getLongestMovieUnder16();

                JTextField textField2 = new JTextField(output);
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.weighty = 1.0;
                frame.add(textField2, gbc);


                // ADDING frames to internalcontainer
                internalFrameContainer.add(frame, BorderLayout.CENTER);


                // MAXIMIZE FRAME
                try {
                    frame.setMaximum(true);
                } catch (PropertyVetoException exception) {
                    System.out.println("Error full-screening frame.");
                }

                centerContainer.revalidate();
                centerContainer.repaint();
                frame.setVisible(true);
            }
        });

        functie5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                internalFrameContainer.removeAll();
                JInternalFrame frame = new JInternalFrame("Accounts met een enkel Profiel", false, false, false, false);
                frame.setLayout(new GridBagLayout());


                // UI components and logic for frame
                JLabel label1 = new JLabel("Hieronder volgen de accounts met slechts 1 enkel profiel");
                gbc.anchor = GridBagConstraints.NORTH;
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weighty = 0.0;
                frame.add(label1, gbc);
                int i = 0;
                ArrayList<String> array = session.getAccountsWithOneProfile();

                for (String item : array) {
                    JTextField textField = new JTextField(item);
                    gbc.anchor = GridBagConstraints.NORTH;
                    gbc.gridy = i + 1;

                    textField.setEnabled(false);
                    frame.add(textField, gbc);
                }


                // ADDING frames to internalcontainer
                internalFrameContainer.add(frame, BorderLayout.CENTER);


                // MAXIMIZE FRAME
                try {
                    frame.setMaximum(true);
                } catch (PropertyVetoException exception) {
                    System.out.println("Error full-screening frame.");
                }

                centerContainer.revalidate();
                centerContainer.repaint();
                frame.setVisible(true);
            }
        });

        functie6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                internalFrameContainer.removeAll();
                JInternalFrame frame = new JInternalFrame("Functie 6", false, false, false, false);
                frame.setLayout(new GridBagLayout());


                // UI components and logic for frame
                JTextField textField1 = new JTextField("TEST 6 YOU");
                gbc.anchor = GridBagConstraints.NORTH;
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weighty = 0.0;
                frame.add(textField1, gbc);

                JTextField textField2 = new JTextField("KNOW DA WAE");
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.weighty = 1.0;
                frame.add(textField2, gbc);


                // ADDING frames to internalcontainer
                internalFrameContainer.add(frame, BorderLayout.CENTER);


                // MAXIMIZE FRAME
                try {
                    frame.setMaximum(true);
                } catch (PropertyVetoException exception) {
                    System.out.println("Error full-screening frame.");
                }

                centerContainer.revalidate();
                centerContainer.repaint();
                frame.setVisible(true);
            }
        });



        DatabaseConnection.disconnect();

        centerContainer.validate();
        centerContainer.repaint();

        return centerContainer;
    }
}

