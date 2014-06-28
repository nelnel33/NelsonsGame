/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.leveleditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import nelsontsui.nelsonsgame.game.DamagableEntity;
import nelsontsui.nelsonsgame.game.DimensionDouble;
import nelsontsui.nelsonsgame.game.Entity;
import nelsontsui.nelsonsgame.game.MapGate;
import nelsontsui.nelsonsgame.game.NonPlayerCharacter;
import nelsontsui.nelsonsgame.game.Point;
import nelsontsui.nelsonsgame.game.Portal;
import nelsontsui.nelsonsgame.game.Projectile;
import nelsontsui.nelsonsgame.game.SpawnableItem;
import nelsontsui.nelsonsgame.game.*;

public class EditingPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener{       
    protected Point cursor;//where the cursor is;
    protected Point placement;//where the entity will be placed
    protected DimensionDouble dimension;//how large the entity will be (dimension by dimension)
    
    protected ArrayList<Entity> npcs = new ArrayList<>();
    protected nelsontsui.nelsonsgame.game.Character player;
    
    protected static final int gridRows = 40;
    protected static final int gridColumns = 74;
    protected Point[][] points = new Point[gridRows][gridColumns];
    protected DimensionDouble[][] dimensions = new DimensionDouble[gridRows][gridColumns]; 
    protected Entity[][] npcsOnGrid = new Entity[gridRows][gridColumns];
    
    protected EntityTile[] detailedSelectors = new EntityTile[LevelEditorDisplay.TOTAL_DETAILEDSELECTORS];
    protected String currentDetailedSelectorId;
    
    //defaultOptions
    protected String defaultName="Default Name";
    protected double defaultHitpoints=10;
    protected double defaultDamage=2;
    protected double defaultDetectionRadius=500;
    protected double defaultProjectileSpeed=300;
    protected double defaultSpeed=50;
    protected String defaultTalkableSpeech="Set the speech of a TalkableGate by clicking custom, which is underneath configure.";
    protected int defaultQuantity=1;
    protected int defaultArmorProtection=10;
    protected int defaultWeaponDamage=10;
    protected String defaultItemName="Item";
    protected int defaultPotionStrength=2;
    protected Point defaultPortalExit = new Point(0,0);
    
    protected DimensionDouble defaultDimension = new DimensionDouble(10,10);
            
    
    //for characters & npcs
    protected String name;
    protected double hitpoints;
    protected double damage;
    protected double detectionRadius;
    protected double projectileSpeed;
    protected double speed;
    //for talkablegates
    protected String talkableSpeech;
    //for items
    protected int quantity;
    protected int armorProtection;
    protected int weaponDamage;
    protected String itemName;
    protected int potionStrength;
    //for portals
    protected Point portalExit;

    public static final int TOP_LEFT = 100;
    public static final int TOP_RIGHT = 200;
    public static final int BOTTOM_LEFT = 300;
    public static final int BOTTOM_RIGHT = 400;
    
    private static int increment = 0;
    
