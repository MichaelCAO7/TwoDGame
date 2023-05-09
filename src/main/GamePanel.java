package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	//SCREEN SETTINGS
	public final int originalTileSize = 16;	//16x16 tile default size of player character, NPC's, etc.
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; //48x48 tile 
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; //768 pixels
	public final int screenHeight = tileSize * maxScreenRow; //576 pixels
	
	//WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
//	public final int worldWidth = tileSize * maxWorldCol;			didn't end up needing these
//	public final int worldHeight = tileSize * maxWorldRow;
	
	
	//FPS
	int FPS = 60;
	
	// SYSTEM
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	
	Thread gameThread;
	
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10];
	
	//Set players default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	//	GameStates
	int playState = 1;
	int pauseState = 0;
	int gameState = playState;
	
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
		
	}
	
	public void setupGame() {
		
		aSetter.setObject();
		playMusic(0);
		stopMusic();
		
		
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	


	@Override
	public void run() {
		
		double drawInterval = 1000000000 / FPS;		//0.01666 seconds
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while(gameThread != null) {
			
//			long currentTime = System.nanoTime();
//			System.out.println("currentlyTime: "+ currentTime);

			// 1. UPDATE:	Update information such as character position
			if(gameState == playState) {
				update();
			}
			
			// 2. DRAW:		Draw the screen with the updated information
			repaint();
			
			
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime / 1000000;	//We need time in milliseconds instead of nanoseconds for the sleep method
				
				if(remainingTime < 0) {		//this statement saves up if there is lag and the draw time exceeds our 60 FPS speed
					remainingTime = 0;
				}
				
				Thread.sleep((long)remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
			
	}
	
	public void update() {
		
/*		if(keyH.upPressed == true) {
			playerY -= playerSpeed;
		}
		else if(keyH.downPressed == true) {
			playerY += playerSpeed;
		}
		else if(keyH.leftPressed == true) {
			playerX -= playerSpeed;
		}
		else if(keyH.rightPressed == true) {
			playerX += playerSpeed;
		}	
													Moved to Player Class
*/													
		player.update();
		
	}

	
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		//TILE
		tileM.draw(g2);
		
//		g2.setColor(Color.white);
//		g2.fillRect(playerX, playerY, tileSize, tileSize);	Moved to Player Class
		
		//OBJECT
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		
		
		
		//PLAYER
		player.draw(g2);
		
		//UI
		ui.draw(g2);
		
		if(gameState == pauseState) {
			drawPauseScreen(g2);
		}
		
		
		
		g2.dispose();
	}
	
	public void drawPauseScreen(Graphics2D g2) {
		int x = tileSize * 2;
		int y = tileSize / 2;
		int width = screenWidth - (tileSize * 4);
		int height = tileSize * 10;
		
		drawSubWindow(g2, x, y / 2, width, height);
		
		//	Drawing things
		g2.setColor(Color.white);
		g2.drawString("PAUSED", (screenWidth - g2.getFontMetrics().stringWidth("PAUSED")) / 2,
				(screenHeight  - tileSize)/2);
	}
	
	public void drawSubWindow(Graphics2D g2, int x, int y, int width, int height) {
		Color c = new Color(0, 0, 0, 200);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
	}

	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
		
	}
	public void stopMusic() {
	
		music.stop();
		
	}
	public void playSE(int i) {
		
		se.setFile(i);
		se.play();
		
	}
	
	
	
	
}