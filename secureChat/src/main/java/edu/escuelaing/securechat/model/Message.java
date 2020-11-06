/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.securechat.model;

import java.util.Observable;

/**
 *
 * @author diego
 */
public class Message extends Observable{
    
    private String mensaje;
    
    public Message(){
    }
    
    public String getMessage(){
        return mensaje;
    }
    
    public void setMessage(String mensaje){
        this.mensaje = mensaje;
        this.setChanged();
        this.notifyObservers(this.getMessage());
    }
    
}
