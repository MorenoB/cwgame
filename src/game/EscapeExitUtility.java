package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class EscapeExitUtility {
	public static void update(GameContainer gc) {
		Input input = gc.getInput();
		if(input.isKeyDown(Input.KEY_ESCAPE)) {
			gc.exit();
		}
	}
}
