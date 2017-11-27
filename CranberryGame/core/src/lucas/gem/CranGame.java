package lucas.gem;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
	final static int WIDTH = 1080;
	final static int HEIGHT = (int)(WIDTH*(16f/9f));
	OrthographicCamera cam;
	SpriteBatch batch;
	ShapeRenderer sr;
	Texture img;
	TextureRegion[] animationFrames;
	Animation<TextureRegion> animation;
	float elapsedTime;
	private Berry cran;
	Level level;

	box[][] currentBoxes;
	static int nextBorder=0;

	@Override
	public void create () {
		cran = new Berry(30,100);
		//plat= new Platform(100,100);
		batch = new SpriteBatch();
		sr=new ShapeRenderer();
		cam=new OrthographicCamera(WIDTH,HEIGHT);
		cam.position.set(WIDTH/2,HEIGHT/2,0);
		cam.update();
		level=new Level();
		rearangeBoxes();
		cran.giveBoxConfig(currentBoxes);
	}

	void rearangeBoxes(){	}
	void reset(){
		cran=new Berry(30,100);
		nextBorder=0;
		rearangeBoxes();
		cran.giveBoxConfig(currentBoxes);
	}
	void update() {
		if (cran.getPosition().y<0){
			reset();
		}
		cran.update(3f);
	}
	public void render () {
		update();
		elapsedTime += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//TextureRegion currentFrame = animation.getKeyFrame(elapsedTime,true);
		drawShapes(sr);

		cam.position.x=cran.getPosition().x-5+WIDTH/2;
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.draw(cran.getFrame(),cran.getPosition().x,cran.getPosition().y,128,100);
		batch.end();

	}
	void drawShapes(ShapeRenderer sr){
		sr.setColor(1,.5f,.25f,1);
		sr.setAutoShapeType(true);
		sr.setProjectionMatrix(cam.combined);
		sr.begin();
		sr.set(ShapeRenderer.ShapeType.Filled);
		sr.box(0,0,0,1080,1920,0);
		sr.setColor(.2f,.4f,.8f,.75f);
		sr.box(1080,0,0,1080,1920,0);
		drawBoxes(sr);
		sr.end();
	}
	void drawBoxes(ShapeRenderer sr){
		for (int i = 0; i < currentBoxes[0].length; i++) {
			sr.setColor(Color.RED);

			sr.box(currentBoxes[0][i].getXPos()+(nextBorder-1)*1080, currentBoxes[0][i].getYPos(), 0, currentBoxes[0][i].getWidth(), currentBoxes[0][i].getHeight(), 0);
		}
		for (int i=0;i<currentBoxes[1].length;i++){
			sr.setColor(Color.BLUE);
			sr.box(currentBoxes[1][i].getXPos()+nextBorder*1080, currentBoxes[1][i].getYPos(), 0, currentBoxes[1][i].getWidth(), currentBoxes[1][i].getHeight(), 0);
		}
	}



	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		//img.dispose();
	}
}
