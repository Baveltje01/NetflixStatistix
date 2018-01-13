//package com.netflixstatistix.userinterface;
//
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import java.awt.*;
//import java.sql.Time;
//import com.netflixstatistix.jgravatar.*;
//
//public class UIHandler {
//
//
//    // GLOBAL DECLARATIONS
//    private Gravatar gravatar = new Gravatar();
//    private TimeKeeper timeKeeper = new TimeKeeper();
//
//
//    public JPanel createLeftProfileMenu(int profielID) {
//        JPanel leftProfileMenu = new JPanel();
//
//        JPanel userDropdownContainer = new JPanel(new BorderLayout());
//        JComboBox usersDropdown = new JComboBox(userArray);
//        usersDropdown.setSelectedIndex(0);
//        userDropdownContainer.add(usersDropdown, BorderLayout.NORTH);
//
//        // UserDetails Menu
//        JPanel userSubContainer = new JPanel(new BorderLayout());
//        userSubContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
//
//        //Grabbing subscriber-gravatar image
//        gravatar.setSize(150);
//        byte[] gravatarByte = gravatar.download(subscriberEmail);
//        JLabel gravatarImage = new JLabel(new ImageIcon(gravatarByte));
//        userSubContainer.add(gravatarImage, BorderLayout.NORTH);
//
//        JLabel greeting = new JLabel(timeKeeper.greeting() + " " + this.user, JLabel.CENTER);
//        greeting.setFont(new Font("Arial", Font.PLAIN, 18));
//        userSubContainer.add(greeting, BorderLayout.SOUTH);
//
//        userContainer.add(userSubContainer, BorderLayout.SOUTH);
//
//    }
//
//
//}
