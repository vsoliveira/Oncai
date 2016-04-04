package oncai;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel implements ActionListener{
	
	private Timer timer;
	private Map m;
	private Player p;
	
	public Board(){
		
		m = new Map();
		p = new Player();
		addKeyListener(new Al());
		setFocusable(true);
		
		timer = new Timer(25, this);
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e){
		repaint();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		
		for(int y = 0; y < 10; y++){
			for (int x = 0; x < 7; x++){
				if(m.getMap(x, y).equals("g")){
					g.drawImage(m.getGrass(), x * 32, y * 32, null);
				}
				
				if(m.getMap(x, y).equals("w")){
					g.drawImage(m.getWall(), x * 32, y * 32, null);
				}
				
//				if(m.getMap(x, y).equals("d")){
//					g.drawImage(m.getDog(), x * 32, y * 32, null);
//				}
//				
//				if(m.getMap(x, y).equals("j")){
//					g.drawImage(m.getJaguar(), x * 32, y * 32, null);
//				}
				
			}
		}
		
		g.drawImage(p.getPlayer(), p.getX(), p.getY(), null);
	}
	
	public class Al extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			int keycode = e.getKeyCode();
			if(keycode == KeyEvent.VK_W){
				p.move(0, -32, 0, -1);
			}
			if(keycode == KeyEvent.VK_S){
				p.move(0, 32, 0, 1);
			}
			if(keycode == KeyEvent.VK_A){
				p.move(-32, 0, -1, 0);
			}
			if(keycode == KeyEvent.VK_D){
				p.move(32, 0, 32, 0);
			}
		}
		
		public void keyReleased(KeyEvent e){
			
		}
		
		public void keyTyped(KeyEvent e){
			
		}
		
	}
}
