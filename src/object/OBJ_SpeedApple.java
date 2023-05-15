package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_SpeedApple extends SuperObject {
	
	GamePanel gp;
	
	public OBJ_SpeedApple(GamePanel gp) {
		
		this.gp = gp;
		
		name = "SpeedApple";
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream("/objects/speedapple.png"));
			uTool.scaleImage(image, gp.tileSize, gp.tileSize);

		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}