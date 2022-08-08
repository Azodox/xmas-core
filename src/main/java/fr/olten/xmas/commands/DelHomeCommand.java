package fr.olten.xmas.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.HelpCommand;
import fr.olten.xmas.Core;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

@CommandAlias("delhome")
public class DelHomeCommand extends BaseCommand {

    private final Core core;

    public DelHomeCommand(Core core) {
        this.core = core;
    }

    @Default
    @Description("Delete a home.")
    public void delHome(Player player, String homeName){
        if(this.core.getHomeManager().getHome(player.getUniqueId(), homeName).isEmpty()){
            player.sendMessage(Component.text("Erreur : ce home n'existe pas.").color(NamedTextColor.RED));
            return;
        }

        this.core.getHomeManager().remove(player.getUniqueId(), homeName);
    }

    @HelpCommand
    public void onHelp(Player player){
        player.sendMessage(Component.text("/delhome <homeName>").color(NamedTextColor.RED));
    }
}
