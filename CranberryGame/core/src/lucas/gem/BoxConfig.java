package lucas.gem;

import com.badlogic.gdx.utils.Array;

/**
 * Created by chandler on 11/11/2017.
 */

enum BoxConfig {
    CONFIG1(1),CONFIG2(2),CONFIG3(3),CONFIG4(4),CONFIG5(5);
    Array<box> config;
    BoxConfig(int i){
        this.config=new Array<box>();
        switch (i){
            case 1:
                config.add(new box(0,0,1080,50));
                config.add(new box(500,200,200,20));
                config.add(new box(790,200,290,20));
                break;
            case 2:
                config.add(new box(0,0,1080,50));
                config.add(new box(200,50,150,100));
                config.add(new box(350,50,150,200));
                config.add(new box(500,200,150,10));
                break;
            case 3:
                config.add(new box(0,0,100,50));
                config.add(new box(170,0,100,200));
                config.add(new box(170,0,230,50));
                config.add(new box(370,350,100,100));
                config.add(new box(570,500,430,50));
                config.add(new box(570,0,510,50));
                break;
            case 4:
                config.add(new box(0,0,1080,50));
                config.add(new box(200,50,100,100));
                config.add(new box(600,50,60,124));
                config.add(new box(0,500,250,50));
                config.add(new box(500,450,300,50));
                config.add(new box(950,500,130,100));
            case 5:
                config.add(new box(0,0,1080,50));
                config.add(new box(100,100,50,50));
                config.add(new box(200,100,50,150));
                config.add(new box(300,100,50,250));
                config.add(new box(400,100,50,350));
                break;
        }
    }
    box[] getBoxes(){
        return this.config.toArray(box.class);
    }
    Array<box> getBoxArray(){
        return this.config;
    }
}
