package nelsontsui.nelsonsgame.leveleditor;

import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import nelsontsui.nelsonsgame.game.Character;
import nelsontsui.nelsonsgame.game.Entity;

public class AnalysisPanel extends JDialog{
    private ArrayList<Entity> npcs = new ArrayList<>();
    private Character player;
    private JPanel entities;
    private JPanel entityStatistics;
    public AnalysisPanel(JFrame owner, ArrayList<Entity> npcs, Character player){
        super(owner, "Nelson's Game: Entity Analyzer");
        this.npcs = npcs;
        this.player = player;
        
        init();
    }
    private void init(){
        
    }
    
    public static void main(String[] args){
        
    }
}
