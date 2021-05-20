package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class LeaderCard groups the attributes common to all the Leader Cards
 *
 * @author Mattia Sironi
 */
//Modifiche al Model UML: attributo requirements type non è più un boolean ma è un int, perché ho 4 sottoclassi
public class LeaderCard implements Serializable, Printable {
    private final int type;
    private final int victoryPoints;


    public LeaderCard(int type, int victoryPoints) {
        this.type = type;
        this.victoryPoints = victoryPoints;

    }


    public int getType() {
        return type;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
//
//    public boolean same(LeaderCard lc){
//        boolean check = true;
//        if (lc.getType()!=this.getType() || lc.getVictoryPoints()!=this.getVictoryPoints())
//            return false;
//        switch (this.getType()){
//            case 1-> {
//                DiscountLCard dc= (DiscountLCard)lc;
//            }
//            case 2->{
//                ExtraDepotLCard me = (ExtraDepotLCard)this;
//                ExtraDepotLCard other = (ExtraDepotLCard)lc;
//                if(!(me.getResDepot()==other.getResDepot()&&
//                        me.getResType()==other.getResType()))
//                    return false;
//            }
//            case 3->{
//                ExtraProdLCard me = (ExtraProdLCard)this;
//                ExtraProdLCard other = (ExtraProdLCard)lc;
//                if(!(me.getInput()==other.getInput()&&
//                        me.getColor()==other.getColor()
//                       ))
//                    return false;
//            }
//            default->{
//                WhiteTrayLCard me = (WhiteTrayLCard)this;
//                WhiteTrayLCard other = (WhiteTrayLCard)lc;
//                if(!(me.getResType()==other.getResType()&&
//                        me.getX1Color()==other.getX1Color()&&
//                        me.getX2Color()==other.getX2Color()
//                ))
//                    return false;
//
//            }
//        }
//        return true;
//    }
    public boolean equals(LeaderCard lc){
        return false;
    }


    @Override
    public void print() {
    }
}
