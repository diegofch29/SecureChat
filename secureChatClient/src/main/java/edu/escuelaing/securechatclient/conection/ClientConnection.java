/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.securechatclient.conection;


import edu.escuelaing.securechatclient.crypto.Crypto;
import java.io.*;
import java.net.*;

/**
 *
 * @author diego
 */

public class ClientConnection {
    
    Socket echoSocket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    BufferedReader stdIn = null;
    Crypto crypto;
        
        
    public ClientConnection() {
        
        crypto = new Crypto();
        
    }
    
    
    public  void start() throws IOException {
        
        
        try {
            echoSocket = new Socket("127.0.0.1", 35000);
            out = new PrintWriter(echoSocket.getOutputStream(), true); in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don’t know about host!.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn’t get I/O for " + "the connection to: localhost.");
            System.exit(1);
        }
        
        
    }
    
    public void send(String userInput) throws IOException{
        String s = crypto.encrypt(userInput);
        System.out.println("SEND "+ s.getBytes("UTF-8") +" "+ s.getBytes("UTF-8").length);
        out.println(s);
    }
    
    public String update() throws IOException{
        String s;
        if ((s = in.readLine()) != null){
            System.out.println("UPDATE: " + s.getBytes("UTF-8") +" "+ s.getBytes("UTF-8").length);
            return crypto.decrypt(s);
        }
        return s;
    }
    
    public void close() throws IOException{
        out.close(); 
        in.close();
        stdIn.close();
        echoSocket.close();
}
    
}

