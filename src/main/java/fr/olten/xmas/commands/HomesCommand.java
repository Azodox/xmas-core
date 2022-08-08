package fr.olten.xmas.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.olten.xmas.Core;
import fr.olten.xmas.inventory.HomeListView;
import me.saiintbrisson.minecraft.ViewFrame;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@CommandAlias("homes")
public class HomesCommand extends BaseCommand {

    private final Core core;

    public HomesCommand(Core core) {
        this.core = core;
    }

    @Default
    @Description("List homes.")
    public void homes(Player player){
        this.openHomeList(player, player);
    }

    @CatchUnknown
    @Description("List homes of a player.")
    @CommandPermission("xmas.homes.list.other")
    @CommandCompletion("@target")
    public void homes(Player player, OfflinePlayer target){
        this.openHomeList(player, target);
    }
    @HelpCommand
    public void onHelp(Player player){
        player.sendMessage(Component.text(String.format("/homes%s", player.hasPermission("xmas.homes.list.other") ? " [joueur]" : "")).color(NamedTextColor.RED));
    }

    private void openHomeList(Player player, OfflinePlayer target){
        var view = new HomeListView(this.core, 4 * 9, ChatColor.YELLOW + "Homes de " + ChatColor.WHITE + "" + ChatColor.BOLD + target.getName(),
                this.core.getHomeManager().getHomes(target.getUniqueId()).stream().toList(), target);
        var viewFrame = ViewFrame.of(this.core, view).register();
        viewFrame.open(view.getClass(), player);
    }

}
