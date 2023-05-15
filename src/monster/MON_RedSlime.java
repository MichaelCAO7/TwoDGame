package monster;

import entity.Entity;
import main.GamePanel;
import java.util.Random;
import object.SuperObject;

public class MON_RedSlime extends Entity {

	public MON_RedSlime(GamePanel gp) {
		super(gp);
		String name;
		
		name = "Red Slime";
		speed = 1;
		maxLife = 4;
		life = maxLife;
		
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		getImage();
		
	}
	public void  getImage() {
		
		up1 = setup("/monster/redslime_down_1",48,48);
		up2 = setup("/monster/redslime_down_2",48,48); 
		down1 = setup("/monster/redslime_down_1",48,48);
		down2 = setup("/monster/redslime_down_2",48,48);
		left1 = setup("/monster/redslime_down_1",48,48);
		left2 = setup("/monster/redslime_down_2",48,48);
		right1 = setup("/monster/redslime_down_1",48,48);
		right2 = setup("/monster/redslime_down_2",48,48);
	}
	public void setAction() {
		
		Random random = new Random();
		int i = random.nextInt(100)+1;
		
		if (i <= 25) {
			direction = "up";
		}
		if (i > 25 && i <= 50) {
			direction = "down";
		}
		if (i > 50 && i <= 75) {
			direction = "left";
		}
		if (i > 75 && i <= 100) {
			direction = "right";
		}
	}
	

}
