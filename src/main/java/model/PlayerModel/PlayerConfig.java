package model.PlayerModel;

import java.awt.Color;

/**
 * A simple data class to hold configuration details for a player
 * before the actual Player object is created for the game logic.
 */
public class PlayerConfig {
    public final String name;
    public final boolean isBot;
    public final Color color;

    public PlayerConfig(String name, boolean isBot, Color color) {
        this.name = name;
        this.isBot = isBot;
        this.color = color;
    }

    // Optional: Add getters if needed elsewhere, though public final fields are often sufficient for simple data holders.
    public String getName() {
        return name;
    }

    public boolean isBot() {
        return isBot;
    }

    public Color getColor() {
        return color;
    }
}
