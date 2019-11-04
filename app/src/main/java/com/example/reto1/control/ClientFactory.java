/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.reto1.control;

/**
 * The factory of the Client.
 * @author Daira Eguzkiza, Jon Calvo Gaminde
 */
public class ClientFactory {
    /**
     * Creates a new Client Object and returns it.
     * @param ip The IP of the server
     * @param port The port of the server
     * @return The new Client object.
     */
    public static Client getClient(String ip, int port){
        return new ClientImplementation(ip, port);
    }
    
}
