package nelsontsui.nelsonsgame.game.entities;

import nelsontsui.nelsonsgame.game.mapping.Point;
import nelsontsui.nelsonsgame.game.mapping.Hitbox;
import nelsontsui.nelsonsgame.game.entities.Entity;
import nelsontsui.nelsonsgame.game.entities.Character;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
public class NonPlayerCharacter extends Character implements Externalizable {
    
    public static final String CLASSNAME = "Non-Player Character";
    public static final String DESCRIPTION = "A solid entity that is controlled by the computer;Recommended Use: Computer Controlled Character;";
    public static final String FILENAME = "non_player_character";
    
    protected int detectionRadius;
    protected int characterClass;
    
    public static final int WARRIOR = 10;
    public static final int ARCHER = 20;
    public static final int TANK = 30;
    public static final int SUBBOSS = 40;
    public static final int BOSS = 50;
    public static final int BR_WARRIOR = 51;
    public static final int BR_ARCHER = 52;
    public static final int BR_TANK = 53;
    public static final int BR_SUBBOSS = 54;
    
    private static final long serialVersionUID = 335L;
    public NonPlayerCharacter(){
        super();
    }
    public NonPlayerCharacter(
              double x,
              double y,
              double width,
              double height,
              double travelX,
              double travelY,
              double projectileSpeed,
              String name,
              double hitpoints,
              double damage,
              int detectionRadius,
              int characterClass
              ){
        super(x,y,width,height,travelX,travelY,projectileSpeed,name,hitpoints,damage);
        this.detectionRadius = detectionRadius;
        this.characterClass = characterClass;
    }
    public int getDetectionRadius(){
        return detectionRadius;
    }
    public int getCharacterClass(){
        return characterClass;
    }
    public void setDetectionRadius(int detectionRadius){
        this.detectionRadius=detectionRadius;
    }
    public void setCharacterClass(int characterClass){
        this.characterClass = characterClass;
    }
    public Hitbox getDetectionBox(){
        Hitbox h = this.getHitbox();
        Point close = new Point((h.close.x-detectionRadius),(h.close.y-detectionRadius));
        Point far = new Point((h.far.x+detectionRadius),(h.far.y+detectionRadius));
        return new Hitbox(close,far);   
    }
    public String characterClassAsString(int c){
        if(c==WARRIOR){
            return "Warrior";
        }
        else if(c==ARCHER){
            return "Archer";
        }
        else if(c==TANK){
            return "Tank";
        }
        else if(c==SUBBOSS){
            return "Subboss";
        }
        else if(c==BOSS){
            return "Boss";
        }
        else if(c==BR_WARRIOR){
            return "Bossroom-Warrior";
        }
        else if(c==BR_ARCHER){
            return "Bossroom-Archer";
        }
        else if(c==BR_TANK){
            return "Bossroom-Tank";
        }
        else if(c==BR_SUBBOSS){
            return "Bossroom-Subboss";
        }
        else{
            return "INVALID CLASS";
        }
    }
    
    public void move(ArrayList<Entity> entities, Player player){
        if(canAct(player)){
            if(!this.getHitbox().isTouching(player.getHitbox())){
                if(player.getHitbox().isStrictlyLeft(this.getHitbox())
                        &&canMoveLeft(entities)
                        &&withinPanelX()!=Hitbox.RIGHT){
                    moveLeft();//moveLeft
                }
                if(player.getHitbox().isStrictlyRight(this.getHitbox())//isLeft
                        &&canMoveRight(entities)
                        &&withinPanelX()!=Hitbox.LEFT){
                    moveRight();//moveRight
                } 
                if(player.getHitbox().isStrictlyBelow(this.getHitbox())
                        &&canMoveDown(entities)
                        &&withinPanelY()!=Hitbox.BELOW){      
                    moveDown();
                }
                if(player.getHitbox().isStrictlyAbove(this.getHitbox())
                        &&canMoveUp(entities)
                        &&withinPanelY()!=Hitbox.ABOVE){                    
                    moveUp();
                                            
                }
            }
        }
    }
    
    
    public static void checkMove(ArrayList<Entity> entities, Player player){
        for(int i=0;i<entities.size();i++){
            if(entities.get(i) instanceof NonPlayerCharacter){
                ((NonPlayerCharacter)entities.get(i)).move(entities, player);
            }
        }
    }    
    
    public boolean canAct(Player player){
        return (player.getHitbox().isTouching(
            this.getDetectionBox()));
    }
    
    public boolean canFireProjectile(Player player){
        if((this).characterClass == NonPlayerCharacter.ARCHER
                ||(this).characterClass == NonPlayerCharacter.BOSS
                ||(this).characterClass == NonPlayerCharacter.SUBBOSS
                ||(this).characterClass == NonPlayerCharacter.BR_ARCHER
                ||(this).characterClass == NonPlayerCharacter.BR_SUBBOSS){
            if((this).getDetectionBox().isTouching(player.getHitbox())){
                return true;
            }    
        }
        return false;        
    }
    
    public void fireProjectile(Player player){
        if(this.canFireProjectile(player)){
            if(this.getHitbox().isStrictlyAbove(player.getHitbox())
                    &&(this).canFireNextProjectile()){
                (this).loadProjectile(Entity.DOWN);
            }
            else if(this.getHitbox().isStrictlyLeft(player.getHitbox())
                    &&(this).canFireNextProjectile()){
                (this).loadProjectile(Entity.RIGHT);
            }
            else if(this.getHitbox().isStrictlyRight(player.getHitbox())
                    &&(this).canFireNextProjectile()){
                (this).loadProjectile(Entity.LEFT);                    
            }
            else if(this.getHitbox().isStrictlyBelow(player.getHitbox())
                    &&(this).canFireNextProjectile()){
                (this).loadProjectile(Entity.UP);
            }
        }        
    }
    
