package it.polimi.ingsw.message.ActionMessages;

import it.polimi.ingsw.message.Message;

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
