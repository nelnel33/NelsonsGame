package nelsontsui.nelsonsgame.game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import nelsontsui.nelsonsgame.leveleditor.LevelEditorDisplay;

/*
|=|=SerialVersionUIDs=|=|
Entity - 331
OpaqueEntity - 332
DamagableEntity - 333
Character - 334
NonPlayerCharacter - 335

MapGate - 3311
TalkableGate - 3312
Portal - 3313

SpawnableItem - 3314
Item - 33141
UnusuableItem - 33142
Armor - 33143
Weapon - 33144
Bow - 33145
Arrow - 33146
Potion - 33147
StrengthPotion - 33148
HealthPotion - 33149

Hitbox - 341
Point - 342

Inventory - 351
*/

public class MainDisplay extends JFrame implements ActionListener{
    private GameDisplay gameDisplay;
    private StartMenu startMenu;
    
    //Button passed to GameDisplay
    private JButton toStartMenu;
    private JButton restartButton;
    private JButton importButtonGameDisplay;
    
    //Buttons in the start menu
    private JButton play;
    private JButton howTo;
    private JButton importButton;
    private JButton leveleditor;
    
    //JDialog that contains howto/instructions
    private HowTo instructions;
    
    //JDialog that contains Import - file selector
    private FileSelector importer;
    
    //JDialog that contains LevelEditor
    private LevelEditorDisplay levelEditor;
    
    //Warning Message 
    private boolean canBeClicked;
    
    //Win, Lose, and General Messages
    public static final String winMessageMain = "\nYOU WON!";
    public static final String genMessage = "Press \'Start Menu\' to return to the Start Menu";
    public static final String loseMessageMain = "\nYou Lose. :(";
    
    public static final String winMessageCruel = "\nWoohoo, you won. \nI'm not impressed. \nFor the record. \nThis was not in anyway impressive.";
    public static final String loseMessageCruel = "\nYou lost... \nhow many times has this been? \nI lost count.";
    
    
    public MainDisplay(){
        super("Nelson's Game");
        
        init();
        
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000,610));        
        pack();
        //framePlacement();
        setLocationRelativeTo(null);
    }
    private void init(){
        play = new JButton("Play Nelson's Game");
        howTo = new JButton("Instructions");
        importButton = new JButton("Import and Play!");
        leveleditor = new JButton("Level Editor");
        
        setStartMenuButtonAction();
        setGameDisplayButtonAction();
        startMenu = new StartMenu(play,howTo,importButton,leveleditor);
        add(startMenu);
    }
    private void removeStartMenu(){
        this.remove(startMenu);
    }
    private void removeGameDisplay(){
        this.remove(gameDisplay);
    }
    private void setStartMenuButtonAction(){
        play.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                removeStartMenu();
                swapToDefaultGameDisplay();
            }
        });  
        howTo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                instructions = new HowTo(MainDisplay.this);                
            }
        });
        importButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                FileSelector f = new FileSelector(FileSelector.IMPORT,null,null);
                if(f.getNpcs()!=null && f.getPlayer()!=null){
                    ArrayList<Entity> npcs = f.getNpcs();
                    Character player = f.getPlayer();
                    removeStartMenu();
                    swapToGameDisplay(npcs,player);
                    gameDisplay.setNpcs(npcs);
                    gameDisplay.setPlayer(player);
                }
            }
        });
        leveleditor.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                levelEditor = new LevelEditorDisplay(MainDisplay.this);
            }
        });
    }
    private void swapToStartMenu(){
        add(startMenu);
    }
    private void setGameDisplayButtonAction(){
        toStartMenu = new JButton("Start Menu");
        restartButton = new JButton("Restart");
        importButtonGameDisplay = new JButton("Import");
        toStartMenu.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(warningMessage()){
                    removeGameDisplay();
                    swapToStartMenu();
                    MainDisplay.this.repaint();
                }
            }
        });
        restartButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(warningMessage()){
                    removeGameDisplay();
                    swapToDefaultGameDisplay();
                    MainDisplay.this.repaint();
                }
            }
        });   
        importButtonGameDisplay.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                FileSelector f = new FileSelector(FileSelector.IMPORT,null,null);
                if(f.getNpcs()!=null && f.getPlayer()!=null){
                    ArrayList<Entity> npcs = f.getNpcs();
                    Character player = f.getPlayer();
                    removeGameDisplay();
                    swapToGameDisplay(npcs,player);
                    gameDisplay.setNpcs(npcs);
                    gameDisplay.setPlayer(player);
                }
            }
        });
    }
    private boolean warningMessage(){
        int n = -1;         
        Object[] options = { "OK", "CANCEL" };
            n = JOptionPane.showOptionDialog(MainDisplay.this, "Are you sure you want to exit the level?", "Exit Level?",
            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);       
        if(n == JOptionPane.NO_OPTION){              
            return false;
        }
        else{
            return true;
        }
                
    }
    private void swapToGameDisplay(ArrayList<Entity> e, Character p){
        gameDisplay = new GameDisplay(toStartMenu,restartButton,importButtonGameDisplay,e,p);
        add(gameDisplay);
        gameDisplay.getFocus();
    }
    private void swapToDefaultGameDisplay(){
        gameDisplay = new GameDisplay(toStartMenu,restartButton,importButtonGameDisplay);          
        add(gameDisplay);
        gameDisplay.getFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
        //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        /*
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
            UIManager.setLookAndFeel(info.getClassName());
            break;
                }
            }
        } catch (Exception e) {
            System.out.println("Cannot set desired UI. Default UI was set.");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        */
        MainDisplay main = new MainDisplay();
    }
    
}
