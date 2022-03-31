package fr.olten.xmas.commands;

import fr.olten.xmas.Core;
import fr.olten.xmas.util.ItemBuilder;
import fr.olten.xmas.util.ScrollerInventory;
import io.github.llewvallis.commandbuilder.CommandContext;
import io.github.llewvallis.commandbuilder.ExecuteCommand;
import io.github.llewvallis.commandbuilder.PlayerOnlyCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class HomesCommand {

    private final Core core;

    public HomesCommand(Core core) {
        this.core = core;
    }

    @ExecuteCommand
    @PlayerOnlyCommand
    public void homes(CommandContext ctx, OfflinePlayer player){
        var sender = (Player) ctx.getSender();
        var homes = this.core.getHomeManager().getHomes(player.getUniqueId());
        var items = homes.stream().map(home -> new ItemBuilder(Material.GOLD_BLOCK).setName(ChatColor.GOLD + home.name()).build()).toList();
        new ScrollerInventory(items, ChatColor.YELLOW + "Homes de " + ChatColor.WHITE + "" + ChatColor.BOLD + player.getName(), sender);
    }
}
