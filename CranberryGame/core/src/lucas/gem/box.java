package lucas.gem;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

/**
 * Created by chandler on 11/11/2017.
 */

public class box{
    private static Random random=new Random();
    float[] bounds=new float[] {0,0,0,0};
    Color color;
    boolean physical;
    Texture texture;

    box(float xPosition,float yPosition,float width, float height){
        bounds=new float[] {xPosition,yPosition,width,height};
        float[] colorHolder={0f,0f,0f,0f};
        for (int i=0;i<4;i++){
            colorHolder[i]=random.nextFloat();
        }
        color=new Color(colorHolder[0],colorHolder[1],colorHolder[2],colorHolder[3]);
        physical=true;
    }
    box(float xPosition,float yPosition,float width, float height,boolean phys,String text){
        bounds=new float[] {xPosition,yPosition,width,height};
        physical=phys;
        texture=new Texture(text);
    }
    void setPhysical(boolean b){
        physical=b;
    }
    void setWidth(float n){
        bounds[3]=n;
    }
    void setHeight(float n){
        bounds[4]=n;
    }
    void setX(float n){
        bounds[0]=n;
    }
    void setY(float n){
        bounds[1]=n;
    }
    void setColor(float[] rgba){
        color.set(rgba[0],rgba[1],rgba[2],rgba[3]);
    }
    Color getColor(){return color;}
    float getXPos(){
        return bounds[0];
    }
    float getWidth(){
        return bounds[2];
    }
    float getYPos(){
        return bounds[1];
    }
    float getHeight(){
        return bounds[3];
    }
    float getXandWidth(){
        return bounds[0]+bounds[2];
    }
    float getYandHeight(){
        return bounds[1]+bounds[3];
    }
}
