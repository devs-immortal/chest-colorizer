package net.immortaldevs.colorizer;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.DyeColor;
import net.minecraft.util.StringIdentifiable;

public enum BlockColor implements StringIdentifiable {
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

    public static final PacketCodec<ByteBuf, BlockColor> PACKET_CODEC = PacketCodec.of(
            (color, buf) -> buf.writeInt(color.ordinal()),
            buf -> {
                int ordinal = buf.readInt();
                return values()[ordinal];
            }
    );

    private final String name;

    BlockColor(String name) {
        this.name = name;
    }

    public static BlockColor fromName(String name) {
        for (BlockColor color : values()) {
            if (color.getName().equals(name)) {
                return color;
            }
        }
        return null;
    }

    public static BlockColor fromDyeColor(DyeColor color) {
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

    public String getName() {
        return name;
    }

    @Override
    public String asString() {
        return name;
    }
}
