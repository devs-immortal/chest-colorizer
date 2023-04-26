package net.immortaldevs.colorizer;

import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.DyeItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import static net.minecraft.client.render.TexturedRenderLayers.CHEST_ATLAS_TEXTURE;

public class ColorizedChest {
    public static final SpriteIdentifier WHITE = createChestTextureId("white");
    public static final SpriteIdentifier WHITE_LEFT = createChestTextureId("white_left");
    public static final SpriteIdentifier WHITE_RIGHT = createChestTextureId("white_right");
    public static final SpriteIdentifier LIGHT_GRAY = createChestTextureId("light_gray");
    public static final SpriteIdentifier LIGHT_GRAY_LEFT = createChestTextureId("light_gray_left");
    public static final SpriteIdentifier LIGHT_GRAY_RIGHT = createChestTextureId("light_gray_right");
    public static final SpriteIdentifier GRAY = createChestTextureId("gray");
    public static final SpriteIdentifier GRAY_LEFT = createChestTextureId("gray_left");
    public static final SpriteIdentifier GRAY_RIGHT = createChestTextureId("gray_right");
    public static final SpriteIdentifier BLACK = createChestTextureId("black");
    public static final SpriteIdentifier BLACK_LEFT = createChestTextureId("black_left");
    public static final SpriteIdentifier BLACK_RIGHT = createChestTextureId("black_right");
    public static final SpriteIdentifier BROWN = createChestTextureId("brown");
    public static final SpriteIdentifier BROWN_LEFT = createChestTextureId("brown_left");
    public static final SpriteIdentifier BROWN_RIGHT = createChestTextureId("brown_right");
    public static final SpriteIdentifier RED = createChestTextureId("red");
    public static final SpriteIdentifier RED_LEFT = createChestTextureId("red_left");
    public static final SpriteIdentifier RED_RIGHT = createChestTextureId("red_right");
    public static final SpriteIdentifier ORANGE = createChestTextureId("orange");
    public static final SpriteIdentifier ORANGE_LEFT = createChestTextureId("orange_left");
    public static final SpriteIdentifier ORANGE_RIGHT = createChestTextureId("orange_right");
    public static final SpriteIdentifier YELLOW = createChestTextureId("yellow");
    public static final SpriteIdentifier YELLOW_LEFT = createChestTextureId("yellow_left");
    public static final SpriteIdentifier YELLOW_RIGHT = createChestTextureId("yellow_right");
    public static final SpriteIdentifier LIME = createChestTextureId("lime");
    public static final SpriteIdentifier LIME_LEFT = createChestTextureId("lime_left");
    public static final SpriteIdentifier LIME_RIGHT = createChestTextureId("lime_right");
    public static final SpriteIdentifier GREEN = createChestTextureId("green");
    public static final SpriteIdentifier GREEN_LEFT = createChestTextureId("green_left");
    public static final SpriteIdentifier GREEN_RIGHT = createChestTextureId("green_right");
    public static final SpriteIdentifier CYAN = createChestTextureId("cyan");
    public static final SpriteIdentifier CYAN_LEFT = createChestTextureId("cyan_left");
    public static final SpriteIdentifier CYAN_RIGHT = createChestTextureId("cyan_right");
    public static final SpriteIdentifier LIGHT_BLUE = createChestTextureId("light_blue");
    public static final SpriteIdentifier LIGHT_BLUE_LEFT = createChestTextureId("light_blue_left");
    public static final SpriteIdentifier LIGHT_BLUE_RIGHT = createChestTextureId("light_blue_right");
    public static final SpriteIdentifier BLUE = createChestTextureId("blue");
    public static final SpriteIdentifier BLUE_LEFT = createChestTextureId("blue_left");
    public static final SpriteIdentifier BLUE_RIGHT = createChestTextureId("blue_right");
    public static final SpriteIdentifier PURPLE = createChestTextureId("purple");
    public static final SpriteIdentifier PURPLE_LEFT = createChestTextureId("purple_left");
    public static final SpriteIdentifier PURPLE_RIGHT = createChestTextureId("purple_right");
    public static final SpriteIdentifier MAGENTA = createChestTextureId("magenta");
    public static final SpriteIdentifier MAGENTA_LEFT = createChestTextureId("magenta_left");
    public static final SpriteIdentifier MAGENTA_RIGHT = createChestTextureId("magenta_right");
    public static final SpriteIdentifier PINK = createChestTextureId("pink");
    public static final SpriteIdentifier PINK_LEFT = createChestTextureId("pink_left");
    public static final SpriteIdentifier PINK_RIGHT = createChestTextureId("pink_right");

