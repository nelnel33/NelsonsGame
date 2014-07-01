package nelsontsui.nelsonsgame.game;
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
    
    protected Character Player;
    protected ArrayList<Entity> npcs;
    
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
    private JButton saveButton;
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
    private JButton up;
    private JButton down;
    private JButton left;
    private JButton right;
    private JButton attack;
    private JButton shoot;
    private JButton defend;
    private JButton heal;
    
    //Changable static variables
    public static final int edgeX = 740;
    public static final int edgeY = 400;
    
    //Watermark
    NelsonWatermark watermark;    
    
    //Ingame Elements/Entities
    MapGate win;//TODO: Temporary
    
   

    public GameDisplay(JButton toStartMenu){
    //super("Nelson's Game"); //JFrame to JPanel
    this.toStartMenu = toStartMenu;
        
    createMap();
    
    createBackgroundLayout();    
    check.addActionListener(activePanel);
    check.addActionListener(dialogPanel);
    
    addWatermark();
    
    createStatsLayout();    
    createControlLayout();
    createInventoryLayout();
    
    controlButtonAction();    
    miscButtonAction();
    check.start();

    //setResizable(false); //JFrame to JPanel
    //setVisible(true); //JFrame to JPanel
    //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //JFrame to JPanel 
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
    setPreferredSize(new Dimension(1000,610));
    //getContentPane().  //JFrame to JPanel
            setBackground(new Color(55,198,164));
    
    activePanel = new ActionPanel(Player,npcs,edgeX,edgeY);
    
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

        damageLabel = new JLabel("<html>Damage:<br><br><html>"+Player.getDamage(),SwingConstants.CENTER);
        damageDisplay.add(damageLabel);        
        //damageDisplay.setBorder(blackborder);
        
        hitpointsLabel = new JLabel("<html>Hitpoints:<br><br><html>"+Player.getHitpoints(),SwingConstants.CENTER);
        hitpointsDisplay.add(hitpointsLabel);
        //hitpointsDisplay.setBorder(blackborder);
        
        weaponLabel = new JLabel("<html>Weapon:<br><br><html>"+Player.getWeaponDamage(),SwingConstants.CENTER);
        weaponDisplay.add(weaponLabel);
        //weaponDisplay.setBorder(blackborder);
        
        armorLabel = new JLabel("<html>Armor:<br><br><html>"+Player.getArmorHitpoints(),SwingConstants.CENTER);
        armorDisplay.add(armorLabel);
        //armorDisplay.setBorder(blackborder);
        
        totalDamageLabel = new JLabel("<html>Total<br>Damage:<br><br> <html>"+Player.getDamage(),SwingConstants.CENTER);
        totalDamageDisplay.add(totalDamageLabel);
        //totalDamageDisplay.setBorder(blackborder);
        
        totalHitpointsLabel = new JLabel("<html>Total<br>Hitpoints:<br><br> <html>"+Player.getHitpoints(),SwingConstants.CENTER);
        totalHitpointsDisplay.add(totalHitpointsLabel);
        //totalHitpointsDisplay.setBorder(blackborder);
        
        XPLabel = new JLabel("<html>XP:<br><br><html>"+"null",SwingConstants.CENTER);
        XPDisplay.add(XPLabel);
        //XPDisplay.setBorder(blackborder);
        
        levelLabel = new JLabel("<html>Level:<br><br><html>"+Player.getLevel(),SwingConstants.CENTER);
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
        
        saveButton = new JButton("Save");
        importButton = new JButton("Import");
        //toStartMenu = new JButton("Start Menu");
        howTo = new JButton("Instructions");
        
        saveButton.setBackground(new Color(197,179,88));        
        saveButton.setOpaque(true);
        importButton.setBackground(new Color(197,179,88));
        importButton.setOpaque(true);
        
        buttonPanel.add(toStartMenu);
        buttonPanel.add(howTo);
        buttonPanel.add(importButton);
        buttonPanel.add(saveButton);
    }
    private void miscButtonAction(){
        saveButton.addActionListener(new ActionListener(){
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
        /*
        toStartMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                activePanel.requestFocusInWindow();
            }
        });
        */
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
        damageLabel.setText("<html>Damage:<br><br><html>"+Player.getinitDamage());
        hitpointsLabel.setText("<html>Hitpoints:<br><br><html>"+Player.getinitHitpoints());
        weaponLabel.setText("<html>Weapon:<br><br><html>"+Player.getWeaponDamage());
        armorLabel.setText("<html>Armor:<br><br><html>"+Player.getArmorHitpoints());
        totalDamageLabel.setText("<html>Total<br>Damage:<br><br> <html>"+Player.getDamage());
        totalHitpointsLabel.setText("<html>Total<br>Hitpoints:<br><br> <html>"+Player.getHitpoints());
        XPLabel.setText("<html>XP:<br><br><html>"+"null");
        levelLabel.setText("<html>Level:<br><br><html>"+Player.getLevel());
    }
    
    private void createControlLayout(){
        controlPanel.setLayout(null);
                
        controlsLabel = new JPanel();//size: (230,150)
        controlsLabel.add(new JLabel("<html><b>Controls<b><html>", SwingConstants.CENTER));
        
        up = new JButton("▲");
        down = new JButton("▼");
        left = new JButton("<");
        right = new JButton(">");
        attack = new JButton("Atk");
        shoot = new JButton("Sht");
        defend = new JButton("Def");
        heal = new JButton("Hl");
        
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
        up.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                activePanel.up();
                activePanel.requestFocusInWindow();
            }
        });
        down.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                activePanel.down();
                activePanel.requestFocusInWindow();
            }
        });;
        left.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                activePanel.left();
                activePanel.requestFocusInWindow();
            }
        });
        right.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                activePanel.right();
                activePanel.requestFocusInWindow();
            }
        });
        attack.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                activePanel.attack();
                activePanel.requestFocusInWindow();
            }
        });
        shoot.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                activePanel.shoot();
                activePanel.requestFocusInWindow();
            }
        });
        defend.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                activePanel.defend();
                activePanel.requestFocusInWindow();
            }
        });
        heal.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                activePanel.heal();
                activePanel.requestFocusInWindow();
            }
        });
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
    private void setInventoryIcons(){
    if(!Player.inventory.items.isEmpty()){
        for(int i=0;i<Player.inventory.items.size();i++){
            if(Player.inventory.items.get(i).getQuantity()>0){
                if(Player.inventory.items.get(i) instanceof Armor){
                    if(((Armor)Player.inventory.items.get(i)).getEquipped()){
                        inventoryLabels[i].setText("<html><b>"+Player.inventory.items.get(i).getName()+"<br>"+Player.inventory.items.get(i).getQuantity()+"<b><html>");
                    }
                    else{
                        inventoryLabels[i].setText("<html>"+Player.inventory.items.get(i).getName()+"<br>"+Player.inventory.items.get(i).getQuantity()+"<html>");
                    }
                }
                else if(Player.inventory.items.get(i) instanceof Weapon){
                    if(((Weapon)Player.inventory.items.get(i)).getEquipped()){
                        inventoryLabels[i].setText("<html><b>"+Player.inventory.items.get(i).getName()+"<br>"+Player.inventory.items.get(i).getQuantity()+"<b><html>");
                    }
                    else{
                        inventoryLabels[i].setText("<html>"+Player.inventory.items.get(i).getName()+"<br>"+Player.inventory.items.get(i).getQuantity()+"<html>");
                    }
                }
                else{
                    inventoryLabels[i].setText("<html>"+Player.inventory.items.get(i).getName()+"<br>"+Player.inventory.items.get(i).getQuantity()+"<html>");
                    }
                }
            }
        }
    activePanel.setInventoryItems(inventoryItems);
    }   
    private void inventoryRemoveIcons(){
        int listSize = Player.inventory.items.size();
        for(int i=listSize;i<Inventory.MAX_SIZE;i++){
            inventoryLabels[i].setText(null);
        }
    }
    private void createDialogLayout(){
        
    }
    
    private void createActionLayout(){
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {   
        setInventoryColor();
        setInventoryIcons();
        inventoryRemoveIcons();
        setStatsText();        
        if(dialogPanel.getHasBeenSelected()){
            getFocus();
           dialogPanel.setHasBeenSelected(false);
           }
         
        lose();        
        win.checkIfCanUse(activePanel.npcs);        
        win();
        
    }   
        
    private void win(){
        if(win.canOperate(Player)){
           dialogPanel.message("You Win!!!!");
        }        
    }    
    private void lose(){
        if(Player.getHitpoints()<=0){
            dialogPanel.message("You Lose.");
            check.stop();
        }
    }        
    private void createMap(){
        createExampleMap();
    } 
    private void createExampleMap(){
        ArrayList<Entity> e = new ArrayList<>(); //size of map(width =  740; height = 400)
        Character p = new Character(0,0,10,10,100,100,300,"You",10000,200);
        
        e.add(new OpaqueEntity(70,0,10,70));//right wall of spawn room
        e.add(new OpaqueEntity(0,70,80,10));//bottom wall of spawn room
        e.add(new SpawnableItem(30,30,new Weapon("Pencil",1)));
        e.add(new SpawnableItem(30,30,new Armor("Sombrero",5)));
        e.add(new SpawnableItem(30,30,new HealthPotion("Gum",3,5)));        
        
        Portal one = new Portal(30,50,20,20,new Entity(80,50,20,20),Portal.DEFAULT,true);//portal from spawnroom to room1  
        Portal two = new Portal(80,50,20,20,new Entity(30,50,20,20),Portal.DEFAULT,false);//portal from room1 to spawnroom
        e.add(one);
        e.add(two);
        
        e.add(new OpaqueEntity(150,0,10,360));//right wall of room 1 and room 2; left wall of room 3
        e.add(new OpaqueEntity(0,150,120,10));//bottom wall of room 1
        e.add(new NonPlayerCharacter(100,0,10,10,50,50,0,"Berta",2,5,10,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(100,30,10,10,50,50,0,"Berta",2,5,10,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(30,120,10,10,50,50,0,"Berta",2,5,10,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(50,120,10,10,50,50,0,"Berta",2,5,10,NonPlayerCharacter.WARRIOR));        
        
        e.add(new DamagableEntity(150,362,10,36,2));//breakable entity/door of room 1 to room 2
        e.add(new NonPlayerCharacter(30,200,10,10,50,50,0,"Berta",3,10,40,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(50,200,10,10,50,50,0,"Berta",3,10,40,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(70,200,10,10,50,50,0,"Berta",3,10,40,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(30,250,10,10,50,50,200,"Arlpha",2,5,40,NonPlayerCharacter.ARCHER));
        e.add(new NonPlayerCharacter(30,290,10,10,50,50,0,"Berta",3,10,40,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(50,290,10,10,50,50,0,"Berta",3,10,40,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(70,290,10,10,50,50,0,"Berta",3,10,40,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(30,310,10,10,50,50,200,"Arlpha",2,5,40,NonPlayerCharacter.ARCHER));        
        e.add(new SpawnableItem(0,380,new Weapon("BigStick",4)));
        e.add(new SpawnableItem(0,200,new Armor("T-Shirt",30)));
        e.add(new SpawnableItem(30,300,new Arrow("Rocks",50)));
        e.add(new SpawnableItem(100,340,new HealthPotion("Donuts",5,5)));
        
        e.add(new DamagableEntity(450,2,10,36,2));//breakable entity/door of room 2 to room 3
        e.add(new OpaqueEntity(450,40,10,400));//right wall of room 2; left wall of room 3 and 4
        e.add(new OpaqueEntity(280,180,40,40));//safespot/obstacle middle
        e.add(new DamagableEntity(280,60,40,40,30));//safespot/obstacle top
        e.add(new DamagableEntity(280,300,40,40,30));//safespot/obstacle bottom
        
        e.add(new NonPlayerCharacter(240,60,10,10,50,50,0,"Berta",4,15,200,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(240,80,10,10,50,50,0,"Berta",4,15,200,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(360,60,10,10,25,25,0,"Therta",2,30,200,NonPlayerCharacter.TANK));
        e.add(new NonPlayerCharacter(360,80,10,10,50,50,200,"Arlpha",2,10,200,NonPlayerCharacter.ARCHER));        
        e.add(new NonPlayerCharacter(240,300,10,10,50,50,0,"Berta",4,15,200,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(240,330,10,10,50,50,0,"Berta",4,15,200,NonPlayerCharacter.WARRIOR));
        e.add(new NonPlayerCharacter(360,300,10,10,25,25,0,"Therta",2,30,200,NonPlayerCharacter.TANK));
        e.add(new NonPlayerCharacter(360,330,10,10,50,50,200,"Arlpha",2,10,200,NonPlayerCharacter.ARCHER));        
        e.add(new NonPlayerCharacter(240,180,15,15,50,50,300,"Derlta",10,50,100,NonPlayerCharacter.SUBBOSS));
        
        e.add(new SpawnableItem(200,200,new Bow("Slingshot",3)));
        e.add(new SpawnableItem(450,231,new Weapon("Sword",10)));
        e.add(new SpawnableItem(440,340,new Armor("Shield",100)));
        e.add(new SpawnableItem(300,350,new HealthPotion("H-Pot",10,20)));
        
        e.add(new TalkableGate(450,2,10,36,"Berta, Eye'm lyk dah---sir-prized u made it---lyk dah---dhis far---bart---berta lyk pree-pair to dye."));
        
        e.add(new OpaqueEntity(460,195,280,10));
        
        Portal three = new Portal(720,175,20,20,new Entity(720,380,20,20),Portal.KILL_ALL_NONBOSS,true);//portal to bossroom(1way)
        e.add(three);
        
        e.add(new NonPlayerCharacter(680,50,25,25,25,25,200,"Omerga",15,50,100,NonPlayerCharacter.SUBBOSS));
        e.add(new NonPlayerCharacter(660,100,25,25,25,25,200,"Omerga",15,50,100,NonPlayerCharacter.SUBBOSS));
        e.add(new NonPlayerCharacter(500,150,10,10,50,50,200,"Arlpha",5,10,200,NonPlayerCharacter.ARCHER));
        e.add(new NonPlayerCharacter(530,150,10,10,50,50,200,"Arlpha",5,10,200,NonPlayerCharacter.ARCHER));
        e.add(new SpawnableItem(660,160,new StrengthPotion("S-Pot",1,5)));
        
        //BOSSROOM//
        e.add(new NonPlayerCharacter(600,330,50,50,50,50,200,"Al-ee Sa-ya-nee Dah Pack-ee",30,200,200,NonPlayerCharacter.BOSS));
        win = new MapGate(700,360,20,20,MapGate.KILL_ALL);
        e.add(win);
        e.add(new NonPlayerCharacter(660,300,25,25,25,25,200,"Omerga",10,50,100,NonPlayerCharacter.BR_SUBBOSS));
        e.add(new NonPlayerCharacter(550,230,10,10,50,50,200,"Arlpha",5,10,200,NonPlayerCharacter.BR_ARCHER));
        e.add(new NonPlayerCharacter(550,270,10,10,50,50,0,"Berta",4,15,200,NonPlayerCharacter.BR_WARRIOR));
        e.add(new NonPlayerCharacter(550,300,10,10,50,50,0,"Therta",2,50,200,NonPlayerCharacter.BR_TANK));       
        
        Player = p;
        npcs = e;
    }  
}
