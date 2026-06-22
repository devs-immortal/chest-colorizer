package net.immortaldevs.colorizer;

import net.immortaldevs.colorizer.block.ColorizedBarrelBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.state.ChestRenderState;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

import static net.minecraft.client.renderer.Sheets.CHEST_SHEET;

public class ColorManager {
    public static final SpriteId WHITE = createChestTextureId("white");
    public static final SpriteId WHITE_LEFT = createChestTextureId("white_left");
    public static final SpriteId WHITE_RIGHT = createChestTextureId("white_right");
    public static final SpriteId LIGHT_GRAY = createChestTextureId("light_gray");
    public static final SpriteId LIGHT_GRAY_LEFT = createChestTextureId("light_gray_left");
    public static final SpriteId LIGHT_GRAY_RIGHT = createChestTextureId("light_gray_right");
    public static final SpriteId GRAY = createChestTextureId("gray");
    public static final SpriteId GRAY_LEFT = createChestTextureId("gray_left");
    public static final SpriteId GRAY_RIGHT = createChestTextureId("gray_right");
    public static final SpriteId BLACK = createChestTextureId("black");
    public static final SpriteId BLACK_LEFT = createChestTextureId("black_left");
    public static final SpriteId BLACK_RIGHT = createChestTextureId("black_right");
    public static final SpriteId BROWN = createChestTextureId("brown");
    public static final SpriteId BROWN_LEFT = createChestTextureId("brown_left");
    public static final SpriteId BROWN_RIGHT = createChestTextureId("brown_right");
    public static final SpriteId RED = createChestTextureId("red");
    public static final SpriteId RED_LEFT = createChestTextureId("red_left");
    public static final SpriteId RED_RIGHT = createChestTextureId("red_right");
    public static final SpriteId ORANGE = createChestTextureId("orange");
    public static final SpriteId ORANGE_LEFT = createChestTextureId("orange_left");
    public static final SpriteId ORANGE_RIGHT = createChestTextureId("orange_right");
    public static final SpriteId YELLOW = createChestTextureId("yellow");
    public static final SpriteId YELLOW_LEFT = createChestTextureId("yellow_left");
    public static final SpriteId YELLOW_RIGHT = createChestTextureId("yellow_right");
    public static final SpriteId LIME = createChestTextureId("lime");
    public static final SpriteId LIME_LEFT = createChestTextureId("lime_left");
    public static final SpriteId LIME_RIGHT = createChestTextureId("lime_right");
    public static final SpriteId GREEN = createChestTextureId("green");
    public static final SpriteId GREEN_LEFT = createChestTextureId("green_left");
    public static final SpriteId GREEN_RIGHT = createChestTextureId("green_right");
    public static final SpriteId CYAN = createChestTextureId("cyan");
    public static final SpriteId CYAN_LEFT = createChestTextureId("cyan_left");
    public static final SpriteId CYAN_RIGHT = createChestTextureId("cyan_right");
    public static final SpriteId LIGHT_BLUE = createChestTextureId("light_blue");
    public static final SpriteId LIGHT_BLUE_LEFT = createChestTextureId("light_blue_left");
    public static final SpriteId LIGHT_BLUE_RIGHT = createChestTextureId("light_blue_right");
    public static final SpriteId BLUE = createChestTextureId("blue");
    public static final SpriteId BLUE_LEFT = createChestTextureId("blue_left");
    public static final SpriteId BLUE_RIGHT = createChestTextureId("blue_right");
    public static final SpriteId PURPLE = createChestTextureId("purple");
    public static final SpriteId PURPLE_LEFT = createChestTextureId("purple_left");
    public static final SpriteId PURPLE_RIGHT = createChestTextureId("purple_right");
    public static final SpriteId MAGENTA = createChestTextureId("magenta");
    public static final SpriteId MAGENTA_LEFT = createChestTextureId("magenta_left");
    public static final SpriteId MAGENTA_RIGHT = createChestTextureId("magenta_right");
    public static final SpriteId PINK = createChestTextureId("pink");
    public static final SpriteId PINK_LEFT = createChestTextureId("pink_left");
    public static final SpriteId PINK_RIGHT = createChestTextureId("pink_right");

