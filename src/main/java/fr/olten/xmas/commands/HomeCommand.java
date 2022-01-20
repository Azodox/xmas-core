package fr.olten.xmas.commands;

import io.github.llewvallis.commandbuilder.CommandContext;
import io.github.llewvallis.commandbuilder.ExecuteCommand;
import io.github.llewvallis.commandbuilder.OptionalArg;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class HomeCommand {

    @ExecuteCommand
    private void home(CommandContext ctx, @OptionalArg OfflinePlayer offlinePlayer, String homeName){
        if(ctx.getArgumentStrings().get(0).equals(offlinePlayer.getName())){
            if(ctx.getSender().hasPermission("xmas.homes.see.others")){

            }
        }else {
            if(ctx.getSender() instanceof Player){
                Player player = (Player) ctx.getSender();

            }
        }
    }
}
