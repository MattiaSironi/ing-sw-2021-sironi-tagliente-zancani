package it.polimi.ingsw.model;

import java.io.Serializable;

/** Class Communication represent the Model part where players can have information on what's happening during the game.
 * Communications could be broadcasts (using addresseeID == -1) or personal.
 *
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

    /**
     * This method makes a deep copy of a Communication instance.
     * @return a deep clone of Communication.
     */

    public Communication clone()  {
        Communication clone = new Communication();
        clone.addresseeID = this.addresseeID;
        clone.communication = this.communication;
        return clone;
    }

}
