package lucas.gem;

import javax.swing.Box;

/**
 * Created by chandler on 11/11/2017.
 */


public class Level {
    box[][] order;
    public Level(int i){
        switch (i){
            case 1:
                order = new box[][] {makeBoxes(BoxConfig.CONFIG1),makeBoxes(BoxConfig.CONFIG2), makeBoxes(BoxConfig.CONFIG3),makeBoxes(BoxConfig.CONFIG4),makeBoxes(BoxConfig.CONFIG5)};
                break;
        }
    }
    private box[] makeBoxes(BoxConfig boxConfig){
        box[] boxes=boxConfig.getBoxArray().toArray(box.class);
        return boxes;
    }
    box[] getBoxes(int i){
        System.out.println(i);
        return order[i];
    }
    int getLength(){
        return order.length;
    }
}
