package lucas.gem;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CranGame extends ApplicationAdapter {
	final static int WIDTH = 640;
	final static int HEIGHT = 400;

	SpriteBatch batch;
	Texture img;
	TextureRegion[] animationFrames;
	Animation<TextureRegion> animation;
	float elapsedTime;
	//private Platform plat;
	private Berry cran;
	
	@Override
	public void create () {
		cran = new Berry(50,50);
		//plat= new Platform(100,100);
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		elapsedTime += Gdx.graphics.getDeltaTime();
		//TextureRegion currentFrame = animation.getKeyFrame(elapsedTime,true);


		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//draw cranberry
		batch.draw(cran.getFrame(),cran.getPosition().x,cran.getPosition().y,128,128);
		//batch.draw(plat.getPosition().x,plat.getPosition().y,128,128);

		batch.end();
		cran.update(3f);
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		//img.dispose();
	}
}
