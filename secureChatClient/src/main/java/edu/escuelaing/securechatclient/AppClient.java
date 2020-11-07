/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.securechatclient;

import edu.escuelaing.securechatclient.conection.ClientConnection;
import edu.escuelaing.securechatclient.crypto.Crypto;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
// Librerías gráficas
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author diego
 */
public class AppClient extends JFrame {
    
    private JTextArea mensajesChat;
    private Socket socket;
    private JButton btEnviar;
    private JButton btConectar;
    ClientConnection connection;
    private static AppClient c;
    private Crypto crypto;
    
    public AppClient()
    {
        
    	super("Cliente Chat");
        crypto = new Crypto();
        crypto.genKeys();
        prepararElementos();
        prepararAcciones();
    }
    
    public void prepararElementos()
    {
	      
	      // Elementos de la ventana
	      mensajesChat = new JTextArea();
	      mensajesChat.setEnabled(false); // El area de mensajes del chat no se debe de poder editar
	      mensajesChat.setLineWrap(true); // Las lineas se parten al llegar al ancho del textArea
	      mensajesChat.setWrapStyleWord(true); // Las lineas se parten entre palabras (por los espacios blancos)
	      JScrollPane scrollMensajesChat = new JScrollPane(mensajesChat);
	      JTextField tfMensaje = new JTextField("");
	      btEnviar = new JButton("Enviar");
	      btConectar = new JButton("Conectar");
	      
	      
	      // Colocacion de los componentes en la ventana
	      Container c = this.getContentPane();
	      c.setLayout(new GridBagLayout());
	      
	      GridBagConstraints gbc = new GridBagConstraints();
	      
	      gbc.insets = new Insets(20, 20, 20, 20);
	      
	      gbc.gridx = 0;
	      gbc.gridy = 0;
	      gbc.gridwidth = 2;
	      gbc.weightx = 1;
	      gbc.weighty = 1;
	      gbc.fill = GridBagConstraints.BOTH;
	      c.add(scrollMensajesChat, gbc);
	
	      // Restaura valores por defecto
	      gbc.gridwidth = 1;        
	      gbc.weighty = 0;
	      
	      gbc.fill = GridBagConstraints.HORIZONTAL;        
	      gbc.insets = new Insets(0, 20, 20, 20);
	      
	      gbc.gridx = 0;
	      gbc.gridy = 1;
	      c.add(tfMensaje, gbc);
	      // Restaura valores por defecto
	      gbc.weightx = 0;
	      
	      gbc.gridx = 1;
	      gbc.gridy = 1;
	      c.add(btEnviar, gbc);
	      gbc.gridx = 1;
	      gbc.gridy = 2;
	      c.add(btConectar, gbc);
	      
	      this.setBounds(400, 100, 400, 500);
	      this.setVisible(true);
	      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    
    public void prepararAcciones()
    {
    	btEnviar.addActionListener(new ActionListener() {
             
             @Override
             public void actionPerformed(ActionEvent e) {
                 try {
                     System.out.println(crypto.encryptText("asdas").toString());
                     connection.send(crypto.encryptText("asdas").toString());
                 } catch (IOException ex) {
                     Logger.getLogger(AppClient.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
         });
    	btConectar.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
            	connection = new ClientConnection();
                
                try {
                    connection.start();
                    connection.send(crypto.getEncodedPublicKey());
                    System.out.println("--------------------------------");
                    crypto.decodeKey(connection.update());
                    System.out.println("--------------------------------");
                    Update update = new Update(connection,c);
                    update.start();
                } catch (IOException ex) {
                    Logger.getLogger(AppClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(AppClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeySpecException ex) {
                    Logger.getLogger(AppClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public String update(String message){
        if(crypto.getPublicKey().equals(null)){
            System.out.println(crypto.encryptText(message).toString());
            return crypto.decryptText(message.getBytes()).toString();
        }
        else {
            System.out.println(message);
            return message;
        }
    }
    
    public static void start(AppClient client){
        AppClient.c = client;
        
    }
    
    public static void main(String[] args) {     
    	
        AppClient c = new AppClient(); 
        c.start(c);
        
    }
    /*
    public static void main(String[] args){
        ClientConnection connection = new ClientConnection();
        try {
            connection.start();
        } catch (IOException ex) {
            Logger.getLogger(AppClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
}
