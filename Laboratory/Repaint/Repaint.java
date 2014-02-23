package Repaint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Repaint {
	public static void main(String[] args) {
        JFrame mainWindow = new JFrame("foo");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        final JPanel panel = new JPanel(null);
        mainWindow.setContentPane(panel);
        panel.setPreferredSize(new Dimension(500,500));
        
        final JPanel redPanel = new JPanel(null){
            @Override
            protected void paintComponent(Graphics g) {
            g.setColor(Color.red);
            g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        redPanel.setBounds(0,0,300,300);
        redPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	redPanel.repaint();
            	System.out.println("Red");
            }
        });
        panel.add(redPanel);
        
        final JPanel greenPanel = new JPanel(null){
            @Override
            protected void paintComponent(Graphics g) {
            g.setColor(Color.green);
            g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        greenPanel.setBounds(200,200,500,500);
        greenPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	redPanel.repaint();
            	System.out.println("Green");
            }
        });
        panel.add(greenPanel);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	greenPanel.repaint();
            	System.out.println("Screen");
            }
        });
        panel.setComponentZOrder(redPanel, 0);
        mainWindow.pack();
        mainWindow.setVisible(true);
    }
}
