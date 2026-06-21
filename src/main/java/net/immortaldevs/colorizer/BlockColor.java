package net.immortaldevs.colorizer;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import org.jspecify.annotations.NonNull;

public enum BlockColor implements StringRepresentable {
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

    BlockColor(String name) {
        this.name = name;
    }

    public static BlockColor fromName(String name) {
        for (BlockColor color : values()) {
            if (color.getSerializedName().equals(name)) {
                return color;
            }
        }
        return null;
    }

    public static BlockColor fromDyeColor(DyeColor color) {
        return valueOf(color.name());
    }

    @Override
    public @NonNull String getSerializedName() {
        return name;
    }
}
