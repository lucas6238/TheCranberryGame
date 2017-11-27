package lucas.gem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

import javax.swing.Box;

/**
 * Created by chandler on 11/11/2017.
 */


public class Level {
    box[] boxes;
    int next;
    Stack<Integer> qs;
    public Level(){
        boxes=new box[1000];
        next=0;
        qs=new Stack<Integer>();

    }
    public void addBox(float x,float y,float width,float height,boolean physical, String text){
        System.out.println(x+" "+y+" "+width+" "+height+" "+physical+" "+text);
        if (qs.isEmpty()){
            boxes[next]=new box(x,y,width,height,physical,text);
            next++;
        }
    }
    void render(SpriteBatch sb){
        for (int i=0;i<next;i++){
            sb.draw(boxes[i].texture,boxes[i].getXPos(),boxes[i].getYPos(),boxes[i].getWidth(),boxes[i].getHeight());
        }
    }

}
