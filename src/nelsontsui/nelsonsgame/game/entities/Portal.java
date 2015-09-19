package nelsontsui.nelsonsgame.game.entities;

import nelsontsui.nelsonsgame.game.entities.Entity;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.Externalizable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import nelsontsui.nelsonsgame.game.GameDisplay;

public class Portal extends Entity implements Externalizable{
    
    public static final String CLASSNAME = "Portal";
    public static final String DESCRIPTION = "A non-solid entity/object uses to teleport characters from one location to another;Recommended Use: Portal;"
                + "Click the \'Custom\' button on the bottom to set the exit location of the portal;";
    public static final String FILENAME = "portal";
    
    private Entity subportal;
    private boolean canNpcUse;
    private int condition;
    private boolean canPlayerUse;
    protected boolean isMain;
    
    public static int offsetDistance=10;//must be greater than 1;
    public static int DEFAULT = 100;
    public static int KILL_ALL_NONBOSS = 200;
    
    private static final long serialVersionUID = 3313L;
    
    public Portal(){
        super();
    }
    
    public Portal(//to create subportals
            double x,
            double y,
            double width,
            double height,
            Entity subportal,
            int condition,
            boolean isMain
            ){
        super(x,y,width,height);
        this.isMain = isMain;//for aesthetics.
        this.subportal = subportal;
        this.canNpcUse = false;
        this.condition = condition;
        
        //linkerIndex++;
    }
    public Entity getSubportal(){
        return subportal;
    }
    public void setNpcUse(boolean canNpcUse){
        this.canNpcUse = canNpcUse;
    }
    public boolean canNpcUse(){
        return canNpcUse;
    }
    public int getCondition(){
        return condition;
    }
    public boolean getCanPlayerUse(){
        return canPlayerUse;
    }
    public boolean getIsMain(){
        return isMain;
    }
    public void setSubportal(Entity subportal){
        this.subportal = subportal;
    }
    public void setCondition(int condition){
        this.condition = condition;
    }
    public void setCanPlayerUse(boolean canPlayerUse){
        this.canPlayerUse = canPlayerUse;
    }
    public void setIsMain(boolean isMain){
        this.isMain = isMain;
    }
    
    public static void checkPortals(ArrayList<Entity> entities, Player player){
        if(!entities.isEmpty()){
            for(int i=0;i<entities.size();i++){
                if(entities.get(i) instanceof Portal){
                    for(int j=0;j<entities.size();j++){
                        if(i==j){}
                        else{
                            ((Portal)entities.get(i)).determineCanPlayerUse(entities);
                            ((Portal)entities.get(i)).teleport(player);
                            if(((Portal)entities.get(i)).canNpcUse()){
                                System.out.println(((Portal)entities.get(i)).canNpcUse());
                                ((Portal)entities.get(i)).teleport(entities.get(j));
                            }
                        }
                    }                    
                }
            }
        }
    }
    
    public void determineCanPlayerUse(ArrayList<Entity> e){
        if(condition==DEFAULT){
            canPlayerUse = true;
        }
        else if(condition == KILL_ALL_NONBOSS){
            checkEntities(e);
        }
        
    }
    private void checkEntities(ArrayList<Entity> e){
        int npcCount = 0;
        for(int i=0;i<e.size();i++){
            if(e.get(i) instanceof NonPlayerCharacter){
                if(((NonPlayerCharacter)e.get(i)).getCharacterClass()==NonPlayerCharacter.ARCHER
                        ||((NonPlayerCharacter)e.get(i)).getCharacterClass()==NonPlayerCharacter.SUBBOSS
                        ||((NonPlayerCharacter)e.get(i)).getCharacterClass()==NonPlayerCharacter.WARRIOR
                        ||((NonPlayerCharacter)e.get(i)).getCharacterClass()==NonPlayerCharacter.TANK){
                    canPlayerUse = false;
                    npcCount++;
                }
            }
        }
        if(npcCount==0){
            canPlayerUse = true;
        }
        
    }
    private void setCoords(Entity e, Entity p){
        if(e.getHitbox().isTouching(this.getHitbox())){
            if(p.getX()<GameDisplay.ACTIONPANEL_WIDTH/2){
                e.setX(p.getX()+p.getWidth()+offsetDistance);
                e.setY(p.getY());
            }
            else{
                e.setX(p.getX()-offsetDistance-e.getWidth());
                e.setY(p.getY());
            }
            
            System.out.println(this.toString());
        }
    }

    public void teleport(Entity e){
        if(canPlayerUse){
            setCoords(e,subportal);
        } 
    }
    
    public String conditionAsString(int c){
        if(c==DEFAULT){
            return "Default";
        }
        else if(c==KILL_ALL_NONBOSS){
            return "Kill-All-NonBosses";
        }
        else{
            return "INVALID CONDITION";
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
        return super.toString()+" UseCondition: "+conditionAsString(condition)+"; Subportal: ("+subportal.x+", "+subportal.y+");";
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);   
                
        out.writeObject(subportal);
        out.writeBoolean(canNpcUse);
        out.writeInt(condition);
        out.writeBoolean(canPlayerUse);
        out.writeBoolean(isMain);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        
        subportal = (Entity)in.readObject();
        canNpcUse = in.readBoolean();
        condition = in.readInt();
        canPlayerUse = in.readBoolean();
        isMain = in.readBoolean();
    }
    
    @Override
    public void render(Graphics2D graphic){
        if(this.isMain){
            graphic.setColor(new Color(233,118,17));//Orange
        }
        else{
            graphic.setColor(new Color(43,127,223));//Blue
        }
        graphic.fill(new Ellipse2D.Double(this.getX(),this.getY(),this.getWidth(),this.getHeight()));
    }
    
}
