package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;

/**
 * ObjectMessage is sent from Controller to View in order to keep Views update with game changes.
 */

public class ObjectMessage extends Message {
    private final Object object;
    private final int objectID;
    private final int ID;

    public ObjectMessage(Object object, int objectID, int ID) {
        this.object = object;
        this.objectID = objectID;
        this.ID = ID;
    }

    public Object getObject() {
        return object;
    }

    public int getObjectID() {
        return objectID;
    }

    public int getID() {
        return ID;
    }

}
