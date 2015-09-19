package nelsontsui.nelsonsgame.game.imagearchiver;

import nelsontsui.nelsonsgame.game.entities.DamageableEntity;
import nelsontsui.nelsonsgame.game.entities.Entity;
import nelsontsui.nelsonsgame.game.entities.OpaqueEntity;
import nelsontsui.nelsonsgame.game.entities.Character;
import nelsontsui.nelsonsgame.game.entities.MapGate;
import nelsontsui.nelsonsgame.game.entities.NonPlayerCharacter;
import nelsontsui.nelsonsgame.game.entities.Player;
import nelsontsui.nelsonsgame.game.entities.Portal;
import nelsontsui.nelsonsgame.game.entities.Projectile;
import nelsontsui.nelsonsgame.game.entities.SpawnableItem;
import nelsontsui.nelsonsgame.game.entities.TalkableGate;

/**
 *
 * @author Nelnel33
 */
public class EntityResourceArchiver extends ResourceArchiver{
    
    public static final String DIR = "entities/";
    
    //EVERYTHING WILL DEFAULT TO ENTITY IMAGE
    public ImportedImage ENTITY;
    
    //SHOULD NEVER BE USED
    public ImportedImage CHARACTER;
    
    //IMAGES THAT WILL DEFINETLY BE USED        
    public ImportedImage OPAQUE_ENTITY;
    public ImportedImage DAMAGEABLE_ENTITY;
    public ImportedImage PLAYER;
    public ImportedImage NON_PLAYER_CHARACTER;
    
    public ImportedImage MAP_GATE;
    public ImportedImage TALKABLE_GATE;
    public ImportedImage PORTAL;    
    
    public ImportedImage SPAWNABLE_ITEM;    
    public ImportedImage PROJECTILE;
    
    public EntityResourceArchiver(){
        //DEFAULT
        initImportedImage(ENTITY, DIR+Entity.FILENAME, ImportedImage.ENTITIES_DIR);
        
        //SHOULD NOT BE USED       
        initImportedImage(CHARACTER, DIR+Character.FILENAME, ImportedImage.ENTITIES_DIR);
        
        //WILL BE USED
        initImportedImage(OPAQUE_ENTITY,  DIR+OpaqueEntity.FILENAME, ImportedImage.ENTITIES_DIR);        
        initImportedImage(DAMAGEABLE_ENTITY, DIR+DamageableEntity.FILENAME, ImportedImage.ENTITIES_DIR); 
        initImportedImage(PLAYER, DIR+Player.FILENAME, ImportedImage.ENTITIES_DIR);
        initImportedImage(NON_PLAYER_CHARACTER, DIR+NonPlayerCharacter.FILENAME, ImportedImage.ENTITIES_DIR);

        initImportedImage(MAP_GATE, DIR+MapGate.FILENAME, ImportedImage.ENTITIES_DIR);
        initImportedImage(TALKABLE_GATE, DIR+TalkableGate.FILENAME, ImportedImage.ENTITIES_DIR);
        initImportedImage(PORTAL, DIR+Portal.FILENAME, ImportedImage.ENTITIES_DIR);

        initImportedImage(SPAWNABLE_ITEM,SpawnableItem.FILENAME, ImportedImage.ENTITIES_DIR);
        initImportedImage(PROJECTILE,Projectile.FILENAME, ImportedImage.ENTITIES_DIR);
    }
}
