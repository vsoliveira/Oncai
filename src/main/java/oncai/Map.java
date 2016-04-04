package oncai;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.ImageIcon;

public class Map {
	
	InputStream is;
	
	private Scanner m;
	private String Map[] = new String[10];
	
	private Image grass,
				  wall,
				  dog,
				  jaguar;
	
	public Map(){
		ImageIcon img = new ImageIcon("src//main//resources//images//grass.png");
		grass = img.getImage();
		img = new ImageIcon("src//main//resources//images//wall.png");
		wall = img.getImage();
		
		openFile();
		readFile();
		closeFile();
	}
	
	public Image getGrass(){
		return grass;
	}
	
	public Image getWall(){
		return wall;
	}
	
	public Image getDog(){
		return dog;
	}
	
	public Image getJaguar(){
		return jaguar;
	}
		
	
	public String getMap(int x, int y){
		String index =  Map[y].substring(x, x+1);
		return index;
	}
	
	public void openFile(){
		
		try {
			m = new Scanner(new File("src//main//resources//images//Map.txt"));			
		} catch (Exception e) {
			System.out.println("Erro ao carregar o mapa:" + e);
		}

	}
	
	public void readFile(){
		while (m.hasNext()) {
			for (int i = 0; i < 10; i++){
				Map[i] = m.next();
			}
			
		}
	}
	
	public void closeFile(){
		m.close();
	}
}