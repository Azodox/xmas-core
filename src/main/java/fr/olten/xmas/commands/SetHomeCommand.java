package fr.olten.xmas.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.HelpCommand;
import fr.olten.xmas.Core;
import fr.olten.xmas.home.Home;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("sethome")
public class SetHomeCommand extends BaseCommand {

    private final Core core;

    public SetHomeCommand(Core core) {
        this.core = core;
    }

    @Default
    @Description("Set a home.")
    public void setHome(Player player, String homeName){
        if (this.core.getHomeManager().getHomes(player.getUniqueId()).stream().anyMatch(h -> h.name().equals(homeName))) {
            player.sendMessage(ChatColor.RED + "Erreur : un de vos homes possède déjà ce nom.");
            return;
        }

        this.core.getHomeManager().add(player.getUniqueId(), new Home(
                homeName,
                player.getLocation()
        ));
        player.sendMessage(Component.text("Home créé avec succès.").color(NamedTextColor.AQUA));
    }

    @HelpCommand
    public void onHelp(Player player){
        player.sendMessage(Component.text("/sethome <nom>").color(NamedTextColor.RED));
    }
}
