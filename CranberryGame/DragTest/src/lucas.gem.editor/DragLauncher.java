package lucas.gem.editor;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTFrame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import lucas.gem.CranGame;
import lucas.gem.Dragon;
import lucas.gem.Editor;
import lucas.gem.levelEditor;



public class DragLauncher{
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=500;
		config.height=config.width*16/9;
		LwjglApplication app=new LwjglApplication(new Dragon(),config);
	}
}
