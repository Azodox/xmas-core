package fr.olten.xmas.commands;

import fr.olten.xmas.Core;
import fr.olten.xmas.home.Home;
import io.github.llewvallis.commandbuilder.CommandContext;
import io.github.llewvallis.commandbuilder.ExecuteCommand;
import io.github.llewvallis.commandbuilder.OptionalArg;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class HomeCommand {

    private final Core core;

    public HomeCommand(Core core) {
        this.core = core;
    }

    @ExecuteCommand
    private void home(CommandContext ctx, @OptionalArg OfflinePlayer offlinePlayer, String homeName){
        if(ctx.getArgumentStrings().get(0).equals(offlinePlayer.getName())){
            if(ctx.getSender().hasPermission("xmas.homes.see.others")){

            }
        }else {
            if(ctx.getSender() instanceof Player){
                Player player = (Player) ctx.getSender();
                Home home = new Home(UUID.randomUUID().toString(), player.getLocation());
                this.core.getHomeManager().add(player, home);
            }
        }
    }
}
