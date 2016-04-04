package oncai;

import javax.swing.*;

public class Oncai {

	public static void main(String[] args) {
		new Oncai();

	}
	
	public Oncai(){
		JFrame f = new JFrame();
		f.setTitle("Jogo da Onça");
		f.add(new Board());
		f.setSize(240, 355);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
