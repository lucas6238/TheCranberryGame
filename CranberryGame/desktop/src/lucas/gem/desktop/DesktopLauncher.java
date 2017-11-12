package lucas.gem.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import lucas.gem.CranGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=1080/3;
		config.height=1920/3;
		config.title="Crabeiry Game";
		new LwjglApplication(new CranGame(), config);
	}
}
