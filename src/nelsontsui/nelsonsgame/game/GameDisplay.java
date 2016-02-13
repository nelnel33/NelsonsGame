package nelsontsui.nelsonsgame.game;
import nelsontsui.nelsonsgame.game.entities.SpawnableItem;
import nelsontsui.nelsonsgame.game.entities.TalkableGate;
import nelsontsui.nelsonsgame.game.entities.Portal;
import nelsontsui.nelsonsgame.game.entities.OpaqueEntity;
import nelsontsui.nelsonsgame.game.entities.NonPlayerCharacter;
import nelsontsui.nelsonsgame.game.entities.Player;
import nelsontsui.nelsonsgame.game.entities.MapGate;
import nelsontsui.nelsonsgame.game.mapping.Level;
import nelsontsui.nelsonsgame.game.entities.helper.Inventory;
import nelsontsui.nelsonsgame.game.entities.DamageableEntity;
import nelsontsui.nelsonsgame.game.entities.Entity;
import nelsontsui.nelsonsgame.game.items.HealthPotion;
import nelsontsui.nelsonsgame.game.items.Weapon;
import nelsontsui.nelsonsgame.game.items.StrengthPotion;
import nelsontsui.nelsonsgame.game.items.ProjectileWeapon;
import nelsontsui.nelsonsgame.game.items.Ammo;
import nelsontsui.nelsonsgame.game.items.Armor;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;

public class GameDisplay extends JPanel implements ActionListener{ //JFrame to JPanel
    public long frameCount = 0;
    
    private Timer check= new Timer((int)ActionPanel.TICK,this);
    
    private Insets border;
    public static final Border blackborder = BorderFactory.createLineBorder(Color.black);   
    
    private Level level;
    protected Player player;
    protected ArrayList<Entity> entities = new ArrayList<>();
    
    //Main Panels
    protected ActionPanel activePanel;
    private JPanel statsPanel;
    private JPanel inventoryPanel;
    private JPanel controlPanel;
    private DialogBox dialogPanel;
    
    //Panels in statsPanel
    private JPanel statsLabel;
    private JPanel damageDisplay;
    private JLabel damageLabel;
    private JPanel hitpointsDisplay;
    private JLabel hitpointsLabel;
    private JPanel weaponDisplay;
    private JLabel weaponLabel;
    private JPanel armorDisplay;
    private JLabel armorLabel;
    private JPanel totalDamageDisplay;
    private JLabel totalDamageLabel;
    private JPanel totalHitpointsDisplay;
    private JLabel totalHitpointsLabel;
    private JPanel XPDisplay;
    private JLabel XPLabel;
    private JPanel levelDisplay;
    private JLabel levelLabel;
    
    //buttonPanel(below in the statsPanel; below "Statistics:")
    private JPanel buttonPanel;
    private JButton restartButton;
    private JButton importButton;
    private JButton toStartMenu;
    private JButton howTo;
    
    //Panels in inventoryPanel    
    private JPanel inventoryLabel;
    private InventoryIcon[] inventoryItems;
    private JLabel[] inventoryLabels;
    
    //Panels in dialogPanel
    
    //Panels in controlsPanel
    private JPanel controlsLabel;
    private EnhancedButton up;
    private EnhancedButton down;
    private EnhancedButton left;
    private EnhancedButton right;
    private JButton attack;
    private JButton shoot;
    private JButton defend;
    private JButton heal;
    
    //check if lost or won
    WinLose winLose;
    
    //Changable static variables
    public static final int ACTIONPANEL_WIDTH = 740;
    public static final int ACTIONPANEL_HEIGHT = 400;
    
    //Watermark
    NelsonWatermark watermark;    
    
    //Ingame Elements/Entities
    MapGate win;//TODO: Temporary    

    public GameDisplay(JButton toStartMenu, JButton restartButton, JButton importButton){
    //super("Nelson's Game"); //JFrame to JPanel
    this.toStartMenu = toStartMenu;
    this.restartButton = restartButton;
    this.importButton = importButton;
        
    createMap();
    
    createBackgroundLayout();    
    check.addActionListener(activePanel);
    check.addActionListener(dialogPanel);
    
    addWatermark();
    
    createStatsLayout();    
    createControlLayout();
    createInventoryLayout();
    
    controlButtonAction();
    
    initWinLose();    
    
    miscButtonAction();
    check.start();
    }
    
