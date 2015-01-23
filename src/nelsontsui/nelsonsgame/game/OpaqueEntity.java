package nelsontsui.nelsonsgame.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class OpaqueEntity extends Entity implements Externalizable{   
    
    public static final String CLASSNAME = "Opaque Entity";
    public static final String DESCRIPTION = "A solid object that has a location and size;Recommended Use: Walls";
    
    private static final long serialVersionUID = 332L;
    
    public OpaqueEntity(){
        super();
    }
    public OpaqueEntity(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
    
    @Override
    public String className(){
        return CLASSNAME;
    }
    
    @Override
    public String description(){
        return DESCRIPTION;
    }
    
    @Override
    public String toString(){
        return super.toString();
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
    }
    
    @Override
    public void render(Graphics2D graphic){
        graphic.setColor(Color.DARK_GRAY);
        graphic.fill(new Rectangle2D.Double(this.getX(),this.getY(),this.getWidth(),this.getHeight()));
    }
    
}
