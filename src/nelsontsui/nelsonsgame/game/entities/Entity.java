package nelsontsui.nelsonsgame.game.entities;
import nelsontsui.nelsonsgame.game.mapping.Hitbox;
import nelsontsui.nelsonsgame.game.mapping.Point;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import nelsontsui.nelsonsgame.game.imagearchiver.ImportedImage;

public class Entity implements Externalizable{
    
    public static final String CLASSNAME = "Entity";
    public static final String DESCRIPTION = "A non-solid object that has no attributes other than a location and size; Recommended Use: Pathway;";
    public static final String FILENAME = "entity";
    
    public static final ImportedImage IMAGE = new ImportedImage(FILENAME, ImportedImage.ENTITIES_DIR);
    
    protected double x;
    protected double y;    
    protected double width;
    protected double height;
    protected int direction;
    
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;
    
    //No particular reason why it starts at 33. I just like 33.
    private static final long serialVersionUID = 331L;
    
    
    public Entity(){}
    
    public Entity(double x, double y, double width, double height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.direction = UP;        
    }
    public void setX(double x){
        this.x=x;
    }
    public void setY(double y){
        this.y=y;
    }
    public void setDirection(int direction){
        this.direction = direction;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public int getDirection(){
        return direction;
    }
    public double getWidth(){
        return width;
    }
    public double getHeight(){
        return height;
    }
    public Hitbox getHitbox(){
        return new Hitbox(new Point(this.x,this.y), new Point(this.x+this.width,this.y+this.height));
    }
    public String directionAsString(int d){
        if(d==UP){
            return "Up";
        }
        else if(d==DOWN){
            return "Down";
        }
        else if(d==LEFT){
            return "Left";
        }
        else if(d==RIGHT){
            return "Right";
        }
        else{
            return "INVALID DIRECTION";
        }
    }
    
    public String className(){
        return CLASSNAME;
    }
    
    public String description(){
        return DESCRIPTION;
    }
    
    @Override
    public String toString(){
        return "Class: "+this.getClass()+"; Location: ("+x+","+y+"); Size: ("+width+","+height+"); Direction: "+directionAsString(direction)+";";
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(x);
        out.writeDouble(y);
        out.writeDouble(width);
        out.writeDouble(height);
        out.writeInt(direction);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readDouble();
        y = in.readDouble();
        width = in.readDouble();
        height = in.readDouble();
        direction = in.readInt();
    }
    
    public void render(Graphics2D graphic){
        //IMAGE.drawTiledImage(graphic, x,y,width, height, 10);
        graphic.setColor(new Color(0,204,0));//greenish
        graphic.fill(new Rectangle2D.Double(this.getX(),this.getY(),this.getWidth(),this.getHeight()));
    }
}
