package com.example.reto1.control;


import retoLogin.User;
import retoLogin.exceptions.*;
/**
 *
 * @author Daira Eguzkiza
 */
public interface Client {


    public User loginUser(User user) throws LoginException,
            BadLoginException, BadPasswordException, NoThreadAvailableException;

    public User registerUser(User user) throws RegisterException,
            AlreadyExistsException, NoThreadAvailableException;




}
