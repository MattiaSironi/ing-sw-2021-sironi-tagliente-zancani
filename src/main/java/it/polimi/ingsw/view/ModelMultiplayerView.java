package it.polimi.ingsw.view;


import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.message.Nickname;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class ModelMultiplayerView extends RemoteView implements Observer<Message>  {
    public ModelMultiplayerView(CLI cli) {
        cli.addObserver(new MessageReceiver());


    }
    private void lastchance() { notify(new Nickname("tutt ok, scrivi pure il numero:", 00));
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
        private Socket socket;
        private ObjectInputStream socketIn;
        private ObjectOutputStream socketOut;


        private boolean active = true;

        public synchronized boolean isActive(){
            return active;
        }

        public synchronized void setActive(boolean active){
            this.active = active;
        }


        private synchronized void send(Object message) {
            try {
                socketOut.reset();
                socketOut.writeObject(message);
                socketOut.flush();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

            @Override
        public void update(Message message) {

        }

        @Override
        public void update(Nickname message) {
            if (message.getString().equals("YES")) {
                try {
                    runna();
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("aooooooo");
                }
            } else {
                send(message);
            }
        }

        public void runna() throws IOException, ClassNotFoundException {
            Scanner scan = new Scanner(System.in);
            socket = new Socket("127.0.0.1", 1234);
            System.out.println("Connection established");
            socketIn = new ObjectInputStream(socket.getInputStream());
            socketOut = new ObjectOutputStream(socket.getOutputStream());
            String inputObject = (String) socketIn.readObject();
            sendnotify(inputObject);
            String scannerata =scan.nextLine();
            send(scannerata);
            inputObject = (String) socketIn.readObject();
            sendnotify(inputObject);
            inputObject = (String) socketIn.readObject();
            sendnotify(inputObject);
            scannerata =scan.nextLine();
            send(scannerata);
            inputObject = (String) socketIn.readObject();
            sendnotify(inputObject);
            inputObject = (String) socketIn.readObject();
            sendnotify(inputObject);
            inputObject = (String) socketIn.readObject();
            sendnotify(inputObject);




            socketIn.close();
            socketOut.close();
            socket.close();

        }

        public Thread asyncReadFromSocket(final ObjectInputStream socketIn){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (isActive()) {
                            Object inputObject = socketIn.readObject();
//                            if(inputObject instanceof String){
                                sendnotify((String) inputObject);
//                            }  else {
//                                throw new IllegalArgumentException();
//                            }
                        }
                    } catch (Exception e){
                        setActive(false);
                    }
                }
            });
            t.start();
            return t;
        }



//        public Thread asyncWriteToSocket( final PrintWriter socketOut){
//            Thread t = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        while (isActive()) {
//                            if (!ola.equals(""))
//                            socketOut.println(ola);
//                            socketOut.flush();
//                            ola="";
//                        }
//                    }catch(Exception e){
//                        setActive(false);
//                    }
//                }
//            });
//            t.start();
//            return t;
//        }



    }

    }

