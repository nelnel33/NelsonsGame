package nelsontsui.nelsonsgame.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/*
*NPCS attack once a second
*Player is independent of FPS
*/

public class ActionPanel extends JPanel implements ActionListener, KeyListener{
    public static final double FPS = 50;
    public static final double TICK = 1000.0/FPS;
    public static final double DELTA = TICK/1000.0;
    public long frameCount = 0;
    
    //protected Timer check = new Timer(TICK,this);
    
    protected Character Player;
    protected ArrayList<Entity> npcs = new ArrayList<>();
    private int edgeX;
    private int edgeY;
    
    private InventoryIcon[] inventoryItems = new InventoryIcon[0];//only declared to prevent nullpointerexception
    private DialogBox dialogPanel;
    
    public static final int hitpointsBarOffset = 6;
    public static final int hitpointsBarHeight = 3;
            
    protected boolean[] keyPressed = new boolean[12];
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int ATTACK = 4;
    public static final int SHOOT = 5;
    public static final int DEFEND = 6;
    public static final int HEAL = 7;
    public static final int SELECTFOR = 8;
    public static final int SELECTBACK = 9;
    public static final int USE = 10;
    public static final int DROP = 11;
    public ActionPanel(Character Player, ArrayList<Entity> npcs, int edgeX, int edgeY){
        this.Player = Player;
        this.npcs = npcs;
        this.edgeX = edgeX;
        this.edgeY = edgeY; 
        
        //check.start();
        setSize(this.edgeX,this.edgeY);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    public void setInventoryItems(InventoryIcon[] inventoryItems){
        this.inventoryItems = inventoryItems;
    }
    public void setDialogPanel(DialogBox dialogPanel){
        this.dialogPanel = dialogPanel;
    }
    public void npcMove(){
        for(int i=0;i<npcs.size();i++){
            if(npcs.get(i) instanceof NonPlayerCharacter){
                if(canNpcAct((NonPlayerCharacter)npcs.get(i))){
                    if(!npcs.get(i).getHitbox().isTouching(Player.getHitbox())){
                        if(Player.getHitbox().isBelow(npcs.get(i).getHitbox())
                                &&canNpcMoveDown(npcs.get(i))
                                &&withinPanelY(npcs.get(i))!=Hitbox.BELOW){
                            npcs.get(i).setY(npcs.get(i).getY()+((NonPlayerCharacter)npcs.get(i)).getTravelY());                        
                        }
                        if(Player.getHitbox().isAbove(npcs.get(i).getHitbox())
                                &&canNpcMoveUp(npcs.get(i))
                                &&withinPanelY(npcs.get(i))!=Hitbox.ABOVE){
                            npcs.get(i).setY(npcs.get(i).getY()-((NonPlayerCharacter)npcs.get(i)).getTravelY());
                        }
                        if(Player.getHitbox().isRight(npcs.get(i).getHitbox())
                                &&canNpcMoveRight(npcs.get(i))
                                &&withinPanelY(npcs.get(i))!=Hitbox.RIGHT){
                            npcs.get(i).setX(npcs.get(i).getX()-((NonPlayerCharacter)npcs.get(i)).getTravelX());
                        }
                        if(Player.getHitbox().isLeft(npcs.get(i).getHitbox())
                                &&canNpcMoveLeft(npcs.get(i))
                                &&withinPanelY(npcs.get(i))!=Hitbox.LEFT){
                            npcs.get(i).setX(npcs.get(i).getX()+((NonPlayerCharacter)npcs.get(i)).getTravelX());
                        }                        
                    }
                }
            }
        }
    }
    public boolean canNpcMoveUp(Entity e){        
        for(int i=0;i<npcs.size();i++){
            if(e.equals(npcs.get(i))){}
            else{
                if(npcs.get(i) instanceof OpaqueEntity){
                    if(e.getHitbox().isTouching(npcs.get(i).getHitbox())
                       &&npcs.get(i).getHitbox().isAbove(e.getHitbox())){
                    return false;
                    } 
                }
            }
        }
        return true;
    }
    public boolean canNpcMoveDown(Entity e){
        for(int i=0;i<npcs.size();i++){
            if(npcs.get(i).equals(e)){}
            else{
                if(npcs.get(i) instanceof OpaqueEntity){
                    if(e.getHitbox().isTouching(npcs.get(i).getHitbox())
                       &&npcs.get(i).getHitbox().isBelow(e.getHitbox())){
                    return false;
                    } 
                }
            }
        }
        return true;
    }
    public boolean canNpcMoveLeft(Entity e){              
        for(int i=0;i<npcs.size();i++){
            if(npcs.get(i).equals(e)){}
            else{
                if(npcs.get(i) instanceof OpaqueEntity){
                    if(e.getHitbox().isTouching(npcs.get(i).getHitbox())
                       &&npcs.get(i).getHitbox().isLeft(e.getHitbox())){
                    return false;
                    } 
                }
            }
        }
        return true;
    }
    public boolean canNpcMoveRight(Entity e){
        for(int i=0;i<npcs.size();i++){
            if(npcs.get(i).equals(e)){}
            else{
                if(npcs.get(i) instanceof OpaqueEntity){
                    if(e.getHitbox().isTouching(npcs.get(i).getHitbox())
                       &&npcs.get(i).getHitbox().isRight(e.getHitbox())){
                    return false;
                    } 
                }
            }
        }
        return true;
    }
    
    public boolean canNpcAct(NonPlayerCharacter e){// or if another entity is blocking the path to player
        return (Player.getHitbox().isTouching(
            ((NonPlayerCharacter)e).getDetectionBox()));
    }
    public boolean withinPanel(Entity e){
        if(e.getHitbox().close.y<=0||e.getHitbox().close.x<=0){
            return false;
        }
        else if(e.getHitbox().far.y>=edgeY||e.getHitbox().far.x>=edgeX){
            return false;
        }
        else{
            return true;
        }
    }
    
    public int withinPanelY(Entity e){
        if(e.getHitbox().close.y<=0){
            return Hitbox.ABOVE;
        }
        else if(e.getHitbox().far.y>=edgeY){
            return Hitbox.BELOW;
        }
        else{
        return Hitbox.UNDETERMINED;
        }
    }
    public int withinPanelX(Entity e){
        if(e.getHitbox().close.x<=0){
            return Hitbox.LEFT;
        }
        else if(e.getHitbox().far.x>=edgeX){
            return Hitbox.RIGHT;
        }
        else{
            return Hitbox.UNDETERMINED;
        }
    }
        
    public void projectileInflictDamage(){
        if(!Player.getProjectile().isEmpty()&&!npcs.isEmpty()){
            for(int i=0;i<npcs.size();i++){
                if(Player.getProjectile().get(0).getHitbox().isTouching(npcs.get(i).getHitbox())
                        &&npcs.get(i) instanceof OpaqueEntity){
                    Player.removeProjectile();
                    if(npcs.get(i) instanceof DamagableEntity){
                    Player.attack((DamagableEntity)npcs.get(i));
                    System.out.println("npc"+i+" HP:"+((DamagableEntity)npcs.get(i)).getHitpoints());
                        if(((DamagableEntity)npcs.get(i)).getHitpoints()<=0){
                            if(npcs.get(i) instanceof NonPlayerCharacter){
                                dialogPanel.message(((NonPlayerCharacter)npcs.get(i)).getName()+" has been killed");
                            }
                            npcs.remove(i);
                        }
                        if(Player.getProjectile().isEmpty()){
                            break;
                        }
                    }
                    else{
                        break;
                    }
                }
            }
        }  
    }
    public void projectileWithinPanel(){
        if(!Player.getProjectile().isEmpty()){
            if(withinPanel(Player.getProjectile().get(0))){
                Player.getProjectile().get(0).fire();
            }
            else{
                Player.removeProjectile();
            }
        }
    }
    public void detectForDamage(){
        for(int i=0;i<npcs.size();i++){
            if(npcs.get(i) instanceof NonPlayerCharacter){
                if(Player.getHitbox().isTouching(npcs.get(i).getHitbox())){
                    if(((NonPlayerCharacter)npcs.get(i)).getCharacterClass()!=NonPlayerCharacter.ARCHER){
                        ((NonPlayerCharacter)npcs.get(i)).attack(Player);
                    }                    
                }                
            }
        }
    }
    public boolean canNpcFireProjectile(Entity e){
            if(e instanceof NonPlayerCharacter){
                if(((NonPlayerCharacter)e).characterClass == NonPlayerCharacter.ARCHER
                        ||((NonPlayerCharacter)e).characterClass == NonPlayerCharacter.BOSS
                        ||((NonPlayerCharacter)e).characterClass == NonPlayerCharacter.SUBBOSS
                        ||((NonPlayerCharacter)e).characterClass == NonPlayerCharacter.BR_ARCHER
                        ||((NonPlayerCharacter)e).characterClass == NonPlayerCharacter.BR_SUBBOSS){
                    if(((NonPlayerCharacter)e).getDetectionBox().isTouching(Player.getHitbox())){
                        return true;
                }    
            }
        }
            return false;
    }
    public void npcFireProjectile(){
        for(int i=0;i<npcs.size();i++){
            if(canNpcFireProjectile(npcs.get(i))){
                if(npcs.get(i).getHitbox().isAbove(Player.getHitbox())
                        &&((NonPlayerCharacter)npcs.get(i)).canFireNextProjectile()){
                    ((NonPlayerCharacter)npcs.get(i)).loadProjectile(Entity.DOWN);
                }
                if(npcs.get(i).getHitbox().isBelow(Player.getHitbox())
                        &&((NonPlayerCharacter)npcs.get(i)).canFireNextProjectile()){
                    ((NonPlayerCharacter)npcs.get(i)).loadProjectile(Entity.UP);
                }
                if(npcs.get(i).getHitbox().isLeft(Player.getHitbox())
                        &&((NonPlayerCharacter)npcs.get(i)).canFireNextProjectile()){
                    ((NonPlayerCharacter)npcs.get(i)).loadProjectile(Entity.LEFT);
                }
                if(npcs.get(i).getHitbox().isRight(Player.getHitbox())
                        &&((NonPlayerCharacter)npcs.get(i)).canFireNextProjectile()){
                    ((NonPlayerCharacter)npcs.get(i)).loadProjectile(Entity.RIGHT);
                }
                //((NonPlayerCharacter)npcs.get(i)).getProjectile().get(0).fire();
            }
        }
    }
    public void npcProjectileWithinPanel(){
        for(int i=0;i<npcs.size();i++){
            if(npcs.get(i) instanceof NonPlayerCharacter){
                if(!((NonPlayerCharacter)npcs.get(i)).getProjectile().isEmpty()){
                    if(withinPanel(((NonPlayerCharacter)npcs.get(i)).getProjectile().get(0))){
                        ((NonPlayerCharacter)npcs.get(i)).getProjectile().get(0).fire();
                    }
                    else{
                        ((NonPlayerCharacter)npcs.get(i)).removeProjectile();
                    }
                }
            }
        }
    }
    public void npcProjectileInflictDamage(){
    for(int i=0;i<npcs.size();i++){
        if(npcs.get(i) instanceof NonPlayerCharacter){
            if(!((NonPlayerCharacter)npcs.get(i)).getProjectile().isEmpty()){
                if(((NonPlayerCharacter)npcs.get(i)).getProjectile().get(0).getHitbox().isTouching(Player.getHitbox())){
                    ((NonPlayerCharacter)npcs.get(i)).removeProjectile();
                    ((NonPlayerCharacter)npcs.get(i)).attack(Player);
                            }
                else{
                for(int z=0;z<npcs.size();z++){
                        if(i==z){}
                        else{
                            if(!((NonPlayerCharacter)npcs.get(i)).getProjectile().isEmpty()){
                                if(((NonPlayerCharacter)npcs.get(i)).getProjectile().get(0).getHitbox().isTouching(
                                    (npcs.get(z)).getHitbox())
                                        &&npcs.get(z) instanceof OpaqueEntity){
                                        ((NonPlayerCharacter)npcs.get(i)).removeProjectile();
                                    }
                                }
                            }
                        }
                    }
                    //System.out.println("Player HP:"+Player.getHitpoints());
                        if(((NonPlayerCharacter)npcs.get(i)).getProjectile().isEmpty()){
                            break;
                    }                          
                }
            }
        } 
    }  
    
    public void inflictDamage(){
        for(int i=0;i<npcs.size();i++){
            if(Player.getHitbox().isTouching(npcs.get(i).getHitbox())){
                if(npcs.get(i) instanceof DamagableEntity){
                    Player.attack((DamagableEntity)npcs.get(i));
                    System.out.println("NPC"+i+" HP: "+((DamagableEntity)npcs.get(i)).getHitpoints());
                        if(((DamagableEntity)npcs.get(i)).getHitpoints()<=0){
                            if(npcs.get(i) instanceof NonPlayerCharacter){
                                dialogPanel.message(((NonPlayerCharacter)npcs.get(i)).getName()+" has been killed");
                            }
                            npcs.remove(i);
                        }
                    }
                }                
            }
        }
    public boolean canPlayerMoveUp(){
        for(int i=0;i<npcs.size();i++){
            if(Player.getHitbox().isTouching(npcs.get(i).getHitbox())
                &&!Player.getHitbox().isAbove(npcs.get(i).getHitbox())
                &&npcs.get(i) instanceof OpaqueEntity){
                return false;
            }            
        }
        return true;
    }
    public boolean canPlayerMoveDown(){
        for(int i=0;i<npcs.size();i++){
            if(Player.getHitbox().isTouching(npcs.get(i).getHitbox())
                &&!Player.getHitbox().isBelow(npcs.get(i).getHitbox())
                &&npcs.get(i) instanceof OpaqueEntity){
                return false;
            }            
        }
        return true;
    }
    public boolean canPlayerMoveLeft(){        
        for(int i=0;i<npcs.size();i++){
            if(Player.getHitbox().isTouching(npcs.get(i).getHitbox())
                &&!Player.getHitbox().isRight(npcs.get(i).getHitbox())
                &&npcs.get(i) instanceof OpaqueEntity){
                return false;
            }            
        }
        return true;
    }
    public boolean canPlayerMoveRight(){        
        for(int i=0;i<npcs.size();i++){
            if(Player.getHitbox().isTouching(npcs.get(i).getHitbox())
                &&!Player.getHitbox().isLeft(npcs.get(i).getHitbox())
                &&npcs.get(i) instanceof OpaqueEntity){
                return false;
            }            
        }
        return true;
    }
    
    public void checkForItems(){
        for(int i=0;i<npcs.size();i++){
            if(npcs.get(i) instanceof SpawnableItem){
                if(Player.getHitbox().isTouching(npcs.get(i).getHitbox())
                        &&Player.inventory.getSize()<Inventory.MAX_SIZE){
                    Player.inventory.pickUpItem(((SpawnableItem)npcs.get(i)).getItems());
                    dialogPanel.message(Player.getName()+" picked up: "+((SpawnableItem)npcs.get(i)).getItems().getQuantity()+" "+((SpawnableItem)npcs.get(i)).getItems().getName());
                    npcs.remove(i);
                    for(int j=0;j<Player.inventory.items.size();j++){
                    }
                }
            }
        }
    }
    public void checkForInventory(){
        if((!Player.inventory.items.isEmpty())){
            for(int i=0;i<Player.inventory.items.size();i++){
                if(inventoryItems.length==0){break;}
                else if(inventoryItems[i].isButton1()){
                    use(i);
                }
                else if(inventoryItems[i].isButton2()){
                    drop(i);                    
                }                
            }
        }
    }
    public void checkPortals(){//TODO: Make another portalgate class(portalgate encapsulates another portalgate) created serves as the main portal.
        if(!npcs.isEmpty()){
            for(int i=0;i<npcs.size();i++){
                if(npcs.get(i) instanceof Portal){
                    for(int j=0;j<npcs.size();j++){
                        if(i==j){}
                        else{
                            ((Portal)npcs.get(i)).determineCanPlayerUse(npcs);
                            ((Portal)npcs.get(i)).teleport(Player);
                            if(((Portal)npcs.get(i)).canNpcUse()){
                                ((Portal)npcs.get(i)).teleport(npcs.get(j));
                            }
                        }
                    }                    
                }
            }
        }
    }
    public void regenOverTime(){
        if(Player.getHitpoints()<Player.getinitHitpoints()){
            Player.heal(1);       
        }
    }
            
    public void up(){
        if((withinPanelY(Player)!=Hitbox.ABOVE)&&canPlayerMoveUp()){
        //System.out.print("Moved from: ("+Player.getX()+","+Player.getY()+") ");
        Player.setY(Player.getY()-Player.getTravelY());
        //System.out.println("to: ("+Player.getX()+","+Player.getY()+") ");
        } 
        Player.setDirection(Entity.UP);
    }
    public void down(){
        if(((withinPanelY(Player)!=Hitbox.BELOW)&&canPlayerMoveDown())){
        //System.out.print("Moved from: ("+Player.getX()+","+Player.getY()+") ");
        Player.setY(Player.getY()+Player.getTravelY());
        //System.out.println("to: ("+Player.getX()+","+Player.getY()+") ");  
        }    
        Player.setDirection(Entity.DOWN);
    }
    public void left(){
        if(((withinPanelX(Player)!=Hitbox.LEFT)&&canPlayerMoveLeft())){
        //System.out.print("Moved from: ("+Player.getX()+","+Player.getY()+") ");
        Player.setX(Player.getX()-Player.getTravelX());
        //System.out.println("to: ("+Player.getX()+","+Player.getY()+")");
        }        
        Player.setDirection(Entity.LEFT);
    }
    public void right(){
        if(((withinPanelX(Player)!=Hitbox.RIGHT)&&canPlayerMoveRight())){
        //System.out.print("Moved from: ("+Player.getX()+","+Player.getY()+") ");
        Player.setX(Player.getX()+Player.getTravelX());
        //System.out.println("to: ("+Player.getX()+","+Player.getY()+")");
        } 
        Player.setDirection(Entity.RIGHT);
    }
    public void shoot(){
        boolean hasBow = false;
        boolean hasArrow = false;
        int index=100;//Arbitrary number just to check.
        
        for(int i=0;i<Player.inventory.items.size();i++){
            if(Player.getHasWeapon()){
                if(Player.inventory.items.get(i) instanceof Bow){
                    if(Player.weapon.equals(Player.inventory.items.get(i))){
                        hasBow = true;
                    }
                }
                if(Player.inventory.items.get(i) instanceof Arrow){
                    hasArrow = true;
                    index = i;                
                }
                if(hasBow&&hasArrow){
                    break;
                }
            }
        }
        
        if(Player.canFireNextProjectile()&&hasBow&&(index!=100)){
            Player.loadProjectile(Player.getDirection());
            Player.inventory.useItem(index,Player);
        }
    }
    public void attack(){
        inflictDamage();
    }
    public void defend(){
        
    }
    public void heal(){
        for(int i=0;i<Player.inventory.items.size();i++){
            if(Player.inventory.items.get(i) instanceof HealthPotion){
                Player.inventory.useItem(i,Player);
            }
        }        
    }
    public void use(int i){
        if(Player.inventory.items.get(i) instanceof UnusableItem){
            if(Player.inventory.items.get(i) instanceof Weapon){
                if(Player.getHasWeapon()&&(Player.weapon.equals((Weapon)Player.inventory.items.get(i)))){
                   dialogPanel.message(Player.getName()+" unequip: "+Player.weapon.getName());
                   Player.unequipWeapon((Weapon)Player.inventory.items.get(i));                   
                }
                else{
                   Player.equipWeapon((Weapon)Player.inventory.items.get(i));  
                   dialogPanel.message(Player.getName()+" equip: "+Player.weapon.getName());
                }
            }
            if(Player.inventory.items.get(i) instanceof Armor){
                if(Player.getHasArmor()&&(Player.armor.equals((Armor)Player.inventory.items.get(i)))){
                   dialogPanel.message(Player.getName()+" unequip: "+Player.armor.getName());
                   Player.unequipArmor((Armor)Player.inventory.items.get(i));                    
                }
                else{
                   Player.equipArmor((Armor)Player.inventory.items.get(i));
                   dialogPanel.message(Player.getName()+" equip: "+Player.armor.getName());
                }
            }
        } 
        else {
            dialogPanel.message(Player.getName()+" use: "+Player.inventory.items.get(i).getName());
            Player.inventory.useItem(i,Player);
        }
    }
    public void drop(int i){
        if(Player.inventory.items.get(i) instanceof UnusableItem){
            if(Player.inventory.items.get(i) instanceof Weapon){
                if(Player.getHasWeapon()&&(Player.weapon.equals((Weapon)Player.inventory.items.get(i)))){
                   dialogPanel.message(Player.getName()+" unequip: "+Player.weapon.getName());
                   Player.unequipWeapon((Weapon)Player.inventory.items.get(i));
                }
            }
            else if(Player.inventory.items.get(i) instanceof Armor){
                if(Player.getHasArmor()&&(Player.armor.equals((Armor)Player.inventory.items.get(i)))){
                   dialogPanel.message(Player.getName()+" unequip: "+Player.armor.getName());
                   Player.unequipArmor((Armor)Player.inventory.items.get(i)); 
                }
            }
        }        
        
        dialogPanel.message(Player.getName()+" drop: "+Player.inventory.items.get(i).getQuantity()+" "+Player.inventory.items.get(i).getName());
        
        if(Player.getX()<edgeX/2){
            SpawnableItem temp = Player.inventory.dropItem(i, Player.getX()+Player.DROP_DISTANCE_X, Player.getY());  
            npcs.add(temp);
            }
        else{
            SpawnableItem temp = Player.inventory.dropItem(i, Player.getX()-Player.DROP_DISTANCE_X, Player.getY());                    
            npcs.add(temp);
            }        
        
    }          

    @Override
    public void actionPerformed(ActionEvent e) {
        if(frameCount%FPS==0){
            detectForDamage();
            //System.out.println("Player HP:"+Player.getHitpoints());
            npcFireProjectile();
        }   
        if(frameCount%(10*FPS)==0){
            regenOverTime();
        }
        if(keyPressed[UP]){
            up();
        }
        if(keyPressed[DOWN]){
            down();
        }
        if(keyPressed[LEFT]){
            left();
        }
        if(keyPressed[RIGHT]){
            right();
        }
        if(keyPressed[ATTACK]){
            attack();
            //shoot();
            keyPressed[ATTACK] = false;
        }
        if(keyPressed[SHOOT]){
            shoot();
            keyPressed[SHOOT] = false;
        }
        if(keyPressed[DEFEND]){
                      
        }
        if(keyPressed[HEAL]){
            heal();
            keyPressed[HEAL]=false;
        }
        if(keyPressed[SELECTFOR]){
            //increments inventory; does nothing in actionPerformed method
        }
        if(keyPressed[SELECTBACK]){
            //increments inventory; does nothing in actionPerformed method
        }
        if(keyPressed[USE]){
            keyPressed[USE] = false;  
            if(!Player.inventory.items.isEmpty()){
                for(int i=0;i<Player.inventory.items.size();i++){
                    if(Inventory.inventorySelectorIndex==i){
                        use(i);
                }
            }
        }
        }
        if(keyPressed[DROP]){
            keyPressed[DROP] = false;
            if(!Player.inventory.items.isEmpty()){
            for(int i=0;i<Player.inventory.items.size();i++){
                if(Inventory.inventorySelectorIndex==i){
                    drop(i);                   
                    }
                }
            }
        }               
        checkPortals();
        npcProjectileInflictDamage(); 
        checkForItems();
        checkForInventory();
        Player.inventory.sortInventory();
        npcProjectileWithinPanel();       
        projectileWithinPanel();
        projectileInflictDamage();
        npcMove();
        repaint();
        frameCount++;
        //System.out.println("framecount:"+frameCount);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_UP){
            keyPressed[UP] = true;
            //up(); Player.setDirection(Entity.UP);            
        }
        if(key == KeyEvent.VK_DOWN){
            keyPressed[DOWN] = true;
            //down(); Player.setDirection(Entity.DOWN);
        }
        if(key == KeyEvent.VK_LEFT){
            keyPressed[LEFT] = true;
            //left(); Player.setDirection(Entity.LEFT);
        }
        if(key == KeyEvent.VK_RIGHT){
            keyPressed[RIGHT] = true;
            //right(); Player.setDirection(Entity.RIGHT);
        } 
        if(key == KeyEvent.VK_A){
            //keyPressed[ATTACK] = true;
            //inflictDamage();
        }
        if(key == KeyEvent.VK_S){
            //keyPressed[SHOOT] = true;
            //if(Player.canFireNextProjectile()){
            //Player.loadProjectile(Player.getDirection());
            //}
        }
        if(key == KeyEvent.VK_D){
            keyPressed[DEFEND] = true;
            
        }
        if(key == KeyEvent.VK_H){
            //keyPressed[HEAL] = true;            
        }
        if(key == KeyEvent.VK_W){
            keyPressed[SELECTFOR] = true;
        }
        if(key == KeyEvent.VK_Q){
            keyPressed[SELECTBACK] = true;
        }
        if(key == KeyEvent.VK_E){
            //keyPressed[USE] = true;
        }
        if(key == KeyEvent.VK_R){
            //keyPressed[DROP] = true;
        }
    }
    @Override   
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_UP){
            keyPressed[UP] = false;
            //up(); Player.setDirection(Entity.UP);            
        }
        if(key == KeyEvent.VK_DOWN){
            keyPressed[DOWN] = false;
            //down(); Player.setDirection(Entity.DOWN);
        }
        if(key == KeyEvent.VK_LEFT){
            keyPressed[LEFT] = false;
            //left(); Player.setDirection(Entity.LEFT);
        }
        if(key == KeyEvent.VK_RIGHT){
            keyPressed[RIGHT] = false;
            //right(); Player.setDirection(Entity.RIGHT);
        } 
        if(key == KeyEvent.VK_A){
            keyPressed[ATTACK] = true;
            //inflictDamage();
        }
        if(key == KeyEvent.VK_S){
            keyPressed[SHOOT] = true;
            //if(Player.canFireNextProjectile()){
            //Player.loadProjectile(Player.getDirection());
            //}
        }
        if(key == KeyEvent.VK_D){
            keyPressed[DEFEND] = false;
            
        }
        if(key == KeyEvent.VK_H){
            keyPressed[HEAL] = true;            
        }
        if(key == KeyEvent.VK_W){
            keyPressed[SELECTFOR] = false;
            Inventory.inventorySelectorIncrement(true);
        }
        if(key == KeyEvent.VK_Q){
            keyPressed[SELECTBACK] = false;
            Inventory.inventorySelectorIncrement(false);
        }
        if(key == KeyEvent.VK_E){
            keyPressed[USE] = true;
        }
        if(key == KeyEvent.VK_R){
            keyPressed[DROP] = true;
        }
    }
    
    @Override
    public void paintComponent(Graphics g){//TODO: if character=="stringname" then paint component.
        super.paintComponent(g);
        Graphics2D graphic = (Graphics2D)g;
        
        graphic.setColor(new Color(63,131,104));//Cyanish Green
        graphic.fill(new Rectangle2D.Double(Player.getX(),Player.getY(),Player.getWidth(),Player.getHeight()));
        drawHitpointsBar(graphic,Player);
        
        
        if(!Player.getProjectile().isEmpty()){
            for(int z=0;z<Player.getProjectile().size();z++){
            graphic.fill(new Rectangle2D.Double(Player.getProjectile().get(z).x,Player.getProjectile().get(z).y,
                    Player.getProjectile().get(z).width,Player.getProjectile().get(z).height));
            }
        }
        
        if(!npcs.isEmpty()){
            for(int i=0;i<npcs.size();i++){
                Entity temp = npcs.get(i);
                if(temp instanceof Portal){
                    if(((Portal)temp).isMain){
                        graphic.setColor(new Color(233,118,17));//Orange
                    }
                    else{
                        graphic.setColor(new Color(43,127,223));//Blue
                    }
                    graphic.fill(new Ellipse2D.Double(npcs.get(i).getX(),npcs.get(i).getY(),npcs.get(i).getWidth(),npcs.get(i).getHeight()));
                }
                else if(temp instanceof MapGate){
                    graphic.setColor(Color.black);
                    graphic.draw(new Ellipse2D.Double(npcs.get(i).getX(),npcs.get(i).getY(),npcs.get(i).getWidth(),npcs.get(i).getHeight()));
                }               
                
                else if(temp instanceof NonPlayerCharacter){
                    if(((((NonPlayerCharacter)temp).getCharacterClass()==NonPlayerCharacter.WARRIOR)
                            ||((NonPlayerCharacter)temp).getCharacterClass()==NonPlayerCharacter.BR_WARRIOR)){
                        graphic.setColor(new Color(156,34,34));//dark red
                    }
                    else if(((((NonPlayerCharacter)temp).getCharacterClass()==NonPlayerCharacter.ARCHER)
                            ||((NonPlayerCharacter)temp).getCharacterClass()==NonPlayerCharacter.BR_ARCHER)){
                        graphic.setColor(new Color(176,176,75));//snakeskin
                    }
                    else if(((((NonPlayerCharacter)temp).getCharacterClass()==NonPlayerCharacter.TANK)
                            ||((NonPlayerCharacter)temp).getCharacterClass()==NonPlayerCharacter.BR_TANK)){
                        graphic.setColor(new Color(92,90,19));//camogreen
                    }
                    else if(((((NonPlayerCharacter)temp).getCharacterClass()==NonPlayerCharacter.SUBBOSS)
                            ||((NonPlayerCharacter)temp).getCharacterClass()==NonPlayerCharacter.BR_SUBBOSS)){
                        graphic.setColor(new Color(85,18,105));//dark purple
                    }
                    else if(((NonPlayerCharacter)temp).getCharacterClass()==NonPlayerCharacter.BOSS){
                        graphic.setColor(new Color(0,0,0));//black
                    }
                    graphic.fill(new Rectangle2D.Double(npcs.get(i).getX(),npcs.get(i).getY(),npcs.get(i).getWidth(),npcs.get(i).getHeight()));
                    if(!((NonPlayerCharacter)npcs.get(i)).getProjectile().isEmpty()){
                        for(int z=0;z<((NonPlayerCharacter)npcs.get(i)).getProjectile().size();z++){
                            Projectile p = ((NonPlayerCharacter)npcs.get(i)).getProjectile().get(0);
                            graphic.fill(new Ellipse2D.Double(p.x,p.y,p.width,p.height));
                        }
                    }
                    
                    drawHitpointsBar(graphic,(NonPlayerCharacter)temp);
                    
                }
                else if(temp instanceof DamagableEntity){
                    graphic.setColor(Color.GRAY);//greenish
                    graphic.fill(new Rectangle2D.Double(temp.getX(),temp.getY(),temp.getWidth(),temp.getHeight()));
                }
                else if(temp instanceof SpawnableItem){
                    graphic.setColor(new Color(188,101,121));
                    graphic.fill(new Rectangle2D.Double(temp.getX(),temp.getY(),temp.getWidth(),temp.getHeight()));
                }
                else{
                    graphic.setColor(Color.DARK_GRAY);//greenish
                    graphic.fill(new Rectangle2D.Double(temp.getX(),temp.getY(),temp.getWidth(),temp.getHeight()));
                }

            }
        }
    }
    
    public void drawHitpointsBar(Graphics2D g, Character d){
        double ratio = d.getHitpoints()/d.getinitHitpoints();
        double hitpointsBarWidth = d.getWidth()*ratio;
        g.setColor(Color.GREEN);
        if(d.getY()>(edgeY/2)){
            g.fill(new Rectangle2D.Double(
                    d.getX(),d.getHitbox().close.y-hitpointsBarOffset,hitpointsBarWidth,hitpointsBarHeight));
            }
        else{
            g.fill(new Rectangle2D.Double(
                    d.getX(),d.getHitbox().far.y+hitpointsBarOffset,hitpointsBarWidth,hitpointsBarHeight));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    
}
