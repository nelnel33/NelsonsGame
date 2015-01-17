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
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Nelnel33
 */
public class NelsonTitle extends JPanel{

    private int width = 370;
    private int height = 40;
    private final static String nelsonsGame = "Nelson's Game";
    
    public NelsonTitle(){}
    
    @Override
    public void paintComponent(Graphics g){
        Graphics2D graphic = (Graphics2D)g;
        graphic.setFont(new Font(Font.SANS_SERIF,Font.BOLD,50));
        graphic.setColor(new Color(184,242,242));//light blue;
        graphic.drawString(nelsonsGame,0,50);        
    }
}

