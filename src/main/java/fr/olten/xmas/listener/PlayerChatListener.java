package fr.olten.xmas.listener;

import fr.olten.xmas.Core;
import net.md_5.bungee.api.ChatColor;
import net.valneas.account.AccountManager;
import net.valneas.account.AccountSystem;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    private final Core core;

    public PlayerChatListener(Core core) {
        this.core = core;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent event) {
        var player = event.getPlayer();
        var accountSystem = (AccountSystem) Bukkit.getPluginManager().getPlugin("AccountSystem");

        if(accountSystem == null){
            core.getLogger().severe("AccountSystem is not installed");
            return;
        }

        var accountManager = new AccountManager(accountSystem, player);
        var rank= accountManager.newRankManager().getMajorRank();

        event.setMessage(event.getMessage().replace("<3", ChatColor.of("#C51104") + "\u2764"));
        event.setFormat(Core.CHAT_FORMAT.replace("%rank%", rank.getPrefix()));
    }
}
