package fr.olten.xmas.home;

import org.bukkit.Location;

public record Home(String name, Location location) {

    @Override
    public String toString() {
        return "Home{" +
                "name='" + name + '\'' +
                ", location=" + location.toString() +
                '}';
    }
}
