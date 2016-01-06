package nelsontsui.nelsonsgame.game.entities;

import nelsontsui.nelsonsgame.game.entities.helper.Inventory;
import nelsontsui.nelsonsgame.game.mapping.Hitbox;
import nelsontsui.nelsonsgame.game.entities.Entity;
import nelsontsui.nelsonsgame.game.entities.Character;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import javax.swing.JLabel;
import nelsontsui.nelsonsgame.game.ActionPanel;
import nelsontsui.nelsonsgame.game.DialogBox;
import nelsontsui.nelsonsgame.game.GameDisplay;
import nelsontsui.nelsonsgame.game.InventoryIcon;
import nelsontsui.nelsonsgame.game.items.Ammo;
import nelsontsui.nelsonsgame.game.items.Armor;
import nelsontsui.nelsonsgame.game.items.HealthPotion;
import nelsontsui.nelsonsgame.game.items.ProjectileWeapon;
import nelsontsui.nelsonsgame.game.items.UnusableItem;
import nelsontsui.nelsonsgame.game.items.Weapon;

/**
 *
 * @author Nelnel33, Alan T
 */
public class Player extends Character implements Externalizable{
    public static final String CLASSNAME = "Player";
    public static final String DESCRIPTION = "A solid entity that has health and damage;Recommended Use: Player";
    public static final String FILENAME = "player";

    public Player() {
        super();
    }

    public Player(
            double x,
            double y,
            double width,
            double height,
            double travelX,
            double travelY,
            double projectileSpeed,
            String name,
            double initHitpoints,
            double initDamage){
        super(x, y, width, height, travelX, travelY, projectileSpeed, name, initHitpoints, initDamage);
    }
    
    public void checkForItems(ArrayList<Entity> entities, DialogBox dialogPanel){
        for(int i=0;i<entities.size();i++){
            if(entities.get(i) instanceof SpawnableItem){
                if(this.getHitbox().isTouching(entities.get(i).getHitbox())
                        &&this.inventory.getSize()<Inventory.MAX_SIZE){
                    this.inventory.pickUpItem(((SpawnableItem)entities.get(i)).getItems());
                    dialogPanel.message(this.getName()+" picked up: "+((SpawnableItem)entities.get(i)).getItems().getQuantity()+" "+((SpawnableItem)entities.get(i)).getItems().getName());
                    entities.remove(i);
                    for(int j=0;j<this.inventory.getItems().size();j++){
                    }
                }
            }
        }
    }
    
    public void checkForInventory(InventoryIcon[] inventoryItems, DialogBox dialogPanel, ArrayList<Entity> entities){
        if((!this.inventory.getItems().isEmpty())){
            for(int i=0;i<this.inventory.getItems().size();i++){
                if(inventoryItems.length==0){break;}
                else if(inventoryItems[i].isButton1()){
                    use(i, dialogPanel);
                }
                else if(inventoryItems[i].isButton2()){
                    drop(i, dialogPanel, entities);                    
                }                
            }
        }
    }
    
    public void checkHasBeenFired(){
        if(!this.getProjectile().isEmpty()){
            for(int i=0;i<this.getProjectile().size();i++){
                if(this.getProjectile().get(i).hasBeenFired()){
                    this.getProjectile().get(i).fire();
                }
            }
        }
    }
    
    public void regenOverTime(int amount){
        if(this.getHitpoints()<this.getinitHitpoints()){
            this.heal(amount);       
        }
    } 
    
