package game;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import util.OutputRedirector;

public class EntryPoint {
	public static void main(String[] args) throws SlickException {
		if(!GameContext.DEBUG) OutputRedirector.redirectOutput("log", "system.out.txt");
		DisplayMode desktopMode = Display.getDesktopDisplayMode();
		AppGameContainer app = new AppGameContainer(ColdWarGame.get());
		app.setDisplayMode(desktopMode.getWidth(), desktopMode.getHeight(), true);
		app.setShowFPS(false);
		app.setTargetFrameRate(60);
		app.setVSync(true);
		app.start();
	}
}
