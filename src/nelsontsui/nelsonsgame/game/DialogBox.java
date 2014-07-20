package nelsontsui.nelsonsgame.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DialogBox extends JPanel implements ActionListener{
    private ArrayList<String> text;
    private JTextArea textArea;
    private boolean hasBeenSelected;
    
    public final static String newline = "\n";
    public final static String bp = ">>";
    
    public DialogBox(int width, int height, Color color){
        setLayout(new GridLayout(1,1));
        setPreferredSize(new Dimension(width,height));
        
        textArea = new JTextArea(100,40);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setPreferredSize(new Dimension(width,height));
        textArea.setBackground(color);
        textArea.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                hasBeenSelected = true;
            }

            @Override
            public void focusLost(FocusEvent e) {}
        });
        
        JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(width,height));  
        
        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }
    public boolean getHasBeenSelected(){
        return hasBeenSelected;
    }
    public void setHasBeenSelected(boolean h){
        hasBeenSelected = h;
    }
    public void message(String s){
        textArea.append(s+newline);
    }    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(textArea.getLineCount()>textArea.getRows()){
            textArea.setRows(textArea.getRows()+100);
        }
        revalidate();
        repaint();
    }
    
    
    
}
