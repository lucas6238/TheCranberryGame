package lucas.gem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Lucas on 11/4/2017.
 */

public class Berry {
    private Vector3 position;
    private Vector3 velocity;
    private Vector3 accel;
    private Vector3 gravity;

    Vector3 size=new Vector3(128,128,0);

    private Rectangle bounds;
    private Texture cranTexture;
    private Sprite cranSprite;
    private boolean isTouchingGround;
    private Animation cranAnimation;

    BoxConfig[] boxConfigs;
    int numOfBoxes;
    box[][] boxes;

    public Berry(int x, int y){
        position = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);
        accel = new Vector3(0,0,0);
        gravity = new Vector3(0,-1,0);

        cranTexture =  new Texture("NewCB4_FrontAnimated.png");

        cranAnimation = new Animation(cranTexture,12,64f);
        cranSprite = new Sprite(cranTexture,0,0,32,32);
        bounds = new Rectangle(x,y,128,128);
        boxConfigs=new BoxConfig[2];
        boxes=new box[2][];
        numOfBoxes= 0;
    }

    void giveBoxConfig(box[][] b){
        System.arraycopy(b,0,boxes,0,2);

    }
    public void update(float dt){
        handleInput();
        adjustments();
        cranAnimation.update(dt);
    }

    public Sprite getCran(){
        return cranSprite;
    }
    public Vector3 getPosition(){
        return position;
    }
    public TextureRegion getFrame(){
        return cranAnimation.getFrame();
    }

    public void handleInput(){
        if(isTouchingGround&&Gdx.input.isKeyPressed(62)){//62=spacebar
            velocity.set(0,13,0);
            isTouchingGround=false;
        }
    }

    private void adjustments(){
        if (!Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            velocity.add(gravity);
        }else{
            velocity.y+=gravity.y*.5f;
        }
        velocity.x+=1;
        if (velocity.x>5){
            velocity.x=5;
        }
        if (checkX()) {
            position.x += velocity.x;
        }else{
            velocity.x=0;
        }


        if (checkY()) {
            position.y += velocity.y;
        }else{
            if (velocity.y<0){
                isTouchingGround=true;
            }
            velocity.y=0;
        }
    }

    public boolean checkX(){

        for (int j=0;j<2;j++) {
            numOfBoxes=boxes[j].length;
            for (int i = 0; i < numOfBoxes; i++) {
                if (position.y + size.y > boxes[j][i].getYPos() &&
                        position.y < boxes[j][i].getYandHeight() &&
                        velocity.x + position.x + size.x > boxes[j][i].getXPos()+1080*(CranGame.nextBorder-1+j) &&
                        velocity.x + position.x < boxes[j][i].getXandWidth()+1080*(CranGame.nextBorder-1+j)
                        ) {
                    position.x = boxes[j][i].getXPos()+1080*(CranGame.nextBorder-1+j) - size.x;
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkY(){
        for (int j=0;j<2;j++){
            numOfBoxes=boxes[j].length;
            for (int i=0;i<numOfBoxes;i++){
                if (    position.y+velocity.y+size.y>boxes[j][i].getYPos()&&
                        position.y+velocity.y<boxes[j][i].getYandHeight()&&
                        position.x+size.x>boxes[j][i].getXPos()+1080*(CranGame.nextBorder-1+j)&&
                        position.x<boxes[j][i].getXandWidth()+1080*(CranGame.nextBorder-1+j)){
                    if (velocity.y<0){
                        position.y=boxes[j][i].getYandHeight();
                    }
                    return false;
                }
            }
        }
        return true;
    }



}
