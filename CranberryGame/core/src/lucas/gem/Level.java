package lucas.gem;

import java.util.Random;

/**
 * Created by chandler on 11/11/2017.
 */


public class Level {
    box[] boxes;
    public Level(BoxConfig b){
        switch(b){
            case LEVEL1:
                boxes=new box[] {
                        new box(0,0,1080,100),
                        new box(200,100,480,100),
                        new box (450,200,340,100)
                };
        }
    }
    public boolean getBox(int i,float[] cords,float[] color){
        if (i<boxes.length){
            System.arraycopy(boxes[i].bounds,0,cords,0,4);
            System.arraycopy(boxes[i].color,0,color,0,4);
            return true;
        }
        return false;
    }
}
