package fr.olten.xmas.listener;

import fr.olten.xmas.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final Core core;

    public PlayerJoinListener(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if(this.core.getHomeManager().exists(player)){
            this.core.getHomeManager().checkName(player);
        }else{
            this.core.getHomeManager().init(player);
        }
    }
}
