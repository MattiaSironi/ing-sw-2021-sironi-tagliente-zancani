package it.polimi.ingsw.message.CommonMessages;

import it.polimi.ingsw.model.Printable;

public class PrintableMessage {
    Printable p;

    public PrintableMessage(Printable p) {
        this.p = p;
    }

    public Printable getP() {
        return p;
    }
}
