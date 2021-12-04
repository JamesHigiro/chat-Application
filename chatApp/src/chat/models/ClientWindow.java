/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.models;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

/**
 *
 * @author Joela
 */
public class ClientWindow extends JFrame implements ActionListener, MouseListener, KeyListener {

    private String currentUser = "";
    private boolean newChat = false;
    private final HashMap<String, String> chats = new HashMap<>();
//    private ArrayList<String> chats = new ArrayList<>();
    private int windowWidth;
    private int windowHeight;
    public Border leftMainBorder = BorderFactory.createLineBorder(new Color(150, 145, 150), 1, false);
    private JPanel leftMainPanel;
    private JPanel rightMainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel leftTitleLabel;
    private JLabel rightTitleLabel;
    private JPanel rightTitlePanel;
    private JPanel rightTitlePanel2;
    private JPanel rightBottomPanel;
    private JScrollPane leftPanelParent;
    private JScrollPane rightPanelParent;
    private JTextField userTypeField;
    private JTextField userField;
    private JButton sendBtn;
    private JButton connectBtn;
    private JButton disconnectBtn;
    private JButton newMessagebtn;
    private JTextField portField;
    private JTextField serverIpField;
    private List<JLabel> leftLabels = new ArrayList<>();
    private ClientWindow this_ch;
    private String messageToSend;
    private Socket client;