    public void up(ArrayList<Entity> entities){
        if((withinPanelY(this)!=Hitbox.ABOVE)&&
                canMoveUp(entities)){
            this.moveUp();
        } 
        this.setDirection(Entity.UP);
    }
    public void down(ArrayList<Entity> entities){
        if(((withinPanelY(this)!=Hitbox.BELOW)&&
                canMoveDown(entities))){
            this.moveDown();
        }    
        this.setDirection(Entity.DOWN);
    }
    public void left(ArrayList<Entity> entities){
        if(((withinPanelX(this)!=Hitbox.LEFT)&&
                canMoveLeft(entities))){
            this.moveLeft();
        }        
        this.setDirection(Entity.LEFT);
    }
    public void right(ArrayList<Entity> entities){
        if(((withinPanelX(this)!=Hitbox.RIGHT)&&
                canMoveRight(entities))){
            this.moveRight();
        } 
        this.setDirection(Entity.RIGHT);
    }
    public void shoot(){
        boolean hasBow = false;
        boolean hasArrow = false;
        boolean isCompatible = false;
        int index=100;//Arbitrary number just to check.
        
        for(int i=0;i<this.inventory.getItems().size();i++){
            if(this.getHasWeapon()){
                if(this.inventory.getItems().get(i) instanceof ProjectileWeapon){
                    if(this.weapon.equals(this.inventory.getItems().get(i))){
                        hasBow = true;
                    }
                }
                if(this.inventory.getItems().get(i) instanceof Ammo){
                    hasArrow = true;
                    index = i;                
                    if(hasArrow
                            &&(this.weapon instanceof ProjectileWeapon)
                            &&(this.inventory.getItems().get(i) instanceof Ammo)){
                        isCompatible = ((ProjectileWeapon)this.weapon).isCompatible((Ammo)this.inventory.getItems().get(i));
                    }
                }
                if(hasBow&&hasArrow){
                    break;
                }
            }
        }
        
        if(hasBow&&(index!=100)&&isCompatible){
            this.loadProjectile(this.getDirection());
            this.inventory.useItem(index,this);
            for(int i=0;i<this.getProjectile().size();i++){
                this.getProjectile().get(i).fire();
            }
        }
    }
    public void attack(ArrayList<Entity> entities, DialogBox dialogPanel){
        inflictDamage(entities, dialogPanel);
    }
    public void defend(){
        
    }
    public void heal(){
        for(int i=0;i<this.inventory.getItems().size();i++){
            if(this.inventory.getItems().get(i) instanceof HealthPotion){
                this.inventory.useItem(i,this);
            }
        }        
    }
    public void use(int i, DialogBox dialogPanel){
        if(this.inventory.getItems().get(i) instanceof UnusableItem){
            if(this.inventory.getItems().get(i) instanceof Weapon){
                if(this.getHasWeapon()&&(this.weapon.equals((Weapon)this.inventory.getItems().get(i)))){
                    dialogPanel.message(this.getName()+" unequip: "+this.weapon.getName());
                    this.unequipWeapon((Weapon)this.inventory.getItems().get(i));                   
                }
                else if(this.getHasWeapon()){
                    dialogPanel.message(this.getName()+" unequip: "+this.weapon.getName());
                    this.unequipWeapon(this.weapon);
                    this.equipWeapon((Weapon)this.inventory.getItems().get(i));
                    dialogPanel.message(this.getName()+" equip: "+this.inventory.getItems().get(i).getName());
                }
                else{
                    this.equipWeapon((Weapon)this.inventory.getItems().get(i));  
                    dialogPanel.message(this.getName()+" equip: "+this.weapon.getName());
                }
            }
            if(this.inventory.getItems().get(i) instanceof Armor){
                if(this.getHasArmor()&&(this.armor.equals((Armor)this.inventory.getItems().get(i)))){
                    dialogPanel.message(this.getName()+" unequip: "+this.armor.getName());
                    this.unequipArmor((Armor)this.inventory.getItems().get(i));                    
                }
                else if(this.getHasArmor()){
                    dialogPanel.message(this.getName()+" unequip: "+this.armor.getName());
                    this.unequipArmor(this.armor);
                    this.equipArmor((Armor)this.inventory.getItems().get(i));
                    dialogPanel.message(this.getName()+" equip: "+this.inventory.getItems().get(i).getName());
                }
                else{
                    this.equipArmor((Armor)this.inventory.getItems().get(i));
                    dialogPanel.message(this.getName()+" equip: "+this.armor.getName());
                }
            }
        } 
        else {
            dialogPanel.message(this.getName()+" use: "+this.inventory.getItems().get(i).getName());
            this.inventory.useItem(i,this);
        }
    }
    
    public void useKey(DialogBox dialogPanel){
        if(!this.inventory.getItems().isEmpty()){
            for(int i=0;i<this.inventory.getItems().size();i++){
                if(Inventory.inventorySelectorIndex==i){
                    this.use(i, dialogPanel);
                }
            }
        } 
    }
    
