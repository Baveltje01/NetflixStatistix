package com.netflixstatistix.userinterface;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import com.netflixstatistix.connections.DatabaseInterface;
import com.netflixstatistix.session.Session;

public class UI implements Runnable{

    // Initializing different classes
    public Session session = new Session();
    private DatabaseInterface di = new DatabaseInterface();
    private UIHandler uih = new UIHandler();

    // Variables for easy configuration
    private Border grey = BorderFactory.createLineBorder(Color.lightGray);

    // Creating JFrame which acts as main container.
    private JFrame frame;

    @Override
    public void run() {
        this.frame = new JFrame("Netflix Statistix");
        this.frame.setPreferredSize(new Dimension(800, 800));
        this.frame.setMinimumSize(new Dimension(700,700));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    private void createComponents(Container container) {
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        frame.setJMenuBar(uih.mainMenu());



        pane.add(uih.createDetails(1001, "Test"), BorderLayout.CENTER);


        JPanel westContainer = new JPanel(new BorderLayout());
        westContainer.setBorder(grey);

        // Adding WestContainer
        westContainer.add(uih.createLeftProfileMenu(session.getAbonneeID(), session.getProfielNaam(), session.getAbonneeEmail()), BorderLayout.NORTH);
        westContainer.add(uih.createShowSelector(session.getProfielNaam(), session.getLatestVideoTitleArray()), BorderLayout.SOUTH);
        pane.add(westContainer, BorderLayout.WEST);

        // Credits Footer
        pane.add(uih.createFooter(), BorderLayout.SOUTH);
        pane.remove(uih.createFooter());


    }
    public JFrame getFrame() {
        return frame;
    }
}