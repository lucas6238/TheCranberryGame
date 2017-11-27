package lucas.gem;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

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

    Level level;

    Menu menu;

    Texture img;    @Override
    public void create() {
        menu=new Menu();

        img=new Texture("badlogic.jpg");

        cam = new OrthographicCamera(1920, 1080);
        sr = new ShapeRenderer();
        sb=new SpriteBatch();
        cam.position.set(1920 / 2, 1080 / 2, 0);
        cam.update();
        level=new Level();

    }

    @Override
    public void render() {
        update();
        sr.setAutoShapeType(true);
        sr.setProjectionMatrix(cam.combined);
        sr.begin();
        sr.box(x,y,0,105,105,0);
        sr.end();
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        level.render(sb);
        sb.draw(img,x,y,100,100);
        sb.end();
    }
    void update(){
        mouseCords();
        if (Gdx.input.justTouched()){
            x=mousex;
            y=mousey;
            System.out.println(x+" "+y);
            level.addBox(x,y,menu.getWidth(),menu.getHeight(),menu.physical,menu.getTexture());
        }
    }
    void mouseCords(){
        Vector3 cords=new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
        mousex=cam.unproject(cords).x;
        mousey=cords.y;
    }


}