package oncai;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class UserInterface extends JPanel implements MouseListener, MouseMotionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8165755806870240197L;
	static int squareSize = 64;
	static int mouseX, mouseY, newMouseX, newMouseY, captureX = 0, captureY = 0;

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.setBackground(new Color(255,200,100));
//		JLabel movimentosPossiveis = new JLabel(Oncai2.posibleMoves());
//	    movimentosPossiveis.setHorizontalAlignment(SwingConstants.RIGHT);
//	    JLabel labelJaguar = new JLabel(String.valueOf(Oncai2.jaguarPosition));
//	    labelJaguar.setHorizontalAlignment(SwingConstants.RIGHT);
//	    this.add(movimentosPossiveis);
//	    this.add(labelJaguar);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		for (int i = 0; i < 35; i+=1){
			g.setColor(new Color(255,200,100));
			g.fillRect((i%5+(i/5)%1)*squareSize, (i/5)*squareSize, squareSize, squareSize);
			g.setColor(new Color(100, 100, 100));

			g.fillOval((i%5+(i/5)%1)*squareSize, ((i/5)%5)*squareSize, squareSize, squareSize);
			g.fillOval(1*squareSize, 5*squareSize, squareSize, squareSize);
			g.fillOval(2*squareSize, 5*squareSize, squareSize, squareSize);
			g.fillOval(3*squareSize, 5*squareSize, squareSize, squareSize);
			g.fillOval(0*squareSize, 6*squareSize, squareSize, squareSize);
			g.fillOval(2*squareSize, 6*squareSize, squareSize, squareSize);
			g.fillOval(4*squareSize, 6*squareSize, squareSize, squareSize);

		}
		ClassLoader cl = this.getClass().getClassLoader();
		Image chessPiecesImage;
		chessPiecesImage = new ImageIcon(cl.getResource("images/ChessPieces.png")).getImage();
		for(int i=0; i<35; i++){
			int j=-1, k=-1;
			switch (Oncai2.oncaiBoard [i/5][i%5]) {
			case "j": j=5;k=0;
			break;
			case "d": j=5;k=1;
			break;
			}
			if (j!=-1 && k!=-1){
				g.drawImage(chessPiecesImage, (i%5)*squareSize, (i/5)*squareSize, (i%5+1)*squareSize, (i/5+1)*squareSize, j*64, k*64, (j+1)*64, (k+1)*64, this);
			}
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getX() < 8* squareSize && e.getY() < 8*squareSize){
			mouseX = e.getX();
			mouseY = e.getY();
			repaint();
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		String dragMove = null;
		if (e.getX() < 8 * squareSize && e.getY() < 8 * squareSize){
			newMouseX = e.getX();
			newMouseY = e.getY();
			if (e.getButton() == MouseEvent.BUTTON1){				

				if ("j".equals(Oncai2.oncaiBoard[mouseY/squareSize][mouseX/squareSize])){
					if (captureX != 0 && captureY != 0){
						dragMove = ""+mouseY/squareSize+mouseX/squareSize+newMouseY/squareSize+newMouseX/squareSize+captureY/squareSize+captureX/squareSize+Oncai2.oncaiBoard[captureY/squareSize][captureX/squareSize];
						captureX = 0;
						captureY = 0;
					} else {
						dragMove = ""+mouseY/squareSize+mouseX/squareSize+newMouseY/squareSize+newMouseX/squareSize+"-"+"-"+Oncai2.oncaiBoard[newMouseY/squareSize][newMouseX/squareSize];
					}
				} else {
					dragMove = ""+mouseY/squareSize+mouseX/squareSize+newMouseY/squareSize+newMouseX/squareSize+"-"+"-"+Oncai2.oncaiBoard[newMouseY/squareSize][newMouseX/squareSize];
				}

			}
			String userPossibilities = Oncai2.posibleMoves();
			if (userPossibilities.replaceAll(dragMove, "").length() < userPossibilities.length()){
				Oncai2.makeMove(dragMove);
				Oncai2.flipPlayer();
				try {
					Oncai2.makeMove(Oncai2.alphaBeta(Oncai2.globalDepth, 1000000, -1000000, "", 0));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Oncai2.flipPlayer();

				repaint();
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (e.getX() < 8 * squareSize && e.getY() < 8 * squareSize){
			if ("j".equals(Oncai2.oncaiBoard[mouseY/squareSize][mouseX/squareSize])){
				int dragCaptureX = e.getX();
				int dragCaptureY = e.getY();	
				if ("d".equals(Oncai2.oncaiBoard[dragCaptureY/squareSize][dragCaptureX/squareSize])){
					captureX = dragCaptureX;
					captureY = dragCaptureY;

				}	
			}
		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}

