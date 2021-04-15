package it.polimi.ingsw.view;


import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.message.Nickname;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.Scanner;

public class CLI extends Observable<Message> implements Observer<Message> {
    Scanner in;

public void run()  {
    System.out.println("welcome to the game. this is an alpha version so you will be connected to the loopback address");
    System.out.println("type YES if you are ready to this experience:");

        in= new Scanner(System.in);
        String input =in.next();
        notify(new Nickname(input, 00));
        while (true) {
             input =in.next();
            notify(new Nickname(input, 00));
        }


}
    @Override
    public void update(Message message) {

    }

    @Override
    public void update(Nickname message) {
        System.out.println(message.getString());

    }
}
