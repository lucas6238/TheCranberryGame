package lucas.gem;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class CranGame extends ApplicationAdapter {
	final static int WIDTH = 640;
	final static int HEIGHT = 400;
	OrthographicCamera cam;
	SpriteBatch batch;
	ShapeRenderer sr;
	Texture img;
	TextureRegion[] animationFrames;
	Animation<TextureRegion> animation;
	float elapsedTime;
	//private Platform plat;
	private Berry cran;
	Level level;

	
	@Override
	public void create () {
		cran = new Berry(50,100);
		//plat= new Platform(100,100);
		batch = new SpriteBatch();
		sr=new ShapeRenderer();
		cam=new OrthographicCamera(1080,1920);
		cam.position.set(1080/2,1920/2,0);
		cam.update();

		level=new Level(BoxConfig.LEVEL1);

	}

	@Override
	public void render () {
		elapsedTime += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(1, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//TextureRegion currentFrame = animation.getKeyFrame(elapsedTime,true);
		drawShapes(sr);

		cam.position.x=cran.getPosition().x-50;
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.draw(cran.getFrame(),cran.getPosition().x,cran.getPosition().y,128,128);
		batch.end();

		cran.giveBoxes(level.boxes);
		cran.update(3f);
	}
	void drawShapes(ShapeRenderer sr){
		sr.setColor(1,.5f,.25f,1);
		sr.setAutoShapeType(true);
		sr.setProjectionMatrix(cam.combined);
		sr.begin();
		sr.set(ShapeRenderer.ShapeType.Filled);
		sr.box(0,0,0,1080,1920,0);
		sr.setColor(.2f,.4f,.8f,.75f);
		sr.box(0,1900,0,1080,20,0);
		drawBoxes(sr);
		sr.end();
	}
	void drawBoxes(ShapeRenderer sr){
		int i=0;
		float[] cords=new float[4],color=new float[4];
		while (level.getBox(i,cords,color)){
			i++;

			sr.setColor(color[0],color[1],color[2],color[3]);
			sr.box(cords[0],cords[1],0,cords[2],cords[3],0);
		}
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		//img.dispose();
	}
}
