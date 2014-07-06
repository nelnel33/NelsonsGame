package nelsontsui.nelsonsgame.leveleditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import nelsontsui.nelsonsgame.game.Point;
import nelsontsui.nelsonsgame.game.*;

public class LevelEditorDisplay extends JDialog implements ActionListener, MouseListener{   
    //static decs
    public static final int TOTAL_SELECTORS = 4;
    public static final int TOTAL_DETAILEDSELECTORS = 40;
    public static final int PLAYER_CHARACTER = 0;
    public static final int ENTITY = 1;
    public static final int NONPLAYERCHARACTER = 2;
    public static final int ITEM = 3;
    
    //gap for layout
    private final int gap = 10;
    
    //what will be read from and written to
    private ArrayList<Entity> npcs = new ArrayList<>();
    private nelsontsui.nelsonsgame.game.Character player;
    
    //width and height of the map/gameplay panel
    private int panelWidth = GameDisplay.edgeX;
    private int panelHeight = GameDisplay.edgeY;
    
    //where your cursor is/where your cursor is being dragged to
    private Point cursor;//where your cursor is
    private Point placement;//where the entity will be placed
    private DimensionDouble dimension;//how large the entity will be
    
    
    //bottom middle panel(coords for cursor,drag,and dragarea)
    private JPanel pointPanel;
    private JLabel cursorLabel;
    private JLabel placementLabel;
    private JLabel dimensionLabel;
    
    //the name of the file that is being written to
    private String fileName;
    
    private EditingPanel editPanel;   
    
    //bottom right, main selectors
    private JPanel selectorPanel;
    private EntityTile[] selectors = new EntityTile[TOTAL_SELECTORS];
    
    //button holders
    private JPanel buttonHolder;
    private JPanel saveAndImportButtonHolder;
    private JPanel defaultAndCustomOptions;
    
    //misc buttons
    private JButton save;
    private JButton importFile;
    private JButton defaultOptions;
    private JButton customOptions;
    
    //selectors for detailed selecting
    private JPanel detailedSelectorPanelHolder;//Placeholder for starting    
    private JPanel[] detailedSelectorPanel = new JPanel[TOTAL_SELECTORS]; //index of TOTAL_SELECTORS.   
    private EntityTile[][] detailedSelectors = new EntityTile[TOTAL_SELECTORS][TOTAL_DETAILEDSELECTORS];
    
    //text/name
    private JPanel textPanel;
    private JPanel labelerPanel;
    private JLabel labelerLabel;
    
    //timer
    Timer check = new Timer((int)ActionPanel.TICK,this);
    
    //JDialog for customoptions
    CustomOptionsPanel customPanel;
    
