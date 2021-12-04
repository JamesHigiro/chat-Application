/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.runnables;

import chat.models.ClientWindow;
import java.awt.Color;

/**
 *
 * @author Joela
 */
public class ClienSideExecution {
    public static void main(String[] args) {
     ClientWindow clientForm = new ClientWindow(1000, 750);
        clientForm.setVisible(true);
        clientForm.getContentPane().setBackground(new Color(100,250,130));   
    }
     
}
