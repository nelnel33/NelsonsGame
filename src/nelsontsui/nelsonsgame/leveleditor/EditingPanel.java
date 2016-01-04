/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.leveleditor;

import nelsontsui.nelsonsgame.game.entities.TalkableGate;
import nelsontsui.nelsonsgame.game.entities.OpaqueEntity;
import nelsontsui.nelsonsgame.game.entities.Player;
import nelsontsui.nelsonsgame.game.mapping.Level;
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
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import nelsontsui.nelsonsgame.game.*;
import nelsontsui.nelsonsgame.game.entities.DamageableEntity;
import nelsontsui.nelsonsgame.game.DimensionDouble;
import nelsontsui.nelsonsgame.game.entities.Entity;
import nelsontsui.nelsonsgame.game.entities.MapGate;
import nelsontsui.nelsonsgame.game.entities.NonPlayerCharacter;
import nelsontsui.nelsonsgame.game.mapping.Point;
import nelsontsui.nelsonsgame.game.entities.Portal;
import nelsontsui.nelsonsgame.game.entities.Projectile;
import nelsontsui.nelsonsgame.game.entities.SpawnableItem;
import nelsontsui.nelsonsgame.game.items.Armor;
import nelsontsui.nelsonsgame.game.items.Ammo;
import nelsontsui.nelsonsgame.game.items.ProjectileWeapon;
import nelsontsui.nelsonsgame.game.items.HealthPotion;
import nelsontsui.nelsonsgame.game.items.StrengthPotion;
import nelsontsui.nelsonsgame.game.items.Weapon;

/**
 * 
 * @author Nelnel33, Alan T
 */
public class EditingPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener{       
    protected Point cursor;//where the cursor is;
    protected Point placement;//where the entity will be placed
    protected DimensionDouble dimension;//how large the entity will be (dimension by dimension)
    
    //written to and read from
    private Level level;
    protected ArrayList<Entity> entities = new ArrayList<>();
    protected Player player;
    
    //check if JOptionPane should be prompted
    protected boolean playerSet=false;
    protected boolean placedSomething=false;
    
    //for the grid itself
    protected static final int gridRows = 40;
    protected static final int gridColumns = 74;
    protected Point[][] points = new Point[gridRows][gridColumns];
    protected DimensionDouble[][] dimensions = new DimensionDouble[gridRows][gridColumns]; 
    protected Entity[][] npcsOnGrid = new Entity[gridRows][gridColumns];
    
    //what has been selected
    protected EntityTile[] detailedSelectors = new EntityTile[LevelEditorDisplay.TOTAL_DETAILEDSELECTORS];
    protected String currentDetailedSelectorId;
    
    //dragger/highlighter
    protected Point originalPoint;
    protected Entity highlighter;
    protected boolean mousePressed;       
    protected boolean mouseDragged;
    
    //default entity that has been selected
    protected final String defaultDetailedSelectorId = "Entity";
    
    //defaultOptions
    protected final String defaultName="Name";
    protected final int defaultHitpoints=20;
    protected final int defaultDamage=2;
    protected final int defaultDetectionRadius=200;
    protected final int defaultProjectileSpeed=200;
    protected final int defaultSpeed=50;
    protected final String defaultTalkableSpeech="Set the speech of a TalkableGate by clicking custom, which is underneath configure.";
    protected final int defaultQuantity=1;
    protected final int defaultArmorProtection=5;
    protected final int defaultWeaponDamage=5;
    protected final String defaultItemName="Item";
    protected final int defaultPotionStrength=2;
    protected final Point defaultPortalExit = new Point(0,0);    
    protected final DimensionDouble defaultDimension = new DimensionDouble(10,10);
    protected final File defaultCurrentFile = new File("null");
            
    
    //for characters & entities
    protected String name;
    protected int hitpoints;
    protected int damage;
    protected int detectionRadius;
    protected int projectileSpeed;
    protected int speed;
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
    //for mapgates
    protected File currentFile;

    public static final int TOP_LEFT = 100;
    public static final int TOP_RIGHT = 200;
    public static final int BOTTOM_LEFT = 300;
    public static final int BOTTOM_RIGHT = 400;
    
    private static int increment = 0;
    
    private boolean hasPlacedFirstPortal;
    private Portal currPortal;
    