    public LevelEditorDisplay(JFrame owner){
       super(owner, "Nelson's Game: Level Editor");
       setLayout(null);
       
       editingPanelInit();  
       textPanel();
       pointPanelInit();
       selectorPanelInit();
       detailedSelectorPanelHolderInit();       
       selectorLayout();
       buttonHolderPanelInit();
       detailedSelectorPanelInit();
       addDetailedSelectorButtons();
       haveSelectorsBeenClicked();
       labelerPanelInit();
       addActions();
       
       getContentPane().setBackground(new Color(55,198,164));
       
       check.addActionListener(editPanel);
       check.start();
       
       setPreferredSize(new Dimension(gap+panelWidth+gap+detailedSelectorPanelHolder.getWidth()+gap,
               gap+panelHeight+gap+selectorPanel.getHeight()+gap+gap+gap));
       setResizable(false);
       setVisible(true);
       setDefaultCloseOperation(DISPOSE_ON_CLOSE);   
       pack();
       setLocationRelativeTo(null);
    }
    private void editingPanelInit(){
        cursor = new Point(0.0,0.0);
        placement = new Point(0.0,0.0);
        dimension = new DimensionDouble(10,10);
        editPanel = new EditingPanel(cursor,placement,dimension);
        editPanel.setBorder(GameDisplay.blackborder);
        editPanel.setSize(new Dimension(panelWidth,panelHeight));        
        editPanel.setBounds(gap,gap,panelWidth,panelHeight);
        //background color set in paintComponent of EditingPanel class;
        
        add(editPanel);
    }
    private void selectorLayout(){
        initSelectors();
        
        selectorPanel.add(selectors[PLAYER_CHARACTER]);
        selectorPanel.add(selectors[ENTITY]);
        selectorPanel.add(selectors[NONPLAYERCHARACTER]);
        selectorPanel.add(selectors[ITEM]);
    }
    private void initSelectors(){        
        selectors[PLAYER_CHARACTER] = new EntityTile("Player", "Player");
        selectors[ENTITY] = new EntityTile("Entity", "Entity");
        selectors[NONPLAYERCHARACTER] = new EntityTile("NPC", "NPC");
        selectors[ITEM] = new EntityTile("Item", "Item");
    }
    private void textPanel(){
        textPanel = new JPanel();
        //textPanel.setBorder(GameDisplay.blackborder);
        textPanel.add(new JLabel("<html><b>Nelson's Game: <b><html>",SwingConstants.CENTER));
        textPanel.add(new JLabel("<html><b>Level Editor<b><html>",SwingConstants.CENTER));
        textPanel.setPreferredSize(new Dimension(120,EntityTile.size));
        textPanel.setBounds(gap,gap+panelHeight+gap,textPanel.getPreferredSize().width,EntityTile.size);
        textPanel.setBackground(new Color(197,179,88));
        
        add(textPanel);
    }
    private void buttonHolderPanelInit(){
        save = new JButton("Save");
        importFile = new JButton("Import");
        saveAndImportButtonHolder = new JPanel();
        saveAndImportButtonHolder.setLayout(new GridLayout(2,1));
        saveAndImportButtonHolder.add(save);
        saveAndImportButtonHolder.add(importFile);
        saveAndImportButtonHolder.setBackground(new Color(197,179,88));
        
        defaultOptions = new JButton("Default");
        customOptions = new JButton("Custom");        
        defaultAndCustomOptions = new JPanel();
        defaultAndCustomOptions.setLayout(new GridLayout(3,1));
        defaultAndCustomOptions.add(new JLabel("<html><b>Configure<b><html>",SwingConstants.CENTER));        
        defaultAndCustomOptions.add(defaultOptions);
        defaultAndCustomOptions.add(customOptions);  
        defaultAndCustomOptions.setBackground(new Color(197,179,88));
        
        buttonHolder = new JPanel();
        buttonHolder.setPreferredSize(new Dimension(170,EntityTile.size));
        buttonHolder.setBounds(gap+textPanel.getPreferredSize().width+
                gap+pointPanel.getPreferredSize().width+gap+selectorPanel.getPreferredSize().width,
                gap+panelHeight+gap,buttonHolder.getPreferredSize().width,EntityTile.size);
        buttonHolder.setLayout(new GridLayout(1,2));
        buttonHolder.add(saveAndImportButtonHolder);
        buttonHolder.add(defaultAndCustomOptions);
        buttonHolder.setBackground(new Color(197,179,88));
        
        add(buttonHolder);
    }
    private void selectorPanelInit(){
        selectorPanel = new JPanel();
        //selectorPanel.setBorder(GameDisplay.blackborder);
        selectorPanel.setLayout(new GridLayout(1,5,2,2));        
        selectorPanel.setSize(new Dimension(EntityTile.size*5,EntityTile.size));        
        selectorPanel.setBounds(gap+textPanel.getPreferredSize().width+gap+pointPanel.getPreferredSize().width+gap,
                gap+panelHeight+gap,EntityTile.size*4,EntityTile.size);        
        selectorPanel.setBackground(new Color(197,179,88));
        
        add(selectorPanel);
    }
    private void detailedSelectorPanelHolderInit(){
        detailedSelectorPanelHolder = new JPanel();
        detailedSelectorPanelHolder.setLayout(new GridLayout(8,5));
        //detailedSelectorPanelHolder.setBorder(GameDisplay.blackborder);   
        detailedSelectorPanelHolder.setPreferredSize(new Dimension(250,400));
        detailedSelectorPanelHolder.setBounds(gap+gap+panelWidth,gap,
                detailedSelectorPanelHolder.getPreferredSize().width,detailedSelectorPanelHolder.getPreferredSize().height);
        detailedSelectorPanelHolder.setBackground(new Color(197,179,88));
        add(detailedSelectorPanelHolder);
    }
    private void pointPanelInit(){
        pointPanel = new JPanel();
        cursorLabel = new JLabel("Cursor: ("+cursor.getX()+","+cursor.getY()+")",SwingConstants.CENTER);
        placementLabel = new JLabel("Entity Location: ",SwingConstants.CENTER);
        dimensionLabel = new JLabel("Width & Height of Entity: ",SwingConstants.CENTER);
        pointPanel.setLayout(new GridLayout(4,1));
        
        pointPanel.add(new JLabel("<html><i>Locations & Coordinates<i><html>",SwingConstants.CENTER));
        pointPanel.add(cursorLabel);
        pointPanel.add(placementLabel);
        pointPanel.add(dimensionLabel);
        pointPanel.setBackground(new Color(250,250,210));
        
        pointPanel.setPreferredSize(new Dimension(220,EntityTile.size));
        pointPanel.setBounds(gap+textPanel.getWidth()+gap,gap+panelHeight+gap,pointPanel.getPreferredSize().width ,EntityTile.size);
        add(pointPanel);
    }
    private void setPointPanelText(){
        cursorLabel.setText("Cursor: ("+cursor.getX()+","+cursor.getY()+")");        
        placement = editPanel.getPlacement();
        placementLabel.setText("Entity Location: ("+placement.getX()+","+placement.getY()+")");
        dimension = editPanel.getDimension();
        dimensionLabel.setText("Dimension of Entity: ("+dimension.getWidth()+","+dimension.getHeight()+")");
    }
    private void detailedSelectorPanelInit(){
        for(int i=0;i<TOTAL_SELECTORS;i++){
            detailedSelectorPanel[i] = new JPanel();
            detailedSelectorPanel[i].setPreferredSize(detailedSelectorPanelHolder.getPreferredSize());
            detailedSelectorPanel[i].setLayout(new GridLayout(8,5));
        }
    }
    private void labelerPanelInit(){
        labelerPanel = new JPanel();
        labelerLabel = new JLabel("<html><br>"+"Entity"+"<html>");
        labelerPanel.add(labelerLabel,SwingConstants.CENTER);
        labelerPanel.setPreferredSize(new Dimension(detailedSelectorPanelHolder.getPreferredSize().width,
            textPanel.getPreferredSize().height));
        labelerPanel.setBackground(new Color(250,250,210));
        labelerPanel.setBounds(panelWidth+gap+gap,detailedSelectorPanelHolder.getPreferredSize().height+gap+gap,
                labelerPanel.getPreferredSize().width,labelerPanel.getPreferredSize().height);
        
        add(labelerPanel);
    }
    private void checkLabelerPanel(){
        if(editPanel.currentDetailedSelectorId!=null){
            labelerLabel.setText("<html><br>"+editPanel.currentDetailedSelectorId+"<html>");
        }
    }
    private void addDetailedSelectorButtons(){
        //detailedSelectors[PLAYER_CHARACTER];
        detailedSelectors[PLAYER_CHARACTER][0] = new EntityTile("Player","Player");
        
        //detailedSelectors[ENTITY];
        detailedSelectors[ENTITY][0] = new EntityTile("Entity","Entity");
        detailedSelectors[ENTITY][1] = new EntityTile("Opaque","OpaqueEntity");
        detailedSelectors[ENTITY][2] = new EntityTile("Damagable","DamagableEntity");
        detailedSelectors[ENTITY][3] = new EntityTile("Talk Gate","TalkableGate");
        detailedSelectors[ENTITY][4] = new EntityTile("Map Gate(Default)","MapGateReachGate"); 
        detailedSelectors[ENTITY][5] = new EntityTile("Map Gate(Kill-All)","MapGateKillAll");        
        detailedSelectors[ENTITY][6] = new EntityTile("Main Portal(Default)","MainPortalDefault");
        detailedSelectors[ENTITY][7] = new EntityTile("Sub-Portal(Default)","SubPortalDefault");
        detailedSelectors[ENTITY][8] = new EntityTile("Main Portal(Kill-All)","MainPortalKillAll");
        detailedSelectors[ENTITY][9] = new EntityTile("Sub-Portal(Kill-All)","SubPortalKillAll");
        
        //detailedSelectors[NONPLAYERCHARACTER];
        detailedSelectors[NONPLAYERCHARACTER][0] = new EntityTile("Warrior","Warrior");
        detailedSelectors[NONPLAYERCHARACTER][1] = new EntityTile("BR-War","BossroomWarrior");
        detailedSelectors[NONPLAYERCHARACTER][2] = new EntityTile("Archer","Archer");
        detailedSelectors[NONPLAYERCHARACTER][3] = new EntityTile("BR-Arch","BossroomArcher");
        detailedSelectors[NONPLAYERCHARACTER][4] = new EntityTile("Tank","Tank");
        detailedSelectors[NONPLAYERCHARACTER][5] = new EntityTile("BR-Tank","BossroomTank");
        detailedSelectors[NONPLAYERCHARACTER][6] = new EntityTile("Subboss","Subboss");
        detailedSelectors[NONPLAYERCHARACTER][7] = new EntityTile("BR-Sub","BossroomSubboss");
        detailedSelectors[NONPLAYERCHARACTER][8] = new EntityTile("Boss","Boss");
                
        //detailedSelectors[ITEM];                
        detailedSelectors[ITEM][0] = new EntityTile("Armor","Armor");
        detailedSelectors[ITEM][1] = new EntityTile("Weapon","Weapon");
        detailedSelectors[ITEM][2] = new EntityTile("Bow","Bow");
        detailedSelectors[ITEM][3] = new EntityTile("Arrow","Arrow");
        detailedSelectors[ITEM][4] = new EntityTile("H-Pot","HealthPotion");
        detailedSelectors[ITEM][5] = new EntityTile("S-Pot","StrengthPotion");
        
        for(int i=0;i<TOTAL_SELECTORS;i++){
            for(int j=0;j<detailedSelectors[i].length;j++){
                if(detailedSelectors[i][j]==null){}
                else{
                detailedSelectorPanel[i].add(detailedSelectors[i][j]);
                detailedSelectorPanel[i].setBackground(new Color(197,179,88));
                }
            }
        }
    }
    private void addActions(){
        defaultOptions.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                editPanel.initDefaults();
            }
        });
        customOptions.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                customPanel = new CustomOptionsPanel(LevelEditorDisplay.this,editPanel);
            }
        });
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(editPanel.playerSet){
                    editPanel.setGameNpcs();
                    nelsontsui.nelsonsgame.game.Character player = editPanel.getPlayer();
                    ArrayList<Entity> npcs = editPanel.getNpcs();
                    FileSelector fileSelector = new FileSelector(FileSelector.SAVE,npcs,player);
                }
                else{
                    JOptionPane.showMessageDialog(LevelEditorDisplay.this, "You must place a Player!", "No Player!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        importFile.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(editPanel.placedSomething){
                    JOptionPane.showMessageDialog(LevelEditorDisplay.this, "There are unsaved changes. Are you sure you want to import?", "Unsaved Changes", JOptionPane.INFORMATION_MESSAGE);
                }
                FileSelector fileSelector = new FileSelector(FileSelector.IMPORT,new ArrayList<Entity>(),null);
                editPanel.setNpcs(fileSelector.getNpcs());
                editPanel.setPlayer(fileSelector.getPlayer());
                editPanel.setGridNpcs();
            }
        });
    }
    public void checkIfCanToggle(){
        selectors[PLAYER_CHARACTER].canToggle(selectors);
        selectors[ENTITY].canToggle(selectors);
        selectors[NONPLAYERCHARACTER].canToggle(selectors);
        selectors[ITEM].canToggle(selectors);
        
        for(int i=0;i<TOTAL_SELECTORS;i++){
            for(int j=0;j<TOTAL_DETAILEDSELECTORS;j++){
                if(detailedSelectors[i][j]==null){}
                else{
                detailedSelectors[i][j].canToggle(detailedSelectors[i]);
                }
            }
        }
    }
    private void haveSelectorsBeenClicked(){
        selectors[PLAYER_CHARACTER].addMouseListener(new MouseListener(){           
            @Override
            public void mouseReleased(MouseEvent e) {
                if(selectors[PLAYER_CHARACTER].getClicked()){            
                detailedSelectorPanel[PLAYER_CHARACTER].setBounds(detailedSelectorPanelHolder.getBounds());
                LevelEditorDisplay.this.remove(detailedSelectorPanelHolder);
                detailedSelectorPanelHolder = detailedSelectorPanel[PLAYER_CHARACTER];
                editPanel.setDetailedSelectors(detailedSelectors[PLAYER_CHARACTER]);
                add(detailedSelectorPanelHolder);            
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
            
            @Override
            public void mouseClicked(MouseEvent e) {}
            
            @Override
            public void mousePressed(MouseEvent e) {}
        });
        selectors[ENTITY].addMouseListener(new MouseListener(){           
            @Override
            public void mouseReleased(MouseEvent e) {
                if(selectors[ENTITY].getClicked()){            
                detailedSelectorPanel[ENTITY].setBounds(detailedSelectorPanelHolder.getBounds());
                LevelEditorDisplay.this.remove(detailedSelectorPanelHolder);
                detailedSelectorPanelHolder = detailedSelectorPanel[ENTITY];
                editPanel.setDetailedSelectors(detailedSelectors[ENTITY]);
                add(detailedSelectorPanelHolder);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
            
            @Override
            public void mouseClicked(MouseEvent e) {}
            
            @Override
            public void mousePressed(MouseEvent e) {}
        });
        selectors[NONPLAYERCHARACTER].addMouseListener(new MouseListener(){           
            @Override
            public void mouseReleased(MouseEvent e) {
                if(selectors[NONPLAYERCHARACTER].getClicked()){
                detailedSelectorPanel[NONPLAYERCHARACTER].setBounds(detailedSelectorPanelHolder.getBounds());
                LevelEditorDisplay.this.remove(detailedSelectorPanelHolder);
                detailedSelectorPanelHolder = detailedSelectorPanel[NONPLAYERCHARACTER];
                editPanel.setDetailedSelectors(detailedSelectors[NONPLAYERCHARACTER]);
                add(detailedSelectorPanelHolder);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
            
            @Override
            public void mouseClicked(MouseEvent e) {}
            
            @Override
            public void mousePressed(MouseEvent e) {}
        });
        selectors[ITEM].addMouseListener(new MouseListener(){           
            @Override
            public void mouseReleased(MouseEvent e) {
                if(selectors[ITEM].getClicked()){
                detailedSelectorPanel[ITEM].setBounds(detailedSelectorPanelHolder.getBounds());
                LevelEditorDisplay.this.remove(detailedSelectorPanelHolder);
                detailedSelectorPanelHolder = detailedSelectorPanel[ITEM];
                editPanel.setDetailedSelectors(detailedSelectors[ITEM]);
                add(detailedSelectorPanelHolder);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
            
            @Override
            public void mouseClicked(MouseEvent e) {}
            
            @Override
            public void mousePressed(MouseEvent e) {}
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        checkIfCanToggle();        
        checkIfCanToggle();
        setPointPanelText();  
        checkLabelerPanel();
        validate();
        repaint();
    }
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}
