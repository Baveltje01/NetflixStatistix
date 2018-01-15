package com.netflixstatistix.userinterface;

        import javax.swing.*;
        import javax.swing.border.Border;
        import javax.swing.border.EmptyBorder;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.net.URL;
        import java.util.ArrayList;

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

        JPanel centerContainer = new JPanel(new GridBagLayout());
        centerContainer.setBorder(grey);
        centerContainer.setBorder(new EmptyBorder(10,10,10,10));

        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        JLabel detailTitle = new JLabel("Details van " + movieName, JLabel.CENTER);            // TESTING SETTING
        detailTitle.setFont(new Font("Arial", Font.BOLD, 20));

        JButton functie1 = new JButton("Functie1");
        JButton functie2 = new JButton("Functie2");
        JButton functie3 = new JButton("Functie3");
        JButton functie4 = new JButton("Functie4");
        JButton functie5 = new JButton("Functie5");
        JButton functie6 = new JButton("Functie6");
        JInternalFrame frame1 = new JInternalFrame("Testframe 1");
        JTextField textField1 = new JTextField("Testtextfield 1");

        textField1.setSize(200,200);
        textField1.setVisible(true);

        frame1.setSize(200,100);
        frame1.setVisible(true);

        functie1.addActionListener(new ActionListener() {                                                              // NEW WINDOW OPENS ON PRESSING 'aboutItemMenu'
            public void actionPerformed(ActionEvent e){
                centerContainer.removeAll();
                gbc1.anchor = GridBagConstraints.FIRST_LINE_START;
                gbc1.gridx = 0;
                gbc1.gridy = 0;
                centerContainer.add(functie1,gbc1);

                gbc1.anchor = GridBagConstraints.FIRST_LINE_START;
                gbc1.gridx = 1;
                gbc1.gridy = 0;
                centerContainer.add(functie2,gbc1);

                gbc1.anchor = GridBagConstraints.FIRST_LINE_START;
                gbc1.gridx = 2;
                gbc1.gridy = 0;
                centerContainer.add(functie3,gbc1);

                gbc1.anchor = GridBagConstraints.FIRST_LINE_START;
                gbc1.gridx = 3;
                gbc1.gridy = 0;
                centerContainer.add(functie4,gbc1);

                gbc1.anchor = GridBagConstraints.FIRST_LINE_START;
                gbc1.gridx = 4;
                gbc1.gridy = 0;
                centerContainer.add(functie5,gbc1);

                gbc1.anchor = GridBagConstraints.FIRST_LINE_START;
                gbc1.gridx = 5;
                gbc1.gridy = 0;
                centerContainer.add(functie6,gbc1);

                gbc1.anchor = GridBagConstraints.FIRST_LINE_START;
                gbc1.gridx = 0;
                gbc1.gridy = 1;
                centerContainer.add(frame1,gbc1);

                centerContainer.revalidate();
                centerContainer.repaint();
            }
        });



        gbc1.gridx = 1;
        gbc1.gridy = 0;
        centerContainer.add(functie1);


        gbc1.gridx = 0;
        gbc1.gridy = 2;

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
        table.setEnabled(false);

        gbc1.gridx = 0;
        gbc1.gridy = 3;
        centerContainer.add(new JScrollPane(table), gbc1);

        DatabaseConnection.disconnect();

        centerContainer.validate();
        centerContainer.repaint();

        return centerContainer;
    }
}

