package nelsontsui.nelsonsgame.game;

import java.io.Externalizable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


public class TalkableGate extends Entity implements Externalizable{
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
    
    public static String description(){
        return "TalkableGate: A non-solid object/entity. When you step over it, it speaks in the dialog box;"
                + "Recommended Use: Boss Dialog, Instructions for level;";
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
}
