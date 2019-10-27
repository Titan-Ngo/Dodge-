package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList; 
import java.util.Queue;

public class IntroGUI implements Screen {
	
	// Instance vars
	private OrthographicCamera camera;
	private SpriteBatch batch;
	BitmapFont font = new BitmapFont();
	
	private Game game;
	private Stage stage;
	private TextField userNameField;
	
	// Players
	String[] userNameList = new String[4];
	
	public IntroGUI(Game game) {
		this.game = game;
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
//		TextButton button = new TextButton("Confirm", new Skin());
//		button.setPosition(300, 300);
//		button.setSize(300, 60);
		
//		stage.addActor(button);
//		button.addListener(new ClickListener() {
//		
//		});
		
		TextField userNameField = new TextField("", new Skin());
		userNameField.setMessageText("test");
		userNameField.setPosition(30, 30);
		stage.addActor(userNameField);
		String test = userNameField.getText();
		System.out.println(test);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(5, 5, 5, 5); // Background color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clears the screen
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	

}
