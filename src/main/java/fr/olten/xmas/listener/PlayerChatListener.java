package fr.olten.xmas.listener;

import fr.olten.xmas.Core;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.valneas.account.AccountSystemApi;
import net.valneas.account.rank.RankManager;
import net.valneas.account.rank.RankUnit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerChatListener implements Listener {

    private final Core core;
    private final AccountSystemApi accountSystem;

    public PlayerChatListener(Core core, AccountSystemApi accountSystem) {
        this.core = core;
        this.accountSystem = accountSystem;
    }

    /**
     * Handle the chat event.
     * Replace <3 by the heart symbol.
     * And set the format.
     * @param event The event
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncChatEvent event) {
        var player = event.getPlayer();
        var accountManager = accountSystem.getAccountManager(player);
        var rank= accountManager.newRankManager().getMajorRank();

        RankManager<? extends RankUnit> rankManager = accountManager.newRankManager();



        event.message(event.message().replaceText(builder -> builder.matchLiteral("<3").replacement(Component.text("\u2764").color(TextColor.color(0xC51104)))));
        event.renderer((source, sourceDisplayName, message, viewer) ->
                MiniMessage.miniMessage().deserialize(rank.getPrefix())
                        .append(sourceDisplayName.color(NamedTextColor.NAMES.value(rank.getColor())))
                .append(Component.text(" : ").color(NamedTextColor.GRAY))
                .append(message));
    }
}
