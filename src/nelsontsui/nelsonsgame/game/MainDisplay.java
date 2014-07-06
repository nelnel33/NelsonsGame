package nelsontsui.nelsonsgame.game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import nelsontsui.nelsonsgame.leveleditor.LevelEditorDisplay;

public class MainDisplay extends JFrame implements ActionListener{
    private GameDisplay gameDisplay;
    private StartMenu startMenu;
    
    //Button passed to GameDisplay
    private JButton toStartMenu;
    
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
    
    
    public MainDisplay(){
        super("Nelson's Game");
        
        init();

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
        toStartMenu.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                removeGameDisplay();
                swapToStartMenu();
                MainDisplay.this.repaint();
            }
        });
    }
    private void swapToGameDisplay(ArrayList<Entity> e, Character p){
        gameDisplay = new GameDisplay(toStartMenu,e,p);
        add(gameDisplay);
        gameDisplay.getFocus();
    }
    private void swapToDefaultGameDisplay(){
        gameDisplay = new GameDisplay(toStartMenu);          
        add(gameDisplay);
        gameDisplay.getFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    public static void main(String[] args){
        MainDisplay main = new MainDisplay();
    }
    
}
