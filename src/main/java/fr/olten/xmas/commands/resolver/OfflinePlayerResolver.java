package fr.olten.xmas.commands.resolver;

import co.aikar.commands.BukkitCommandExecutionContext;
import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.contexts.ContextResolver;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class OfflinePlayerResolver implements ContextResolver<OfflinePlayer, BukkitCommandExecutionContext> {

    @Override
    public OfflinePlayer getContext(BukkitCommandExecutionContext bukkitCommandExecutionContext) throws InvalidCommandArgument {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(bukkitCommandExecutionContext.getFirstArg());

        if (!offlinePlayer.hasPlayedBefore()) {
            throw new InvalidCommandArgument("ce joueur n'existe pas");
        }

        return offlinePlayer;
    }
}
