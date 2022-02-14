package fr.olten.xmas.commands.arguments;

import io.github.llewvallis.commandbuilder.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OfflinePlayerArgument implements ArgumentParser<OfflinePlayer> {

    @Override
    public OfflinePlayer parse(String argument, int position, CommandContext context) throws ArgumentParseException {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(argument);
        if (!offlinePlayer.isOnline() && !offlinePlayer.hasPlayedBefore()) {
            throw new ArgumentParseException("ce joueur n'existe pas");
        }

        return offlinePlayer;
    }

    @Override
    public Set<String> complete(List<Object> parsedArguments, String currentArgument, int position, CommandContext context) {
        return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toSet());
    }
}