    public EditingPanel(Point cursor, Point placement, DimensionDouble dimension){
        this.cursor = cursor;
        this.placement = placement;
        this.dimension = dimension;
        
        initDefaults();//must be public called elsewhere;        
        
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
        currentFile = defaultCurrentFile;
        hasPlacedFirstPortal = false;
        currPortal = null;
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
    public void mousePressed(MouseEvent e) {
        int py = (int)(placement.getY()/10);
        int px = (int)(placement.getX()/10);
        if(SwingUtilities.isLeftMouseButton(e)){            
            originalPoint = new Point(placement.getX(),placement.getY());
            mousePressed = true;
            System.out.println("pressed @"+originalPoint.getX()+","+originalPoint.getY());
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e) && mousePressed){      
            int ox = (int)originalPoint.getX();
            int oy = (int)originalPoint.getY();
            Point temp = cursorToDimension(new Point(e.getX(),e.getY()));
            double width = (temp.getX())-ox;
            double height = (temp.getY())-oy;
            //if(ox!=placement.getX() && oy!=placement.getY()){
                highlighter = new Entity(ox,oy,width,height);
                highlighter = reorientEntity(highlighter);
                mouseDragged = true;
                System.out.println("drag from point:"+originalPoint.getX()+","+originalPoint.getY()+" to dimen:"+width+","+height);
            //}
        }
    }
    public Point cursorToDimension(Point c){
        double x = c.getX();
        double y = c.getY();
        double modx = x%10;
        double mody = y%10;    
        double newx = (x-modx)+10;
        double newy = (y-mody)+10;
        return new Point(newx,newy);
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        int py = (int)(placement.getY()/10);
        int px = (int)(placement.getX()/10);
        if(SwingUtilities.isLeftMouseButton(e)){  
            if(mousePressed&&mouseDragged){
                int ox = (int)highlighter.getX();
                int oy = (int)highlighter.getY();
                double width = highlighter.getWidth();
                double height = highlighter.getHeight();
                mousePressed = false;
                mouseDragged = false;
                createBasedOnId(oy/10,ox/10,new Point(ox,oy),new DimensionDouble(width,height));
                placedSomething = true;
                highlighter = null;
                originalPoint = null;
                System.out.println("rel @"+px+","+py+"; point: "+ox+","+oy+"; dimen:"+width+","+height);
            }
            else{
                points[py][px] = new Point(placement.getX(),placement.getY());
                createBasedOnId(py,px,points[py][px],dimension);
                placedSomething=true;
            }
        }
        if(SwingUtilities.isRightMouseButton(e)){
            // outerloop and innerloop checks through the entire matrix
            outerloop : for(int i=0;i<gridRows;i++){
                innerloop : for(int j=0;j<gridColumns;j++){
                    if(npcsOnGrid[i][j] != null){
                        double x = npcsOnGrid[i][j].getX();
                        double y = npcsOnGrid[i][j].getY();
                        double width = npcsOnGrid[i][j].getWidth();
                        double height = npcsOnGrid[i][j].getHeight();
                        //this checks if the cursor is intersecting an entity
                        if(new java.awt.Rectangle.Double(x, y, width, height).intersects(new java.awt.Rectangle.Double(e.getX(),e.getY(),1,1))){
                            if((npcsOnGrid[i][j] instanceof nelsontsui.nelsonsgame.game.entities.Character)&&!(npcsOnGrid[i][j] instanceof NonPlayerCharacter)){
                                playerSet=false;
                            }
                            points[i][j] = null;
                            npcsOnGrid[i][j]=null;
                            break outerloop;
                        }
                    }
                }
            }
        }
    }  
    public int determineSupposedPointOrigin(Point origin, Point released){
        if(origin.getX()-released.getX()<0){
            if(origin.getY()-released.getY()<0){
                return TOP_LEFT;
            }
            else{
                return BOTTOM_LEFT;
            }
        }
        else{
            if(origin.getY()-released.getY()<0){
                return TOP_RIGHT;
            }
            else{
                return BOTTOM_RIGHT;
            }
        }
    }
    public Entity reorientEntity(Entity e){
        int x = (int)e.getX();
        int y = (int)e.getY();
        int width = (int)e.getWidth();
        int height = (int)e.getHeight();        
        int rx = x+width;
        int ry = y+height;
        int aw = Math.abs(width);
        int ah = Math.abs(height);
        Point origin = new Point(x,y);
        Point rel = new Point(rx,ry);
        int po = determineSupposedPointOrigin(origin,rel);
        
        if(po == TOP_LEFT){
            return e;
        }
        else if(po == BOTTOM_LEFT){
            return new Entity(rx,ry,aw,ah);
        }
        else if(po == TOP_RIGHT){
            return new Entity(rx,y,aw,ah);
        }
        else if(po == BOTTOM_RIGHT){
            return new Entity(rx,ry,aw,ah);
        }
        else{
            System.out.println("CANNOT REORIENT");
            return null;
        }
    }
    public void createBasedOnId(int r, int c, Point p, DimensionDouble dimension){
        if(currentDetailedSelectorId==null){
            npcsOnGrid[r][c] = new Entity(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight());
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("Player")){
            if(playerSet==false){
                npcsOnGrid[r][c] = new Player(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),
                    speed,speed,projectileSpeed,name,hitpoints,damage);
                playerSet=true;
            }
            else{
                JOptionPane.showMessageDialog(this, "You cannot place more than one Player!", "Too Many Players!", JOptionPane.INFORMATION_MESSAGE);
            }
            
        }
        //DetailedSelectors-Entity
        else if(currentDetailedSelectorId.equalsIgnoreCase("Entity")){
            npcsOnGrid[r][c] = new Entity(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight());
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("OpaqueEntity")){
            npcsOnGrid[r][c] = new OpaqueEntity(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight());
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("DamagableEntity")){
            npcsOnGrid[r][c] = new DamageableEntity(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),hitpoints);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("TalkableGate")){
            npcsOnGrid[r][c] = new TalkableGate(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),talkableSpeech);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("MapGateReachGate")){
            npcsOnGrid[r][c] = new MapGate(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),MapGate.REACH_GATE);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("MapGateReachGateExit")){
            npcsOnGrid[r][c] = new MapGate(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),MapGate.REACH_GATE);
            if(currentFile.getName()!=null){
                ((MapGate)npcsOnGrid[r][c]).setFile(currentFile);
            }
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("MapGateKillAll")){
            npcsOnGrid[r][c] = new MapGate(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),MapGate.KILL_ALL);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("MapGateKillAllExit")){
            npcsOnGrid[r][c] = new MapGate(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),MapGate.KILL_ALL);
            if(currentFile.getName()!=null){
                ((MapGate)npcsOnGrid[r][c]).setFile(currentFile);
            }
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("MainPortalDefault")){
            Portal curr = new Portal(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),new Entity(portalExit.getX(),portalExit.getY(),dimension.getWidth(),dimension.getHeight()),Portal.DEFAULT,true);
            npcsOnGrid[r][c] = curr;
            togglePortalPlacement(curr);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("SubPortalDefault")){
            Portal curr = new Portal(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),new Entity(portalExit.getX(),portalExit.getY(),dimension.getWidth(),dimension.getHeight()),Portal.DEFAULT,false);
            npcsOnGrid[r][c] = curr;
            togglePortalPlacement(curr);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("MainPortalKillAll")){
            Portal curr = new Portal(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),new Entity(portalExit.getX(),portalExit.getY(),dimension.getWidth(),dimension.getHeight()),Portal.KILL_ALL_NONBOSS,true);
            npcsOnGrid[r][c] = curr;
            togglePortalPlacement(curr);
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("SubPortalKillAll")){
            Portal curr = new Portal(p.getX(),p.getY(),dimension.getWidth(),dimension.getHeight(),new Entity(portalExit.getX(),portalExit.getY(),dimension.getWidth(),dimension.getHeight()),Portal.KILL_ALL_NONBOSS,false);
            npcsOnGrid[r][c] = curr;
            togglePortalPlacement(curr);
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
            npcsOnGrid[r][c] = new SpawnableItem(p.getX(),p.getY(),new Armor(itemName,armorProtection));
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("Weapon")){
            npcsOnGrid[r][c] = new SpawnableItem(p.getX(),p.getY(),new Weapon(itemName,weaponDamage));
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("ProjectileWeapon")){
            npcsOnGrid[r][c] = new SpawnableItem(p.getX(),p.getY(),new ProjectileWeapon(itemName,weaponDamage));
        }
        else if(currentDetailedSelectorId.equalsIgnoreCase("Ammo")){
            npcsOnGrid[r][c] = new SpawnableItem(p.getX(),p.getY(),new Ammo(itemName,quantity));
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
    public void togglePortalPlacement(Portal curr){
        if(hasPlacedFirstPortal){
            hasPlacedFirstPortal = false;
            currPortal.setSubportal(curr);
            curr.setSubportal(currPortal);
            currPortal = null;            
        }
        else{
            hasPlacedFirstPortal = true;
            currPortal = curr;
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
                    break;
                }
                else{
                    currentDetailedSelectorId = defaultDetailedSelectorId;
                }
            }
        }        
    }
    public void setGameNpcs(){
        entities = new ArrayList<>();
        for(int i=0;i<npcsOnGrid.length;i++){//adds all entities on grid into entities
            for(int j=0;j<npcsOnGrid[i].length;j++){
                if(npcsOnGrid[i][j]!=null){
                    if((npcsOnGrid[i][j] instanceof Player)&&
                            !(npcsOnGrid[i][j] instanceof NonPlayerCharacter)){
                        player = (Player)npcsOnGrid[i][j];
                    }
                    else{
                        entities.add(npcsOnGrid[i][j]);
                    }
                }
            }
        }
    }
    public ArrayList<Entity> getEntities(){//TO USE MUST IMPLEMENT setGameNpcs()!!!!
       return entities;
    }
    public Player getPlayer(){//TO USE MUST IMPLEMENT setGameNpcs()!!!!
        return player;
    }
    public Level getLevel(){
        return level;
    }
    public void setEntities(ArrayList<Entity> entities){
        this.entities = entities;
    }
    public void setPlayer(Player player){
        this.player = player;
    }
    public void setLevel(Level level){
        this.level = level;
        setPlayer(level.getPlayer());
        setEntities(level.getEntities());
    }
    public void setGridNpcs(){
        /*
        for(int i=0;i<npcsOnGrid.length;i++){
            for(int j=0;j<npcsOnGrid[i].length;j++){
                npcsOnGrid[i][j] = null;
            }
        }*/
        if(player==null || entities==null){}
        else{
            npcsOnGrid = new Entity[gridRows][gridColumns];        
            int px = ((int)player.getX())/10;
            int py = ((int)player.getY())/10;
            npcsOnGrid[py][px] = player;
            playerSet = true;
            if(!entities.isEmpty()){
                for(int i=0;i<entities.size();i++){
                    int nx = ((int)entities.get(i).getX())/10;
                    int ny = ((int)entities.get(i).getY())/10;
                    npcsOnGrid[ny][nx] = entities.get(i);
                    //System.out.println("Placed "+npcsOnGrid[ny][nx]+"@"+nx+","+ny);
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
                //Point p = points[z][k];
                Entity e = npcsOnGrid[z][k];
                if(e==null){}
                else{
                    e.render(graphic);
                    //colorEntities(e,graphic);
                    //graphic.fill(new Rectangle2D.Double(e.getX(),e.getY(),e.getWidth(),e.getHeight()));
                    //System.out.println("painted"+e+"@"+e.getX()+","+e.getY());
                }
            }
        }
        
        drawHighlighter(highlighter,graphic);
    }
    public void drawHighlighter(Entity e, Graphics2D graphic){
        if(highlighter!=null){
            graphic.setColor(Color.MAGENTA);
            graphic.fill(new Rectangle2D.Double(highlighter.getX(),highlighter.getY(),highlighter.getWidth(),highlighter.getHeight()));
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
        else if(temp instanceof nelsontsui.nelsonsgame.game.entities.Character){
            graphic.setColor(new Color(63,131,104));//Cyanish Green     
        }
        else if(temp instanceof DamageableEntity){
            graphic.setColor(Color.GRAY);//grey                    
        }
        else if(temp instanceof MapGate || temp instanceof TalkableGate){
            graphic.setColor(new Color(192,192,192));
        }
        else if(temp instanceof SpawnableItem){
            graphic.setColor(new Color(188,101,121));                    
        }
        else if(temp instanceof OpaqueEntity){
            graphic.setColor(Color.DARK_GRAY);
        }
        else{
            graphic.setColor(new Color(0,204,0));//greenish                    
        }
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        checkForId();
        repaint();
    }
    
}
