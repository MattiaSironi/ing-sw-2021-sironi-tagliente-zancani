package it.polimi.ingsw.observer;

import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;


public interface Observer<Message> {

    void update(Message message);

    void update(Nickname message);

    void update(InputMessage message);

    void update(IdMessage message);

    void update(ErrorMessage message);

    void update(OutputMessage message);

    void update (ChooseNumberOfPlayer message);

    void update (PrintableMessage message);

    void update (ObjectMessage message);

    void update (ManageResourceMessage message);

    void update(MarketMessage message);

    void update(ResourceListMessage message);

    void update(PlaceResourceMessage message);

    void update(BuyDevCardMessage message);

    void update(PlayLeaderMessage message);

    void update(ProductionMessage message);
}
