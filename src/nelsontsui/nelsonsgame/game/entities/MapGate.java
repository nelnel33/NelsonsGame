/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import nelsontsui.nelsonsgame.game.ActionPanel;
import nelsontsui.nelsonsgame.game.DialogBox;
import nelsontsui.nelsonsgame.game.MainDisplay;
import nelsontsui.nelsonsgame.leveleditor.ReadFile;

public class MapGate extends Entity implements Externalizable{
    
    public static final String CLASSNAME = "Map Gate";
    public static final String DESCRIPTION = "A non-solid object that is used to exit the map or display that you have won;"
                + "Recommended Use: Level linker;";
    
    private int useCondition;
    private boolean canUse;
    
    private File file;
    private boolean usedToExitMap;
    
    private boolean done = false;
    
    public static final int KILL_ALL = 0;
    public static final int REACH_GATE = 1;
    
    private static final long serialVersionUID = 3311L;
    
    public MapGate(){
        super();
    }
    public MapGate(double x, double y, double width, double height, int useCondition) {
        super(x, y, width, height);
        this.useCondition = useCondition;
        usedToExitMap = false;
        assignCanUse();
    }
    public boolean usedToExitMap(){
        return usedToExitMap;
    }
    public void setFile(File file){
        if(file!=null){
            this.file = file;        
            usedToExitMap = true;
        }
    }
    public File getFile(){
        return file;
    }
    public void removeFile(){
        this.file = null;
        usedToExitMap = false;
    }
    private void assignCanUse(){
        if(useCondition==REACH_GATE){
            canUse = true;
        }
        else{
            canUse = false;
        }
    }
    public void checkIfCanUse(ArrayList<Entity> e){
        if(useCondition==KILL_ALL){
            for(int i=0;i<e.size();i++){
                if(e.get(i) instanceof NonPlayerCharacter){
                    canUse = false;
                    return;
                }
            }
            canUse=true;
        }
    }
    public boolean canOperate(Entity e){
        if((e.getHitbox().isTouching(this.getHitbox()))&&canUse){
            return true;
        }
        else{
            return false;
        }
    }
    public void operate(Entity e, ActionPanel action){
        if((e.getHitbox().isTouching(this.getHitbox())) && canUse && !done){
            setOperation(action);
            done = true;
        }
    }
     public void operate(Entity e, DialogBox dialog){
        if((e.getHitbox().isTouching(this.getHitbox())) && canUse && !done){
            setOperation(dialog);
            done = true;
        }
    }
    public void setOperation(ActionPanel action){
        if(usedToExitMap && (file!=null) ){
            ReadFile reader = new ReadFile(file);
            reader.read();
            ArrayList<Entity> entities = reader.getEntities();
            Player player = reader.getPlayer();
            if(player != null && entities != null){
                action.setEntities(entities);
                action.setPlayer(player);
                System.out.println("Player and Npcs were transfered(MAPGATE)");
            }
            else{
                System.out.println("MapGate failed to transfer"); 
            }
        }
    }
    public void setOperation(DialogBox dialog){   
        dialog.message(MainDisplay.winMessageCruel);
        dialog.message(MainDisplay.genMessage);
        System.out.println("reached gate; won;");
    }
    public String useConditionAsString(int u){
        if(u==KILL_ALL){
            return "Kill-All";
        }
        else if(u==REACH_GATE){
            return "Reach-Gate";
        }
        else{
            return "INVALID USE CONDITION";
        }
    }
    
    public static void checkForMapGateOperation(ArrayList<Entity> entities, Player player, DialogBox dialogPanel, ActionPanel activePanel){
        if(!entities.isEmpty()){
            for(int i=0;i<entities.size();i++){
                if(entities.get(i) instanceof MapGate){
                    MapGate m = (MapGate)entities.get(i);
                    m.checkIfCanUse(entities);
                    if(m.usedToExitMap()){
                        m.operate(player, activePanel);
                    }
                    else{
                        m.operate(player,dialogPanel);
                    }                            
                }
            }
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
        return "UseCondition: "+useConditionAsString(useCondition)+"; CanUse: "+canUse+"; FilePath: "+file+"; usedToExitMap: "+usedToExitMap+"; "
                +super.toString();
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);   
        
        out.writeInt(useCondition);
        out.writeBoolean(canUse);
        out.writeObject(file);
        out.writeBoolean(usedToExitMap);        
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        
        useCondition = in.readInt();
        canUse = in.readBoolean();
        file = (File)in.readObject();
        usedToExitMap = in.readBoolean();
    }
    
    @Override
    public void render(Graphics2D graphic){
        graphic.setColor(Color.black);
        graphic.draw(new Ellipse2D.Double(this.getX(),this.getY(),this.getWidth(),this.getHeight()));
    }
    
}
