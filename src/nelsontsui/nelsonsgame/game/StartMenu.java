package nelsontsui.nelsonsgame.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class StartMenu extends JPanel implements ActionListener{
    private JButton play;//functionality set in MainDisplay
    private JButton howTo;//func set in MainDisplay
    private JButton importButton;//func set in MainDisplay
    private JButton leveleditor; //func set in MainDisplay
    JPanel buttonHolder;
    public StartMenu(JButton play,JButton howTo, JButton importButton, JButton leveleditor){
        
        this.play = play;
        this.howTo = howTo;
        this.importButton = importButton;
        this.leveleditor = leveleditor;
        
        setLayout();
        
        setBackground(Color.BLUE);
        setPreferredSize(new Dimension(1000,610));
    }
    private void setLayout(){
        buttonHolder = new JPanel();
        buttonHolder.setLayout(new GridLayout(4,1));
        buttonHolder.add(play); 
        buttonSize(play);
        buttonHolder.add(howTo); 
        buttonSize(howTo);
        buttonHolder.add(importButton); 
        buttonSize(importButton);
        buttonHolder.add(leveleditor); 
        buttonSize(leveleditor);
        buttonHolder.setPreferredSize(new Dimension(100,100));
        buttonHolder.setBackground(Color.red);
        
        setLayout(new BorderLayout());
        add(buttonHolder, BorderLayout.CENTER);
    }
    private void buttonSize(JButton b){
        b.setPreferredSize(new Dimension(25,100));
    }  
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
