package fr.olten.xmas.listener;

import fr.olten.xmas.Core;
import fr.olten.xmas.util.ScrollerInventory;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private final Core core;

    public InventoryClickListener(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        var player = (Player) e.getWhoClicked();
        var item = e.getCurrentItem();
        var view = e.getView();

        if(!ScrollerInventory.getUsers().containsKey(player.getUniqueId())) return;

        if(view.getTitle().contains(ChatColor.YELLOW + "Homes de ")){

            if(!item.hasItemMeta()) return;
            if(!item.getItemMeta().hasDisplayName()) return;
            if(!item.getType().equals(Material.GOLD_BLOCK)) return;

            var target = Bukkit.getOfflinePlayer(ChatColor.stripColor(view.getTitle()).substring(9));
            var home = this.core.getHomeManager().getHome(target.getUniqueId(), ChatColor.stripColor(item.getItemMeta().getDisplayName()));
            if(home.isPresent()){
                player.teleport(home.get().location());
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.AQUA + "Téléportation vers " + ChatColor.BOLD + target.getName() + "/" + home.get().name() + ChatColor.AQUA + "..."));
            }
            e.setCancelled(true);
        }
    }
}
