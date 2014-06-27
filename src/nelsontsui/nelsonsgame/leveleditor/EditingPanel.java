/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.leveleditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import nelsontsui.nelsonsgame.game.DamagableEntity;
import nelsontsui.nelsonsgame.game.DimensionDouble;
import nelsontsui.nelsonsgame.game.Entity;
import nelsontsui.nelsonsgame.game.MapGate;
import nelsontsui.nelsonsgame.game.NonPlayerCharacter;
import nelsontsui.nelsonsgame.game.Point;
import nelsontsui.nelsonsgame.game.Portal;
import nelsontsui.nelsonsgame.game.Projectile;
import nelsontsui.nelsonsgame.game.SpawnableItem;
import nelsontsui.nelsonsgame.game.TalkableGate;

public class EditingPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener{       
    protected Point cursor;//where the cursor is;
    protected Point placement;//where the entity will be placed
    protected DimensionDouble dimension;//how large the entity will be (dimension by dimension)
    
    protected ArrayList<Entity> npcs = new ArrayList<>();
    protected nelsontsui.nelsonsgame.game.Character player;
    
    protected static final int gridRows = 40;
    protected static final int gridColumns = 74;
    protected Point[][] points = new Point[gridRows][gridColumns];
    protected DimensionDouble[][] dimensions = new DimensionDouble[gridRows][gridColumns]; 
    protected Entity[][] npcsOnGrid = new Entity[gridRows][gridColumns];
    
    public static final int TOP_LEFT = 100;
    public static final int TOP_RIGHT = 200;
    public static final int BOTTOM_LEFT = 300;
    public static final int BOTTOM_RIGHT = 400;
    
    private static int increment = 0;
    
    public EditingPanel(Point cursor, Point placement, DimensionDouble dimension){
        this.cursor = cursor;
        this.placement = placement;
        this.dimension = dimension;
        
        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    public Point getEditCursor(){
        return cursor;
    }
    public Point getPlacement(){
        return placement;
    }
    public DimensionDouble getDimension(){
        return dimension;
    }
    public void setEditCursor(Point cursor){
        this.cursor = cursor;
    }
    public void setPlacement(Point placement){
        this.placement = placement;
    }
    public void setDimension(DimensionDouble dimension){
        this.dimension = dimension;
    }   
    public void cursorToPlacement(Point c){
        double x = cursor.getX();
        double y = cursor.getY();
        double modx = x%10;
        double mody = y%10;        
        placement.setX(x-modx);
        placement.setY(y-mody);
    }
     @Override
    public void mouseMoved(MouseEvent e) {
        cursor.setX(e.getX());
        cursor.setY(e.getY());
        cursorToPlacement(cursor);
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        int py = (int)(placement.getY()/10);
        int px = (int)(placement.getX()/10);
        if(SwingUtilities.isLeftMouseButton(e)){            
            points[py][px] = new Point(placement.getX(),placement.getY());
        }
        if(SwingUtilities.isRightMouseButton(e)){
            points[py][px] = null;
        }
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        Graphics2D graphic = (Graphics2D)g;
        for(int i=10;i<gridColumns*10;i+=10){  
            graphic.drawLine(i,0,i,gridRows*10);
        }
        for(int j=10;j<gridRows*10;j+=10){
            graphic.drawLine(0,j,gridColumns*10,j);
        }
        for(int z = 0;z<gridRows;z++){
            for(int k = 0;k<gridColumns;k++){
                Point p = points[z][k];
                if(p==null){}
                else{
                    graphic.fill(new Rectangle2D.Double(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight()));
                }
            }
        }
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    
    
    
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
}
