package com.netflixstatistix.userinterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameSwitcher extends UI implements ActionListener {

    private JButton button;

    public FrameSwitcher(JButton button) {
        this.button = button;
    }


    @Override
    public void actionPerformed(ActionEvent ae) {
        session.setCurrentVideoTitle(button.getText());
        System.out.println(session.getCurrentVideoTitle());



    }
}
