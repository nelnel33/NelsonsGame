/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class NelsonWatermark extends JPanel{
    private int width = 700;
    private int height = 16;
    private final static String web = "nelsontsui.com";
    private final static String gamename = "Nelson's Game";
    
    public NelsonWatermark(){}
    
    @Override
    public void paintComponent(Graphics g){
        Graphics2D graphic = (Graphics2D)g;
        graphic.setFont(new Font(Font.SANS_SERIF,Font.BOLD,15));
        graphic.setColor(new Color(184,242,242));//light blue;
        graphic.drawString(web,15,15);
        graphic.drawString(gamename,865,15);        
    }
}
