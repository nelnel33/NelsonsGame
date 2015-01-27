/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.game.mapping;

import nelsontsui.nelsonsgame.game.entities.Entity;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import nelsontsui.nelsonsgame.game.entities.Entity;
import nelsontsui.nelsonsgame.game.entities.Player;



/**
 *
 * @author Nelnel33
 */
public class Level{
    
    private Player player;
    private ArrayList<Entity> entities;
    
    public Level(){
        
    }

    public Level(Player player, ArrayList<Entity> entities) {
        this.player = player;
        this.entities = entities;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }
    
    public void addEntities(Entity entity){
        this.entities.add(entity);
    }
    
}
