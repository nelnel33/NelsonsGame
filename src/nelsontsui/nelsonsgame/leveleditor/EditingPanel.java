package nelsontsui.nelsonsgame.leveleditor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import nelsontsui.nelsonsgame.game.Point;
import nelsontsui.nelsonsgame.game.*;

public class EditingPanel extends JPanel implements MouseListener, MouseMotionListener{
    protected Point cursor;
    protected Point drag;
    protected DimensionDouble dragArea;
    public EditingPanel(Point cursor, Point drag, DimensionDouble dragArea){
        this.cursor = cursor;
        this.drag = drag;
        this.dragArea = dragArea;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    public Point getEditCursor(){
        return cursor;
    }
    public Point getDrag(){
        return drag;
    }
    public DimensionDouble getDragArea(){
        return dragArea;
    }
    public void setEditCursor(Point cursor){
        this.cursor = cursor;
    }
    public void setDrag(Point drag){
        this.drag = drag;
    }
    public void setDragArea(DimensionDouble dragArea){
        this.dragArea = dragArea;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
       cursor.setX(e.getX());
       cursor.setY(e.getY());
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