    public static void checkFireProjectile(ArrayList<Entity> entities, Player player){
        for(int i=0;i<entities.size();i++){
            if(entities.get(i) instanceof NonPlayerCharacter){
                ((NonPlayerCharacter)entities.get(i)).fireProjectile(player);
            }
        }
    }
    
    public void projectileWithinPanel(){
        if(!(this).getProjectile().isEmpty()){
            if(withinPanel((this).getProjectile().get(0))){
                (this).getProjectile().get(0).fire();
            }
            else{
                (this).removeProjectile();
            }
        }
    }
    
    public static void checkProjectileWithinPanel(ArrayList<Entity> entities){
        for(int i=0;i<entities.size();i++){
            if(entities.get(i) instanceof NonPlayerCharacter){
                ((NonPlayerCharacter)entities.get(i)).projectileWithinPanel();
            }
        }
    }
    
    public void projectileInflictDamage(Player player, ArrayList<Entity> entities){         
        if(!((this).getProjectile().isEmpty())){
            if((this).getProjectile().get(0).getHitbox().isTouching(player.getHitbox())){
                    (this).removeProjectile();
                    (this).attack(player);
            }
            else{
                for(int j=0;j<entities.size();j++){
                    if(this.equals(entities.get(j))){}
                    else{                    
                        if(entities.get(j) instanceof OpaqueEntity
                                &&(this).getProjectile().get(0).getHitbox().isTouching((entities.get(j)).getHitbox())){
                            (this).removeProjectile();
                            break;
                        }                        
                    }
                }
            }
        }
    } 
    
    public static void checkProjectileInflictDamage(Player player, ArrayList<Entity> entities){
        for(int i=0;i<entities.size();i++){
            Entity ent = entities.get(i);
            if(ent instanceof NonPlayerCharacter){
                ((NonPlayerCharacter)ent).projectileInflictDamage(player, entities);
            }
        }
    }
    
    public static void detectForDamage(ArrayList<Entity> entities, Player player){
        for(int i=0;i<entities.size();i++){
            if(entities.get(i) instanceof NonPlayerCharacter){
                if(player.getHitbox().isTouching(entities.get(i).getHitbox())){
                    if(((NonPlayerCharacter)entities.get(i)).getCharacterClass()!=NonPlayerCharacter.ARCHER){
                        ((NonPlayerCharacter)entities.get(i)).attack(player);
                    }                    
                }                
            }
        }
    }
    
    
    public String className(){
        return CLASSNAME;
    }
    
    public String description(){
        return DESCRIPTION;
    }
    
    public String instanceDescription(){
        return DESCRIPTION+characterClassAsString(characterClass);
    }
    
    @Override
    public String toString(){
        return "CharacterClass: "+characterClassAsString(characterClass)+
                "; DetectionRadius: "+detectionRadius+"; "+super.toString();
    }
    
    public boolean equals(NonPlayerCharacter n){
        return getHitbox().equals(n.getHitbox()) 
                && characterClass == n.characterClass 
                && detectionRadius == n.detectionRadius
                && getName().equals(n.getName());
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        
        out.writeInt(detectionRadius);      
        out.writeInt(characterClass);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        
        detectionRadius = in.readInt();
        characterClass = in.readInt();
    }
    
    public void render(Graphics2D graphic){
        if(((this.getCharacterClass()==NonPlayerCharacter.WARRIOR)
                ||this.getCharacterClass()==NonPlayerCharacter.BR_WARRIOR)){
            graphic.setColor(new Color(156,34,34));//dark red
        }
        else if(((this.getCharacterClass()==NonPlayerCharacter.ARCHER)
                ||this.getCharacterClass()==NonPlayerCharacter.BR_ARCHER)){
            graphic.setColor(new Color(176,176,75));//snakeskin
        }
        else if(((this.getCharacterClass()==NonPlayerCharacter.TANK)
            ||this.getCharacterClass()==NonPlayerCharacter.BR_TANK)){
            graphic.setColor(new Color(92,90,19));//camogreen
        }
        else if(((this.getCharacterClass()==NonPlayerCharacter.SUBBOSS)
                ||this.getCharacterClass()==NonPlayerCharacter.BR_SUBBOSS)){
            graphic.setColor(new Color(85,18,105));//dark purple
        }
        else if(this.getCharacterClass()==NonPlayerCharacter.BOSS){
            graphic.setColor(new Color(0,0,0));//black
        }
        changeColorWhenHit(graphic,this);
        graphic.fill(new Rectangle2D.Double(this.getX(),this.getY(),this.getWidth(),this.getHeight()));
        
        if(!this.getProjectile().isEmpty()){
            for(int z=0;z<this.getProjectile().size();z++){
                Projectile p = this.getProjectile().get(0);
                graphic.fill(new Ellipse2D.Double(p.x,p.y,p.width,p.height));
            }
        }
        
        if(!this.getProjectile().isEmpty()){
            for(int z=0;z<this.getProjectile().size();z++){
                this.getProjectile().get(z).render(graphic, graphic.getColor());
            }
        }
                    
        drawHitpointsBar(graphic,this);
    }
    
    
    
    
   
}
