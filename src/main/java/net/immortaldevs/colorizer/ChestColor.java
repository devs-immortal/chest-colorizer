package net.immortaldevs.colorizer;

import net.minecraft.util.DyeColor;
import net.minecraft.util.StringIdentifiable;

public enum ChestColor implements StringIdentifiable {
    DEFAULT("default"),
    WHITE("white"),
    LIGHT_GRAY("light_gray"),
    GRAY("gray"),
    BLACK("black"),
    BROWN("brown"),
    RED("red"),
    ORANGE("orange"),
    YELLOW("yellow"),
    LIME("lime"),
    GREEN("green"),
    CYAN("cyan"),
    LIGHT_BLUE("light_blue"),
    BLUE("blue"),
    PURPLE("purple"),
    MAGENTA("magenta"),
    PINK("pink");

    private final String name;

    ChestColor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ChestColor fromName(String name) {
        for (ChestColor color : values()) {
            if (color.getName().equals(name)) {
                return color;
            }
        }
        return null;
    }

    public static ChestColor fromDyeColor(DyeColor color) {
        return switch (color) {
            case WHITE -> WHITE;
            case LIGHT_GRAY -> LIGHT_GRAY;
            case GRAY -> GRAY;
            case BLACK -> BLACK;
            case BROWN -> BROWN;
            case RED -> RED;
            case ORANGE -> ORANGE;
            case YELLOW -> YELLOW;
            case LIME -> LIME;
            case GREEN -> GREEN;
            case CYAN -> CYAN;
            case LIGHT_BLUE -> LIGHT_BLUE;
            case BLUE -> BLUE;
            case PURPLE -> PURPLE;
            case MAGENTA -> MAGENTA;
            case PINK -> PINK;
        };
    }

    @Override
    public String asString() {
        return name;
    }
}
