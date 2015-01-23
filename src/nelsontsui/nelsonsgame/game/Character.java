package nelsontsui.nelsonsgame.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import static nelsontsui.nelsonsgame.game.ActionPanel.hitpointsBarHeight;
import static nelsontsui.nelsonsgame.game.ActionPanel.hitpointsBarOffset;
import nelsontsui.nelsonsgame.game.items.Armor;
import nelsontsui.nelsonsgame.game.items.Item;
import nelsontsui.nelsonsgame.game.items.ProjectileWeapon;
import nelsontsui.nelsonsgame.game.items.Weapon;

public class Character extends DamagableEntity implements Externalizable{
    
    public static final String CLASSNAME = "Character";
    public static final String DESCRIPTION = "A solid entity that has health and damage;Recommended Use: Player";
    
    private String name;    
    private double initDamage;
    private double damage;
    private double travelX;
    private double travelY;
    private double projectileSpeed;    
    protected Inventory inventory = new Inventory();
    
    protected Weapon weapon;
    protected Armor armor;
    protected double level;
    
    private ArrayList<Projectile> projectile = new ArrayList<>();
    private boolean canFireNextProjectile = true;
            
    private boolean hasArmor=false;
    private boolean hasWeapon=false;
    
    public double DROP_DISTANCE_X;
    public double DROP_DISTANCE_Y;
    
    public static final int DROP_DISTANCE = 5;
    public static final int PROJECTILE_SIZE = 5;
    
    private static final long serialVersionUID = 334L;
    