    public GameDisplay(JButton toStartMenu, JButton restartButton, JButton importButton, Level level){
    this.toStartMenu = toStartMenu;
    this.restartButton = restartButton;
    this.importButton = importButton;
    this.level = level;
    this.player = level.getPlayer();
    this.entities = level.getEntities();
    
    createBackgroundLayout();    
    check.addActionListener(activePanel);
    check.addActionListener(dialogPanel);
    
    addWatermark();
    
    createStatsLayout();    
    createControlLayout();
    createInventoryLayout();
    
    controlButtonAction();  
            
    initWinLose();
    
    miscButtonAction();
    check.start();
    }
    public void setLevel(Level level){
        this.level = level;
    }
    public void setEntities(ArrayList<Entity> entities){
        this.entities = entities;
    }
    public void setPlayer(Player Player){
        this.player = Player;
    }
    public void getFocus(){
        activePanel.requestFocusInWindow();
    }
    private void addWatermark(){
        watermark = new NelsonWatermark();
        watermark.setBounds(0,570,1020,20);
        add(watermark);
    }
    
    private void createBackgroundLayout(){
    setLayout(null);
    setPreferredSize(new Dimension(1000,600));
    //getContentPane().  //JFrame to JPanel
            setBackground(new Color(55,198,164));
    
    activePanel = new ActionPanel(level);
    
    statsPanel = new JPanel();
    inventoryPanel = new JPanel();
    controlPanel = new JPanel();
    dialogPanel = new DialogBox(300,150,new Color(197,179,88));
    
    activePanel.setDialogPanel(dialogPanel);
    
    border = getInsets();
    //System.out.println(getSize()+","+getWidth()+","+getHeight());
    
    activePanel.setBackground(new Color(228,221,181));
    activePanel.setPreferredSize(new Dimension(740,400));//890,500::1200,800
    //activePanel.setBorder(blackborder);
    //System.out.println("activePanel: "+activePanel.getAlignmentX()+","+activePanel.getAlignmentY()+")");
    
    statsPanel.setBackground(new Color(250,250,210));
    statsPanel.setPreferredSize(new Dimension(430,150));//890,250
    //statsPanel.setBorder(blackborder);
    //System.out.println("statsPanel: "+statsPanel.getBounds());
    
    inventoryPanel.setBackground(new Color(197,179,88));
    inventoryPanel.setPreferredSize(new Dimension(230,400));//280,500
    //inventoryPanel.setBorder(blackborder);
    //System.out.println("inventoryPanel: "+inventoryPanel.getBounds());
            
    controlPanel.setBackground(new Color(197,179,88));
    controlPanel.setPreferredSize(new Dimension(230,150));//280,250
    //controlPanel.setBorder(blackborder);
    //System.out.println("controlPanel: "+controlPanel.getBounds());
    
    dialogPanel.setBackground(new Color(197,179,88));
    dialogPanel.setPreferredSize(new Dimension(300,150));
    //dialogPanel.setBorder(blackborder);
    //System.out.println("dialogPanel: "+dialogPanel.getBounds());
    
    add(activePanel);
    add(statsPanel);
    add(inventoryPanel);
    add(controlPanel);
    add(dialogPanel);
    
    int activeWidth = activePanel.getPreferredSize().width;
    int activeHeight = activePanel.getPreferredSize().height;
    int activeGapX = 10+border.left; 
    int activeSpaceTakenX = activeGapX+activeWidth+10; 
    int activeGapY = 10+border.top; 
    int activeSpaceTakenY = activeGapY+activeHeight+10;     
    activePanel.setBounds(activeGapX,activeGapY,activeWidth,activeHeight);
    
    int statsWidth = statsPanel.getPreferredSize().width;
    int statsHeight = statsPanel.getPreferredSize().height;
    statsPanel.setBounds(activeGapX, activeSpaceTakenY, statsWidth, statsHeight);
    
    int inventoryWidth = inventoryPanel.getPreferredSize().width;
    int inventoryHeight = inventoryPanel.getPreferredSize().height;
    inventoryPanel.setBounds(activeSpaceTakenX, activeGapY, inventoryWidth, inventoryHeight);
    
    int controlWidth = controlPanel.getPreferredSize().width;
    int controlHeight = controlPanel.getPreferredSize().height;
    controlPanel.setBounds(activeSpaceTakenX, activeSpaceTakenY, controlWidth, controlHeight);
    
    int dialogWidth = dialogPanel.getPreferredSize().width;
    int dialogHeight = dialogPanel.getPreferredSize().height;
    dialogPanel.setBounds(activeGapX+statsWidth+10, activeSpaceTakenY, dialogWidth, dialogHeight);
    
    }
    private void createStatsLayout(){
        statsPanel.setLayout(new GridLayout(2,5,1,1));
        
        statsLabel = new JPanel();
        damageDisplay = new JPanel();
        hitpointsDisplay = new JPanel();
        weaponDisplay = new JPanel();
        armorDisplay = new JPanel();
        totalDamageDisplay = new JPanel();
        totalHitpointsDisplay = new JPanel();
        XPDisplay = new JPanel();
        levelDisplay = new JPanel();
   
        statsLabel.add(new JLabel("<html><br><b>Statistics<b>:<html>",SwingConstants.CENTER));
        //statsLabel.setBorder(blackborder);

        damageLabel = new JLabel("<html>Damage:<br><br><html>"
                +player.getDamage()
                ,SwingConstants.CENTER);
        damageDisplay.add(damageLabel);        
        //damageDisplay.setBorder(blackborder);
        
        hitpointsLabel = new JLabel("<html>Hitpoints:<br><br><html>"
                +player.getHitpoints()
                ,SwingConstants.CENTER);
        hitpointsDisplay.add(hitpointsLabel);
        //hitpointsDisplay.setBorder(blackborder);
        
        weaponLabel = new JLabel("<html>Weapon:<br><br><html>"
                +player.getWeaponDamage()
                ,SwingConstants.CENTER);
        weaponDisplay.add(weaponLabel);
        //weaponDisplay.setBorder(blackborder);
        
        armorLabel = new JLabel("<html>Armor:<br><br><html>"
                +player.getArmorHitpoints()
                ,SwingConstants.CENTER);
        armorDisplay.add(armorLabel);
        //armorDisplay.setBorder(blackborder);
        
        totalDamageLabel = new JLabel("<html>Total<br>Damage:<br><br> <html>"
                +player.getDamage()
                ,SwingConstants.CENTER);
        totalDamageDisplay.add(totalDamageLabel);
        //totalDamageDisplay.setBorder(blackborder);
        
        totalHitpointsLabel = new JLabel("<html>Total<br>Hitpoints:<br><br> <html>"
                +player.getHitpoints()
                ,SwingConstants.CENTER);
        totalHitpointsDisplay.add(totalHitpointsLabel);
        //totalHitpointsDisplay.setBorder(blackborder);
        
        XPLabel = new JLabel("<html>XP:<br><br><html>"+"null",SwingConstants.CENTER);
        XPDisplay.add(XPLabel);
        //XPDisplay.setBorder(blackborder);
        
        levelLabel = new JLabel("<html>Level:<br><br><html>"
                +player.getLevel()
                ,SwingConstants.CENTER);
        levelDisplay.add(levelLabel);
        //levelDisplay.setBorder(blackborder);
        
        statsLabel.setBackground(new Color(197,179,88));
        damageDisplay.setBackground(new Color(197,179,88));
        hitpointsDisplay.setBackground(new Color(197,179,88));
        weaponDisplay.setBackground(new Color(197,179,88));
        armorDisplay.setBackground(new Color(197,179,88));
        totalDamageDisplay.setBackground(new Color(197,179,88));
        totalHitpointsDisplay.setBackground(new Color(197,179,88));
        XPDisplay.setBackground(new Color(197,179,88));        
        levelDisplay.setBackground(new Color(197,179,88));
        
        setButtonPanelLayout();
        
        statsPanel.add(statsLabel);
        statsPanel.add(damageDisplay);
        statsPanel.add(weaponDisplay);
        statsPanel.add(totalDamageDisplay);
        statsPanel.add(XPDisplay);
        statsPanel.add(buttonPanel);
        statsPanel.add(hitpointsDisplay);
        statsPanel.add(armorDisplay);
        statsPanel.add(totalHitpointsDisplay);
        statsPanel.add(levelDisplay);      
    }
    private void setButtonPanelLayout(){//Directory Buttons(in the Stats Panel)
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4,1));
        buttonPanel.setBackground(new Color(197,179,88));
        
        //restartButton = new JButton("Save");
        //importButton = new JButton("Import");
        //toStartMenu = new JButton("Start Menu");
        howTo = new JButton("Instructions");
        
        toStartMenu.setBackground(new Color(197,179,88));        
        toStartMenu.setOpaque(true);
        howTo.setBackground(new Color(197,179,88));
        howTo.setOpaque(true);
        restartButton.setBackground(new Color(197,179,88));        
        restartButton.setOpaque(true);
        importButton.setBackground(new Color(197,179,88));
        importButton.setOpaque(true);
        
        toStartMenu.setToolTipText(toStartMenu.getText());
        howTo.setToolTipText(howTo.getText());
        restartButton.setToolTipText(restartButton.getText()+" - Only works for Campaign");
        importButton.setToolTipText(importButton.getText());    
        
        buttonPanel.add(toStartMenu);
        buttonPanel.add(howTo);
        buttonPanel.add(importButton);
        buttonPanel.add(restartButton);
    }
    private void miscButtonAction(){
        
        restartButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                activePanel.requestFocusInWindow();
            }
        });
        
        importButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                activePanel.requestFocusInWindow();
            }
        });
        
        toStartMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                activePanel.requestFocusInWindow();
            }
        });
        
        howTo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                activePanel.requestFocusInWindow();
                dialogPanel.message(
                        "||General Information||"+DialogBox.newline+
                        "**Make sure to scroll all the way down!**"+DialogBox.newline+
                        DialogBox.newline+
                        "||Controls||" +DialogBox.newline+
                        "Arrow Keys - Up,Down,Left,Right" +DialogBox.newline+
                        "A - attack"+DialogBox.newline+
                        "S - shoot"+DialogBox.newline+
                        "D - defend"+DialogBox.newline+
                        "H - heal"+DialogBox.newline+
                        "Q - inventory selector[backwards]"+DialogBox.newline+
                        "W - inventory selector[forwards]"+DialogBox.newline+
                        "E/Mouse 1[Left Click] - use"+DialogBox.newline+
                        "R/Mouse 2[Right Click - drop"+DialogBox.newline+
                        DialogBox.newline+
                        "||Color Identification||"+DialogBox.newline+
                        "Warrior - red"+DialogBox.newline+
                        "Archer - light green"+DialogBox.newline+
                        "Tank - dark green"+DialogBox.newline+
                        "Sub-Boss - purple"+DialogBox.newline+
                        "Boss - black"+DialogBox.newline+
                        "Dark Grey - walls"+DialogBox.newline+
                        "Grey - doors[damagable walls]"+DialogBox.newline+
                        "Pink - items"+DialogBox.newline+
                        "Orange/Blue - main/sub - portals"+DialogBox.newline+
                        DialogBox.newline+
                        "||Objective||"+DialogBox.newline+
                        "Make it to the black circle and you win!"                       
                        
                );
            }
        });        
    }
    private void setStatsText(){
        if(player!=null){
            damageLabel.setText("<html>Damage:<br><br><html>"+player.getinitDamage());
            hitpointsLabel.setText("<html>Hitpoints:<br><br><html>"+player.getinitHitpoints());
            weaponLabel.setText("<html>Weapon:<br><br><html>"+player.getWeaponDamage());
            armorLabel.setText("<html>Armor:<br><br><html>"+player.getArmorHitpoints());
            totalDamageLabel.setText("<html>Total<br>Damage:<br><br> <html>"+player.getDamage());
            totalHitpointsLabel.setText("<html>Total<br>Hitpoints:<br><br> <html>"+player.getHitpoints());
            XPLabel.setText("<html>XP:<br><br><html>"+"null");
            levelLabel.setText("<html>Level:<br><br><html>"+player.getLevel());
        }
    }
    
    private void createControlLayout(){
        controlPanel.setLayout(null);
                
        controlsLabel = new JPanel();//size: (230,150)
        controlsLabel.add(new JLabel("<html><b>Controls<b><html>", SwingConstants.CENTER));
        
        up = new EnhancedButton("▲",activePanel,Entity.UP);
        down = new EnhancedButton("▼",activePanel,Entity.DOWN);
        left = new EnhancedButton("<",activePanel,Entity.LEFT);
        right = new EnhancedButton(">",activePanel,Entity.RIGHT);
        attack = new JButton("A");
        shoot = new JButton("S");
        defend = new JButton("D");
        heal = new JButton("H");
        
        up.setToolTipText("Up");
        down.setToolTipText("Down");
        left.setToolTipText("Left");
        right.setToolTipText("Right");
        attack.setToolTipText("Attack");
        shoot.setToolTipText("Shoot");
        defend.setToolTipText("Defend");
        heal.setToolTipText("Heal");
        
        up.setBackground(new Color(197,179,88));
        down.setBackground(new Color(197,179,88));
        left.setBackground(new Color(197,179,88));
        right.setBackground(new Color(197,179,88));
        attack.setBackground(new Color(197,179,88));
        shoot.setBackground(new Color(197,179,88));
        defend.setBackground(new Color(197,179,88));
        heal.setBackground(new Color(197,179,88));       
        
        int buttonWidth = 40;
        int buttonHeight = 40;
        
        up.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        down.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        left.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        right.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        attack.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        shoot.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        defend.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        heal.setPreferredSize(new Dimension(buttonWidth, buttonHeight));               
        
        //first row//
        int gapx = 10;
        int gapy = 10;
        int controlLabelWidth = 180;
        int firstRowHeight = 20;
        controlPanel.add(controlsLabel);
        controlsLabel.setBackground(controlPanel.getBackground());
        controlsLabel.setBounds(25,2,controlLabelWidth, firstRowHeight);
        
        //second row//
        controlPanel.add(up);
        int gapFromLeftBorderSecondRow = 95;
        int gapFromTopBorderSecondRow = firstRowHeight+gapy;
        
        up.setBounds(gapFromLeftBorderSecondRow, gapFromTopBorderSecondRow, buttonWidth,buttonHeight);
        
        //third row//  
        int gapFromLeftBorderThirdRow = 45;
        int gapFromTopBorderThirdRow = gapFromTopBorderSecondRow+buttonHeight;
        
        controlPanel.add(left); 
        left.setBounds(gapFromLeftBorderThirdRow, gapFromTopBorderThirdRow,buttonWidth,buttonHeight);
        controlPanel.add(down);
        down.setBounds(gapFromLeftBorderThirdRow+buttonWidth+gapx, gapFromTopBorderThirdRow,buttonWidth,buttonHeight);
        controlPanel.add(right);
        right.setBounds(gapFromLeftBorderThirdRow+buttonWidth+buttonWidth+gapx+gapx, gapFromTopBorderThirdRow,buttonWidth,buttonHeight);
                
        //fourth row//    
        int gapFromLeftBorderFourthRow = 20;
        int gapFromTopBorderFourthRow = gapFromTopBorderThirdRow+buttonHeight;
                
        controlPanel.add(attack);
        attack.setBounds(gapFromLeftBorderFourthRow, gapFromTopBorderFourthRow,buttonWidth,buttonHeight);
        controlPanel.add(shoot);
        shoot.setBounds(gapFromLeftBorderFourthRow+gapx+buttonWidth, gapFromTopBorderFourthRow,buttonWidth,buttonHeight);
        controlPanel.add(defend);
        defend.setBounds(gapFromLeftBorderFourthRow+gapx+buttonWidth+gapx+buttonWidth, gapFromTopBorderFourthRow,buttonWidth,buttonHeight);
        controlPanel.add(heal);
        heal.setBounds(gapFromLeftBorderFourthRow+gapx+buttonWidth+gapx+buttonWidth+gapx+buttonWidth, gapFromTopBorderFourthRow,buttonWidth,buttonHeight);
    }
    private void controlButtonAction(){
        //up -- uses EnhancedButton
        //down -- uses EnhancedButton
        //left -- uses EnhancedButton
        //right -- uses EnhancedButton
        cardinalDirectionButtonAction();
        attack.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                player.attack(entities, dialogPanel);
                activePanel.requestFocusInWindow();
            }
        });
        shoot.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                player.shoot();
                activePanel.requestFocusInWindow();
            }
        });
        defend.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                player.defend();
                activePanel.requestFocusInWindow();
            }
        });
        heal.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                player.heal();
                activePanel.requestFocusInWindow();
            }
        });
    }
    private void cardinalDirectionButtonAction(){
        check.addActionListener(up);
        check.addActionListener(down);
        check.addActionListener(left);
        check.addActionListener(right);
    }
    
    private void createInventoryLayout(){
        GridBagLayout g = new GridBagLayout();
        inventoryPanel.setLayout(g);
        GridBagConstraints c = new GridBagConstraints();
        
        JPanel buffer1 = new JPanel();
        //JPanel buffer2 = new JPanel();
        inventoryLabel = new JPanel();
        inventoryLabel.add(new JLabel("<html><b>Inventory<b><html>",SwingConstants.CENTER));
        //buffer2.setBackground(Color.black);
        c.gridx=0;
        c.gridy=0;
        //c.weightx=1;
        c.weighty=0;
        g.setConstraints(buffer1,c);
        buffer1.setBackground(inventoryPanel.getBackground());
        inventoryPanel.add(buffer1);
        
        c.gridx=1;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.BOTH;
        g.setConstraints(inventoryLabel,c);
        inventoryLabel.setBackground(inventoryPanel.getBackground());
        inventoryPanel.add(inventoryLabel);
        
        //c.weightx=0;
        c.weighty=2;
        c.fill = GridBagConstraints.NONE;        
        inventoryItems = new InventoryIcon[Inventory.MAX_SIZE];
        
        for(int i=0; i<Inventory.MAX_SIZE;i++){
        InventoryIcon panel = new InventoryIcon();
        panel.setPreferredSize(new Dimension(60,60));
        inventoryItems[i] = panel;
        //inventoryItems[i].setBorder(blackborder);         
        inventoryLabels = new JLabel[Inventory.MAX_SIZE];
        }
        
        for(int z=0;z<inventoryItems.length;z++){
            inventoryLabels[z] = new JLabel();
            inventoryItems[z].add(inventoryLabels[z]);
        }        
        
        for(int h=0;h<Inventory.MAX_SIZE;h++){
            //c.weightx=1;
            c.gridx = (int)(h%Inventory.GRID_ROW);
            c.gridy = (int)(h/Inventory.GRID_COL)+1;
            g.setConstraints(inventoryItems[h], c);
            if(h%Inventory.GRID_ROW==Inventory.GRID_COL){
                c.gridwidth = GridBagConstraints.REMAINDER;
                c.weighty=0;
            }
            inventoryPanel.add(inventoryItems[h]);
        }
    }
    private void setInventoryColor(){
        for(int i=0;i<Inventory.MAX_SIZE;i++){
            if(Inventory.inventorySelectorIndex!=i){
                inventoryItems[i].setBackground(new Color(250,250,210));//default color
            }
            else{
                inventoryItems[i].setBackground(new Color(135,206,250));
            }
        }
        //System.out.println(Inventory.inventorySelectorIndex);
    }
    /**
    private void setInventoryIcons(){
    if((player!=null)&&(!player.inventory.items.isEmpty())){
        for(int i=0;i<player.inventory.items.size();i++){
            if(player.inventory.items.get(i).getQuantity()>0){
                if(player.inventory.items.get(i) instanceof Armor){
                    if(((Armor)player.inventory.items.get(i)).getEquipped()){
                        inventoryLabels[i].setText("<html><b>"+player.inventory.items.get(i).getAbbreviation()+"<br>"+player.inventory.items.get(i).getQuantity()+"<b><html>");
                    }
                    else{
                        inventoryLabels[i].setText("<html>"+player.inventory.items.get(i).getAbbreviation()+"<br>"+player.inventory.items.get(i).getQuantity()+"<html>");
                    }
                }
                else if(player.inventory.items.get(i) instanceof Weapon){
                    if(((Weapon)player.inventory.items.get(i)).getEquipped()){
                        inventoryLabels[i].setText("<html><b>"+player.inventory.items.get(i).getAbbreviation()+"<br>"+player.inventory.items.get(i).getQuantity()+"<b><html>");
                    }
                    else{
                        inventoryLabels[i].setText("<html>"+player.inventory.items.get(i).getAbbreviation()+"<br>"+player.inventory.items.get(i).getQuantity()+"<html>");
                    }
                }
                else{
                    inventoryLabels[i].setText("<html>"+player.inventory.items.get(i).getAbbreviation()+"<br>"+player.inventory.items.get(i).getQuantity()+"<html>");
                    }
                }
            }
        }
    activePanel.setInventoryItems(inventoryItems);
    }   
    private void inventoryRemoveIcons(){
        if(player!=null){
            int listSize = player.inventory.items.size();
            for(int i=listSize;i<Inventory.MAX_SIZE;i++){
                inventoryLabels[i].setText(null);
            }
        }
    }
    private void inventorySetToolTipText(){
        for(int i=0;i<player.inventory.items.size();i++){
            inventoryItems[i].setToolTipText(player.inventory.items.get(i).getName());
        }
    }
    **/
    private void createDialogLayout(){
        
    }
    
    private void createActionLayout(){
        
    }
    private void initWinLose(){
        winLose = new WinLose();
    }
    private void checkIfWinOrLose(){
        winLose.operate(player, check, dialogPanel);
    }
    @Override
    public void actionPerformed(ActionEvent e) {   
        setInventoryColor();
        player.setInventoryIcons(inventoryLabels,activePanel,inventoryItems);
        player.inventorySetToolTipText(inventoryItems);
        player.inventoryRemoveIcons(inventoryLabels);
        setStatsText();    
        checkIfWinOrLose();
        if(dialogPanel.getHasBeenSelected()){
            getFocus();
            dialogPanel.setHasBeenSelected(false);
        }    
    }          
    private void createMap(){
        createExampleMap();
    } 
    private void createExampleMap(){
        ArrayList<Entity> e = new ArrayList<>(); //size of map(width =  740; height = 400)
        Player p = new Player(0,0,10,10,100,100,300,"You",10000,200);
        
        e.add(new OpaqueEntity(70,0,10,70));//right wall of spawn room
        e.add(new OpaqueEntity(0,70,80,10));//bottom wall of spawn room
        e.add(new SpawnableItem(30,30,new Weapon("Sword",1)));
        e.add(new SpawnableItem(30,30,new Armor("C-Mail",5)));
        e.add(new SpawnableItem(30,30,new HealthPotion("Shrimp",3,5)));        
        
        Portal one = new Portal(30,50,20,20,new Entity(80,50,20,20),Portal.DEFAULT,true);//portal from spawnroom to room1  
        Portal two = new Portal(80,50,20,20,new Entity(30,50,20,20),Portal.DEFAULT,false);//portal from room1 to spawnroom
        e.add(one);
        e.add(two);
        
        e.add(new OpaqueEntity(150,0,10,360));//right wall of room 1 and room 2; left wall of room 3
        e.add(new OpaqueEntity(0,150,120,10));//bottom wall of room 1
        e.add(new NonPlayerCharacter(100,0,10,10,50,50,0,"Robert",2,5,10,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(100,30,10,10,50,50,0,"Ned",2,5,10,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(30,120,10,10,50,50,0,"Joffrey",2,5,10,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(50,120,10,10,50,50,0,"Aegon",2,5,10,NonPlayerCharacter.WARRIOR));        
        
        e.add(new DamageableEntity(150,362,10,36,2));//breakable entity/door of room 1 to room 2
        e.add(new NonPlayerCharacter(30,200,10,10,50,50,0,"Viserys",3,10,40,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(50,200,10,10,50,50,0,"Jack",3,10,40,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(70,200,10,10,50,50,0,"Joseph",3,10,40,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(30,250,10,10,50,50,200,"Ygritte",2,5,40,NonPlayerCharacter.ARCHER));
        e.add(new NonPlayerCharacter(30,290,10,10,50,50,0,"Daemon",3,10,40,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(50,290,10,10,50,50,0,"Johnson",3,10,40,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(70,290,10,10,50,50,0,"Gregor",3,10,40,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(30,310,10,10,50,50,200,"Karils",2,5,40,NonPlayerCharacter.ARCHER));        
        e.add(new SpawnableItem(0,380,new Weapon("L-Sword",4)));
        e.add(new SpawnableItem(0,200,new Armor("C-Mail++",30)));
        e.add(new SpawnableItem(30,300,new Ammo("Arrow",50)));
        e.add(new SpawnableItem(100,340,new HealthPotion("Beef",5,5)));
        
        e.add(new DamageableEntity(450,2,10,36,2));//breakable entity/door of room 2 to room 3
        e.add(new OpaqueEntity(450,40,10,400));//right wall of room 2; left wall of room 3 and 4
        e.add(new OpaqueEntity(280,180,40,40));//safespot/obstacle middle
        e.add(new DamageableEntity(280,60,40,40,30));//safespot/obstacle top
        e.add(new DamageableEntity(280,300,40,40,30));//safespot/obstacle bottom
        
        e.add(new NonPlayerCharacter(240,60,10,10,50,50,0,"Grim",4,15,200,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(240,80,10,10,50,50,0,"Billy",4,15,200,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(360,60,10,10,25,25,0,"Sandor",2,30,200,NonPlayerCharacter.TANK));
        e.add(new NonPlayerCharacter(360,80,10,10,50,50,200,"Robin",2,10,200,NonPlayerCharacter.ARCHER));        
        e.add(new NonPlayerCharacter(240,300,10,10,50,50,0,"Robb",4,15,200,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(240,330,10,10,50,50,0,"Krem",4,15,200,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(360,300,10,10,25,25,0,"Gregor",2,30,200,NonPlayerCharacter.TANK));
        e.add(new NonPlayerCharacter(360,330,10,10,50,50,200,"Petyr",2,10,200,NonPlayerCharacter.ARCHER));        
        e.add(new NonPlayerCharacter(240,180,15,15,50,50,300,"Geoffry",10,50,100,NonPlayerCharacter.SUBBOSS));
        
        e.add(new SpawnableItem(200,200,new ProjectileWeapon("Bow",3)));
        e.add(new SpawnableItem(450,231,new Weapon("Rapier",10)));
        e.add(new SpawnableItem(440,340,new Armor("P-body",100)));
        e.add(new SpawnableItem(300,350,new HealthPotion("Revival",10,20)));
        
        e.add(new TalkableGate(450,2,10,36,"I'm surprised you made it this far. PREPARE TO DIE!"));
        
        e.add(new OpaqueEntity(460,195,280,10));
        
        Portal three = new Portal(720,176,20,20,new Entity(720,380,20,20),Portal.KILL_ALL_NONBOSS,true);//portal to bossroom(1way)
        e.add(three);
        
        e.add(new NonPlayerCharacter(680,50,25,25,25,25,200,"Barristan Selmy",15,50,100,NonPlayerCharacter.SUBBOSS));
        e.add(new NonPlayerCharacter(660,100,25,25,25,25,200,"Dharoks the Wretched",15,50,100,NonPlayerCharacter.SUBBOSS));
        e.add(new NonPlayerCharacter(500,150,10,10,50,50,200,"The Great English Longbowman",5,10,200,NonPlayerCharacter.ARCHER));
        e.add(new NonPlayerCharacter(530,150,10,10,50,50,200,"The Almost As Great English Longbowman",5,10,190,NonPlayerCharacter.ARCHER));
        e.add(new SpawnableItem(660,160,new StrengthPotion("S-Pot",1,5)));
        
        //BOSSROOM//
        e.add(new NonPlayerCharacter(600,330,30,30,50,50,200,"Tim Tebow",100,200,200,NonPlayerCharacter.BOSS));
        win = new MapGate(700,360,20,20,MapGate.KILL_ALL);
        e.add(win);
        e.add(new NonPlayerCharacter(660,300,25,25,25,25,200,"James T. Kirk",10,50,100,NonPlayerCharacter.BR_SUBBOSS));
        e.add(new NonPlayerCharacter(550,230,10,10,50,50,200,"A-Rod",5,10,200,NonPlayerCharacter.BR_ARCHER));
        e.add(new NonPlayerCharacter(550,270,10,10,50,50,0,"Mark Zuckerburg",4,15,200,NonPlayerCharacter.BR_WARRIOR));
        e.add(new NonPlayerCharacter(550,300,10,10,50,50,0,"Thad Castle",2,50,200,NonPlayerCharacter.BR_TANK));       
        
        player = p;
        entities = e;
        level = new Level(p,e);
            
            
    }  
}
