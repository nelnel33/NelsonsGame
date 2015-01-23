package nelsontsui.nelsonsgame.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


public class TalkableGate extends Entity implements Externalizable{
    
    public static final String CLASSNAME = "TalkableGate";
    public static final String DESCRIPTION = "A non-solid object/entity. When you step over it, it speaks in the dialog box;"
                + "Recommended Use: Boss Dialog, Instructions for level;"; 
    
    private String speech;
    private boolean flag = true;
    
    private static final long serialVersionUID = 3312L;
    
    public TalkableGate(){
        super();
    }    
    public TalkableGate(
            double x, 
            double y, 
            double width, 
            double height, 
            String speech         
            ){
        super(x, y, width, height);
        this.speech = speech;
    }
    public String getSpeech(){
        return speech;
    }
  
    public void setSpeech(String speech){
        this.speech = speech;
    }       
    public boolean canOperate(Entity e){
        if((e.getHitbox().isTouching(this.getHitbox()))&&flag){
            flag = false;
            return true;            
        }
        else{
            return false;
        }
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
        return "Speech: "+speech+"; "+super.toString();
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);   
        
        out.writeObject(speech);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        
        speech = (String)in.readObject();
    }
    
    @Override
    public void render(Graphics2D graphic){
        graphic.setColor(Color.DARK_GRAY);
        graphic.draw(new Rectangle2D.Double(this.getX(),this.getY(),this.getWidth(),this.getHeight()));
    }
}
