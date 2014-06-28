package nelsontsui.nelsonsgame.game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        importButton = new JButton("Import");
        leveleditor = new JButton("Level Editor");
        
        setStartMenuButtonAction();
        setGameDisplayButtonAction();
        startMenu = new StartMenu(play,howTo,importButton,leveleditor);
        add(startMenu);
    }
    private void framePlacement(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
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
                swapToGameDisplay();
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
    private void swapToGameDisplay(){
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
