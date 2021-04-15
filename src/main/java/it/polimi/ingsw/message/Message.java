package it.polimi.ingsw.message;

import java.io.Serializable;

abstract public class Message implements Serializable {


    abstract public String getString();

    abstract public void setString(String nickname);

    abstract public int getID();

    abstract public void setID(int ID);
}
