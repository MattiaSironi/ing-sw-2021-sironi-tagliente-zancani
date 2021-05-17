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
    private int favorTile1;
    private int favorTile2;
    private int favorTile3;
    private ArrayList<Slot> faithTrackSlot;


    public FaithTrack() {
        favorTile1 = 0;
        favorTile2 = 0;
        favorTile3 = 0;
        faithTrackSlot = createFaithTrack();
        marker = 0;
    }

    private ArrayList<Slot> createFaithTrack() {
        Gson gson = new Gson();
        Reader reader =  new InputStreamReader(PersonalBoard.class.getResourceAsStream("/json/slot.json"));
        Slot[]  slots  = gson.fromJson(reader, Slot[].class);
        ArrayList<Slot> faithTrack = new ArrayList<>();
        faithTrack.addAll(Arrays.asList(slots));
        return  faithTrack;

    }
    public void moveFaithMarkerPos(int q) {
        this.marker += q;
        if (marker>24) marker=24;
    }

    public void setFavorTile(int ft) {
        switch (ft) {
            case 0:
                this.favorTile1 = 1;
                break;
            case 1:
                this.favorTile2 = 1;
                break;
            case 2:
                this.favorTile3 = 1;
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

    public int getFavorTile1() {
        return favorTile1;
    }

    public void setFavorTile1(int favorTile1) {
        this.favorTile1 = favorTile1;
    }

    public int getFavorTile2() {
        return favorTile2;
    }

    public void setFavorTile2(int favorTile2) {
        this.favorTile2 = favorTile2;
    }

    public int getFavorTile3() {
        return favorTile3;
    }

    public void setFavorTile3(int favorTile3) {
        this.favorTile3 = favorTile3;
    }

    public ArrayList<Slot> getFaithTrackSlot() {
        return faithTrackSlot;
    }

    public void setFaithTrackSlot(ArrayList<Slot> faithTrackSlot) {
        this.faithTrackSlot = faithTrackSlot;
    }

    @Override
    public void print() {

        System.out.println("POSITION = " + getMarker());
        System.out.println("FAVOR TILE 1 = " + getFavorTile1());
        System.out.println("FAVOR TILE 2 = " + getFavorTile2());
        System.out.println("FAVOR TILE 3 = " + getFavorTile3());

    }
}
