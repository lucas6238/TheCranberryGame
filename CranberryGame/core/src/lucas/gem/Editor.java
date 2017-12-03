package lucas.gem;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Chandler on 11/16/2017.
 */

public class Editor extends ApplicationAdapter {
    static Color color = new Color(.1f, .5f, 0f, 1);
    ShapeRenderer sr;
    SpriteBatch sb;
    OrthographicCamera cam;
    float x,y;
    float mousex,mousey;

    private box[] boxes;
    private int boxesPlace;
    Menu menu;

    Texture img;    @Override
    public void create() {
        menu=new Menu();

        boxes=new box[1000];
        boxesPlace=0;
        cam = new OrthographicCamera(1080, 1920);
        sr = new ShapeRenderer();
        sb=new SpriteBatch();
        cam.position.set(1920 / 2, 1080 / 2, 0);

        //this looks retarded but there's an issue where the screen is a few pixels lower than it should be until it's resized. This fixes it
        Gdx.graphics.setResizable(false);
        Gdx.graphics.setResizable(true);


    }

    @Override
    public void render() {
        update();
        Gdx.gl.glClearColor(0, 0, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        renderLevel();
        sb.end();
    }
    void rendershapes(){
        sr.setAutoShapeType(true);
        sr.setProjectionMatrix(cam.combined);
        sr.begin();
        sr.box(x,y,0,105,105,0);
        sr.end();
    }
    void renderLevel(){
        for (int i=0;i<boxesPlace;i++){
            sb.draw(boxes[i].texture,boxes[i].getXPos(),boxes[i].getYPos(),boxes[i].getWidth(),boxes[i].getHeight());
        }
    }
    void update(){
        mouseCords();
        if (Gdx.input.justTouched()){
            addBox(mousex,mousey,menu.getWidth(),menu.getHeight(),menu.physical,menu.getTexture());
        }
    }
    void addBox(float x,float y,float width,float height,boolean physical,String texture){
        boxes[boxesPlace]=new box(x,y,width,height,physical,texture);
        boxesPlace++;
    }
    void removeBox(int i){
        for (i=i;i<boxesPlace;i++){
            boxes[i]=boxes[i+1];
        }
        boxesPlace--;
    }
    void mouseCords(){
        Vector3 cords=new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
        if (Gdx.input.justTouched()){
            mousey=cam.unproject(cords).y;
            mousex=cords.x;
        }
    }


}