package nelsontsui.nelsonsgame.leveleditor;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AnalysisPanel extends JDialog{    
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
        if(this.text.equals(s)){}
        else{
            text.setText(s);
            repaint();
        }
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
}
