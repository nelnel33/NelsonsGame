/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class InventoryIcon extends JPanel implements MouseListener {
    private boolean isButton1 = false;
    private boolean isButton2 = false;
    public InventoryIcon(){
        this.addMouseListener(this);
    }
    public boolean isButton1(){
        boolean temp = isButton1;
        isButton1 = false;
        return temp;
    }
    public boolean isButton2(){
        boolean temp = isButton2;
        isButton2 = false;
        return temp;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouse = e.getButton();
        if(SwingUtilities.isLeftMouseButton(e)){
            //isButton1 = true;
            //System.out.println("clicked button1");
        }
        if(SwingUtilities.isRightMouseButton(e)){
            //isButton2 = true;
            //System.out.println("clicked button2");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int mouse = e.getButton();
        if(SwingUtilities.isLeftMouseButton(e)){
            isButton1 = true;
            //System.out.println("rel button1");
        }
        if(SwingUtilities.isRightMouseButton(e)){
            isButton2 = true;
            //System.out.println("rel button2");
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
    
}
