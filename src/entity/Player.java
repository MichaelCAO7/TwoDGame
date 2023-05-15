package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	public int hasKey = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		solidArea = new Rectangle(10, 14, 28, 28);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		armorImage();
		armorAttackImage();
		
		
		
	}
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
		
		maxLife = 6;
		life = maxLife;
	}
	
	public void getPlayerImage() {

		up1 = setup("/player/boy_up_1",gp.tileSize,gp.tileSize);
		up2 = setup("/player/boy_up_2",gp.tileSize,gp.tileSize);
		down1 = setup("/player/boy_down_1",gp.tileSize,gp.tileSize);	
		down2 = setup("/player/boy_down_2",gp.tileSize,gp.tileSize);	
		left1 = setup("/player/boy_left_1",gp.tileSize,gp.tileSize);	
		left2 = setup("/player/boy_left_2",gp.tileSize,gp.tileSize);	
		right1 = setup("/player/boy_right_1",gp.tileSize,gp.tileSize);	
		right2 = setup("/player/boy_right_2",gp.tileSize,gp.tileSize);	
	}
	public void getPlayerAttackImage() {
		
			attackUp1 = setup("/player/boy_attack_up_1",gp.tileSize,gp.tileSize*2);
			attackUp2 = setup("/player/boy_attack_up_2",gp.tileSize,gp.tileSize*2);
			attackDown1 = setup("/player/boy_attack_down_1",gp.tileSize,gp.tileSize*2);
			attackDown2 = setup("/player/boy_attack_down_2",gp.tileSize,gp.tileSize*2);
			attackLeft1 = setup("/player/boy_attack_left_1",gp.tileSize*2,gp.tileSize);
			attackLeft2 = setup("/player/boy_attack_left_2",gp.tileSize*2,gp.tileSize);
			attackRight1 = setup("/player/boy_attack_right_1",gp.tileSize*2,gp.tileSize);
			attackRight2 = setup("/player/boy_attack_right_2",gp.tileSize*2,gp.tileSize);
	}
	
	public void armorImage() {
	
			armorUp1 = setup("/player/armor_up_1",gp.tileSize,gp.tileSize);
			armorUp2 = setup("/player/armor_up_2",gp.tileSize,gp.tileSize);
			armorDown1 = setup("/player/armor_down_1",gp.tileSize,gp.tileSize);
			armorDown2 = setup("/player/armor_down_2",gp.tileSize,gp.tileSize);
			armorLeft1 = setup("/player/armor_left_1",gp.tileSize,gp.tileSize);
			armorLeft2 = setup("/player/armor_left_2",gp.tileSize,gp.tileSize);
			armorRight1 = setup("/player/armor_right_1",gp.tileSize,gp.tileSize);
			armorRight2 = setup("/player/armor_right_2",gp.tileSize,gp.tileSize);
			
	}
	
	public void armorAttackImage() {
	
			armorAttackUp1 = setup("/player/armor_attack_up_1",gp.tileSize,gp.tileSize*2);
			armorAttackUp2 = setup("/player/armor_attack_up_2",gp.tileSize,gp.tileSize*2);
			armorAttackDown1 = setup("/player/armor_attack_down_1",gp.tileSize,gp.tileSize*2);
			armorAttackDown2 = setup("/player/armor_attack_down_2",gp.tileSize,gp.tileSize*2);
			armorAttackLeft1 = setup("/player/armor_attack_left_1",gp.tileSize*2,gp.tileSize);
			armorAttackLeft2 = setup("/player/armor_attack_left_2",gp.tileSize*2,gp.tileSize);
			armorAttackRight1 = setup("/player/armor_attack_right_1",gp.tileSize*2,gp.tileSize);
			armorAttackRight2 = setup("/player/armor_attack_right_2",gp.tileSize*2,gp.tileSize);
	}
	
	public void update() {
		
		if(attacking == true) {
			attacking();
		}
		
		else if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true || keyH.armorPressed == true) {
			
			if(keyH.upPressed == true) {
				direction = "up";
				
			}
			else if(keyH.downPressed == true) {
				direction = "down";
				
			}
			else if(keyH.leftPressed == true) {
				direction = "left";
				
			}
			else if(keyH.rightPressed == true) {
				direction = "right";
		
			}
			
			else if (keyH.enterPressed == true) {
				attacking = true;
			}
			else if (keyH.armorPressed == true) {
				armor = true;
			}
			//CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			//CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			
			// IF COLLISION IS FALSE, PLAYER CAN MOVE
			if(collisionOn == false && keyH.enterPressed == false) {
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break; 
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}	
			}
			
			spriteCounter++;
			if(spriteCounter > 8) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
	}
	public void attacking() {
		
		spriteCounter++;
		
		if(spriteCounter <= 5) {
			spriteNum = 1;
		}
		if(spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;
		}
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	
	public void pickUpObject(int i) {
		if(i != 999) {
			String objectName = gp.obj[i].name;
			
			switch(objectName) {
			case "Key":
				gp.playSE(1);
				hasKey++;
				gp.obj[i] = null;
				gp.ui.showMessage("You got a key!");
				break;
			case "Door":
				if(hasKey > 0) {
					gp.playSE(3);
					gp.obj[i] = null;
					hasKey--;
				}
				else {
					gp.ui.showMessage("You need a key!");
				}
				break;
			case "Boots":
				gp.playSE(2);
				speed += 2;
				gp.obj[i] = null;
				gp.ui.showMessage("Speed Upgrade!");
				break;
			case "Chest":
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSE(4);
				break;
			case "SpeedApple":
				gp.playSE(2);
				speed += 10;
				gp.obj[i] = null;
				gp.ui.showMessage("superSpeed!");
				break;
				
			}
			
			
			
		}
	}
	
	
	public void draw(Graphics2D g2) {
		
//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);		Now we are going to use our sprite image
		
		BufferedImage image = null;

		switch(direction) {
			case "up":
				if (attacking == false && armor == true ) {
					if(spriteNum == 1) {image = armorUp1;}
					if(spriteNum == 2) {image = armorUp2;}	
				}
				else if (attacking == false) {
					if(spriteNum == 1) {image = up1;}
					if(spriteNum == 2) {image = up2;}
				}
				else if (attacking == true) {
					if(spriteNum == 1) {image = attackUp1;}
					if(spriteNum == 2) {image = attackUp2;}
				}
				if (attacking == true && armor == true ) {
					if(spriteNum == 1) {image = armorAttackUp1;}
					if(spriteNum == 2) {image = armorAttackUp2;}	
				}
				break;	
			case "down":
				if (attacking == false && armor == true ) {
					if(spriteNum == 1) {image = armorDown1;}
					if(spriteNum == 2) {image = armorDown2;}	
				}
				else if (attacking == false) {
					if(spriteNum == 1) {image = down1;}
					if(spriteNum == 2) {image = down2;}
				}
				else if (attacking == true) {
					if(spriteNum == 1) {image = attackDown1;}
					if(spriteNum == 2) {image = attackDown2;}
				}
				if (attacking == true && armor == true) {
					if(spriteNum == 1) {image = armorAttackDown1;}
					if(spriteNum == 2) {image = armorAttackDown2;}
				}
				break;
			case "left":
				if (attacking == false && armor == true ) {
					if(spriteNum == 1) {image = armorLeft1;}
					if(spriteNum == 2) {image = armorLeft2;}	
				}
				else if (attacking == false) {
					if(spriteNum == 1) {image = left1;}
					if(spriteNum == 2) {image = left2;}
				}
				else if (attacking == true) {
					if(spriteNum == 1) {image = attackLeft1;}
					if(spriteNum == 2) {image = attackLeft2;}
				}
				if (attacking == true && armor == true ) {
					if(spriteNum == 1) {image = armorAttackLeft1;}
					if(spriteNum == 2) {image = armorAttackLeft2;}	
				}
				break;
			case "right":
				if (attacking == false && armor == true ) {
					if(spriteNum == 1) {image = armorRight1;}
					if(spriteNum == 2) {image = armorRight2;}	
				}
				else if (attacking == false) {
					if(spriteNum == 1) {image = right1;}
					if(spriteNum == 2) {image = right2;}
				}
				else if (attacking == true) {
					if(spriteNum == 1) {image = attackRight1;}
					if(spriteNum == 2) {image = attackRight2;}
				}
				if (attacking == true && armor == true ) {
					if(spriteNum == 1) {image = armorAttackRight1;}
					if(spriteNum == 2) {image = armorAttackRight2;}	
				}
				break;
		}
		g2.drawImage(image,  screenX,  screenY, null);
		
		
	}
	
	

}