    public static SpriteId getColorizedTextureId(ChestRenderState chestRenderState) {
        String worldName = getLevelName();
        if (worldName == null) return null;
        BlockColor color = getChestColor(worldName, chestRenderState);
        ChestType type = chestRenderState.type;
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
            case DEFAULT -> null;
        };
    }

    public static SpriteId getColorizedTextureId(ChestType type, SpriteId normal, SpriteId left, SpriteId right) {
        return switch (type) {
            case LEFT -> left;
            case RIGHT -> right;
            default -> normal;
        };
    }

    public static BlockColor getColor(BlockPos pos) {
        String worldName = getLevelName();
        BlockColor color = Config.getColor(worldName, pos);
        if (color == null) return BlockColor.DEFAULT;
        return color;
    }

    public static void updateColor(BlockPos pos, DyeItem dyeItem) {
        String worldName = getLevelName();
        DyeColor dyeColor = dyeItem.components().get(DataComponents.DYE);
        Config.setColor(worldName, pos, BlockColor.fromDyeColor(dyeColor));
    }

    public static void clearColor(BlockPos pos) {
        String worldName = getLevelName();
        Config.removeColor(worldName, pos);
    }

    public static void clearChestColor(BlockPos pos, BlockState state) {
        Direction chestDirection = state.getValue(ChestBlock.FACING);
        ChestType chestType = state.getValue(ChestBlock.TYPE);
        String worldName = getLevelName();
        Config.removeColor(worldName, pos);
        if (chestType == ChestType.LEFT) {
            BlockPos right = pos.offset(chestDirection.getClockWise().getUnitVec3i());
            Config.removeColor(worldName, right);
        } else if (chestType == ChestType.RIGHT) {
            BlockPos left = pos.offset(chestDirection.getCounterClockWise().getUnitVec3i());
            Config.removeColor(worldName, left);
        }
    }

    public static BlockState getBarrelState(BlockState state, BlockPos pos) {
        return ColorizerMod.BARREL_BLOCK.defaultBlockState()
                .setValue(ColorizedBarrelBlock.COLOR, getColor(pos))
                .setValue(BarrelBlock.FACING, state.getValue(BarrelBlock.FACING))
                .setValue(BarrelBlock.OPEN, state.getValue(BarrelBlock.OPEN));
    }


    private static BlockColor getChestColor(String worldName, ChestRenderState chestRenderState) {
        BlockColor color = Config.getColor(worldName, chestRenderState.blockPos);
        ChestType type = chestRenderState.type;
        if (color == null) {
            Direction chestDirection = chestRenderState.blockState.getValue(ChestBlock.FACING);
            if (type == ChestType.LEFT) {
                BlockPos right = chestRenderState.blockPos.offset(chestDirection.getClockWise().getUnitVec3i());
                color = Config.getColor(worldName, right);
            } else if (type == ChestType.RIGHT) {
                BlockPos left = chestRenderState.blockPos.offset(chestDirection.getCounterClockWise().getUnitVec3i());
                color = Config.getColor(worldName, left);
            }
        }
        return color;
    }

    private static String getLevelName() {
        Minecraft client = Minecraft.getInstance();
        IntegratedServer singleplayerServer = client.getSingleplayerServer();

        if (singleplayerServer != null) {
            return singleplayerServer.getWorldData().getLevelName();
        }
        if (client.getCurrentServer() != null) {
            return client.getCurrentServer().ip;
        }
        return null;
    }

    private static SpriteId createChestTextureId(String variant) {
        return new SpriteId(CHEST_SHEET, Identifier.fromNamespaceAndPath(ColorizerMod.MOD_ID, "entity/chest/" + variant));
    }
}
