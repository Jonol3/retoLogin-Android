package com.example.reto1.control;


import retoLogin.User;
import retoLogin.exceptions.*;
/**
 * This is the interface of the Client methods.
 * @author Daira Eguzkiza, Jon Calvo Gaminde
 */
public interface Client {

    /**
     * Method that connects with a server using a socket and returns a message
     * with the user that's trying to log in (if everything's okay) and the
     * response type.
     *
     * @param user A username and password received by the login window.
     * @return The full user that gets from the server (if it has it).
     * @throws LoginException An unknown error occurred while trying to log in
     * the user.
     * @throws BadLoginException The entered user does not exist on the
     * database.
     * @throws BadPasswordException The password doesn't match with the one from
     * the database.
     * @throws NoThreadAvailableException The maximum number of threads
     * available for the clients has been reached.
     */
    public User loginUser(User user) throws LoginException,
            BadLoginException, BadPasswordException, NoThreadAvailableException;

    /**
     * Method that connects with a server using a socket and returns a message
     * with the registered user (if everything's gone right) and the response
     * type.
     *
     * @param user All the data from a user received by the sign up window.
     * @return The user once is signed in (if everything's gone right.)
     * @throws RegisterException There's been an exception while trying to sign
     * in the user.
     * @throws AlreadyExistsException The username is already in use by another
     * user.
     * @throws NoThreadAvailableException The maximum number of threads
     * available for the clients has been reached.
     */
    public User registerUser(User user) throws RegisterException,
            AlreadyExistsException, NoThreadAvailableException;




}
