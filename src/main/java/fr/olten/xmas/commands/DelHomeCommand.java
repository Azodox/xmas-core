package fr.olten.xmas.commands;

import fr.olten.xmas.Core;
import io.github.llewvallis.commandbuilder.CommandContext;
import io.github.llewvallis.commandbuilder.ExecuteCommand;
import io.github.llewvallis.commandbuilder.PlayerOnlyCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DelHomeCommand {

    private final Core core;

    public DelHomeCommand(Core core) {
        this.core = core;
    }

    @ExecuteCommand
    @PlayerOnlyCommand
    public void delHome(CommandContext ctx, String homeName){
        Player player = (Player) ctx.getSender();

        if(this.core.getHomeManager().getHome(player.getUniqueId(), homeName).isEmpty()){
            player.sendMessage(ChatColor.RED + "Erreur : ce home n'existe pas.");
            return;
        }

        this.core.getHomeManager().remove(player.getUniqueId(), homeName);
    }
}
