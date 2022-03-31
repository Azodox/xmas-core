package fr.olten.xmas.commands;

import fr.olten.xmas.Core;
import fr.olten.xmas.home.Home;
import io.github.llewvallis.commandbuilder.CommandContext;
import io.github.llewvallis.commandbuilder.ExecuteCommand;
import io.github.llewvallis.commandbuilder.PlayerOnlyCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetHomeCommand {

    private final Core core;

    public SetHomeCommand(Core core) {
        this.core = core;
    }

    @ExecuteCommand
    @PlayerOnlyCommand
    public void setHome(CommandContext ctx, String homeName){
        Player player = (Player) ctx.getSender();
        if (this.core.getHomeManager().getHomes(player.getUniqueId()).stream().anyMatch(h -> h.name().equals(homeName))) {
            player.sendMessage(ChatColor.RED + "Erreur : un de vos homes possède déjà ce nom.");
            return;
        }

        this.core.getHomeManager().add(player.getUniqueId(), new Home(
                homeName,
                player.getLocation()
        ));
        player.sendMessage(ChatColor.AQUA + "Home créé avec succès.");
    }
}
