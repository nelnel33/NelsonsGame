/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Alan T
 */
public class NelsonTitle extends JPanel{    
    public NelsonTitle(){
        setOpaque(false);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D)g;
        
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource("/nelsontsui/nelsonsgame/game/resources/startmenu/nelsons_sword.png"));
        } catch (IOException ex) {}
        gd.drawImage(image, 0, 100, 600, 78, null);
        
        Font font = MainDisplay.MEDIEVAL_FONT.getFont(0,80);
        gd.setFont(font);
        
        gd.setColor(Color.BLACK);
        gd.drawString("NELSON'S",150,170);
        gd.drawString("GAME",425,250);
    }
}

