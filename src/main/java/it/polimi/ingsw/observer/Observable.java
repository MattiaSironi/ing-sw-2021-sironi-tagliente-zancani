package it.polimi.ingsw.observer;

import it.polimi.ingsw.message.ActionMessages.*;
import it.polimi.ingsw.message.CommonMessages.*;

import java.util.ArrayList;
import java.util.List;

public class Observable<Message> {

    private final List<Observer<Message>> observers = new ArrayList<>();

    public void addObserver(Observer<Message> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer<Message> observer) {
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    protected void notify(Message message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }
    }

    protected void notify(Nickname message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }

    }

    protected void notify(InputMessage message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }

    }

    protected void notify(IdMessage message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }

    }

    protected void notify(ErrorMessage message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }

    }

    protected void notify(OutputMessage message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }

    }
    protected void notify(ChooseNumberOfPlayer message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }

    }

    protected void notify(PrintableMessage message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }

    }

    protected void notify(ObjectMessage message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }

    }

    protected void notify(ManageResourceMessage message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }

    }
    protected void notify(MarketMessage message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }

    }
    protected void notify(ResourceListMessage message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }

    }

    protected void notify(PlaceResourceMessage message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }

    }
    protected void notify(BuyDevCardMessage message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }

    }


    protected void notify(PlayLeaderMessage message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }
    }

    protected void notify(ProductionMessage message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }

    }

    protected void notify(EndActionMessage message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }

    }

    protected void notify(EndTurnMessage message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }

    }

//    protected void notify(LeaderProdMessage message) {
//        synchronized (observers) {
//            for (Observer<Message> observer : observers) {
//                observer.update(message);
//            }
//        }
//
//   }

    protected void notify(BasicProductionMessage message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }

    }

    protected void notify(LeaderProductionMessage message) {
        synchronized (observers) {
            for (Observer<Message> observer : observers) {
                observer.update(message);
            }
        }

    }


}





