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
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import nelsontsui.nelsonsgame.game.Point;
import nelsontsui.nelsonsgame.game.*;

public class LevelEditorDisplay extends JDialog implements ActionListener, MouseListener{   
    //static decs
    private static final int TOTAL_SELECTORS = 4;
    private static final int TOTAL_DETAILEDSELECTORS = 40;
    private static final int PLAYER_CHARACTER = 0;
    private static final int ENTITY = 1;
    private static final int NONPLAYERCHARACTER = 2;
    private static final int ITEM = 3;
    
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
    private JPanel defaultAndSetDimension;
    
    //misc buttons
    private JButton save;
    private JButton importFile;
    private JButton defaultDimension;
    private JButton setDimension;
    
    //selectors for detailed selecting
    private JPanel detailedSelectorPanelHolder;//Placeholder for starting    
    private JPanel[] detailedSelectorPanel = new JPanel[TOTAL_SELECTORS]; //index of TOTAL_SELECTORS.   
    private EntityTile[][] detailedSelectors = new EntityTile[TOTAL_SELECTORS][TOTAL_DETAILEDSELECTORS];
    
    //text/name
    private JPanel textPanel;
    
    //timer
    Timer check = new Timer((int)ActionPanel.TICK,this);
    
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
       
       check.addActionListener(editPanel);
       check.start();
       
