package PanelTransition;

import javax.swing.*;
import java.awt.*;

public class PanelTransition extends JFrame{
	public static void main(String[] args){
	    JFrame frame = new JFrame("Test");
	    frame.setSize(400,500);
	    frame.setVisible(true);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    frame.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();

	    JLabel title = new JLabel("Locations");
	    title.setFont(new Font("Serif", Font.BOLD, 40));
	    c.gridx = 0;
	    c.gridy = 0;
	    c.gridwidth = 3;
	    frame.add(title, c);

	    JButton b1 = new JButton("View Locations");
	    c.gridx = 0;
	    c.gridy = 1;
	    c.gridwidth = 1;
	    frame.add(b1, c);

	    JButton b2 = new JButton("Insert Locations");
	    c.gridx = 1;
	    c.gridy = 1;
	    frame.add(b2, c);

	    JButton b3 = new JButton("Help");
	    c.gridx = 2;
	    c.gridy = 1;
	    frame.add(b3, c);

	    TextArea text1 = new TextArea(15,40);
	    c.gridx = 0;
	    c.gridy = 2;
	    c.gridwidth = 3;
	    frame.add(text1, c);

	    frame.pack();
	}
}
