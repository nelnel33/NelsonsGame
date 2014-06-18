package nelsontsui.nelsonsgame.game;

import java.util.ArrayList;

public class Portal extends Entity{
    private boolean isTwoWay;
    private Entity subportal;
    protected boolean isMain;
    private boolean canNpcUse;
    public static int offsetDistance=5;//must be greater than 1;
    
    
    //public static int linkerIndex=0;
    
    public Portal(//to create subportals
            double x,
            double y,
            double width,
            double height,
            Entity subportal
            ){
        super(x,y,width,height);
        this.subportal = subportal;
        this.canNpcUse = false;
        this.isTwoWay = false;
        this.isMain = false;
        
        //linkerIndex++;
    }
    public Portal(//to create mainportals
            double x,
            double y,
            double width,
            double height,
            Portal subportal
            ){
        super(x,y,width,height);
        this.subportal = subportal;
        this.canNpcUse = false;
        this.isTwoWay = true;
        this.isMain = true;
        
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
    public boolean isTwoWay(){
        return isTwoWay;
    }
    public void setSubportal(Entity subportal){
        this.subportal = subportal;
    }
    public void setTwoWay(boolean isTwoWay){
        this.isTwoWay = isTwoWay;
    }
    public ArrayList<Entity> addPortals(ArrayList<Entity> e){
        e.add(this);
        e.add(subportal);
        return e;
    }
    public void teleport(Entity e){
        if(isTwoWay){
            if(e.getHitbox().isTouching(this.getHitbox())){
                if(subportal.getX()<GameDisplay.edgeX/2){
                    e.setX(subportal.getX()+subportal.getWidth()+offsetDistance);
                    e.setY(subportal.getY());
                }
                else{
                    e.setX(subportal.getX()-subportal.getWidth()-offsetDistance);
                    e.setY(subportal.getY());
                }
            }
            /*
            else if(e.getHitbox().isTouching(subportal.getHitbox())){
                if(this.getX()<GameDisplay.edgeX/2){
                    e.setX(this.getX()+this.getWidth()+offsetDistance);
                    e.setY(subportal.getY());
                }
                else{
                    e.setX(this.getX()-this.getWidth()-offsetDistance);
                    e.setY(subportal.getY());
                }
            }  
            */
        }
        else if(isTwoWay==false){
            if(e.getHitbox().isTouching(this.getHitbox())){
                if(subportal.getX()<GameDisplay.edgeX/2){
                    e.setX(subportal.getX()+subportal.getWidth()+offsetDistance);
                    e.setY(subportal.getY());
                }
                else{
                    e.setX(subportal.getX()-subportal.getWidth()-offsetDistance);
                    e.setY(subportal.getY());
                }
            }
        }        
    }   
    
}
