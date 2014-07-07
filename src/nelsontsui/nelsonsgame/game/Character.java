package nelsontsui.nelsonsgame.game;

import java.util.ArrayList;
public class Character extends DamagableEntity{
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
        super.hitpoints = initHitpoints+getArmorHitpoints();
        damage = initDamage+getWeaponDamage();
        level = initDamage+initHitpoints;
        DROP_DISTANCE_X = width+5;
        DROP_DISTANCE_Y = height+5;
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
    public void attack(DamagableEntity other){
        if(this.hasWeapon){
            if(!(this.weapon instanceof Bow)){
                int d = (int)(Math.random()*this.damage);
                other.hurt(d);            
            }
        }
        else{
            int d = (int)(Math.random()*this.damage);
            other.hurt(d); 
        }
    }
    public void shoot(DamagableEntity other){
        if(this.hasWeapon){
            if(this.weapon instanceof Bow){
                int d = (int)(Math.random()*this.damage);
                other.hurt(d);        
            }
        }
    }
    public void gainHitpoint(){
        hitpoints++;
    }
    public void gainDamage(){
        damage++;
    }
    public Projectile loadProjectile(int direction){
        projectile.add(new Projectile(x,y,width/3,height/3,projectileSpeed,direction)); 
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
   
}
