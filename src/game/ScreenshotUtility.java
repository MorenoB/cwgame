package game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageIOWriter;
import org.newdawn.slick.state.StateBasedGame;

public class ScreenshotUtility {
	
	public static void update(GameContainer gc, StateBasedGame sbg, Graphics graphics) {
		Input input = gc.getInput();
		
		try {
			if(input.isKeyDown(Input.KEY_F2)) {
				Image image = new Image(gc.getWidth(), gc.getHeight());
				graphics.copyArea(image, 0, 0);
				ImageIOWriter writer = new ImageIOWriter();
				String screenshotDirName = GameContext.defines.getField("screenshotDirectory");
				File screenshotDir = new File(screenshotDirName);
				if(!screenshotDir.exists()) {
					screenshotDir.mkdirs();
				}
				
				FileOutputStream fos = new FileOutputStream(screenshotDirName + System.currentTimeMillis()
						+ ".png");
				writer.saveImage(image, "png", fos, true);
				fos.close();
			}
		} catch(SlickException exception) {
			exception.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
