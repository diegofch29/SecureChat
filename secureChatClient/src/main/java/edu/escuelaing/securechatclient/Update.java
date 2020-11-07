/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.securechatclient;

import edu.escuelaing.securechatclient.conection.ClientConnection;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diego
 */
public class Update extends Thread {
    
    ClientConnection connection;
    AppClient client;
    
    public Update(ClientConnection connection, AppClient client){
        this.client = client;
        this.connection = connection;
        
    }
    
    @Override
    public void run(){
        while (true){
            try {
                
                client.update(connection.update());
            } catch (IOException ex) {
                Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        
}
