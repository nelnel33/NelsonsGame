package nelsontsui.nelsonsgame.leveleditor;

import nelsontsui.nelsonsgame.game.entities.SpawnableItem;
import nelsontsui.nelsonsgame.game.entities.TalkableGate;
import nelsontsui.nelsonsgame.game.entities.Portal;
import nelsontsui.nelsonsgame.game.entities.OpaqueEntity;
import nelsontsui.nelsonsgame.game.entities.NonPlayerCharacter;
import nelsontsui.nelsonsgame.game.entities.Player;
import nelsontsui.nelsonsgame.game.entities.MapGate;
import nelsontsui.nelsonsgame.game.mapping.Level;
import nelsontsui.nelsonsgame.game.entities.DamageableEntity;
import nelsontsui.nelsonsgame.game.entities.Entity;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import nelsontsui.nelsonsgame.game.*;
import nelsontsui.nelsonsgame.game.mapping.Point;
import nelsontsui.nelsonsgame.game.entities.Character;

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
    private Level level;
    private ArrayList<Entity> entities = new ArrayList<>();
    private Player player;
    
    //width and height of the map/gameplay panel
    private int panelWidth = GameDisplay.ACTIONPANEL_WIDTH;
    private int panelHeight = GameDisplay.ACTIONPANEL_HEIGHT;
    
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
    private JButton analyze;
    private JButton instructions;
    private JButton defaultOptions;
    private JButton customOptions;
    
    //selectors for detailed selecting
    private JPanel detailedSelectorPanelHolder;//Placeholder for starting    
    private JPanel[] detailedSelectorPanel = new JPanel[TOTAL_SELECTORS]; //index of TOTAL_SELECTORS.   
    private EntityTile[][] detailedSelectors = new EntityTile[TOTAL_SELECTORS][TOTAL_DETAILEDSELECTORS];
    
    //text/name
    private JPanel textPanel;
    private JScrollPane labelerPanel;
    private JLabel labelerLabel;
    private String labelerText;    
    
    //AnalysisPanel
    private AnalysisPanel analysis;
    private String analysisText;
    
    //timer
    private Timer check = new Timer((int)ActionPanel.TICK,this);
    
    //JDialog for customoptions
    private CustomOptionsPanel customPanel;
    
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
       addCloseButtonAction();
       
       getContentPane().setBackground(new Color(55,198,164));
       
       check.addActionListener(editPanel);
       check.start();
       
       addActions();
       
       setPreferredSize(new Dimension(gap+panelWidth+gap+detailedSelectorPanelHolder.getWidth()+gap,
              gap+panelHeight+gap+selectorPanel.getHeight()+gap+gap+gap));
       setResizable(false);
       setVisible(true);
       setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);   
       pack();
       setLocationRelativeTo(null);
    }
    private void addCloseButtonAction(){
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
                int n = -1;
                if(editPanel.placedSomething){
                    Object[] options = { "OK", "CANCEL" };
                    n = JOptionPane.showOptionDialog(LevelEditorDisplay.this, "There are unsaved changes. Are you sure you want to close the window?", "Unsaved Changes",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);        
                }
                if(n == JOptionPane.NO_OPTION){              
                    //does nothing
                }
                else{
                    e.getWindow().dispose();
                }
            }
        });
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
        textPanel.setLayout(new GridLayout(3,1));
        analyze = new JButton("Analyze");
        //textPanel.setBorder(GameDisplay.blackborder);
        textPanel.add(new JLabel("<html><b>Nelson's Game: <b><html>",SwingConstants.CENTER));
        textPanel.add(new JLabel("<html><b>Level Editor<b><html>",SwingConstants.CENTER));
        textPanel.add(analyze);
        textPanel.setPreferredSize(new Dimension(120,EntityTile.size));
        textPanel.setBounds(gap,gap+panelHeight+gap,textPanel.getPreferredSize().width,EntityTile.size);
        textPanel.setBackground(new Color(197,179,88));
        
        add(textPanel);
    }
    private void buttonHolderPanelInit(){
        save = new JButton("Save");
        importFile = new JButton("Import");
        instructions = new JButton("Instructions");        
        saveAndImportButtonHolder = new JPanel();
        saveAndImportButtonHolder.setLayout(new GridLayout(3,1));
        saveAndImportButtonHolder.add(save);
        saveAndImportButtonHolder.add(importFile);
        saveAndImportButtonHolder.add(instructions);
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
        setLabelerPanelText();
        labelerLabel = new JLabel(labelerText);
        labelerPanel = new JScrollPane(labelerLabel,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        labelerPanel.setPreferredSize(new Dimension(detailedSelectorPanelHolder.getPreferredSize().width,
            textPanel.getPreferredSize().height));
        labelerPanel.setBackground(new Color(250,250,210));
        labelerPanel.setBounds(panelWidth+gap+gap,detailedSelectorPanelHolder.getPreferredSize().height+gap+gap,
                labelerPanel.getPreferredSize().width,labelerPanel.getPreferredSize().height);
        
        add(labelerPanel);
    }
    private void setLabelerPanelText(){
        int ex = (int)placement.getX()/10;
        int ey = (int)placement.getY()/10;
        Entity e = editPanel.npcsOnGrid[ey][ex];
        if(e==null){
            //analysisText = "";
            labelerText = "";
        }
        else{
            analysisText = e.toString();
            labelerText = StringWrapper.wrapOnSemiColonHTML(e.toString());
        }
    }
    private void checkLabelerPanel(){
        //if(editPanel.currentDetailedSelectorId!=null){
            //labelerLabel.setText("<html><br>"+editPanel.currentDetailedSelectorId+"<html>");
            setLabelerPanelText();
            labelerLabel.setText(labelerText);
            labelerPanel.requestFocusInWindow();
        //}
    }
    private void addDetailedSelectorButtons(){
        //detailedSelectors[PLAYER_CHARACTER];
        detailedSelectors[PLAYER_CHARACTER][0] = new EntityTile("Player","Player");
            detailedSelectors[PLAYER_CHARACTER][0].setToolTipText(
                    StringWrapper.wrapOnSemiColonHTML(Character.DESCRIPTION));
        
        
        //detailedSelectors[ENTITY];
        detailedSelectors[ENTITY][0] = new EntityTile("Entity","Entity");
            detailedSelectors[ENTITY][0].setToolTipText(
                    StringWrapper.wrapOnSemiColonHTML(Entity.DESCRIPTION));
        detailedSelectors[ENTITY][1] = new EntityTile("Opaque","OpaqueEntity");
            detailedSelectors[ENTITY][1].setToolTipText(
                    StringWrapper.wrapOnSemiColonHTML(OpaqueEntity.DESCRIPTION));
        detailedSelectors[ENTITY][2] = new EntityTile("Damagable","DamagableEntity");
            detailedSelectors[ENTITY][2].setToolTipText(StringWrapper.wrapOnSemiColonHTML(DamageableEntity.DESCRIPTION));
        detailedSelectors[ENTITY][3] = new EntityTile("Talk Gate","TalkableGate");
            detailedSelectors[ENTITY][3].setToolTipText(
                    StringWrapper.wrapOnSemiColonHTML(TalkableGate.DESCRIPTION));
        detailedSelectors[ENTITY][4] = new EntityTile("Map Gate(Default)","MapGateReachGate"); 
            detailedSelectors[ENTITY][4].setToolTipText(
                    StringWrapper.wrapOnSemiColonHTML(MapGate.DESCRIPTION));
        detailedSelectors[ENTITY][5] = new EntityTile("Exit Map(Default)","MapGateReachGateExit");
            detailedSelectors[ENTITY][5].setToolTipText(
                    StringWrapper.wrapOnSemiColonHTML(MapGate.DESCRIPTION));
        detailedSelectors[ENTITY][6] = new EntityTile("Map Gate(Kill-All)","MapGateKillAll"); 
            detailedSelectors[ENTITY][6].setToolTipText(
                    StringWrapper.wrapOnSemiColonHTML(MapGate.DESCRIPTION));
        detailedSelectors[ENTITY][7] = new EntityTile("Exit Map(Kill-All)","MapGateKillAllExit");
            detailedSelectors[ENTITY][7].setToolTipText(
                    StringWrapper.wrapOnSemiColonHTML(MapGate.DESCRIPTION));
        detailedSelectors[ENTITY][8] = new EntityTile("Main Portal(Default)","MainPortalDefault");
            detailedSelectors[ENTITY][8].setToolTipText(
                    StringWrapper.wrapOnSemiColonHTML(Portal.DESCRIPTION));
        detailedSelectors[ENTITY][9] = new EntityTile("Sub-Portal(Default)","SubPortalDefault");
            detailedSelectors[ENTITY][9].setToolTipText(
                    StringWrapper.wrapOnSemiColonHTML(Portal.DESCRIPTION));
        detailedSelectors[ENTITY][10] = new EntityTile("Main Portal(Kill-All)","MainPortalKillAll");
            detailedSelectors[ENTITY][10].setToolTipText(
                    StringWrapper.wrapOnSemiColonHTML(Portal.DESCRIPTION));
        detailedSelectors[ENTITY][11] = new EntityTile("Sub-Portal(Kill-All)","SubPortalKillAll");
            detailedSelectors[ENTITY][11].setToolTipText(
                    StringWrapper.wrapOnSemiColonHTML(Portal.DESCRIPTION));
        
        //detailedSelectors[NONPLAYERCHARACTER];
        detailedSelectors[NONPLAYERCHARACTER][0] = new EntityTile("Warrior","Warrior");
            detailedSelectors[NONPLAYERCHARACTER][0].setToolTipText(
                StringWrapper.wrapOnSemiColonHTML(NonPlayerCharacter.DESCRIPTION));
        detailedSelectors[NONPLAYERCHARACTER][1] = new EntityTile("BR-War","BossroomWarrior");
            detailedSelectors[NONPLAYERCHARACTER][1].setToolTipText(
                StringWrapper.wrapOnSemiColonHTML(NonPlayerCharacter.DESCRIPTION));
        detailedSelectors[NONPLAYERCHARACTER][2] = new EntityTile("Archer","Archer");
            detailedSelectors[NONPLAYERCHARACTER][2].setToolTipText(
                StringWrapper.wrapOnSemiColonHTML(NonPlayerCharacter.DESCRIPTION));
        detailedSelectors[NONPLAYERCHARACTER][3] = new EntityTile("BR-Arch","BossroomArcher");
            detailedSelectors[NONPLAYERCHARACTER][3].setToolTipText(
                StringWrapper.wrapOnSemiColonHTML(NonPlayerCharacter.DESCRIPTION));
        detailedSelectors[NONPLAYERCHARACTER][4] = new EntityTile("Tank","Tank");
            detailedSelectors[NONPLAYERCHARACTER][4].setToolTipText(
                    StringWrapper.wrapOnSemiColonHTML(NonPlayerCharacter.DESCRIPTION));
        detailedSelectors[NONPLAYERCHARACTER][5] = new EntityTile("BR-Tank","BossroomTank");
            detailedSelectors[NONPLAYERCHARACTER][5].setToolTipText(
                    StringWrapper.wrapOnSemiColonHTML(NonPlayerCharacter.DESCRIPTION));
        detailedSelectors[NONPLAYERCHARACTER][6] = new EntityTile("Subboss","Subboss");
            detailedSelectors[NONPLAYERCHARACTER][6].setToolTipText(
                    StringWrapper.wrapOnSemiColonHTML(NonPlayerCharacter.DESCRIPTION));
        detailedSelectors[NONPLAYERCHARACTER][7] = new EntityTile("BR-Sub","BossroomSubboss");
            detailedSelectors[NONPLAYERCHARACTER][7].setToolTipText(
                    StringWrapper.wrapOnSemiColonHTML(NonPlayerCharacter.DESCRIPTION));
        detailedSelectors[NONPLAYERCHARACTER][8] = new EntityTile("Boss","Boss");
            detailedSelectors[NONPLAYERCHARACTER][8].setToolTipText(
                    StringWrapper.wrapOnSemiColonHTML(NonPlayerCharacter.DESCRIPTION));
                
        //detailedSelectors[ITEM];                
        detailedSelectors[ITEM][0] = new EntityTile("Armor","Armor");
            detailedSelectors[ITEM][0].setToolTipText(
                StringWrapper.wrapOnSemiColonHTML(SpawnableItem.DESCRIPTION));
        detailedSelectors[ITEM][1] = new EntityTile("Weapon","Weapon");
            detailedSelectors[ITEM][1].setToolTipText(
                StringWrapper.wrapOnSemiColonHTML(SpawnableItem.DESCRIPTION));
        detailedSelectors[ITEM][2] = new EntityTile("ProjectileWeapon","ProjectileWeapon");
            detailedSelectors[ITEM][2].setToolTipText(
                StringWrapper.wrapOnSemiColonHTML(SpawnableItem.DESCRIPTION));
        detailedSelectors[ITEM][3] = new EntityTile("Ammo","Ammo");
            detailedSelectors[ITEM][3].setToolTipText(
                StringWrapper.wrapOnSemiColonHTML(SpawnableItem.DESCRIPTION));
        detailedSelectors[ITEM][4] = new EntityTile("H-Pot","HealthPotion");
            detailedSelectors[ITEM][4].setToolTipText(
                StringWrapper.wrapOnSemiColonHTML(SpawnableItem.DESCRIPTION));
        detailedSelectors[ITEM][5] = new EntityTile("S-Pot","StrengthPotion");
            detailedSelectors[ITEM][5].setToolTipText(
                StringWrapper.wrapOnSemiColonHTML(SpawnableItem.DESCRIPTION));
        
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
                    Player player = editPanel.getPlayer();
                    ArrayList<Entity> entities = editPanel.getEntities();
                    level = new Level(player, entities);
                    FileSelector fileSelector = new FileSelector(FileSelector.SAVE,level);
                    //System.out.println(fileSelector.getResponse());
                    if(fileSelector.getResponse() == JFileChooser.APPROVE_OPTION){
                        editPanel.placedSomething = false;
                    }
                }
                else{
                    JOptionPane.showMessageDialog(LevelEditorDisplay.this, "You must place a Player!", "No Player!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        importFile.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int n = -1;
                if(editPanel.placedSomething){
                    Object[] options = { "OK", "CANCEL" };
                    n = JOptionPane.showOptionDialog(LevelEditorDisplay.this, "There are unsaved changes. Are you sure you want to import?", "Unsaved Changes",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
                    //JOptionPane.showMessageDialog(LevelEditorDisplay.this, "There are unsaved changes. Are you sure you want to import?", "Unsaved Changes", JOptionPane.WARNING_MESSAGE);
                }
                if(n == JOptionPane.NO_OPTION){              
                    //does nothing
                }
                else{
                    FileSelector fileSelector = new FileSelector(FileSelector.IMPORT,null);
                    editPanel.setLevel(fileSelector.getLevel());
                    editPanel.setGridNpcs();
                }
                
            }
        });
        instructions.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                LevelEditorInstructions instructions = new LevelEditorInstructions(LevelEditorDisplay.this);                
            }
        });
        analyze.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                analysis = new AnalysisPanel(LevelEditorDisplay.this);
            }
        });
    }
    private void updateAnalysisPanel(){
        if(analysis!=null && analysisText != null){
            analysis.setText(analysisText);
        }
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
    public void setOtherEntityTiles(){
        selectors[PLAYER_CHARACTER].setButtons(selectors);
        selectors[ENTITY].setButtons(selectors);
        selectors[NONPLAYERCHARACTER].setButtons(selectors);
        selectors[ITEM].setButtons(selectors);
        
        for(int i=0;i<TOTAL_SELECTORS;i++){
            for(int j=0;j<TOTAL_DETAILEDSELECTORS;j++){
                if(detailedSelectors[i][j]==null){}
                else{
                detailedSelectors[i][j].setButtons(detailedSelectors[i]);
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
        updateAnalysisPanel();      
        //checkIfCanToggle();
        setOtherEntityTiles();
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