    public void drop(int i, DialogBox dialogPanel, ArrayList<Entity> entities){
        if(this.inventory.getItems().get(i) instanceof UnusableItem){
            if(this.inventory.getItems().get(i) instanceof Weapon){
                if(this.getHasWeapon()&&(this.weapon.equals((Weapon)this.inventory.getItems().get(i)))){
                   dialogPanel.message(this.getName()+" unequip: "+this.weapon.getName());
                   this.unequipWeapon((Weapon)this.inventory.getItems().get(i));
                }
            }
            else if(this.inventory.getItems().get(i) instanceof Armor){
                if(this.getHasArmor()&&(this.armor.equals((Armor)this.inventory.getItems().get(i)))){
                   dialogPanel.message(this.getName()+" unequip: "+this.armor.getName());
                   this.unequipArmor((Armor)this.inventory.getItems().get(i)); 
                }
            }
        }        
        
        dialogPanel.message(this.getName()+" drop: "+this.inventory.getItems().get(i).getQuantity()+" "+this.inventory.getItems().get(i).getName());
        
        if(this.getX()<GameDisplay.ACTIONPANEL_WIDTH/2){
            SpawnableItem temp = this.inventory.dropItem(i, this.getX()+this.DROP_DISTANCE_X, this.getY());  
            entities.add(temp);
        }
        else{
            SpawnableItem temp = this.inventory.dropItem(i, this.getX()-this.DROP_DISTANCE_X, this.getY());                    
            entities.add(temp);
        }       
    }  
    
    public void dropKey(ArrayList<Entity> entities, DialogBox dialogPanel){
        if(!this.inventory.getItems().isEmpty()){
            for(int i=0;i<this.inventory.getItems().size();i++){
                if(Inventory.inventorySelectorIndex==i){
                    this.drop(i, dialogPanel, entities);                   
                }
            }
        }
    }
    
    public void setInventoryIcons(JLabel[] inventoryLabels, ActionPanel activePanel, InventoryIcon[] inventoryItems){
        if((this!=null)&&(!this.inventory.getItems().isEmpty())){
            for(int i=0;i<this.inventory.getItems().size();i++){
                if(this.inventory.getItems().get(i).getQuantity()>0){
                    if(this.inventory.getItems().get(i) instanceof Armor){
                        if(((Armor)this.inventory.getItems().get(i)).getEquipped()){
                            inventoryLabels[i].setText("<html><b>"+this.inventory.getItems().get(i).getAbbreviation()+"<br>"+this.inventory.getItems().get(i).getQuantity()+"<b><html>");
                        }
                        else{
                            inventoryLabels[i].setText("<html>"+this.inventory.getItems().get(i).getAbbreviation()+"<br>"+this.inventory.getItems().get(i).getQuantity()+"<html>");
                        }
                    }
                else if(this.inventory.getItems().get(i) instanceof Weapon){
                    if(((Weapon)this.inventory.getItems().get(i)).getEquipped()){
                        inventoryLabels[i].setText("<html><b>"+this.inventory.getItems().get(i).getAbbreviation()+"<br>"+this.inventory.getItems().get(i).getQuantity()+"<b><html>");
                    }
                    else{
                        inventoryLabels[i].setText("<html>"+this.inventory.getItems().get(i).getAbbreviation()+"<br>"+this.inventory.getItems().get(i).getQuantity()+"<html>");
                    }
                }
                else{
                    inventoryLabels[i].setText("<html>"+this.inventory.getItems().get(i).getAbbreviation()+"<br>"+this.inventory.getItems().get(i).getQuantity()+"<html>");
                    }
                }
            }
        }
    activePanel.setInventoryItems(inventoryItems);
    }   
    
    public void inventoryRemoveIcons(JLabel[] inventoryLabels){
        if(this!=null){
            int listSize = this.inventory.getItems().size();
            for(int i=listSize;i<Inventory.MAX_SIZE;i++){
                inventoryLabels[i].setText(null);
            }
        }
    }
    public void inventorySetToolTipText(InventoryIcon[] inventoryItems){
        for(int i=0;i<this.inventory.getItems().size();i++){
            inventoryItems[i].setToolTipText(this.inventory.getItems().get(i).getName());
        }
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);       
    }
    
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
    }
    
    @Override
    public void render(Graphics2D graphic){
        //Draw Player and Player's Projectiles
        graphic.setColor(new Color(63,131,104));//Cyanish Green
        graphic.fill(new Rectangle2D.Double(this.getX(),this.getY(),this.getWidth(),this.getHeight()));
        drawHitpointsBar(graphic,this);     
                
        if(!this.getProjectile().isEmpty()){
            for(int z=0;z<this.getProjectile().size();z++){
                this.getProjectile().get(z).render(graphic, new Color(63,131,104));
            }
        }
    }
    
}
