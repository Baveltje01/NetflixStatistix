package com.netflixstatistix.userinterface.AccountSettings;
import javax.swing.*;
import java.awt.*;

public class AccountInterface extends JFrame{

    private AccountContentPanel contentPanel;

    public AccountInterface(String title){

        super(title);
        setLayout(new BorderLayout());                       // Layout
        AccountContentPanel contentPanel = new AccountContentPanel();      // Initiated from Class in same folder

        Container content = getContentPane();                // Container to give content a place in the window
        content.add(contentPanel, BorderLayout.CENTER);

    }
}



