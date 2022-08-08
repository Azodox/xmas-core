package fr.olten.xmas.listener;

import fr.olten.xmas.Core;
import fr.olten.xmas.manager.TeamNameTagManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
import java.time.Duration;

public class PlayerJoinListener implements Listener {

    private final Core core;

    public PlayerJoinListener(Core core) {
        this.core = core;
    }

    /**
     * Called when a player joins the server.
     * Will update the player's team name tag and home's file's name field.
     * @param e The event.
     * @throws IOException If an error occurs.
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws IOException {
        Player player = e.getPlayer();
        if(this.core.getHomeManager().exists(player.getUniqueId())){
            this.core.getHomeManager().checkName(player);
        }else{
            this.core.getHomeManager().init(player);
        }

        TeamNameTagManager.update(player);
        player.showTitle(
                Title.title(Component.text("Joyeux Noël").color(NamedTextColor.AQUA),
                        Component.text(player.getName()).decorate(TextDecoration.BOLD),
                        Title.Times.times(Duration.ofSeconds(2), Duration.ofSeconds(4), Duration.ofSeconds(2)))
        );
    }
}
