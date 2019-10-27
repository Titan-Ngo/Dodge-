package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class Main {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Dodge!";
		config.width = WIDTH;
		config.height = HEIGHT;
		
		new LwjglApplication(new DodgeGame(), config);
	}
}