    public static SpriteIdentifier getColorizedTextureId(BlockEntity entity, ChestType type) {
        String worldName = getLevelName();
        if (worldName == null) return null;
        ChestColor color = getColor(worldName, entity, type);
        if (color == null) return null;
        return switch (color) {
            case WHITE -> getColorizedTextureId(type, WHITE, WHITE_LEFT, WHITE_RIGHT);
            case LIGHT_GRAY -> getColorizedTextureId(type, LIGHT_GRAY, LIGHT_GRAY_LEFT, LIGHT_GRAY_RIGHT);
            case GRAY -> getColorizedTextureId(type, GRAY, GRAY_LEFT, GRAY_RIGHT);
            case BLACK -> getColorizedTextureId(type, BLACK, BLACK_LEFT, BLACK_RIGHT);
            case BROWN -> getColorizedTextureId(type, BROWN, BROWN_LEFT, BROWN_RIGHT);
            case RED -> getColorizedTextureId(type, RED, RED_LEFT, RED_RIGHT);
            case ORANGE -> getColorizedTextureId(type, ORANGE, ORANGE_LEFT, ORANGE_RIGHT);
            case YELLOW -> getColorizedTextureId(type, YELLOW, YELLOW_LEFT, YELLOW_RIGHT);
            case LIME -> getColorizedTextureId(type, LIME, LIME_LEFT, LIME_RIGHT);
            case GREEN -> getColorizedTextureId(type, GREEN, GREEN_LEFT, GREEN_RIGHT);
            case CYAN -> getColorizedTextureId(type, CYAN, CYAN_LEFT, CYAN_RIGHT);
            case LIGHT_BLUE -> getColorizedTextureId(type, LIGHT_BLUE, LIGHT_BLUE_LEFT, LIGHT_BLUE_RIGHT);
            case BLUE -> getColorizedTextureId(type, BLUE, BLUE_LEFT, BLUE_RIGHT);
            case PURPLE -> getColorizedTextureId(type, PURPLE, PURPLE_LEFT, PURPLE_RIGHT);
            case MAGENTA -> getColorizedTextureId(type, MAGENTA, MAGENTA_LEFT, MAGENTA_RIGHT);
            case PINK -> getColorizedTextureId(type, PINK, PINK_LEFT, PINK_RIGHT);
        };
    }

    public static SpriteIdentifier getColorizedTextureId(ChestType type, SpriteIdentifier normal, SpriteIdentifier left, SpriteIdentifier right) {
        return switch (type) {
            case LEFT -> left;
            case RIGHT -> right;
            default -> normal;
        };
    }

    public static void updateChestColor(BlockPos pos, DyeItem dyeItem) {
        String worldName = getLevelName();
        Config.setColor(worldName, pos, ChestColor.fromDyeColor(dyeItem.getColor()));
    }

    public static void clearChestColor(BlockPos pos) {
        String worldName = getLevelName();
        Config.removeColor(worldName, pos);
    }

    private static ChestColor getColor(String worldName, BlockEntity entity, ChestType type) {
        ChestColor color = Config.getColor(worldName, entity.getPos());
        if (color == null) {
            Direction chestDirection = entity.getCachedState().get(ChestBlock.FACING);
            if (type == ChestType.LEFT) {
                BlockPos right = entity.getPos().offset(chestDirection.rotateYClockwise());
                color = Config.getColor(worldName, right);
            } else if (type == ChestType.RIGHT) {
                BlockPos left = entity.getPos().offset(chestDirection.rotateYCounterclockwise());
                color = Config.getColor(worldName, left);
            }
        }
        return color;
    }

    private static String getLevelName() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.isInSingleplayer() && client.getServer() != null) {
            return client.getServer().getSaveProperties().getLevelName();
        }
        if (!client.isInSingleplayer() && client.getCurrentServerEntry() != null) {
            return client.getCurrentServerEntry().address;
        }
        return null;
    }

    private static SpriteIdentifier createChestTextureId(String variant) {
        return new SpriteIdentifier(CHEST_ATLAS_TEXTURE, new Identifier("colorizer", "entity/chest/" + variant));
    }
}
