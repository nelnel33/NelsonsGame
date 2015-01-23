package nelsontsui.nelsonsgame.game;

import nelsontsui.nelsonsgame.game.items.UnusableItem;
import nelsontsui.nelsonsgame.game.items.HealthPotion;
import nelsontsui.nelsonsgame.game.items.Weapon;
import nelsontsui.nelsonsgame.game.items.ProjectileWeapon;
import nelsontsui.nelsonsgame.game.items.Ammo;
import nelsontsui.nelsonsgame.game.items.Armor;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/*
*NPCS attack once a second
*player is independent of FPS
*/

public class ActionPanel extends JPanel implements ActionListener, KeyListener{
    public static final double FPS = 50;
    public static final double TICK = 1000.0/FPS;
    public static final double DELTA = TICK/1000.0;
    public static final double INVERSE_DELTA = 1000.0/TICK;
    public long frameCount = 0;
    
    //protected Timer check = new Timer(TICK,this);
    
    private Level level;    
    protected Player player;
    protected ArrayList<Entity> entities = new ArrayList<>();
    
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
    public ActionPanel(Level level){
        this.level = level;
        this.player = level.getPlayer();
        this.entities = level.getEntities(); 
        
        setSize(GameDisplay.ACTIONPANEL_WIDTH,GameDisplay.ACTIONPANEL_HEIGHT);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    public ArrayList<Entity> getNpcs(){
        return entities;
    }
    public Character getPlayer(){
        return player;
    }
    public void setNpcs(ArrayList<Entity> npcs){
        this.entities = npcs;
    }
    public void setPlayer(Player player){
        this.player = player;
    }
    public Level getLevel() {
        return level;
    }
    public void setLevel(Level level) {
        this.level = level;
    }   
    public void setInventoryItems(InventoryIcon[] inventoryItems){
        this.inventoryItems = inventoryItems;
    }
    public void setDialogPanel(DialogBox dialogPanel){
        this.dialogPanel = dialogPanel;
    }
    public void npcMove(){
        for(int i=0;i<entities.size();i++){
            if(entities.get(i) instanceof NonPlayerCharacter){
                if(canNpcAct((NonPlayerCharacter)entities.get(i))){
                    if(!entities.get(i).getHitbox().isTouching(player.getHitbox())){
                        if(player.getHitbox().isStrictlyLeft(entities.get(i).getHitbox())
                                &&canNpcMoveLeft(entities.get(i))
                                &&withinPanelX(entities.get(i))!=Hitbox.RIGHT){
                            //npcs.get(i).setX(entities.get(i).getX()-((NonPlayerCharacter)entities.get(i)).getTravelX());
                            ((NonPlayerCharacter)entities.get(i)).moveLeft();//moveLeft
                        }
                        if(player.getHitbox().isStrictlyRight(entities.get(i).getHitbox())//isLeft
                                &&canNpcMoveRight(entities.get(i))
                                &&withinPanelX(entities.get(i))!=Hitbox.LEFT){
                            //npcs.get(i).setX(entities.get(i).getX()+((NonPlayerCharacter)entities.get(i)).getTravelX());
                            ((NonPlayerCharacter)entities.get(i)).moveRight();//moveRight
                        } 
                        if(player.getHitbox().isStrictlyBelow(entities.get(i).getHitbox())
                                &&canNpcMoveDown(entities.get(i))
                                &&withinPanelY(entities.get(i))!=Hitbox.BELOW){
                            //npcs.get(i).setY(entities.get(i).getY()+((NonPlayerCharacter)entities.get(i)).getTravelY());      
                            ((NonPlayerCharacter)entities.get(i)).moveDown();
                        }
                        if(player.getHitbox().isStrictlyAbove(entities.get(i).getHitbox())
                                &&canNpcMoveUp(entities.get(i))
                                &&withinPanelY(entities.get(i))!=Hitbox.ABOVE){
                            //npcs.get(i).setY(entities.get(i).getY()-((NonPlayerCharacter)entities.get(i)).getTravelY());
                            ((NonPlayerCharacter)entities.get(i)).moveUp();
                        }                      
                    }
                }
            }
        }
    }
    public boolean canNpcMoveUp(Entity e){        
        for(int i=0;i<entities.size();i++){
            if(e.equals(entities.get(i))){}
            else{
                if(entities.get(i) instanceof OpaqueEntity){
                    if(e.getHitbox().isTouching(entities.get(i).getHitbox())
                       &&e.getHitbox().isStrictlyBelow(entities.get(i).getHitbox())){
                        return false;
                    } 
                }
            }
        }
        return true;
    }
    public boolean canNpcMoveDown(Entity e){
        for(int i=0;i<entities.size();i++){
            if(entities.get(i).equals(e)){}
            else{
                if(entities.get(i) instanceof OpaqueEntity){
                    if(e.getHitbox().isTouching(entities.get(i).getHitbox())
                       &&e.getHitbox().isStrictlyAbove(entities.get(i).getHitbox())){
                        return false;
                    } 
                }
            }
        }
        return true;
    }
    public boolean canNpcMoveLeft(Entity e){              
        for(int i=0;i<entities.size();i++){
            if(entities.get(i).equals(e)){}
            else{
                if(entities.get(i) instanceof OpaqueEntity){
                    if(e.getHitbox().isTouching(entities.get(i).getHitbox())
                       &&e.getHitbox().isStrictlyRight(entities.get(i).getHitbox())){
                        return false;
                    } 
                }
            }
        }
        return true;
    }
    public boolean canNpcMoveRight(Entity e){
        for(int i=0;i<entities.size();i++){
            if(entities.get(i).equals(e)){}
            else{
                if(entities.get(i) instanceof OpaqueEntity){
                    if(e.getHitbox().isTouching(entities.get(i).getHitbox())
                       &&e.getHitbox().isStrictlyLeft(entities.get(i).getHitbox())){
                        return false;
                    } 
                }
            }
        }
        return true;
    }
    
    public boolean canNpcAct(NonPlayerCharacter e){// or if another entity is blocking the path to player
        return (player.getHitbox().isTouching(
            ((NonPlayerCharacter)e).getDetectionBox()));
    }
    public boolean withinPanel(Entity e){
        if(e.getHitbox().close.y<=0||e.getHitbox().close.x<=0){
            return false;
        }
        else if(e.getHitbox().far.y>=GameDisplay.ACTIONPANEL_HEIGHT||e.getHitbox().far.x>=GameDisplay.ACTIONPANEL_WIDTH){
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
        else if(e.getHitbox().far.y>=GameDisplay.ACTIONPANEL_HEIGHT){
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
        else if(e.getHitbox().far.x>=GameDisplay.ACTIONPANEL_WIDTH){
            return Hitbox.RIGHT;
        }
        else{
            return Hitbox.UNDETERMINED;
        }
    }
        
    public void projectileInflictDamage(){
        if(!(player.getProjectile().isEmpty() || entities.isEmpty())){
            for(int i=0;i<entities.size();i++){
                for(int j=player.getProjectile().size()-1;j>=0;j--){
                    if(player.getProjectile().get(j).getHitbox().isTouching(entities.get(i).getHitbox())
                            &&entities.get(i) instanceof OpaqueEntity){
                        player.removeProjectile();
                        j--;
                        if(entities.get(i) instanceof DamagableEntity){
                            player.shoot((DamagableEntity)entities.get(i));
                            System.out.println("npc"+i+" HP:"+((DamagableEntity)entities.get(i)).getHitpoints());
                            if(((DamagableEntity)entities.get(i)).getHitpoints()<=0){
                                if(entities.get(i) instanceof NonPlayerCharacter){
                                    dialogPanel.message(((NonPlayerCharacter)entities.get(i)).getName()+" has been killed");
                                }
                                entities.remove(i);
                            }
                        }
                    }
                }
            }
        }
    }
    public void projectileWithinPanel(){
        if(!player.getProjectile().isEmpty()){
            if(withinPanel(player.getProjectile().get(0))){
                //Player.getProjectile().get(0).fire();
            }
            else{
                player.removeProjectile();
            }
        }
    }
    public void detectForDamage(){
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
    public boolean canNpcFireProjectile(Entity e){
            if(e instanceof NonPlayerCharacter){
                if(((NonPlayerCharacter)e).characterClass == NonPlayerCharacter.ARCHER
                        ||((NonPlayerCharacter)e).characterClass == NonPlayerCharacter.BOSS
                        ||((NonPlayerCharacter)e).characterClass == NonPlayerCharacter.SUBBOSS
                        ||((NonPlayerCharacter)e).characterClass == NonPlayerCharacter.BR_ARCHER
                        ||((NonPlayerCharacter)e).characterClass == NonPlayerCharacter.BR_SUBBOSS){
                    if(((NonPlayerCharacter)e).getDetectionBox().isTouching(player.getHitbox())){
                        return true;
                }    
            }
        }
            return false;
    }
    public void npcFireProjectile(){
        for(int i=0;i<entities.size();i++){
            if(canNpcFireProjectile(entities.get(i))){
                if(entities.get(i).getHitbox().isStrictlyAbove(player.getHitbox())
                        &&((NonPlayerCharacter)entities.get(i)).canFireNextProjectile()){
                    ((NonPlayerCharacter)entities.get(i)).loadProjectile(Entity.DOWN);
                }
                else if(entities.get(i).getHitbox().isStrictlyLeft(player.getHitbox())
                        &&((NonPlayerCharacter)entities.get(i)).canFireNextProjectile()){
                    ((NonPlayerCharacter)entities.get(i)).loadProjectile(Entity.RIGHT);
                }
                else if(entities.get(i).getHitbox().isStrictlyRight(player.getHitbox())
                        &&((NonPlayerCharacter)entities.get(i)).canFireNextProjectile()){
                    ((NonPlayerCharacter)entities.get(i)).loadProjectile(Entity.LEFT);                    
                }
                else if(entities.get(i).getHitbox().isStrictlyBelow(player.getHitbox())
                        &&((NonPlayerCharacter)entities.get(i)).canFireNextProjectile()){
                    ((NonPlayerCharacter)entities.get(i)).loadProjectile(Entity.UP);
                }
            }
        }
    }
    public void npcProjectileWithinPanel(){
        for(int i=0;i<entities.size();i++){
            if(entities.get(i) instanceof NonPlayerCharacter){
                if(!((NonPlayerCharacter)entities.get(i)).getProjectile().isEmpty()){
                    if(withinPanel(((NonPlayerCharacter)entities.get(i)).getProjectile().get(0))){
                        ((NonPlayerCharacter)entities.get(i)).getProjectile().get(0).fire();
                    }
                    else{
                        ((NonPlayerCharacter)entities.get(i)).removeProjectile();
                    }
                }
            }
        }
    }
    public void npcProjectileInflictDamage(){         
        for(int i=0;i<entities.size();i++){
            if(entities.get(i) instanceof NonPlayerCharacter){
                if(!(((NonPlayerCharacter)entities.get(i)).getProjectile().isEmpty())){
                    if(((NonPlayerCharacter)entities.get(i)).getProjectile().get(0).getHitbox().isTouching(player.getHitbox())){
                        ((NonPlayerCharacter)entities.get(i)).removeProjectile();
                        ((NonPlayerCharacter)entities.get(i)).attack(player);
                        }
                    else{
                        for(int j=0;j<entities.size();j++){
                            if(i==j){}
                            else{                    
                                if(entities.get(j) instanceof OpaqueEntity
                                    &&((NonPlayerCharacter)entities.get(i)).getProjectile().get(0).getHitbox().isTouching((entities.get(j)).getHitbox())){
                                        ((NonPlayerCharacter)entities.get(i)).removeProjectile();
                                        break;
                                }                        
                            }
                        }
                    }
                }
            }
        }
    }  
    
    public void inflictDamage(){
        for(int i=0;i<entities.size();i++){
            if(player.getHitbox().isTouching(entities.get(i).getHitbox())){
                if(entities.get(i) instanceof DamagableEntity){
                    if(player.attack((DamagableEntity)entities.get(i)) && player.getHasWeapon()){
                        dialogPanel.message("You cannot attack a Damagable Entity with a "+
                            player.weapon.getName()+" equipped");
                    }
                    System.out.println("NPC"+i+" HP: "+((DamagableEntity)entities.get(i)).getHitpoints());
                        if(((DamagableEntity)entities.get(i)).getHitpoints()<=0){
                            if(entities.get(i) instanceof NonPlayerCharacter){
                                dialogPanel.message(((NonPlayerCharacter)entities.get(i)).getName()+" has been killed");
                            }
                            entities.remove(i);
                        }
                    }
                }                
            }
        }
    public boolean canPlayerMoveUp(){
        for(int i=0;i<entities.size();i++){
            if(entities.get(i) instanceof OpaqueEntity
                &&player.getHitbox().isTouching(entities.get(i).getHitbox())
                &&player.getHitbox().isStrictlyBelow(entities.get(i).getHitbox())){
                return false;
            }            
        }
        return true;
    }
    public boolean canPlayerMoveDown(){
        for(int i=0;i<entities.size();i++){
            if(entities.get(i) instanceof OpaqueEntity
                &&player.getHitbox().isTouching(entities.get(i).getHitbox())
                &&player.getHitbox().isStrictlyAbove(entities.get(i).getHitbox())){
                return false;
            }            
        }
        return true;
    }
    public boolean canPlayerMoveLeft(){        
        for(int i=0;i<entities.size();i++){
            if(entities.get(i) instanceof OpaqueEntity
                &&player.getHitbox().isTouching(entities.get(i).getHitbox())
                &&player.getHitbox().isStrictlyRight(entities.get(i).getHitbox())){
                return false;
            }            
        }
        return true;
    }
    public boolean canPlayerMoveRight(){        
        for(int i=0;i<entities.size();i++){
            if(entities.get(i) instanceof OpaqueEntity
                &&player.getHitbox().isTouching(entities.get(i).getHitbox())
                &&player.getHitbox().isStrictlyLeft(entities.get(i).getHitbox())){
                return false;
            }            
        }
        return true;
    }
    
    public void checkForItems(){
        for(int i=0;i<entities.size();i++){
            if(entities.get(i) instanceof SpawnableItem){
                if(player.getHitbox().isTouching(entities.get(i).getHitbox())
                        &&player.inventory.getSize()<Inventory.MAX_SIZE){
                    player.inventory.pickUpItem(((SpawnableItem)entities.get(i)).getItems());
                    dialogPanel.message(player.getName()+" picked up: "+((SpawnableItem)entities.get(i)).getItems().getQuantity()+" "+((SpawnableItem)entities.get(i)).getItems().getName());
                    entities.remove(i);
                    for(int j=0;j<player.inventory.items.size();j++){
                    }
                }
            }
        }
    }
    public void checkForInventory(){
        if((!player.inventory.items.isEmpty())){
            for(int i=0;i<player.inventory.items.size();i++){
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
    public void checkPortals(){
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
    public void checkTalkableGates(){
        if(!entities.isEmpty()){
            for(int i=0;i<entities.size();i++){
                if(entities.get(i) instanceof TalkableGate){
                    TalkableGate t = (TalkableGate)entities.get(i);          
                    if(t.canOperate(player)){
                        dialogPanel.message(t.getSpeech());                                                          
                    }
                }
            }
        }
    }
    public void regenOverTime(){
        if(player.getHitpoints()<player.getinitHitpoints()){
            player.heal(1);       
        }
    }  
    public void checkHasBeenFired(){
        if(!player.getProjectile().isEmpty()){
            for(int i=0;i<player.getProjectile().size();i++){
                if(player.getProjectile().get(i).hasBeenFired()){
                    player.getProjectile().get(i).fire();
                }
            }
        }
    }
    public void checkForMapGateOperation(){
        if(!entities.isEmpty()){
            for(int i=0;i<entities.size();i++){
                if(entities.get(i) instanceof MapGate){
                    MapGate m = (MapGate)entities.get(i);
                    m.checkIfCanUse(entities);
                    if(m.usedToExitMap()){
                        m.operate(player, this);
                    }
                    else{
                        m.operate(player,dialogPanel);
                    }                            
                }
            }
        }
    }
            
    public void up(){
        if((withinPanelY(player)!=Hitbox.ABOVE)&&
                canPlayerMoveUp()){
        //System.out.print("Moved from: ("+player.getX()+","+player.getY()+") ");
        //Player.setY(player.getY()-player.getTravelY());
            player.moveUp();
        //System.out.println("to: ("+player.getX()+","+player.getY()+") ");
        } 
        player.setDirection(Entity.UP);
    }
    public void down(){
        if(((withinPanelY(player)!=Hitbox.BELOW)&&
                canPlayerMoveDown())){
        //System.out.print("Moved from: ("+player.getX()+","+player.getY()+") ");
        //Player.setY(player.getY()+player.getTravelY());
            player.moveDown();
        //System.out.println("to: ("+player.getX()+","+player.getY()+") ");  
        }    
        player.setDirection(Entity.DOWN);
    }
    public void left(){
        if(((withinPanelX(player)!=Hitbox.LEFT)&&
                canPlayerMoveLeft())){
        //System.out.print("Moved from: ("+player.getX()+","+player.getY()+") ");
        //Player.setX(player.getX()-player.getTravelX());
            player.moveLeft();
        //System.out.println("to: ("+player.getX()+","+player.getY()+")");
        }        
        player.setDirection(Entity.LEFT);
    }
    public void right(){
        if(((withinPanelX(player)!=Hitbox.RIGHT)&&
                canPlayerMoveRight())){
        //System.out.print("Moved from: ("+player.getX()+","+player.getY()+") ");
        //Player.setX(player.getX()+player.getTravelX());
            player.moveRight();
        //System.out.println("to: ("+player.getX()+","+player.getY()+")");
        } 
        player.setDirection(Entity.RIGHT);
    }
    public void shoot(){
        boolean hasBow = false;
        boolean hasArrow = false;
        boolean isCompatible = false;
        int index=100;//Arbitrary number just to check.
        
        for(int i=0;i<player.inventory.items.size();i++){
            if(player.getHasWeapon()){
                if(player.inventory.items.get(i) instanceof ProjectileWeapon){
                    if(player.weapon.equals(player.inventory.items.get(i))){
                        hasBow = true;
                    }
                }
                if(player.inventory.items.get(i) instanceof Ammo){
                    hasArrow = true;
                    index = i;                
                    if(hasArrow
                            &&(player.weapon instanceof ProjectileWeapon)
                            &&(player.inventory.items.get(i) instanceof Ammo)){
                        isCompatible = ((ProjectileWeapon)player.weapon).isCompatible((Ammo)player.inventory.items.get(i));
                    }
                }
                if(hasBow&&hasArrow){
                    break;
                }
            }
        }
        
        if(hasBow&&(index!=100)&&isCompatible){
            player.loadProjectile(player.getDirection());
            player.inventory.useItem(index,player);
            for(int i=0;i<player.getProjectile().size();i++){
                player.getProjectile().get(i).fire();
            }
        }
    }
    public void attack(){
        inflictDamage();
    }
    public void defend(){
        
    }
    public void heal(){
        for(int i=0;i<player.inventory.items.size();i++){
            if(player.inventory.items.get(i) instanceof HealthPotion){
                player.inventory.useItem(i,player);
            }
        }        
    }
    public void use(int i){
        if(player.inventory.items.get(i) instanceof UnusableItem){
            if(player.inventory.items.get(i) instanceof Weapon){
                if(player.getHasWeapon()&&(player.weapon.equals((Weapon)player.inventory.items.get(i)))){
                   dialogPanel.message(player.getName()+" unequip: "+player.weapon.getName());
                   player.unequipWeapon((Weapon)player.inventory.items.get(i));                   
                }
                else{
                   player.equipWeapon((Weapon)player.inventory.items.get(i));  
                   dialogPanel.message(player.getName()+" equip: "+player.weapon.getName());
                }
            }
            if(player.inventory.items.get(i) instanceof Armor){
                if(player.getHasArmor()&&(player.armor.equals((Armor)player.inventory.items.get(i)))){
                   dialogPanel.message(player.getName()+" unequip: "+player.armor.getName());
                   player.unequipArmor((Armor)player.inventory.items.get(i));                    
                }
                else{
                   player.equipArmor((Armor)player.inventory.items.get(i));
                   dialogPanel.message(player.getName()+" equip: "+player.armor.getName());
                }
            }
        } 
        else {
            dialogPanel.message(player.getName()+" use: "+player.inventory.items.get(i).getName());
            player.inventory.useItem(i,player);
        }
    }
    public void drop(int i){
        if(player.inventory.items.get(i) instanceof UnusableItem){
            if(player.inventory.items.get(i) instanceof Weapon){
                if(player.getHasWeapon()&&(player.weapon.equals((Weapon)player.inventory.items.get(i)))){
                   dialogPanel.message(player.getName()+" unequip: "+player.weapon.getName());
                   player.unequipWeapon((Weapon)player.inventory.items.get(i));
                }
            }
            else if(player.inventory.items.get(i) instanceof Armor){
                if(player.getHasArmor()&&(player.armor.equals((Armor)player.inventory.items.get(i)))){
                   dialogPanel.message(player.getName()+" unequip: "+player.armor.getName());
                   player.unequipArmor((Armor)player.inventory.items.get(i)); 
                }
            }
        }        
        
        dialogPanel.message(player.getName()+" drop: "+player.inventory.items.get(i).getQuantity()+" "+player.inventory.items.get(i).getName());
        
        if(player.getX()<GameDisplay.ACTIONPANEL_WIDTH/2){
            SpawnableItem temp = player.inventory.dropItem(i, player.getX()+player.DROP_DISTANCE_X, player.getY());  
            entities.add(temp);
            }
        else{
            SpawnableItem temp = player.inventory.dropItem(i, player.getX()-player.DROP_DISTANCE_X, player.getY());                    
            entities.add(temp);
            }        
        
    }          

    @Override
    public void actionPerformed(ActionEvent e) {
        if(player!=null){
            if(frameCount%FPS==0){
                detectForDamage();
                //System.out.println("player HP:"+player.getHitpoints());
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
                
            }
            if(keyPressed[SELECTBACK]){
                //increments inventory; does nothing in actionPerformed method
            }
            if(keyPressed[USE]){
                keyPressed[USE] = false;  
                if(!player.inventory.items.isEmpty()){
                    for(int i=0;i<player.inventory.items.size();i++){
                        if(Inventory.inventorySelectorIndex==i){
                            use(i);
                    }
                }
            }
            }
            if(keyPressed[DROP]){
                keyPressed[DROP] = false;
                if(!player.inventory.items.isEmpty()){
                for(int i=0;i<player.inventory.items.size();i++){
                    if(Inventory.inventorySelectorIndex==i){
                        drop(i);                   
                        }
                    }
                }
            }  
            
            checkForInventory();
            npcProjectileInflictDamage();
            npcMove();
            npcProjectileWithinPanel();            
            checkForMapGateOperation();
            checkHasBeenFired();
            checkPortals();
            checkTalkableGates(); 
            checkForItems();
            projectileWithinPanel();
            projectileInflictDamage();
            player.inventory.sortInventory();    
            repaint();
            frameCount++;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_UP){
            keyPressed[UP] = true;           
        }
        if(key == KeyEvent.VK_DOWN){
            keyPressed[DOWN] = true;
        }
        if(key == KeyEvent.VK_LEFT){
            keyPressed[LEFT] = true;
        }
        if(key == KeyEvent.VK_RIGHT){
            keyPressed[RIGHT] = true;
        } 
        if(key == KeyEvent.VK_A){
        }
        if(key == KeyEvent.VK_S){
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
        }
        if(key == KeyEvent.VK_DOWN){
            keyPressed[DOWN] = false;
        }
        if(key == KeyEvent.VK_LEFT){
            keyPressed[LEFT] = false;
        }
        if(key == KeyEvent.VK_RIGHT){
            keyPressed[RIGHT] = false;
        } 
        if(key == KeyEvent.VK_A){
            keyPressed[ATTACK] = true;
        }
        if(key == KeyEvent.VK_S){
            keyPressed[SHOOT] = true;
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
        
        for(Entity ent: entities){
            ent.render(graphic);
        }
        
        player.render(graphic);
        
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    
}
