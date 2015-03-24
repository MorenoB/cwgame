package game;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class EntryPoint {
	public static void main(String[] args) throws SlickException {
		DisplayMode desktopMode = Display.getDesktopDisplayMode();
		AppGameContainer app = new AppGameContainer(ColdWarGame.get());
		app.setDisplayMode(desktopMode.getWidth(), desktopMode.getHeight(), true);
		app.setShowFPS(false);
		app.setTargetFrameRate(60);
		app.setVSync(true);
		app.start();
	}
}
