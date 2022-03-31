package fr.olten.xmas.commands;

import fr.olten.xmas.Core;
import fr.olten.xmas.home.Home;
import io.github.llewvallis.commandbuilder.CommandContext;
import io.github.llewvallis.commandbuilder.ExecuteCommand;
import io.github.llewvallis.commandbuilder.OptionalArg;
import io.github.llewvallis.commandbuilder.PlayerOnlyCommand;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;


public class HomeCommand {

    private final Core core;

    public HomeCommand(Core core) {
        this.core = core;
    }

    @ExecuteCommand
    @PlayerOnlyCommand
    private void home(CommandContext ctx, @OptionalArg String homeName) {
        Player player = (Player) ctx.getSender();
        if(ctx.getArgumentStrings().size() == 0){
            Set<Home> homes = this.core.getHomeManager().getHomes(player.getUniqueId());
            if(homes.isEmpty()){
                player.sendMessage(ChatColor.YELLOW + "Vous n'avez aucun home.");
                return;
            }
            player.spigot().sendMessage(homes.stream().map(h -> {
                BaseComponent baseComponent = h.toComponent();
                baseComponent.addExtra(" ");
                return baseComponent;
            }).toArray(BaseComponent[]::new));

            return;
        }

        Optional<Home> optional = this.core.getHomeManager().getHome(player.getUniqueId(), homeName);
        if(optional.isEmpty()){
            player.sendMessage(ChatColor.RED + "Erreur : ce home n'existe pas.");
            return;
        }

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GRAY + "Téléportation vers " + ChatColor.BOLD + homeName + ChatColor.GRAY + "..."));
        player.teleport(optional.get().location());
    }
}
