/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.reto1.control;

import java.util.logging.Logger;
import retoLogin.User;
import retoLogin.exceptions.*;

/**
 * This is the class that implements the Client methods.
 * @author Daira Eguzkiza, Jon Calvo Gaminde
 */
public class ClientImplementation implements Client {
    String ip;
    int puerto;
    private static final Logger LOGGER = Logger
            .getLogger("com.example.reto1.control.ClientImplementation");

    public ClientImplementation(String ip, int puerto) {
        this.ip = ip;
        this.puerto = puerto;
    }

    @Override
    @SuppressWarnings("LoggerStringConcat")
    public User loginUser(User user) throws LoginException,
            BadLoginException, BadPasswordException, NoThreadAvailableException {

        ClientThread clientThread = new ClientThread(ip, puerto, 1, user);
        clientThread.start();
        try {
            clientThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User data = clientThread.getLoginResult();
        return data;
    }

    @Override
    @SuppressWarnings("LoggerStringConcat")
    public User registerUser(User user) throws RegisterException,
            AlreadyExistsException, NoThreadAvailableException {
        ClientThread clientThread = new ClientThread(ip, puerto, 2, user);
        clientThread.start();
        try {
            clientThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clientThread.getRegisterResult();
        return user;
    }
}