    public ClientWindow(int windowWidth, int windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;

        this.setBounds(0, 0, windowWidth, windowHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this_ch = this;

        // left side
        leftMainPanel = new JPanel();
        leftMainPanel.setPreferredSize(new Dimension((int) windowWidth / 3, windowHeight));
        leftMainPanel.setBackground(new Color(230, 230, 230));
        leftMainPanel.setOpaque(true);
        leftMainPanel.setBorder(leftMainBorder);

        leftTitleLabel = new JLabel();
        leftTitleLabel.setPreferredSize(new Dimension((int) (windowWidth / 3) - 20, 50));
        leftTitleLabel.setBackground(new Color(230, 230, 230));
        leftTitleLabel.setOpaque(true);
        leftTitleLabel.setFont(new Font(null, Font.BOLD, 16));
        ImageIcon image = new ImageIcon("current_user.jpg");
        Image img = image.getImage();
        Image newImg = img.getScaledInstance(50, leftTitleLabel.getHeight() - 5, Image.SCALE_SMOOTH);
        ImageIcon image2 = new ImageIcon(newImg);
        leftTitleLabel.setIcon(image2); // setting an image icon
        leftTitleLabel.setHorizontalTextPosition(JLabel.RIGHT);//set text position with values: LEFT, CENTER, RIGHT
        leftTitleLabel.setVerticalTextPosition(JLabel.CENTER);

        leftTitleLabel.setVisible(false);

        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension((int) windowWidth / 3, windowHeight * 10));
//       leftPanel.setBackground(new Color(255,255,255));
        leftPanel.setBackground(Color.darkGray);
        leftPanel.setOpaque(true);
        leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        leftPanelParent = new JScrollPane(leftPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        leftPanelParent.setPreferredSize(new Dimension((int) windowWidth / 3, windowHeight - 50));

        // right side
        rightMainPanel = new JPanel();
        rightMainPanel.setPreferredSize(new Dimension((int) (windowWidth - (windowWidth / 3)), windowHeight));
        rightMainPanel.setBackground(new Color(230, 230, 230));
        rightMainPanel.setOpaque(true);

        rightTitlePanel = new JPanel();
        rightTitlePanel.setPreferredSize(new Dimension((int) (windowWidth - (windowWidth / 3)), 50));
        rightTitlePanel.setBackground(new Color(230, 230, 230));
        rightTitlePanel.setOpaque(true);
        rightTitlePanel.setLayout(new GridLayout(1, 4));

        rightTitleLabel = new JLabel();
        image = new ImageIcon("images.jpg");
        img = image.getImage();
        newImg = img.getScaledInstance(50, 45, Image.SCALE_SMOOTH);
        image2 = new ImageIcon(newImg);
        rightTitleLabel.setIcon(image2); // setting an image icon
        rightTitleLabel.setText(currentUser);
        rightTitleLabel.setHorizontalTextPosition(JLabel.RIGHT);//set text position with values: LEFT, CENTER, RIGHT
        rightTitleLabel.setVerticalTextPosition(JLabel.CENTER);

        newMessagebtn = new JButton("new chat");
        connectBtn = new JButton("connect");
        disconnectBtn = new JButton("disconnect");
        connectBtn.setBackground(new Color(230, 230, 230));
        disconnectBtn.setBackground(new Color(230, 230, 230));
        newMessagebtn.setBackground(new Color(153, 153, 253));
        connectBtn.setBorder(null);
        newMessagebtn.setBorder(null);
        disconnectBtn.setBorder(null);
        connectBtn.setFocusable(false);
        disconnectBtn.setFocusable(false);
        newMessagebtn.setFocusable(false);
        portField = new JTextField("Server Port...");
        serverIpField = new JTextField("localhost");
        userField = new JTextField("username..");
        portField.setBorder(null);
        userField.setBorder(null);
        serverIpField.setBorder(null);
        portField.setBackground(new Color(210, 100, 230));
        serverIpField.setBackground(new Color(200, 230, 230));
        userField.setBackground(new Color(230, 215, 230));

        rightTitlePanel.add(rightTitleLabel);
//       rightTitlePanel.add(portField);
        rightTitlePanel.add(serverIpField);
        rightTitlePanel.add(userField);
        rightTitlePanel.add(newMessagebtn);
        rightTitlePanel.add(connectBtn);
//       rightTitlePanel.add(disconnectBtn);

        // main panel to the right for messages body
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension((int) (windowWidth - (windowWidth / 3)), windowHeight * 30));
        rightPanel.setBackground(new Color(245, 245, 245));
        rightPanel.setOpaque(true);

        rightPanelParent = new JScrollPane(rightPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        rightPanelParent.setPreferredSize(new Dimension((int) (windowWidth - (windowWidth / 3)), windowHeight - 150));

        // bottom panel to the right
        rightBottomPanel = new JPanel();
        rightBottomPanel.setPreferredSize(new Dimension((int) (windowWidth - (windowWidth / 3)), 50));
        rightBottomPanel.setBackground(new Color(230, 230, 230));
        rightBottomPanel.setOpaque(true);

        userTypeField = new JTextField();
        userTypeField.setEnabled(false);
        userTypeField.addKeyListener(this);
        sendBtn = new JButton("Send");
        sendBtn.setBackground(Color.darkGray);
        sendBtn.setOpaque(true);
        sendBtn.setFocusable(false);
        sendBtn.setForeground(Color.white);
        sendBtn.setEnabled(false);
        sendBtn.addActionListener(this);

        sendBtn.setPreferredSize(new Dimension(100, 40));
        userTypeField.setPreferredSize(new Dimension((int) (windowWidth - (windowWidth / 3)) - 130, 40));
        rightBottomPanel.add(userTypeField, BorderLayout.WEST);
        rightBottomPanel.add(sendBtn, BorderLayout.EAST);

        connectBtn.addActionListener(this);
//       disconnectBtn.addActionListener(this);
        newMessagebtn.addActionListener(this);

//       disconnectBtn.setVisible(false);
        newMessagebtn.setVisible(false);
        rightTitleLabel.setVisible(false);

        //Adding components on the left
        this.add(leftMainPanel, BorderLayout.WEST);
        leftMainPanel.add(leftTitleLabel, BorderLayout.NORTH);
        leftMainPanel.add(leftPanelParent, BorderLayout.SOUTH);

        //adding components on the right
        this.add(rightMainPanel, BorderLayout.EAST);
        rightMainPanel.add(rightTitlePanel, BorderLayout.NORTH);
        rightMainPanel.add(rightPanelParent, BorderLayout.CENTER);
        rightMainPanel.add(rightBottomPanel, BorderLayout.SOUTH);

    }

    private void addLeftLabels(String user, String messagePart) {
        String message = "<html><b>" + user + "</b><br/>" + messagePart + "</html>";
        int widthPassed = (int) (windowWidth - (windowWidth / 3));
        JLabel leftAlbel = new JLabel();
        leftAlbel.addMouseListener(this_ch);
        leftAlbel.setPreferredSize(new Dimension((int) windowWidth / 3, 65));
        leftAlbel.setBorder(leftMainBorder);
        leftAlbel.setFont(new Font(null, Font.PLAIN, 12));
        leftAlbel.setForeground(Color.white);

        ImageIcon image = new ImageIcon("images.jpg");

        Image img = image.getImage();
        Image newImg = img.getScaledInstance(50, leftAlbel.getHeight() - 5, Image.SCALE_SMOOTH);
        ImageIcon image2 = new ImageIcon(newImg);

        leftAlbel.setIcon(image2); // setting an image icon
        leftAlbel.setText(message);
        leftAlbel.setHorizontalTextPosition(JLabel.RIGHT);//set text position with values: LEFT, CENTER, RIGHT
        leftAlbel.setVerticalTextPosition(JLabel.CENTER);

        leftLabels.add(leftAlbel);
        leftPanel.add(leftAlbel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int widthPassed = (int) (windowWidth - (windowWidth / 3));
        PrintWriter OUT;
        if (e.getSource() == sendBtn) {
            JLabel label = new JLabel("");
            label.setPreferredSize(new Dimension((int) (widthPassed / 2), 65));
            label.setBackground(new Color(150, 100, 80));
            label.setOpaque(true);
            rightPanel.add(label, BorderLayout.WEST);

            String mesg = userTypeField.getText().trim();
            JLabel label2 = new JLabel();
            int countLine = 30;
            int jumpNewline = 0;
            String messages = "<html>";
            for (int i = 0; i < mesg.length(); i++) {
                if (jumpNewline < 35) {
                    messages += mesg.substring(i, i + 1);
                    jumpNewline += 1;
                } else {
                    messages += "<br/>" + mesg.substring(i, i + 1);
                    countLine += 25;
                    jumpNewline = 0;
                }
            }
            messages += "<html/>";
            label2.setText(messages);
            label2.setPreferredSize(new Dimension((int) widthPassed / 3, countLine));
            label2.setBackground(Color.green);
            label2.setOpaque(true);
            label2.setFont(new Font(null, Font.ROMAN_BASELINE, 14));
            rightPanel.add(label2, BorderLayout.WEST);
            try {
                OUT = new PrintWriter(client.getOutputStream(), true);
                if (newChat) {
                    OUT.println(userTypeField.getText().trim());
                } else {
                    if (currentUser.equals("")) {
                        JOptionPane.showMessageDialog(null, "Follow the instructions for starting the new Chat!");
                    } else {
                        OUT.println("@" + currentUser + "@" + userTypeField.getText());
                    }
                }
                newChat = false;
            } catch (IOException ex) {
                //
            }
            String chatTemp;
            boolean userFound = false;
            for (String key : chats.keySet()) {
                if (currentUser.equals(key)) {
                    chatTemp = chats.get(key);
                    chatTemp = chatTemp + "@receiver@" + userTypeField.getText().trim() + ",";
                    chats.put(key, chatTemp);
                    userFound = true;
                    break;
                }
            }
            if (!userFound) {
                chatTemp = "@receiver@" + userTypeField.getText().trim() + ",";
                chats.put(currentUser, chatTemp);
            }
            userTypeField.setText("");
        } else if (e.getSource() == connectBtn) {
            Thread listener = new Thread(new ClientSide());
            listener.start();
        } else if (e.getSource() == newMessagebtn) {
            newChat = true;
            rightTitleLabel.setVisible(false);
            currentUser = "";
            rightPanel.removeAll();
            rightPanel.revalidate();
            rightPanel.repaint();
            JOptionPane.showMessageDialog(null, "To start new chat with new user, type @new@new_user_username@messageTosend");
        }
    }

    private void fillRecievedMessage(String recivedMessage) {
        int widthPassed = (int) (windowWidth - (windowWidth / 3));
        JLabel label2 = new JLabel();
        int countLine = 30;
        int jumpNewline = 0;
        String messages = "<html>";
        for (int i = 0; i < recivedMessage.length(); i++) {
            if (jumpNewline < 35) {
                messages += recivedMessage.substring(i, i + 1);
                jumpNewline += 1;
            } else {
                messages += "<br/>" + recivedMessage.substring(i, i + 1);
                countLine += 25;
                jumpNewline = 0;
            }
        }
        messages += "<html/>";
        label2.setText(messages);
        label2.setPreferredSize(new Dimension((int) widthPassed / 3, countLine));
        label2.setBackground(Color.white);
        label2.setOpaque(true);
        label2.setFont(new Font(null, Font.ROMAN_BASELINE, 14));
        rightPanel.add(label2, BorderLayout.EAST);

        JLabel label = new JLabel("");
        label.setPreferredSize(new Dimension((int) (widthPassed / 2), 65));
        label.setBackground(new Color(245, 245, 245));
        label.setOpaque(true);
        rightPanel.add(label, BorderLayout.EAST);
    }

    private void fillSentMessages(String messageSent) {
        int widthPassed = (int) (windowWidth - (windowWidth / 3));
        JLabel label = new JLabel("");
        label.setPreferredSize(new Dimension((int) (widthPassed / 2), 65));
        label.setBackground(new Color(245, 245, 245));
        label.setOpaque(true);
        rightPanel.add(label, BorderLayout.WEST);

        String mesg = messageSent;
        JLabel label2 = new JLabel();
        int countLine = 30;
        int jumpNewline = 0;
        String messages = "<html>";
        for (int i = 0; i < mesg.length(); i++) {
            if (jumpNewline < 35) {
                messages += mesg.substring(i, i + 1);
                jumpNewline += 1;
            } else {
                messages += "<br/>" + mesg.substring(i, i + 1);
                countLine += 25;
                jumpNewline = 0;
            }
        }
        messages += "<html/>";
        label2.setText(messages);
        label2.setPreferredSize(new Dimension((int) widthPassed / 3, countLine));
        label2.setBackground(Color.green);
        label2.setOpaque(true);
        label2.setFont(new Font(null, Font.ROMAN_BASELINE, 14));
        rightPanel.add(label2, BorderLayout.WEST);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (JLabel lbl : leftLabels) {
            if (e.getSource() == lbl) {
                int indexOfBreak = lbl.getText().indexOf("<b>");
                int indexOfEndBreak = lbl.getText().indexOf("</b>");
                currentUser = lbl.getText().substring(indexOfBreak + 3, indexOfEndBreak);
                rightTitleLabel.setText(currentUser);
                rightTitleLabel.setVisible(true);
                rightPanel.removeAll();
                rightPanel.revalidate();
                rightPanel.repaint();
                try {
                    String[] chatsTemp = chats.get(currentUser).split(",");
                    for (String chat : chatsTemp) {
                        if (chat.startsWith("@sender@")) {
                            fillRecievedMessage(chat.substring(8));
                        } else if (chat.startsWith("@receiver@")) {
                            fillSentMessages(chat.substring(10));
                        } else {
                            //
                        }
                    }
                } catch (Exception ex) {
                    //
                }
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            int widthPassed = (int) (windowWidth - (windowWidth / 3));
            PrintWriter OUT;
            JLabel label = new JLabel("");
            label.setPreferredSize(new Dimension((int) (widthPassed / 2), 65));
            label.setBackground(new Color(245, 245, 245));
            label.setOpaque(true);
            rightPanel.add(label, BorderLayout.WEST);

            String mesg = userTypeField.getText().trim();
            JLabel label2 = new JLabel();
            int countLine = 30;
            int jumpNewline = 0;
            String messages = "<html>";
            for (int i = 0; i < mesg.length(); i++) {
                if (jumpNewline < 35) {
                    messages += mesg.substring(i, i + 1);
                    jumpNewline += 1;
                } else {
                    messages += "<br/>" + mesg.substring(i, i + 1);
                    countLine += 25;
                    jumpNewline = 0;
                }
            }
            messages += "<html/>";
            label2.setText(messages);
            label2.setPreferredSize(new Dimension((int) widthPassed / 3, countLine));
            label2.setBackground(Color.green);
            label2.setOpaque(true);
            label2.setFont(new Font(null, Font.ROMAN_BASELINE, 14));
            rightPanel.add(label2, BorderLayout.WEST);
            try {
                OUT = new PrintWriter(client.getOutputStream(), true);
                if (newChat) {
                    OUT.println(userTypeField.getText().trim());
                } else {
                    if (currentUser.equals("")) {
                        JOptionPane.showMessageDialog(null, "Follow the instructions for starting the new Chat!");
                    } else {
                        OUT.println("@" + currentUser + "@" + userTypeField.getText());
                    }
                }
                newChat = false;
            } catch (IOException ex) {
                //
            }
            String chatTemp;
            boolean userFound = false;
            for (String key : chats.keySet()) {
                if (currentUser.equals(key)) {
                    chatTemp = chats.get(key);
                    chatTemp = chatTemp + "@receiver@" + userTypeField.getText().trim() + ",";
                    chats.put(key, chatTemp);
                    userFound = true;
                    break;
                }
            }
            if (!userFound) {
                chatTemp = "@receiver@" + userTypeField.getText().trim() + ",";
                chats.put(currentUser, chatTemp);
            }
            userTypeField.setText("");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //
    }

    private class ClientSide implements Runnable {

        private PrintWriter OUT;

        public ClientSide() {
            leftTitleLabel.setText(userField.getText().trim());
            leftTitleLabel.setVisible(true);
            newMessagebtn.setVisible(true);
            connectBtn.setVisible(false);
            serverIpField.setVisible(false);
            userField.setVisible(false);
            userTypeField.setEnabled(true);
            sendBtn.setEnabled(true);
        }

        @Override
        public void run() {
            try {
                client = new Socket(serverIpField.getText().trim(), ConnectionUtility.PORT);
                OUT = new PrintWriter(client.getOutputStream(), true);
                OUT.println("@connectuser@" + userField.getText().trim());
                Thread clientThread = new Thread(new ClientServerHandler(client));
                clientThread.start();
            } catch (IOException e) {
                //
            }
        }

    }

    private class ClientServerHandler implements Runnable {

        private BufferedReader IN;
        private Socket clientSocket;

        public ClientServerHandler(Socket clientSocket) {
            try {
                this.clientSocket = clientSocket;
                this.IN = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {

            }
        }

        @Override
        public void run() {
            try {
                String recievedMessage;
                String messageSender;
                int indOfAt;
                while (true) {
                    recievedMessage = IN.readLine();
                    if (recievedMessage.equals("server_user_not_found")) {
                        JOptionPane.showMessageDialog(null, "The username for new user does not exist!");
                    } else if (recievedMessage.startsWith("server_user_found@")) {
                        int indUserReturned = recievedMessage.indexOf("@");
                        currentUser = recievedMessage.substring(indUserReturned + 1);
                        rightTitleLabel.setText(currentUser);
                        rightTitleLabel.setVisible(true);

                    } else {
                        indOfAt = recievedMessage.indexOf("@", 1);
                        messageSender = recievedMessage.substring(1, indOfAt);
                        recievedMessage = recievedMessage.substring(indOfAt + 1);
                        if (currentUser.equals("")) {
                            currentUser = messageSender;
                            rightTitleLabel.setText(currentUser);
                            rightTitleLabel.setVisible(true);
                        }
                        if (currentUser.equals(messageSender)) {
                            String chatTemp;
                            boolean userFound = false;
                            for (String key : chats.keySet()) {
                                if (messageSender.equals(key)) {
                                    chatTemp = chats.get(key);
                                    chatTemp = chatTemp + "@sender@" + recievedMessage + ",";
                                    chats.put(key, chatTemp);
                                    userFound = true;
                                    break;
                                }
                            }
                            if (!userFound) {
                                chatTemp = "@sender@" + recievedMessage + ",";
                                addLeftLabels(messageSender, recievedMessage);
                                chats.put(messageSender, chatTemp);
                            }

                            fillRecievedMessage(recievedMessage);
                        } else {
                            String chatTemp;
                            boolean userFound = false;
                            for (String key : chats.keySet()) {
                                if (messageSender.equals(key)) {
                                    chatTemp = chats.get(key);
                                    chatTemp = chatTemp + "@sender@" + recievedMessage + ",";
                                    chats.put(key, chatTemp);
                                    userFound = true;
                                    break;
                                }
                            }
                            if (!userFound) {
                                chatTemp = "@sender@" + recievedMessage + ",";
                                addLeftLabels(messageSender, recievedMessage);
                                chats.put(messageSender, chatTemp);
                            }
                        }

                    }

                }
            } catch (IOException e) {
                //
            }
        }

    }

}
