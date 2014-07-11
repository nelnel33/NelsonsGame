package nelsontsui.nelsonsgame.leveleditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import nelsontsui.nelsonsgame.game.Character;
import nelsontsui.nelsonsgame.game.Entity;

public class AnalysisPanel extends JDialog implements ActionListener{    
    private JTextArea text;
    private JScrollPane pane;
    public AnalysisPanel(JDialog owner){
        super(owner, "Nelson's Game: Entity Analyzer");
        
        text = new JTextArea();
                
        showPane();
        
        setPreferredSize(new Dimension(450,400));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        
        pack();
        setLocationRelativeTo(owner);
    }
    public void setText(String s){
        s = StringWrapper.wrapOnSemiColon(s);
        text.setText(s);
    }
    
    private void showPane(){
        text.setLineWrap(true);
        text.setWrapStyleWord(true);  
        text.setEditable(false);
        text.setBackground(new Color(250,250,210));
        pane = new JScrollPane(text);   
        pane.setBackground(new Color(250,250,210));
        add(pane);
    } 

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
