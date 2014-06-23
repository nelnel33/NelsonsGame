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

public class LevelEditorDisplay extends JDialog implements ActionListener{   
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
    private Point cursor;
    private Point drag;
    private DimensionDouble dragArea;
    
    //the name of the file that is being written to
    private String fileName;
    
    private EditingPanel editPanel;
    
    //bottom right, main selectors
    private JPanel selectorPanel;
    private EntityTile[] selectors = new EntityTile[TOTAL_SELECTORS];
    
    //misc buttons
    private JButton save;
    private JButton importFile;
    
    //selectors for detailed selecting
    private JPanel detailedSelectorPanelHolder;//Placeholder for starting    
    private JPanel[] detailedSelectorPanel; //index of TOTAL_SELECTORS.   
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
       selectorPanelInit();
       detailedSelectorPanelHolderInit();       
       selectorLayout();
       
       check.start();
       
       setSize(gap+panelWidth+gap+detailedSelectorPanelHolder.getWidth()+gap,
               gap+panelHeight+gap+selectorPanel.getHeight()+gap+gap+gap);
       setResizable(false);
       setVisible(true);
       setDefaultCloseOperation(DISPOSE_ON_CLOSE);   
    }
    private void editingPanelInit(){
        cursor = new Point(0.0,0.0);
        drag = new Point(0.0,0.0);
        dragArea = new DimensionDouble(0.0,0.0);
        editPanel = new EditingPanel(cursor,drag,dragArea);
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
        selectorPanel.add(save);
        selectorPanel.add(importFile);
    }
    private void initSelectors(){        
        selectors[PLAYER_CHARACTER] = new EntityTile("Player");
        selectors[ENTITY] = new EntityTile("Entity");
        selectors[NONPLAYERCHARACTER] = new EntityTile("NPC");
        selectors[ITEM] = new EntityTile("Item");
        save = new JButton("Save");
        importFile = new JButton("Import File");
    }
    private void textPanel(){
        textPanel = new JPanel();
        textPanel.setBorder(GameDisplay.blackborder);
        textPanel.add(new JLabel("<html><b>Nelson's Game: <b><html>",SwingConstants.CENTER));
        textPanel.add(new JLabel("<html><b>Level Editor<b><html>",SwingConstants.CENTER));
        textPanel.setPreferredSize(new Dimension(180,EntityTile.size));
        textPanel.setBounds(gap,gap+panelHeight+gap,180,EntityTile.size);
        textPanel.setBackground(Color.ORANGE);
        
        add(textPanel);
    }
    private void selectorPanelInit(){
        selectorPanel = new JPanel();
        selectorPanel.setBorder(GameDisplay.blackborder);
        selectorPanel.setLayout(new GridLayout(1,5,2,2));        
        selectorPanel.setSize(new Dimension(EntityTile.size*5,EntityTile.size));        
        selectorPanel.setBounds(gap+textPanel.getPreferredSize().width+gap,gap+panelHeight+gap,EntityTile.size*6,EntityTile.size);        
        selectorPanel.setBackground(Color.BLACK);
        
        add(selectorPanel);
    }
    private void detailedSelectorPanelHolderInit(){
        detailedSelectorPanelHolder = new JPanel();
        //detailedSelectorPanel.setPreferredSize(new GridLayout)
        detailedSelectorPanelHolder.setBorder(GameDisplay.blackborder);        
        detailedSelectorPanelHolder.setBounds(gap+gap+panelWidth,gap,250,460);
        detailedSelectorPanelHolder.setBackground(Color.BLACK);
        add(detailedSelectorPanelHolder);
    }
    public static void main(String[] args){
        LevelEditorDisplay c = new LevelEditorDisplay(new JFrame());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        selectors[PLAYER_CHARACTER].canToggle(selectors);
        selectors[ENTITY].canToggle(selectors);
        selectors[NONPLAYERCHARACTER].canToggle(selectors);
        selectors[ITEM].canToggle(selectors);
        
        System.out.println(cursor.getX());
    }
    
}
