package nelsontsui.nelsonsgame.game;

import nelsontsui.nelsonsgame.game.entities.Entity;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class EnhancedButton extends JButton implements MouseListener, ActionListener{
    
    private boolean pressed = false;
    private ActionPanel activePanel;
    private int operation;
    
    //uses Entity."Direction" for operation
    //Entity.UP; Entity.DOWN; Entity.LEFT; Entity.RIGHT;
    
    public EnhancedButton(String text, ActionPanel activePanel, int operation){
        super(text);
        this.activePanel = activePanel;
        this.operation = operation;
        
        this.addMouseListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(pressed){
            if(operation == Entity.UP){
                activePanel.getPlayer().up(activePanel.getEntities());
            }
            else if(operation == Entity.DOWN){
                activePanel.getPlayer().down(activePanel.getEntities());
            }
            else if(operation == Entity.LEFT){
                activePanel.getPlayer().left(activePanel.getEntities());
            }
            else if(operation == Entity.RIGHT){
                activePanel.getPlayer().right(activePanel.getEntities());
            }
            else{
                System.out.println("INVALID OPERATION!");
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true; 
        activePanel.requestFocusInWindow();
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
        activePanel.requestFocusInWindow();
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
    @Override
    public void mouseClicked(MouseEvent e) {}
    
}
