package fr.olten.xmas.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.olten.xmas.Core;
import fr.olten.xmas.home.Home;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;

@CommandAlias("home")
public class HomeCommand extends BaseCommand {

    private final Core core;

    public HomeCommand(Core core) {
        this.core = core;
    }

    @Default
    @Description("See home list.")
    private void home(Player player) {
        Set<Home> homes = this.core.getHomeManager().getHomes(player.getUniqueId());
        if(homes.isEmpty()){
            player.sendMessage(ChatColor.YELLOW + "Vous n'avez aucun home.");
            return;
        }
        player.sendMessage(Component.join(JoinConfiguration.commas(true), homes.stream().map(Home::toComponent).toList()));
    }

    @CatchUnknown
    @Description("Set a home.")
    public void home(Player player, String homeName){
        Optional<Home> optional = this.core.getHomeManager().getHome(player.getUniqueId(), homeName);
        if(optional.isEmpty()){
            player.sendMessage(ChatColor.RED + "Erreur : ce home n'existe pas.");
            return;
        }

        player.sendActionBar(Component.text(ChatColor.GRAY + "Téléportation vers " + ChatColor.BOLD + homeName + ChatColor.GRAY + "..."));
        player.teleport(optional.get().location());
    }

    @HelpCommand
    public void onHelp(Player player){
        player.sendMessage(Component.text("/home [home]").color(NamedTextColor.RED));
    }
}
