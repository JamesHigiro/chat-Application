/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.runnables;

import chat.models.ServerWindow;
import java.awt.Color;

/**
 *
 * @author Joela
 */
public class ServerSideExecution {
        public static void main(String args[]){
        ServerWindow serverForm = new ServerWindow();
        serverForm.getContentPane().setBackground(new Color(10,202,10));
        serverForm.setVisible(true);
    }
}
