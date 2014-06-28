package nelsontsui.nelsonsgame.game;

import java.util.ArrayList;

public class Portal extends Entity{
    private Entity subportal;
    private boolean canNpcUse;
    private int condition;
    private boolean canPlayerUse;
    protected boolean isMain;
    
    public static int offsetDistance=10;//must be greater than 1;
    public static int DEFAULT = 100;
    public static int KILL_ALL_NONBOSS = 200;
    
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
    public void determineCanPlayerUse(ArrayList<Entity> e){
        if(condition==DEFAULT){
            canPlayerUse = true;
        }
        else if(condition == KILL_ALL_NONBOSS){
            checkNpcs(e);
        }
        
    }
    private void checkNpcs(ArrayList<Entity> e){
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
                    if(p.getX()<GameDisplay.edgeX/2){
                        e.setX(p.getX()+p.getWidth()+offsetDistance);
                        e.setY(p.getY());
                    }
                    else{
                        e.setX(p.getX()-offsetDistance);
                        e.setY(p.getY());
                    }
                }
            }

    public void teleport(Entity e){
        if(canPlayerUse){
            setCoords(e,subportal);
        } 
    }
    
}
