package oncai;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1221064099118621189L;
	
	private Timer timer;
	private Map m;
	private Player d1,d2,d3,d4,d5,
				   d6,d7,d8,d9,d10,
				   d11,d12,d13,d14,
				   j;

	private String message = "";
	private boolean win = false;

	private Font font = new Font("Serif", Font.BOLD, 48);

	public Board(){

		/* INSTÂNCIA O MAPA E AS PEÇAS*/
		
		m = new Map();
		d1 = new Player(1,1,"dog");
		d2 = new Player(2,1, "dog");
		d3 = new Player(3,1, "dog");
		d4 = new Player(4,1, "dog");
		d5 = new Player(5,1, "dog");
		d6 = new Player(1,2, "dog");
		d7 = new Player(2,2, "dog");
		d8 = new Player(3,2, "dog");
		d9 = new Player(4,2, "dog");
		d10 = new Player(5,2, "dog");
		d11 = new Player(1,3, "dog");
		d12 = new Player(2,3, "dog");
		d13 = new Player(4,3, "dog");
		d14 = new Player(5,3, "dog");
		j = new Player(3,3, "jaguar");
		
		
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

		if (!win){
			for(int y = 0; y < 10; y++){
				for (int x = 0; x < 7; x++){
					if(m.getMap(x, y).equals("g")){
						g.drawImage(m.getGrass(), x * 32, y * 32, null);
					}

					if(m.getMap(x, y).equals("w")){
						g.drawImage(m.getWall(), x * 32, y * 32, null);
					}
				}
			}
			
			/* DESENHA AS PEÇAS NO TABULEIRO*/
			
			g.drawImage(d1.getPlayer(), d1.getTileX() * 32, d1.getTileY() * 32, null);
			g.drawImage(d2.getPlayer(), d2.getTileX() * 32, d2.getTileY() * 32, null);
			g.drawImage(d3.getPlayer(), d3.getTileX() * 32, d3.getTileY() * 32, null);
			g.drawImage(d4.getPlayer(), d4.getTileX() * 32, d4.getTileY() * 32, null);
			g.drawImage(d5.getPlayer(), d5.getTileX() * 32, d5.getTileY() * 32, null);
			g.drawImage(d6.getPlayer(), d6.getTileX() * 32, d6.getTileY() * 32, null);
			g.drawImage(d7.getPlayer(), d7.getTileX() * 32, d7.getTileY() * 32, null);
			g.drawImage(d8.getPlayer(), d8.getTileX() * 32, d8.getTileY() * 32, null);
			g.drawImage(d9.getPlayer(), d9.getTileX() * 32, d9.getTileY() * 32, null);
			g.drawImage(d10.getPlayer(), d10.getTileX() * 32, d10.getTileY() * 32, null);
			g.drawImage(d11.getPlayer(), d11.getTileX() * 32, d11.getTileY() * 32, null);
			g.drawImage(d12.getPlayer(), d12.getTileX() * 32, d12.getTileY() * 32, null);
			g.drawImage(d13.getPlayer(), d13.getTileX() * 32, d13.getTileY() * 32, null);
			g.drawImage(d14.getPlayer(), d14.getTileX() * 32, d14.getTileY() * 32, null);
			g.drawImage(j.getPlayer(), j.getTileX() * 32, j.getTileY() * 32, null);
		}

		if(win){
			g.setColor(Color.CYAN);
			g.setFont(font);
			g.drawString(message, 150, 300);
		}


	}
	
	/* REALIZA O MOVIMENTO DE UMA PEÇA APENAS
	 * TODO: IMPLEMENTAR ESSE METODO NA CLASSE PLAYER.
	 */

	public class Al extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			int keycode = e.getKeyCode();
			if(keycode == KeyEvent.VK_W){
				if(!m.getMap(d1.getTileX(), d1.getTileY() - 1).equals("w")){
					d1.move(0, -1);
				}
			}
			if(keycode == KeyEvent.VK_S){
				if(!m.getMap(d1.getTileX(), d1.getTileY() + 1).equals("w")){
					d1.move(0, 1);
				}
			}
			if(keycode == KeyEvent.VK_A){
				if(!m.getMap(d1.getTileX() - 1, d1.getTileY()).equals("w")){
					d1.move(-1, 0);
				}
			}
			if(keycode == KeyEvent.VK_D){
				if(!m.getMap(d1.getTileX() + 1, d1.getTileY()).equals("w")){
					d1.move(1, 0);
				}
			}
		}

		public void keyReleased(KeyEvent e){

		}

		public void keyTyped(KeyEvent e){

		}

	}
}
