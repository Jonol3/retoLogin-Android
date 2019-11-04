package com.example.reto1.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import retoLogin.Message;
import retoLogin.User;
import retoLogin.exceptions.AlreadyExistsException;
import retoLogin.exceptions.BadLoginException;
import retoLogin.exceptions.BadPasswordException;
import retoLogin.exceptions.LoginException;
import retoLogin.exceptions.NoThreadAvailableException;
import retoLogin.exceptions.RegisterException;

/**
 * The class used as connection with the server
 * @author Jon Calvo Gaminde
 */
public class ClientThread extends Thread{
    private int puerto;
    private String ip;
    private int type;
    private int answer;
    private User user;
    private User data;

    public ClientThread (String ip, int puerto, int type, User user) {
        this.ip = ip;
        this.puerto = puerto;
        this.type = type;
        this.user = user;
    }

    /**
     * This methods returns the result of a login.
     * @return The full user that gets from the server (if it has it), else null.
     * @throws BadLoginException The login was wrong.
     * @throws BadPasswordException The password was wrong.
     * @throws LoginException An Exception of the database, or failed to connect to it.
     * @throws NoThreadAvailableException The database is overloaded.
     */
    public synchronized User getLoginResult() throws BadLoginException, BadPasswordException, LoginException, NoThreadAvailableException {
        switch (answer) {
            case 0:
                return data;
            case 1:
                throw new LoginException("Error trying to log in.");
            case 2:
                throw new NoThreadAvailableException("Server is busy.");
            case 3:
                throw new BadLoginException("Bad login.");
            case 4:
                throw new BadPasswordException("The password is not "
                        + "correct.");
        }
        return null;
    }

    /**
     * This methods checks the result of a register.
     * @throws AlreadyExistsException The users login already exists in the DB.
     * @throws NoThreadAvailableException The database is overloaded.
     * @throws RegisterException An Exception of the database, or failed to connect to it.
     */
    public synchronized void getRegisterResult() throws AlreadyExistsException, NoThreadAvailableException, RegisterException {
        switch (answer) {
            case 0:
                return;
            case 1:
                throw new RegisterException("Error trying to log in.");
            case 2:
                throw new NoThreadAvailableException("Server is busy.");
            case 3:
                throw new AlreadyExistsException("User already exists.");
        }
    }

    /**
     * This method creates the socket and does the DAO operation.
     */
    @Override
    public void run() {
        if (type == 1) {
            //This operation is a login
            Message message = new Message();
            Socket cliente = null;
            ObjectInputStream entrada = null;
            ObjectOutputStream salida = null;
            try {
                cliente = new Socket();
                cliente.connect(new InetSocketAddress(ip,puerto), 2000);

                salida = new ObjectOutputStream(cliente.getOutputStream());
                entrada = new ObjectInputStream(cliente.getInputStream());

                message.setUser(user);
                message.setType(type);
                salida.writeObject(message);
                Message m = (Message) entrada.readObject();

                answer = m.getType();
                if (answer == 0)
                    data = m.getUser();
                else
                    data = null;
            } catch (IOException | ClassNotFoundException  e) {
                //SocketTimeoutException and SocketException are caught by the IOException
                answer = 1;
            } finally {
                try {
                    if (cliente != null) {
                        cliente.close();
                    }
                    if (entrada != null) {
                        entrada.close();
                    }

                    if (salida != null) {
                        salida.close();
                    }
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }

        } else if (type == 2){
            //This operation is a register
            Message message = new Message();
            Socket cliente = null;
            ObjectInputStream entrada = null;
            ObjectOutputStream salida = null;
            try {
                cliente = new Socket();
                cliente.connect(new InetSocketAddress(ip,puerto), 2000);

                salida = new ObjectOutputStream(cliente.getOutputStream());
                entrada = new ObjectInputStream(cliente.getInputStream());

                message.setUser(user);
                message.setType(type);
                salida.writeObject(message);

                Message m = (Message) entrada.readObject();

                answer = m.getType();
            } catch (IOException | ClassNotFoundException e) {
                //SocketTimeoutException and SocketException are caught by the IOException
                answer = 1;
            } finally {

                try {
                    if (cliente != null) {
                        cliente.close();
                    }
                    if (entrada != null) {
                        entrada.close();
                    }

                    if (salida != null) {
                        salida.close();
                    }
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }

    }
}

