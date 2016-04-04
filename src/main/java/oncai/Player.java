package oncai;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Player {
	
	private int x,
				y,
				tileX,
				tileY;
	
	private Image player;
	
	public Player(){
		
		ImageIcon img = new ImageIcon("src//main//resources//images//dog.png");
		player = img.getImage();
		
		
		x = 32;
		y = 32;
		
		tileX = 1;
		tileY = 1;
	}
	
	public void move(int dx, int dy, int tx, int ty){
		x += dx;
		y += dy;
		
		tileX += tx;
		tileY += ty;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getTileX(){
		return tileX;
	}
	
	public int getTileY(){
		return tileY;
	}
	
	public Image getPlayer(){
		return player;
	}
	
	
}
