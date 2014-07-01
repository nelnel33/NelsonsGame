package nelsontsui.nelsonsgame.leveleditor;
import nelsontsui.nelsonsgame.game.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class EntityTile extends JPanel implements MouseListener{
    protected boolean clicked = false;
    private boolean canToggle = true;
    private String entityName;
    private String id;
    
    public static final int size = 50;//size*size = dimension of button
    
    public EntityTile(String entityName, String id){           
        this.entityName = entityName;
        this.id = id;
        //this.setBorder(GameDisplay.blackborder);
        this.setPreferredSize(new Dimension(size,size));
        this.setBackground(new Color(250,250,210));        
        this.setLayout(new GridBagLayout());//GridBagLayout default settings to center text;
        GridBagConstraints c = new GridBagConstraints();        
        JLabel tile = new JLabel(entityName,SwingConstants.CENTER);
        tile.setVerticalAlignment(JLabel.CENTER);
        this.add(new JLabel(),c);
        this.add(tile,c);
        this.addMouseListener(this);        
    }    
    public String getEntityName(){
        return entityName;
    }
    public String getId(){
        return id;
    }
    public void setEntityName(String entityName){
        this.entityName = entityName;
    }
    public void setId(String id){
        this.id = id;
    }
    public boolean getClicked(){
        return clicked;
    }
    public void canToggle(EntityTile[] e){
        for(int i=0;i<e.length;i++){
            if(e[i]==null){}
            else if(e[i].clicked&&!this.equals(e[i])){
                canToggle = false;
                return;
            }
            
        }
        canToggle = true;
    }    
    @Override
    public void mouseReleased(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)){
            if(clicked == false&&canToggle){
                clicked = true;
                this.setBackground(Color.CYAN);
            }
            else{
                clicked = false;
                this.setBackground(new Color(250,250,210));
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
    
}
