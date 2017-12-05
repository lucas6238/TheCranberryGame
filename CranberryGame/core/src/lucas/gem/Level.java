package lucas.gem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import javax.swing.Box;

/**
 * Created by chandler on 11/11/2017.
 */
public class Level {
    Stack<box> boxes;


    int next;

    String level[];
    int nextLevel;

    Stack<Integer> qs;

    BufferedReader br;

    public Level(){
        boxes=new Stack<box>();
        next=0;
        qs=new Stack<Integer>();
        level=new String[]{"start.level","start.level","start.level","start.level","start.level"};
        nextLevel=0;
    }
    Random random = new Random();
    box[] getBoxes(){
        boxes=new Stack<box>();
        readFile();
        box[] bux=new box[boxes.size()];
        System.arraycopy(boxes.toArray(),0,bux,0,boxes.size());
        return bux;
    }
    void readFile(){
        try{
            br=new BufferedReader(new FileReader(new File(level[nextLevel])));
            nextLevel++;
            String line;
            String[] data;
            while ((line=br.readLine())!=null){
                data=line.split(" ");
                if (!data[0].startsWith("/")) {
                    addBox(Float.valueOf(data[0]), Float.valueOf(data[1]), Float.valueOf(data[2]), Float.valueOf(data[3]), Boolean.valueOf(data[4]), data[5]);
                }
            }
        }catch(FileNotFoundException fnfe){

        }catch (IOException ioe){

        }
    }
    int getLength(){
        return level.length;
    }
    public void addBox(){
        for (int i=0;i<10;i++) {
            boxes.push(new box(i*108, 0, 108, 100, true, "groundTexture2b.png"));
        }
        int num=(int)((random.nextFloat()/2+.5)*CranGame.nextBorder);
        if (num>5){
            num=5;
        }
        switch (num){
            case 0:
                break;
            case 5:
                boxes.push(new box(900,100,100,100,true,"box4a.png"));
            case 4:
                boxes.push(new box(525,100,100,250,true,"box4a.png"));
            case 3:
                boxes.push(new box(400,100,100,100,true,"box4a.png"));
            case 2:
                boxes.push(new box(200,100,100,100,true,"box4a.png"));
            case 1:
                boxes.push(new box(650,100,100,100,true,"box4a.png"));

        }
    }
    public void addBox(float x,float y,float width,float height,boolean physical, String text){
        boxes.push(new box(x,y,width,height,physical,text));

    }
    void render(SpriteBatch sb){
        for (int i=0;i<next;i++){
            //sb.draw(boxes[i].texture,boxes[i].getXPos(),boxes[i].getYPos(),boxes[i].getWidth(),boxes[i].getHeight());
        }
    }

}
