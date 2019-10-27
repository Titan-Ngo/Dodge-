package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;


import java.util.List;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
//import java.awt.PageAttributes.MediaType;
import java.util.ArrayList;
import java.util.LinkedList; 
import java.util.Queue;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DodgeGame implements ApplicationListener {
	
	// Constants
	//private long BULLET_FREQ = (long) 0.85;
	private double BULLET_FREQ = (double) 0.7;
	
	public int numOfPlayers = 1;
	
	// Instance vars
	private OrthographicCamera camera;
	private SpriteBatch batch;
	BitmapFont font;
	
	// Players
	List<Player> playerList = new ArrayList<Player>();
	Player me;
	
	// Team lives
	//Heart[] team1Hearts = new Heart[3];
	//Heart[] team2Hearts = new Heart[3];	
	// Bullet handling
	Queue<Bullet> bulletList;
	float timeElapsed;
	// Booleans for game state
	boolean isPaused = false;
	boolean gameOver = false;
	
	
	/**
	 * create sets up the game. Runs once.
	 */
	@Override
	public void create() {
//		Stage stage = new Stage();
//		
//		TextField userNameField = new TextField("", new Skin());
//		userNameField.setMessageText("test");
//		userNameField.setPosition(30, 30);
//		stage.addActor(userNameField);
//		String test = userNameField.getText();
//		System.out.println(test);
		
//		TextField usernameTextField = new TextField("", AssetLoader.defaultSkin);
//		usernameTextField.setPosition(24,73);
//		usernameTextField.setSize(88, 14);
//
//		stage.add(usernameTextField);            // <-- Actor now on stage 
//		Gdx.input.setInputProcessor(stage);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(2, 2);
		font.setColor(Color.BLACK);
		
		int count = 1;
		while( count <= numOfPlayers ) { //TODO: add join

			try {				

				String request        = "http://dodge-env.abzxkrzv5v.us-east-2.elasticbeanstalk.com/users/join";
				URL    url            = new URL( request );
				HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
				conn.setDoOutput( true );
				conn.setInstanceFollowRedirects( false );
				conn.setRequestMethod( "POST" );
				conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
				conn.setRequestProperty( "charset", "utf-8");
				String jsonInputString = "{\"name\": \"wangangela2000\"}";
				try(OutputStream os = conn.getOutputStream()) {
				    byte[] input = jsonInputString.getBytes("utf-8");
				    os.write(input, 0, input.length);           
				}
				
				String result = "";
				try(BufferedReader br = new BufferedReader(
						  new InputStreamReader(conn.getInputStream(), "utf-8"))) {
						    StringBuilder response = new StringBuilder();
						    String responseLine = null;
						    while ((responseLine = br.readLine()) != null) {
						        response.append(responseLine.trim());
						    }
						    result += response.toString();
						}
		            JSONParser jsonParser = new JSONParser();
		            

	                JSONObject obj = ((JSONObject)jsonParser.parse(result));
	                long team_id = (long)((JSONObject)((JSONObject)(obj.get("id"))).get("status")).get("team_id");
	                long hearts = (long)((JSONObject)((JSONObject)(obj.get("id"))).get("status")).get("active");
	                String player_id = (String)((JSONObject)((JSONObject)(obj.get("id"))).get("user_id")).get("player_id");
	                String player_name = (String)((JSONObject)((JSONObject)(obj.get("id"))).get("user_id")).get("player_name");
	                long positionX = (long)((JSONObject)(obj.get("position"))).get("x");
	                long positionY = (long)((JSONObject)(obj.get("position"))).get("y");
	     
	    			me = (new Player((int)team_id, player_id, player_name, (int)(hearts)));
	    			playerList.add(me);
	    			count++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		bulletList = new LinkedList<Bullet>();
		timeElapsed = 0;
		
	}
	int x = 0;
	/**
	 * render runs frame by frame. 
	 */
	@Override
	public void render() {
		if(x <= 0) {
		try {				

			String request        = "http://dodge-env.abzxkrzv5v.us-east-2.elasticbeanstalk.com/users/update";
			URL    url            = new URL( request );
			HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
			conn.setDoOutput( true );
			conn.setInstanceFollowRedirects( false );
			conn.setRequestMethod( "POST" );
			conn.setRequestProperty( "Content-Type", "application/json"); 
			conn.setRequestProperty( "charset", "utf-8");
			String jsonInputString = "{\"id\":{\"status\":{\"team_id\":" + me.teamNum +",\"active\":" + me.numLives + "},\"user_id\":"
					+ "{\"player_id\":\"" + me.player_id + "\",\"player_name\":\"" + me.userName + "\"}},\"position\":"
					+ "{\"x\":" + me.x + ",\"y\":" + me.y + "}}\r\n";
			try(OutputStream os = conn.getOutputStream()) {
			    byte[] input = jsonInputString.getBytes("utf-8");
			    os.write(input, 0, input.length);           
			}
			
//			String result = "";
//			try(BufferedReader br = new BufferedReader(
//					  new InputStreamReader(conn.getInputStream(), "utf-8"))) {
//					    StringBuilder response = new StringBuilder();
//					    String responseLine = null;
//					    while ((responseLine = br.readLine()) != null) {
//					        response.append(responseLine.trim());
//					    }
//					    result += response.toString();
//					}
//	            JSONParser jsonParser = new JSONParser();
//	            
//
//                JSONObject obj = ((JSONObject)jsonParser.parse(result));
//                long team_id = (long)((JSONObject)((JSONObject)(obj.get("id"))).get("status")).get("team_id");
//                long hearts = (long)((JSONObject)((JSONObject)(obj.get("id"))).get("status")).get("active");
//                String player_id = (String)((JSONObject)((JSONObject)(obj.get("id"))).get("user_id")).get("player_id");
//                String player_name = (String)((JSONObject)((JSONObject)(obj.get("id"))).get("user_id")).get("player_name");
//                float positionX = Float.parseFloat(((JSONObject)(obj.get("position"))).get("x").toString());
//                float positionY = Float.parseFloat(((JSONObject)(obj.get("position"))).get("y").toString());
//     
//    			playerList.add(new Player((int)team_id, player_id, player_name, (int)(hearts)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {				

			String request        = "http://dodge-env.abzxkrzv5v.us-east-2.elasticbeanstalk.com/users/state";
			URL    url            = new URL( request );
			HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
			conn.setDoOutput( true );
			conn.setInstanceFollowRedirects( false );
			conn.setRequestMethod( "POST" );
			conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
			conn.setRequestProperty( "charset", "utf-8");
			String jsonInputString = "{}";
			try(OutputStream os = conn.getOutputStream()) {
			    byte[] input = jsonInputString.getBytes("utf-8");
			    os.write(input, 0, input.length);           
			}
			
			String result = "";
			try(BufferedReader br = new BufferedReader(
					  new InputStreamReader(conn.getInputStream(), "utf-8"))) {
					    StringBuilder response = new StringBuilder();
					    String responseLine = null;
					    while ((responseLine = br.readLine()) != null) {
					        response.append(responseLine.trim());
					    }
					    result += response.toString();
					}
			
	            JSONParser jsonParser = new JSONParser();
	            
	        //	List<Player> playerList = new ArrayList<Player>();
                JSONArray array = ((JSONArray)jsonParser.parse(result));
                for(int i = 0; i < array.size(); i++) {
                	JSONObject obj = (JSONObject) array.get(i);
                    long team_id = (long)((JSONObject)((JSONObject)(obj.get("id"))).get("status")).get("team_id");
                    long hearts = (long)((JSONObject)((JSONObject)(obj.get("id"))).get("status")).get("active");
                    String player_id = (String)((JSONObject)((JSONObject)(obj.get("id"))).get("user_id")).get("player_id");
                    String player_name = (String)((JSONObject)((JSONObject)(obj.get("id"))).get("user_id")).get("player_name");
                    float positionX = Float.parseFloat(((JSONObject)(obj.get("position"))).get("x").toString());
                    float positionY = Float.parseFloat(((JSONObject)(obj.get("position"))).get("y").toString());
                    System.out.println("1 "+Integer.toString(playerList.size()));


                    if(!player_id.equals(me.player_id)) {
                    for(int j = 0; j < playerList.size(); j++) {
                    	Player player = playerList.get(j);
                    
                    	if(player_id.equals(player.player_id)) {
                    		player.numLives = (int)hearts;
                    		player.x = positionX;
                    		player.y = positionY;
                    		break;
                    	}
                    	else if(j == playerList.size() - 1) {
                			playerList.add(new Player((int)team_id, player_id, player_name, (int)(hearts), positionX, positionY));
                    	}
                    }

                    }
                    System.out.println("2 "+Integer.toString(playerList.size()));

                    	
                }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		x = 10;
		}
		x--;
		
		
		Gdx.gl.glClearColor(5, 5, 5, 5); // Background color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clears the screen
		
		if (Gdx.input.isKeyPressed(Input.Keys.R) && gameOver) { restart(); return; }
		if (gameOver) { 
			batch.begin();
			Texture goTexture = new Texture(Gdx.files.internal("sprites/Game_Over.png"));
			Sprite goSprite = new Sprite(goTexture, 0, 0, 320, 320);
			goSprite.setPosition(Main.WIDTH/2 - 160, Main.HEIGHT/2 - 100);
			goSprite.draw(batch);
			batch.end();
			return; 
		}
		
		batch.setProjectionMatrix(camera.combined);
		
		// DRAWING START ------------------------------
		batch.begin();
		for (Player player : playerList) {
			player.draw(batch, font);
		}
		//player2.draw(batch);
		this.updateBullets(Gdx.graphics.getDeltaTime());

		batch.end();
		// DRAWING END --------------------------------
		
		// Updates
		this.timeElapsed += Gdx.graphics.getDeltaTime();
		
		if (timeElapsed >= BULLET_FREQ) {
			
			// Make new bullets for each player
			for( Player player: playerList ) {
				bulletList.add(new Bullet(player.teamNum, player.x, player.y));
			}
			
			timeElapsed = 0;
		}
		

		// Player1 - W (up)/S (down)
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			me.moveUp(Gdx.graphics.getDeltaTime());
			
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			me.moveDown(Gdx.graphics.getDeltaTime());
		}
//		
//		// Player2 - up arrow/down arrow
//		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//			playerList.get(1).moveUp(Gdx.graphics.getDeltaTime());
//		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//			playerList.get(1).moveDown(Gdx.graphics.getDeltaTime());
//		}					
		
		// Get rid of dead players
		for(int i = 0; i < playerList.size(); i++ ) {
			if( !playerList.get(i).isAlive() ) {
				playerList.remove(i);
			}
		}
		// End game if all players are dead
		if (!me.isAlive()) {
			end();
		}

	}
	
	@Override
	public void pause() {}
	@Override
	public void resume() {}
	@Override
	public void resize(int width, int height) {}
	
	public void restart() {
		this.gameOver = false;
		this.dispose();
		this.create();
	}
	
	public void end() {
		this.gameOver = true;
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
	
	public void updateBullets(float delta) {
		for (Bullet currBullet : this.bulletList) {
			
			if (currBullet.hasHit) {continue;}
			
			currBullet.moveForward(delta);
			currBullet.draw(batch);
			
			// Check if bullets collide with player
			int toggle = 0;
			for( Player player: playerList ) {
				player.checkCollision(currBullet);
			}
		
		}
	}
	

}