    public EditingPanel(Point cursor, Point placement, DimensionDouble dimension){
        this.cursor = cursor;
        this.placement = placement;
        this.dimension = dimension;
        
        initDefaults();        
        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    public void initDefaults(){        
        name=defaultName;
        hitpoints=defaultHitpoints;
        damage=defaultDamage;
        detectionRadius=defaultDetectionRadius;
        projectileSpeed=defaultProjectileSpeed;
        speed=defaultSpeed;        
        talkableSpeech=defaultTalkableSpeech;        
        quantity=defaultQuantity;
        armorProtection=defaultArmorProtection;
        weaponDamage=defaultWeaponDamage;
        itemName=defaultItemName;
        potionStrength=defaultPotionStrength;        
        portalExit = defaultPortalExit;
        dimension = defaultDimension;
    }
    public Point getEditCursor(){
        return cursor;
    }
    public Point getPlacement(){
        return placement;
    }
    public DimensionDouble getDimension(){
        return dimension;
    }
    public EntityTile[] getDetailedSelectors(){
        return detailedSelectors;
    }
    public void setEditCursor(Point cursor){
        this.cursor = cursor;
    }
    public void setPlacement(Point placement){
        this.placement = placement;
    }
    public void setDimension(DimensionDouble dimension){
        this.dimension = dimension;
    }   
    public void setDetailedSelectors(EntityTile[] detailedSelectors){
        this.detailedSelectors = detailedSelectors;
    }
    public void cursorToPlacement(Point c){
        double x = cursor.getX();
        double y = cursor.getY();
        double modx = x%10;
        double mody = y%10;        
        placement.setX(x-modx);
        placement.setY(y-mody);
    }
     @Override
    public void mouseMoved(MouseEvent e) {
        cursor.setX(e.getX());
        cursor.setY(e.getY());
        cursorToPlacement(cursor);
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        int py = (int)(placement.getY()/10);
        int px = (int)(placement.getX()/10);
        if(SwingUtilities.isLeftMouseButton(e)){            
            points[py][px] = new Point(placement.getX(),placement.getY());
            createBasedOnId(py,px,points[py][px]);
        }
        if(SwingUtilities.isRightMouseButton(e)){
            points[py][px] = null;
            npcsOnGrid[py][px]=null;
        }
    }  
    //private Entity createLinkForPortal(){}
    
    public void createBasedOnId(int r, int c, Point p){
        if(currentDetailedSelectorId==null){
            npcsOnGrid[r][c] = new Entity(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight());
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("Player")){
            npcsOnGrid[r][c] = new nelsontsui.nelsonsgame.game.Character(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),
                    speed,speed,projectileSpeed,name,hitpoints,damage);
        }
        //DetailedSelectors-Entity
        else if(currentDetailedSelectorId.equalsIgnoreCase("Entity")){
            npcsOnGrid[r][c] = new Entity(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight());
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("OpaqueEntity")){
            npcsOnGrid[r][c] = new OpaqueEntity(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight());
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("DamagableEntity")){
            npcsOnGrid[r][c] = new DamagableEntity(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),hitpoints);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("TalkableGate")){
            npcsOnGrid[r][c] = new TalkableGate(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),talkableSpeech);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("MapGateReachGate")){
            npcsOnGrid[r][c] = new MapGate(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),MapGate.REACH_GATE);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("MapGateKillAll")){
            npcsOnGrid[r][c] = new MapGate(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),MapGate.KILL_ALL);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("MainPortalDefault")){
            npcsOnGrid[r][c] = new Portal(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),new Entity(portalExit.getX(),portalExit.getY(),dimension.getWidth(),dimension.getHeight()),Portal.DEFAULT,true);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("SubPortalDefault")){
            npcsOnGrid[r][c] = new Portal(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),new Entity(portalExit.getX(),portalExit.getY(),dimension.getWidth(),dimension.getHeight()),Portal.DEFAULT,false);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("MainPortalKillAll")){
            npcsOnGrid[r][c] = new Portal(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),new Entity(0,0,10,10),Portal.KILL_ALL_NONBOSS,true);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("SubPortalKillAll")){
            npcsOnGrid[r][c] = new Portal(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),new Entity(0,0,10,10),Portal.KILL_ALL_NONBOSS,false);
        }
        //DetailSelectors - NPCS
        else if(currentDetailedSelectorId.equalsIgnoreCase("Warrior")){
            npcsOnGrid[r][c] = new NonPlayerCharacter(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),
                    speed,speed,projectileSpeed,name,hitpoints,damage,(int)detectionRadius,NonPlayerCharacter.WARRIOR);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("BossroomWarrior")){
            npcsOnGrid[r][c] = new NonPlayerCharacter(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),
                    speed,speed,projectileSpeed,name,hitpoints,damage,(int)detectionRadius,NonPlayerCharacter.BR_WARRIOR);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("Archer")){
            npcsOnGrid[r][c] = new NonPlayerCharacter(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),
                    speed,speed,projectileSpeed,name,hitpoints,damage,(int)detectionRadius,NonPlayerCharacter.ARCHER);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("BossroomArcher")){
            npcsOnGrid[r][c] = new NonPlayerCharacter(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),
                    speed,speed,projectileSpeed,name,hitpoints,damage,(int)detectionRadius,NonPlayerCharacter.BR_ARCHER);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("Tank")){
            npcsOnGrid[r][c] = new NonPlayerCharacter(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),
                    speed,speed,projectileSpeed,name,hitpoints,damage,(int)detectionRadius,NonPlayerCharacter.TANK);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("BossroomTank")){
            npcsOnGrid[r][c] = new NonPlayerCharacter(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),
                    speed,speed,projectileSpeed,name,hitpoints,damage,(int)detectionRadius,NonPlayerCharacter.BR_TANK);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("Subboss")){
            npcsOnGrid[r][c] = new NonPlayerCharacter(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),
                    speed,speed,projectileSpeed,name,hitpoints,damage,(int)detectionRadius,NonPlayerCharacter.SUBBOSS);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("BossroomSubboss")){
            npcsOnGrid[r][c] = new NonPlayerCharacter(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),
                    speed,speed,projectileSpeed,name,hitpoints,damage,(int)detectionRadius,NonPlayerCharacter.BR_SUBBOSS);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("Boss")){
            npcsOnGrid[r][c] = new NonPlayerCharacter(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),
                    speed,speed,projectileSpeed,name,hitpoints,damage,(int)detectionRadius,NonPlayerCharacter.BOSS);
        }
        //detailedSelectors-Items
        else if(currentDetailedSelectorId.equalsIgnoreCase("Armor")){
            npcsOnGrid[r][c] = new SpawnableItem(p.getX(),p.getY(),new Armor(itemName,quantity));
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("Weapon")){
            npcsOnGrid[r][c] = new SpawnableItem(p.getX(),p.getY(),new Weapon(itemName,quantity));
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("Bow")){
            npcsOnGrid[r][c] = new SpawnableItem(p.getX(),p.getY(),new Bow(itemName,quantity));
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("Arrow")){
            npcsOnGrid[r][c] = new SpawnableItem(p.getX(),p.getY(),new Arrow(itemName,quantity));
        }
         else if(currentDetailedSelectorId.equalsIgnoreCase("HealthPotion")){
            npcsOnGrid[r][c] = new SpawnableItem(p.getX(),p.getY(),new HealthPotion(itemName,quantity,potionStrength));
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("StrengthPotion")){
            npcsOnGrid[r][c] = new SpawnableItem(p.getX(),p.getY(),new StrengthPotion(itemName,quantity,potionStrength));
        }       
        else{
            npcsOnGrid[r][c] = new Entity(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight());
        }
        
        
    }
    public void checkForId(){
        //protected EntityTile[] detailedSelectors;
        //protected String currentDetailedSelectorID;
        for(int i=0;i<LevelEditorDisplay.TOTAL_DETAILEDSELECTORS;i++){
            if(detailedSelectors[i]==null){}
            else{
                if(detailedSelectors[i].getClicked()){
                    currentDetailedSelectorId = detailedSelectors[i].getId();
                }
            }
        }
    }
    @Override
    public void paintComponent(Graphics g){
        Graphics2D graphic = (Graphics2D)g;
        g.setColor(new Color(250,250,210));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(getForeground());
        for(int i=10;i<gridColumns*10;i+=10){  
            graphic.drawLine(i,0,i,gridRows*10);
        }
        for(int j=10;j<gridRows*10;j+=10){
            graphic.drawLine(0,j,gridColumns*10,j);
        }
        for(int z = 0;z<gridRows;z++){
            for(int k = 0;k<gridColumns;k++){
                Point p = points[z][k];
                Entity e = npcsOnGrid[z][k];
                if(p==null||e==null){}
                else{
                    colorEntities(e,graphic);
                    graphic.fill(new Rectangle2D.Double(p.getX(),p.getY(),e.getWidth(),e.getHeight()));
                }
            }
        }
    }
    public void colorEntities(Entity temp, Graphics2D graphic){
        if(temp instanceof Portal){
            if(((Portal)temp).getIsMain()){
                graphic.setColor(new Color(233,118,17));//Orange
            }
            else{
                graphic.setColor(new Color(43,127,223));//Blue
            }
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
        }
        else if(temp instanceof DamagableEntity){
            graphic.setColor(Color.GRAY);//greenish                    
        }
        else if(temp instanceof SpawnableItem){
            graphic.setColor(new Color(188,101,121));                    
        }
        else if(temp instanceof nelsontsui.nelsonsgame.game.Character){
            graphic.setColor(new Color(63,131,104));//Cyanish Green     
        }
        else{
            graphic.setColor(Color.DARK_GRAY);//greenish                    
        }
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    
    
    
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        checkForId();
        repaint();
    }
    
}
