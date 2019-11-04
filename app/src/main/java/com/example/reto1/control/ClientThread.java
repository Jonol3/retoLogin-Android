package com.example.reto1.control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import retoLogin.Message;
import retoLogin.User;
import retoLogin.exceptions.AlreadyExistsException;
import retoLogin.exceptions.BadLoginException;
import retoLogin.exceptions.BadPasswordException;
import retoLogin.exceptions.LoginException;
import retoLogin.exceptions.NoThreadAvailableException;
import retoLogin.exceptions.RegisterException;

public class ClientThread extends Thread{
    private int puerto;
    private String ip;
    int type;
    private int answer;
    private User user;
    private User data;

    public ClientThread (String ip, int puerto, int type, User user) {
        this.ip = ip;
        this.puerto = puerto;
        this.type = type;
        this.user = user;
    }


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
        return;
    }

    @Override
    public void run() {
        if (type == 1) {
            Message message = new Message();
            Socket cliente = null;
            ObjectInputStream entrada = null;
            ObjectOutputStream salida = null;
            try {
                cliente = new Socket(ip,puerto);
                cliente.setSoTimeout(5000);

                salida = new ObjectOutputStream(cliente.getOutputStream());
                entrada = new ObjectInputStream(cliente.getInputStream());

                message.setUser(user);
                message.setType(1);
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
            Message message = new Message();
            Socket cliente = null;
            ObjectInputStream entrada = null;
            ObjectOutputStream salida = null;
            try {
                cliente = new Socket(ip, puerto);
                cliente.setSoTimeout(5000);

                salida = new ObjectOutputStream(cliente.getOutputStream());
                entrada = new ObjectInputStream(cliente.getInputStream());

                message.setUser(user);
                message.setType(2);
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

