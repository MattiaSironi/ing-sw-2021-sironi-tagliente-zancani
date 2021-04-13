package it.polimi.ingsw.view;


import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.message.Nickname;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ModelMultiplayerView extends RemoteView implements Observer<Message>  {
    public ModelMultiplayerView(CLI cli) {
        cli.addObserver(new MessageReceiver());


    }
    public void sendnotify(String  c)  {
        notify(new Nickname(c, 00) );
    }

    public ModelMultiplayerView(ClientConnection c, int ID) {
        super(c, ID);
    }
    public void  iKnowMyNickname(String name)  {
        notify(new Nickname("I know you Nickname! it's "  +  name, 0));
    }

    private class MessageReceiver implements Observer<Message>{
        Socket socket;
        private boolean active = true;

        public synchronized boolean isActive(){
            return active;
        }

        public synchronized void setActive(boolean active){
            this.active = active;
        }
        @Override
        public void update(Message message) {

        }

        @Override
        public void update(Nickname message) {
            try {
                runna();
            } catch (IOException e) {
                System.out.println("aooooooo");
            }
        }
        public void runna() throws IOException {
            socket = new Socket("127.0.0.1", 1234);
            System.out.println("Connection established");
            ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
            try{
                Thread t0 = asyncReadFromSocket(socketIn);
                Thread t1 = asyncWriteToSocket(socketOut);
                t0.join();
                t1.join();
            } catch(InterruptedException | NoSuchElementException e){
                System.out.println("Connection closed from the client side");
            } finally {

                socketIn.close();
                socketOut.close();
                socket.close();
            }
        }
        public Thread asyncReadFromSocket(final ObjectInputStream socketIn){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (isActive()) {
                            Object inputObject = socketIn.readObject();
                            if(inputObject instanceof String){
                                sendnotify((String) inputObject);
                            }  else {
                                throw new IllegalArgumentException();
                            }
                        }
                    } catch (Exception e){
                        setActive(false);
                    }
                }
            });
            t.start();
            return t;
        }

        public Thread asyncWriteToSocket( final PrintWriter socketOut){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (isActive()) {
                            String inputLine = stdin.nextLine();
                            socketOut.println(inputLine);
                            socketOut.flush();
                        }
                    }catch(Exception e){
                        setActive(false);
                    }
                }
            });
            t.start();
            return t;
        }



    }

    }

