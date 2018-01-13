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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
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
    ShapeRenderer sr;
    SpriteBatch sb;
    OrthographicCamera cam;
    int currentObject=-1;

    Vector3 mouse = new Vector3();
    Vector3 lastMouse = new Vector3(mouse.x,mouse.y,0);
    Vector3 adjustment =  new Vector3(0,2,0);
    private box[] boxes;
    private int boxesPlace;
    Menu menu;
    DragListener dl;
    String fileName="";
    boolean shouldLoad=false;
    float xdif,ydif;

    Texture img;    @Override
    public void create() {
        menu=new Menu(this);
        boxes=new box[1000];
        boxesPlace=0;
        dl = new DragListener();
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
        mouseCords();
        if (currentObject>-1){
            boxes[currentObject].setBounds(menu.getBounds());
        }
        handleInput();
    }
    void handleInput(){

        keyCheck();

        if (Gdx.input.justTouched()) {
            if (checkForSelection()) {
                setSelected();
                xdif=mouse.x-boxes[currentObject].getXPos();
                ydif=mouse.y-boxes[currentObject].getYPos();

            } else {
                menu.resetValues();
                addBox(mouse.x, mouse.y, menu.getWidth(), menu.getHeight(), menu.physical, menu.getTexture());
            }
        }



    }


    void setSelected(){
        Menu.setX(boxes[currentObject].getXPos());
        Menu.setY(boxes[currentObject].getYPos());
        Menu.setWidth(boxes[currentObject].getWidth());
        Menu.setHeight(boxes[currentObject].getHeight());
    }
    void keyCheck(){
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            menu.setX(boxes[currentObject].getXPos()-1);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            menu.setX(boxes[currentObject].getXPos()+1);
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            menu.setY(boxes[currentObject].getYPos()+1);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            menu.setY(boxes[currentObject].getYPos()-1);
        if (Gdx.input.isKeyPressed(112)&&currentObject !=-1)
            removeBox(currentObject);
    }
    boolean checkForSelection(){
        for (int i=0;i<boxesPlace;i++){
            if (boxes[i].checkCollision(mouse.x,mouse.y)){
                currentObject=i;
                return true;
            }
        }
        return false;
    }
    void addBox(float x,float y,float width,float height,boolean physical,String texture){
        float tempx=x,tempy=y;
        for (int i=0;i<boxesPlace;i++){
            box b = boxes[i];
            if (checkForCloseToPoint(b.getXPos(),b.getYPos())){
                tempx=b.getXPos()-width;
                tempy=b.getYPos();
            }else if ( checkForCloseToPoint(b.getXPos(),b.getYandHeight())){
                tempx=b.getXPos()-width;
                tempy=b.getYandHeight();
            }else if (checkForCloseToPoint(b.getXandWidth(),b.getYPos())){
                tempx=b.getXandWidth();
                tempy=b.getYPos();
            }else if (checkForCloseToPoint(b.getXandWidth(),b.getYandHeight())){
                tempx=b.getXandWidth();
                tempy=b.getYandHeight();
            }
        }
//        System.out.println("placing at"+tempx+" "+tempy);
        Menu.setX(tempx);
        Menu.setY(tempy);
        boxes[boxesPlace]=new box(x,y,width,height,physical,texture);
        currentObject=boxesPlace;
        boxesPlace++;
    }
    boolean checkForCloseToPoint(float x,float y){
//        System.out.println("Check for" + x+" "+y);
        if (Math.abs(mouse.x-x)<20&&Math.abs(mouse.y-y)<20){
            return true;
        }
        return false;
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
                addBox(Float.valueOf(fields[0]),Float.valueOf(fields[1]),Float.valueOf(fields[2]),Float.valueOf(fields[3]),Boolean.getBoolean(fields[4]),fields[5]);
            }
        }catch(IOException io){
            System.out.println(io);
        }
    }
    void mouseCords(){
        if (Gdx.input.justTouched()){
            Vector3 cords=new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            cam.unproject(cords);
            mouse.x=cords.x;
            mouse.y =cords.y;
        }
    }
}