       setSize(gap+panelWidth+gap+detailedSelectorPanelHolder.getWidth()+gap,
               gap+panelHeight+gap+selectorPanel.getHeight()+gap+gap+gap);
       setResizable(false);
       setVisible(true);
       setDefaultCloseOperation(DISPOSE_ON_CLOSE);   
    }
    private void editingPanelInit(){
        cursor = new Point(0.0,0.0);
        placement = new Point(0.0,0.0);
        dimension = new DimensionDouble(10,10);
        editPanel = new EditingPanel(cursor,placement,dimension);
        editPanel.setBorder(GameDisplay.blackborder);
        editPanel.setSize(new Dimension(panelWidth,panelHeight));        
        editPanel.setBounds(gap,gap,panelWidth,panelHeight);
        editPanel.setBackground(Color.white);
        
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
        selectors[PLAYER_CHARACTER] = new EntityTile("Player");
        selectors[ENTITY] = new EntityTile("Entity");
        selectors[NONPLAYERCHARACTER] = new EntityTile("NPC");
        selectors[ITEM] = new EntityTile("Item");
    }
    private void textPanel(){
        textPanel = new JPanel();
        textPanel.setBorder(GameDisplay.blackborder);
        textPanel.add(new JLabel("<html><b>Nelson's Game: <b><html>",SwingConstants.CENTER));
        textPanel.add(new JLabel("<html><b>Level Editor<b><html>",SwingConstants.CENTER));
        textPanel.setPreferredSize(new Dimension(120,EntityTile.size));
        textPanel.setBounds(gap,gap+panelHeight+gap,textPanel.getPreferredSize().width,EntityTile.size);
        textPanel.setBackground(Color.ORANGE);
        
        add(textPanel);
    }
    private void buttonHolderPanelInit(){
        save = new JButton("Save");
        importFile = new JButton("Import");
        saveAndImportButtonHolder = new JPanel();
        saveAndImportButtonHolder.setLayout(new GridLayout(2,1));
        saveAndImportButtonHolder.add(save);
        saveAndImportButtonHolder.add(importFile);
        
        defaultDimension = new JButton("Default");
        setDimension = new JButton("Custom");        
        defaultAndSetDimension = new JPanel();
        defaultAndSetDimension.setLayout(new GridLayout(3,1));
        defaultAndSetDimension.add(new JLabel("<html><b>Dimensions<b><html>",SwingConstants.CENTER));        
        defaultAndSetDimension.add(defaultDimension);
        defaultAndSetDimension.add(setDimension);  
        
        buttonHolder = new JPanel();
        buttonHolder.setPreferredSize(new Dimension(170,EntityTile.size));
        buttonHolder.setBounds(gap+textPanel.getPreferredSize().width+
                gap+pointPanel.getPreferredSize().width+gap+selectorPanel.getPreferredSize().width,
                gap+panelHeight+gap,buttonHolder.getPreferredSize().width,EntityTile.size);
        buttonHolder.setLayout(new GridLayout(1,2));
        buttonHolder.add(saveAndImportButtonHolder);
        buttonHolder.add(defaultAndSetDimension);
        
        add(buttonHolder);
    }
    private void selectorPanelInit(){
        selectorPanel = new JPanel();
        selectorPanel.setBorder(GameDisplay.blackborder);
        selectorPanel.setLayout(new GridLayout(1,5,2,2));        
        selectorPanel.setSize(new Dimension(EntityTile.size*5,EntityTile.size));        
        selectorPanel.setBounds(gap+textPanel.getPreferredSize().width+gap+pointPanel.getPreferredSize().width+gap,
                gap+panelHeight+gap,EntityTile.size*4,EntityTile.size);        
        selectorPanel.setBackground(Color.BLACK);
        
        add(selectorPanel);
    }
    private void detailedSelectorPanelHolderInit(){
        detailedSelectorPanelHolder = new JPanel();
        detailedSelectorPanelHolder.setLayout(new GridLayout(8,5));
        detailedSelectorPanelHolder.setBorder(GameDisplay.blackborder);   
        detailedSelectorPanelHolder.setPreferredSize(new Dimension(250,400));
        detailedSelectorPanelHolder.setBounds(gap+gap+panelWidth,gap,
                detailedSelectorPanelHolder.getPreferredSize().width,detailedSelectorPanelHolder.getPreferredSize().height);
        detailedSelectorPanelHolder.setBackground(Color.white);
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
        pointPanel.setBackground(Color.gray);
        
        pointPanel.setPreferredSize(new Dimension(220,EntityTile.size));
        pointPanel.setBounds(gap+textPanel.getWidth()+gap,gap+panelHeight+gap,pointPanel.getPreferredSize().width ,EntityTile.size);
        add(pointPanel);
    }
    public void setPointPanelText(){
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
    private void addDetailedSelectorButtons(){
        //detailedSelectors[PLAYER_CHARACTER];
        detailedSelectors[PLAYER_CHARACTER][0] = new EntityTile("Player");
        
        //detailedSelectors[ENTITY];
        detailedSelectors[ENTITY][0] = new EntityTile("Entity");
        detailedSelectors[ENTITY][1] = new EntityTile("Opaque");
        detailedSelectors[ENTITY][2] = new EntityTile("Damagable");
        detailedSelectors[ENTITY][3] = new EntityTile("Talk Gate");
        detailedSelectors[ENTITY][4] = new EntityTile("Map Gate"); 
        detailedSelectors[ENTITY][5] = new EntityTile("Portal");    
        
        //detailedSelectors[NONPLAYERCHARACTER];
        detailedSelectors[NONPLAYERCHARACTER][0] = new EntityTile("Warrior");
        detailedSelectors[NONPLAYERCHARACTER][1] = new EntityTile("BR-War");
        detailedSelectors[NONPLAYERCHARACTER][2] = new EntityTile("Archer");
        detailedSelectors[NONPLAYERCHARACTER][3] = new EntityTile("BR-Arch");
        detailedSelectors[NONPLAYERCHARACTER][4] = new EntityTile("Tank");
        detailedSelectors[NONPLAYERCHARACTER][5] = new EntityTile("BR-Tank");
        detailedSelectors[NONPLAYERCHARACTER][6] = new EntityTile("Subboss");
        detailedSelectors[NONPLAYERCHARACTER][7] = new EntityTile("BR-Sub");
        detailedSelectors[NONPLAYERCHARACTER][8] = new EntityTile("Boss");
                
        //detailedSelectors[ITEM];                
        detailedSelectors[ITEM][0] = new EntityTile("Armor");
        detailedSelectors[ITEM][1] = new EntityTile("Weapon");
        detailedSelectors[ITEM][2] = new EntityTile("Bow");
        detailedSelectors[ITEM][3] = new EntityTile("Arrow");
        detailedSelectors[ITEM][4] = new EntityTile("H-Pot");
        detailedSelectors[ITEM][4] = new EntityTile("S-Pot");
        
        for(int i=0;i<TOTAL_SELECTORS;i++){
            for(int j=0;j<detailedSelectors[i].length;j++){
                if(detailedSelectors[i][j]==null){}
                else{
                detailedSelectorPanel[i].add(detailedSelectors[i][j]);
                }
            }
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
    public void haveSelectorsBeenClicked(){
        selectors[PLAYER_CHARACTER].addMouseListener(new MouseListener(){           
            @Override
            public void mouseReleased(MouseEvent e) {
                if(selectors[PLAYER_CHARACTER].getClicked()){            
                detailedSelectorPanel[PLAYER_CHARACTER].setBounds(detailedSelectorPanelHolder.getBounds());
                LevelEditorDisplay.this.remove(detailedSelectorPanelHolder);
                detailedSelectorPanelHolder = detailedSelectorPanel[PLAYER_CHARACTER];
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
        //checkIfSelectorsClicked();        
        checkIfCanToggle();
        setPointPanelText();  
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

    public static void main(String[] args){
        LevelEditorDisplay c = new LevelEditorDisplay(new JFrame());
    }
    
}
