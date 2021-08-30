import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener, ActionListener {
	private static final int initSize = 3;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] coordinatesX = new int[1000];
	private int[] coordinatesY = new int[1000];
	private int foodX,foodY;
	
	private int size;
	private boolean isFirstMove,noFood;
	private int keyPressed;
	private ImageIcon food;

	private Timer t;
	private boolean gameOver;
	private FileInputStream mem;

	public Board() {
		size = initSize;
		isFirstMove = true;
		t = new Timer(100, this);
		food=new ImageIcon("food.png");
		t.start();
		noFood=false;
		foodX=(int ) (Math.random()*48)*20 +10;
		foodY=(int ) (Math.random()*34)*20 +10;
		gameOver=false;
		try {
			mem=new FileInputStream("records.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void paint(Graphics g) {
		g.drawRect(10, 10, 1001, 701);
		g.setColor(Color.CYAN);
		g.fillRect(11, 11, 1000, 700);
		if (isFirstMove) {
			food.paintIcon(this, g,foodX,foodY);
			ImageIcon head = new ImageIcon("head.png");
			head.paintIcon(this, g, 210, 210);
			coordinatesX[0] = 210;
			coordinatesY[0] = 210;
			for (int i = 1; i < initSize + 1; i++) {
				coordinatesX[i] = coordinatesX[i - 1] - 20;
				coordinatesY[i] = coordinatesY[i - 1];
				ImageIcon body = new ImageIcon("body.png");
				body.paintIcon(this, g, coordinatesX[i], coordinatesY[i]);
			}
			isFirstMove=false;
		} else if(!gameOver){
			
			ImageIcon head = new ImageIcon("head.png");
			head.paintIcon(this, g, coordinatesX[0], coordinatesY[0]);
			for (int i = 0; i < size; i++) {
				if(i!=0 && coordinatesX[i]==coordinatesX[0] && coordinatesY[i]==coordinatesY[0])
					gameOver=true;
				else {
				ImageIcon body = new ImageIcon("body.png");
				body.paintIcon(this, g, coordinatesX[i], coordinatesY[i]);
				}
			}
			noFood=coordinatesX[0]==foodX && coordinatesY[0]==foodY? true:false;
			if(noFood) {
				foodX=(int ) (Math.random()*48)*20 +10;
				foodY=(int ) (Math.random()*34)*20 +10;
				size++;
			}
			
			food.paintIcon(this, g,foodX,foodY);
		}
		else {
			 // create a frame
	        JFrame f = new JFrame("GAMEOVER!");
	        JLabel l=new JLabel("                      The game is over, you lost! Final pontuation: "+(size-initSize)+"                 ");
	        f.setBounds(200,200,400, 100);
	        // create a button
	        JButton b = new JButton("Restart");
	 
	        // add action listener
	        b.addActionListener(this);
	        // create a panel
	        JPanel p1 = new JPanel();
	        
	        f.setResizable(false);
	        p1.add(l);
	        p1.add(b);
	        p1.show();
	        f.add(p1);
	        f.show();
	        b.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					/*try {
						System.out.println("hey");
						byte[] bytes=mem.readAllBytes();
						System.out.println("hey1");
						bytes[bytes.length]=(byte) (size-initSize);
						System.out.println("hey2");
						@SuppressWarnings("resource")
						FileOutputStream file=new FileOutputStream("records.txt");
						System.out.println("hey4");
						file.write(bytes);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}*/
					coordinatesX=new int[1000];
					coordinatesY=new int[1000];
					gameOver=false;
					isFirstMove=true;
					size=initSize;
					keyPressed=0;
					f.hide();
					b.setVisible(false);
					p1.hide();
					l.hide();
					
				}
	        	
	        });
	      
		}
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		t.start();
		if(gameOver) t.stop();
		switch (keyPressed) {
		// right
		case 39:
			
			for (int i = size-1; i >=0; i--) {
				coordinatesY[i+1]=coordinatesY[i];
			}
			for (int i = size-1; i >0; i--) {
				coordinatesX[i]=coordinatesX[i-1];
			}
			if(coordinatesX[0]+20>990)
				coordinatesX[0]=10;
			else
			coordinatesX[0]+=20;
			
			break;
		// left
		case 37:
			
			for (int i = size-1; i >=0; i--) {
				coordinatesY[i+1]=coordinatesY[i];
			}
			for (int i = size-1; i >0; i--) {
				coordinatesX[i]=coordinatesX[i-1];
			}
			if(coordinatesX[0]-20<10)
				coordinatesX[0]=990;
			else
			coordinatesX[0]-=20;
			
			break;
		// up
		case 38:
			for (int i = size-1; i >= 0; i--) {
				coordinatesX[i+1]=coordinatesX[i];
			}
			for (int i = size-1; i > 0; i--) {
				coordinatesY[i]=coordinatesY[i-1];
			}
			if(coordinatesY[0]-20<10)
				coordinatesY[0]=690;
			else
			coordinatesY[0]-=20;
			break;
		// down
		case 40:
			for (int i = size-1; i >= 0; i--) {
				coordinatesX[i+1]=coordinatesX[i];
			}
			for (int i = size-1; i > 0; i--) {
				coordinatesY[i]=coordinatesY[i-1];
			}
			if(coordinatesY[0]+20>700)
				coordinatesY[0]=10;
			else
			coordinatesY[0]+=20;
			
			break;
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if((keyPressed%2==0 && e.getKeyCode()%2==1) || (keyPressed%2==1 && e.getKeyCode()%2==0)) {
			keyPressed = e.getKeyCode();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
