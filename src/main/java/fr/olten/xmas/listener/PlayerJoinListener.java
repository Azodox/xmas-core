package fr.olten.xmas.listener;

import fr.olten.xmas.Core;
import fr.olten.xmas.home.Home;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;

public class PlayerJoinListener implements Listener {

    private final Core core;

    public PlayerJoinListener(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws IOException {
        Player player = e.getPlayer();
        if(this.core.getHomeManager().exists(player.getUniqueId())){
            this.core.getHomeManager().checkName(player);
        }else{
            this.core.getHomeManager().init(player);
        }
    }
}
