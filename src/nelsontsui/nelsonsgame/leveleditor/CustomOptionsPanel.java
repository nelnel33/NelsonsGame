/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.leveleditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import nelsontsui.nelsonsgame.game.DimensionDouble;
import nelsontsui.nelsonsgame.game.FileSelector;
import nelsontsui.nelsonsgame.game.Point;

public class CustomOptionsPanel extends JDialog implements ActionListener{
    /*
    //for characters & entities
    protected String name=defaultName;
    protected double hitpoints=defaultHitpoints;
    protected double damage=defaultDamage;
    protected double detectionRadius=defaultDetectionRadius;
    protected double projectileSpeed=defaultProjectileSpeed;
    protected double speed=defaultSpeed;
    //for talkablegates
    protected String talkableSpeech=defaultTalkableSpeech;
    //for items
    protected int quantity=defaultQuantity;
    protected int armorProtection=defaultArmorProtection;
    protected int weaponDamage=defaultWeaponDamage;
    protected String itemName=defaultItemName;
    protected int potionStrength=defaultPotionStrength;
    //for portals
    protected Point portalExit = defaultPortalExit;
    */
    private JButton update;
    private EditingPanel editPanel;
    
    private JPanel nameAndFieldHolder;
    private JPanel previousValueHolder;
    
    public static final int TOTAL_FIELDS = 15;
    public static final int NAME=0;
    public static final int HITPOINTS=1;
    public static final int DAMAGE=2;
    public static final int DETECTIONRADIUS=3;
    public static final int PROJECTILESPEED=4;
    public static final int SPEED=5;
    public static final int TALKABLESPEECH=6;
    public static final int QUANTITY=7;
    public static final int ARMORPROTECTION=8;
    public static final int WEAPONDAMAGE=9;
    public static final int ITEMNAME=10;
    public static final int POTIONSTRENGTH=11;
    public static final int DIMENSIONS=12;
    public static final int PORTALEXIT=13;
    public static final int MAPGATEFILE = 14;
    
    
    protected JComponent[] fields = new JComponent[TOTAL_FIELDS];
    protected JLabel[] nameOfFields = new JLabel[TOTAL_FIELDS];
    protected JLabel[] previousValues = new JLabel[TOTAL_FIELDS];
    protected JLabel[] buffers = new JLabel[TOTAL_FIELDS];
            
