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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Chandler on 11/16/2017.
 */

public class Editor extends ApplicationAdapter {
    static Color color = new Color(.1f, .5f, 0f, 1);
    ShapeRenderer sr;
    SpriteBatch sb;
    OrthographicCamera cam;
    float x,y;
    int currentObject=-1;
    float mousex,mousey;

    private box[] boxes;
    private int boxesPlace;
    Menu menu;
    String fileName="";
    boolean shouldLoad=false;

    Texture img;    @Override
    public void create() {
        menu=new Menu(this);
        boxes=new box[1000];
        boxesPlace=0;
        cam = new OrthographicCamera(1080, 1920);
        sr = new ShapeRenderer();
        sb=new SpriteBatch();
        cam.position.set(1080 / 2, 1920 / 2, 0);

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

        renderLevel();
        renderSelection();
    }
    void renderLevel(){
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        for (int i=0;i<boxesPlace;i++){
            sb.draw(boxes[i].texture,boxes[i].getXPos(),boxes[i].getYPos(),boxes[i].getWidth(),boxes[i].getHeight());
        }
        sb.end();
    }
    void renderSelection(){
        if (currentObject>-1){
            box b=boxes[currentObject];
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.setProjectionMatrix(cam.combined);
            sr.setColor(1,0,0,1);
            sr.box(b.getXPos(),b.getYPos(),0,b.getWidth(),b.getHeight(),0);
            sr.end();
        }
    }
    void setShouldLoad(String s){
        fileName=s;
        shouldLoad=true;
    }
    void update(){
        if (shouldLoad){
            shouldLoad=false;
            load(fileName);
        }
        for (int i=0;i<100;i++){
            if (Gdx.input.isKeyPressed(i)){
                System.out.println(i);
            }
        }
        mouseCords();
        if (currentObject>-1){
            boxes[currentObject].bounds[2]=menu.getWidth();
            boxes[currentObject].bounds[3]=menu.getHeight();
            boxes[currentObject].bounds[0]=menu.getX();
            boxes[currentObject].bounds[1]=menu.getY();
        }
        handleInput();
    }
    void handleInput(){
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            menu.setX(boxes[currentObject].getXPos()-1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            menu.setX(boxes[currentObject].getXPos()+1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            menu.setY(boxes[currentObject].getYPos()+1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            menu.setY(boxes[currentObject].getYPos()-1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DEL)){
            if (currentObject !=-1) {
                removeBox(currentObject);
            }
        }
        if (Gdx.input.justTouched()) {
            if (checkForSelection()) {
                Menu.setX(boxes[currentObject].getXPos());
                Menu.setY(boxes[currentObject].getYPos());
                Menu.setWidth(boxes[currentObject].getWidth());
                Menu.setHeight(boxes[currentObject].getHeight());
            } else {
                menu.resetValues();
                addBox(mousex, mousey, menu.getWidth(), menu.getHeight(), menu.physical, menu.getTexture());
                Menu.setX(mousex);
                Menu.setY(mousey);
            }
        }
    }
    boolean checkForSelection(){
        for (int i=0;i<boxesPlace;i++){
            if (mousex>boxes[i].getXPos()&&mousex<boxes[i].getXandWidth()&&mousey>boxes[i].getYPos()&&mousey<boxes[i].getYandHeight()){
                currentObject=i;
                return true;
            }
        }
        return false;
    }
    void addBox(float x,float y,float width,float height,boolean physical,String texture){
        boxes[boxesPlace]=new box(x,y,width,height,physical,texture);
        currentObject=boxesPlace;
        boxesPlace++;
    }
    void removeBox(int i){
        currentObject=-1;
        for (i=i;i<boxesPlace;i++){
            boxes[i]=boxes[i+1];
        }
        boxesPlace--;
    }
    void save(String fileName){
        try{
            FileWriter fw=new FileWriter(fileName+".level");
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i=0;i<boxesPlace;i++){
                bw.write(boxes[i].getLine());
            }
            bw.close();
        }catch(IOException ioe){
        }
    }
    void load(String fileName){
        System.out.println(fileName);
        try{
            FileReader fr=new FileReader(fileName);
            BufferedReader br=new BufferedReader(fr);
            String[] fields;
            String line;
            boxes=new box[1000];
            currentObject=-1;
            boxesPlace=0;
            while ((line=br.readLine())!=null){
                System.out.println(line);
                fields=line.split(",");
                addBox(Float.valueOf(fields[0]),Float.valueOf(fields[1]),Float.valueOf(fields[2]),Float.valueOf(fields[3]),Boolean.getBoolean(fields[4]),"box4a.png");
            }
        }catch(IOException io){
            System.out.println(io);
        }
    }
    void mouseCords(){
        Vector3 cords=new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
        if (Gdx.input.justTouched()){
            mousey=cam.unproject(cords).y;
            mousex=cords.x;
        }
    }
}