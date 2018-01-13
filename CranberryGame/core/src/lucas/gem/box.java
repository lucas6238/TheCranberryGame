package lucas.gem;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;

/**
 * Created by chandler on 11/11/2017.
 */

public class box extends Actor{
    private static Random random=new Random();
    float[] bounds=new float[] {0,0,0,0};
    Color color;
    boolean physical;
    Texture texture;
    String textureName="";

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
        textureName=text;
    }
    boolean checkCollision(float x,float y){
        return x>bounds[0]&&x<bounds[0]+bounds[2]&&y>bounds[1]&&y<bounds[1]+bounds[3];
    }
    String getLine(){
        return bounds[0]+","+bounds[1]+","+bounds[2]+","+bounds[3]+","+physical+","+textureName+"\r\n";
    }
    public void setBounds(float[] b){
        System.arraycopy(b,0,bounds,0,4);
    }
    public void setPhysical(boolean b){
        physical=b;
    }
    public void setWidth(float n){
        bounds[3]=n;
    }
    public void setHeight(float n){
        bounds[4]=n;
    }
    public void setX(float n){
        bounds[0]=n;
    }
    public void setY(float n){
        bounds[1]=n;
    }
    public void setColor(float[] rgba){
        color.set(rgba[0],rgba[1],rgba[2],rgba[3]);
    }
    public Color getColor(){return color;}
    public float getXPos(){
        return bounds[0];
    }
    public float getWidth(){
        return bounds[2];
    }
    public float getYPos(){
        return bounds[1];
    }
    public float getHeight(){
        return bounds[3];
    }
    public float getXandWidth(){
        return bounds[0]+bounds[2];
    }
    public float getYandHeight(){
        return bounds[1]+bounds[3];
    }
}
