package nelsontsui.nelsonsgame.leveleditor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import nelsontsui.nelsonsgame.game.*;
import nelsontsui.nelsonsgame.game.Point;

public class EditingPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener{
    protected ArrayList<Point> ends = new ArrayList<>(); 
    protected ArrayList<Point> starts = new ArrayList<>();
    protected ArrayList<DimensionDouble> dimensions = new ArrayList<>();
    protected ArrayList<Point> points = new ArrayList<>();
   
    
    protected Point cursor;//where the cursor is;
    protected Point dragStart;//Where the drawRect starts;
    protected Point dragEnd;//Where drawRect ends;
    protected DimensionDouble dragArea;//How large your drawRect will be;
    
    protected ArrayList<Entity> npcs = new ArrayList<>();
    protected nelsontsui.nelsonsgame.game.Character player;
    
    public static final int TOP_LEFT = 100;
    public static final int TOP_RIGHT = 200;
    public static final int BOTTOM_LEFT = 300;
    public static final int BOTTOM_RIGHT = 400;
    
    private static int increment = 0;
    
    //private boolean hasBeenReleased = false;
    
    public EditingPanel(Point cursor,Point dragStart, Point dragEnd, DimensionDouble dragArea){
        this.cursor = cursor;
        this.dragStart = dragStart;
        this.dragArea = dragArea;
        this.dragEnd = dragEnd;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    public Point getEditCursor(){
        return cursor;
    }
    public Point getDragStart(){
        return dragStart;
    }
    public Point getDragEnd(){
        return dragEnd;
    }
    public DimensionDouble getDragArea(){
        return dragArea;
    }
    
    public void setEditCursor(Point cursor){
        this.cursor = cursor;
    }
    public void setDragStart(Point dragStart){
        this.dragStart = dragStart;
    }
    public void setDragEnd(Point dragEnd){
        this.dragEnd = dragEnd;
    }
    public void setDragArea(DimensionDouble dragArea){
        this.dragArea = dragArea;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)){
            dragStart = new Point(e.getX(),e.getY());
            //dragEnd = new Point(cursor.getX(),cursor.getY());
            dragArea = new DimensionDouble(Math.abs(e.getX()-cursor.getX()),Math.abs(e.getY()-cursor.getY()));
            starts.add(dragStart);
            //ends.add(dragEnd);
            dimensions.add(dragArea);    
            //hasBeenReleased = true;        
            //setPointsAndCheck();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
            cursor.setX(e.getX());
            cursor.setY(e.getY());
        }
    
    @Override
    public void mousePressed(MouseEvent e) {
        
    }
    @Override
    public void mouseReleased(MouseEvent e) {    
        if(SwingUtilities.isLeftMouseButton(e)){
            dragEnd = new Point(cursor.getX(),cursor.getY());
            ends.add(dragEnd);
        }
        if(SwingUtilities.isRightMouseButton(e)){
            
        }
    }
    public int determineSupposedPointOrigin(Point origin, Point released){
        if(origin.getX()-released.getX()<0){
            if(origin.getY()-released.getY()<0){
                return TOP_LEFT;
            }
            else{
                return BOTTOM_LEFT;
            }
        }
        else{
            if(origin.getY()-released.getY()<0){
                return TOP_RIGHT;
            }
            else{
                return BOTTOM_RIGHT;
            }
        }
    }
    public void setPointsAndCheck(){
        if((dimensions.size()>increment)&&(starts.size()>increment)&&(ends.size()>increment)){
            if(determineSupposedPointOrigin(starts.get(increment),ends.get(increment))
                    ==TOP_LEFT){
                points.add(starts.get(increment));
            }
            else if(determineSupposedPointOrigin(starts.get(increment),ends.get(increment))
                    ==BOTTOM_LEFT){
                points.add(new Point(starts.get(increment).getX(),ends.get(increment).getY()));
            }
            else if(determineSupposedPointOrigin(starts.get(increment),ends.get(increment))
                    ==TOP_RIGHT){
                points.add(new Point(ends.get(increment).getX(),starts.get(increment).getY()));
            }
            else{//BOTTOM RIGHT
                points.add(new Point(ends.get(increment).getX(),ends.get(increment).getY()));
            }            
            increment++;
            //hasBeenReleased = false;
        }
    }
    
   @Override
   public void paintComponent(Graphics g){
       Graphics2D graphic = (Graphics2D)g;
       
       for(int i=0;i<increment;i++){
            graphic.fill(new Rectangle2D.Double(points.get(i).getX(),points.get(i).getY(),
                    dimensions.get(i).getWidth(),dimensions.get(i).getHeight()));
       }
   }
   @Override
    public void actionPerformed(ActionEvent e) {
        setPointsAndCheck();
   }
   
    
    
    

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}
