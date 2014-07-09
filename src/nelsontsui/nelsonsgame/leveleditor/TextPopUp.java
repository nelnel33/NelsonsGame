package nelsontsui.nelsonsgame.leveleditor;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextPopUp extends JDialog{
    private JTextArea text;
    private JScrollPane pane;
    public TextPopUp(JFrame owner, String title, JTextArea text){
        super(owner,title);    
        this.text = text;
        
        showInstructions();
        
        getContentPane().setBackground(new Color(250,250,210));
        
        add(pane);
        
        setPreferredSize(new Dimension(300,500));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        
        pack();
        setLocationRelativeTo(owner);
    }
    private void showInstructions(){
        text.setLineWrap(true);
        text.setWrapStyleWord(true);  
        text.setEditable(false);
        text.setBackground(new Color(250,250,210));
        pane = new JScrollPane(text);   
        pane.setBackground(new Color(250,250,210));
    }    
}
