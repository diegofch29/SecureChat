/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.securechat.conection;

import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author diego
 */
public class ClientConnection extends Thread implements Observer{
    
    public Socket socket;
    

    public ClientConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}