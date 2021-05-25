package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * @author Mattia Sironi
 */
public class Communication implements Serializable {

    private int addresseeID;
    private CommunicationList communication;



    public int getAddresseeID() {
        return addresseeID;
    }

    public void setAddresseeID(int addresseeID) {
        this.addresseeID = addresseeID;
    }

    public CommunicationList getCommunication() {
        return communication;
    }

    public void setCommunication(CommunicationList communication) {
        this.communication = communication;
    }

    public Communication clone()  {
        Communication clone = new Communication();
        clone.addresseeID = this.addresseeID;
        clone.communication = this.communication;
        return clone;
    }
}
