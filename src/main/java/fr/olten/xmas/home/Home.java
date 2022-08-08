package fr.olten.xmas.home;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public record Home(String name, Location location) {

    @Override
    public String toString() {
        return "Home{" +
                "name='" + name + '\'' +
                ", location=" + location.toString() +
                '}';
    }

    public Component toComponent(){
        return Component.text(name).hoverEvent(HoverEvent.showText(Component.text(
                ChatColor.DARK_GRAY + "§m                 \n" +
                        ChatColor.RED + "x : " + ChatColor.YELLOW + location.getX() + "\n" +
                        ChatColor.RED + "y : " + ChatColor.YELLOW + location.getY() + "\n" +
                        ChatColor.RED + "z : " + ChatColor.YELLOW + location.getZ() + "\n" +
                        ChatColor.RED + "yaw : " + ChatColor.YELLOW + location.getYaw() + "\n" +
                        ChatColor.RED + "pitch : " + ChatColor.YELLOW + location.getPitch() + "\n" +
                        ChatColor.RED + "world : " + ChatColor.YELLOW + location.getWorld().getName() + "\n" +
                        ChatColor.DARK_GRAY + "§m                 "
        )));
    }
}
