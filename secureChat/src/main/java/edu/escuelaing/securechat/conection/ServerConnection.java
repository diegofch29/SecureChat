/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.securechat.conection;


import edu.escuelaing.securechat.model.Message;
 import java.net.*;
 import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author diego
 */
public class ServerConnection{
    
    public Message messages = new Message();

    public ServerConnection() {
        
    }
    
    
    public void start() throws IOException  {
        
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000, 2);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        List<ClientConnection> sockets = new ArrayList<>();
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                ClientConnection con = new ClientConnection(clientSocket, messages);
                System.out.println(con.toString());
                con.start();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            
        }
        
        //clientSocket.close();
        //serverSocket.close();
    }
}