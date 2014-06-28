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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import nelsontsui.nelsonsgame.game.DimensionDouble;
import nelsontsui.nelsonsgame.game.Point;

public class CustomOptionsPanel extends JDialog implements ActionListener{
    /*
    //for characters & npcs
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
    
    public static final int TOTAL_FIELDS = 14;
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
    
    
    protected JTextField[] fields = new JTextField[TOTAL_FIELDS];
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
        fields[DIMENSIONS] = new JTextField(""+editPanel.dimension.getWidth()+","+editPanel.dimension.getHeight());
        fields[PORTALEXIT] = new JTextField(""+editPanel.portalExit.getX()+","+editPanel.portalExit.getY());
        
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
        nameOfFields[DIMENSIONS] = new JLabel("Dimension of Entity");
        nameOfFields[PORTALEXIT] = new JLabel("Exit Coordinates of Portal(Seperated by comma [,])");
        
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
        previousValues[DIMENSIONS] = new JLabel("Current: ("+editPanel.dimension.getWidth()+","+editPanel.dimension.getHeight()+")");
        previousValues[PORTALEXIT] = new JLabel("Current: ("+editPanel.portalExit.getX()+","+editPanel.portalExit.getY()+")");
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
        update.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                setValues();
                dispose();
            }
        });
    }
    private void setValues(){
        if(fields[NAME].getText()!=null){
            editPanel.name = fields[NAME].getText();
        }
        if((fields[HITPOINTS]!=null)&&isStringNumerical(fields[HITPOINTS].getText())){
            editPanel.hitpoints = Double.parseDouble(fields[HITPOINTS].getText());
        }
        if((fields[DAMAGE]!=null)&&isStringNumerical(fields[DAMAGE].getText())){
            editPanel.damage = Double.parseDouble(fields[DAMAGE].getText());
        }
        if((fields[DETECTIONRADIUS]!=null)&&isStringNumerical(fields[DETECTIONRADIUS].getText())){
            editPanel.detectionRadius = Double.parseDouble(fields[DETECTIONRADIUS].getText());
        }
        if((fields[PROJECTILESPEED]!=null)&&isStringNumerical(fields[PROJECTILESPEED].getText())){
            editPanel.projectileSpeed = Double.parseDouble(fields[PROJECTILESPEED].getText());
        }
        if(fields[TALKABLESPEECH]!=null){
            editPanel.talkableSpeech = fields[TALKABLESPEECH].getText();
        }
        if((fields[QUANTITY]!=null)&&isStringNumerical(fields[QUANTITY].getText())){
            editPanel.quantity = (int)Double.parseDouble(fields[QUANTITY].getText());
        }
        if((fields[ARMORPROTECTION]!=null)&&isStringNumerical(fields[ARMORPROTECTION].getText())){
            editPanel.armorProtection = (int)Double.parseDouble(fields[ARMORPROTECTION].getText());
        }
        if((fields[WEAPONDAMAGE]!=null)&&isStringNumerical(fields[WEAPONDAMAGE].getText())){
            editPanel.weaponDamage = (int)Double.parseDouble(fields[WEAPONDAMAGE].getText());
        }
        if(fields[ITEMNAME]!=null){
            editPanel.itemName = fields[ITEMNAME].getText();
        }
        if((fields[POTIONSTRENGTH]!=null)&&isStringNumerical(fields[POTIONSTRENGTH].getText())){
            editPanel.potionStrength = (int)Double.parseDouble(fields[POTIONSTRENGTH].getText());
        }
        if(fields[DIMENSIONS]!=null){
            DimensionDouble d = decodeDimension(fields[DIMENSIONS].getText());
            if(d!=null){
                editPanel.dimension = d;
            }
        } 
        if(fields[PORTALEXIT]!=null){
            Point p = decodeCoords(fields[PORTALEXIT].getText());
            if(p!=null){
                editPanel.portalExit = p;
            }
        }       
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
