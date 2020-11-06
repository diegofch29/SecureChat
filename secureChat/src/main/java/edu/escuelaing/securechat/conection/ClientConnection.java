/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.securechat.conection;

import edu.escuelaing.securechat.model.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diego
 */
public class ClientConnection extends Thread implements Observer {
    
    private Socket clientSocket;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private List<ClientConnection> sockets = new ArrayList<>();
    public Message messages;
    

    public ClientConnection(Socket socket, Message message) {
        this.clientSocket = socket;
        this.messages = message;
    }
    
    @Override
    public void run(){
        messages.addObserver(this);
        try {
            while(true){
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine, outputLine;
                inputLine = in .readLine();
                System.out.println("Mensaje: " + inputLine);
                messages.setMessage(inputLine);
                outputLine = "Respuesta " + inputLine;   
                System.out.println(outputLine);
                if (outputLine.equals(null)){
                    out.close(); in .close();   
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
        try {
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void print(String outputLine){
        out.println(outputLine);
    }
    
    public void addSockets(List sockets){
        this.sockets =  sockets;
    }

    @Override
    public void update(Observable o, Object arg) {
       print(arg.toString());
    }
    

    
}
