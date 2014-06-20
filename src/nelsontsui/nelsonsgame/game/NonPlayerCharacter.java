/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.game;

/**
 *
 * @author Nelnel33
 */
public class NonPlayerCharacter extends Character {
    protected int detectionRadius;
    protected int characterClass;
    
    public static final int WARRIOR = 10;
    public static final int ARCHER = 20;
    public static final int TANK = 30;
    public static final int SUBBOSS = 40;
    public static final int BOSS = 50;
    
    public NonPlayerCharacter(){
        detectionRadius = 30;
        characterClass = 1;
        
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
   
}
