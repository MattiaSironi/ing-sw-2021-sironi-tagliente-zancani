package it.polimi.ingsw.model;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Mattia Sironi
 */
public class FaithTrack implements Printable, Serializable { //ObjectID = 13
    private int marker;
    private Integer favorTile1;
    private Integer favorTile2;
    private Integer favorTile3;
    private final ArrayList<Slot> faithTrackSlot;
    private Integer loriPos;


    public FaithTrack(Integer loriPos) {
        favorTile1 = null;
        favorTile2 = null;
        favorTile3 = null;
        faithTrackSlot = createFaithTrack();
        marker = 0;
        this.loriPos = loriPos;
    }

    /**
     * Method createFaithTrack parses a JSON file and creates a FaithTrack instance
     * @return a FaithTrack instance.
     */

    private ArrayList<Slot> createFaithTrack() {
        Gson gson = new Gson();
        Reader reader =  new InputStreamReader(PersonalBoard.class.getResourceAsStream("/json/slot.json"));
        Slot[]  slots  = gson.fromJson(reader, Slot[].class);
        ArrayList<Slot> faithTrack = new ArrayList<>(Arrays.asList(slots));
        return  faithTrack;

    }

    /**
     * Method moveFaithMarkerPos moves the marker on the FaithTrack by param q.
     */
    public void moveFaithMarkerPos(int q) {
        this.marker += q;
        if (marker>24) marker=24;
    }

    /**
     * Method setFavorTile sets one of the favorTiles (selected with param ft) by param value.
     */

    public void setFavorTile(int ft, int value) {
        switch (ft) {
            case 0:
                this.favorTile1 = value;
                break;
            case 1:
                this.favorTile2 = value;
                break;
            case 2:
                this.favorTile3 = value;
                break;
            default:
        }
    }

    public int getMarker() {
        return marker;
    }

    public void setMarker(int marker) {
        this.marker = marker;
    }

    public Integer getFavorTile1() {
        return favorTile1;
    }

    public Integer getFavorTile2() {
        return favorTile2;
    }

    public Integer getFavorTile3() {
        return favorTile3;
    }

    public ArrayList<Slot> getFaithTrackSlot() {
        return faithTrackSlot;
    }



    public Integer getLoriPos() {
        return loriPos;
    }

    public void setLoriPos(Integer loriPos) {
        this.loriPos = loriPos;
    }

    /**
     * Method print prints to console a FaithTrack instance.
     */

    @Override
    public void print() {

            System.out.println("POSITION = " + getMarker());
            if (getFavorTile1()!=null) System.out.println("FAVOR TILE 1 = " + getFavorTile1());
            if (getFavorTile2()!=null) System.out.println("FAVOR TILE 2 = " + getFavorTile2());
            if (getFavorTile3()!=null) System.out.println("FAVOR TILE 3 = " + getFavorTile3());
            if (loriPos != null) System.out.println("LORENZO IL MAGNIFICO POSITION = " + getLoriPos());


    }

    /**
     * Method moveLoriPos moves Lorenzo il Mangifico on the FaithTrack by param number.
     */

    public void moveLoriPos(int number) {
        this.loriPos += number;
        if (loriPos >= 24) loriPos=24;


    }

    /**
     * Method clone creates a deep copy of a FaithTrack instance.
     * @return a deep copy of a FaithTrack instance.
     */

    public FaithTrack clone() {
        FaithTrack clone;
        if (loriPos == null) clone = new FaithTrack(null);
        else {
            int loripos = loriPos;
            clone = new FaithTrack(loripos);
        }
        clone.marker = this.marker;
        clone.favorTile1 = this.favorTile1;
        clone.favorTile2 = this.favorTile2;
        clone.favorTile3 = this.favorTile3;
        return clone;
    }
}
