package it.polimi.ingsw.view;

import com.google.gson.Gson;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.Market;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;

public class MainTest {

    public Market main(){
        Gson gson = new Gson();
        Reader reader =  new InputStreamReader(MainTest.class.getResourceAsStream("/json/tray.json"));
        Marble[]  biglie  = gson.fromJson(reader, Marble[].class);
        Collections.shuffle(Arrays.asList(biglie));
        Marble[][] mercato = new Marble[3][4];
        for (int i=0, k=0; i<3; i++)  {
            for (int j=0 ; j<4 ; j++ )  {
                mercato[i][j]= biglie[k];
                k++;
            }
        }

        Market m = new Market(mercato, biglie[12]);

        return m;


    }

    public MainTest() {
    }
}
