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
public class AppClient {
    
    
    public static void main(String[] args){
        ClientConnection connection = new ClientConnection();
        try {
            connection.start();
        } catch (IOException ex) {
            Logger.getLogger(AppClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
