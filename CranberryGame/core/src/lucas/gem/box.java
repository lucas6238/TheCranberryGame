package lucas.gem;

import java.util.Random;

/**
 * Created by chandler on 11/11/2017.
 */

public class box{
    Random random=new Random();
    float[] bounds=new float[] {0,0,0,0};
    float[] color=new float[] {0,0,0,0};
    box(float xPosition,float yPosition,float width, float height){
        bounds=new float[] {xPosition,yPosition,width,height};
        color=new float[]{random.nextFloat(),random.nextFloat(),random.nextFloat(),1};
    }
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
