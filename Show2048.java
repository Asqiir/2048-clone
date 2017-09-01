package clone2048;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import clone2048.Save2048.MovementListener;

public class Show2048 extends JPanel {
	JFrame frame = new JFrame("Asqiir's 2048 clone");
	Save2048 model;
	
	public void setListener(final MovementListener up, final MovementListener down, final MovementListener left, final MovementListener right) {
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {}

			@Override
			public void keyReleased(KeyEvent arg0) {}

			@Override
			public void keyTyped(KeyEvent arg0) {
				char key = arg0.getKeyChar();
				
				if(key == 'a') {
					left.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "left key pressed"));
				}
				if(key == 'd') {
					right.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "left key pressed"));
				}
				if(key == 'w') {
					up.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "left key pressed"));
				}
				if(key == 's') {
					down.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "left key pressed"));
				}
			}
			}
		);
		
	}
	
	public Show2048(Save2048 model) {
		this.model = model;
		
		frame.setSize(400,400);
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void update() {
		frame.setTitle("Asqiir's 2048 clone – " + model.getScore() + " points" );
		frame.repaint();
		
		if(model.isLost()) {
			JFrame scores = new JFrame("Highscore");
			JLabel text = new JLabel();
			scores.add(text);
			text.setText("Your highscores:\n"  /*model.getHighscores()*/);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		int[][] draw = model.getData();
		Graphics2D g2d = (Graphics2D) g;
		
		for(int x=0; x<4; x++) {
			for(int y=0; y<4; y++) {
				if(draw[x][y] != 0) {
					g.setColor(getColor(draw[x][y]));
					g.fillRect(x*100, y*100, 100, 100);
					
					if(g.getColor() != Color.black && g.getColor() != Color.darkGray && g.getColor() != Color.blue) {
						g.setColor(Color.black);	
					} else {
						g.setColor(Color.white);
					}
					
					g2d.drawString(new Integer((int)Math.pow(2,(draw[x][y]))).toString(), x*100+45, y*100+45);
				}
			}
		}
	}

	private Color getColor(int colorIdentifier) {
		switch(colorIdentifier) {
			case(1): return Color.white;
			case(2): return Color.lightGray;
			case(3): return Color.darkGray;
			case(4): return Color.black;
			case(5): return Color.yellow;
			case(6): return Color.orange;
			case(7): return Color.red;
			case(8): return Color.pink;
			case(9): return Color.magenta;
			case(10): return Color.cyan;
			case(11): return Color.blue;
			default: return Color.green;
		}
	}
	
}
