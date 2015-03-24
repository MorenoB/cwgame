package gui;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class Container extends AbstractComponent {
	private final List<Component> components = new ArrayList<Component>(20);
	
	public Container() {
		super(0, 0, (float) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (float) Toolkit
				.getDefaultToolkit().getScreenSize().getHeight());
		setPriority(0);
	}
	
	public void addComponent(Component c) {
		components.add(c);
		Collections.sort(components);
	}
	
	public void addComponents(Component... c) {
		for(Component element: c) {
			components.add(element);
		}
		
		Collections.sort(components);
	}
	
	@Override
	public boolean contains(float x, float y) {
		for(Component c: components) {
			if(c.contains(x, y))
				return true;
		}
		
		return false;
	}
	
	public List<Component> getComponents() {
		return components;
	}
	
	public void internalRender(GameContainer container, StateBasedGame game, Graphics graphics) {
		for(int i = 0; i < components.size(); i++) {
			components.get(i).render(container, game, graphics);
		}
	}
	
	public void internalUpdate(GameContainer container, StateBasedGame game, int delta) {
		for(Component c: components) {
			c.update(container, game, delta);
		}
	}
}
