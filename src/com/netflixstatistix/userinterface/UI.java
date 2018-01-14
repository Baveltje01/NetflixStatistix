package com.netflixstatistix.userinterface;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import com.netflixstatistix.connections.DatabaseInterface;
import com.netflixstatistix.jgravatar.*;

public class UI implements Runnable{

    // Initializing different classes
    private AppDetails appDetails = new AppDetails();
    private TimeKeeper timeKeeper = new TimeKeeper();
    private Gravatar gravatar = new Gravatar();
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



        pane.add(uih.createDetails(1001), BorderLayout.CENTER);


        JPanel westContainer = new JPanel(new BorderLayout());
        westContainer.setBorder(grey);

        // Adding WestContainer
        westContainer.add(uih.createLeftProfileMenu(1215426), BorderLayout.NORTH);
        westContainer.add(uih.createShowSelector("Frank"), BorderLayout.SOUTH);
        pane.add(westContainer, BorderLayout.WEST);

        // Credits Footer
        pane.add(uih.createFooter(), BorderLayout.SOUTH);

    }

    public JFrame getFrame() {
        return frame;
    }
}
