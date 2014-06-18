package nelsontsui.nelsonsgame.game;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JViewport;

public class DialogBox extends JPanel{
    private ArrayList<String> text;
    private JScrollPane pane;
    private JViewport view;
    public DialogBox(){
        this.view = new JViewport();
        this.view.setPreferredSize(new Dimension(this.getPreferredSize().width,this.getPreferredSize().height));
        pane = new JScrollPane(view,JScrollBar.VERTICAL,JScrollBar.UNDEFINED_CONDITION);
    }
    
    public static void main(String[] args){
        
    }
    
    
}
