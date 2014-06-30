/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.leveleditor;

import java.util.ArrayList;
import nelsontsui.nelsonsgame.game.Entity;
import nelsontsui.nelsonsgame.game.Character;

public class WriteFile {
    private String fileName;
    private ArrayList<Entity> npcs;
    private Character player;
    public WriteFile(String fileName, ArrayList<Entity> npcs, Character player){
        this.fileName = fileName;
        this.npcs = npcs;
        this.player = player;
    }
    public void write(String path){
        
    }
}
