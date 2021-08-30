import java.awt.Color;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame jf=new JFrame();
		Board board=new Board();
		 jf.setBounds(0, 0, 1030, 760);
		 jf.setBackground(Color.WHITE);
		 jf.setResizable(false);
		 jf.setVisible(true);
		 jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 jf.addKeyListener(board);
		 jf.add(board);
	}

}
