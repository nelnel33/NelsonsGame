package nelsontsui.nelsonsgame.game;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.Timer;

public class GameDisplay extends JFrame implements ActionListener{
    public long frameCount = 0;
    
    private Timer check= new Timer((int)ActionPanel.TICK,this);
    
    private Insets border;
    public static final Border blackborder = BorderFactory.createLineBorder(Color.black);   
    
    protected Character Player;
    protected ArrayList<Entity> npcs;
    
    //Main Panels
    private ActionPanel activePanel;
    private JPanel statsPanel;
    private JPanel inventoryPanel;
    private JPanel controlPanel;
    private JPanel dialogPanel;
    
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
    
    private JPanel buttonPanel;
    private JButton saveButton;
    private JButton importButton;
    
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
    
    public static final int edgeX = 740;
    public static final int edgeY = 400;

    public GameDisplay(){
    super("Nelson's Game"); 
        
    createMap();
    
    createBackgroundLayout();    
    check.addActionListener(activePanel);
    
    createStatsLayout();    
    createControlLayout();
    createInventoryLayout();
    
    controlButtonAction();    
    miscButtonAction();
    check.start();

    setResizable(false);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }
    
    private void createBackgroundLayout(){
    setLayout(null);
    setSize(1000,600);
    getContentPane().setBackground(new Color(55,198,164));
    
    activePanel = new ActionPanel(Player,npcs,edgeX,edgeY);
    
    statsPanel = new JPanel();
    inventoryPanel = new JPanel();
    controlPanel = new JPanel();
    dialogPanel = new JPanel();
    
    border = getInsets();
    System.out.println(getSize()+","+getWidth()+","+getHeight());
    
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
        
        saveButton = new JButton("Save");
        importButton = new JButton("Import");
        saveButton.setBackground(new Color(197,179,88));        
        saveButton.setOpaque(true);
        importButton.setBackground(new Color(197,179,88));
        importButton.setOpaque(true);
        
        //saveButton.setBorderPainted(false);
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2,1));
        buttonPanel.setBackground(new Color(197,179,88));
        buttonPanel.add(saveButton);
        buttonPanel.add(importButton);
        
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
    private void miscButtonAction(){
        saveButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                activePanel.requestFocusInWindow();
            }
        });
        importButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                activePanel.requestFocusInWindow();
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
            public void actionPerformed(ActionEvent e){
                activePanel.up();
                activePanel.requestFocusInWindow();
            }
        });
        down.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                activePanel.down();
                activePanel.requestFocusInWindow();
            }
        });;
        left.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                activePanel.left();
                activePanel.requestFocusInWindow();
            }
        });
        right.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                activePanel.right();
                activePanel.requestFocusInWindow();
            }
        });
        attack.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                activePanel.attack();
                activePanel.requestFocusInWindow();
            }
        });
        shoot.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                activePanel.shoot();
                activePanel.requestFocusInWindow();
            }
        });
        defend.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                activePanel.defend();
                activePanel.requestFocusInWindow();
            }
        });
        heal.addActionListener(new ActionListener(){
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
    
    private void lose(){
        if(Player.getHitpoints()<=0){
            System.exit(0);
        }    
    }
    
    private void createMap(){//TODO DELETE METHOD
        ArrayList<Entity> killers = new ArrayList<>();
        killers.add(new NonPlayerCharacter(100,100,20,20,50,50,300,"killer1",10,10,200,NonPlayerCharacter.ARCHER));
        killers.add(new NonPlayerCharacter(300,300,20,20,50,50,0,"killer2",15,10,200,NonPlayerCharacter.TANK));
        killers.add(new NonPlayerCharacter(500,500,60,60,50,50,0,"BicBoi",10,10,40,NonPlayerCharacter.WARRIOR));
        killers.add(new NonPlayerCharacter(465,20,30,30,50,50,0,"obj",10,10,500,NonPlayerCharacter.WARRIOR));
        killers.add(new OpaqueEntity(500,0,20,100));
        killers.add(new NonPlayerCharacter(100,100,30,30,50,50,0,"killer3",15,10,500,NonPlayerCharacter.TANK));
        killers.add(new NonPlayerCharacter(30,200,30,30,50,50,500,"obj",100,10,100,NonPlayerCharacter.BOSS));
        killers.add(new NonPlayerCharacter(420,100,30,30,50,50,0,"killer3",20,10,500,NonPlayerCharacter.TANK));
        killers.add(new OpaqueEntity(500,0,20,100));
        killers.add(new DamagableEntity(200,200,50,50,10));
        killers.add(new SpawnableItem(600,50,new HealthPotion("H-Pot",5,5)));
        killers.add(new SpawnableItem(600,50,new Arrow("Arrow", 30)));
        killers.add(new SpawnableItem(600,50,new Bow("Bow", 2)));
        killers.add(new SpawnableItem(600,50,new Weapon("Sword", 3)));
        killers.add(new SpawnableItem(600,50,new Armor("Armor10",20)));
        killers.add(new SpawnableItem(600,50,new Armor("Armor20",10)));
        killers.add(new SpawnableItem(600,60,new HealthPotion("H-Pot",5,5)));
        killers.add(new SpawnableItem(600,70,new HealthPotion("H-Pot",5,5)));
        killers.add(new SpawnableItem(600,50,new StrengthPotion("S-Pot",5,1.5)));
        killers.add(new SpawnableItem(600,60,new HealthPotion("H-Pot",5,5)));
        killers.add(new SpawnableItem(650,70,new HealthPotion("H-Pot",5,5)));
        killers.add(new SpawnableItem(660,60,new HealthPotion("H-Pot",5,5)));
        killers.add(new SpawnableItem(670,70,new HealthPotion("H-Pot",5,5)));
        Portal one = new Portal(350,250,20,20,new Portal(180,120,20,20,new Entity(300,300,20,20)));
        killers = one.addPortals(killers);
        Character achar = new Character(600,30,10,10,100,100,300,"Player",100,2);
        
        
        Player = achar;
        npcs = killers;
    } 
    @Override
    public void actionPerformed(ActionEvent e) {   
        //controlButtonAction();
        setInventoryColor();
        setInventoryIcons();
        inventoryRemoveIcons();
        setStatsText();  
        lose();
    }
    
    public static void main(String[] args){
        GameDisplay game = new GameDisplay();
    }

   

   
    
}
