package oncai;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Player {

	private int	tileX,
	tileY;

	private Image player;

	public Player(){

		ImageIcon img = new ImageIcon("src//main//resources//images//dog.png");
		player = img.getImage();

		tileX = 1;
		tileY = 1;
	}

	public Player(int x, int y, String animal){

		if (animal.equals("dog")){
			ImageIcon img = new ImageIcon("src//main//resources//images//dog.png");
			player = img.getImage();
		}
		if (animal.equals("jaguar")){
			ImageIcon img = new ImageIcon("src//main//resources//images//jaguar.png");
			player = img.getImage();
		}

		tileX = x;
		tileY = y;
	}

	public void move(int dx, int dy){
		tileX += dx;
		tileY += dy;
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