    public CustomOptionsPanel(JDialog owner,EditingPanel editPanel){
        super(owner,"Nelson's Game: Custom Configurations");
        this.editPanel = editPanel;
        this.setLayout(null);
        
        getContentPane().setBackground(new Color(197,179,88));
        
        fieldsInit();      
        addComponents();
        updateAddAction();
        
        setPreferredSize(new Dimension(nameAndFieldHolder.getPreferredSize().width+previousValueHolder.getPreferredSize().width,
            nameAndFieldHolder.getPreferredSize().height+update.getPreferredSize().height+30));
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
        pack();            
        setLocationRelativeTo(owner);
    }
    private void fieldsInit(){
        nameAndFieldHolder = new JPanel();
        nameAndFieldHolder.setLayout(new GridLayout(TOTAL_FIELDS*2,1));
        previousValueHolder = new JPanel();
        previousValueHolder.setLayout(new GridLayout(TOTAL_FIELDS*2,1));
        
        update = new JButton("Confirm");
        update.setToolTipText("Close Window To Cancel");
        
        for(int i=0;i<fields.length;i++){
            buffers[i] = new JLabel();
        }
        
        fields[NAME] = new JTextField(editPanel.name);
        fields[HITPOINTS] = new JTextField(""+editPanel.hitpoints);
        fields[DAMAGE] = new JTextField(""+editPanel.damage);
        fields[DETECTIONRADIUS] = new JTextField(""+editPanel.detectionRadius);
        fields[PROJECTILESPEED] = new JTextField(""+editPanel.projectileSpeed);
        fields[SPEED] = new JTextField(""+editPanel.speed);
        fields[TALKABLESPEECH] = new JTextField(editPanel.talkableSpeech);
        fields[QUANTITY] = new JTextField(""+editPanel.quantity);
        fields[ARMORPROTECTION] = new JTextField(""+editPanel.armorProtection);
        fields[WEAPONDAMAGE] = new JTextField(""+editPanel.weaponDamage);
        fields[ITEMNAME] = new JTextField(""+editPanel.itemName);
        fields[POTIONSTRENGTH] = new JTextField(""+editPanel.potionStrength);
        fields[DIMENSIONS] = new JTextField(""+(int)editPanel.dimension.getWidth()+","+(int)editPanel.dimension.getHeight());
        fields[PORTALEXIT] = new JTextField(""+(int)editPanel.portalExit.getX()+","+(int)editPanel.portalExit.getY());
        fields[MAPGATEFILE] = new JButton("Click to open a file");
        
        
        nameOfFields[NAME] = new JLabel("Name of NPCs or Player");
        nameOfFields[HITPOINTS] = new JLabel("Hitpoints of NPCs or Player");
        nameOfFields[DAMAGE] = new JLabel("Damage of NPCs or Player");
        nameOfFields[DETECTIONRADIUS] = new JLabel("Detection Radius of NPCs");
        nameOfFields[PROJECTILESPEED] = new JLabel("Projectile Speed of NPCs or Player");
        nameOfFields[SPEED] = new JLabel("Speed of NPCs or Player");
        nameOfFields[TALKABLESPEECH] = new JLabel("Speech of any Talkable-Gate");
        nameOfFields[QUANTITY] = new JLabel("Quantity of Items");
        nameOfFields[ARMORPROTECTION] = new JLabel("Protection of Armor");
        nameOfFields[WEAPONDAMAGE] = new JLabel("Damage of Weapon");
        nameOfFields[ITEMNAME] = new JLabel("Name of Item");
        nameOfFields[POTIONSTRENGTH] = new JLabel("Strength of Potion");
        nameOfFields[DIMENSIONS] = new JLabel("Dimension of Entity(Seperated by comma [,])");
        nameOfFields[PORTALEXIT] = new JLabel("Exit Coordinates of Portal(Seperated by comma [,])");
        nameOfFields[MAPGATEFILE] = new JLabel("File that the MapGate is linked to");
        
        previousValues[NAME] = new JLabel("Current: "+editPanel.name);
            previousValues[NAME].setToolTipText(editPanel.name);
        previousValues[HITPOINTS] = new JLabel("Current: "+editPanel.hitpoints);
        previousValues[DAMAGE] = new JLabel("Current: "+editPanel.damage);
        previousValues[DETECTIONRADIUS] = new JLabel("Current: "+editPanel.detectionRadius);
        previousValues[PROJECTILESPEED] = new JLabel("Current: "+editPanel.projectileSpeed);
        previousValues[SPEED] = new JLabel("Current: "+editPanel.speed);
        previousValues[TALKABLESPEECH] = new JLabel("Current: "+editPanel.talkableSpeech);
            previousValues[TALKABLESPEECH].setToolTipText(editPanel.talkableSpeech);
        previousValues[QUANTITY] = new JLabel("Current: "+editPanel.quantity);
        previousValues[ARMORPROTECTION] = new JLabel("Current: "+editPanel.armorProtection);
        previousValues[WEAPONDAMAGE] = new JLabel("Current: "+editPanel.weaponDamage);
        previousValues[ITEMNAME] = new JLabel("Current: "+editPanel.itemName);
            previousValues[ITEMNAME].setToolTipText(editPanel.itemName);
        previousValues[POTIONSTRENGTH] = new JLabel("Current: "+editPanel.potionStrength);
        previousValues[DIMENSIONS] = new JLabel("Current: ("+(int)editPanel.dimension.getWidth()+","+(int)editPanel.dimension.getHeight()+")");
        previousValues[PORTALEXIT] = new JLabel("Current: ("+(int)editPanel.portalExit.getX()+","+(int)editPanel.portalExit.getY()+")");
        previousValues[MAPGATEFILE] = new JLabel("Current: "+editPanel.currentFile);
            previousValues[MAPGATEFILE].setToolTipText(""+editPanel.currentFile);
    }
    private void addComponents(){
        for(int i=0;i<TOTAL_FIELDS;i++){
            nameAndFieldHolder.setBackground(new Color(197,179,88));
            nameAndFieldHolder.setBackground(new Color(197,179,88));
            previousValueHolder.setBackground(new Color(197,179,88));
            previousValueHolder.setBackground(new Color(197,179,88));           
            
            nameAndFieldHolder.add(nameOfFields[i]);
            nameAndFieldHolder.add(fields[i]);
            previousValueHolder.add(previousValues[i]);
            previousValueHolder.add(buffers[i]);
        }
        nameAndFieldHolder.setPreferredSize(new Dimension(350,600));
        previousValueHolder.setPreferredSize(new Dimension(300,600));
        update.setPreferredSize(new Dimension(70,20));
        nameAndFieldHolder.setBounds(0,0,nameAndFieldHolder.getPreferredSize().width,nameAndFieldHolder.getPreferredSize().height);
        previousValueHolder.setBounds(nameAndFieldHolder.getPreferredSize().width,0,previousValueHolder.getPreferredSize().width,previousValueHolder.getPreferredSize().height);
        update.setBounds((nameAndFieldHolder.getPreferredSize().width/2)-(update.getPreferredSize().width/2),nameAndFieldHolder.getPreferredSize().height,
                update.getPreferredSize().width,update.getPreferredSize().height);
        
        add(nameAndFieldHolder);
        add(previousValueHolder);
        add(update);        
    }
    private void updateAddAction(){
        ((JButton)fields[MAPGATEFILE]).addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                FileSelector cabinet = new FileSelector(FileSelector.IMPORT, null);
                if(cabinet.getFile()!=null){
                    editPanel.currentFile = cabinet.getFile();
                }
            }
        });
        update.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                setValues();
                dispose();
            }
        });
    }
    private void setValues(){
        if(((JTextField)fields[NAME]).getText()!=null){
            editPanel.name = ((JTextField)fields[NAME]).getText();
        }
        if((((JTextField)fields[HITPOINTS])!=null)&&isStringNumerical(((JTextField)fields[HITPOINTS]).getText())){
            int hitpoints = Integer.parseInt(((JTextField)fields[HITPOINTS]).getText());
            if(hitpoints>0){
                editPanel.hitpoints = hitpoints;
            }   
        }
        if((fields[DAMAGE]!=null) && isStringNumerical(((JTextField)fields[DAMAGE]).getText())){
            int damage = Integer.parseInt(((JTextField)fields[DAMAGE]).getText());
            editPanel.damage = damage;
        }
        if((fields[DETECTIONRADIUS]!=null)&&isStringNumerical(((JTextField)fields[DETECTIONRADIUS]).getText())){
            int detection = Integer.parseInt(((JTextField)fields[DETECTIONRADIUS]).getText());
            if(detection>0){
                editPanel.detectionRadius = detection;
            }
        }
        if((fields[PROJECTILESPEED]!=null)&&isStringNumerical(((JTextField)fields[PROJECTILESPEED]).getText())){
            int projectilespeed = Integer.parseInt(((JTextField)fields[PROJECTILESPEED]).getText());
            editPanel.projectileSpeed = projectilespeed;
            
        }
        if((fields[SPEED]!=null)&&isStringNumerical(((JTextField)fields[SPEED]).getText())){
            int speed = Integer.parseInt(((JTextField)fields[SPEED]).getText());
            editPanel.speed = speed;
        }
        if(fields[TALKABLESPEECH]!=null){
            editPanel.talkableSpeech = ((JTextField)fields[TALKABLESPEECH]).getText();
        }
        if((fields[QUANTITY]!=null)&&isStringNumerical(((JTextField)fields[QUANTITY]).getText())){
            int quantity = Integer.parseInt(((JTextField)fields[QUANTITY]).getText());
            if(quantity>0){
                editPanel.quantity = quantity;
            }
        }
        if((fields[ARMORPROTECTION]!=null)&&isStringNumerical(((JTextField)fields[ARMORPROTECTION]).getText())){
            int armor = Integer.parseInt(((JTextField)fields[ARMORPROTECTION]).getText());
            if(armor>0){
                editPanel.armorProtection = armor;
            }
        }
        if((fields[WEAPONDAMAGE]!=null)&&isStringNumerical(((JTextField)fields[WEAPONDAMAGE]).getText())){
            int weapon = Integer.parseInt(((JTextField)fields[WEAPONDAMAGE]).getText());
            if(weapon>0){
                editPanel.weaponDamage = weapon;
            }
        }
        if(fields[ITEMNAME]!=null){
            editPanel.itemName = ((JTextField)fields[ITEMNAME]).getText();
        }
        if((fields[POTIONSTRENGTH]!=null)&&isStringNumerical(((JTextField)fields[POTIONSTRENGTH]).getText())){
            int potion = Integer.parseInt(((JTextField)fields[POTIONSTRENGTH]).getText());
            if(potion>0){
                editPanel.potionStrength = Integer.parseInt(((JTextField)fields[POTIONSTRENGTH]).getText());
            }
        }
        if(fields[DIMENSIONS]!=null){
            DimensionDouble d = decodeDimension(((JTextField)fields[DIMENSIONS]).getText());
            if(d!=null){
                editPanel.dimension = d;
            }
        } 
        if(fields[PORTALEXIT]!=null){
            Point p = decodeCoords(((JTextField)fields[PORTALEXIT]).getText());
            if(p!=null){
                editPanel.portalExit = p;
            }
        }
        //fields[MAPGATEFILE] @ updateAddActions();
    }
    private Point decodeCoords(String s){
        double x;
        double y;
        String sx="";
        String sy="";
        int h=0;
        for(int i=0;i<s.length();i++){
            if(!(s.charAt(i)==',')){
                sx=sx+s.charAt(i);
            }
            else if(s.charAt(i)==','){
                h=i;
                break;
            }
        }
        for(int j=h+1;j<s.length();j++){
            sy = sy+s.charAt(j);
        }        
        if((!(sx.equals("")||sy.equals("")))&&(isStringNumerical(sx))&&(isStringNumerical(sy))){
            x = Double.parseDouble(sx);
            y = Double.parseDouble(sy);
            return new Point(x,y);
        }
        else{return null;}
    }
    private DimensionDouble decodeDimension(String s){
        double x;
        double y;
        String sx="";
        String sy="";
        int h=0;
        for(int i=0;i<s.length();i++){
            if(!(s.charAt(i)==',')){
                sx=sx+s.charAt(i);
            }
            else if(s.charAt(i)==','){
                h=i;
                break;
            }
        }
        for(int j=h+1;j<s.length();j++){
            sy = sy+s.charAt(j);
        }
        
        if((!(sx.equals("")||sy.equals("")))&&(isStringNumerical(sx))&&(isStringNumerical(sy))){
            x = Double.parseDouble(sx);
            y = Double.parseDouble(sy);
            return new DimensionDouble(x,y);
        }
        else{return null;}
    }
    private boolean isStringNumerical(String s){
        for(int i=0;i<s.length();i++){
            if(!Character.isDigit(s.charAt(i))){ 
                return false;
            } 
        }
        return true;
    }
    @Override
    public void actionPerformed(ActionEvent e) {}
}
