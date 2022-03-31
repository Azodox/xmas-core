package fr.olten.xmas.home;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
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

    public BaseComponent toComponent(){
        TextComponent textComponent = new TextComponent(name);
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(
                ChatColor.DARK_GRAY + "§m                 \n" +
                        ChatColor.RED + "x : " + ChatColor.YELLOW + location.getX() + "\n" +
                        ChatColor.RED + "y : " + ChatColor.YELLOW + location.getY() + "\n" +
                        ChatColor.RED + "z : " + ChatColor.YELLOW + location.getZ() + "\n" +
                        ChatColor.RED + "yaw : " + ChatColor.YELLOW + location.getYaw() + "\n" +
                        ChatColor.RED + "pitch : " + ChatColor.YELLOW + location.getPitch() + "\n" +
                        ChatColor.RED + "world : " + ChatColor.YELLOW + location.getWorld().getName() + "\n" +
                        ChatColor.DARK_GRAY + "§m                 "
        )));
        return textComponent;
    }
}
