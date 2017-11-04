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

    private Rectangle bounds;
    private Texture cranTexture;
    private Sprite cranSprite;
    private boolean isTouchingGround;
    private Animation cranAnimation;


    public Berry(int x, int y){
        position = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);
        accel = new Vector3(0,0,0);
        gravity = new Vector3(0,-1,0);

        cranTexture =  new Texture("NewCB4_FrontAnimated.png");

        cranAnimation = new Animation(cranTexture,12,64f);
        cranSprite = new Sprite(cranTexture,0,0,32,32);
        bounds = new Rectangle(x,y,128,128);
    }
    public void update(float dt){

        handleInput();
        adjustments();
        cranAnimation.update(dt);

        checkEdges();
        accel = new Vector3(0,0,0);


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
        if(Gdx.input.isKeyPressed(62)){

            accel.add(0, 2, 0);
            velocity.set(0,10,0);
            System.out.println(velocity.y);
            System.out.println(accel.y);
        }
    }
    private void adjustments(){
        checkGround();
        accel.add(gravity);
        velocity.add(accel);

        position.add(velocity);


    }
    public void checkEdges(){
        if(this.position.y<0){
            position.y=0;
        }
        if(this.position.y>CranGame.HEIGHT-32){
            position.y=CranGame.HEIGHT -32;
            velocity.y=0;
            System.out.println("celling");
        }

    }
    public void checkGround(){
        if(this.position.y <=0){
            velocity.y =0;
        }


    }


}
