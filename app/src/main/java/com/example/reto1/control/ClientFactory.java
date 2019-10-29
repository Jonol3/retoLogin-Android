/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.reto1.control;

/**
 *
 * @author Daira Eguzkiza
 */
public class ClientFactory {
    public static Client getClient(){
        return new ClientImplementation();
    }
    
}
