package lucas.gem;

import com.badlogic.gdx.graphics.Color;

import java.util.Random;

/**
 * Created by chandler on 11/11/2017.
 */

public class box{
    private static Random random=new Random();
    float[] bounds=new float[] {0,0,0,0};
    Color color;

    box(float xPosition,float yPosition,float width, float height){
        bounds=new float[] {xPosition,yPosition,width,height};
        float[] colorHolder={0f,0f,0f,0f};
        for (int i=0;i<4;i++){
            colorHolder[i]=random.nextFloat();
        }
        color=new Color(colorHolder[0],colorHolder[1],colorHolder[2],colorHolder[3]);
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
