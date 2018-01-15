package com.netflixstatistix.userinterface.account;

import com.netflixstatistix.session.CurrentSession;

import javax.swing.*;
import java.awt.*;

public class AccountManager extends CurrentSession implements Runnable{

    private JFrame frame;

    public AccountManager() {
    }

    @Override
    public void run() {
        frame = new JFrame("Wijzig Gebruikersgegevens");
        frame.setPreferredSize(new Dimension(365, 455));
        frame.setResizable(false);

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        createComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    private void createComponents(Container container) {

        JPanel basePanel = new JPanel(new GridBagLayout());
        basePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton button = new JButton("Test");

        basePanel.add(button);
        frame.add(basePanel);




    }

    public JFrame getFrame() {
        return frame;
    }



}
