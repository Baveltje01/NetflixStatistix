package com.netflixstatistix.userinterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import com.netflixstatistix.connections.DatabaseConnection;
import com.netflixstatistix.connections.DatabaseInterface;
import com.netflixstatistix.jgravatar.*;

public class UIHandler {


    // GLOBAL DECLARATIONS
    private DatabaseInterface di = new DatabaseInterface();
    private Gravatar gravatar = new Gravatar();
    private TimeKeeper timeKeeper = new TimeKeeper();

    // TEMPORARY VARIABLES
    private String user = "Bas";

    public JPanel createLeftProfileMenu(int profielID) {
        JPanel leftProfileMenu = new JPanel();

        JPanel userDropdownContainer = new JPanel(new BorderLayout());
        JComboBox usersDropdown = new JComboBox(di.getProfielenFromAbonnee(profielID));
        usersDropdown.setSelectedIndex(0);
        userDropdownContainer.add(usersDropdown, BorderLayout.NORTH);

        // UserDetails Menu
        JPanel userSubContainer = new JPanel(new BorderLayout());
        userSubContainer.setBorder(new EmptyBorder(20, 20, 20, 20));

        //Grabbing subscriber-gravatar image
        gravatar.setSize(150);
        byte[] gravatarByte = gravatar.download("basvanrooten@me.com");
        JLabel gravatarImage = new JLabel(new ImageIcon(gravatarByte));
        userSubContainer.add(gravatarImage, BorderLayout.NORTH);

        JLabel greeting = new JLabel(timeKeeper.greeting() + " " + this.user, JLabel.CENTER);
        greeting.setFont(new Font("Arial", Font.PLAIN, 18));

        userSubContainer.add(gravatarImage, BorderLayout.NORTH);
        userSubContainer.add(greeting, BorderLayout.SOUTH);

        leftProfileMenu.add(userDropdownContainer, BorderLayout.NORTH);
        leftProfileMenu.add(userSubContainer, BorderLayout.SOUTH);

        return leftProfileMenu;
    }


}
