package fr.olten.xmas.listener;

import fr.olten.xmas.manager.TeamNameTagManager;
import net.valneas.account.api.events.rank.MajorRankChangedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Listener for major rank changes.
 */
public class RankChangedListener implements Listener {

    /**
     * Handle the event when a player change his rank
     * @param event The event
     */
    @EventHandler
    public void onRankChange(MajorRankChangedEvent event) {
        TeamNameTagManager.update(event.getAccount());
    }
}