    public Character(){
        super();
        //init();
    }
    public Character(
              double x,
              double y,
              double width,
              double height,
              double travelX,
              double travelY,
              double projectileSpeed,
              String name,
              double initHitpoints,
              double initDamage
              ){
        super(x,y,width,height,initHitpoints);
        this.travelX = travelX*ActionPanel.DELTA;
        this.travelY = travelY*ActionPanel.DELTA;
        this.projectileSpeed = projectileSpeed*ActionPanel.DELTA;
        this.name=name;
        super.initHitpoints=initHitpoints;
        this.initDamage=initDamage;
        init();
    }
    private void init(){
        super.hitpoints = initHitpoints+getArmorHitpoints();
        damage = initDamage+getWeaponDamage();
        level = initDamage+initHitpoints;
        DROP_DISTANCE_X = width+DROP_DISTANCE;
        DROP_DISTANCE_Y = height+DROP_DISTANCE;
    }
    public double getTravelX(){
        return travelX;
    }
    public double getTravelY(){
        return travelY;
    }
    public String getName(){//
        return name;
    }
    public double getinitDamage(){
        return initDamage;
    }
    public double getDamage(){
        return damage;
    }
    public double getWeaponDamage(){
        if(hasWeapon){
            return weapon.getDamage();
        }
        else{
            return 0;
        }
    }
    public double getArmorHitpoints(){
        if(hasArmor){
            return armor.getProtection();
        }
        else{
            return 0;
        }
        
    }
    public double getLevel(){
        return level;
    }
    public ArrayList<Projectile> getProjectile(){
        return projectile;
    }
    public Inventory getInventory(){
        return inventory;
    }
    public boolean getHasArmor(){
        return hasArmor;
    }
    public boolean getHasWeapon(){
        return hasWeapon;
    }    
    public void setName(String name){
        this.name = name;
    }
    public void setDamage(double damage){
        this.damage = damage;
    }
    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }
    public void addToInventory(Item item){
        this.inventory.addToInventory(item);
    }
    public boolean attack(DamagableEntity other){
        if(this.hasWeapon){
            if(!(this.weapon instanceof ProjectileWeapon)){
                int d = (int)(Math.random()*this.damage);
                other.hurt(d); 
                return false;
            }
        }
        else{
            int d = (int)(Math.random()*this.damage);
            other.hurt(d); 
        }
        other.setHasBeenHit(true);
        return true;
    }
    public void shoot(DamagableEntity other){
        if(this.hasWeapon){
            if(this.weapon instanceof ProjectileWeapon){
                int d = (int)(Math.random()*this.damage);
                other.hurt(d);        
            }
            other.setHasBeenHit(true);
        }
    }
    public void gainHitpoint(){
        hitpoints++;
    }
    public void gainDamage(){
        damage++;
    }
    public Projectile loadProjectile(int direction){
        projectile.add(new Projectile(x,y,PROJECTILE_SIZE,PROJECTILE_SIZE,projectileSpeed,direction)); 
        canFireNextProjectile=false;
        return projectile.get(projectile.size()-1);
    }
    public void removeProjectile(){
        projectile.remove(0);
        canFireNextProjectile=true;
    }
    public boolean canFireNextProjectile(){
        return canFireNextProjectile;
    }
    public void setCanFireNextProjectile(boolean t){
        canFireNextProjectile = t;
    }
    public void equipArmor(Armor armor){
        if(hasArmor==false){
            this.armor = armor;
            double addedProtection = armor.getProtection();
            hitpoints+=addedProtection;
            hasArmor=true;
            armor.setEquipped(true);
        }
    }
    public void unequipArmor(Armor armor){
        if(hasArmor==true){
            this.armor = null;
            double addedProtection = armor.getProtection();
            hitpoints-=addedProtection;
            hasArmor=false;
            armor.setEquipped(false);
        }
    }
    public void equipWeapon(Weapon weapon){
        if(hasWeapon==false){
            this.weapon = weapon;
            double addedDamage = weapon.getDamage();
            damage+=addedDamage;
            hasWeapon=true;
            weapon.setEquipped(true);
        }
    }
    public void unequipWeapon(Weapon weapon){
        if(hasWeapon==true){
            this.weapon = null;
            double addedDamage = weapon.getDamage();
            damage-=addedDamage;
            hasWeapon=false;
            weapon.setEquipped(false);
        }
    }
    
    public void moveUp(){
        this.y-=travelY;
    }
    public void moveDown(){
        this.y+=travelY;
    }
    public void moveLeft(){
        this.x-=travelX;
    }
    public void moveRight(){
        this.x+=travelX;
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
        return "Name: "+name+"; "+super.toString()+" Damage: "+damage+"; Speed: ("+travelX*ActionPanel.INVERSE_DELTA+","+travelY*ActionPanel.INVERSE_DELTA+"); ProjectileSpeed: "+projectileSpeed*ActionPanel.INVERSE_DELTA+";";
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        
        out.writeObject(name);
        out.writeDouble(initDamage);
        out.writeDouble(damage);
        out.writeDouble(travelX);
        out.writeDouble(travelY);
        out.writeDouble(projectileSpeed);
        out.writeObject(inventory);        
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        
        name = (String)in.readObject();
        initDamage = in.readDouble();
        damage = in.readDouble();
        travelX = in.readDouble();
        travelY = in.readDouble();
        projectileSpeed = in.readDouble();
        inventory = (Inventory)in.readObject();
        init();
    }
    
    @Override
    public void render(Graphics2D graphic){
        //Draw player and player's Projectiles
        graphic.setColor(new Color(63,131,104));//Cyanish Green
        graphic.fill(new Rectangle2D.Double(this.getX(),this.getY(),this.getWidth(),this.getHeight()));
        drawHitpointsBar(graphic,this);     
                
        if(!this.getProjectile().isEmpty()){
            for(int z=0;z<this.getProjectile().size();z++){
                this.getProjectile().get(z).render(graphic, new Color(63,131,104));
            }
        }
    }
    
    public void drawHitpointsBar(Graphics2D g, Character d){
        double ratio = d.getHitpoints()/d.getinitHitpoints();
        double hitpointsBarWidth = d.getWidth()*ratio;
        g.setColor(Color.GREEN);
        if(d.getY()>(GameDisplay.ACTIONPANEL_HEIGHT/2)){
            g.fill(new Rectangle2D.Double(
                    d.getX(),d.getHitbox().close.y-hitpointsBarOffset,hitpointsBarWidth,hitpointsBarHeight));
            }
        else{
            g.fill(new Rectangle2D.Double(
                    d.getX(),d.getHitbox().far.y+hitpointsBarOffset,hitpointsBarWidth,hitpointsBarHeight));
        }
    }
   
}
