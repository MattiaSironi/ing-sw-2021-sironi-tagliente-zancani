package it.polimi.ingsw.message;

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
