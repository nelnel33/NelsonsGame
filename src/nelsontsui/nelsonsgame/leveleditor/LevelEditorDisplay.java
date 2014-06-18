package nelsontsui.nelsonsgame.leveleditor;

import nelsontsui.nelsonsgame.game.GameDisplay;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LevelEditorDisplay extends JFrame{    
    private static final int TOTAL_SELECTORS = 4;
    private static final int TOTAL_DETAILEDSELECTORS = 40;
    
    private final int gap = 10;
    
    private int panelWidth = GameDisplay.edgeX;
    private int panelHeight = GameDisplay.edgeY;
    
    private String fileName;
    
    private JPanel activePanel;
    
    private JPanel selectorPanel;
    private EntityTile[] selectors = new EntityTile[TOTAL_SELECTORS];
    private JButton save;
    private JButton importFile;
    
    private JPanel detailedSelectorPanelHolder;
    
    private JPanel[] detailedSelectorPanel;    
    private EntityTile[][] detailedSelectors = new EntityTile[TOTAL_SELECTORS][TOTAL_DETAILEDSELECTORS];
    
    private JPanel textPanel;
       
    private static final int PLAYER_CHARACTER = 0;
    private static final int ENTITY = 1;
    private static final int NONPLAYERCHARACTER = 2;
    private static final int ITEM = 3;
    
    public LevelEditorDisplay(){
       super("Nelson's Game: Level Editor");
       setLayout(null);
       
       activePanelInit();
       selectorPanelInit();  
       textPanel();
       detailedSelectorPanelHolderInit();
       
       selectorLayout();
       
       setSize(gap+panelWidth+gap+detailedSelectorPanelHolder.getWidth()+gap,
               gap+panelHeight+gap+selectorPanel.getHeight()+gap+gap+gap);
       setResizable(false);
       setVisible(true);
       setDefaultCloseOperation(EXIT_ON_CLOSE);   
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
        textPanel.add(new JLabel("<html><br><b>Nelson's Game:<b><br><html>",SwingConstants.CENTER));
        textPanel.add(new JLabel("<html><b>Level Editor<b><html>",SwingConstants.CENTER));
        textPanel.setBounds(gap+600+gap,gap+panelHeight+gap,130,EntityTile.size);
        textPanel.setBackground(Color.ORANGE);
        
        add(textPanel);
    }
    private void activePanelInit(){
        activePanel = new JPanel();
        activePanel.setBorder(GameDisplay.blackborder);
        activePanel.setSize(new Dimension(panelWidth,panelHeight));        
        activePanel.setBounds(gap,gap,panelWidth,panelHeight);
        activePanel.setBackground(Color.white);
        
        add(activePanel);
    }
    private void selectorPanelInit(){
        selectorPanel = new JPanel();
        selectorPanel.setBorder(GameDisplay.blackborder);
        selectorPanel.setLayout(new GridLayout(1,5,2,2));        
        selectorPanel.setSize(new Dimension(EntityTile.size*5,EntityTile.size));        
        selectorPanel.setBounds(gap,gap+panelHeight+gap,EntityTile.size*6,EntityTile.size);        
        selectorPanel.setBackground(Color.BLACK);
        
        add(selectorPanel);
    }
    private void detailedSelectorPanelHolderInit(){
        detailedSelectorPanelHolder = new JPanel();
        //detailedSelectorPanel.setPreferredSize(new GridLayout)
        detailedSelectorPanelHolder.setBorder(GameDisplay.blackborder);        
        detailedSelectorPanelHolder.setBounds(gap+gap+panelWidth,gap,250,510);
        detailedSelectorPanelHolder.setBackground(Color.BLACK);
        add(detailedSelectorPanelHolder);
    }
    public static void main(String[] args){
        LevelEditorDisplay c = new LevelEditorDisplay();
    }
    
}
