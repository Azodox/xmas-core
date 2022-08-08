package fr.olten.xmas.listener;

import fr.olten.xmas.manager.TeamNameTagManager;
import net.valneas.account.events.rank.MajorRankChangedEvent;
import net.valneas.account.lib.eventbus.Subscribe;
import org.bukkit.command.CommandSender;

/**
 * Listener for major rank changes.
 */
public class RankChangedListener {

    /**
     * Handle the event when a player change his rank
     * @param event The event
     */
    @Subscribe
    public void onRankChange(MajorRankChangedEvent<CommandSender> event) {
        TeamNameTagManager.update(event.getAccountManager());
    }
}
