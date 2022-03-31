package fr.olten.xmas.commands;

import fr.olten.xmas.Core;
import fr.olten.xmas.util.HeadUtil;
import fr.olten.xmas.util.ItemBuilder;
import fr.olten.xmas.util.ScrollerInventory;
import io.github.llewvallis.commandbuilder.CommandContext;
import io.github.llewvallis.commandbuilder.ExecuteCommand;
import io.github.llewvallis.commandbuilder.PlayerOnlyCommand;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Random;

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
        var list = HeadUtil.getTranslator().keySet().stream().filter(s -> s.startsWith("birdhouse-")).toList();
        var items = homes.stream().map(home -> new ItemBuilder(HeadUtil.getHead(list.get(new Random().nextInt(list.size()))).clone()).setName(ChatColor.GOLD + home.name()).build()).toList();
        new ScrollerInventory(items, ChatColor.YELLOW + "Homes de " + ChatColor.WHITE + "" + ChatColor.BOLD + player.getName(), sender);
    }
}
