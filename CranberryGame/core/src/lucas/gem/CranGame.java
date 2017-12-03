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

	boolean camStopped=false;

	box[][] currentBoxes;
	static int nextBorder=0;

	@Override
	public void create () {
		cran = new Berry(540,100);
		batch = new SpriteBatch();
		cam=new OrthographicCamera(WIDTH,HEIGHT);
		cam.position.set(WIDTH/2,HEIGHT/2,0);
		cam.update();
		camStopped=false;
		level = new Level();

		currentBoxes=new box[2][100];
		addScreenLevelthing();
		cran.giveBoxConfig(currentBoxes);
	}

	void rearangeBoxes(){	}
	void reset(){
		cran=new Berry(540,100);
		nextBorder=0;
		level.nextLevel=0;
		camStopped=false;
		rearangeBoxes();
		cran.giveBoxConfig(currentBoxes);
	}
	void update() {
		if (cran.getPosition().y<0){
			reset();
		}
		if (!camStopped) {
			if (cam.position.x - (WIDTH / 2) > 1080 * (nextBorder + 1)) {
				System.out.println(cam.position.x + " " + WIDTH / 2 + " " + nextBorder);
				addScreenLevelthing();
				nextBorder++;
				cran.giveBoxConfig(currentBoxes);
			}
		}
		cran.update(3f);
	}
	public void render () {
		elapsedTime += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 1, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//TextureRegion currentFrame = animation.getKeyFrame(elapsedTime,true);


		update();
		if (!camStopped) {
			cam.position.x = cran.getPosition().x+WIDTH/4;
			cam.update();
		}
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.draw(cran.getFrame(),cran.getPosition().x,cran.getPosition().y,128,100);
		drawStage(batch);
		batch.end();
	}
	void drawStage(SpriteBatch sb){
		for (int i=0; i<2;i++){
			for (int j = 0;j<currentBoxes[i].length;j++){
				sb.draw(currentBoxes[i][j].texture,currentBoxes[i][j].getXPos()+((nextBorder+i)*1080),currentBoxes[i][j].getYPos(),currentBoxes[i][j].getWidth(),currentBoxes[i][j].getHeight());
			}
		}
	}
	void addScreenLevelthing(){
		if (CranGame.nextBorder == 0) {
			currentBoxes[0]=level.getBoxes();
			currentBoxes[1]=level.getBoxes();
		}
		else{
			try {
				currentBoxes[0] = currentBoxes[1];
				currentBoxes[1] = level.getBoxes();
			}catch(ArrayIndexOutOfBoundsException e){
				camStopped=true;
				currentBoxes[1]=new box[]{};
			}
		}
	}



	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		//img.dispose();
	}
}
