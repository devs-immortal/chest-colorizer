package net.immortaldevs.colorizer;

import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.core.BlockPos;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("UnstableApiUsage")
public class ColorizerClientGameTest implements FabricClientGameTest {
    @Override
    public void runTest(@NonNull ClientGameTestContext context) {
        try (TestSingleplayerContext singleplayer = context.worldBuilder().create()) {
            singleplayer.getClientLevel().waitForChunksDownload();

            BlockPos chestPos = blockInFrontOfPlayer(singleplayer);
            BlockPos barrelPos = chestPos.east();
            setBlock(singleplayer, chestPos, "minecraft:chest");
            setBlock(singleplayer, barrelPos, "minecraft:barrel");

            renderInView(context, singleplayer, chestPos, "colorizer-chest-barrel");

            sneakRightClickWith(context, singleplayer, chestPos, "minecraft:red_dye");
            assertStoredColorIs(context, chestPos, BlockColor.RED);
            context.takeScreenshot("colorizer-chest-dyed-red");

            sneakRightClickWith(context, singleplayer, barrelPos, "minecraft:blue_dye");
            assertStoredColorIs(context, barrelPos, BlockColor.BLUE);
            renderInView(context, singleplayer, barrelPos, "colorizer-barrel-dyed-blue");

            sneakRightClickWith(context, singleplayer, chestPos, "minecraft:paper");
            assertStoredColorIs(context, chestPos, BlockColor.DEFAULT);

            sneakRightClickWith(context, singleplayer, barrelPos, "minecraft:paper");
            assertStoredColorIs(context, barrelPos, BlockColor.DEFAULT);
            renderInView(context, singleplayer, barrelPos, "colorizer-cleared");
        }
    }

    private static BlockPos blockInFrontOfPlayer(TestSingleplayerContext singleplayer) {
        BlockPos playerSpawn = singleplayer.getServer().computeOnServer(
                server -> server.getPlayerList().getPlayers().getFirst().blockPosition()
        );
        return playerSpawn.north(3);
    }

    private static void setBlock(TestSingleplayerContext singleplayer, BlockPos pos, String blockId) {
        singleplayer.getServer().runCommand(
                "setblock %d %d %d %s".formatted(pos.getX(), pos.getY(), pos.getZ(), blockId)
        );
    }

    private static void renderInView(
            ClientGameTestContext context, TestSingleplayerContext singleplayer, BlockPos target, String screenshotName
    ) {
        context.getInput().lookAt(target);
        singleplayer.getClientLevel().waitForChunksRender();
        context.waitTicks(5);
        context.takeScreenshot(screenshotName);
    }

    private static void sneakRightClickWith(
            ClientGameTestContext context, TestSingleplayerContext singleplayer, BlockPos target, String itemId
    ) {
        singleplayer.getServer().runCommand("item replace entity @p hotbar.0 with " + itemId);
        context.waitTicks(3);

        context.getInput().lookAt(target);
        context.waitTicks(2);
        context.getInput().holdKey(options -> options.keyShift);
        context.waitTicks(2);
        context.getInput().pressKey(options -> options.keyUse);
        context.waitTicks(5);
        context.getInput().releaseKey(options -> options.keyShift);
    }

    private static void assertStoredColorIs(ClientGameTestContext context, BlockPos pos, BlockColor expected) {
        context.runOnClient(_ -> {
            BlockColor actual = ColorManager.getColor(pos);
            if (actual != expected) {
                throw new AssertionError("expected colour " + expected + " at " + pos + " but was " + actual);
            }
        });
    }
}
