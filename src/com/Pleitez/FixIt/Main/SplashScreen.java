package com.Pleitez.FixIt.Main;

import java.awt.Rectangle;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class SplashScreen extends JWindow {
    private static final long serialVersionUID = -3236195005741281382L;
    
    public SplashScreen() {
        JWindow jwindow = new JWindow();
        JPanel jpanel = new JPanel();
        
//        BufferedImage icon32 = loadBufferedImage("/res/icon/splash.png");
//        ImageIcon image = new ImageIcon(icon32);
        
        JLabel label1 = new JLabel(new ImageIcon(Class.class.getResource("/icon/splash.png")));
        jpanel.add(label1);
        
        jwindow.getContentPane().add(jpanel);
        jwindow.setBounds(new Rectangle(512, 512));
        jwindow.setLocationRelativeTo(null);
        
        jwindow.setVisible(true);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jwindow.setVisible(false);
        
        jwindow.dispose();
    }
    
//    private BufferedImage loadBufferedImage(String string){
//        try{
//            BufferedImage bufferedImage = ImageIO.read(this.getClass().getResource(string));
//            return bufferedImage;
//        }
//        catch(IOException e){
//            e.printStackTrace();
//        }
//        return null;
//    }
}
