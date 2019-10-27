package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DodgeGame implements ApplicationListener {
	
	// Instance vars
	private OrthographicCamera camera;
	private SpriteBatch batch;
	// Players
	Player player1;
	Player player2;
	
	/**
	 * create sets up the game. Runs once.
	 */
	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
		batch = new SpriteBatch();
		
		player1 = new Player(1);
		player2 = new Player(2);
	}
	
	/**
	 * render runs frame by frame. 
	 */
	@Override
	public void render() {
		Gdx.gl.glClearColor(5, 5, 5, 5); // Background color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clears the screen
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		player1.draw(batch);
		player2.draw(batch);
		batch.end();
		
		// Updates
		
		// Controls
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
		batch.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}
	

}
